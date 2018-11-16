package com.sxyl.portal.domain.constant;

public enum  LinePositionEnum {

    //圆心
    CIRCLE_CENTER(1,"x,y和圆心相同"),
    //半径
    RADIUS(2,"距离扣减半径的值"),
    //半径的价值
    TRIANGLE(3,"通过半径求三角函数的边,会与圆中心点重叠")
    ;

    private final int code;
    private final String desc;
    LinePositionEnum(int code , String desc) {
        this.code = code;
        this.desc = desc;
    }


    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
