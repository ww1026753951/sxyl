package com.sxyl.portal.domain.constant;

/*******
 * 动画的枚举类
 */
public enum AnimationEnum {


    //总数量
    TOTAL(0,"总数量"),
    //复制
    COPY(1,"复制"),
    //移动
    MOVE(2,"移动"),
    //变色
    CHANGE_COLOR(3,"变色"),

    //销毁
    DESTROY(4,"销毁"),

    //交换内容
    CHANGE_CONTENT(10,"交换内容"),

    //乘法
    MULTIPLY(51,"乘法"),

    //加法
    SUM(52,"加法"),


    //sigmoid
    SIGMOID(61,"sigmoid"),
    SQUARED_ERROR(62,"平方差"),

    //附加函数
    COMPUTE_OUT_WEIGHT(71,"更新输出层权重"),
    COMPUTE_HIDDEN_WEIGHT(72,"更新隐藏层权重"),



    //公式------------------------
    //公式复制
    FORMULA_COPY(111,"复制")


    ;

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
