package com.sxyl.portal.domain.tree.avl;

import com.sxyl.portal.domain.sort.ArrayNode;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.tree
 * @date:2020/6/8
 */
public class AVLTreeNode  implements Serializable {

    private static final long serialVersionUID = 1L;

    int key ;

    int height;

    /***
     * 左侧节点
     */
    AVLTreeNode left;

    /***
     * 右侧节点
     */
    AVLTreeNode right ;


    /***
     * 父级节点
     */
    AVLTreeNode parent;

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






    public AVLTreeNode(int d ,ArrayNode arrayNode ) {
        cid = arrayNode.getCid();
        nodeTextId = arrayNode.getValueTextId();
        nodeText = String.valueOf(arrayNode.getValue());
        key = d;
        height = 1;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AVLTreeNode getLeft() {
        return left;
    }

    public void setLeft(AVLTreeNode left) {
        this.left = left;
    }

    public AVLTreeNode getRight() {
        return right;
    }

    public void setRight(AVLTreeNode right) {
        this.right = right;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getNodeTextId() {
        return nodeTextId;
    }

    public void setNodeTextId(String nodeTextId) {
        this.nodeTextId = nodeTextId;
    }

    public String getNodeText() {
        return nodeText;
    }

    public void setNodeText(String nodeText) {
        this.nodeText = nodeText;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getBuffer() {
        return buffer;
    }

    public void setBuffer(Integer buffer) {
        this.buffer = buffer;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public AVLTreeNode getParent() {
        return parent;
    }

    public void setParent(AVLTreeNode parent) {
        this.parent = parent;
    }
}
