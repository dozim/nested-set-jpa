package com.doz.collection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.doz.model.Node;
import org.jetbrains.annotations.NotNull;

public class GraphCreator {

    private static final NodeList EMPTY_NODELIST = new NodeList(null, null);

    private final Map<Node, NodeList> quickFinder = new HashMap<>();
    private NodeList rootNode;

    public void addRootNode(Node node) {
        rootNode = new NodeList(null, node);
        quickFinder.put(node, rootNode);
    }

    public void addChildren(Node parent, Node... newChildren) {
        NodeList nodeList = quickFinder.get(parent);

        if (Arrays.stream(newChildren).anyMatch(quickFinder::containsKey)) {
            throw new UnsupportedOperationException("The children nodes are not allowed to have a parent");
        } else if (nodeList == null) {
            throw new UnsupportedOperationException("Parent node does not exist in graph");
        } else {
            List<NodeList> children = nodeList.getChildren();
            Arrays.stream(newChildren)
                    .map(node -> new NodeList(parent, node))
                    .forEach(newNodeList -> {
                        children.add(newNodeList);
                        quickFinder.put(newNodeList.getNode(), newNodeList);
                    });
        }
    }

    public void removeNode(Node node) {
        NodeList nodeList = quickFinder.get(node);

        if (nodeList != null) {
            removeChildrenNodes(nodeList);
            quickFinder.remove(nodeList.getNode());
            removeNodeFromParentChildren(nodeList);
        }
    }

    private void removeNodeFromParentChildren(NodeList nodeList) {
        Node parent = nodeList.getParent();
        if (parent != null) {
            quickFinder.get(parent)
                    .getChildren()
                    .remove(nodeList);
        }
    }

    private void removeChildrenNodes(NodeList nodeList) {
        nodeList.getChildren()
                .stream()
                .map(NodeList::getNode)
                .forEach(this::removeNode);
    }

    private int applyNestedSetAlgorithm(Node node, int count) {
        node.setLeftLink(count++);

        NodeList nodeList = quickFinder.getOrDefault(node, EMPTY_NODELIST);

        for (NodeList list : nodeList.getChildren()) {
            Node childNode = list.getNode();
            count = applyNestedSetAlgorithm(childNode, count);
        }

        node.setRightLink(count++);
        return count;
    }

    private void applyPartialNestedSetAlgorithm(Node parent, Node child) {
        increaseLinksForNewNodeAndItsParent(quickFinder.get(child));

        quickFinder.keySet()
                .stream()
                .filter(node -> node.getRightLink() > parent.getRightLink() && node.getLeftLink() < parent.getRightLink())
                .forEach(node -> node.setRightLink(node.getRightLink() + 2));

        quickFinder.keySet()
                .stream()
                .filter(node -> node.getRightLink() > parent.getRightLink() && node.getLeftLink() > parent.getRightLink())
                .forEach(node -> {
                    node.setLeftLink(node.getLeftLink() + 2);
                    node.setRightLink(node.getRightLink() + 2);
                });
    }

    private void applyPartialNestedSetAlgorithm(NodeList nodeList) {
        NodeList parentNodeList = increaseLinksForNewNodeAndItsParent(nodeList);
        increaseFollowingNodesLinks(parentNodeList, nodeList);
    }

    @NotNull
    private NodeList increaseLinksForNewNodeAndItsParent(NodeList nodeList) {
        NodeList parentNodeList = quickFinder.get(nodeList.getParent());
        Node parent = parentNodeList.getParent();
        Node node = nodeList.getNode();
        int index = parentNodeList.getChildren().indexOf(nodeList);
        if (index > 0) {
            NodeList precessedNodeList = parentNodeList.getChildren().get(index - 1);
            Node precessedNode = precessedNodeList.getNode();
            node.setLeftLink(precessedNode.getLeftLink() + 2);
            node.setRightLink(precessedNode.getRightLink() + 2);
        } else {
            node.setLeftLink(parent.getLeftLink() + 2);
            node.setRightLink(parent.getRightLink() + 2);
        }
        parent.setRightLink(parent.getRightLink() + 2);
        return parentNodeList;
    }

    private void increaseFollowingNodesLinks(NodeList parentNodeList, NodeList childNodeList) {
        List<NodeList> children = parentNodeList.getChildren();
        int index = 0;

        for (; index < children.size(); index++) {
            NodeList child = children.get(index);
            if (child == childNodeList) {
                index++;
                break;
            }
        }

        for (int i = index; i < children.size(); i++) {
            NodeList child = children.get(i);

        }


        /*if (foundChild) {
            Node node = child.getNode();
            node.setLeftLink(node.getLeftLink() + 2);

        }*/
    }

    public Set<Node> getNodesAsNestedSet() {
        applyNestedSetAlgorithm(rootNode.getNode(), 0);
        return quickFinder.keySet();
    }

    private static class NodeList {
        private final Node parent;
        private final Node node;
        private final List<NodeList> children;

        private NodeList(Node parent, Node node) {
            this.parent = parent;
            this.node = node;
            this.children = new LinkedList<>();
        }

        private List<NodeList> getChildren() {
            return children;
        }

        private Node getParent() {
            return parent;
        }

        private Node getNode() {
            return node;
        }
    }
}

