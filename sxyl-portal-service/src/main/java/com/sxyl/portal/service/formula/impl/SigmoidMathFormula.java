package com.sxyl.portal.service.formula.impl;

import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.graph.MathFormula;
import com.sxyl.portal.service.formula.BaseFormula;
import com.sxyl.portal.service.formula.IMathFormula;
import org.springframework.stereotype.Service;

@Service("sigmoidMathFormula")
public class SigmoidMathFormula extends BaseFormula implements IMathFormula {


    @Override
    public MathFormula createMathFormula(FormulaCommon formulaCommon) {
        StringBuffer stringBuffer = new StringBuffer();
        //公式等号左侧的部分
        stringBuffer.append(super.getF(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub())) ;
        //sigmoid计算公式部分
        stringBuffer.append("{sigmoid}(");
        stringBuffer.append(super.getVariable("x"));
        stringBuffer.append(")");
        //计算结果的部分
        stringBuffer.append(super.getResult());
        String formulaTemplate = stringBuffer.toString();
        MathFormula mathFormula = new MathFormula();
        mathFormula.setRfc(formulaTemplate);
        mathFormula.setFc(super.replaceFormula(formulaTemplate));
        return mathFormula;

    }

}
