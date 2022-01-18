package org.mo.lambda;

import org.junit.jupiter.api.Test;
import org.mo.lambda.pojo.Gender;
import org.mo.lambda.pojo.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 并行流
 * @author WindShadow
 * @version 2022-01-18.
 */

public class ParallelSteamExamples {

    static List<Person> getPeople() {

        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person("xxx-" + i,23, Gender.MAN,"开发","北京",100 * 1000L));
        }
        return people;
    }

    static long runTimer(Runnable runnable) {

        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        return end - start;
    }

    // 并行模式 随机返回
    @Test
    void parallelExample() {

        List<Person> people = getPeople();

        // 随机返回一个
        for (int i = 0; i < 100; i++) {

            Optional<Person> randomOneInFor = people
                    .stream()
                    .parallel()
                    .findAny();
            randomOneInFor.ifPresent(person -> System.out.println("randomOneInFor: " + person));
        }
    }

    // 并行模式 简单类型
    @Test
    void parallelWhenSimpleObjectExample() {

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {

            stringList.add("string-" + i);
        }

        Consumer<Stream<String>> streamConsumer = stream ->
                stream.peek(str -> sleep(TimeUnit.MILLISECONDS,1L)).collect(Collectors.toList());

        System.out.println("串行流用时：" + runTimer(() -> streamConsumer.accept(stringList.stream())));
        System.out.println("并行流用时：" + runTimer(() -> streamConsumer.accept(stringList.parallelStream())));
    }



    // 并行模式 复杂类型
    @Test
    void parallelWhenComplexObjectExample() {

        List<Person> people = getPeople();

        Consumer<Stream<Person>> streamConsumer = stream ->
                stream.peek(person -> sleep(TimeUnit.MILLISECONDS,10L)).collect(Collectors.toList());

        System.out.println("串行流用时：" + runTimer(() -> streamConsumer.accept(people.stream())));
        System.out.println("并行流用时：" + runTimer(() -> streamConsumer.accept(people.parallelStream())));
    }

    static void sleep(TimeUnit timeUnit,long timeout) {

        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

        /*
            并行:多个任务在同一时间点发生，由多个线程执行，底层使用 JDK fork/join framework
            效率：
                    简单类型    Steam并行迭代 > 外部迭代  >  Steam串行迭代
                    复杂类型    Steam并行迭代 > Steam串行迭代  > 外部迭代
         */
}
