package com.sxyl.portal.domain.formula;

import com.sxyl.portal.domain.formula.common.FormulaCommon;
import lombok.Data;

import java.io.Serializable;

@Data
public class OutWeightFormula extends FormulaCommon  implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
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
     * 输出层权重对应的上一层的计算值
     */
    private String weightOutput;


    /***
     * 输出层权重对应的上一层的计算值上标
     */
    private String weightOutputSup;

    /***
     * 输出层权重对应的上一层的计算值下标
     */
    private String weightOutputSub;

}
