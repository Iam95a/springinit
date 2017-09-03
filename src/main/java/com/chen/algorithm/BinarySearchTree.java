package com.chen.algorithm;

/**
 * 二叉查找树
 */
public class BinarySearchTree<T extends Comparable<? super T>> {
    private static class BinaryNode<T> {
        public BinaryNode(T element) {
            this(element, null, null);

        }

        public BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        private T element;
        private BinaryNode<T> left;
        private BinaryNode<T> right;
    }

    private BinaryNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void makeEmpty() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(T x) {

        return contains(x, root);
    }

    private boolean contains(T x, BinaryNode<T> ele) {
        if (ele == null) {
            return false;
        } else if (x.compareTo(ele.element) < 0) {
            return contains(x, ele.left);
        } else if (x.compareTo(ele.element) > 0) {
            return contains(x, ele.right);
        }
        return false;
    }

    public T findMin() {
        return findMin(root);
    }

    private T findMin(BinaryNode<T> ele) {
        if (ele == null) {
            return null;
        } else if (ele.left == null) {
            return ele.element;
        } else {
            return findMin(ele.left);
        }
    }

    public T findMax() {
        return findMax(root);
    }

    private T findMax(BinaryNode<T> ele) {
        if (ele == null) {
            return null;
        } else if (ele.right == null) {
            return ele.element;
        } else {
            return findMax(ele.right);
        }
    }

    //插入
    public void insert(T x) {
        BinaryNode<T> ele=new BinaryNode<T>(x);
        insert(ele, root);
    }

    //通过递归插入
    private void insert(BinaryNode<T> ele, BinaryNode<T> node) {
        if (node == null) {
            node = ele;
        }
        int compareStatus = ele.element.compareTo(node.element);
        if (compareStatus < 0) {
            insert(ele, node.left);
        } else if (compareStatus == 0) {
            node = ele;
        } else {
            insert(ele, node.right);
        }
    }

    public void remove(T x){
        remove(x,root);

    }

    /**
     *删除
     * 思路是如果这个节点没有子节点 可以直接删除
     * 一个子节点 将这个节点直接赋值给子节点
     * 如果有两个子节点  可以将这个子节点的右子节点的最小值和这个节点调换
     * 并把右子节点最小值位置上的删除
     */
    private void remove(T x,BinaryNode<T> node){
        if(node==null){
          return;
        }
        int compareStatus=x.compareTo(node.element);
        if(compareStatus<0){
            remove(x,node.left);
        }else if(compareStatus>0){
            remove(x,node.right);
        }else if(node.left!=null&&node.right!=null){
            node.element=findMin(node.right);
            remove(node.element,node.right);
        }else {
            //如果两边都是null  那么这个节点就直接赋值为null了
            //也就是父节点的这个节点的指向也变成了null
            node=(node.left!=null?node.left:node.right);
        }
    }

}
