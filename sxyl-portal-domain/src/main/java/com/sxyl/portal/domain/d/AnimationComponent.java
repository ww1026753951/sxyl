package com.sxyl.portal.domain.d;


import java.io.Serializable;
import java.util.List;

public abstract class AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /****
     * AnimationType
     * @see com.sxyl.portal.domain.constant.AnimationEnum
     */
    private Integer at;

    /*****
     * 执行步骤序列号,从1 开始
     */
    private Integer c;

    /***
     * 总计数器totalCount
     */
    private Integer tc;




    /****
     *分组内图画的对象
     */
    private List<AnimationComponent> acs;


    public void setAt(Integer at) {
        this.at = at;
    }

    public Integer getAt() {
        return at;
    }

    public List<AnimationComponent> getAcs() {
        return acs;
    }

    public void setAcs(List<AnimationComponent> acs) {
        this.acs = acs;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Integer getTc() {
        return tc;
    }

    public void setTc(Integer tc) {
        this.tc = tc;
    }

    /****
     *  设置组件类型
     */
    abstract void setAnimationType();
}
