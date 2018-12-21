package com.sxyl.portal.domain.d.compute;

import com.sxyl.portal.domain.constant.AnimationEnum;
import com.sxyl.portal.domain.d.AnimationComponent;

import java.io.Serializable;
import java.util.List;

public class Multiply extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 目标id
     */
    private String tid ;

    /***
     *  相加id
     */
    private List<String> ids;


    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.MULTIPLY.getType());
    }

}
