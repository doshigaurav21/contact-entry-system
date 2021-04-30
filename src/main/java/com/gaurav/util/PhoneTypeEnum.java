package com.gaurav.util;

public enum PhoneTypeEnum {

    HOME("home"),
    WORK("work"),
    MOBILE("mobile");

    PhoneTypeEnum(String phoneType) {
        this.value = phoneType;
    }

    private String value;

    public String toValue() {
        return value;
    }

}
