package com.lc.pattern.structure.proxy.dynamicproxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * java.lang.reflect.Proxy
 * @author LittleCharacter
 */
public class Proxy {
	private static int proxyCount = 0;//代理类计数器

	/**
	 * 1.由于tools api支持有限，并且为JDK1.6的新支持，兼容性太差，编译速度也不快，这也是为什么SUN自己都不用，反而用Groove<br>
	 * 2.本方法使用JVM的编译器API，用不到loader{ClassLoader}
	 */
	public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) {
		if (h == null) {
			throw new NullPointerException();
		}

		//只保留最后的直接接口
		Set<Class<?>> interfaceSet = new HashSet<Class<?>>();
		for (Class<?> clazz : interfaces) {
			//remove上级个接口和重复接口
			removeSuperAndSameInterfaces(interfaceSet, clazz);
			interfaceSet.add(clazz);
		}

		Object proxy = getProxyObject(interfaceSet, h);

		proxyCount ++;

		return proxy;
	}

	/**
	 * remove上级个接口和重复接口
	 * @param interfaceSet
	 * @param clazz
	 */
	private static void removeSuperAndSameInterfaces(Set<Class<?>> interfaceSet, Class<?> clazz) {
		if(interfaceSet.contains(clazz))
			interfaceSet.remove(clazz);
		Class<?>[] interfaces = clazz.getInterfaces();
		if(0 != interfaces.length)
			for (Class<?> super_clazz : interfaces)
				removeSuperAndSameInterfaces(interfaceSet, super_clazz);
	}

	/**
	 * 获取代理类
	 * @param interfaceSet
	 * @param h
	 * @return
	 */
	private static Object getProxyObject(Set<Class<?>> interfaceSet, InvocationHandler h) {
		Object proxy = null;
		StringBuilder sb = new StringBuilder();

		proxyHead(sb, interfaceSet);
		proxyBody(sb, interfaceSet, h);

		//源代码和class落地了，即在硬盘上创建了.java和.class文件
		proxy = generateProxyWithFile(sb.toString(), h);
		//源代码和class不落地，即全程在内存中操作，，没能实现，下面注释的部分就是，返回的代理类是null
		//generateProxyWithoutFile(sb.toString(), h);

		/*此种方式只能操作声明为public的成员
		try {
			proxy.getClass().getField("h").set(proxy, h);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} */

		return proxy;
	}

	/**
	 * 构造$Proxyx.java之head
	 * @param sb
	 * @param interfaceSet
	 */
	private static void proxyHead(StringBuilder sb, Set<Class<?>> interfaceSet) {
		sb.append("public class $Proxy" + (proxyCount) + " implements ");
		// 遍历所有的代理接口，并在代理类中实现他们
		Iterator<Class<?>> iterator = interfaceSet.iterator();
		int size = interfaceSet.size();
		for (int i = 0; i < size; i++) {
			// 对于大部分class来说，其实getName、getCanonicalNam这两个方法没有什么不同的，但是对于array或内部类等就显示出来了。
			// getName返回的是[[Ljava.lang.String之类的表现形式，而getCanonicalName返回的就是跟我们声明类似的形式。
			sb.append(iterator.next().getCanonicalName());
			if (i != size - 1)
				sb.append(", ");
		}
		sb.append("\n");
	}

	/**
	 * 构造$Proxyx.java之body
	 * @param sb
	 * @param interfaceSet
	 * @param h
	 */
	private static void proxyBody(StringBuilder sb, Set<Class<?>> interfaceSet, InvocationHandler h) {
		sb.append("{\n");
		String str = h.getClass().getCanonicalName();
		sb.append("\t").append(str).append(" h;\n");
		//构造方法
		sb.append("\t public $Proxy").append(proxyCount).append("(").append(str).append(" h)").append(" {\n");
		sb.append("\t\tthis.h = h;\n");
		sb.append("\t}\n\n");
		//接口方法
		for (Class<?> clazz : interfaceSet) {
			//拿到当前接口的所有方法
			Method[] methods = clazz.getMethods();
			//逐一实现方法
			for (Method method : methods)
			{
				//获取返回类型
				Class<?> returnType = method.getReturnType();
				sb.append("\t@Override\n");
				sb.append("\tpublic ").append(returnType.getName()).append(" ").append(method.getName()).append("(");
				Class<?>[] parameters = method.getParameterTypes();
				//该变量用来附加在形参参数名称后，用来区分参数列表中的对象，例如 ：String arg0,String arg1...
				int i = 0;
				//该字符串用来保存形参的参数名称，调用invoke方法的时候会用到这些 ，参数名称的列表
				String args = "";
				//该字符串保存了形参的字节码文件，就像Object.class这样的，用来转发调用请求时的参数类型
				String pclazz = "";
				//参数数组长度
				int length = parameters.length;
				for (Class<?> parameter : parameters) {
					sb.append(parameter.getCanonicalName()).append(" arg").append(i);
					args+="arg"+i;
					pclazz+=parameter.getCanonicalName()+".class";
					if(i != length - 1) {
						sb.append(",");
						args += ",";
						pclazz += ",";
					}
					i++;
				}
				sb.append(") {\n");

				sb.append("\t\tObject obj = null;\n");
				sb.append("\t\ttry {\n");
				sb.append("\t\t\tobj = h.invoke(");
				//public Object invoke(Object proxy, Method method, Object[] args){}
				//public Method getMethod(String name, Class<?>... parameterTypes){}
				sb.append("this, ");
				sb.append(clazz.getCanonicalName()+".class.getMethod(\""+method.getName()+"\","+(length == 0 ? "new Class<?>[]{}" : pclazz)+"), ");
				sb.append((length == 0 ? "new Object[]{}" : "new Object[]{" + args + "}"));
				sb.append(");\n");

				sb.append("\t\t} catch (Throwable t) {\n\t\t\tt.printStackTrace();\n\t\t}\n");
				sb.append("\t\treturn");

				//写入返回值，不过要注意的是基本类型，如果直接返回不强制转换为包装类型的话会出现ClassCastException
				//Object cannot be cast to primitive type
				if(returnType != void.class) {
					if(returnType == boolean.class)
						sb.append(" (Boolean)");
					else if(returnType == int.class)
						sb.append(" (Integer)");
					else if(returnType == byte.class)
						sb.append(" (Byte)");
					else if(returnType == short.class)
						sb.append(" (Short)");
					else if(returnType == long.class)
						sb.append(" (Long)");
					else if(returnType == float.class)
						sb.append(" (Float)");
					else if(returnType == double.class)
						sb.append(" (Double)");
					else if(returnType == char.class)
						sb.append(" (Character)");
					else
						sb.append(" ("+returnType.getCanonicalName()+")");
					sb.append("obj");
				}
				sb.append(";\n");
				sb.append("\t}\n");
			}
		}
		sb.append("}");
	}

	@SuppressWarnings("resource")
	private static Object generateProxyWithFile(String source, InvocationHandler h) {
		Object proxy = null;
		String classPath = Proxy.class.getResource("/").getPath();
		try {
			//将.java源文件写到磁盘
			FileWriter fw = new FileWriter(classPath + "$Proxy" + proxyCount +".java");
			fw.write(source);
			fw.flush();
			fw.close();
			//获得系统编译器
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null,null, null);
			//读入.java源文件
			Iterable<? extends JavaFileObject> fileObject = fileManager.getJavaFileObjects(classPath + "$Proxy" + proxyCount +".java");
			//编译
			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, fileObject);
			task.call();
			fileManager.close();
			//指定class路径，默认和源代码路径一致，加载class
			URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file:" + classPath)});
			Class<?> clazz = classLoader.loadClass("$Proxy" + proxyCount);
			Constructor<?> constructor = clazz.getConstructor(h.getClass());
			proxy = constructor.newInstance(h);

			//用完后删除动态生成的文件
			File javaFile = new File(classPath + "$Proxy" + proxyCount +".java");
			if (javaFile.exists()) {
				javaFile.delete();
			}
			File classFile = new File(classPath + "$Proxy" + proxyCount +".class");
			if (classFile.exists()) {
				classFile.delete();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return proxy;
	}

