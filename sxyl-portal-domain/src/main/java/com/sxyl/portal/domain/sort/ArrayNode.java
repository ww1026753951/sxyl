package com.sxyl.portal.domain.sort;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ArrayNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 方框的id
     */
    private String rid;


    /**
     * 圆的id
     */
    private String cid;

    /***
     * 数组的值的id
     */
    private String valueTextId;

    /***
     * 数组的值 ,与
     */
    private Integer value;


}
