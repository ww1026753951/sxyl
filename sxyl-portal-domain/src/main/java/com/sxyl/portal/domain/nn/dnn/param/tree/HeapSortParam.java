package com.sxyl.portal.domain.nn.dnn.param.tree;

import com.sxyl.portal.domain.tree.BinaryTreeNode;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HeapSortParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 基础数组
     */
    private List<Integer> array;


    /***
     * 树信息
     */
    private BinaryTreeNode binaryTreeNode;


}
