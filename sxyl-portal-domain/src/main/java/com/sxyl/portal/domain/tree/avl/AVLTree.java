package com.sxyl.portal.domain.tree.avl;


import com.sxyl.portal.common.JUUID;
import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.d.ChangeAttr;
import com.sxyl.portal.domain.d.ChangeAttrDetail;
import com.sxyl.portal.domain.d.MultiMove;
import com.sxyl.portal.domain.graph.Circle;
import com.sxyl.portal.domain.graph.GraphComponent;
import com.sxyl.portal.domain.graph.Text;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.tree.BaseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.controller.tree
 * @date:2020/6/8
 */
public class AVLTree extends BaseTree {

    AVLTreeNode root;

    private boolean animationFlag = false;


    private AnimationTotal animationTotal = new AnimationTotal();


    private Map<String ,AVLTreeNode> nodeMap = new HashMap<>();

    // A utility function to get the height of the tree
    public int height(AVLTreeNode N) {
        if (N == null)
            return 0;

        return N.getHeight();
    }

    // A utility function to get maximum of two integers
    public int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public AVLTreeNode rightRotate(AVLTreeNode y) {
        AVLTreeNode x = y.getLeft();
        AVLTreeNode T2 = x.getRight();

        // Perform rotation
        x.setRight(y);
        y.setLeft(T2);

        y.setParent(x);
        if(T2!=null){
            T2.setParent(y);

        }

        // Update heights
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);

