package com.sxyl.portal.domain.construct;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constract
 *
 * @date:2020/12/17
 */
@Data
public class ElementConstruct  implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * id
     */
    private Long id;

    /***
     * 页码id
     */
    private Long pageId;

    /***
     * 页面元素的唯一流水号
     */
    private String uuid;

    /****
     * 元素类型
     * @see com.sxyl.portal.domain.constant.ComponentTypeEnum
     */
    private Integer componentType;

    /****
     * 元素的map
     */
    private String attributeMap;
    /***
     * 排序号,影响页面展示顺序
     */
    private Integer sortNum;
}
