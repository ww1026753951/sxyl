package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;
import com.sxyl.portal.domain.d.AnimationComponent;
import lombok.Data;

import java.io.Serializable;

public class FormulaCopy extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * 源头id
     */
    private String sid;

    /***
     * 目标值id
     */
    private String tid;

    public FormulaCopy() {
    }

    public FormulaCopy(String sid, String tid) {
        this.sid = sid;
        this.tid = tid;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.FORMULA_COPY.getType());
    }


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
}
