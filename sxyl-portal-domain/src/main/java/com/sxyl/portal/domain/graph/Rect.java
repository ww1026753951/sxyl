package com.sxyl.portal.domain.graph;

import com.sxyl.portal.domain.constant.ComponentTypeEnum;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.graph
 * @date:2020/9/17
 */
public class Rect  extends GraphComponent implements Serializable {

    /***
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /***
     * 方框的宽度
     */
    private Integer width;

    /***
     * 方框的高度
     */
    private Integer height ;

    /***
     * 填充颜色的字段
     */
    private String fill ;

    /***
     * 边框的颜色
     */
    private String stroke;

//    /***
//     * 高度
//     */
//    private Integer t;


    private Integer x;

    private Integer y;



    public Rect(String id , Integer width , Integer height, Integer x,Integer y , String fill) {
        super.setId(id);
        this.width = width ;
        this.height = height;
        this.x = x;
        this.y = y ;
        this.fill = fill ;
    }


    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.RECT.getType());
    }
}
