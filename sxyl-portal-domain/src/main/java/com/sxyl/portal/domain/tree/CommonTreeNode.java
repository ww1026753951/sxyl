package com.sxyl.portal.domain.tree;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CommonTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * id , 唯一标识
     */
    private String id ;

    /***
     * 节点
     */
    private String nodeTextId;

    /***
     * 节点名称
     */
    private String nodeText;




    private List<CommonTreeNode> childs;
}
