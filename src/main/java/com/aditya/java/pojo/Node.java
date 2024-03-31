package com.aditya.java.pojo;

import com.aditya.java.util.GraphUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Node<T> {

    public T data;
    public Set<Node<T>> connectedNodes;

    public Node(T data) {
        super();
        this.data = data;
        this.connectedNodes = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(data, node.data) && Objects.equals(GraphUtil.convertConnectedNodesToSetOfData(this),
                GraphUtil.convertConnectedNodesToSetOfData(node));
    }



    @Override
    public int hashCode() {
        return Objects.hash(data, GraphUtil.convertConnectedNodesToSetOfData(this));
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", connectedNodes=" + GraphUtil.convertConnectedNodesToSetOfData(this) +
                '}';
    }
}
