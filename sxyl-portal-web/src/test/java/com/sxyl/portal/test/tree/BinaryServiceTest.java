package com.sxyl.portal.test.tree;

import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.service.tree.BinaryTreeService;
import com.sxyl.portal.test.BaseTest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.test.tree
 * @date:2020/4/5
 */
public class BinaryServiceTest extends BaseTest {

    @Resource
    private BinaryTreeService binaryTreeService;



    @Test
    public void testCreateTree(){
        String arrayText="1,2,3,4,5" ;
        int[] array = getArrays(null,arrayText);


        TreeConstruct treeConstruct = binaryTreeService.getBinaryTreeService(array);

        Assert.assertEquals(treeConstruct , null);
    }







    /***
     * 获取数组信息
     * @param arrays
     * @param arrayStr
     */
    protected int[] getArrays(int[] arrays,String arrayStr){
        int arraySize = 12 ;
        try {
            if(StringUtils.isNotBlank(arrayStr)){
                String[] str = arrayStr.split(",");
                if(str.length > 0){
                    arrays = new int[str.length];
                }
                for (int i = 0 ; i < str.length ; i ++){
                    String s = str[i];
                    if(NumberUtils.isNumber(s)){
                        arrays[i] = new Integer(s);
                    }else {
                        arrays[i] = 0 ;
                    }
                }
            }
        }catch (Exception e){

        }

        if(arrays==null || arrays.length ==0){
//            List<Integer> random = new ArrayList<>();
            arrays=new int[arraySize];
            for (int i=0 ; i<arraySize;i++){
                arrays[i] = (int)(1+Math.random()*30);
//                random.add((int)(1+Math.random()*30));
            }


        }


        return arrays ;
    }



}