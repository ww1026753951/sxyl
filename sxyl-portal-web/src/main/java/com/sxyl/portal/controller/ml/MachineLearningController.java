package com.sxyl.portal.controller.ml;

import com.sxyl.portal.controller.BaseController;
import com.sxyl.portal.domain.constant.*;
import com.sxyl.portal.domain.graph.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller()
@RequestMapping("/ml")
public class MachineLearningController extends BaseController {


    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("/dnnConstruct")
    @ResponseBody
    public Object dnnConstruct() throws Exception{
        Group groupInput =  circleInput();

        Group circleHidden = circleHidden();

        Group circleOutput = circleOutput();

        Group all = new Group();

        all.setMl(100);
        all.setMt(100);

        all.setCompose(ComponentCompositeEnum.VERTICAL.getType());

        all.addChild(groupInput);
        all.addChild(circleHidden);

        all.addChild(circleOutput);


        //线的图形
        List inputIds = groupInput.getChild().stream().map(GraphComponent::getId).collect(Collectors.toList());
//        List hiddenIds = circleHidden.getChild().stream().map(GraphComponent::getId).collect(Collectors.toList());
        List hiddenIds = new ArrayList();

        for (GraphComponent gc: circleHidden.getChild()){
            if(gc.getCt()== ComponentTypeEnum.CIRCLE.getType()){
                hiddenIds.add(gc.getId());
            }
        }

        List outputIds = circleOutput.getChild().stream().map(GraphComponent::getId).collect(Collectors.toList());


        all.addChild(lineList(inputIds,hiddenIds));
        all.addChild(lineList(hiddenIds,outputIds));
        return all;
    }


    /****
     * 展示圆形结构
     * @return
     */
    private Group circleInput(){

        int circleNum = 3 ;

        Group group = new Group();
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
//        group.setSt("INPUT LAYER1");
        for (int i=0;i< circleNum;i++){
            Circle circle = new Circle();
//            circle.setSt("I"+(i+1));
            circle.setId("inputLayer1-"+i);
            if(i==0){
                circle.setMl(55);
                circle.setMt(50);
            }else if(i == circleNum - 1){//最后一位为 bias ,所以间距设置多一些
                circle.setMt(80);
//                circle.setSt("B");
            }else {
                circle.setMt(20);
            }
            //半径
            circle.setR(50);
            circle.setS("red");

            //圆心的文本
            Text t = new Text();
            t.setSt("I"+(i+1));
            t.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            t.setX(0);
            t.setY(0);
            circle.addCurrentComponent(t);



            group.addChild(circle);
        }
        return group;
    }



    /****
     * 展示圆形结构
     * @return
     */
    private Group circleHidden(){

        int r = 70;
        Group group = new Group();
        group.setMl(350);
        group.setMt(-50);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
//        group.setSt("HIDDEN LAYER");
        for (int i=0;i< 3;i++){
            Circle circle = new Circle();
//            circle.setSt("H"+(i+1));
            circle.setId("hiddenLayer1-"+i);
            if(i==0){
                circle.setMl(55);
                circle.setMt(50);
            }else {

                circle.setMt(30);
            }
            //半径
            circle.setR(r);
            circle.setS("blue");

            //圆心的文本
            Text left = new Text();
            left.setSt("net"+(i+1));
            left.setX(-r/2);
            left.setY(0);
            left.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            circle.addCurrentComponent(left);



            Text right = new Text();
            right.setSt("out"+(i+1));
            right.setX(r/2);
            right.setY(0);
            right.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            circle.addCurrentComponent(right);

            //圆心的线
            Line l = new Line();
            l.setId("hidden"+(i+1));
            l.setLt(LineTypeEnum.POSITION.getCode());
            l.setX1(0);
            l.setY1(-r);
            l.setX2(0);
            l.setY2(r);
//            group.addChild(l);

            circle.addCurrentComponent(l);

            group.addChild(circle);



        }
        return group;
    }

    private Group circleOutput(){

        Group group = new Group();
//        group.setSt("OUTPUT LAYER");
        group.setMl(350);
        group.setMt(50);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        for (int i=0;i< 3;i++){
            Circle circle = new Circle();
            circle.setId("outputLayer1-"+i);
//            circle.setSt("O"+(i+1));
            if(i==0){
                circle.setMl(55);
                circle.setMt(50);
            }
            //半径
            circle.setR(50);
            circle.setS("blue");

            //圆心的文本
            Text t = new Text();
            t.setSt("O"+(i+1));
            t.setX(0);
            t.setY(0);
            t.setSts(ShowTextPositionEnum.MIDDLE.getCode());
            circle.addCurrentComponent(t);

            group.addChild(circle);
        }
        return group;
    }


    private Group lineList(List<String> sid,List<String> tid){
        Group group = new Group();
        for (String s: sid){
            for (String t:tid){
                Line l = new Line();
                l.setId(s + t);
                l.setSid(s);
                l.setTid(t);
                l.setLpt(new Integer[]{LinePositionEnum.RADIUS.getCode(),
                        LinePositionEnum.CIRCLE_CENTER.getCode(),
                        LinePositionEnum.RADIUS.getCode(),
                        LinePositionEnum.CIRCLE_CENTER.getCode()});
                group.addChild(l);
            }
        }
        return group;
    }
}

