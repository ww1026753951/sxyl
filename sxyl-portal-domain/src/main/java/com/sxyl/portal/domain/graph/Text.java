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


    /****
     * 比例,用于线状的文字展示时的偏移量比例计算,
     * 当为2时,则 (x2 - x1)/2  。 结果为中间位置。
     * 当为1时，则与 x2位置重叠
     */
    private Double ratio;


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

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.TEXT.getType());
    }
}
