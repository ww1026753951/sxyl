package com.sxyl.portal.domain.graph;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.constant.ComponentTypeEnum;

import java.io.Serializable;

/****
 * 圆的对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Text extends GraphComponent implements Serializable {
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
     * 需要展示的文字
     * showText
     */
    private String st;



    /****
     * 需要展示文字的位置
     * showTextPosition
     * @see com.sxyl.portal.domain.constant.ShowTextPositionEnum
     *
     */
    private String sts;


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
        super.setCt(ComponentTypeEnum.TEXT.getType());
    }
}
