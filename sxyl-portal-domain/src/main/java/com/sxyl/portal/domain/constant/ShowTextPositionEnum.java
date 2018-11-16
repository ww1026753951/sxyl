package com.sxyl.portal.domain.constant;

/****
 * 文字展示位置的枚举
 */
public enum  ShowTextPositionEnum {

    //中间位置
    MIDDLE(1,"中间位置"),
    //在范围内的顶部
    TOP_INSIDE(2,"顶部-在范围内"),
    //在范围外的顶部
    TOP_OUTSIDE(3,"顶部-在范围外");

    private final int type;
    private final String desc;
    private ShowTextPositionEnum(int type , String desc) {
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
