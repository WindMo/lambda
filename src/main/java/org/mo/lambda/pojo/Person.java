package org.mo.lambda.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WindShadow
 * @version 2022-01-14.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

    private String name;
    private Integer age;
    private Gender gender;
    private String job;
    private String city;
    private Long salary;

    @Override
    public String toString() {
        return "<" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", job='" + job + '\'' +
                ", city='" + city + '\'' +
                ", salary=" + salary +
                '>';
    }
}
