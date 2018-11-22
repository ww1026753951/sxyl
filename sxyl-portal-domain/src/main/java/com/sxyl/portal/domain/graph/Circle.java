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

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.CIRCLE.getType());
    }
}
