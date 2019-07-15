package com.sxyl.portal.domain.tree.rb;

import com.sxyl.portal.domain.sort.ArrayNode;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.tree.rb
 * @date:2019/7/3
 */
public class RBInsertVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private List<ArrayNode> arrayNodeList ;

    private int node;

    public List<ArrayNode> getArrayNodeList() {
        return arrayNodeList;
    }

    public void setArrayNodeList(List<ArrayNode> arrayNodeList) {
        this.arrayNodeList = arrayNodeList;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }
}
