package com.sxyl.portal.domain.constant;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2020/11/26
 */
public enum  LeftEnum {



    LEFT(1,"左侧"),
    RIGHT(2,"右侧");

    private final int type;
    private final String desc;
    LeftEnum(int type , String desc) {
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
