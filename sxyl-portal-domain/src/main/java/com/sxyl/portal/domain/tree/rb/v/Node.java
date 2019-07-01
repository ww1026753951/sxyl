package com.sxyl.portal.domain.tree.rb.v;

import lombok.Data;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.tree.rb
 * @date:2019/7/1
 */
@Data
public class Node {


    public int data; // holds the key
    public Node parent; // pointer to the parent
    public Node left; // pointer to left child
    public Node right; // pointer to right child
    public int color; // 1 . Red, 0 . Black
}
