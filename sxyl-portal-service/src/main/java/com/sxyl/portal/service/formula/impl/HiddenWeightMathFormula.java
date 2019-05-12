package com.sxyl.portal.service.formula.impl;

import com.sxyl.portal.domain.formula.HiddenWeightFormula;
import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.formula.dnn.HiddenWeightContent;
import com.sxyl.portal.domain.graph.MathFormula;
import com.sxyl.portal.service.formula.BaseFormula;
import com.sxyl.portal.service.formula.IMathFormula;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("hiddenWeightMathFormula")
public class HiddenWeightMathFormula  extends BaseFormula implements IMathFormula {



    @Override
    public MathFormula createMathFormula(FormulaCommon formulaCommon) {

        HiddenWeightFormula hwf = (HiddenWeightFormula)formulaCommon;

        StringBuffer stringBuffer = new StringBuffer();
        //公式等号左侧的部分
        stringBuffer.append(super.getF(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub())) ;
        //sigmoid计算公式部分
        stringBuffer.append(super.getNodeText(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub()));

        stringBuffer.append("-");

        // e total 对权重求偏导部分
        stringBuffer.append("\\frac{\\partial Error(all)}{\\partial ");
        stringBuffer.append(super.getNodeText(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub()));
        stringBuffer.append("}\\\\=");

        stringBuffer.append(super.getVariable(super.getNodeText(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub())));
        //第一个小括号为 权重减的括号
        stringBuffer.append("-(");

        //误差项求和部分
        addFormula(stringBuffer ,hwf.getLists() ) ;

        //隐藏层的输出偏导部分
        stringBuffer.append("*");
        stringBuffer.append(super.getVariable(super.getNodeText(hwf.getHiddenOut() , hwf.getHiddenOutSup() ,hwf.getHiddenOutSub())));


        stringBuffer.append("*(1-");
        stringBuffer.append(super.getVariable(super.getNodeText(hwf.getHiddenOut() , hwf.getHiddenOutSup() ,hwf.getHiddenOutSub())));
        stringBuffer.append(")*");
        stringBuffer.append(super.getVariable(super.getNodeText(hwf.getIv(),"",hwf.getIvSub())));
        stringBuffer.append(super.getResult()) ;
        return wrapMathFormula(stringBuffer.toString());
    }

    private void addFormula(StringBuffer stringBuffer ,List<HiddenWeightContent> list){
        stringBuffer.append("(");

        for (int i =0;i< list.size() ; i++){
            HiddenWeightContent c = list.get(i);
            stringBuffer.append("(");

            //总:  -(实际值-预测值)*预测值*(1-预测值)
            //第一部分 start      -(实际值-预测值)
            stringBuffer.append("-(");
            //实际值
            stringBuffer.append(super.getVariable(c.getActual()));
            stringBuffer.append("-");
            //预测值
            stringBuffer.append(super.getVariable(super.getNodeText(c.getPredict() , c.getPredictSup() , c.getPredictSub())));
            stringBuffer.append(")*");
            //第一部分 end

            //第二部分 start   预测值
            stringBuffer.append(super.getVariable(super.getNodeText(c.getPredict() , c.getPredictSup() , c.getPredictSub())));
            //第二部分 end

            //第三部分 start (1-预测值)
            stringBuffer.append("*(1-");
            stringBuffer.append(super.getVariable(super.getNodeText(c.getPredict() , c.getPredictSup() , c.getPredictSub())));
            stringBuffer.append("))*");
            stringBuffer.append(super.getVariable(super.getNodeText(c.getWeight() , c.getWeightSup() , c.getWeightSub())));

            if(i != list.size()-1){
                stringBuffer.append("+");
            }
        }

        stringBuffer.append(")") ;

    }
}
