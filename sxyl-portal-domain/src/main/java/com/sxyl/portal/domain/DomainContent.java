package com.sxyl.portal.domain;

import lombok.Data;

import java.io.Serializable;

/****
 * 获取当前域名对象
 */
@Data
public class DomainContent implements Serializable {
    private static final long serialVersionUID = 1L;


    /***
     * 当前域名
     */
    private String domain;


    /***
     * 域名所对应的名称
     */
    private String domainName;

    /***
     * 域名对应的title
     */
    private String title ;
}
