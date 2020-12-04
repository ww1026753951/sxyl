package com.sxyl.portal.domain.constant;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2020/11/24
 */
public enum  TopEnum {


    TOP(1,"顶部"),
    BOTTOM(2,"底部");

    private final int type;
    private final String desc;
    TopEnum(int type , String desc) {
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
