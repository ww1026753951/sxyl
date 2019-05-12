package com.sxyl.portal.common;

public class JUUID {

    /****
     * 获取uuid , 因为页面元素的id不能用数字开头， 所以加个  a
     * @return
     */
    public static String getUUID(){
        return "a"+ java.util.UUID.randomUUID().toString();
    }
}
