package org.mo.lambda;

import org.mo.lambda.service.SomeService;

/**
 * @author WindShadow
 * @version 2022-01-15.
 */

public class Java8_1_LambdaFeature {

    // 初识lambda表达式

    public static void main(String[] args) {

//        System.setProperty("jdk.internal.lambda.dumpProxyClasses","C:\\Users\\Administrator\\Desktop\\");

        // 调用接口中的静态方法
        SomeService.sayHelloWorld();

        // 匿名内部类实现接口
        SomeService anonymousImpl = new SomeService() {
            @Override
            public void say() {

                System.out.println("Hello");
            }
        };
        anonymousImpl.say();

        // lambda表达式实现接口
        SomeService lambdaImpl = () -> System.out.println("你好");
        lambdaImpl.say();

        // 匿名内部类对象的Class
        System.out.println(anonymousImpl.getClass());
        // lambda表达式对象的Class
        System.out.println(lambdaImpl.getClass());
    }
}