        // Return new root
        return x;
    }

    public AVLTreeNode leftRotate(AVLTreeNode x) {
        AVLTreeNode y = x.getRight();
        AVLTreeNode T2 = y.getLeft();

        // Perform rotation
        y.setLeft(x);
        x.setRight(T2);

        //ww
        x.setParent(y);
        if (T2 != null){
            T2.setParent(x);

        }

        //  Update heights
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight( max(height(y.getLeft()), height(y.getRight())) + 1);

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    public int getBalance(AVLTreeNode N) {
        if (N == null)
            return 0;

        return height(N.getLeft()) - height(N.getRight());
    }

    public AVLTreeNode insert(AVLTreeNode node, int key,ArrayNode arrayNode ) {

        /* 1.  Perform the normal BST insertion */
        if (node == null){
            return (new AVLTreeNode(key , arrayNode));
        }

        if (key < node.getKey()){
            AVLTreeNode avlTreeNode = insert(node.getLeft(), key ,arrayNode);

            avlTreeNode.setParent(node);
            node.setLeft(avlTreeNode);

        }
        else if (key > node.getKey()){
            AVLTreeNode avlTreeNode =insert(node.getRight(), key ,arrayNode);
            avlTreeNode.setParent(node);
            node.setRight(avlTreeNode);

        }
        else { // Duplicate keys not allowed
            return node;
        }

        /* 2. Update height of this ancestor node */
        node.setHeight(1 + max(height(node.getLeft()),height(node.getRight())));

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && key < node.getLeft().getKey())
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.getRight().getKey()){
            //左旋转
            leftAnimation(node);

            return leftRotate(node);
        }


        // Left Right Case
        if (balance > 1 && key > node.getLeft().getKey()) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.getRight().getKey()) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    /***
     * 查询插入节点的父级节点
     * @param key
     * @param parent
     * @return
     */
    public AVLTreeNode findInserParent(AVLTreeNode node,AVLTreeNode parent, int key){
        if (node == null){
            return parent;
        }

        if (key < node.getKey()){
            return findInserParent(node.getLeft(),node , key);
        }else if (key > node.getKey()){
            return findInserParent(node.getRight() , node , key);
        }else {
            return node;
        }

    }

    public void preOrder(AVLTreeNode node) {
        if (node != null) {
            System.out.print(node.getKey() + " ");
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    /* Given a non-empty binary search tree, return the
  node with minimum key value found in that tree.
  Note that the entire tree does not need to be
  searched. */

    public AVLTreeNode minValueNode(AVLTreeNode node) {
        AVLTreeNode current = node;

        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null)
            current = current.getLeft();

        return current;
    }



    public AVLTreeNode deleteNode(AVLTreeNode root, int key){
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key < root.getKey())
            root.setLeft(deleteNode(root.getLeft(), key));

            // If the key to be deleted is greater than the
            // root's key, then it lies in right subtree
        else if (key > root.getKey())
            root.setRight(deleteNode(root.getRight(), key));

            // if key is same as root's key, then this is the node
            // to be deleted
        else {

            // node with only one child or no child
            if ((root.getLeft() == null) || (root.getRight() == null)){
                AVLTreeNode temp = null;
                if (temp == root.getLeft())
                    temp = root.getRight();
                else
                    temp = root.getLeft();

                // No child case
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
            }
            else
            {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                AVLTreeNode temp = minValueNode(root.getRight());

                // Copy the inorder successor's data to this node
                root.setKey(temp.getKey());

                // Delete the inorder successor
                root.setRight(deleteNode(root.getRight(), temp.getKey()));
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.setHeight(max(height(root.getLeft()), height(root.getRight())) + 1);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeft()) < 0)
        {
            root.setLeft(leftRotate(root.getLeft()));
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.getRight()) > 0)
        {
            root.setRight(rightRotate(root.getRight()));
            return leftRotate(root);
        }

        return root;
    }


    /****
     *
     * @param x
     *
     *
    x.setRight(T2);

     */
    private void leftAnimation(AVLTreeNode  x){
        if (animationFlag){

            AVLTreeNode  y = x.getRight();

            AVLTreeNode T2 = y.getLeft();

            ChangeAttr changeAttr = new ChangeAttr();
            MultiMove multiMove = new MultiMove();

            List<GraphComponent> list = new ArrayList<>();

            multiMove.setGcs(list);

            animationTotal.addComponent(multiMove);
            animationTotal.addComponent(changeAttr);


            List<ChangeAttrDetail> changeList = new ArrayList<>();
            changeAttr.setList(changeList);

            //x的基础属性
            int xWidth = x.getWidth();
            int xLevel = x.getLevel();
            int xY = compute(xLevel);

            int yWidth = y.getWidth();
            int yLevel = y.getLevel();
            int yBuffer = y.getBuffer();
            int yY =  compute(yLevel);


            int xNewLevel = xLevel + 1;
            int xNewWidth = xWidth - yBuffer;
            int xNewBuffer = y.getBuffer();
            int xNewY = compute(xNewLevel);


            int yNewLevel = yLevel -1;
            int yNewWidth = xWidth ;
            int yNewY = compute(yNewLevel);



            String ycid = y.getCid();
            String xcid = x.getCid();



            //增加x圆的动画
            this.addCircleAndTextAnimation(list,x.getCid(), x.getNodeTextId(),xNewWidth , xNewY);


            //增加y圆的动画
            this.addCircleAndTextAnimation(list,y.getCid(), y.getNodeTextId(),xWidth , xY);



            //增加 x , y 线的动画
            this.addLineAnimation(list  , xcid ,ycid,  ycid,xcid,
                     xNewWidth,xNewY - r ,yNewWidth , yNewY + r );

            /***
             * 交换 x 的父级节点到x的线的属性值
             */
            if (x.getParent() !=null){

                addChangeLineId(changeList , x.getParent().getCid() , x.getCid() , x.getParent().getCid(),y.getCid());
            }

            // x 的右侧节点的左侧节点变成 x左侧节点的右侧节点
            if (T2 !=null) {

                int T2y = compute(T2.getLevel());
                int T2Width = xNewWidth + T2.getBuffer() ;

                //增加x圆的动画
                this.addCircleAndTextAnimation(list,T2.getCid(), T2.getNodeTextId(),T2Width , T2y);

                //增加 x , y 线的动画
                this.addLineAnimation(list  , y.getCid() ,T2.getCid(),  x.getCid(), T2.getCid(),
                        xNewWidth,xNewY + r ,T2Width , T2y - r );

                //x的左节点下的双子节点
                refreshLeftRotateLeftChild(list,T2 ,T2.getLevel() ,T2Width ,T2y ,new Double(T2.getBuffer()*rate).intValue() ) ;
            }

            if (x.getLeft()!=null) {

                AVLTreeNode avlLeftTreeNode = x.getLeft() ;

                int xLeftNewBuffer = new Double(avlLeftTreeNode.getBuffer()*rate).intValue() ;
                int xLeftNewLevel = avlLeftTreeNode.getLevel() + 1;
                int xLeftNewWidth = avlLeftTreeNode.getWidth()-xLeftNewBuffer ;
                int xLeftNewY = compute(xLeftNewLevel);

                //增加x圆的动画
                this.addCircleAndTextAnimation(list,avlLeftTreeNode.getCid(), avlLeftTreeNode.getNodeTextId(),xLeftNewWidth , xLeftNewY);


                //增加 x , y 线的动画
                this.addLineAnimation(list  , x.getCid() ,x.getLeft().getCid(),  null, null,
                        x.getLeft().getWidth(),compute(x.getLeft().getLevel()) + r ,xLeftNewWidth , xLeftNewY - r );

                //x的左节点下的双子节点
                refreshLeftRotateLeftChild(list,x.getLeft() ,xLeftNewLevel ,xLeftNewWidth ,xLeftNewY ,new Double(xLeftNewBuffer*rate).intValue()) ;
            }

            if (y.getRight()!=null){

//                refreshLeftRotateRightNode(y , list);


                AVLTreeNode domRY = y.getRight();
//

                //增加圆的动画
                this.addCircleAndTextAnimation(list,domRY.getCid(), domRY.getNodeTextId(),yWidth , yY);
                //增加线的动画
                this.addLineAnimation(list ,ycid , domRY.getCid() ,null,null,
                        yNewWidth , yNewY + r , yWidth,yY - r);

//
//                if(y.getRight().getLeft() != null){
//                    y.getRight().getLeft().setBuffer( y.getBuffer());
//                }
//                if (y.getRight().getRight() != null ) {
//                    y.getRight().getRight().setBuffer( y.getBuffer());
//
//                }

                refreshLeftRotateLeftChild(list, y.getRight() , y.getLevel() , y.getWidth() , compute(y.getLevel()) ,new Double(y.getBuffer()*rate).intValue());

//                refreshLeftRotateLeftChild(list, y.getRight());

//                if (y.getRight().getRight() != null) {
//
//                    AVLTreeNode domRYRY = y.getRight().getRight();
//
//                    int domRYRYNewY = compute(domRY.getLevel());
//
//                    //增加x圆的动画
//                    this.addCircleAndTextAnimation(list,domRYRY.getCid(), domRYRY.getNodeTextId(),domRY.getWidth() , domRYRYNewY);
//
//                    //增加线的动画
//                    this.addLineAnimation(list  , domRY.getCid() ,domRYRY.getCid() ,null,null,
//                             yWidth,yY + r , domRY.getWidth() , domRYRYNewY  - r);
//
//                }
                domRY.getRight();
            }
        }
    }


    /***
     * 右旋
     * @param x
     */
    private void rightAnimation(AVLTreeNode  x){
        if (animationFlag){

            AVLTreeNode  y = x.getRight();

            AVLTreeNode T2 = y.getLeft();

            ChangeAttr changeAttr = new ChangeAttr();
            MultiMove multiMove = new MultiMove();

            List<GraphComponent> list = new ArrayList<>();

            multiMove.setGcs(list);

            animationTotal.addComponent(multiMove);
            animationTotal.addComponent(changeAttr);


            List<ChangeAttrDetail> changeList = new ArrayList<>();
            changeAttr.setList(changeList);

            //x的基础属性
            int xWidth = x.getWidth();
            int xLevel = x.getLevel();
            int xY = compute(xLevel);

            int yWidth = y.getWidth();
            int yLevel = y.getLevel();
            int yBuffer = y.getBuffer();
            int yY =  compute(yLevel);


            int xNewLevel = xLevel + 1;
            int xNewWidth = xWidth - yBuffer;
            int xNewBuffer = y.getBuffer();
            int xNewY = compute(xNewLevel);


            int yNewLevel = yLevel -1;
            int yNewWidth = xWidth ;
            int yNewY = compute(yNewLevel);



            String ycid = y.getCid();
            String xcid = x.getCid();



            //增加x圆的动画
            this.addCircleAndTextAnimation(list,x.getCid(), x.getNodeTextId(),xNewWidth , xNewY);


            //增加y圆的动画
            this.addCircleAndTextAnimation(list,y.getCid(), y.getNodeTextId(),xWidth , xY);



            //增加 x , y 线的动画
            this.addLineAnimation(list  , xcid ,ycid,  ycid,xcid,
                    xNewWidth,xNewY - r ,yNewWidth , yNewY + r );

            /***
             * 交换 x 的父级节点到x的线的属性值
             */
            if (x.getParent() !=null){

                addChangeLineId(changeList , x.getParent().getCid() , x.getCid() , x.getParent().getCid(),y.getCid());
            }

            // x 的右侧节点的左侧节点变成 x左侧节点的右侧节点
            if (T2 !=null) {

                int T2y = compute(T2.getLevel());
                int T2Width = xNewWidth + T2.getBuffer() ;

                //增加x圆的动画
                this.addCircleAndTextAnimation(list,T2.getCid(), T2.getNodeTextId(),T2Width , T2y);

                //增加 x , y 线的动画
                this.addLineAnimation(list  , y.getCid() ,T2.getCid(),  x.getCid(), T2.getCid(),
                        xNewWidth,xNewY + r ,T2Width , T2y - r );

                //x的左节点下的双子节点
                refreshLeftRotateLeftChild(list,T2 ,T2.getLevel() ,T2Width ,T2y ,new Double(T2.getBuffer()*rate).intValue() ) ;
            }

            if (x.getLeft()!=null) {

                AVLTreeNode avlLeftTreeNode = x.getLeft() ;

                int xLeftNewBuffer = new Double(avlLeftTreeNode.getBuffer()*rate).intValue() ;
                int xLeftNewLevel = avlLeftTreeNode.getLevel() + 1;
                int xLeftNewWidth = avlLeftTreeNode.getWidth()-xLeftNewBuffer ;
                int xLeftNewY = compute(xLeftNewLevel);

                //增加x圆的动画
                this.addCircleAndTextAnimation(list,avlLeftTreeNode.getCid(), avlLeftTreeNode.getNodeTextId(),xLeftNewWidth , xLeftNewY);


                //增加 x , y 线的动画
                this.addLineAnimation(list  , x.getCid() ,x.getLeft().getCid(),  null, null,
                        x.getLeft().getWidth(),compute(x.getLeft().getLevel()) + r ,xLeftNewWidth , xLeftNewY - r );

                //x的左节点下的双子节点
                refreshLeftRotateLeftChild(list,x.getLeft() ,xLeftNewLevel ,xLeftNewWidth ,xLeftNewY ,new Double(xLeftNewBuffer*rate).intValue()) ;
            }

            if (y.getRight()!=null){

//                refreshLeftRotateRightNode(y , list);


                AVLTreeNode domRY = y.getRight();
//

                //增加圆的动画
                this.addCircleAndTextAnimation(list,domRY.getCid(), domRY.getNodeTextId(),yWidth , yY);
                //增加线的动画
                this.addLineAnimation(list ,ycid , domRY.getCid() ,null,null,
                        yNewWidth , yNewY + r , yWidth,yY - r);

//
//                if(y.getRight().getLeft() != null){
//                    y.getRight().getLeft().setBuffer( y.getBuffer());
//                }
//                if (y.getRight().getRight() != null ) {
//                    y.getRight().getRight().setBuffer( y.getBuffer());
//
//                }

                refreshLeftRotateLeftChild(list, y.getRight() , y.getLevel() , y.getWidth() , compute(y.getLevel()) ,new Double(y.getBuffer()*rate).intValue());

//                refreshLeftRotateLeftChild(list, y.getRight());

//                if (y.getRight().getRight() != null) {
//
//                    AVLTreeNode domRYRY = y.getRight().getRight();
//
//                    int domRYRYNewY = compute(domRY.getLevel());
//
//                    //增加x圆的动画
//                    this.addCircleAndTextAnimation(list,domRYRY.getCid(), domRYRY.getNodeTextId(),domRY.getWidth() , domRYRYNewY);
//
//                    //增加线的动画
//                    this.addLineAnimation(list  , domRY.getCid() ,domRYRY.getCid() ,null,null,
//                             yWidth,yY + r , domRY.getWidth() , domRYRYNewY  - r);
//
//                }
                domRY.getRight();
            }
        }
    }

    private void refreshLeftRotateRightChild(List<GraphComponent> list , AVLTreeNode parentNode, int parentNewLevel ,
                                             int parentNewWidth,  int parentNewY){

//        if(parentNode.getLeft()!=null){
//
//            AVLTreeNode avlTreeNode = parentNode.getLeft();
//            int xNewLevel = parentNewLevel + 1;
//            int xNewBuffer = new Double(parentNode.left.getBuffer()*rate).intValue() ;
//            int xNewWidth = parentNode.left.getWidth() - xNewBuffer ;
//            int xNewY = compute(xNewLevel);
//            //重新刷新map里的x值
////            refreshMap(parentNode.left.getCid() , xNewLevel,xNewWidth,xNewBuffer);
//
//
//            //增加圆的动画
//            this.addCircleAndTextAnimation(list, avlTreeNode.getCid(), avlTreeNode.getNodeTextId(),xNewWidth , xNewY);
//            //增加线的动画
//            this.addLineAnimation(list ,parentNode.getCid() , avlTreeNode.getCid() ,null,null,
//                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);
//
////            refreshLeftRotateChild(list  ,changeList, parentNode.left ,xNewLevel,
////                    xNewWidth , xNewBuffer ,xNewY);
//        }
//
//        if (parentNode.right != null) {
//
//            AVLTreeNode avlTreeNode = parentNode.getRight();
//
//            int xRightNewBuffer = new Double(parentNode.right.getBuffer()*rate).intValue() ;
//            int xRightNewLevel = parentNewLevel - 1 ;
//            int xNewWidth = parentNewWidth + xRightNewBuffer ;
//            int xNewY = compute(xRightNewLevel);
//
//            //增加圆的动画
//            this.addCircleAndTextAnimation(list, avlTreeNode.getCid(), avlTreeNode.getNodeTextId(),xNewWidth , xNewY);
//            //增加线的动画
//            this.addLineAnimation(list ,parentNode.getCid() , avlTreeNode.getCid() ,null,null,
//                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);
//
//            //重新刷新map里的x值
////            refreshMap(parentNode.right.getCid() , xRightNewLevel,xNewWidth,xRightNewBuffer);
////            refreshLeftRotateChild(list , changeList , parentNode.right ,xRightNewLevel,
////                    xNewWidth , xRightNewBuffer ,xNewY);
//        }

    }



    private void refreshLeftRotateLeftChild(List<GraphComponent> list , AVLTreeNode parentNode, int parentNewLevel ,
                                        int parentNewWidth,  int parentNewY , int newBuffer){
        if(parentNode.getLeft()!=null){

            AVLTreeNode avlTreeNode = parentNode.getLeft();
            int xNewLevel = parentNewLevel + 1;
//            int xNewBuffer = new Double(parentNode.left.getBuffer()*rate).intValue() ;
            int xNewWidth = parentNewWidth - newBuffer ;
            int xNewY = compute(xNewLevel);
            //重新刷新map里的x值
//            refreshMap(parentNode.left.getCid() , xNewLevel,xNewWidth,xNewBuffer);


            //增加圆的动画
            this.addCircleAndTextAnimation(list, avlTreeNode.getCid(), avlTreeNode.getNodeTextId(),xNewWidth , xNewY);
            //增加线的动画
            this.addLineAnimation(list ,parentNode.getCid() , avlTreeNode.getCid() ,null,null,
                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);

            refreshLeftRotateLeftChild(list  , parentNode.getLeft() ,xNewLevel,
                    xNewWidth , compute(xNewLevel) ,new Double(newBuffer*rate).intValue() );
        }

        if (parentNode.right != null) {

            AVLTreeNode avlTreeNode = parentNode.getRight();

//            int xRightNewBuffer = new Double(parentNode.right.getBuffer()*rate).intValue() ;
            int xRightNewLevel = parentNewLevel + 1 ;
            int xNewWidth = parentNewWidth + newBuffer ;
            int xNewY = compute(xRightNewLevel);

            //增加圆的动画
            this.addCircleAndTextAnimation(list, avlTreeNode.getCid(), avlTreeNode.getNodeTextId(),xNewWidth , xNewY);
            //增加线的动画
            this.addLineAnimation(list ,parentNode.getCid() , avlTreeNode.getCid() ,null,null,
                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);


            refreshLeftRotateLeftChild(list  , parentNode.getRight() ,xRightNewLevel,
                    xNewWidth , compute(xRightNewLevel) ,new Double(newBuffer*rate).intValue() );

            //重新刷新map里的x值
//            refreshMap(parentNode.right.getCid() , xRightNewLevel,xNewWidth,xRightNewBuffer);
//            refreshLeftRotateChild(list , changeList , parentNode.right ,xRightNewLevel,
//                    xNewWidth , xRightNewBuffer ,xNewY);
        }
    }





    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

