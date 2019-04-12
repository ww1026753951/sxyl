package com.sxyl.portal.domain.nn.dnn;

import lombok.Data;

import java.io.Serializable;

@Data
public class DnnInputNeuron implements Serializable {

    private static final long serialVersionUID = 1L;

    /****
     * 标识id ,用于html展示使用
     */
    private String id ;

    /***
     * 神经元下标
     */
    private Integer index;

    /****
     * 文本展示内容
     */
    private String text;

    /***
     * 文本展示的id
     */
    private String textId;

    /***
     * 实际值的文本内容
     */
    private String valueText;

    /***
     * 实际值的文本id
     */
    private String valueTextId;

    /****
     * 神经元类型
     * @see com.sxyl.portal.domain.constant.NeuronTypeEnum
     */
    private Integer neuronType;

}
