package com.sxyl.portal.service.sort;

import com.sxyl.portal.domain.tree.TreeConstruct;

public interface RBTreeService {


    /***
     * 根据数据创建 tree  结构
     * @param arrays
     * @return
     */
    TreeConstruct getRbSortConstruct(int[] arrays);
}
