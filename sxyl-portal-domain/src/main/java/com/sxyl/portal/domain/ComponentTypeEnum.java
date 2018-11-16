package com.sxyl.portal.domain;

public enum  ComponentType {

    //分组的枚举
    GROUP(1,"group"),
    //文字
    TEXT(2,"text"),
    //矩形
    RECT(3,"rect"),
    //圆形的枚举
    CIRCLE(4,"circle");

    private final int type;
    private final String desc;
    private ComponentType(int type ,String desc) {
        this.type = type;
        this.desc = desc;
    }


    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
