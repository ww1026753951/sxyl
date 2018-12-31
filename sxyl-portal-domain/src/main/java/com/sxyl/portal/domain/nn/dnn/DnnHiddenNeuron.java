package com.sxyl.portal.domain.nn.dnn;

import lombok.Data;

import java.io.Serializable;

@Data
public class DnnHiddenNeuron implements Serializable {

    private static final long serialVersionUID = 1L;



    //标识id ,用于html展示使用
    private String id ;

    //神经元下标
    private Integer index;

    //求和的文本id
    private String sumTextId;

    //求和的文本
    private String sumText;

    //求和的值id
    private String sumValueTextId;

    //求和的值
    private String sumValueText;

    //激活函数后的id
    private String activationTextId;

    //激活函数的文本
    private String activationText;

    //激活函数后的id
    private String activationValueTextId;

    //激活函数的文本
    private String activationValueText;

    //神经元类型
    private Integer neuronType;
}