package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

public class Copy extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /****
     * 源头id
     */
    private String sid;

    /***
     * 目标id 结尾
     */
    private String tidEnd;



    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTidEnd() {
        return tidEnd;
    }

    public void setTidEnd(String tidEnd) {
        this.tidEnd = tidEnd;
    }

    @Override
    void setAnimationType() {
        super.setAt(AnimationEnum.COPY.getType());
    }
}
