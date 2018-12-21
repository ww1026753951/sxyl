package com.sxyl.portal.domain.constant;

public enum  FormulaTypeEnum {



    //文本类型的公式
    TEXT(1,"text"),
    //MATH_JAX
    MATH_JAX(2,"MATH_JAX类型的公式");

    private final int type;
    private final String desc;
    FormulaTypeEnum(int type , String desc) {
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
