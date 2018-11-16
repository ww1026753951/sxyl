package com.sxyl.portal.domain.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.constant.ComponentTypeEnum;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Line extends GraphComponent implements Serializable {


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

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.LINE.getType());
    }
}
