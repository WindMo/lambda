package org.mo.lambda;

import org.mo.lambda.service.DateTimeComponent;
import org.mo.lambda.service.SomeService;

/**
 * @author WindShadow
 * @version 2022-01-15.
 */

public class Java8_3_LambdaMethodReference {

    // lambda表达式中的方法引用

    public static void main(String[] args) {

        // 方法引用
        sayBySomeService(Java8_3_LambdaMethodReference::printJava);

        DateTimeComponent component = new DateTimeComponent();
        sayBySomeService(component::outDateTime);

        // 传入表达式
        sayBySomeService(() -> System.out.println("你好"));
    }

    static void printJava() {

        System.out.println("Java");
    }

    static void sayBySomeService(SomeService service) {

        service.say();
    }
}
