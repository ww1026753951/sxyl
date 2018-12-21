package com.sxyl.portal.domain.d;


import java.io.Serializable;

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
//    private Integer c;



    public void setAt(Integer at) {
        this.at = at;
    }

    public Integer getAt() {
        if(at == null){
            setAnimationType();
        }
        return at;
    }

//    public Integer getC() {
//        return c;
//    }
//
//    public void setC(Integer c) {
//        this.c = c;
//    }

    /****
     *  设置组件类型
     */
    public abstract void setAnimationType();
}
