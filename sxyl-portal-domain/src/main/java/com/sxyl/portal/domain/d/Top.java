package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.d
 * @date:2020/10/6
 */
public class Top extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * id
     */
    private String id;

    /***
     * id数组
     */
    private String[] ids;


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

        super.setAt(AnimationEnum.TOP.getType());
    }
}
