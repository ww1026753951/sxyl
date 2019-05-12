package com.sxyl.portal.domain.d.compute;

import com.sxyl.portal.domain.constant.AnimationEnum;
import com.sxyl.portal.domain.d.AnimationComponent;

import java.io.Serializable;
import java.util.List;

public class Sigmoid extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 目标id
     */
    private String tid ;

    /***
     *  计算id
     */
    private String cid;



    public Sigmoid() {
    }

    public Sigmoid(String tid, String cid) {
        this.tid = tid;
        this.cid = cid;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.SIGMOID.getType());
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

}
