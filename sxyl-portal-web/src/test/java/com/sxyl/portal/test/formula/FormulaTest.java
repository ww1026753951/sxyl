package com.sxyl.portal.test.formula;

import com.sxyl.portal.domain.formula.HiddenWeightFormula;
import com.sxyl.portal.domain.formula.OutWeightFormula;
import com.sxyl.portal.domain.formula.SumFormula;
import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.formula.common.FormulaNode;
import com.sxyl.portal.domain.formula.common.FormulaNodeContent;
import com.sxyl.portal.domain.formula.dnn.HiddenWeightContent;
import com.sxyl.portal.domain.graph.MathFormula;
import com.sxyl.portal.service.formula.IMathFormula;
import com.sxyl.portal.service.formula.impl.HiddenWeightMathFormula;
import com.sxyl.portal.service.formula.impl.MeanSquaredErrorMathFormula;
import com.sxyl.portal.service.formula.impl.OutWeightMathFormula;
import com.sxyl.portal.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class FormulaTest extends BaseTest {

    @Resource(name ="sumMathFormula")
    private IMathFormula iMathFormula;

    @Test
    public void testSumFormula(){
        MathFormula mathFormula = iMathFormula.createMathFormula(sumFormula());
        System.out.print(mathFormula.getFc());
        System.out.print(mathFormula.getRfc());
    }


    @Test
    public void testMSEFormula(){
        IMathFormula iMathFormula =  new MeanSquaredErrorMathFormula();
        FormulaCommon formulaCommon = new FormulaCommon();
        formulaCommon.setF("Error");
        MathFormula mathFormula = iMathFormula.createMathFormula(formulaCommon);
        System.out.print(mathFormula.getFc());
        System.out.print(mathFormula.getRfc());
    }


    @Test
    public void testWeightFormula(){

        IMathFormula iMathFormula =  new OutWeightMathFormula();
        OutWeightFormula outWeightFormula = new OutWeightFormula();

        outWeightFormula.setF("W");
        outWeightFormula.setFsub("1");
        outWeightFormula.setFsup("2");


        outWeightFormula.setPredict("z");
        outWeightFormula.setPredictSup("21");
        outWeightFormula.setPredictSub("22");

        outWeightFormula.setActual("y");

        outWeightFormula.setWeightOutput("c");
        outWeightFormula.setWeightOutputSub("31");
        outWeightFormula.setWeightOutputSup("32");


        MathFormula mathFormula = iMathFormula.createMathFormula(outWeightFormula);
        System.out.print(mathFormula.getFc());
        System.out.print(mathFormula.getRfc());
    }





    @Test
    public void testHiddenWeightFormula(){

        IMathFormula iMathFormula =  new HiddenWeightMathFormula();
        HiddenWeightFormula hiddenWeightFormula = new HiddenWeightFormula();

        hiddenWeightFormula.setF("w");
        hiddenWeightFormula.setFsup("wp");
        hiddenWeightFormula.setFsub("wb");

        hiddenWeightFormula.setIv("x1");


        hiddenWeightFormula.setHiddenOut("a");
        hiddenWeightFormula.setHiddenOutSup("1");
        hiddenWeightFormula.setHiddenOutSub("2");

        List<HiddenWeightContent> list = new ArrayList<>();

        HiddenWeightContent hiddenWeightContent1 = new HiddenWeightContent();
        hiddenWeightContent1.setActual("y1");
        hiddenWeightContent1.setPredict("a");
        hiddenWeightContent1.setPredictSup("3");
        hiddenWeightContent1.setPredictSub("4");
        list.add(hiddenWeightContent1);


        HiddenWeightContent hiddenWeightContent2 = new HiddenWeightContent();
        hiddenWeightContent2.setActual("y2");
        hiddenWeightContent2.setPredict("a");
        hiddenWeightContent2.setPredictSup("5");
        hiddenWeightContent2.setPredictSub("6");
        list.add(hiddenWeightContent2);

        hiddenWeightFormula.setLists(list);


        MathFormula mathFormula = iMathFormula.createMathFormula(hiddenWeightFormula);
        System.out.print(mathFormula.getFc());
        System.out.print(mathFormula.getRfc());
    }





    private SumFormula sumFormula(){
        SumFormula sumFormula = new SumFormula();
        List<FormulaNodeContent> list = new ArrayList<>();
        FormulaNodeContent formulaNodeContent1 = new FormulaNodeContent();
        List<FormulaNode> fn1 = new ArrayList<>();
        FormulaNode w1 = new FormulaNode("W","1","2","*");
        fn1.add(w1);
        FormulaNode x1 = new FormulaNode("x","1","2","");
        fn1.add(x1);
        formulaNodeContent1.setNodes(fn1);
        list.add(formulaNodeContent1);


        FormulaNodeContent formulaNodeContent2 = new FormulaNodeContent();
        List<FormulaNode> fn2 = new ArrayList<>();
        FormulaNode w2 = new FormulaNode("W","2","21","*");
        fn2.add(w2);
        FormulaNode x2 = new FormulaNode("x","2","22","");
        fn2.add(x2);
        formulaNodeContent2.setNodes(fn2);
        list.add(formulaNodeContent2);


        sumFormula.setContents(list);

        sumFormula.setF("z");
        sumFormula.setFsub("1");
        sumFormula.setFsup("1");

        return sumFormula;

    }
}
