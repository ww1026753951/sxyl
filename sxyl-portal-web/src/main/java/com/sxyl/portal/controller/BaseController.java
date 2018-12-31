package com.sxyl.portal.controller;


import com.sxyl.portal.domain.DomainContent;
import com.sxyl.portal.domain.DomainContentMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * Created by wangweiyf on 2015/7/15.
 */
public class BaseController extends MultiActionController {

    @Resource
    private DomainContentMap domainContentMap;

    private String[] whiteList = new String[]{"www.sixiangyaolan.com","www.donghuasuanfa.com","www.suanfadonghua.com"
            ,"www.sixiangyaolan.cn","www.donghuasuanfa.cn","www.suanfadonghua.cn"
            ,"www.sixiangyaolan.org","www.donghuasuanfa.org","www.suanfadonghua.org"
            ,"www.sixiangyaolan.net","www.donghuasuanfa.net","www.suanfadonghua.net"
            ,"www.sixiangyaolan.com","www.donghuasuanfa.com","www.suanfadonghua.com"
    };

    /****
     * 根据域名获取
     * @return
     */
    protected DomainContent getDomainByReferer(HttpServletRequest request){
        String serverName = request.getServerName();
        return domainContentMap.getDomainContentByServerName(serverName);
    }

    /****
     * 根据域名获取
     * @return
     */
    protected boolean checkRefer(HttpServletRequest request){
        String r =request.getHeader("Referer");
        if(StringUtils.isBlank(r)){
            return false;
        }
        if(r.contains("?")){
            r = r.substring(0 , r.indexOf("?"));
        }
        try {
            URI referUri = new URI(r);
            String domain = referUri.getHost().toLowerCase();
            for (String s : whiteList){
                if(s.equals(domain)){
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }

        return false;
    }



}
