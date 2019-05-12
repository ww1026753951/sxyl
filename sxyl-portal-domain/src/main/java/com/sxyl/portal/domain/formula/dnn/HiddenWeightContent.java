package com.sxyl.portal.domain.formula.dnn;

import lombok.Data;

import java.io.Serializable;

@Data
public class HiddenWeightContent  implements Serializable {

    private static final long serialVersionUID = 1L;

    /****
     * 实际值
     */
    private String actual;

    /***
     * 预测值
     */
    private String predict;
    /***
     * 预测值上标
     */
    private String predictSup;
    /***
     * 预测值下标
     */
    private String predictSub;

    /***
     * 权重
     */
    private String weight;
    /****
     * 权重上标
     */
    private String weightSup;
    /***
     * 权重下标
     */
    private String weightSub;
}
