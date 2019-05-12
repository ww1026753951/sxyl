package com.sxyl.portal.domain.formula;

import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.formula.common.FormulaNodeContent;
import lombok.Data;

import java.util.List;

@Data
public class SumFormula extends FormulaCommon {


    /***
     * 求和的节点公式
     */
    private List<FormulaNodeContent> contents;

}
