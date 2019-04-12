package com.sxyl.portal.domain.nn.dnn;

import lombok.Data;

@Data
public class Neuron {

    //标识id ,用于html展示使用
    private String id ;


    /****
     * 神经元类型
     * @see com.sxyl.portal.domain.constant.NeuronTypeEnum
     */
    private Integer neuronType;
}
