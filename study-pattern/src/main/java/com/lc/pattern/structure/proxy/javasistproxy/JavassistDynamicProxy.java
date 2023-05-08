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

            // 添加构造方法
            CtConstructor constructor = new CtConstructor(new CtClass[]{}, proxyClass);
            constructor.setBody("{}");
            proxyClass.addConstructor(constructor);

            // 添加代理方法
            CtMethod[] declaredMethods = interfaceClass.getDeclaredMethods();
            for (CtMethod declaredMethod : declaredMethods) {
                CtMethod method = new CtMethod(declaredMethod.getReturnType(), declaredMethod.getName(), declaredMethod.getParameterTypes(), proxyClass);
                // ！！！这里实际的实现类是写死的，是不是也可以传进来，这样既动态，又高效（调用时）！！！
                method.setBody("{System.out.println(\"添加用户前的代理操作\"); com.lc.pattern.structure.proxy.javasistproxy.UserService target = new com.lc.pattern.structure.proxy.javasistproxy.UserServiceImpl(); target." + declaredMethod.getName() +"($1, $2); System.out.println(\"添加用户后的代理操作\");}");
                proxyClass.addMethod(method);
            }

            // 使用代理类生成代理对象
            Class<?> clazz = proxyClass.toClass();
            UserService userService = (UserService) clazz.newInstance();

            // 使用代理对象调用被代理接口方法
            userService.addUser("user1", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
