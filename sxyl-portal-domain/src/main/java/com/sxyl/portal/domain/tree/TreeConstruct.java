package com.sxyl.portal.domain.tree;

import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.sort.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/***
 * 树的结构
 */
public class TreeConstruct implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     *  数组的列表
     */
    private List<ArrayNode> arrayLists;


    /****
     * dom结构
     */
    private Group group;


    /****
     * 动画的组件
     */
    private AnimationTotal at ;

    public TreeConstruct() {
    }

    public TreeConstruct(Group group, AnimationTotal at) {
        this.group = group;
        this.at = at;
    }

    public TreeConstruct(List<ArrayNode> arrayLists, Group group, AnimationTotal at) {
        this.arrayLists = arrayLists;
        this.group = group;
        this.at = at;
    }

    public List<ArrayNode> getArrayLists() {
        return arrayLists;
    }

    public void setArrayLists(List<ArrayNode> arrayLists) {
        this.arrayLists = arrayLists;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public AnimationTotal getAt() {
        return at;
    }

    public void setAt(AnimationTotal at) {
        this.at = at;
    }
}
