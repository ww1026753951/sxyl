package com.sxyl.portal.service.formula;

import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.graph.MathFormula;

public interface IMathFormula {

    /***
     * 创建公式对象
     * @return
     */
    MathFormula createMathFormula(FormulaCommon formulaCommon);
}
