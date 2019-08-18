package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.d
 * @date:2019/7/18
 */
public class ChangeAttr extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    private List<ChangeAttrDetail> list ;

    public List<ChangeAttrDetail> getList() {
        return list;
    }

    public void setList(List<ChangeAttrDetail> list) {
        this.list = list;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.CHANGE_ATTR.getType());
    }
}
