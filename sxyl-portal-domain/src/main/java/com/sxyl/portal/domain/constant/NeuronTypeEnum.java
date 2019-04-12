package com.sxyl.portal.domain.constant;

public enum NeuronTypeEnum {

    //输入神经元
    INPUT_NEURON(1,"输入层神经元"),
    //隐藏神经元
    HIDDEN_NEURON(2,"隐藏层神经元"),
    //神经元
    OUTPUT_NEURON(3,"输出层神经元"),
    //偏置量
    BIAS(4,"偏置量");

    private final int code;
    private final String desc;
    NeuronTypeEnum(int code , String desc) {
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
