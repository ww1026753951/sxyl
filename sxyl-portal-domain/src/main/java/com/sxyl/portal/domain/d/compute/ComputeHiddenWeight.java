package com.sxyl.portal.domain.d.compute;

import com.sxyl.portal.domain.constant.AnimationEnum;
import com.sxyl.portal.domain.d.AnimationComponent;

import java.io.Serializable;
import java.util.List;

public class ComputeHiddenWeight extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    private String tid;

    private List<String> targetId;

    private List<String> outId;

    private List<String> weightId;

    private String oid ;

    private String iv;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public List<String> getTargetId() {
        return targetId;
    }

    public void setTargetId(List<String> targetId) {
        this.targetId = targetId;
    }

    public List<String> getOutId() {
        return outId;
    }

    public void setOutId(List<String> outId) {
        this.outId = outId;
    }

    public List<String> getWeightId() {
        return weightId;
    }

    public void setWeightId(List<String> weightId) {
        this.weightId = weightId;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.COMPUTE_HIDDEN_WEIGHT.getType());
    }
}
