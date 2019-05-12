package com.sxyl.portal.service.nn.impl;

import com.sxyl.portal.domain.constant.formula.FormulaConstant;
import com.sxyl.portal.domain.formula.HiddenWeightFormula;
import com.sxyl.portal.domain.formula.OutWeightFormula;
import com.sxyl.portal.domain.formula.SigmoidFormula;
import com.sxyl.portal.domain.formula.SumFormula;
import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.formula.common.FormulaNode;
import com.sxyl.portal.domain.formula.common.FormulaNodeContent;
import com.sxyl.portal.domain.formula.dnn.HiddenWeightContent;
import com.sxyl.portal.domain.graph.MathFormula;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.graph.Text;
import com.sxyl.portal.domain.nn.NnWeight;
import com.sxyl.portal.domain.nn.dnn.*;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.service.formula.IMathFormula;
import com.sxyl.portal.service.formula.impl.*;
import com.sxyl.portal.service.nn.NnCommonService;
import com.sxyl.portal.service.nn.NnFormulaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NnFormulaServiceImpl extends NnCommonService implements NnFormulaService {

    private int nodeBuffer = 60;
    private int symbolBuffer = 20;

    @Override
    public List<MathFormula> getDnnFormulaTotal() {

        /****
         * 总公式的列表
         */
        List<MathFormula> result = new ArrayList<>();


        //求和函数
        MathFormula sumInput = new MathFormula();
        sumInput.setId("sumInput");
        sumInput.setFc("hidden_net1 = w1*x_1+w2*x_2+w3*x_3+..+wn*x_n");


        //sigmoid input输入框
        MathFormula sigmoidSumInput = new MathFormula();
        sigmoidSumInput.setId("sigmoid_sumInput");
        sigmoidSumInput.setFc("out1 = sigmoid(net1)");

        //sigmoid input输入框
        MathFormula sumHidden = new MathFormula();
        sumHidden.setId("sumHidden");
        sumHidden.setFc("out_net1 = w9*out1+w8*out2+w3*x3+..+wn*out_n");

        MathFormula sigmoidSumHidden = new MathFormula();
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

        //输入层到第一个隐藏层的公式
        this.addFormula(result ,  dnnConstructParam);

        //最后一个隐藏层到 输出层的公式
        this.addFormulaHiddenToOut(result , dnnConstructParam);


        //输出层损失函数
        this.addErrorFormula(result ,  dnnConstructParam);

        //反向传播 隐藏层到输出层的偏导
        this.addOutWeightFormula(result, dnnConstructParam );

        //反向传播   输入层到 隐藏层的偏导
        addHiddenWeightFormula(result,inputIds,firstHidden , outputIds , dnnConstructParam);

        return result ;
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

            List<FormulaNodeContent> formulaNodeContents = new ArrayList<>();


            //按照权重循环
            for (int j = 0 ;j < dnnHiddenNeuron.getNeuronWeight().size() ; j++ ) {

                NnWeight nnWeights = dnnHiddenNeuron.getNeuronWeight().get(j);

                String sid = nnWeights.getSid();

                DnnInputNeuron inputNeuron = dnnConstructParam.getInputNeuronMap().get(sid) ;

                FormulaNodeContent fn = new FormulaNodeContent();
                List<FormulaNode> nodes = new ArrayList<>();
                FormulaNode x2 = new FormulaNode(inputNeuron.getText(),"",nnWeights.getSourceIndex().toString(),"*");
                nodes.add(x2);
                FormulaNode w2 = new FormulaNode(nnWeights.getText(),layoutNo,(nnWeights.getTargetIndex())+""+(nnWeights.getSourceIndex()),"");
                nodes.add(w2);
                fn.setNodes(nodes);
                formulaNodeContents.add(fn);



            }
            //相加的节点
            MathFormula sumFormula = getSumFormula(dnnHiddenNeuron.getSumText() , layoutNo ,dnnHiddenNeuron.getIndex().toString()  ,  formulaNodeContents);
            groupSum.addChild(sumFormula);
            result.addChild(groupSum);



            /***
             * sigmoid 公式版本
             */
            Group sigmoidInput = new Group(super.getSigmoidId(tid) , true);
            MathFormula mathFormula = getSigmoidFormula(dnnHiddenNeuron.getActivationText() , layoutNo ,  dnnHiddenNeuron.getIndex().toString());
            sigmoidInput.addChild(mathFormula);

            //sigmoid  等号左边的
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

            List<FormulaNodeContent> formulaNodeContents = new ArrayList<>();


            for (int j = 0 ;j < dnnOutputNeuron.getNeuronWeight().size() ; j++ ) {
                NnWeight nnWeights = dnnOutputNeuron.getNeuronWeight().get(j);
                String sid = nnWeights.getSid();
                DnnHiddenNeuron hiddenNeuron = dnnConstructParam.getHiddenNeuronMap().get(sid) ;

                FormulaNodeContent fn = new FormulaNodeContent();
                List<FormulaNode> nodes = new ArrayList<>();
                FormulaNode x2 = new FormulaNode(hiddenNeuron.getActivationText(),sLayoutNo,nnWeights.getSourceIndex().toString(),"*");
                nodes.add(x2);
                FormulaNode w2 = new FormulaNode(nnWeights.getText(),tLayoutNo,(nnWeights.getTargetIndex())+""+(nnWeights.getSourceIndex()),"");
                nodes.add(w2);
                fn.setNodes(nodes);
                formulaNodeContents.add(fn);

            }


            MathFormula sumFormula = getSumFormula(dnnOutputNeuron.getSumText() , tLayoutNo ,dnnOutputNeuron.getIndex().toString()  ,  formulaNodeContents);
            groupSum.addChild(sumFormula);

            result.addChild(groupSum);



            //sigmoid ,初始化 x的值
            Group sigmoidInput = new Group();
            sigmoidInput.setId(super.getSigmoidId(tid));
            sigmoidInput.setCache(true);
            //sigmoid函数
            MathFormula mathFormula = getSigmoidFormula(dnnOutputNeuron.getActivationText() , tLayoutNo ,  dnnOutputNeuron.getIndex().toString());
            sigmoidInput.addChild(mathFormula);


            result.addChild(sigmoidInput);
        }
    }


    /***
     * 添加损失函数公式
     * @param result
     * @param dnnConstructParam
     */
    private void addErrorFormula(Group result ,DnnConstructParam dnnConstructParam){
        Group groupError;

        for (int i = 0 ; i<dnnConstructParam.getOutputLayer().getNeurons().size() ; i ++){
            DnnOutputNeuron dnnOutputNeuron = dnnConstructParam.getOutputLayer().getNeurons().get(i);
            groupError = new Group();
            groupError.setId(super.getFormulaSquaredErrorId(dnnOutputNeuron.getId()));
            groupError.setCache(true);

            /***
             * 增加公式
             */
            IMathFormula mathFormula = new MeanSquaredErrorMathFormula();
            FormulaCommon formulaCommon = new FormulaCommon();
            formulaCommon.setF(dnnOutputNeuron.getCostText());
            MathFormula formula = mathFormula.createMathFormula(formulaCommon) ;
            groupError.addChild(formula);


            result.addChild(groupError);

        }


    }


    /***
     * 添加损失函数公式
     * -(targeto1 - outo1)  * outo1(1-outo1) * outh1
     * @param result
     */
    private void addOutWeightFormula(Group result ,DnnConstructParam dnnConstructParam){
        Group outWeight;
        String tlayoutNo="2";
        String slayoutNo="1";
        for (DnnOutputNeuron dnnOutputNeuron : dnnConstructParam.getOutputLayer().getNeurons()){
            String tid = dnnOutputNeuron.getId() ;
            for(NnWeight nnWeight : dnnOutputNeuron.getNeuronWeight()){
                String sid = nnWeight.getSid() ;
                //获取权重对应的 隐藏层map
                DnnHiddenNeuron dnnHiddenNeuron = dnnConstructParam.getHiddenNeuronMap().get(sid);
                outWeight = new Group(super.getFormulaUpdateWeightId(tid,sid) , true);
                MathFormula mathFormula = getWeightFormula(dnnOutputNeuron.getActualText(),
                        nnWeight.getText() , tlayoutNo , nnWeight.getTargetIndex() + "" + nnWeight.getSourceIndex(),
                        dnnOutputNeuron.getActivationText(), tlayoutNo, dnnOutputNeuron.getIndex().toString() ,
                        dnnHiddenNeuron.getActivationText(), slayoutNo, dnnHiddenNeuron.getIndex().toString()
                );
                outWeight.addChild(mathFormula);
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
    private void addHiddenWeightFormula(Group result , List<String> inputList, List<String> hiddenList, List<String> targetList
            ,DnnConstructParam dnnConstructParam){
        Group hiddenWeight;

        String tlayoutNo="2";
        String slayoutNo="1";

        List<DnnHiddenLayer> hiddenLayers = dnnConstructParam.getDnnHiddenLayerList();

        DnnOutputLayer dnnOutputLayer = dnnConstructParam.getOutputLayer() ;
        //隐藏层循环
        for ( int i = 0 ; i < hiddenLayers.size() ; i++){
            DnnHiddenLayer dnnHiddenLayer = hiddenLayers.get(i);
            //隐藏层神经单元循环
            for (DnnHiddenNeuron dnnHiddenNeuron : dnnHiddenLayer.getNeurons()){
                for (NnWeight hnnWeight : dnnHiddenNeuron.getNeuronWeight()){
                    String sid = hnnWeight.getSid();
                    String tid = hnnWeight.getTid();
                    hiddenWeight = new Group(super.getFormulaUpdateWeightId(tid,sid) , true);

                    DnnInputNeuron dnnInputNeuron = dnnConstructParam.getInputNeuronMap().get(sid);
                    List<HiddenWeightContent> hiddenWeightContents = new ArrayList<>() ;

                    //按照输出层进行循环
                    for (DnnOutputNeuron dnnOutputNeuron :dnnOutputLayer.getNeurons()){
                        for (NnWeight nnWeight :dnnOutputNeuron.getNeuronWeight()){
                            //如果输出层的 id 和隐藏层的id相同,则说明是能匹配的权重值
                            if(nnWeight.getSid().equals(tid)){
                                HiddenWeightContent hiddenWeightContent = new HiddenWeightContent();
                                hiddenWeightContent.setActual(dnnOutputNeuron.getActualText());

                                hiddenWeightContent.setPredict(dnnOutputNeuron.getActivationText());
                                hiddenWeightContent.setPredictSup(tlayoutNo);
                                hiddenWeightContent.setPredictSub(dnnOutputNeuron.getIndex().toString());

                                hiddenWeightContent.setWeight(nnWeight.getText());
                                hiddenWeightContent.setWeightSup(tlayoutNo);
                                hiddenWeightContent.setWeightSub(nnWeight.getTargetIndex()+""+nnWeight.getSourceIndex());

                                hiddenWeightContents.add(hiddenWeightContent);
                            }
                        }
                    }
                    //todo
                    MathFormula mathFormula = getHiddenWeightFormula(hnnWeight.getText(), slayoutNo , hnnWeight.getTargetIndex()+""+hnnWeight.getSourceIndex(),
                            dnnInputNeuron.getText(),dnnInputNeuron.getIndex().toString(),dnnHiddenNeuron.getActivationText(),
                            slayoutNo , dnnHiddenNeuron.getIndex().toString(),
                            hiddenWeightContents);
                    hiddenWeight.addChild(mathFormula);
                    result.addChild(hiddenWeight);

                }
            }
        }
        //输入层循环

    }



    /***
     * 获取激活函数 sigmoid
     * template -> construct -> runConstruct
     * $$ a^1_1=sigmoid(***x***)=***????***
     * @param sigmoidText
     * @return
     */
    private MathFormula getSigmoidFormula(String sigmoidText,String layoutNo , String index){
        IMathFormula iMathFormula = new SigmoidMathFormula();
        SigmoidFormula sigmoidFormula = new SigmoidFormula();
        sigmoidFormula.setF(sigmoidText);
        sigmoidFormula.setFsup(layoutNo);
        sigmoidFormula.setFsub(index);
        MathFormula mathFormula = iMathFormula.createMathFormula(sigmoidFormula);
        return mathFormula ;
    }


    private MathFormula getSumFormula(String sumText,String layoutNo , String index ,List<FormulaNodeContent> list ){
        IMathFormula iMathFormula = new SumMathFormula();
        SumFormula sumFormula = new SumFormula();
        sumFormula.setContents(list);
        sumFormula.setF(sumText);
        sumFormula.setFsup(layoutNo);
        sumFormula.setFsub(index);
        MathFormula mathFormula = iMathFormula.createMathFormula(sumFormula);
        return mathFormula ;
    }


    /***
     * 权重更新方法
     * @param actual
     * @param f
     * @param fsup
     * @param fsub
     * @param predict
     * @param predictSup
     * @param predictSub
     * @param weightOutput
     * @param weightOutputSup
     * @param weightOutputSub
     * @return
     */
    private MathFormula getWeightFormula(String actual ,String f, String fsup ,String fsub,
                                         String predict , String predictSup , String predictSub ,
                                         String weightOutput , String weightOutputSup , String weightOutputSub
                                         ){
        IMathFormula iMathFormula =  new OutWeightMathFormula();
        OutWeightFormula outWeightFormula = new OutWeightFormula();
        outWeightFormula.setActual(actual);
        outWeightFormula.setF(f);
        outWeightFormula.setFsup(fsup);
        outWeightFormula.setFsub(fsub);
        outWeightFormula.setPredict(predict);
        outWeightFormula.setPredictSup(predictSup);
        outWeightFormula.setPredictSub(predictSub);
        outWeightFormula.setWeightOutput(weightOutput);
        outWeightFormula.setWeightOutputSup(weightOutputSup);
        outWeightFormula.setWeightOutputSub(weightOutputSub);
        MathFormula mathFormula = iMathFormula.createMathFormula(outWeightFormula);
        return mathFormula ;
    }


    /***
     * 隐藏层
     * @param iv
     * @return
     */
    private MathFormula getHiddenWeightFormula(String f, String fsup ,String fsub,
                                               String iv,String ivSub,String hiddenOut,String hiddenOutSup,String hiddenOutSub,
                                               List<HiddenWeightContent> list){
        IMathFormula iMathFormula =  new HiddenWeightMathFormula();
        HiddenWeightFormula hiddenWeightFormula = new HiddenWeightFormula();

        hiddenWeightFormula.setF(f);
        hiddenWeightFormula.setFsup(fsup);
        hiddenWeightFormula.setFsub(fsub);
        hiddenWeightFormula.setIv(iv);
        hiddenWeightFormula.setIvSub(ivSub);
        hiddenWeightFormula.setHiddenOut(hiddenOut);
        hiddenWeightFormula.setHiddenOutSup(hiddenOutSup);
        hiddenWeightFormula.setHiddenOutSub(hiddenOutSub);
        hiddenWeightFormula.setLists(list);


        MathFormula mathFormula = iMathFormula.createMathFormula(hiddenWeightFormula);
        return mathFormula ;
    }
}




