package com.sxyl.portal.domain.tree;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class BinaryTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

//    /***
//     * id , 唯一标识
//     */
//    private String id ;

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

    public BinaryTreeNode( String cid, String nodeTextId, String nodeText) {
        this.cid = cid;
        this.nodeTextId = nodeTextId;
        this.nodeText = nodeText;
    }
}
