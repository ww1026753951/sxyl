package com.sxyl.portal.domain.constant;

public enum  ComponentCompositeEnum {


    //水平的枚举
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
