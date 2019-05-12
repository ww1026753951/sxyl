package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

public class Swap extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 源头id
     */
    private String[] sid ;

    /***
     * 目标id
     */
    private String[] tid ;



    public Swap() {
    }

    public Swap(String[] sid, String[] tid) {
        this.sid = sid;
        this.tid = tid;
    }

    public Swap(String[] sid, String[] tid,String ad) {
        this.sid = sid;
        this.tid = tid;
        super.setAd(ad);
    }

    public String[] getSid() {
        return sid;
    }

    public void setSid(String[] sid) {
        this.sid = sid;
    }

    public String[] getTid() {
        return tid;
    }

    public void setTid(String[] tid) {
        this.tid = tid;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.SWAP.getType());
    }
}
