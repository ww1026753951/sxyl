package com.sxyl.portal.service;

import com.sxyl.portal.domain.constant.DisplayEnum;
import com.sxyl.portal.domain.constant.LinePositionEnum;
import com.sxyl.portal.domain.graph.Line;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service
 * @date:2019/6/22
 */
public class CommonService {


    /***
     * 根据起始和截止， 创建线
     * @param s
     * @param t
     * @return
     */
    protected Line createLine(String s  , String t , boolean h){
        Line l = new Line();
        l.setId(s +"-"+ t);
        l.setSid(s);
        l.setTid(t);
        if(h){
            l.setH(DisplayEnum.NONE.getContent());
        }
        l.setLpt(new Integer[]{LinePositionEnum.CIRCLE_CENTER.getCode(),
                LinePositionEnum.RADIUS.getCode(),
                LinePositionEnum.CIRCLE_CENTER.getCode(),
                LinePositionEnum.RADIUS.getCode()});

        return l ;
    }
}
