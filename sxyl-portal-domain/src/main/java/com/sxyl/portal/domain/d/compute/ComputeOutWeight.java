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


    private String weight;

    /***
     * 目实际值
     */
    private String actual;

    /***
     * 输出层输出
     */
    private String outo;


    /***
     * 隐藏层输出
     */
    private String outh;

    public ComputeOutWeight(String tid, String weight, String actual, String outo, String outh) {
        this.tid = tid;
        this.weight = weight;
        this.actual = actual;
        this.outo = outo;
        this.outh = outh;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
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
