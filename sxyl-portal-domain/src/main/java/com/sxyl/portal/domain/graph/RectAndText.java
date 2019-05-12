package com.sxyl.portal.domain.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.constant.ComponentTypeEnum;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RectAndText extends GraphComponent implements Serializable {

    /***
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /***
     * 方框的值
     */
    private Integer value;

    /***
     * 方框的高度
     */
    private Integer height ;

    /***
     * 填充颜色的字段
     */
    private String fill ;

    /***
     * 高度
     */
    private Integer t;

    public RectAndText(String id , Integer value , Integer height, Integer t,Integer ml , String fill) {
        super.setId(id);
        super.setMl(ml);
        this.value = value ;
        this.height = height;
        this.t = t;
        this.fill = fill ;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.RECT_AND_TEXT.getType());
    }
}
