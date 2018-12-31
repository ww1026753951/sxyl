package com.sxyl.portal.service.nn.impl;

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

    private double ratio = 1.3D;

    //数值的 缓冲值
    private int VALUE_BUFFER = 20;

    //权重的上下标的buffer
    private int WEIGHT_SUB_BUFFER = -10 ;


    @Override
    public Group getDnnConstruct(List<String> inputIds,List<List<String>> hiddenIds,List<String> outputIds, DnnConstructParam dnnConstructParam) {
        //创建输入层 inputIds
        Group groupInput =  circleInput(dnnConstructParam.getDnnInputLayer());
        //创建隐藏层  hiddenIds
        Group circleHidden = circleHidden(dnnConstructParam.getDnnHiddenLayerList());
        //创建输出层 outputIds ,
        Group circleOutput = circleOutput(dnnConstructParam.getOutputLayer());
        Group all = new Group();
        all.setMl(80);
        all.setMt(120);
        all.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        all.addChild(groupInput);
        all.addChild(circleHidden);
        all.addChild(circleOutput);
        //误差项的值
        all.addChild(createError(outputIds));

        //todo
        all.addChild(lineList(inputIds,hiddenIds.get(0) ,1));
//        start = start + inputIds.size()* hiddenIds.get(0).size();
        all.addChild(lineList(hiddenIds.get(0),outputIds,hiddenIds.size() + 1));


        return all;
    }




    /****
     * 创建输入层的圆心
     *
     * List<String> inputIdsa ,
     * @return
     */
    private Group circleInput(DnnInputLayer dnnInputLayer){
        int r = 40 ;
        Group group = new Group();
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());

        List<DnnInputNeuron> list = dnnInputLayer.getNeurons();
        for (int i=0;i< list.size();i++){
            DnnInputNeuron dnnInputNeuron = list.get(i);

            Circle circle = new Circle(dnnInputNeuron.getId(), r ,"red");
//            circle.setId();
            circle.setMt(40);

            if(i==0){
                circle.setMl(35);
//                circle.setMt(30);
//            }else if(i == inputIds.size() - 1){//最后一位为 bias ,所以间距设置多一些
//                circle.setMt(50);
//            }else {
//                circle.setMt(50);
            }
            //半径
//            circle.setR(r);
//            circle.setS("red");

            //圆心的文本
            circle.addCurrentComponent(new Text(dnnInputNeuron.getTextId() , 0,0,dnnInputNeuron.getText() ,ShowTextPositionEnum.MIDDLE.getCode() ));

            circle.addCurrentComponent(new Text(dnnInputNeuron.getValueTextId(), 0,r + VALUE_BUFFER,
                    dnnInputNeuron.getValueText(),ShowTextPositionEnum.MIDDLE.getCode()));

            group.addChild(circle);
        }

        return group;
    }



    /****
     * 创建隐藏层的结构   List<List<String>> hiddenIds ,
     * @return
     */
    private Group circleHidden(List<DnnHiddenLayer> hiddenLayers){

        int r = 60;
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
                circle.setMt(30);
                if(j==0){
                    circle.setMl(35);
                }

                //圆心的文本 , net 单元 ,sum的文本
                circle.addCurrentComponent(new Text(dnnHiddenNeuron.getSumTextId(),-r/2,0,
                        dnnHiddenNeuron.getSumText(),ShowTextPositionEnum.MIDDLE.getCode()));

                //圆心的文本 ,  net 单元的输出值
                circle.addCurrentComponent(new Text(dnnHiddenNeuron.getSumValueTextId() , -r/2 , r + VALUE_BUFFER,
                        dnnHiddenNeuron.getSumValueText() , ShowTextPositionEnum.MIDDLE.getCode()));

                //圆心的文本 , out 单元
                circle.addCurrentComponent(new Text(dnnHiddenNeuron.getActivationTextId(),r/2,0,
                        dnnHiddenNeuron.getActivationText(),ShowTextPositionEnum.MIDDLE.getCode()));

                circle.addCurrentComponent(new Text(dnnHiddenNeuron.getActivationValueTextId(),r/2,r + VALUE_BUFFER,
                        dnnHiddenNeuron.getActivationValueText(),ShowTextPositionEnum.MIDDLE.getCode()));

                //圆心的线
                circle.addCurrentComponent(new Line(LineTypeEnum.POSITION.getCode(),0,-r,0,r));
                group.addChild(circle);
            }
        }
        return group;
    }

    /****
     * 创建输出层的结构  List<String> outputIdsc ,
     * @return
     */
    private Group circleOutput( DnnOutputLayer dnnOutputLayer){

        int r = 60;
        Group group = new Group();
        group.setMl(300);
        group.setMt(-20);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());


        List<DnnOutputNeuron> list = dnnOutputLayer.getNeurons();
        DnnOutputNeuron dnnOutputNeuron;
        for (int i=0;i< list.size();i++){
            dnnOutputNeuron = list.get(i);
            String outId = dnnOutputNeuron.getId();
            Circle circle = new Circle(outId , r, "blue");
//            circle.setId(outId);
//            circle.setSt("O"+(i+1));

            circle.setMt(50);
            if(i==0){
                circle.setMl(55);
//            }else{
//                circle.setMt(60);
            }
            //半径

            //圆心的文本 , net 单元
            circle.addCurrentComponent(new Text(dnnOutputNeuron.getSumTextId(),-r/2 ,0,
                    dnnOutputNeuron.getSumText() ,ShowTextPositionEnum.MIDDLE.getCode() ));

            //圆心的文本 ,  net 单元的输出值
            circle.addCurrentComponent(new Text(dnnOutputNeuron.getSumValueTextId(), -r/2 , r + VALUE_BUFFER,
                    dnnOutputNeuron.getSumValueText(),ShowTextPositionEnum.MIDDLE.getCode()));

            //圆心的文本 , out 单元
            circle.addCurrentComponent(new Text(dnnOutputNeuron.getActivationTextId(),r/2,0,
                    dnnOutputNeuron.getActivationText(),ShowTextPositionEnum.MIDDLE.getCode()));

            circle.addCurrentComponent(new Text(dnnOutputNeuron.getActivationValueTextId(),r/2,r + VALUE_BUFFER,
                    dnnOutputNeuron.getActivationValueText(),ShowTextPositionEnum.MIDDLE.getCode()));

            //圆心的线
//            Line l = ;
//            l.setId("hidden"+(i+1));
//            l.setLt();
//            l.setX1(0);
//            l.setY1(-r);
//            l.setX2(0);
//            l.setY2(r);
            circle.addCurrentComponent(new Line(LineTypeEnum.POSITION.getCode(),0,-r,0,r));
            group.addChild(circle);
        }



        return group;
    }


    /***
     * 创建误差值
     * @param outputIds
     * @return
     */
    private Group createError(List<String> outputIds){

        int r = 60;
        Group group = new Group();
        group.setMl(100);
        group.setMt(10);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        for (int i=0;i< outputIds.size();i++){
            String errorId = super.getErrorTextId(outputIds.get(i));
            Circle circle = new Circle();
            circle.setId(errorId);

            circle.setMt(50);
            if(i==0){
                circle.setMl(55);
            }
            //半径
            circle.setR(r);
            circle.setS("green");

            //圆心的文本 , net 单元
            Text t = new Text();
            t.setId(super.getTextId(errorId , super.NET_ID) );
            t.setSt(super.getTargetNetText(new Integer(i+1).toString()));
            t.setX(0);
            t.setY(0);
            t.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            circle.addCurrentComponent(t);


            //圆心的文本 , 底部的损失函数
            Text tBottom = new Text();
            tBottom.setId(super.getHiddenBottomOutId(errorId) );
            tBottom.setSt(super.getErrorText(new Integer(i+1).toString())+"=???");
            tBottom.setD(new Integer(i+1));
            tBottom.setX(0);
            tBottom.setY(r + VALUE_BUFFER);
            tBottom.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            circle.addCurrentComponent(tBottom);


            group.addChild(circle);
        }
        return group;

    }


    /****
     * 创建 层与层之间的线,权重的线
     * @param sid
     * @param tid
     * @return
     */
    private Group lineList(List<String> sid,List<String> tid ,int layerNo){
        Group group = new Group();
        for (int i = 0 ; i<tid.size() ; i++){
            String t = tid.get(i);
            for (int j = 0 ; j < sid.size() ; j ++ ){
                String s = sid.get(j);
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

                text.addChild(new TSPAN("" + layerNo , BaselineShiftEnum.LINE_SUPER.getCode() , 0 ,5));
                text.addChild(new TSPAN((i+1)+""+(j+1),BaselineShiftEnum.LINE_SUB.getCode() , WEIGHT_SUB_BUFFER , -5 ));
//                text.addCurrentComponent()

                //线上的文本
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
                textValue.setSt(new DecimalFormat("#.00").format(Math.random()) );
                textValue.setMl(50);
                textValue.setRatio(ratio);
                textValue.setSts(ShowTextPositionEnum.MIDDLE.getCode());

                l.addCurrentComponent(text);

                l.addCurrentComponent(textEqual);

                l.addCurrentComponent(textValue);

                group.addChild(l);
            }
        }
        return group;
    }
}
