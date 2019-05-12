package com.sxyl.portal.domain.formula.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FormulaNodeContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 节点的值
     */
    private List<FormulaNode> nodes;


    /***
     * 加法连接符
     */
    private String connect = "+";
}
