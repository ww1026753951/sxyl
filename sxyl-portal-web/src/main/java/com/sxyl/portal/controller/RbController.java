package com.sxyl.portal.controller;

import com.sxyl.portal.domain.DomainContent;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.service.sort.RBTreeService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.controller
 * @date:2019/6/22
 */

@Controller
@RequestMapping("/tree")
public class RbController extends BaseController {


    /***
     *
     */
    @Autowired
    private RBTreeService rbTreeService;


    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/rbTreePortal")
    public String dnnPortal(ModelMap modelMap , HttpServletRequest request) throws Exception {

        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc", dc);
        return "/screen/algorithm/rb-tree-portal";

    }

    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/rbTreeConstruct")
    @ResponseBody
    public Object dnnConstruct(int[] arrays,String arrayStr){
//        int arraySize = 12 ;
//        try {
//            if(StringUtils.isNotBlank(arrayStr)){
//                String[] str = arrayStr.split(",");
//                if(str.length > 0){
//                    arrays = new int[str.length];
//                }
//                for (int i = 0 ; i < str.length ; i ++){
//                    String s = str[i];
//                    if(NumberUtils.isNumber(s)){
//                        arrays[i] = new Integer(s);
//                    }else {
//                        arrays[i] = 0 ;
//                    }
//                }
//            }
//        }catch (Exception e){
//
//        }

//        if(arrays==null || arrays.length ==0){
//            arrays=new int[arraySize];
//            for (int i=0 ; i<arraySize;i++){
//                arrays[i] = (int)(1+Math.random()*30);
//            }
//        }


//        arrays = new int[]{11,2,21,56,34,12,56,78,12,23};

//        arrays = new int[]{13,8,17,1,11,15,25,6,22,27,21,35};
//        arrays = new int[]{13,8,17,1,11,15,25,6,22,27,21,35};



        arrays = new int[]{7,3,18,10,22,8,11,26};

        arrays = new int[]{7,3,18,10,22,8,11,26,2,6,13};


        TreeConstruct treeConstruct = rbTreeService.getRbSortConstruct(arrays );
        return treeConstruct;
    }
}
