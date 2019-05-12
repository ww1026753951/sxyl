package com.sxyl.portal.domain.formula;

import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.formula.dnn.HiddenWeightContent;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HiddenWeightFormula extends FormulaCommon implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 隐藏层
     */
    private List<HiddenWeightContent> lists;

    /***
     *
     */
    private String hiddenOut;


    /***
     *
     */
    private String hiddenOutSup;


    private String hiddenOutSub;

    /***
     * x的值
     */
    private String iv;

    /***
     * x的下标
     */
    private String ivSub;

}
