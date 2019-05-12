package com.sxyl.portal.domain.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
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

    /*****
     * 元素的类型
     * componentType
     * @see com.sxyl.portal.domain.constant.ComponentTypeEnum
     */
    private Integer ct;

    /***
     * 元素的数据
     * data
     */
    private Integer d;

    /***
     *
     */
    private String h ;


    /****
     *分组内图画的对象, 不会增加到节点下面， 只会增加到同级上,
     * 即 没有上下级关系
     */
    private List<GraphComponent> currentComponent;

    /***
     * 属性值
     */
    private List<Attr> attr;

    public boolean addCurrentComponent(GraphComponent graphComponent) {
        if(this.currentComponent==null){
            this.currentComponent = new ArrayList<GraphComponent>();
        }
        this.currentComponent.add(graphComponent);
        return true;
    }

    public List<GraphComponent> getCurrentComponent() {
        return currentComponent;
    }

    /****
     *  设置组件类型
     */
    abstract void setComponentType();


    public String getId() {
//        if(StringUtils.isBlank(id)){
//            return UUID.randomUUID().toString();
//        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public List<Attr> getAttr() {
        return attr;
    }

    public void setAttr(List<Attr> attr) {
        this.attr = attr;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }
}
