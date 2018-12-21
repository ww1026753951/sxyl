package com.sxyl.portal.domain.d.compute;

import com.sxyl.portal.domain.constant.AnimationEnum;
import com.sxyl.portal.domain.d.AnimationComponent;

import java.io.Serializable;

/****
 *
 * 暂时先把整体计算逻辑写成类， 后期优化
 *
 * **/
public class ComputeOutWeight extends AnimationComponent implements Serializable {

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
     * 输出层输出
     */
    private String outo;


    /***
     * 隐藏层输出
     */
    private String outh;


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

    public String getOuto() {
        return outo;
    }

    public void setOuto(String outo) {
        this.outo = outo;
    }

    public String getOuth() {
        return outh;
    }

    public void setOuth(String outh) {
        this.outh = outh;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.COMPUTE_OUT_WEIGHT.getType());
    }

}