//	private static Object generateProxyWithoutFile(String source, InvocationHandler h) {
//		Object proxy = null;
//		try {
//			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//			JavaFileManager fileManager = new MemoryFileManager(compiler.getStandardFileManager(null, null, null));
//
//			//创建代理类的内存编译单元
//			JavaFileObject fileObject = MemoryFileManager.getJavaFileObject("$Proxy" + proxyCount, source);
//			Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(fileObject);
//
//			//为编译器创建编译任务
//			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
//			task.call();
//
//			//获得ClassLoader，加载class文件
//			ClassLoader classLoader = fileManager.getClassLoader(null);
//			Class<?> clazz = classLoader.loadClass("$Proxy" + proxyCount);
//
//			//获得实例
//			Constructor<?> constructor = clazz.getConstructor(h.getClass());
//			proxy = constructor.newInstance(h);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		return proxy;
//	}
//
//	static class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
//	    private Map<String, byte[]> classes = new HashMap<String, byte[]>();;
//
//	    public MemoryFileManager(JavaFileManager fileManager) {
//	        super(fileManager);
//	    }
//
//	    @Override
//	    public JavaFileObject getJavaFileForOutput(Location location, String name,Kind kind,FileObject originatingSource)throws UnsupportedOperationException {
//	    	class JavaClassInArray extends SimpleJavaFileObject {
//	            private String name;
//
//	            JavaClassInArray(String name) {
//	                super(URI.create("mfm:///" + name.replace('.','/') + Kind.CLASS.extension),
//	                      Kind.CLASS);
//	                this.name = name;
//	            }
//
//	            public OutputStream openOutputStream() {
//	                return new FilterOutputStream(new ByteArrayOutputStream()) {
//	                    public void close() throws IOException {
//	                        out.close();
//	                        ByteArrayOutputStream bos = (ByteArrayOutputStream)out;
//	                        classes.put(name, bos.toByteArray());
//	                    }
//	                };
//	            }
//	        }
//
//	    	if (originatingSource instanceof JavaSourceFromString) {
//	            return new JavaClassInArray(name);
//	        } else {
//	            throw new UnsupportedOperationException();
//	        }
//	    }
//
//	    @Override
//		public ClassLoader getClassLoader(Location location) {
//			return new ClassLoader() {
//				@Override
//				public Class<?> loadClass(String name) throws ClassNotFoundException {
//					try {
//						return super.loadClass(name);
//					} catch (ClassNotFoundException e) {
//						byte[] classData = classes.get(name);
//						return defineClass(name, classData, 0, classData.length);
//					}
//				}
//			};
//		}
//
//	    public static JavaFileObject getJavaFileObject(String name, String code) {
//	    	return new JavaSourceFromString(name, code);
//	    }
//
//	    static class JavaSourceFromString extends SimpleJavaFileObject {
//	        final String code;
//
//	        JavaSourceFromString(String name, String code) {
//	            super(URI.create("mfm:///" + name.replace('.','/') + Kind.SOURCE.extension),
//	                  Kind.SOURCE);
//	            this.code = code;
//	        }
//
//	        @Override
//	        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
//	            return code;
//	        }
//	    }
//	}
}