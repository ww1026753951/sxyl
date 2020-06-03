package com.sxyl.portal.domain.constant;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2020/5/17
 */
public enum  AlgorithmTypeEnum {


    //红黑树
    RB_TREE(1,"rb"),
    //二叉树
    BINARY(2,"binary"),
    //avl树
    AVL_TREE(3,"avl");

    private final int type;
    private final String desc;

    private AlgorithmTypeEnum(int type , String desc) {
        this.type = type;
        this.desc = desc;
    }


    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
