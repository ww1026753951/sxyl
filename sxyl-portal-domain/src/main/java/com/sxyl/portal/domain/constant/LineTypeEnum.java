package com.sxyl.portal.domain.constant;

public enum  LineTypeEnum {
    //绝对起始位置
    POSITION(1,"x1,y1,x2,y2使用绝对位置"),
    //元素对元素
    P_2_P(2,"元素对元素的位置")
    ;

    private final int code;
    private final String desc;
    LineTypeEnum(int code , String desc) {
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
