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


    /****
     *分组内图画的对象
     */
    private List<GraphComponent> child;

    public Text() {
    }

    public Text(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Text(Integer x, Integer y, String st) {
        this.x = x;
        this.y = y;
        this.st = st;
    }


    public Text(String id ,Integer x, Integer y, String st) {
        super.setId(id);
        this.x = x;
        this.y = y;
        this.st = st;
    }

    public Text(String id ,Integer x, Integer y, String st, String sts) {
        super.setId(id);
        this.x = x;
        this.y = y;
        this.st = st;
        this.sts = sts;
    }
    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.TEXT.getType());
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


    public boolean addChild(GraphComponent graphComponent) {
        if(this.child==null){
            this.child = new ArrayList<GraphComponent>();
        }
        this.child.add(graphComponent);
        return true;
    }

    public List<GraphComponent> getChild() {
        return child;
    }

    public void setChild(List<GraphComponent> child) {
        this.child = child;
    }
}
