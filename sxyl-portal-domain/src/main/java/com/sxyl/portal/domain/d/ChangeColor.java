package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

public class ChangeColor extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 要变更的ids的数组
     */
    private String[] ids;

    /***
     * 要变更的颜色,如果设置此字段,则所有id ,都会变更为此颜色,如果需要各个元素赋值,则使用 colors 数组
     */
    private String totalColor;

    /****
     * 要变更的颜色数组
     */
    private String[] colors;

    public ChangeColor(){

    }


    public ChangeColor( String totalColor,String... ids ){
        this.totalColor = totalColor ;
        this.ids = ids;
    }







    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getTotalColor() {
        return totalColor;
    }

    public void setTotalColor(String totalColor) {
        this.totalColor = totalColor;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.CHANGE_COLOR.getType());
    }
}
