package com.sxyl.portal.domain.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.constant.ComponentTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/****
 * 分组的对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group extends GraphComponent implements Serializable {

    /***
     * 子元素组合类型
     * @see com.sxyl.portal.domain.constant.ComponentCompositeEnum
     *
     * composeType
     */
    private Integer compose ;

    /***
     * 缓存类,主要用于公式处理时是否保存到前端对象。
     */
    private boolean cache;

    /****
     *分组内图画的对象
     */
    private List<GraphComponent> child;

    public boolean addChild(GraphComponent graphComponent) {
        if(this.child==null){
            this.child = new ArrayList<GraphComponent>();
        }
        this.child.add(graphComponent);
        return true;
    }

    @Override
    void setComponentType() {
        super.setCt(ComponentTypeEnum.GROUP.getType());
    }

    public List<GraphComponent> getChild() {
        return child;
    }

    public void setChild(List<GraphComponent> child) {
        this.child = child;
    }

    public Integer getCompose() {
        return compose;
    }

    public void setCompose(Integer compose) {
        this.compose = compose;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }
}
