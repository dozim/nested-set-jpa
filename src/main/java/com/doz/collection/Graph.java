package com.doz.collection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.doz.model.Node;
import org.jetbrains.annotations.NotNull;

public class Graph implements Iterable<Node>{

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
            count = applyNestedSetAlgorithm(childNode, count++);
        }

        node.setRightLink(count++);
        return count;
    }

    public Set<Node> getNodesAsNestedSet() {
        applyNestedSetAlgorithm(rootNode.getNode(), 0);
        return quickFinder.keySet();
    }

    @NotNull
    @Override
    public Iterator<Node> iterator() {
        return new Iterator<Node>() {

            private NodeList current = rootNode;
            private int index = 0;

            @Override
            public boolean hasNext() {
                return current != null && quickFinder.keySet().size() < index;
            }

            @Override
            public Node next() {
                NodeList nodeList = getNodeListForIndex(index, current);

                return nodeList.getNode();
            }

            private NodeList getNodeListForIndex(int index, NodeList current) {
                if (index == 0) {
                    return current;
                } else if (index > current.getChildren().size()){
                   return null;
                    // return getNodeListForIndex(index - current.getChildren().size(), )
                } else {
                    return current.getChildren().get(index);
                }
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Node> consumer) {

    }

    @Override
    public Spliterator<Node> spliterator() {
        return null;
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

