package com.sxyl.portal.domain.formula.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormulaNode implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * 节点值得文案
     */
    private String nodeText;

    /***
     * 节点的上标
     */
    private String nodeSup;

    /***
     * 节点的下标
     */
    private String nodeSub;

    /***
     * 加法连接符
     */
    private String connect ;
}