//        /* Constructing tree given in the above figure */
//        tree.root = tree.insert(tree.root, 10);
//        tree.root = tree.insert(tree.root, 20);
//        tree.root = tree.insert(tree.root, 30);
//        tree.root = tree.insert(tree.root, 40);
//        tree.root = tree.insert(tree.root, 50);
//        tree.root = tree.insert(tree.root, 25);
//
//        /* The constructed AVL Tree would be
//             30
//            /  \
//          20   40
//         /  \     \
//        10  25    50
//        */
//        System.out.println("Preorder traversal" +
//                " of constructed tree is : ");
//        tree.preOrder(tree.root);







        /* Constructing tree given in the above figure */
        tree.root = tree.insert(tree.root, 9 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(9)));
        tree.root = tree.insert(tree.root, 5,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(5)));
        tree.root = tree.insert(tree.root, 10 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(10)));
        tree.root = tree.insert(tree.root, 0 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(0)));
        tree.root = tree.insert(tree.root, 6 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(6)));
        tree.root = tree.insert(tree.root, 11 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(11)));
        tree.root = tree.insert(tree.root, -1,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(-1)));
        tree.root = tree.insert(tree.root, 1,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(1)));
        tree.root = tree.insert(tree.root, 2,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(2)));

        /* The constructed AVL Tree would be
        9
        / \
        1 10
        / \ \
        0 5 11
        / / \
        -1 2 6
        */
        System.out.println("Preorder traversal of "+
                "constructed tree is : ");
        tree.preOrder(tree.root);

        tree.root = tree.deleteNode(tree.root, 10);

        /* The AVL Tree after deletion of 10
        1
        / \
        0 9
        /     / \
        -1 5 11
        / \
        2 6
        */
        System.out.println("");
        System.out.println("Preorder traversal after "+
                "deletion of 10 :");
        tree.preOrder(tree.root);

    }

    public AVLTreeNode getRoot() {
        return root;
    }

    public void setRoot(AVLTreeNode root) {
        this.root = root;
    }

    public AnimationTotal getAnimationTotal() {
        return animationTotal;
    }

    public void setAnimationTotal(AnimationTotal animationTotal) {
        this.animationTotal = animationTotal;
    }

    public boolean isAnimationFlag() {
        return animationFlag;
    }

    public void setAnimationFlag(boolean animationFlag) {
        this.animationFlag = animationFlag;
    }
}
