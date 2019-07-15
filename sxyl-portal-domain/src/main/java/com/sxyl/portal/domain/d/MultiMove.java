package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;
import com.sxyl.portal.domain.graph.GraphComponent;

import java.io.Serializable;
import java.util.List;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.d
 * @date:2019/7/3
 */
public class MultiMove extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * 元素列表
     */
    private List<GraphComponent> gcs;


    public List<GraphComponent> getGcs() {
        return gcs;
    }

    public void setGcs(List<GraphComponent> gcs) {
        this.gcs = gcs;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.MULTI_MOVE.getType());
    }
}
