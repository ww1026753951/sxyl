package com.sxyl.portal.service.nn.impl;

import com.sxyl.portal.domain.constant.PositionEnum;
import com.sxyl.portal.domain.constant.formula.FormulaConstant;
import com.sxyl.portal.domain.formula.MathFormulaTemplate;
import com.sxyl.portal.domain.graph.Attr;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.graph.Text;
import com.sxyl.portal.domain.nn.NnWeight;
import com.sxyl.portal.domain.nn.dnn.*;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.service.nn.NnCommonService;
import com.sxyl.portal.service.nn.NnFormulaService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NnFormulaServiceImpl extends NnCommonService implements NnFormulaService {


    private int nodeBuffer = 60;
    private int symbolBuffer = 20;

    /***
     * 公式起始的x值
     */
    private final int FORMULA_X = 150;

    /***
     * 公式起始的y值
     */
    private final int FORMULA_Y = 30;


    @Override
    public List<MathFormulaTemplate> getDnnFormulaTotal() {

        /****
         * 总公式的列表
         */
        List<MathFormulaTemplate> result = new ArrayList<>();


        //求和函数
        MathFormulaTemplate sumInput = new MathFormulaTemplate();
        sumInput.setId("sumInput");
        sumInput.setFc("hidden_net1 = w1*x_1+w2*x_2+w3*x_3+..+wn*x_n");


        //sigmoid input输入框
        MathFormulaTemplate sigmoidSumInput = new MathFormulaTemplate();
        sigmoidSumInput.setId("sigmoid_sumInput");
        sigmoidSumInput.setFc("out1 = sigmoid(net1)");

        //sigmoid input输入框
        MathFormulaTemplate sumHidden = new MathFormulaTemplate();
        sumHidden.setId("sumHidden");
        sumHidden.setFc("out_net1 = w9*out1+w8*out2+w3*x3+..+wn*out_n");

        MathFormulaTemplate sigmoidSumHidden = new MathFormulaTemplate();
        sigmoidSumHidden.setId("sigmoid_sumHidden");
        sigmoidSumHidden.setFc("net1 = w1*x1+w2*x2+w3*x3+..+wn*xn");

        //输入层求和
        result.add(sumInput);
        //sigmoid  输入层
        result.add(sigmoidSumInput);

        //隐藏层求和
        result.add(sumHidden);
        // sigmoid 隐藏层
        result.add(sigmoidSumHidden);
        return result;
    }





    @Override
    public Group getDnnFormulaDetailSVG(List<String> inputIds, List<List<String>> hiddenIds, List<String> outputIds , DnnConstructParam dnnConstructParam){

        Group result = new Group();

        List<String> firstHidden =hiddenIds.get(0);

        int start =0 ;
        //输入层到第一个隐藏层的公式
        this.addFormula(result ,  dnnConstructParam);

        //最后一个隐藏层到 输出层的公式
        this.addFormulaHiddenToOut(result , dnnConstructParam);


        //输出层损失函数
        this.addErrorFormula(result , outputIds);

        //反向传播 隐藏层到输出层的偏导
        this.addOutWeightFormula(result,firstHidden , outputIds ,start );

        //反向传播   输入层到 隐藏层的偏导
        addHiddenWeightFormula(result,inputIds,firstHidden , outputIds);

        return result ;
    }


    /***
     * 获取权重的信息
     * @return
     */
    private Text getWeightSumAndPlus(String sid , String tid , String layoutNo , NnWeight nnWeights , Map<String , DnnInputNeuron> dnnInputNeuronMap){
        Text wx = new Text(super.getSumNodeId(sid,tid)  ,PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND);

        DnnInputNeuron inputNeuron = dnnInputNeuronMap.get(sid) ;
        wx.setSt(inputNeuron.getText() + " * " + nnWeights.getText());
        wx = this.addSubAndSup(wx ,  null , nnWeights.getSourceIndex().toString(),0,0,-26,0);
        wx = this.addSubAndSup(wx, layoutNo , (nnWeights.getTargetIndex())+""+(nnWeights.getSourceIndex()), -6,0,18,0);
        return wx;
    }

    /***
     * 获取权重的信息
     * @return
     */
    private Text getHiddenWeightSumAndPlus(String sid , String tid , String sLayoutNo,String tLayoutNo , NnWeight nnWeights , Map<String , DnnHiddenNeuron> dnnHiddenNeuronMap){
        Text wx = new Text(super.getSumNodeId(sid,tid)  ,PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND);

        List<Attr> attrs = new ArrayList<>();
        attrs.add(new Attr("word-spacing" , "3"));
        DnnHiddenNeuron hiddenNeuron = dnnHiddenNeuronMap.get(sid) ;
        wx.setSt(hiddenNeuron.getActivationText() + " * " + nnWeights.getText() );
        wx.setAttr(attrs);
        wx = this.addSubAndSup(wx ,  sLayoutNo , nnWeights.getSourceIndex().toString(),-5,0,-32,0);
        wx = this.addSubAndSup(wx, tLayoutNo , (nnWeights.getTargetIndex())+""+(nnWeights.getSourceIndex()), -6,0,25,0);
        return wx;
    }

    /***
     * 增加公式
     * @param result
     */
    private void addFormula(Group result ,  DnnConstructParam dnnConstructParam){
        String layoutNo = "1" ;
        List<DnnHiddenLayer> list = dnnConstructParam.getDnnHiddenLayerList();
        DnnHiddenLayer firstDnnHiddenLayer = list.get(0);
        for (DnnHiddenNeuron dnnHiddenNeuron :firstDnnHiddenLayer.getNeurons()){

            String tid = dnnHiddenNeuron.getId();
            Group groupSum = new Group();
            groupSum.setId(super.getSumInput(tid));
            groupSum.setCache(true);
            //等号左侧的公式
            Text net = new Text(FORMULA_X,FORMULA_Y,dnnHiddenNeuron.getSumText());
            net = this.addSubAndSup(net, layoutNo , dnnHiddenNeuron.getIndex().toString());
            groupSum.addChild(net);
            groupSum.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"="));

            //按照权重循环
            for (int j = 0 ;j < dnnHiddenNeuron.getNeuronWeight().size() ; j++ ) {
                NnWeight nnWeights = dnnHiddenNeuron.getNeuronWeight().get(j);
                String sid = nnWeights.getSid();
                groupSum.addChild(getWeightSumAndPlus(sid,tid,layoutNo , nnWeights , dnnConstructParam.getInputNeuronMap()));
                if(j != dnnHiddenNeuron.getNeuronWeight().size() -1){
                    //加法符号
                    groupSum.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,FormulaConstant.PLUS));
                }
            }
            //相加的节点
            groupSum.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"="));
            //结果集
            groupSum.addChild(new Text(getFormulaResultId(tid),PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"???"));
