/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Gabriel
 * @param <T>
 */
public class TreeNode<T> implements Iterable<TreeNode<T>> {

    T data;
    TreeNode<T> parent;
    List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<>();
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}