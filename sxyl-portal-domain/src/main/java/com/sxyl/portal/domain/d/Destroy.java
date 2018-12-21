package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

public class Destroy extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    public Destroy() {
    }

    public Destroy(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.DESTROY.getType());
    }
}