//            groupSum.addChild(new Text(getFormulaResultId(tid),PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"$$z^1 = W^1*X + b^1$$"));

            result.addChild(groupSum);

            //sigmoid ,初始化 x的值
            Group sigmoidInput = new Group();
            sigmoidInput.setId(super.getSigmoidId(tid));
            sigmoidInput.setCache(true);

            //sigmoid  等号左边的
            Text netSigmoid = new Text(FORMULA_X,FORMULA_Y,dnnHiddenNeuron.getActivationText());
            netSigmoid = this.addSubAndSup(netSigmoid, layoutNo , dnnHiddenNeuron.getIndex().toString());
            sigmoidInput.addChild(netSigmoid);
            sigmoidInput.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"="));
            sigmoidInput.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"sigmoid("));
            sigmoidInput.addChild(new Text(super.getSigmoidNodeId(tid),PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,10,0 ,"x"));
            sigmoidInput.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,10,0 ,")"));
            sigmoidInput.addChild(new Text( PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"="));
            sigmoidInput.addChild(new Text(super.getFormulaResultId(tid) , PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"???"));
            result.addChild(sigmoidInput);
        }

    }


    /***
     * 增加公式
     * @param result
     */
    private void addFormulaHiddenToOut(Group result  ,DnnConstructParam dnnConstructParam){
        DnnOutputLayer dnnOutputLayer = dnnConstructParam.getOutputLayer();
        String sLayoutNo = "1" ;
        String tLayoutNo = "2" ;
        for (DnnOutputNeuron dnnOutputNeuron :dnnOutputLayer.getNeurons()){

            String tid = dnnOutputNeuron.getId();
            Group groupSum = new Group();
            groupSum.setId(super.getSumInput(tid));
            groupSum.setCache(true);
            //等号左侧的公式
            Text net = new Text(FORMULA_X,FORMULA_Y,dnnOutputNeuron.getSumText());
            net = this.addSubAndSup(net, tLayoutNo , dnnOutputNeuron.getIndex().toString());
            groupSum.addChild(net);
            groupSum.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"="));

            //按照权重循环
            for (int j = 0 ;j < dnnOutputNeuron.getNeuronWeight().size() ; j++ ) {
                NnWeight nnWeights = dnnOutputNeuron.getNeuronWeight().get(j);
                String sid = nnWeights.getSid();
                groupSum.addChild(getHiddenWeightSumAndPlus(sid,tid , sLayoutNo , tLayoutNo, nnWeights , dnnConstructParam.getHiddenNeuronMap()));
                if(j != dnnOutputNeuron.getNeuronWeight().size() -1){
                    //加法符号
                    groupSum.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,FormulaConstant.PLUS));
                }
            }
            //相加的节点
            groupSum.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"="));
            //结果集
            groupSum.addChild(new Text(getFormulaResultId(tid),PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"???"));
            result.addChild(groupSum);

            //sigmoid ,初始化 x的值
            Group sigmoidInput = new Group();
            sigmoidInput.setId(super.getSigmoidId(tid));
            sigmoidInput.setCache(true);

            //sigmoid  等号左边的
            Text netSigmoid = new Text(FORMULA_X,FORMULA_Y,dnnOutputNeuron.getActivationText());
            netSigmoid = this.addSubAndSup(netSigmoid, tLayoutNo , dnnOutputNeuron.getIndex().toString());
            sigmoidInput.addChild(netSigmoid);
            sigmoidInput.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"="));
            sigmoidInput.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"sigmoid("));
            sigmoidInput.addChild(new Text(super.getSigmoidNodeId(tid),PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,10,0 ,"x"));
            sigmoidInput.addChild(new Text(PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,10,0 ,")"));
            sigmoidInput.addChild(new Text( PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"="));
            sigmoidInput.addChild(new Text(super.getFormulaResultId(tid) , PositionEnum.EXTEND_PLUS ,PositionEnum.EXTEND,"???"));
            result.addChild(sigmoidInput);
        }
    }


    /***
     * 添加损失函数公式
     * @param result
     * @param targetList
     */
    private void addErrorFormula(Group result , List<String> targetList){
        Group groupError;

        for (int i = 0 ; i<targetList.size() ; i ++){
            String tid = targetList.get(i);
            groupError = new Group();
            groupError.setId(super.getFormulaSquaredErrorId(tid));
            groupError.setCache(true);

            int x = 150;
            int y = 30 ;


            //损失函数
            Text netError = new Text(x,y);
            netError.setSt(super.getErrorText(new Integer(i+1).toString()) + "=");
            groupError.addChild(netError);


            // ((target - out1)^2)/2
            x=x+nodeBuffer;
            Text target_s = new Text(x,y);
            target_s.setSt("((");
            groupError.addChild(target_s);


            x = x + symbolBuffer;
            Text target = new Text(x,y);
            target.setSt(super.getTargetNetText(new Integer(i+1).toString()));
            target.setId(super.getSquaredErrorTargetNodeId(tid));
            groupError.addChild(target);


            //减号
            x = x + nodeBuffer + symbolBuffer;
            groupError.addChild(new Text(x,y,"-"));


            x = x + symbolBuffer;
            Text error = new Text(x,y);
            error.setSt(super.getErrorText(new Integer(i+1).toString()));
            error.setId(super.getSquaredErrorErrorNodeId(tid));
            groupError.addChild(error);


            x = x + nodeBuffer;
            Text t = new Text(x,y);
            t.setSt(")^2)/2=");
            groupError.addChild(t);

//            x=x+symbolBuffer;
//            groupError.addChild(new Text( x, y ,"="));
            x=x+nodeBuffer;
            groupError.addChild(new Text(super.getFormulaResultId(tid) , x, y ,"???"));

            result.addChild(groupError);

        }


    }


    /***
     * 添加损失函数公式
     * -(targeto1 - outo1)  * outo1(1-outo1) * outh1
     * @param result
     * @param targetList
     */
    private void addOutWeightFormula(Group result , List<String> hiddenList, List<String> targetList,int start){
        Group outWeight;
        for (int i = 0 ; i < targetList.size() ; i ++){
            String tid = targetList.get(i);
            for (int j = 0 ; j < hiddenList.size() ; j++) {
                String sid = hiddenList.get(j);
                String weightText = super.getWeightText(new Integer((start++)+1).toString());
                outWeight = new Group();
                outWeight.setId(super.getFormulaUpdateWeightId(tid,sid));
                outWeight.setCache(true);

                int x = 150;
                int y = 30 ;

                x = x + symbolBuffer;
                Text target = new Text(x,y);
//                text.setSt();
                target.setSt(weightText+"="+weightText + " - ∂Etotal/∂"+weightText+"=");
//                target.setId(super.getSquaredErrorTargetNodeId(tid));
                outWeight.addChild(target);

                x = x + nodeBuffer*4;
                outWeight.addChild(new Text(x , y ,"-("));

                // targeto1
                x = x + symbolBuffer ;
                Text t = new Text(x,y);
                t.setId(super.getOutWeightTargetNodeId(tid));
                t.setSt(super.getTargetNetText(new Integer(j+1).toString()));
                outWeight.addChild(t);

                x = x + nodeBuffer ;
                outWeight.addChild(new Text(x , y ,"-"));

                x = x + symbolBuffer ;

                Text out0 = new Text(x,y);
                out0.setId(super.getOutWeightOutNodeId(tid,"0"));
                out0.setSt(super.getOutOutText(new Integer(j+1).toString()));
                outWeight.addChild(out0);

                x = x + nodeBuffer ;

                outWeight.addChild(new Text(x , y , ")*"));


                x = x + symbolBuffer ;
                Text out1 = new Text(x,y);
                out1.setId(super.getOutWeightOutNodeId(tid,"1"));
                out1.setSt(super.getOutOutText(new Integer(j+1).toString()));
                outWeight.addChild(out1);

                x = x + nodeBuffer ;
                outWeight.addChild(new Text(x,y,"(1-"));

                x = x + symbolBuffer ;
                Text out2 = new Text(x,y);
                out2.setId(super.getOutWeightOutNodeId(tid,"2"));
                out2.setSt(super.getOutOutText(new Integer(j+1).toString()));
                outWeight.addChild(out2);


                x = x + nodeBuffer ;
                outWeight.addChild(new Text(x,y,")*"));

                x = x + symbolBuffer ;
                Text outh = new Text(x,y);
                outh.setId(super.getOutWeightOutHiddenNodeId(sid));
                outh.setSt(super.getHiddenOutText(new Integer(j+1).toString()));
                outWeight.addChild(outh);
//                result.addChild(new Text(x , y ,"-"));

                x = x+ nodeBuffer+symbolBuffer;
                //相加的节点
                outWeight.addChild(new Text(x,y,"="));

                //结果集
                x=x+symbolBuffer;
                outWeight.addChild(new Text(super.getFormulaResultId(tid) , x, y ,"???"));

                result.addChild(outWeight);

            }
        }
    }







    /***
     * 添加损失函数公式
     * (δo1 * w10 +δo2 * w13 + δo3* w14)  * outh1(1-outh1) * x1
     * @param result
     * @param targetList
     */
    private void addHiddenWeightFormula(Group result , List<String> inputList, List<String> hiddenList, List<String> targetList){
        Group hiddenWeight;

        for (int k = 0 ; k<inputList.size() ; k ++){

            String iid = inputList.get(k);

            Integer inputIndex = k+1;

            for (int i = 0 ; i < hiddenList.size() ; i ++){
                String hid = hiddenList.get(i);
                Integer hiddenIndex = i;

                hiddenWeight = new Group();
                hiddenWeight.setId(super.getFormulaUpdateWeightId(hid,iid));
                hiddenWeight.setCache(true);


                int x = 150;
                int y = 30 ;

                String weightText = super.getWeightText(new Integer(inputIndex+(hiddenIndex)*3).toString());
                Text target = new Text(x,y);
//                text.setSt();
                target.setSt(weightText+"="+weightText + " - ∂Etotal/∂"+weightText+"= (");
//                target.setId(super.getSquaredErrorTargetNodeId(tid));
                hiddenWeight.addChild(target);


                for (int j = 0 ; j < targetList.size() ; j++) {

                    if(j==0){
                        x = x + nodeBuffer*3+symbolBuffer ;
                    }else {
                        x = x + symbolBuffer;
                    }
                    Text delta =new Text(x,y);
                    delta.setId(super.getDeltaOutputNodeId(targetList.get(j)));
                    int targetIndex = j+1;
                    delta.setSt(super.getDeltaOutputText(new Integer(targetIndex).toString()));
                    hiddenWeight.addChild(delta);

                    x = x+nodeBuffer;
                    hiddenWeight.addChild(new Text(x,y,"*"));



                    x = x+symbolBuffer;
                    Text weight =new Text(x,y);
                    weight.setSt(super.getWeightText(new Integer(inputList.size() * hiddenList.size() + 1 + targetList.size()*j).toString()));
                    weight.setId(super.getOutputWeightNodeId(hid , targetList.get(j)));

                    hiddenWeight.addChild(weight);

                    x = x + nodeBuffer;
                    if(j==targetList.size()-1){
                        hiddenWeight.addChild(new Text(x,y,")*("));
                    }else {
                        hiddenWeight.addChild(new Text(x,y,FormulaConstant.PLUS));
                    }

                }




                x = x + symbolBuffer ;
                Text out1 = new Text(x,y);
                out1.setId(super.getOutWeightOutNodeId(hid,"0"));
                out1.setSt(super.getHiddenOutText(new Integer(i+1).toString()));
                hiddenWeight.addChild(out1);

                x = x + nodeBuffer ;
                hiddenWeight.addChild(new Text(x,y,"(1-"));

                x = x + symbolBuffer ;
                Text out2 = new Text(x,y);
                out2.setId(super.getOutWeightOutNodeId(hid,"1"));
                out2.setSt(super.getHiddenOutText(new Integer(i+1).toString()));
                hiddenWeight.addChild(out2);


                x = x + nodeBuffer ;
                hiddenWeight.addChild(new Text(x,y,")*"));

                x = x + symbolBuffer ;
                Text outh = new Text(x,y);
                outh.setId(super.getOutWeightOutHiddenNodeId(iid));
                outh.setSt(super.getInputText(new Integer(inputIndex).toString()));
                hiddenWeight.addChild(outh);

                x = x+ nodeBuffer+symbolBuffer;
                //相加的节点
                hiddenWeight.addChild(new Text(x,y,"="));

                //结果集
                x=x+symbolBuffer;
                hiddenWeight.addChild(new Text(super.getFormulaResultId(iid) , x, y ,"???"));


                result.addChild(hiddenWeight);

            }

        }


    }
}




