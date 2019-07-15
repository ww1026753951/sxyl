package com.sxyl.portal.domain.sort;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

public class ArrayNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 方框的id
     */
    private String rid;


    /**
     * 圆的id
     */
    private String cid;

    /***
     * 数组的值的id
     */
    private String valueTextId;

    /***
     * 数组的值 ,与
     */
    private Integer value;


    public ArrayNode() {
    }

    public ArrayNode(String rid, String cid, String valueTextId, Integer value) {
        this.rid = rid;
        this.cid = cid;
        this.valueTextId = valueTextId;
        this.value = value;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getValueTextId() {
        return valueTextId;
    }

    public void setValueTextId(String valueTextId) {
        this.valueTextId = valueTextId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
