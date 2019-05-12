package com.sxyl.portal.service.formula.impl;

import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.graph.MathFormula;
import com.sxyl.portal.service.formula.BaseFormula;
import com.sxyl.portal.service.formula.IMathFormula;
import org.springframework.stereotype.Service;

/****
 * 均方差公式
 */
@Service("meanSquaredErrorMathFormula")
public class MeanSquaredErrorMathFormula  extends BaseFormula implements IMathFormula {


    /***
     *
      <span>$$Error(all) = \sum\frac{1}{2}(y - \hat{y})^2$$</span>
     * @param formulaCommon
     * @return
     */
    @Override
    public MathFormula createMathFormula(FormulaCommon formulaCommon) {

        StringBuffer stringBuffer = new StringBuffer();
        //公式等号左侧的部分
        stringBuffer.append(super.getF(formulaCommon.getF(),"" , "")) ;
        //sigmoid计算公式部分
        stringBuffer.append("\\frac{1}{2}(");
        stringBuffer.append(super.getVariable("y"));
        stringBuffer.append("-");
        stringBuffer.append(super.getVariable("\\hat{y}"));
        stringBuffer.append(")^2");
        //计算结果的部分
        stringBuffer.append(super.getResult());


        String formulaTemplate = stringBuffer.toString();
        MathFormula mathFormula = new MathFormula();
        mathFormula.setRfc(formulaTemplate);
        mathFormula.setFc(super.replaceFormula(formulaTemplate));
        return mathFormula;
    }
}
