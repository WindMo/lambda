package org.mo.lambda.pojo;

import lombok.Getter;

/**
 * @author WindShadow
 * @version 2022-01-14.
 */

public enum Gender {

    MAN(1),WOMAN(2);

    @Getter
    private final int code;

    Gender(int code) {
        this.code = code;
    }
}
