package com.sxyl.portal.domain.tree.rb;

import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.sort.NodeExecuteStep;
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
public class RBExecuteVo implements Serializable {
    private static final long serialVersionUID = 1L;


    /****
     * 数组列表
     */
    private List<ArrayNode> arrayNodeList ;

    /***
     * 插入或者删除的几点
     */
    private int node;

    /***
     * 操作历史
     */
    private List<NodeExecuteStep> executeHistory;

    /***
     * 执行的节点,逗号分割
     */
//    private String executeNodes ;

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

//    public String getExecuteNodes() {
//        return executeNodes;
//    }
//
//    public void setExecuteNodes(String executeNodes) {
//        this.executeNodes = executeNodes;
//    }


    public List<NodeExecuteStep> getExecuteHistory() {
        return executeHistory;
    }

    public void setExecuteHistory(List<NodeExecuteStep> executeHistory) {
        this.executeHistory = executeHistory;
    }
}
