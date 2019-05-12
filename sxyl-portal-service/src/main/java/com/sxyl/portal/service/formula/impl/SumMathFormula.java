package com.sxyl.portal.service.formula.impl;

import com.sxyl.portal.domain.formula.SumFormula;
import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.formula.common.FormulaNode;
import com.sxyl.portal.domain.formula.common.FormulaNodeContent;
import com.sxyl.portal.domain.graph.MathFormula;
import com.sxyl.portal.service.formula.BaseFormula;
import com.sxyl.portal.service.formula.IMathFormula;
import org.springframework.stereotype.Service;


@Service("sumMathFormula")
public class SumMathFormula extends BaseFormula implements IMathFormula {


    @Override
    public MathFormula createMathFormula(FormulaCommon formulaCommon) {

        SumFormula sumFormula = (SumFormula)formulaCommon;


        StringBuffer total = new StringBuffer();
        //公式等号左侧的部分
        total.append(super.getF(formulaCommon.getF(), formulaCommon.getFsup() , formulaCommon.getFsub())) ;


        for (int i =0 ; i < sumFormula.getContents().size(); i++){
            FormulaNodeContent fnc = sumFormula.getContents().get(i);
            StringBuffer sbNode = new StringBuffer();

            for (FormulaNode fn : fnc.getNodes()){
                sbNode.append(super.getNodeText(fn.getNodeText(), fn.getNodeSup() , fn.getNodeSub())) ;
                sbNode.append(fn.getConnect());
            }
            sbNode = super.getVariable(sbNode);


            if(i!= sumFormula.getContents().size()-1){

                sbNode.append(fnc.getConnect());
            }
            total.append(sbNode);
//            stringBuffer.append(super.getF( fnc.getNodeConnect() , fnc.get))
        }

        total.append(super.getResult());
        String formulaTemplate = total.toString();
        MathFormula mathFormula = new MathFormula();
        mathFormula.setRfc(formulaTemplate);
        mathFormula.setFc(super.replaceFormula(formulaTemplate));

        return mathFormula;
    }



}
