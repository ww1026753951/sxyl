package com.sxyl.portal.domain.constant;

/*******
 * 动画的枚举类
 */
public enum AnimationEnum {


    //复制
    COPY(1,"复制"),
    //移动
    MOVE(2,"移动"),
    //变色
    CHANGE_COLOR(3,"变色");

    private final int type;
    private final String desc;
    AnimationEnum(int type , String desc) {
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
