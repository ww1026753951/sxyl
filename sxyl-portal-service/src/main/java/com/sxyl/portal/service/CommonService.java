package com.sxyl.portal.service;

import com.sxyl.portal.domain.BaseMove;
import com.sxyl.portal.domain.common.CommonAdapterScreen;
import com.sxyl.portal.domain.constant.*;
import com.sxyl.portal.domain.graph.Line;


/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service
 * @date:2019/6/22
 */
public class CommonService {


    //元素和元素之间的 buffer
    protected final int ACTIVE_COLUMN_BUFFER = 10;

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

    //创建方框的移动

    /****
     *
     * @param topEnum 顶部的枚举,表示元素放置底部所在矩形 上部还是底部
     * @param leftEnum 左侧的枚举,表示元素放置左侧所在矩形 左侧还是右侧
     * @param backX 底部矩形的x
     * @param backY 底部矩形的y
     * @param backWidth 底部矩形的 宽
     * @param backHeight 底部矩形的 高
     * @param rowIndex 行的下标
     * @param columnIndex 列的下标
     * @param elementWidth 底部元素的宽
     * @param elementHeight 底部元素的高
     * @return
     */
    private BaseMove createBaseMove ( TopEnum topEnum , LeftEnum leftEnum ,
                                             int backX , int backY , int backWidth , int backHeight,
                                             int rowIndex , int columnIndex,
                                             int elementWidth , int elementHeight){


        //核心线程的方框 +  核心线程的高度

        BaseMove move = new BaseMove();

        int x = backX ;
        int y =  backY ;

        if (leftEnum.getType() == LeftEnum.RIGHT.getType()) {
            x = x + backWidth;
        }
        //如果是右侧,则
        if (leftEnum.getType() == LeftEnum.RIGHT.getType()) {
            x = x -  (columnIndex * elementWidth) - elementWidth - ( (columnIndex+1) *ACTIVE_COLUMN_BUFFER);
        }else {
            x = x +  (columnIndex * elementWidth) + elementWidth + ((columnIndex+1) *ACTIVE_COLUMN_BUFFER);
        }
        //如果底部,则将高度加上 底部矩形的
        if (TopEnum.BOTTOM.getType() == topEnum.getType()) {
            y = y +  backHeight;
        }

        if (TopEnum.BOTTOM.getType() == topEnum.getType()) {
            y =  backY  + ( rowIndex * elementHeight)  + ( rowIndex * elementHeight )  ;
        }else {
            y =  backY  - ( rowIndex * elementHeight)   -  ( rowIndex * elementHeight )  ;
        }

        move.setX(x);
        move.setY(y);


        return move;

    }


    /****
     * 计算 xy
     * @param screenX 屏幕的x
     * @param screenY 屏幕的y
     * @return
     */
    protected CommonAdapterScreen computeXY( int screenX, int screenY,int componentX , int componentY){
        CommonAdapterScreen commonAdapterScreen = new CommonAdapterScreen();

        int adapterScreenEnumCode= computeHorizontalOrVertical(screenX , screenY);
        int x=0;int y = 0 ;
        //竖屏幕  宽大于高
        if (adapterScreenEnumCode == AdapterScreenEnum.VERTICAL.getCode()){
            x = screenX/2 - componentX;
            y = screenX/5;
        }
        //横屏  宽小于高
        if (adapterScreenEnumCode == AdapterScreenEnum.HORIZONTAL.getCode()){

        }


        commonAdapterScreen.setX(x);
        commonAdapterScreen.setY(y);

        return commonAdapterScreen;

    }

    private int computeHorizontalOrVertical(int screenX, int screenY){

        if (screenX >= screenY){
            return AdapterScreenEnum.VERTICAL.getCode();
        }
        if(screenX < screenY){

            return AdapterScreenEnum.HORIZONTAL.getCode();
        }
        return 0;


    }


}
