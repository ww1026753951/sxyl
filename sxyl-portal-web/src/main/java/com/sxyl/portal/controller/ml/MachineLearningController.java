package com.sxyl.portal.controller.ml;

import com.sxyl.portal.controller.BaseController;
import com.sxyl.portal.domain.constant.ComponentCompositeEnum;
import com.sxyl.portal.domain.constant.LinePositionEnum;
import com.sxyl.portal.domain.graph.Circle;
import com.sxyl.portal.domain.graph.GraphComponent;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.graph.Line;
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
        Group group =  circleList();

        Group group1 = circleList1();

        Group group2 = circleList2();

        Group all = new Group();

        all.setMl(100);
        all.setMt(100);

        all.setCompose(ComponentCompositeEnum.VERTICAL.getType());

        all.addChild(group);
        all.addChild(group1);

        all.addChild(group2);


        //线的图形
        List inputIds = group.getChild().stream().map(GraphComponent::getId).collect(Collectors.toList());
        List hiddenIds = group1.getChild().stream().map(GraphComponent::getId).collect(Collectors.toList());
        List outputIds = group2.getChild().stream().map(GraphComponent::getId).collect(Collectors.toList());


        all.addChild(lineList(inputIds,hiddenIds));
        all.addChild(lineList(hiddenIds,outputIds));
        return all;
    }


    /****
     * 展示圆形结构
     * @return
     */
    private Group circleList(){

        Group group = new Group();
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        group.setSt("INPUT LAYER1");
        for (int i=0;i< 3;i++){
            Circle circle = new Circle();
            circle.setSt("X"+(i+1));
            circle.setId("inputLayer1-"+i);
            if(i==0){
                circle.setMl(55);
                circle.setMt(50);
            }
            //半径
            circle.setR(50);
            circle.setS("red");
            group.addChild(circle);
        }
        return group;
    }



    /****
     * 展示圆形结构
     * @return
     */
    private Group circleList1(){

        Group group = new Group();
        group.setMl(150);
        group.setMt(-50);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        group.setSt("HIDDEN LAYER");
        for (int i=0;i< 4;i++){
            Circle circle = new Circle();
            circle.setSt("Y"+(i+1));
            circle.setId("hiddenLayer1-"+i);
            if(i==0){
                circle.setMl(55);
                circle.setMt(50);
            }
            //半径
            circle.setR(50);
            circle.setS("blue");
            group.addChild(circle);
        }
        return group;
    }

    private Group circleList2(){

        Group group = new Group();
        group.setSt("OUTPUT LAYER");
        group.setMl(150);
        group.setMt(50);
        group.setCompose(ComponentCompositeEnum.VERTICAL.getType());
        for (int i=0;i< 3;i++){
            Circle circle = new Circle();
            circle.setId("outputLayer1-"+i);
            circle.setSt("Z"+(i+1));
            if(i==0){
                circle.setMl(55);
                circle.setMt(50);
            }
            //半径
            circle.setR(50);
            circle.setS("blue");
            group.addChild(circle);
        }
        return group;
    }


    private Group lineList(List<String> sid,List<String> tid){
        Group group = new Group();
        for (String s: sid){
            for (String t:tid){
                Line l = new Line();
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

