package com.sxyl.portal.service.formula.impl;

import com.sxyl.portal.domain.formula.OutWeightFormula;
import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.graph.MathFormula;
import com.sxyl.portal.service.formula.BaseFormula;
import com.sxyl.portal.service.formula.IMathFormula;
import org.springframework.stereotype.Service;

@Service("outWeightMathFormula")
public class OutWeightMathFormula  extends BaseFormula implements IMathFormula {



    @Override
    public MathFormula createMathFormula(FormulaCommon formulaCommon) {

        OutWeightFormula outWeightFormula = (OutWeightFormula)formulaCommon;

        StringBuffer stringBuffer = new StringBuffer();
        //公式等号左侧的部分
        stringBuffer.append(super.getF(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub())) ;
        //sigmoid计算公式部分
        stringBuffer.append(super.getNodeText(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub()));

        stringBuffer.append("-");

        // e total 对权重求偏导部分
        stringBuffer.append("\\frac{\\partial Error(all)}{\\partial ");
        stringBuffer.append(super.getNodeText(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub()));
        stringBuffer.append("}=");

        stringBuffer.append(super.getVariable(super.getNodeText(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub())));
        //第一个小括号为 权重减的括号
        stringBuffer.append("-(");

        //第二小括号是  更新权重的偏导，
        stringBuffer.append("-(");

        stringBuffer.append(super.getVariable(new StringBuffer(outWeightFormula.getActual()))) ;

        stringBuffer.append("-");
        StringBuffer predict = super.getNodeText(outWeightFormula.getPredict() ,outWeightFormula.getPredictSup() , outWeightFormula.getPredictSub() );
        stringBuffer.append(super.getVariable(predict));

        stringBuffer.append(")*");
        stringBuffer.append(super.getVariable(predict));
        stringBuffer.append("(1-");
        stringBuffer.append(super.getVariable(predict));
        stringBuffer.append(")*");


        stringBuffer.append(super.getVariable(super.getNodeText(outWeightFormula.getWeightOutput(),
                outWeightFormula.getWeightOutputSup() , outWeightFormula.getWeightOutputSub()))) ;

        stringBuffer.append(")");
        //计算结果的部分
        stringBuffer.append(super.getResult());


        return wrapMathFormula(stringBuffer.toString());
    }
}
