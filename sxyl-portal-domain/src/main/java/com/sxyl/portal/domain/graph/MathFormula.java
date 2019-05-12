package com.sxyl.portal.domain.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.constant.ComponentTypeEnum;
import com.sxyl.portal.domain.graph.GraphComponent;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MathFormula extends GraphComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * 横坐标
     */
    private Integer x;

    /**
     * 纵坐标
     * */
    private Integer y ;

    /**
     * 公式内容
     */
    private String fc;

    /***
     * 运行时公式， 带有替换内容的占位符
     * fc segment
     */
    private String rfc;


    public MathFormula() {
    }

    public MathFormula( Integer x, Integer y , String fc) {
        this.x = x;
        this.y = y;
        this.fc = fc;
    }

    public MathFormula(String id , Integer x, Integer y , String fc) {
        super.setId(id);
        this.x = x;
        this.y = y;
        this.fc = fc;
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

    public String getFc() {
        return fc;
    }

    public void setFc(String fc) {
        this.fc = fc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.FORMULA.getType());
    }
}
