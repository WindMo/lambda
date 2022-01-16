package org.mo.lambda;

import org.mo.lambda.service.SomeService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author WindShadow
 * @version 2022-01-15.
 */

public class Java8_2_LambdaValidate {

    // lambda表达式内使用的外部变量必须是final的

    public static void main(String[] args) {

        String str = "你好";
        int n = 100;
        List<String> list = new ArrayList<>();
        list.add("张三");
        // 传入lambda表达式
        sayBySomeService(() -> {

            System.out.println(str);
            System.out.println(n);
            System.out.println(list);

        });

        preSayBySomeService(list);

//        str = "";
//        list = new LinkedList<>();

    }

    static void preSayBySomeService(List<String> list) {

        sayBySomeService(() -> System.out.println(list));
    }

    static void sayBySomeService(SomeService service) {

        // 创建一个野线程在2s后执行 service.say();
        new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            service.say();
        }).start();
    }
}
