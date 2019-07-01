package com.sxyl.portal.service.sort.impl;

import com.sxyl.portal.common.JUUID;
import com.sxyl.portal.domain.constant.ComponentCompositeEnum;
import com.sxyl.portal.domain.constant.DisplayEnum;
import com.sxyl.portal.domain.constant.LinePositionEnum;
import com.sxyl.portal.domain.constant.ShowTextPositionEnum;
import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.graph.*;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.tree.BinaryTreeNode;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.domain.tree.rb.RBTNode;
import com.sxyl.portal.domain.tree.rb.RBTree;
import com.sxyl.portal.domain.tree.rb.RBTreeColor;
import com.sxyl.portal.service.CommonService;
import com.sxyl.portal.service.sort.RBTreeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RBTreeServiceImpl extends CommonService implements RBTreeService {



    // 圆的半径
    private final int r = 20;

    @Override
    public TreeConstruct getRbSortConstruct(int[] arrays) {

        RBTree<Integer> tree=new RBTree<Integer>();



        /***
         * 构建数组
         */
        List<ArrayNode> arrayNodes = createArrayNode(arrays) ;

//        for(int i=0; i<arrays.length; i++) {
//            tree.insert(arrays[i]);
//            // 设置mDebugInsert=true,测试"添加函数"
//        }
//        RBTree


        for(ArrayNode arrayNode : arrayNodes) {

            tree.insert(arrayNode , arrayNode.getValue());
            // 设置mDebugInsert=true,测试"添加函数"
        }

        //dnn动画
        AnimationTotal animationTotal = null;

        /***
         * 层序遍历树生成结构
         */
        Group group = getArrayAndTreeConstruct(arrayNodes , tree);

        group = createLineConstruct(group ,  tree.getRoot());

        return new TreeConstruct(group , animationTotal );
    }




    /***
     * 根据数组构建列表
     * @param arrays
     * @return
     */
    private List<ArrayNode> createArrayNode(int[] arrays){
        List<ArrayNode> arrayNodes = new ArrayList<>();
        ArrayNode arrayNode ;
        for (int i : arrays){
            arrayNode = new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(i)) ;
            arrayNodes.add(arrayNode);
        }
        return arrayNodes;
    }






    /***
     * 层序遍历构建数组和树结构
     * @param rbTree
     * @return
     */
    private Group getArrayAndTreeConstruct(List<ArrayNode> arrayNodes , RBTree rbTree) {
        Group all = new Group();

//        all.setMl(500);
        all.setMt(20);
        all.setCompose(ComponentCompositeEnum.NONE.getType());

        Group array = new Group();
        array.setCompose(ComponentCompositeEnum.HORIZONTAL.getType());
        int ml=50;
        for (ArrayNode arrayNode : arrayNodes){
            ml = ml + 50;
            array.addChild(new RectAndText(arrayNode.getRid() , arrayNode.getValue(), 30,30 ,ml,"white"));
        }

        all.addChild(array);


        int buffer = 250;
        int defaultML = 600 ;
        double rate = 0.5;

        //层序遍历
        Queue<RBTNode> nodeQueue = new ArrayDeque<>();
        //每层节点的x队列。
//        Queue<Integer> nodeWidthQueue = new ArrayDeque<>();
        //设置默认宽度
        rbTree.getRoot().setWidth(defaultML);
        nodeQueue.add(rbTree.getRoot());
//        nodeWidthQueue.add(defaultML);
        RBTNode temp ;
        int currentLevel = 1;    //记录当前层需要打印的节点的数量
        int nextLevel = 0;//记录下一层需要打印的节点的数量
        int currentIndex = 0 ;//当前层节点的序号
        Group group = new Group();
        Integer parentWidth = 0;
        Integer left ;
        while ((temp = nodeQueue.poll()) != null) {
            currentIndex = currentIndex + 1 ;
//            if (currentIndex % 2 != 0 ) {
//                parentWidth = nodeWidthQueue.poll() ;
//                left = parentWidth - buffer;
//                nodeWidthQueue.add(left.intValue());
//            } else {
//                left = parentWidth + buffer;
//                nodeWidthQueue.add(left.intValue());
//            }

//            left = temp.getParent().getWidth()


            //todo
//            Circle circle = create(group , temp , left);

            Circle circle = create(group , temp , temp.getWidth());
            if (temp.getLeft() != null) {
                //赋值左节点的宽度
                temp.getLeft().setWidth( temp.getWidth() - buffer );
                nodeQueue.add(temp.getLeft());
                nextLevel++;
            }
            if (temp.getRight() != null) {
                //赋值右节点 的宽度
                temp.getRight().setWidth(temp.getWidth() + buffer);
                nodeQueue.add(temp.getRight());
                nextLevel++;
            }

            currentLevel--;
            if(currentLevel == 0) {
                currentIndex = 0;
                group.setMt(80);
                buffer =  new Double(buffer*rate).intValue();
                group.setCompose(ComponentCompositeEnum.NONE.getType());
                all.addChild(group);
                group = new Group();
                currentLevel = nextLevel;
                nextLevel = 0;
            }
        }
        return all;
    }


    /***
     * 创建圆
     * @param group
     * @param node
     */
    public Circle create(Group group , RBTNode node , Integer x) {

        Circle circle = new Circle( node.getCid(), r ,"blue");

        if(node.isColor() == RBTreeColor.RED){
            circle.setF("red");
        }else {
            circle.setF("black");
        }

//        circle.setH(DisplayEnum.NONE.getContent());
        circle.setX(x);
        Text text = new Text(node.getNodeTextId() , x,0, node.getKey().toString() , ShowTextPositionEnum.MIDDLE.getCode());
        text.setF("white");
//        text.setH(DisplayEnum.NONE.getContent());
        circle.addCurrentComponent(text);
        group.addChild(circle);
        return circle ;
    }


    /***
     * 创建线结构
     * @param g
     * @param root
     * @return
     */
    private Group createLineConstruct(Group g , RBTNode<Integer> root){
        if(root == null){
            return g;
        }

        if (root.getLeft() != null) {
            //左节点的线
            g.addChild(createLine(root.getCid() , root.getLeft().getCid() , false));
        }
        if (root.getRight() != null) {
            //右节点的线
            g.addChild(createLine(root.getCid() , root.getRight().getCid() , false));
        }

        createLineConstruct(g, root.getLeft());
        createLineConstruct(g, root.getRight());

        return g ;
    }



}
