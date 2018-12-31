package com.sxyl.portal.domain;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Map;

public class DomainContentMap  implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String defaultHost = "www.donghuasuanfa.com";

    private Map<String, DomainContent> dcMap;


    public Map<String, DomainContent> getDcMap() {
        return dcMap;
    }

    public void setDcMap(Map<String, DomainContent> dcMap) {
        this.dcMap = dcMap;
    }

    /***
     * 根据域名获取 DomainContent 的方法
     * @param serverName
     * @return
     */
    public DomainContent getDomainContentByServerName(String serverName){
        if (StringUtils.isNotBlank(serverName)){
            DomainContent dc =dcMap.get(serverName);
            if(dc!=null){
                return dc;
            }
        }

        return dcMap.get(defaultHost) ;
    }
}
