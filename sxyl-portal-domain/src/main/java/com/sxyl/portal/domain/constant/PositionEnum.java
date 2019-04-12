package com.sxyl.portal.domain.constant;

public enum PositionEnum {

    /***
     * 绝对位置
     */
    ABSOLUTE(0,"绝对位置"),
    /***
     * 继承元素的宽和高
     */
    EXTEND(1,"继承元素的宽和高")
    ,
    /***
     * 继承元素的宽和高
     */
    EXTEND_PLUS(2,"继承元素的宽和高再加上当前元素的宽和高")
    ;

    private final int code;
    private final String desc;
    PositionEnum(int code , String desc) {
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
