package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

public class Move extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 需要移动的元素的id
     */
    private String id;


    /*****
     * 需要移动的元素的目标id
     */
    private String tid;


    /****
     * x 的buffer 设置,
     */
    private Integer bx;

    /****
     * y 的buffer 设置.
     */
    private Integer by;



    /****
     * 需要移动的x位置
     */
//    private Integer x ;

    /****
     * 需要移动的y的位置
     */
//    private Integer y ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Integer getBx() {
        return bx;
    }

    public void setBx(Integer bx) {
        this.bx = bx;
    }

    public Integer getBy() {
        return by;
    }

    public void setBy(Integer by) {
        this.by = by;
    }

    @Override
    void setAnimationType() {
        super.setAt(AnimationEnum.MOVE.getType());
    }
}
