package com.sxyl.portal.domain.formula.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class FormulaCommon implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 函数的f值
     */
    private String f;

    /***
     * 公式的上标
     */
    private String fsup;

    /***
     * 公式的下标
     */
    private String fsub;
}
