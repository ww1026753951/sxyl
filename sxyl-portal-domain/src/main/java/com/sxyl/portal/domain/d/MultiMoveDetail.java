package com.sxyl.portal.domain.d;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.d
 * @date:2020/10/3
 */
@Data
@AllArgsConstructor
public class MultiMoveDetail implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    private Integer x;

    private Integer y;
}
