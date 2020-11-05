package com.sxyl.portal.domain.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.constant.ComponentTypeEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/****
 * 圆的对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Circle extends GraphComponent implements Serializable {
    private static final long serialVersionUID = 1L;



    /****
     * x 的位置
     */
    private Integer x;

    /****
     * y 的位置
     */
    private Integer y;

    /****
     * 圆的半径
     * radius
     */
    private Integer r ;

    /***
     * 边框
     * stroke
     */
    private String s ;

    /****
     * 填充颜色
     * fill
     */
    private String f;

    public Circle() {
    }


    public Circle(String id ,Integer r, String s) {
        this.setId(id);
        this.r = r;
        this.s = s;
    }


    public Circle(String id ,Integer x, Integer y) {
        this.setId(id);
        this.x = x;
        this.y = y;
    }

    public Circle(String id ,Integer r, String s, String f) {
        this.setId(id);
        this.r = r;
        this.s = s;
        this.f = f;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
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

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.CIRCLE.getType());
    }
}
