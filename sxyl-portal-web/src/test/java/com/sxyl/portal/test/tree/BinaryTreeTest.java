package com.sxyl.portal.test.tree;

import com.sxyl.portal.domain.tree.test.BinaryTree;
import org.junit.Before;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.test.tree
 * @date:2020/4/7
 */
public class BinaryTreeTest {

    BinaryTree<Integer> binaryTree = new BinaryTree<>();

    @Before
    public void init() throws Exception{
        binaryTree.insert(15);
        binaryTree.insert(27);
        binaryTree.insert(18);
        binaryTree.insert(8);
        binaryTree.insert(20);
        binaryTree.insert(28);
        binaryTree.insert(30);
        binaryTree.insert(13);
        binaryTree.insert(13);
        binaryTree.insert(26);
        binaryTree.insert(20);
        binaryTree.insert(21);

        binaryTree.findMax();

    }

    @org.junit.Test
    public void makeEmpty() throws Exception {
        System.out.println(binaryTree.isEmpty());
        binaryTree.makeEmpty();
        System.out.println(binaryTree.isEmpty());
    }

    @org.junit.Test
    public void contains() throws Exception {
        System.out.println(binaryTree.contains(3));
        System.out.println(binaryTree.contains(8));
        System.out.println(binaryTree.contains(null));
    }

    @org.junit.Test
    public void findMin() throws Exception {
        System.out.println(binaryTree.isEmpty());
        System.out.println(binaryTree.findMin());
    }

    @org.junit.Test
    public void findMax() throws Exception {
        System.out.println(binaryTree.isEmpty());
        System.out.println(binaryTree.findMax());
    }

    @org.junit.Test
    public void insert() throws Exception {
        System.out.println(binaryTree.getRoot());
        binaryTree.preOrder(binaryTree.getRoot());
    }

    @org.junit.Test
    public void preOrder() throws Exception {
        binaryTree.preOrder(binaryTree.getRoot());
    }

    @org.junit.Test
    public void flatten() throws Exception {
        binaryTree.flatten(binaryTree.getRoot());
        System.out.println(binaryTree.getRoot());
    }

    @org.junit.Test
    public void inOrder() throws Exception {
        binaryTree.inOrder(binaryTree.getRoot());
    }

    @org.junit.Test
    public void postOrder() throws Exception {
        binaryTree.postOrder(binaryTree.getRoot());
    }

    @org.junit.Test
    public void printTree() throws Exception {
        binaryTree.printTree();
        binaryTree.inOrder(binaryTree.getRoot());
    }

    @org.junit.Test
    public void height() throws Exception {
        System.out.println(binaryTree.height(binaryTree.getRoot()));
        //System.out.println(binaryTree.height(binaryTree.getRoot().left));
    }

    @org.junit.Test
    public void remove() throws Exception {
        binaryTree.printTree();
        System.out.println();
        binaryTree.remove(2, binaryTree.getRoot());
        binaryTree.printTree();
    }

    @org.junit.Test
    public void reconstructBinaryTree() throws Exception {
        int[] pre = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] in  = {4, 7, 2, 1, 5, 3, 8, 6};

        BinaryTree binaryTree1 = new BinaryTree(BinaryTree.reconstructBinaryTree(pre, 0, 7, in, 0, 7));
        binaryTree1.postOrder(binaryTree1.getRoot());
    }
}
