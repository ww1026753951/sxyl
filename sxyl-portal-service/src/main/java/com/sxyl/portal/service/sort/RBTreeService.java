package com.sxyl.portal.service.sort;

import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.sort.NodeExecuteStep;
import com.sxyl.portal.domain.tree.TreeConstruct;

import java.util.List;

public interface RBTreeService {


    /***
     * 根据数据创建 tree  结构
     * @param arrays
     * @return
     */
    TreeConstruct getRbSortConstruct(int[] arrays);


    /****
     * 获取插入节点的动画明细
     * @param arrayNodeList
     * @param node
     * @return
     */
    TreeConstruct insertRbNode(List<ArrayNode> arrayNodeList, int node);


    /****
     * 获取插入节点的动画明细
     * @param arrayNodeList
     * @param node
     * @return
     */
    TreeConstruct delRbNode(List<ArrayNode> arrayNodeList, int node, List<NodeExecuteStep> nodeExecuteStepList);
}
