package com.kuda.app.exception;

import lombok.Getter;

@Getter
public enum Resources {

    UTIL("util", 100l, 500l),
    USER("user", 600l, 900l),
    FROM_USER("from.user", 1000l, 1200l),
    DESCRIPTION("description", 1300l, 1500l),
    SETTINGS("settings", 1600l, 1800l),
    ORGANIZATION("organization", 1900l, 2300l),
    ORGANIZATION_CREDENTIAL("organization.credential", 2400l, 2600l),
    USERNAME_PASSWORD("username.password", 2700l, 2900l),
    TOKEN("token", 3000l, 3500l),
    KUDA("kuda", 4000l, 4500l),

    ;

    private Resources(String name, Long fromCode, Long toCode) {
        this.name = name;
        this.fromCode = fromCode;
        this.toCode = toCode;
    }

    private String name;
    private Long fromCode;
    private Long toCode;
}
