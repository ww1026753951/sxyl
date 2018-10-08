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
     * 步骤编码
     */
    private String stepCode ;

    /***
     * 步骤描述
     */
    private String stepDesc;
}
