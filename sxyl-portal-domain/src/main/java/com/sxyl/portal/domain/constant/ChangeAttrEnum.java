package com.sxyl.portal.domain.constant;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2019/8/20
 */
public enum  ChangeAttrEnum {


    //替换
    REPLACE(1,"替换"),
    //文字
    ADD(2,"累加");

    private final int type;
    private final String desc;
    private ChangeAttrEnum(int type , String desc) {
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
