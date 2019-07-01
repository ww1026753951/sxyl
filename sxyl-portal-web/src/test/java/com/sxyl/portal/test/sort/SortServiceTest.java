package com.sxyl.portal.test.sort;

import com.sxyl.portal.common.JUUID;
import com.sxyl.portal.domain.formula.common.FormulaCommon;
import com.sxyl.portal.domain.graph.MathFormula;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.tree.BinaryTreeNode;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.service.formula.IMathFormula;
import com.sxyl.portal.service.formula.impl.MeanSquaredErrorMathFormula;
import com.sxyl.portal.service.sort.SortService;
import com.sxyl.portal.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SortServiceTest extends BaseTest {

    @Autowired
    private SortService sortService;



    @Test
    public void testCreateTree(){
        final double d = Math.random();
        List<ArrayNode> nums = new ArrayList<>();
        for (int i =0; i <11;i++){
//            nums.add(new ArrayNode(JUUID.getUUID(),JUUID.getUUID() ,new Double(d*100).intValue()));
            nums.add(new ArrayNode(JUUID.getUUID(),JUUID.getUUID(),JUUID.getUUID() ,i));
        }

        BinaryTreeNode binaryTreeNode = new BinaryTreeNode( nums.get(0).getCid() , JUUID.getUUID()  , nums.get(0).getValue().toString());
        BinaryTreeNode binaryTree = sortService.buildTree(binaryTreeNode , nums , 0 );

        Assert.assertEquals(binaryTree , null);
    }



    @Test
    public void testTreeConstruct(){

        int[] s = new int[]{11,2,21,56,34,12,56,78,12,23};
        TreeConstruct treeConstruct = sortService.getHeapSortConstruct(s,true);

        Assert.assertEquals(treeConstruct , null);
    }





}
