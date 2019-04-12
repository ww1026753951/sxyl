package com.sxyl.portal.domain.nn;

import lombok.Data;

import java.io.Serializable;

@Data
public class NnWeight implements Serializable {

    private static final long serialVersionUID = 1L;


    /****
     * 来源神经元的下标
     */
    private Integer sourceIndex;

    /***
     * 目标神经元的下标
     */
    private Integer targetIndex;

    /***
     * 源头神经元id
     */
    private String sid;

    /***
     * 目标神经元id
     */
    private String tid;

    /***
     * 权重id
     */
    private String id;


    /***
     * 权重的文本
     */
    private String text;

    /***
     * 权重数值的id
     */
    private String valueId;

    /***
     * 权重数值的文本
     */
    private String valueText;

}
