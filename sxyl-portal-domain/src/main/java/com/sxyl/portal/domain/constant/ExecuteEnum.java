package com.sxyl.portal.domain.constant;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2019/8/14
 */
public enum ExecuteEnum {


    INSERT(1,"新增"),
    DELETE(2,"删除");

    private final int type;
    private final String desc;
    ExecuteEnum(int type , String desc) {
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
