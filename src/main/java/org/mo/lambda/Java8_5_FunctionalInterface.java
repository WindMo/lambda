package org.mo.lambda;

import java.util.Objects;
import java.util.function.*;

/**
 * @author WindShadow
 * @version 2022-01-15.
 */

public class Java8_5_FunctionalInterface {

    // 常用的 jdk 提供的函数式接口

    public static void main(String[] args) {

        // 无参无返回 运行就完事了 >>> 执行者
        Runnable runnable = () -> System.out.println("runnable");
        Runnable runnableImpl = new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable");
            }
        };
        runnable.run();

        // 有参无返回 消费掉一个参数 >>> 消费者
        Consumer<String> consumer = str -> {System.out.println(str);};
        Consumer<String> consumer2 = System.out::println; // 优雅写法
        Consumer<String> consumerImpl = new Consumer<String>() {
            @Override
            public void accept(String str) {
                System.out.println(str);
            }
        };
        consumer.accept("Hello World!");


        // 无参有返回 返回（提供）一个对象  >>> 提供者
        Supplier<String> supplier = () -> {return "abc";};
        Supplier<String> supplier2 = () -> "abc"; // 优雅写法
        Supplier<String> supplierImpl = new Supplier<String>() {
            @Override
            public String get() {
                return "abc";
            }
        };
        String string = supplier.get();


        // 有参有返回 消费+提供=转换  >>> 转换器
        Function<Integer,String> function = integer -> {return String.valueOf(integer);};
        Function<Integer,String> function2 = String::valueOf; // 优雅写法
        Function<Integer,String> functionImpl = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.valueOf(integer);
            }
        };
        String intStr = function.apply(100);


        // 特殊的转换：判断（测试/检验/校验） >>> 校验器
        // boolean
        Predicate<String> isNull = str -> str == null;
        Predicate<String> isNull2 = Objects::isNull; // 优雅写法
        Predicate<String> isNullImpl = new Predicate<String>() {
            @Override
            public boolean test(String str) {
                return str == null;
            }
        };
        String str = null;
        boolean testResult = isNull.test(str);

        //------------------------------------------------------
        // 基本类型的函数式接口等其它接口见 java.util.function
        //------------------------------------------------------
    }
}
