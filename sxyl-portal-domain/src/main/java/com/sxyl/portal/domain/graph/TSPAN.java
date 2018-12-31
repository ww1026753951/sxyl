package com.sxyl.portal.domain.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.constant.ComponentTypeEnum;

import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TSPAN extends GraphComponent implements Serializable {
    private static final long serialVersionUID = 1L;

    /***
     * x轴的偏差
     */
    private Integer dx ;

    /***
     * y轴的偏差
     */
    private Integer dy;

    /***
     * base_line
     */
    private String sts;

    /***
     * 需要展示的文字
     */
    private String st ;

    public TSPAN() {
    }

    public TSPAN(String st, String bl) {
        this.sts = bl;
        this.st = st;
    }

    public TSPAN(String st, String sts,Integer dx, Integer dy) {
        this.dx = dx;
        this.dy = dy;
        this.sts = sts;
        this.st = st;
    }

    public Integer getDx() {
        return dx;
    }

    public void setDx(Integer dx) {
        this.dx = dx;
    }

    public Integer getDy() {
        return dy;
    }

    public void setDy(Integer dy) {
        this.dy = dy;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.TSPAN.getType());
    }
}
