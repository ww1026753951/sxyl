package com.sxyl.portal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionSequence implements Serializable {
    private static final long serialVersionUID = 1L;

    /****
     * 执行步骤的分类code .
     * 例如: 伪代码和执行步骤
     */
    private String groupCode;

    /****
     * 执行步骤的分组 .同时执行
     * 例如: 伪代码和执行步骤
     */
    private String group;


    /****
     * 步骤编码
     */
    private String stepCode ;

    /***
     * 步骤描述
     */
    private String stepDesc;
}
