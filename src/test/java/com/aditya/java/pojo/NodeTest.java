package com.aditya.java.pojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    void equalsShouldReturnTrueForSameInstance() {
        Node<String> node = new Node<>("A");
        assertEquals(node, node);
    }

    @Test
    void equalsShouldReturnTrueForEqualNodes() {
        Node<String> node1 = new Node<>("A");
        node1.connectedNodes.add(new Node<>("B"));

        Node<String> node2 = new Node<>("A");
        node2.connectedNodes.add(new Node<>("B"));

        assertEquals(node1, node2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentData() {
        Node<String> node1 = new Node<>("A");
        Node<String> node2 = new Node<>("B");
        assertNotEquals(node1, node2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentConnections() {
        Node<String> node1 = new Node<>("A");
        node1.connectedNodes.add(new Node<>("B"));

        Node<String> node2 = new Node<>("A");
        node2.connectedNodes.add(new Node<>("C"));

        assertNotEquals(node1, node2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        Node<String> node = new Node<>("A");
        assertFalse(node.equals(null));
    }

    @Test
    void equalsShouldReturnFalseForDifferentClass() {
        Node<String> node = new Node<>("A");
        assertFalse(node.equals("A"));
    }

    @Test
    void hashCodeShouldBeEqualForEqualNodes() {
        Node<String> node1 = new Node<>("A");
        node1.connectedNodes.add(new Node<>("B"));

        Node<String> node2 = new Node<>("A");
        node2.connectedNodes.add(new Node<>("B"));

        assertEquals(node1.hashCode(), node2.hashCode());
    }

    @Test
    void toStringShouldContainDataAndConnections() {
        Node<String> node = new Node<>("A");
        node.connectedNodes.add(new Node<>("B"));

        String result = node.toString();
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
    }

    @Test
    void getDataShouldReturnCorrectValue() {
        Node<Integer> node = new Node<>(42);
        assertEquals(42, node.getData());
    }

}
