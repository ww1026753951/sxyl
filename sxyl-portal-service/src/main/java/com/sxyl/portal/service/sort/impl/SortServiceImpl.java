package com.sxyl.portal.service.sort.impl;

import com.sxyl.portal.common.JUUID;
import com.sxyl.portal.domain.constant.ComponentCompositeEnum;
import com.sxyl.portal.domain.constant.DisplayEnum;
import com.sxyl.portal.domain.constant.LinePositionEnum;
import com.sxyl.portal.domain.constant.ShowTextPositionEnum;
import com.sxyl.portal.domain.d.*;
import com.sxyl.portal.domain.graph.*;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.tree.BinaryTreeNode;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.service.CommonService;
import com.sxyl.portal.service.sort.SortService;
import com.sxyl.portal.service.tree.TreeCommonService;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class SortServiceImpl extends TreeCommonService implements SortService {



    @Override
    public TreeConstruct getHeapSortConstruct(int[] arrays, boolean minHeap) {
        /***
         * 构建数组
         */
        List<ArrayNode> arrayNodes = createArrayNode(arrays) ;
        /***
         * 初始化数组
         */
        BinaryTreeNode initNode = new BinaryTreeNode( arrayNodes.get(0).getCid() , arrayNodes.get(0).getValueTextId()  , arrayNodes.get(0).getValue().toString());
        BinaryTreeNode binaryTree = this.buildTree(initNode , arrayNodes , 0 );
        /***
         * 层序遍历树生成结构
         */
        Group group = getArrayAndTreeConstruct(arrayNodes , binaryTree);
        group = createLineConstruct(group , binaryTree ,true);

        //dnn动画
        AnimationTotal animationTotal =this.getSortAnimation( arrayNodes ,minHeap);

        return new TreeConstruct(group , animationTotal );


    }

    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
     * @param arr
     * @param i
     * @param length
     */
    public void adjustHeap(AnimationTotal animationTotal , List<ArrayNode> arr,int i,int length , boolean minHeap){
//        int temp = arr[i];//先取出当前元素i
        ArrayNode an = arr.get(i);
        for(int k=i*2+1;k<length;k=k*2+1){//从i结点的左子结点开始，也就是2i+1处开始
            if(minHeap){

                if(k+1<length && arr.get(k).getValue().intValue() > arr.get(k+1).getValue().intValue()){//如果左子结点大于右子结点，k指向右子结点
                    k++;
                }
                //比较俩个元素
                ChangeColor changeColor = new ChangeColor("red" , arr.get(k).getCid(),an.getCid());
                changeColor.setAd(String.format("比较父节点元素与子节点元素"));
                animationTotal.addComponent(changeColor);

                if(arr.get(k).getValue().intValue() < an.getValue().intValue()){//如果子节点小于父节点，将子节点值赋给父节点（不用进行交换）
                    animationTotal.addComponent(new Swap( new String[]{arr.get(k).getCid(),arr.get(k).getValueTextId()},
                            new String[]{an.getCid(),an.getValueTextId()},"由于子节点小于父节点,则交换位置"));

                    arr.set(i , arr.get(k));
                    i = k;
                    ChangeColor changeColor1 = new ChangeColor("white" , arr.get(k).getCid(),an.getCid());
                    changeColor1.setAd("");
                    animationTotal.addComponent(changeColor1);
                }else{
                    ChangeColor changeColor1 = new ChangeColor("white" , arr.get(k).getCid(),an.getCid());
                    changeColor1.setAd("");
                    animationTotal.addComponent(changeColor1);
                    break;
                }
            }else {
                if(k+1<length && arr.get(k).getValue().intValue() < arr.get(k+1).getValue().intValue()){//如果左子结点小于右子结点，k指向右子结点
                    k++;
                }
                //比较俩个元素
                ChangeColor changeColor = new ChangeColor("red" , arr.get(k).getCid(),an.getCid());
                changeColor.setAd(String.format("比较父节点元素与子节点元素"));
                animationTotal.addComponent(changeColor);

                if(arr.get(k).getValue().intValue() > an.getValue().intValue()){//如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                    animationTotal.addComponent(new Swap( new String[]{arr.get(k).getCid(),arr.get(k).getValueTextId()},
                            new String[]{an.getCid(),an.getValueTextId()} , "由于子节点大于父节点,则交换位置"));

                    arr.set(i , arr.get(k));
                    i = k;
                    ChangeColor changeColor1 = new ChangeColor("white" , arr.get(k).getCid(),an.getCid());
                    changeColor1.setAd("");
                    animationTotal.addComponent(changeColor1);

                }else{
                    ChangeColor changeColor1 = new ChangeColor("white" , arr.get(k).getCid(),an.getCid());
                    changeColor1.setAd("");
                    animationTotal.addComponent(changeColor1);
                    break;
                }
            }

        }
        arr.set(i , an);
    }

    /**
     * 交换元素
     * @param arr
     * @param a
     * @param b
     */
    public void swap(AnimationTotal animationTotal,List<ArrayNode> arr,int a ,int b){
        ArrayNode temp=arr.get(a);
        arr.set(a,arr.get(b));
        arr.set(b , temp);
        animationTotal.addComponent(new Swap( new String[]{arr.get(a).getCid(),arr.get(a).getValueTextId()},
                new String[]{arr.get(b).getCid(),arr.get(b).getValueTextId()} ,
                "将堆顶元素与末尾元素进行交换,交换后的末尾元素为已排序元素"));
    }





    /***
     * 层序遍历构建数组和树结构
     * @param binaryTreeNode
     * @return
     */
    private Group getArrayAndTreeConstruct(List<ArrayNode> arrayNodes , BinaryTreeNode binaryTreeNode) {
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

        int buffer = 500;
        int defaultML = 1100 ;
        double rate = 0.5;

        //层序遍历
        Queue<BinaryTreeNode> nodeQueue = new ArrayDeque<>();
        //每层节点的x队列。
        Queue<Integer> nodeWidthQueue = new ArrayDeque<>();
        nodeQueue.add(binaryTreeNode);
        nodeWidthQueue.add(defaultML);
        BinaryTreeNode temp ;
        int currentLevel = 1;    //记录当前层需要打印的节点的数量
        int nextLevel = 0;//记录下一层需要打印的节点的数量
        int currentIndex = 0 ;//当前层节点的序号
        Group group = new Group();
        Integer parentWidth = 0;
        Integer left ;
        while ((temp = nodeQueue.poll()) != null) {
            currentIndex = currentIndex + 1 ;
            if (currentIndex % 2 != 0 ) {
                parentWidth = nodeWidthQueue.poll() ;
                left = parentWidth - buffer;
                nodeWidthQueue.add(left.intValue());
            } else {
                left = parentWidth + buffer;
                nodeWidthQueue.add(left.intValue());
            }


            Circle circle = create(group , temp , left ,true);
            if (temp.getLeftNode() != null) {
                nodeQueue.add(temp.getLeftNode());
                nextLevel++;
            }
            if (temp.getRightNode() != null) {
                nodeQueue.add(temp.getRightNode());
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
     * 获取动画步骤的对象
     * @return
     */
    private AnimationTotal getSortAnimation(List<ArrayNode> arrayNodes , boolean minHeap){
        AnimationTotal animationTotal = new AnimationTotal();

        for (int i=0 ;i< arrayNodes.size() ; i++){
            ArrayNode arrayNode = arrayNodes.get(i);
            animationTotal.addComponent(new Move(arrayNode.getRid() , arrayNode.getCid(),
                    "将数组生成完全二叉树"
            ));
            animationTotal.addComponent(new Destroy(arrayNode.getRid() ));

            int parentIndex = (int)Math.ceil(i/(double)2)-1;
            if(parentIndex >= 0){
                animationTotal.addComponent(new Show(arrayNodes.get(parentIndex).getCid() +"-"+arrayNodes.get(i).getCid()));
            }
            animationTotal.addComponent(new Show(arrayNode.getCid(),arrayNode.getValueTextId()));
        }


        for(int i=arrayNodes.size()/2-1;i>=0;i--){
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(animationTotal,arrayNodes,i,arrayNodes.size() , minHeap);
        }

        //2.调整堆结构+交换堆顶元素与末尾元素
        for(int j=arrayNodes.size()-1;j>0;j--){
            swap(animationTotal,arrayNodes,0,j);//将堆顶元素与末尾元素进行交换

            //将末尾的元素置换成绿色
            animationTotal.addComponent(new ChangeColor("green" , arrayNodes.get(j).getCid()));

            adjustHeap(animationTotal ,arrayNodes,0,j , minHeap);//重新对堆进行调整
        }
        //构建队列
//        Queue parentQueue = createBinaryQueue(new ArrayDeque<>() , binaryTreeNode);

        animationTotal.addComponent(new ChangeColor("green" , arrayNodes.get(0).getCid()));
        return animationTotal;
    }



}
