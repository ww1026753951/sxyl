package com.sxyl.portal.domain.d;


import java.io.Serializable;

public abstract class AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /****
     * AnimationType
     * 动画类型
     * @see com.sxyl.portal.domain.constant.AnimationEnum
     */
    private Integer at;

    /****
     * AnimationDescCode
     * 动画描述的编码,
     * 对应 ExecutionSequence 中的
     */
    private Integer ad;

    public void setAt(Integer at) {
        this.at = at;
    }

    public Integer getAt() {
        if(at == null){
            setAnimationType();
        }
        return at;
    }

    public Integer getAd() {
        return ad;
    }

    public void setAd(Integer ad) {
        this.ad = ad;
    }

    /****
     *  设置组件类型
     */
    public abstract void setAnimationType();
}
