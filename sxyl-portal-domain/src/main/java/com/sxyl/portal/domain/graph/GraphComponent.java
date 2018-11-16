package com.sxyl.portal.domain.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/****
 * 所有组件的父类
 * 现在对象布局， 支持下边距和右边距的间隔处理。
 * 因为间隔处理,上边距和左边距没任何用处，所以不需要设置上边距和右边距
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class GraphComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * uuid
     */
    private String id;


    /***
     * 左边距
     * marginRight
     */
    private Integer ml;

    /****
     * 上边距
     * marginTop
     */
    private Integer mt;


    /****
     * 需要展示的文字
     * showText
     */
    private String st;

    /****
     * 需要展示文字的位置
     * showTextPosition
     * @see com.sxyl.portal.domain.constant.ShowTextPositionEnum
     */
    private String stp;

    /*****
     * 元素的类型
     * componentType
     * @see com.sxyl.portal.domain.constant.ComponentTypeEnum
     */
    private Integer ct;


    /****
     *  设置组件类型
     */
    abstract void setComponentType();


    public String getId() {
        if(StringUtils.isBlank(id)){
            return UUID.randomUUID().toString();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public Integer getCt() {
        if(ct ==null){
            setComponentType();
        }
        return ct;
    }

    public void setCt(Integer ct) {
        this.ct = ct;
    }

    public Integer getMl() {
        return ml;
    }

    public void setMl(Integer ml) {
        this.ml = ml;
    }

    public Integer getMt() {
        return mt;
    }

    public void setMt(Integer mt) {
        this.mt = mt;
    }

    public String getStp() {
        return stp;
    }

    public void setStp(String stp) {
        this.stp = stp;
    }
}
