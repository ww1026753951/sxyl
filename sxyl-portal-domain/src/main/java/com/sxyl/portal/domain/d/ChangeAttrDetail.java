package com.sxyl.portal.domain.d;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.d
 * @date:2019/7/18
 */
public class ChangeAttrDetail  implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id ;

    private Map<String,String> map = new HashMap<>();


    public void addValue(String key , String value){

        map.put(key , value);

    }

    public ChangeAttrDetail(String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
