package com.sxyl.portal.service.tree;

import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.sort.NodeExecuteStep;
import com.sxyl.portal.domain.tree.TreeConstruct;

import java.util.List;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.tree
 * @date:2020/6/9
 */
public interface AVLTreeService {



    /***
     * 根据数据创建 tree  结构
     * @param arrays
     * @return
     */
    TreeConstruct getAVLConstruct(int[] arrays);


    /****
     * 获取插入节点的动画明细
     * @param arrayNodeList
     * @param node
     * @return
     */
    TreeConstruct insertAVLNode(List<ArrayNode> arrayNodeList, int node , List<NodeExecuteStep> nodeExecuteStepList);


    /****
     * 获取插入节点的动画明细
     * @param arrayNodeList
     * @param node
     * @return
     */
    TreeConstruct delAVLNode(List<ArrayNode> arrayNodeList, int node, List<NodeExecuteStep> nodeExecuteStepList);
}
