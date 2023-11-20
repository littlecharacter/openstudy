package com.lc.pattern.structure.proxy.javasistproxy;

import javassist.*;

/**
 * @author gujixian
 * @since 2023/5/9
 */
public class JavassistDynamicProxy {
    public static void main(String[] args) {

        // 创建ClassPool对象
        ClassPool pool = ClassPool.getDefault();

        // 创建需要代理的接口及实现类
        try {
            // 创建被代理的接口
            CtClass interfaceClass = pool.get("com.lc.pattern.structure.proxy.javasistproxy.UserService");

            // 创建代理类
            CtClass proxyClass = pool.makeClass("com.lc.pattern.structure.proxy.javasistproxy.UserServiceProxy");

            // 代理类实现接口
            proxyClass.addInterface(interfaceClass);

            // 添加实例变量
            CtField field = new CtField(interfaceClass, "target", proxyClass);
            field.setModifiers(Modifier.PRIVATE);
            proxyClass.addField(field);

            // 添加构造方法
            CtConstructor constructor = new CtConstructor(new CtClass[]{interfaceClass}, proxyClass);
            constructor.setBody("{this.target = $1;}");
            proxyClass.addConstructor(constructor);

            // 添加代理方法
            CtMethod[] declaredMethods = interfaceClass.getDeclaredMethods();
            for (CtMethod declaredMethod : declaredMethods) {
                CtMethod method = new CtMethod(declaredMethod.getReturnType(), declaredMethod.getName(), declaredMethod.getParameterTypes(), proxyClass);
                StringBuilder paramType = new StringBuilder();
                CtClass[] parameterTypes = method.getParameterTypes();
                for (int i = 1; i <= parameterTypes.length; i++) {
                    if (i == parameterTypes.length) {
                        paramType.append("$").append(i);
                    } else {
                        paramType.append("$").append(i).append(",");
                    }
                }
                // ！！！这里实际的实现类是写死的，是不是也可以传进来，这样既动态，又高效（调用时）！！！
                StringBuilder body = new StringBuilder();
                body.append("{");
                body.append("System.out.println(\"添加用户前的代理操作\");");
                String returnType = method.getReturnType().getName();
                if ("void".equals(returnType)) {
                    body.append("target.").append(declaredMethod.getName()).append("(").append(paramType).append(");");
                    body.append("System.out.println(\"添加用户后的代理操作\");");
                } else {
                    body.append(returnType).append(" result = target.").append(declaredMethod.getName()).append("(").append(paramType).append(");");
                    body.append("System.out.println(\"添加用户后的代理操作\");");
                    body.append("return result;");
                }
                body.append("}");
                method.setBody(body.toString());
                proxyClass.addMethod(method);
            }

            // 使用代理类生成代理对象
            Class<?> clazz = proxyClass.toClass();
            // UserService userService = (UserService) clazz.getConstructor(UserService.class).newInstance(new UserServiceImpl());
            UserService userService = (UserService) clazz.getConstructor(Class.forName("com.lc.pattern.structure.proxy.javasistproxy.UserService")).newInstance(new UserServiceImpl());

            // 使用代理对象调用被代理接口方法
            userService.addUser("user1", "123456");
            System.out.println("--------------------------");
            System.out.println(userService.getUser("user1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
