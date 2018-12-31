package com.sxyl.portal.domain.constant.graph;

public enum  BaselineShiftEnum {


    //下方
    LINE_SUB(1,"baseline-shift: sub;","下标"),
    //上方
    LINE_SUPER(2,"baseline-shift: super;","上标");

    private final int type;


    private final String code ;

    private final String desc;
    private BaselineShiftEnum(int type , String code , String desc) {
        this.type = type;
        this.code = code ;
        this.desc = desc;
    }


    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }
}
