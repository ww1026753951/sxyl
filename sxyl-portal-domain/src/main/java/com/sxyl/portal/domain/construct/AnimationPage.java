package com.sxyl.portal.domain.construct;

import lombok.Data;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.construct
 * @date:2020/12/30
 */
@Data
public class AnimationPage {

    /****
     * id
     */
    private Long id;

    /***
     * 页码id
     */
    private Long animationId;

    /***
     * 页码名称
     */
    private String animationName;

    /****
     * 图片
     */
    private String image;

    /****
     * 描述
     */
    private String desc;
}
