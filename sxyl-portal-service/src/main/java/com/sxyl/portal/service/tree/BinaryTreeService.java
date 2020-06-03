package com.sxyl.portal.service.tree;

import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.sort.NodeExecuteStep;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.domain.tree.rb.RBExecuteVo;

import java.util.List;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.tree
 * @date:2020/4/4
 */
public interface BinaryTreeService {


    /***
     * 构建二叉树结构
     * @param arrays
     * @return
     */
    TreeConstruct getBinaryTreeService(int[] arrays);


    /***
     * 前序遍历
     * @param rbExecuteVo
     * @return
     */
    TreeConstruct preOrder(RBExecuteVo rbExecuteVo);

    /***
     * 后序遍历
     * @param rbExecuteVo
     * @return
     */
    TreeConstruct postOrder(RBExecuteVo rbExecuteVo);


    /***
     * 中序遍历
     * @param rbExecuteVo
     * @return
     */
    TreeConstruct infixOrder(RBExecuteVo rbExecuteVo);


    /***
     * 新增节点
     * @return
     */
    TreeConstruct insertBinaryNode(RBExecuteVo rbExecuteVo);



    /***
     * 删除节点
     * @return
     */
    TreeConstruct delBinaryNode(RBExecuteVo rbExecuteVo);




}
