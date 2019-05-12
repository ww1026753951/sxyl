package com.sxyl.portal.domain.constant;

public enum  ComponentCompositeEnum {


    //NONE
    NONE(0,"自定义,不做 x,y轴的相加,后台处理"),
    //水平的枚举 , x的值不变,y值会增加
    HORIZONTAL(1,"水平的枚举"),
    //垂直的枚举
    VERTICAL(2,"垂直的枚举");

    private final int type;
    private final String desc;
    ComponentCompositeEnum(int type , String desc) {
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
