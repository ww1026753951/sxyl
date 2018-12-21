package com.sxyl.portal.domain.d.compute;

import com.sxyl.portal.domain.constant.AnimationEnum;
import com.sxyl.portal.domain.d.AnimationComponent;

import java.io.Serializable;

public class SquaredError extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 目标id
     */
    private String tid ;


    /***
     * 目标id
     */
    private String targetId;

    /***
     * 输出id
     */
    private String outputId;


    public SquaredError() {
    }

    public SquaredError(String tid, String targetId, String outputId) {
        this.tid = tid;
        this.targetId = targetId;
        this.outputId = outputId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getOutputId() {
        return outputId;
    }

    public void setOutputId(String outputId) {
        this.outputId = outputId;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.SQUARED_ERROR.getType());
    }

}
