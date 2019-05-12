package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

public class Show extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * 需要展示的ids
     */
    private String[] ids;

    public Show(String... ids) {
        this.ids = ids;
    }


    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.SHOW.getType());
    }
}
