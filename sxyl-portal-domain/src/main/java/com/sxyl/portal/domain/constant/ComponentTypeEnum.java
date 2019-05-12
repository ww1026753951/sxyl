package com.sxyl.portal.domain.constant;

public enum ComponentTypeEnum {

    //分组的枚举
    GROUP(1,"group"),
    //文字
    TEXT(2,"text"),
    //矩形
    RECT(3,"rect"),
    //圆形的枚举
    CIRCLE(4,"circle"),
    //线的枚举
    LINE(5,"line"),
    //文字的附加样式
    TSPAN(6,"tspan"),
    //文字的附加样式
    FORMULA(7,"formula"),
    //文字的附加样式
    RECT_AND_TEXT(100,"rect");

    private final int type;
    private final String desc;
    private ComponentTypeEnum(int type , String desc) {
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
