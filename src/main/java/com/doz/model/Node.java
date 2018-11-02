package com.doz.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Node", indexes = {@Index(name = "item_id_index", columnList = "ITEM_ID", unique = false)})
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "LEFT_LINK", nullable = false)
    private int leftLink;

    @Column(name = "RIGHT_LINK", nullable = false)
    private int rightLink;

    @JoinColumn(name = "TREE_ID", updatable = false, nullable = false, table = "TREE", referencedColumnName = "ID")
    private long treeId;

    @Column(name = "ITEM_ID", nullable = false)
    private long itemId;

    public Node(int left, int rightLink, long treeId) {
        this.id = 0L;
        this.leftLink = left;
        this.rightLink = rightLink;
        this.treeId = treeId;
    }

    public Node(long treeId) {
        this.treeId = treeId;
        this.id = 0;
    }

    public Node() {
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getItemId() {
        return itemId;
    }

    public long getTreeId() {
        return treeId;
    }

    public void setLeftLink(int leftLink) {
        this.leftLink = leftLink;
    }

    public void setRightLink(int rightLink) {
        this.rightLink = rightLink;
    }

    public int getLeftLink() {
        return leftLink;
    }

    public int getRightLink() {
        return rightLink;
    }
}
