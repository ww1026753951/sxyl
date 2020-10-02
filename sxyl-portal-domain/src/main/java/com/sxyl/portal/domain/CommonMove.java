package com.sxyl.portal.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain
 * @date:2020/10/2
 */
@Data
public class CommonMove  implements Serializable {

    private static final long serialVersionUID = 1L;


    private int x1;

    private int y1;

    private int x2;

    private int y2;
}
