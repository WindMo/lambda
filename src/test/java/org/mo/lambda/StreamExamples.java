package org.mo.lambda;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mo.lambda.pojo.Gender;
import org.mo.lambda.pojo.Person;
import org.mo.lambda.pojo.PersonView;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class StreamExamples {

    private final List<Person> people = new ArrayList<>();

    @BeforeEach
    void initData() {
        // 添加9 个人
        people.add(new Person("A-developer",23, Gender.MAN,"开发","北京",12 * 1000L));
        people.add(new Person("B-tester",24, Gender.MAN,"测试","上海",15 * 1000L));
        people.add(new Person("C-ops",29, Gender.WOMAN,"运维","北京",21 * 1000L));
        people.add(new Person("D-tester",27, Gender.WOMAN,"测试","南京",20 * 1000L));
        people.add(new Person("E-ops",28, Gender.MAN,"运维","南京",19 * 1000L));
        people.add(new Person("F-developer",25, Gender.WOMAN,"开发","上海",13 * 1000L));
        people.add(new Person("G-tester",25, Gender.WOMAN,"测试","北京",12 * 1000L));
        people.add(new Person("H-developer",22, Gender.MAN,"开发","南京",14 * 1000L));
        people.add(new Person("I-ops",30, Gender.MAN,"运维","上海",22 * 1000L));
    }

    // 筛选
    @Test
    void filterExample() {

        // 筛选出年龄大于等于 25 岁的
        List<Person> results = people
                .stream()
                .filter(person -> person.getAge() >= 25)
                .collect(Collectors.toList());
        printCollection(results);

        Person boss = new Person("BOSS",48, Gender.MAN,"老板","深圳",0L);
        Person boss2 = new Person("BOSS",48, Gender.MAN,"老板","深圳",0L);
        people.add(boss);
        people.add(boss2);

        // 去重 依据 Object.equals() 方法
        List<Person> results2 = people
                .stream()
                .distinct()
                .collect(Collectors.toList());
        printCollection(results2);
    }

    // 切片
    @Test
    void sectionExample() {

        // 取前三个
        List<Person> results = people
                .stream()
                .limit(3)
                .collect(Collectors.toList());
        printCollection(results);

        // 跳过前四个
        List<Person> results2 = people
                .stream()
                .skip(4)
                .collect(Collectors.toList());
        printCollection(results2);

        // 跳过前3个，再取2个
        List<Person> results3 = people
                .stream()
                .skip(3)
                .limit(2)
                .collect(Collectors.toList());
        printCollection(results3);
    }

    // 映射
    @Test
    void mappingExample() {

        // 转换为view
        List<PersonView> results = people
                .stream()
                .map(person -> {

                    PersonView view = new PersonView();
                    view.setName(person.getName());
                    view.setAge(person.getAge());
                    view.setGender(person.getGender());
                    view.setSalary(person.getSalary());
                    return view;
                })
                .collect(Collectors.toList());
        printCollection(results);

        // 只看名字
        List<String> nameList = people
                .stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        printCollection(nameList);
    }

    // 窥探
    @Test
    void peekExample() {

        List<Person> results = people.stream()
                .peek(person -> person.setCity("北京"))
                .collect(Collectors.toList());
        printCollection(results);
    }

    // 匹配
    @Test
    void matchExample() {

        // 至少存在一个开发？
        boolean hasDev = people.
                stream()
                .anyMatch(person -> "开发".equals(person.getJob()));
        System.out.println("hasDev: " + hasDev);

        // 全都是运维？
        boolean isAllOps = people.
                stream()
                .allMatch(person -> "运维".equals(person.getJob()));
        System.out.println("isAllOps: " + isAllOps);

        // 没有一个是老板？
        boolean noBoss = people.
                stream()
                .noneMatch(person -> "老板".equals(person.getJob()));
        System.out.println("noBoss: " + noBoss);
    }

    // 查找
    @Test
    void findExample() {

        // 返回第一个
        Optional<Person> first = people
                .stream()
                .findFirst();
        first.ifPresent(person -> System.out.println("first: " + person));

        // 随机返回一个
        Optional<Person> randomOne = people
                .stream()
                .findAny();
        randomOne.ifPresent(person -> System.out.println("randomOne: " + person));
        // FIXME  见下文并行模式
        for (int i = 0; i < 100; i++) {

            Optional<Person> randomOneInFor = people
                    .stream()
                    .findAny();
            randomOneInFor.ifPresent(person -> System.out.println("randomOneInFor: " + person));
        }
    }

    // 排序
    @Test
    void sortExample() {

        List<Person> results = people
                .stream()
                .sorted((p1,p2) -> p1.getAge() - p2.getAge())
//                .sorted(Comparator.comparingInt(Person::getAge))
                .collect(Collectors.toList());
        printCollection(results);
    }

    // 最值
    @Test
    void maxminExample() {

        // 最大年龄的人
        Optional<Person> oldestPersonOpt = people
                .stream()
                .max(Comparator.comparingInt(Person::getAge));
        oldestPersonOpt.ifPresent(oldestPerson -> System.out.println("oldestPerson: " + oldestPerson));

        // 最小年龄的人
        Optional<Person> youngestPersonOpt = people
                .stream()
                .min(Comparator.comparingInt(Person::getAge));
        youngestPersonOpt.ifPresent(youngestPerson -> System.out.println("youngestPerson: " + youngestPerson));
    }

    // 计算总数
    @Test
    void countExample() {

        // 计算男生总数
        long size = people
                .stream()
                .filter(person -> Gender.MAN.equals(person.getGender()))
                .count();
        System.out.println("size: " + size);
    }

    // 归约
    @Test
    void reduceExample() {

        // 统计全部人的工资
        Long salarySum = people
                .stream()
                .map(Person::getSalary)
                .reduce(0L,(s1,s2) -> s1 + s2);
//                .reduce(0L, Long::sum);
        System.out.println("salarySum: " + salarySum);

        Long salarySum2 = people
                .stream()
                .reduce(0L,(aLong, person) -> aLong + person.getSalary(),(s1,s2) -> s1 + s2);
//                .reduce(0L,(aLong, person) -> aLong + person.getSalary(), Long::sum);

        System.out.println("salarySum2: " + salarySum2);

        Optional<Long> salarySumOpt = people
                .stream()
                .map(Person::getSalary)
                .reduce((s1,s2) -> s1 + s2);
//                .reduce(Long::sum);
        salarySumOpt.ifPresent(sum -> System.out.println("sum: " + sum));

    }


    // 均值
    @Test
    void averageExample() {

        // 求平均工资
        OptionalDouble avgSalaryOpt = people
                .stream()
                .mapToLong(Person::getSalary) // 自动拆箱，潜在NPE
                .average();
        avgSalaryOpt.ifPresent(System.out::println);
    }

    // 求和
    @Test
    void sumExample() {

        // 求总工资
        long sumSalary = people
                .stream()
                .mapToLong(Person::getSalary)
                .sum();
        System.out.println(sumSalary);
    }


    // 返回统计基本属性
    @Test
    void summaryExample() {

        LongSummaryStatistics stats = people
                .stream()
                .mapToLong(Person::getSalary)
                .summaryStatistics();
        System.out.println("总数：" + stats.getCount());
        System.out.println("最小值：" + stats.getMin());
        System.out.println("最大值：" + stats.getMax());
        System.out.println("平均值：" + stats.getAverage());
        System.out.println("总和：" + stats.getSum());
    }

    //----------------------------------
    // 中间操作篇，每次中间操作都会产生新的流
    // 终端操作，关闭流 - 收集器
    //----------------------------------

    // 收集 - 总数
    @Test
    void countingExample() {

        // 计算男生总数
        Long size = people
                .stream()
                .filter(person -> Gender.MAN.equals(person.getGender()))
                .collect(Collectors.counting()); // FIXME 可优化为 count() 方法
        System.out.println("size: " + size);
    }

    // 收集 - 最值
    @Test
    void maxminByCollectorExample() {

        Optional<Person> oldestPersonOpt = people
                .stream()
                .collect(Collectors.maxBy(Comparator.comparing(Person::getAge)));
//                .max(Comparator.comparing(Person::getAge));

        oldestPersonOpt.ifPresent(System.out::println);

        Optional<Person> youngestPersonOpt = people
                .stream()
                .collect(Collectors.minBy(Comparator.comparing(Person::getAge)));
//                .min(Comparator.comparing(Person::getAge));

        youngestPersonOpt.ifPresent(System.out::println);
    }

    // 字符拼接
    @Test
    void joinExample() {

        String nameString = people
                .stream()
                .map(Person::getName)
                .collect(Collectors.joining());
        System.out.println("nameString: " + nameString);

        String nameString2 = people
                .stream()
                .map(Person::getName)
                .collect(Collectors.joining(","));
        System.out.println("nameString2: " + nameString2);

        String nameString3 = people
                .stream()
                .map(Person::getName)
                .collect(Collectors.joining(",",">>>","<<<"));
        System.out.println("nameString3: " + nameString3);
    }


    // 收集 - 分组
    @Test
    void groupingExample() {

        // 依据性别分组
        Map<Gender,List<Person>> result = people
                .stream()
                .collect(Collectors.groupingBy(Person::getGender));
        printMap(result);

        System.out.println("-------------------------");

        // 多级分组，依据性别分组，再依据岗位分组
        Map<Gender,Map<String,List<Person>>> result2 = people
                .stream()
                .collect(Collectors.groupingBy(Person::getGender,Collectors.groupingBy(Person::getJob)));
        printMap(result2);

        System.out.println("-------------------------");

        // 跟计算结果分组
        Map<String,List<Person>> result3 = people
                .stream()
                .collect(Collectors.groupingBy(person -> person.getSalary() <= 14 * 1000L ? "加钱" : "不加钱"));
        printMap(result3);

        System.out.println("-------------------------");

        // 依据性别分组，统计每组数量
        Map<Gender,Long> result4 = people
                .stream()
                .collect(Collectors.groupingBy(Person::getGender,Collectors.counting()));
        printMap(result4);
    }

    // 并行模式
    @Test
    void parallelExample() {

        /*
            并行:多个任务在同一时间点发生，由多个线程执行，底层使用 JDK fork/join framework
            效率：
                    简单类型    Steam并行迭代 > 外部迭代  >  Steam串行迭代
                    复杂类型    Steam并行迭代 > Steam串行迭代  > 外部迭代
         */

        // 随机返回一个
        for (int i = 0; i < 100; i++) {

            Optional<Person> randomOneInFor = people
                    .stream()
                    .parallel()
                    .findAny();
            randomOneInFor.ifPresent(person -> System.out.println("randomOneInFor: " + person));
        }

//        List<String> stringList = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//
//            stringList.add("string-" + i);
//        }
//        for (int i = 0; i < 100; i++) {
//
//            Optional<String> randomStringInFor = stringList
//                    .stream()
//                    .parallel()
//                    .findAny();
//            randomStringInFor.ifPresent(str -> System.out.println("randomOneInFor: " + str));
//        }
    }

    //---------print methods----------

    private static <K,V> void printMap(Map<K,V> map) {

        printMap(0,map);
    }

    private static <K,V> void printMap(int indent, Map<K,V> map) {

        String prefix = continuous("\t",indent);
        map.forEach((k,v) -> {

            if (v instanceof Collection) {
                System.out.println(prefix + k);
                printCollection(indent + 1,(Collection)v);
            } else if (v instanceof Map){

                System.out.println(prefix + k);
                printMap(indent + 1,(Map)v);
            } else {
                System.out.println(prefix + k + "=" + v);
            }
        });
    }

    private static <T> void printCollection(Collection<T> list) {

        printCollection(0,list);
    }

    private static <T> void printCollection(int indent, Collection<T> list) {

        String prefix = continuous("\t",indent);
        list.forEach(el -> System.out.println(prefix + el));
    }

    private static String continuous(String str, int count) {

        return Stream.of(new Object[count]).map(o -> str).collect(Collectors.joining(""));
    }
}
