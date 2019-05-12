package com.sxyl.portal.service.nn.impl;

import com.sxyl.portal.common.JUUID;
import com.sxyl.portal.domain.constant.*;
import com.sxyl.portal.domain.constant.graph.BaselineShiftEnum;
import com.sxyl.portal.domain.graph.*;
import com.sxyl.portal.domain.nn.dnn.*;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.service.nn.NnCommonService;
import com.sxyl.portal.service.nn.NnConstructService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class NnConstructServiceImpl extends NnCommonService implements NnConstructService {

    private double ratio = 4.5D;

    //数值的 缓冲值
    private int VALUE_BUFFER = 20;


    //数值的 缓冲值
    private int ERROR_BUFFER = 22;

    //权重的上下标的buffer
    private int WEIGHT_SUB_BUFFER = -6 ;

    private int BIAS_R = 20 ;

    /***
     * 默认颜色
     */
    private final String BIAS_COLOR="gray";


    @Override
    public Group getDnnConstruct(DnnConstructParam dnnConstructParam) {
        //创建输入层
        Group groupInput =  circleInput(dnnConstructParam.getDnnInputLayer());
        //创建隐藏层
        Group circleHidden = circleHidden(dnnConstructParam.getDnnHiddenLayerList());
        //创建输出层  ,
        Group circleOutput = circleOutput(dnnConstructParam.getOutputLayer(),dnnConstructParam.getDnnHiddenLayerList().size()+1);
        Group all = new Group();
        all.setMl(80);
        all.setMt(120);
        all.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        all.addChild(groupInput);
        all.addChild(circleHidden);
        all.addChild(circleOutput);
        //创建线的模块
        all.addChild(lineList(dnnConstructParam));


        return all;
    }




    /****
     * 创建输入层的圆心
     *
     * List<String> inputIdsa ,
     * @return
     */
    private Group circleInput(DnnInputLayer dnnInputLayer){
        int r = 30 ;
        Group group = new Group();
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        group.setMl(20);


        List<DnnInputNeuron> list = dnnInputLayer.getNeurons();
        for (int i=0;i< list.size();i++){
            DnnInputNeuron dnnInputNeuron = list.get(i);
            Circle circle = new Circle(dnnInputNeuron.getId(), r ,"red");
            circle.setMt(50);
            //圆心的文本
            circle.addCurrentComponent(addSubAndSup(
                    new Text(dnnInputNeuron.getTextId() , 0,0,dnnInputNeuron.getText() ,ShowTextPositionEnum.MIDDLE.getCode() ), null,dnnInputNeuron.getIndex().toString()));

            //圆心的文本id
            circle.addCurrentComponent(new Text(dnnInputNeuron.getValueTextId(), 0,r + VALUE_BUFFER,
                    dnnInputNeuron.getValueText(),ShowTextPositionEnum.MIDDLE.getCode()));

            group.addChild(circle);
        }

        //bias的偏置量
//        DnnBiasNeuron dnnBiasNeuron = dnnInputLayer.getDnnBiasNeuron();
//        Circle circleBias = new Circle(dnnBiasNeuron.getId(), BIAS_R ,BIAS_COLOR);
//        circleBias.setMt(15);
////        circleBias.setMl(40);
//        //圆心的文本
//        circleBias.addCurrentComponent(new Text(dnnBiasNeuron.getTextId() , 0,0,dnnBiasNeuron.getText() ,ShowTextPositionEnum.MIDDLE.getCode() ));
//
//        circleBias.addCurrentComponent(new Text(dnnBiasNeuron.getValueTextId(), 0,r + VALUE_BUFFER/3,
//                dnnBiasNeuron.getValueText(),ShowTextPositionEnum.MIDDLE.getCode()));
//        group.addChild(circleBias);

        return group;
    }



    /****
     * 创建隐藏层的结构   List<List<String>> hiddenIds ,
     * @return
     */
    private Group circleHidden(List<DnnHiddenLayer> hiddenLayers){
        int r = 35;
        Group group = new Group();
        group.setMl(300);
        group.setMt(-40);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());

        for (int i = 0;i< hiddenLayers.size();i++){
            DnnHiddenLayer dnnHiddenLayer = hiddenLayers.get(i);



            for (int j=0;j< dnnHiddenLayer.getNeurons().size();j++){
                DnnHiddenNeuron dnnHiddenNeuron = dnnHiddenLayer.getNeurons().get(j) ;
                Circle circle = new Circle(dnnHiddenNeuron.getId(), r ,"blue");
//                circle.setId();
                circle.setMt(50);
                if(j==0){

                }

                //圆心的文本 , net 单元 ,sum的文本
                circle.addCurrentComponent(addSubAndSup(new Text(dnnHiddenNeuron.getSumTextId(),-r/2,0,
                        dnnHiddenNeuron.getSumText(),ShowTextPositionEnum.MIDDLE.getCode()),  (i+1)+"" ,(j+1)+""  ));

                //圆心的文本 ,  net 单元的输出值 sum的值
                circle.addCurrentComponent(new Text(dnnHiddenNeuron.getSumValueTextId() , -r/2 , r + VALUE_BUFFER,
                        dnnHiddenNeuron.getSumValueText() , ShowTextPositionEnum.MIDDLE.getCode()));


                //圆心的文本 , 激活函数后文本
                circle.addCurrentComponent(addSubAndSup(new Text(dnnHiddenNeuron.getActivationTextId(),r/2,0,
                        dnnHiddenNeuron.getActivationText(),ShowTextPositionEnum.MIDDLE.getCode()) ,  (i+1)+"" ,(j+1)+""));

                //圆心   激活函数后的 值的文本
                circle.addCurrentComponent(new Text(dnnHiddenNeuron.getActivationValueTextId(),r/2,r + VALUE_BUFFER,
                        dnnHiddenNeuron.getActivationValueText(),ShowTextPositionEnum.MIDDLE.getCode()));

                //圆心的线
                circle.addCurrentComponent(new Line(LineTypeEnum.POSITION.getCode(),0,-r,0,r));
                group.addChild(circle);
            }

            //bias的偏置量
//            DnnBiasNeuron dnnBiasNeuron = dnnHiddenLayer.getDnnBiasNeuron();
//            Circle circleBias = new Circle(dnnBiasNeuron.getId(), BIAS_R ,BIAS_COLOR);
//            circleBias.setMt(30);
////            circleBias.setMl(40);
//            //圆心的文本
//            circleBias.addCurrentComponent(new Text(dnnBiasNeuron.getTextId() , 0,0,dnnBiasNeuron.getText() ,ShowTextPositionEnum.MIDDLE.getCode() ));
//            circleBias.addCurrentComponent(new Text(dnnBiasNeuron.getValueTextId(), 0,r + VALUE_BUFFER/3,
//                    dnnBiasNeuron.getValueText(),ShowTextPositionEnum.MIDDLE.getCode()));
//            group.addChild(circleBias);


        }
        return group;
    }

    /****
     * 创建输出层的结构   ,
     * @return
     */
    private Group circleOutput( DnnOutputLayer dnnOutputLayer ,Integer layerNo){
        int r = 35;
        Group group = new Group();
        group.setMl(300);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());


        List<DnnOutputNeuron> list = dnnOutputLayer.getNeurons();
        DnnOutputNeuron dnnOutputNeuron;
        for (int i=0;i< list.size();i++){
            dnnOutputNeuron = list.get(i);
            String outId = dnnOutputNeuron.getId();
            Circle circle = new Circle(outId , r, "green");

            circle.setMt(50);
            if(i==0){
                circle.setMl(55);
            }
            //半径

            //圆心的文本 , net 单元
            circle.addCurrentComponent(addSubAndSup(new Text(dnnOutputNeuron.getSumTextId(),-r/2 ,0,
                    dnnOutputNeuron.getSumText() ,ShowTextPositionEnum.MIDDLE.getCode() ) ,layerNo.toString(),(i+1)+"" ));

            //圆心的文本 ,  net 单元的输出值
            circle.addCurrentComponent(new Text(dnnOutputNeuron.getSumValueTextId(), -r/2 , r + VALUE_BUFFER,
                    dnnOutputNeuron.getSumValueText(),ShowTextPositionEnum.MIDDLE.getCode()));

            //圆心的文本 , out 单元
            circle.addCurrentComponent(addSubAndSup(new Text(dnnOutputNeuron.getActivationTextId(),r/2,0,
                    dnnOutputNeuron.getActivationText(),ShowTextPositionEnum.MIDDLE.getCode()) ,layerNo.toString(),(i+1)+"" ));

            circle.addCurrentComponent(new Text(dnnOutputNeuron.getActivationValueTextId(),r/2,r + VALUE_BUFFER,
                    dnnOutputNeuron.getActivationValueText(),ShowTextPositionEnum.MIDDLE.getCode()));

            //圆心的线
            circle.addCurrentComponent(new Line(LineTypeEnum.POSITION.getCode(),0,-r,0,r));

            //实际值
            circle.addCurrentComponent(new Text(dnnOutputNeuron.getActualTextId() ,r + VALUE_BUFFER,0,
                    dnnOutputNeuron.getActualText(),ShowTextPositionEnum.START.getCode()));

            circle.addCurrentComponent(new Text(JUUID.getUUID() ,r + new Double(VALUE_BUFFER*1.8).intValue(),0,
                    "=",ShowTextPositionEnum.START.getCode()));

            circle.addCurrentComponent(new Text(dnnOutputNeuron.getActualValueTextId() ,r + ERROR_BUFFER*2,0,
                    dnnOutputNeuron.getActualValueText(),ShowTextPositionEnum.START.getCode()));

            //损失函数
            circle.addCurrentComponent(new Text(dnnOutputNeuron.getCostTextId() ,r+ VALUE_BUFFER,r +VALUE_BUFFER,
                    dnnOutputNeuron.getCostText(),ShowTextPositionEnum.START.getCode()));

            circle.addCurrentComponent(new Text(dnnOutputNeuron.getCostValueTextId() ,r + ERROR_BUFFER*3,r +VALUE_BUFFER,
                    dnnOutputNeuron.getCostValueText(),ShowTextPositionEnum.START.getCode()));
//            cost.setD(dnnOutputNeuron.getIndex());
//            circle.addCurrentComponent(cost);

            group.addChild(circle);
        }



        return group;
    }

    /****
     * 创建 层与层之间的线,权重的线
     * @param dnnConstructParam
     * @return
     */
    private Group lineList(DnnConstructParam dnnConstructParam){
        Group group = new Group();

        List<DnnHiddenLayer> hiddenList = dnnConstructParam.getDnnHiddenLayerList();

        //输入层的输入单元列表
        List<DnnInputNeuron> inputNeuronList = dnnConstructParam.getDnnInputLayer().getNeurons();

        //隐藏层的首层列表
        List<DnnHiddenNeuron> firstHiddenNeuronList = hiddenList.get(0).getNeurons();

        //隐藏层的最后一层列表
        List<DnnHiddenNeuron> lastHiddenNeuronList = hiddenList.get(hiddenList.size()-1).getNeurons();

        //输出层的单元列表
        List<DnnOutputNeuron> outputNeuronList = dnnConstructParam.getOutputLayer().getNeurons();

        //创建输入层到第一个隐藏层的线
        DnnBiasNeuron inputBias = dnnConstructParam.getDnnInputLayer().getDnnBiasNeuron();
        for (DnnHiddenNeuron dhn : firstHiddenNeuronList){
            for (DnnInputNeuron din : inputNeuronList){
                Line l = this.createLine(din.getId() ,din.getIndex() ,dhn.getId(), dhn.getIndex() , 1);
                group.addChild(l);
            }

//            Line l = this.createLine(inputBias.getId() ,0 ,dhn.getId(), dhn.getIndex() , 1);

//            group.addChild(l);
        }

        // 创建隐藏层到隐藏层的线，但是暂时没有
        if(hiddenList.size()>1){

        }

        //创建最后一个隐藏层到输出层的线
        for (DnnHiddenNeuron dhn: lastHiddenNeuronList){
            for (DnnOutputNeuron don : outputNeuronList){
                Line l = this.createLine(dhn.getId(), dhn.getIndex() , don.getId() ,don.getIndex() ,hiddenList.size() + 1);
                group.addChild(l);
            }
        }


//        for (int i = 0 ; i<tid.size() ; i++){
//            String t = tid.get(i);
//            for (int j = 0 ; j < sid.size() ; j ++ ){
//                String s = sid.get(j);
//                Line l = this.createLine(s ,j,t,i , layerNo);
//                group.addChild(l);
//            }
//        }
        return group;
    }

    private Line createLine(String s ,int sindex ,  String t ,int tindex, int layerNo){
        Line l = new Line();
        l.setId(s +"-"+ t);
        l.setSid(s);
        l.setTid(t);
        l.setLpt(new Integer[]{LinePositionEnum.RADIUS.getCode(),
                LinePositionEnum.CIRCLE_CENTER.getCode(),
                LinePositionEnum.RADIUS.getCode(),
                LinePositionEnum.CIRCLE_CENTER.getCode()});


        //线上的文本
        Text text = new Text();
        //权重的id
        text.setId(super.getNeuronToNeuronWeightId(s,t));
        text.setSt(super.getWeightText(""));
        text.setX(0);
        text.setY(0);
        text.setRatio(ratio);
        text.setSts(ShowTextPositionEnum.MIDDLE.getCode());

        //增加上下标
        text = this.addSubAndSup(text, layerNo + "" , (tindex)+""+(sindex));

        //线上的等号文本
        Text textEqual = new Text();
        //权重的id
        textEqual.setId(super.getNeuronToNeuronWeightId(s,t));
        textEqual.setSt("=");
        textEqual.setX(0);
        textEqual.setY(0);
        textEqual.setMl(25);
        textEqual.setRatio(ratio);
        textEqual.setSts(ShowTextPositionEnum.MIDDLE.getCode());

        //线上的文本
        Text textValue = new Text();
        textValue.setId(super.getNeuronToNeuronWeightValue(s,t));
        textValue.setSt(new DecimalFormat("#0.00").format(Math.random()) );
        textValue.setMl(50);
        textValue.setRatio(ratio);
        textValue.setSts(ShowTextPositionEnum.MIDDLE.getCode());

        l.addCurrentComponent(text);

        l.addCurrentComponent(textEqual);

        l.addCurrentComponent(textValue);

        return l ;
    }

    /****
     * 增加上下标
     * @param t
     * @param sup 上标
     * @param sub 下标
     * @return
     */
//    private Text addSubAndSup(Text t , String sup , String sub){
//        if(sub != null){
//            t.addChild(new TSPAN(sub,BaselineShiftEnum.LINE_SUB.getCode() , 0 , 0 ));
//        }
//        if(sup!=null){
//            t.addChild(new TSPAN(sup, BaselineShiftEnum.LINE_SUPER.getCode() , WEIGHT_SUB_BUFFER ,0));
//        }
//        return t ;
//    }
}
