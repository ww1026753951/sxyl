package com.sxyl.portal.domain.tree.rb;

import lombok.Data;

import static com.sxyl.portal.domain.tree.rb.RBTreeColor.RED;

@Data
public class RBTNode<T extends Comparable<T>> {



    /***
     * 对应array 的 id
     */
    private String cid;

    // 颜色
    boolean color;

    /***
     * 节点
     */
    private String nodeTextId;

    /***
     * 宽度
     */
    private Integer width ;

    // 关键字(键值)
    T key;

    // 左孩子
    RBTNode<T> left;

    // 右孩子
    RBTNode<T> right;

    // 父结点
    RBTNode<T> parent;

    public RBTNode(T key, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right) {
        this.key = key;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public T getKey() {
        return key;
    }

    public String toString() {
        return ""+key+(this.color==RED?"(R)":"B");
    }
}