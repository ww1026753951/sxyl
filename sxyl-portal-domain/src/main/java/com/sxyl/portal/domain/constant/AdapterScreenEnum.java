package com.sxyl.portal.domain.constant;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2020/12/23
 */
public enum  AdapterScreenEnum {


    //纵向
    VERTICAL(2,"竖屏"),
    //横向
    HORIZONTAL(2,"横屏");

    private final int code;
    private final String desc;

    private AdapterScreenEnum(int code , String desc) {
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
