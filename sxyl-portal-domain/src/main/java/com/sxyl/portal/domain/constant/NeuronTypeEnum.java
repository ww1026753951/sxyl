package com.sxyl.portal.domain.constant;

public enum NeuronTypeEnum {

    //神经元
    NEURON(1,"神经元"),
    //偏置量
    BIAS(2,"偏置量");

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
