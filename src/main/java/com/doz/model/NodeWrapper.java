package com.doz.model;

public class NodeWrapper<T> {

    private T value;
    private final Node node;

    public NodeWrapper(T value, Node node) {
        this.value = value;
        this.node = node;
    }

    public T getValue() {
        return value;
    }

    public <V> NodeWrapper<V> changeValue(V otherValue) {
        return new NodeWrapper<>(otherValue, node);
    }
}
