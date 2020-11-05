package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

public class Hide extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    private String[] ids;

    public Hide() {
    }

    public Hide(String id) {
        this.id = id;
    }

    public Hide(String id, String ad) {
        this.id = id;
        super.setAd(ad);
    }

    public Hide(String... id) {
        this.ids = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.HIDE.getType());
    }
}
