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

    /***
     * 简装版本批量移动,保证移动元素中都有  id,x,y
     * @return
     */
    private List<MultiMoveDetail> details;


    public List<GraphComponent> getGcs() {
        return gcs;
    }

    public void setGcs(List<GraphComponent> gcs) {
        this.gcs = gcs;
    }

    public List<MultiMoveDetail> getDetails() {
        return details;
    }

    public void setDetails(List<MultiMoveDetail> details) {
        this.details = details;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.MULTI_MOVE.getType());
    }
}
