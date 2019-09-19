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
     * 对应 ExecutionSequence 中的文案
     *
     */
    private String ad;

    /***
     * don'tShowAnimation
     */
    private boolean ds;

    public void setAt(Integer at) {
        this.at = at;
    }

    public Integer getAt() {
        if(at == null){
            setAnimationType();
        }
        return at;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }


    public boolean getDs() {
        return ds;
    }

    public void setDs(boolean ds) {
        this.ds = ds;
    }

    /****
     *  设置组件类型
     */
    public abstract void setAnimationType();
}
