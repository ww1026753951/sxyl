package com.sxyl.portal.service.nn.impl;

import com.sxyl.portal.domain.constant.*;
import com.sxyl.portal.domain.graph.*;
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


    @Override
    public Group getDnnConstruct(List<String> inputIds,List<List<String>> hiddenIds,List<String> outputIds) {
        //创建输入层
        Group groupInput =  circleInput(inputIds);

        //创建隐藏层
        Group circleHidden = circleHidden(hiddenIds);

        //创建输出层
        Group circleOutput = circleOutput(outputIds);

        Group all = new Group();

        all.setMl(100);
        all.setMt(200);

        all.setCompose(ComponentCompositeEnum.VERTICAL.getType());

        all.addChild(groupInput);
        all.addChild(circleHidden);

        all.addChild(circleOutput);

        //误差项的值
        all.addChild(createError(outputIds));


        int start = 0;
        //todo
        all.addChild(lineList(inputIds,hiddenIds.get(0) ,start));
        start = start + inputIds.size()* hiddenIds.get(0).size();
        all.addChild(lineList(hiddenIds.get(0),outputIds,start));


        return all;
    }




    /****
     * 创建输入层的圆心
     * @return
     */
    private Group circleInput(List<String> inputIds){
        int r = 50 ;
        Group group = new Group();
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        for (int i=0;i< inputIds.size();i++){
            Circle circle = new Circle();
            circle.setId(inputIds.get(i));
            if(i==0){
                circle.setMl(55);
                circle.setMt(50);
            }else if(i == inputIds.size() - 1){//最后一位为 bias ,所以间距设置多一些
                circle.setMt(60);
            }else {
                circle.setMt(80);
            }
            //半径
            circle.setR(r);
            circle.setS("red");

            //圆心的文本
            Text t = new Text();
            t.setId(super.getTextId(inputIds.get(i), super.INPUT_ID_SUFFIX));
            t.setSt(super.getInputText(new Integer(i+1).toString()));
            t.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            t.setX(0);
            t.setY(0);
            circle.addCurrentComponent(t);

            Text tBottom = new Text();
            tBottom.setId(super.getHiddenBottomOutId(inputIds.get(i)));
            tBottom.setSt(new Integer(i+1).toString());
            tBottom.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            tBottom.setX(0);
            tBottom.setY(r + VALUE_BUFFER);
            circle.addCurrentComponent(tBottom);

            group.addChild(circle);
        }
        return group;
    }



    /****
     * 创建隐藏层的结构
     * @return
     */
    private Group circleHidden(List<List<String>> hiddenIds){


        int r = 70;
        Group group = new Group();
        group.setMl(350);
        group.setMt(-50);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());

        for (int x = 0;x< hiddenIds.size();x++){
            for (int i=0;i< hiddenIds.get(x).size();i++){
                Circle circle = new Circle();
                circle.setId(hiddenIds.get(x).get(i));
                if(i==0){
                    circle.setMl(55);
                    circle.setMt(50);
                }else {

                    circle.setMt(60);
                }
                //半径
                circle.setR(r);
                circle.setS("blue");

                //圆心的文本 , net 单元
                Text left = new Text();
                left.setId(super.getTextId(hiddenIds.get(x).get(i) , super.NET_ID) );
                left.setSt(super.getHiddenNetText(new Integer(i+1).toString()));
                left.setX(-r/2);
                left.setY(0);
                left.setSts(ShowTextPositionEnum.MIDDLE.getCode());
                circle.addCurrentComponent(left);


                //圆心的文本 ,  net 单元的输出值
                Text leftValue = new Text();
                leftValue.setId(super.getHiddenBottomNetId(hiddenIds.get(x).get(i)));
                leftValue.setSt("n"+(i+1));
                leftValue.setX(-r/2);
                leftValue.setY(r + VALUE_BUFFER);
                leftValue.setSts(ShowTextPositionEnum.MIDDLE.getCode());
                circle.addCurrentComponent(leftValue);


                //圆心的文本 , out 单元
                Text right = new Text();
                right.setId(super.getTextId(hiddenIds.get(x).get(i), super.OUT_ID));
                right.setSt(getHiddenOutText(new Integer(i+1).toString()));
                right.setX(r/2);
                right.setY(0);
                right.setSts(ShowTextPositionEnum.MIDDLE.getCode());
                circle.addCurrentComponent(right);


                Text rightValue = new Text();
                rightValue.setId(super.getHiddenBottomOutId(hiddenIds.get(x).get(i)));
                rightValue.setSt("o"+(i+1));
                rightValue.setX(r/2);
                rightValue.setY(r + VALUE_BUFFER);
                rightValue.setSts(ShowTextPositionEnum.MIDDLE.getCode());
                circle.addCurrentComponent(rightValue);

                //圆心的线
                Line l = new Line();
                l.setId("hidden"+(i+1));
                l.setLt(LineTypeEnum.POSITION.getCode());
                l.setX1(0);
                l.setY1(-r);
                l.setX2(0);
                l.setY2(r);
                circle.addCurrentComponent(l);
                group.addChild(circle);
            }

        }


        return group;
    }

    /****
     * 创建输出层的结构
     * @return
     */
    private Group circleOutput(List<String> outputIds){

        int r = 60;
        Group group = new Group();
        group.setMl(350);
        group.setMt(40);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        for (int i=0;i< outputIds.size();i++){
            String outId = outputIds.get(i);
            Circle circle = new Circle();
            circle.setId(outputIds.get(i));
//            circle.setSt("O"+(i+1));
            if(i==0){
                circle.setMl(55);
                circle.setMt(50);
            }else{
                circle.setMt(60);
            }
            //半径
            circle.setR(r);
            circle.setS("blue");

            //圆心的文本 , net 单元
            Text left = new Text();
            left.setId(super.getTextId(outId , super.NET_ID) );
            left.setSt(super.getOutNetText(new Integer(i+1).toString()));
            left.setX(-r/2);
            left.setY(0);
            left.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            circle.addCurrentComponent(left);


            //圆心的文本 ,  net 单元的输出值
            Text leftValue = new Text();
            leftValue.setId(super.getHiddenBottomNetId(outId));
            leftValue.setSt("n"+(i+1));
            leftValue.setX(-r/2);
            leftValue.setY(r + VALUE_BUFFER);
            leftValue.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            circle.addCurrentComponent(leftValue);


            //圆心的文本 , out 单元
            Text right = new Text();
            right.setId(super.getTextId(outId, super.OUT_ID));
            right.setSt(super.getOutOutText(new Integer(i+1).toString()));
            right.setX(r/2);
            right.setY(0);
            right.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            circle.addCurrentComponent(right);


            Text rightValue = new Text();
            rightValue.setId(super.getHiddenBottomOutId(outId));
            rightValue.setSt("o"+(i+1));
            rightValue.setX(r/2);
            rightValue.setY(r + VALUE_BUFFER);
            rightValue.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            circle.addCurrentComponent(rightValue);

            //圆心的线
            Line l = new Line();
            l.setId("hidden"+(i+1));
            l.setLt(LineTypeEnum.POSITION.getCode());
            l.setX1(0);
            l.setY1(-r);
            l.setX2(0);
            l.setY2(r);
            circle.addCurrentComponent(l);
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
        group.setMt(0);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        for (int i=0;i< outputIds.size();i++){
            String errorId = super.getErrorTextId(outputIds.get(i));
            Circle circle = new Circle();
            circle.setId(errorId);
            if(i==0){
                circle.setMl(55);
                circle.setMt(50);
            }else{
                circle.setMt(60);
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
    private Group lineList(List<String> sid,List<String> tid ,int i){
        Group group = new Group();
        for (String t:tid){
            for (String s: sid){
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
                text.setSt(super.getWeightText(new Integer((i++)+1).toString()));
                text.setX(0);
                text.setY(0);
                text.setRatio(ratio);
                text.setSts(ShowTextPositionEnum.MIDDLE.getCode());


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
