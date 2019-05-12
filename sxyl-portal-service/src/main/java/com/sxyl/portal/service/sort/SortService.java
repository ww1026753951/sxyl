package com.sxyl.portal.service.sort;

import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.tree.BinaryTreeNode;
import com.sxyl.portal.domain.tree.TreeConstruct;

import java.util.List;

public interface SortService {


    /***
     * 根据数据创建 tree  结构
     * @param arrays
     * @return
     */
    TreeConstruct getHeapSortConstruct(int[] arrays , boolean minHeap);


    /***
     * 通过数组构建满二叉树
     * @param root
     * @param nums
     * @param index
     * @return
     */
    BinaryTreeNode buildTree(BinaryTreeNode root, List<ArrayNode> nums, int index);
}
