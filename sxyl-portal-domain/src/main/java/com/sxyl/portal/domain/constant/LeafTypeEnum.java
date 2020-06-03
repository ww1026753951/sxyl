package com.sxyl.portal.domain.constant;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2020/4/23
 */
public enum LeafTypeEnum {


    //左侧节点
    LEFT(1,"左侧节点"),
    //右侧节点
    RIGHT(2,"累加");

    private final int type;
    private final String desc;
    private LeafTypeEnum(int type , String desc) {
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
