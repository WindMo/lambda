package org.mo.lambda.service;

/**
 * @author WindShadow
 * @version 2022-01-15.
 */

@FunctionalInterface
public interface SomeService {

    void say();

//    String getSomething();

//    default void sayWorld() {
//
//        System.out.println("World");
//    }

    static void sayHelloWorld() {

        System.out.println("Hello World!");
    }
}
