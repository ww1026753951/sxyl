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

    /***
     * 文本
     */
    private String text ;


    public ChangeContent() {
    }

    public ChangeContent(String sid, String tid) {
        this.sid = sid;
        this.tid = tid;
    }

    public ChangeContent(String sid, String tid, String text) {
        this.sid = sid;
        this.tid = tid;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.CHANGE_CONTENT.getType());
    }
}
