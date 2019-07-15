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
    //交换对象
    SWAP(3,"交换"),

    //销毁
    DESTROY(4,"销毁"),
    //移动
    MOVE_FORMULA_RESULT(5,"移动公式结果并且刷新目标id"),
    //复制公式
    COPY_FORMULA_RESULT(6,"复制公式结果"),
    //变色
    CHANGE_COLOR(7,"变色"),
    //展示显示
    SHOW(8,"展示显示内容"),
    //展示显示
    HIDE(9,"隐藏内容"),

    //交换内容
    CHANGE_CONTENT(10,"交换内容"),
    //刷新公式
    FRESH_FORMULA(11,"刷新公式"),
    //清空公式
    CLEAR_FORMULA(12,"清空公式"),
    //批量移动
    MULTI_MOVE(13,"批量移动"),

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
