package com.sxyl.portal.domain.constant;

/****
 * 文字展示位置的枚举
 */
public enum  ShowTextPositionEnum {

    //起始位置
    START(1,"text-anchor: start;","在起始的位置"),
    //中间位置
    MIDDLE(2,"text-anchor: middle;","中间位置"),
    //结束位置
    END(3,"text-anchor: end;","在结束的位置");

    private final int type;


    private final String code ;

    private final String desc;
    private ShowTextPositionEnum(int type , String code , String desc) {
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
