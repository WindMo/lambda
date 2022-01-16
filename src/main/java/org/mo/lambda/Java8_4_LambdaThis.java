package org.mo.lambda;

import org.mo.lambda.service.SomeServiceFactory;
import org.mo.lambda.service.SomeService;

/**
 * @author WindShadow
 * @version 2022-01-15.
 */

public class Java8_4_LambdaThis {

    // lambda表达式中的 this

    public static void main(String[] args) {

        SomeServiceFactory someServiceFactory = new SomeServiceFactory();

        // lambda 表达式内的 this 引用（指针）
        SomeService lambdaSomeService = someServiceFactory.getLambdaSomeService();
        lambdaSomeService.say();

        System.out.println("----------------------");

        // 匿名内部类实现接口时的 this 引用（指针）
        SomeService anonymousSomeService = someServiceFactory.getAnonymousSomeService();
        anonymousSomeService.say();
    }
}
