package org.mo.lambda.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WindShadow
 * @version 2022-01-16.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonView {

    private String name;
    private Integer age;
    private Gender gender;
    private Long salary;

    @Override
    public String toString() {
        return "<" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", salary=" + salary +
                '>';
    }
}
