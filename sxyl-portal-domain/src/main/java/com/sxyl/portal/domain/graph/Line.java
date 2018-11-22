package com.sxyl.portal.domain.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.constant.ComponentTypeEnum;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Line extends GraphComponent implements Serializable {


    /****
     *
     */
    private Integer lt;

    private Integer x1 ;

    private Integer y1;

    private Integer x2;

    private Integer y2;

    /****
     * sourceId
     */
    private String sid;

    /****
     * targetId
     */
    private String tid;

    /****
     *
     * 起始的 x,y ,终止的 x,y的位置枚举
     *
     * @see com.sxyl.portal.domain.constant.LinePositionEnum
     * example 1,1,1,1 ,代表起始，截止， 都与俩圆的起始截止相同
     */
    private Integer[] lpt;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Integer[] getLpt() {
        return lpt;
    }

    public void setLpt(Integer[] lpt) {
        this.lpt = lpt;
    }

    public Integer getLt() {
        return lt;
    }

    public void setLt(Integer lt) {
        this.lt = lt;
    }

    public Integer getX1() {
        return x1;
    }

    public void setX1(Integer x1) {
        this.x1 = x1;
    }

    public Integer getY1() {
        return y1;
    }

    public void setY1(Integer y1) {
        this.y1 = y1;
    }

    public Integer getX2() {
        return x2;
    }

    public void setX2(Integer x2) {
        this.x2 = x2;
    }

    public Integer getY2() {
        return y2;
    }

    public void setY2(Integer y2) {
        this.y2 = y2;
    }

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.LINE.getType());
    }
}
