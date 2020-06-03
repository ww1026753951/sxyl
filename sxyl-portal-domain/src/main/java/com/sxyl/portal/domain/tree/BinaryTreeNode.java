package com.sxyl.portal.domain.tree;

import lombok.Data;

import java.io.Serializable;

@Data
public class BinaryTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是左节点还是右侧节点的标志位
     */
    private int nodeType ;

    private int data;

    /***
     * 对应array 的 id
     */
    private String cid;

    /***
     * 节点
     */
    private String nodeTextId;

    /***
     * 节点名称
     */
    private String nodeText;

    /***
     * 左子节点
     */
    private BinaryTreeNode leftNode ;

    /***
     * 右子节点
     */
    private BinaryTreeNode rightNode ;

    /***
     * 父节点
     */
    private String parentNodeCid ;


    /***
     * 宽度
     */
    private Integer width ;

    /***
     * 当前元素的buffer, 用于宽度减去的值
     */
    private Integer buffer;

    /**
     * 节点的层次
     */
    private Integer level;

    /***
     * 高度
     */
    private Integer height;




    public BinaryTreeNode(){

    }

    public BinaryTreeNode( String cid, String nodeTextId, String nodeText) {
        this.cid = cid;
        this.nodeTextId = nodeTextId;
        this.nodeText = nodeText;
    }
}
