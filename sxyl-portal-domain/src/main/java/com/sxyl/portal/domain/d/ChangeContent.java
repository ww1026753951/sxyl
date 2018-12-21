package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

public class ChangeContent extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     *
     */
    private String sid;

    /****
     *
     */
    private String tid;


    public ChangeContent() {
    }

    public ChangeContent(String sid, String tid) {
        this.sid = sid;
        this.tid = tid;
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

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.CHANGE_CONTENT.getType());
    }
}
