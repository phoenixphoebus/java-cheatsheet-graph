package com.aditya.java.pojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    // Verifies reflexivity: a node is always equal to itself
    @Test
    void equalsShouldReturnTrueForSameInstance() {
        Node<String> node = new Node<>("A");
        assertEquals(node, node);
    }

    // Verifies structural equality: two nodes with the same data and neighbour data are equal
    @Test
    void equalsShouldReturnTrueForEqualNodes() {
        Node<String> node1 = new Node<>("A");
        node1.connectedNodes.add(new Node<>("B"));

        Node<String> node2 = new Node<>("A");
        node2.connectedNodes.add(new Node<>("B"));

        assertEquals(node1, node2);
    }

    // Verifies that nodes with different data values are not equal
    @Test
    void equalsShouldReturnFalseForDifferentData() {
        Node<String> node1 = new Node<>("A");
        Node<String> node2 = new Node<>("B");
        assertNotEquals(node1, node2);
    }

    // Verifies that nodes with the same data but different neighbours are not equal
    @Test
    void equalsShouldReturnFalseForDifferentConnections() {
        Node<String> node1 = new Node<>("A");
        node1.connectedNodes.add(new Node<>("B"));

        Node<String> node2 = new Node<>("A");
        node2.connectedNodes.add(new Node<>("C"));

        assertNotEquals(node1, node2);
    }

    // Verifies null-safety: equals returns false when compared to null
    @Test
    void equalsShouldReturnFalseForNull() {
        Node<String> node = new Node<>("A");
        assertFalse(node.equals(null));
    }

    // Verifies type-safety: equals returns false when compared to a different class
    @Test
    void equalsShouldReturnFalseForDifferentClass() {
        Node<String> node = new Node<>("A");
        assertFalse(node.equals("A"));
    }

    // Verifies the hashCode contract: equal nodes must produce the same hash code
    @Test
    void hashCodeShouldBeEqualForEqualNodes() {
        Node<String> node1 = new Node<>("A");
        node1.connectedNodes.add(new Node<>("B"));

        Node<String> node2 = new Node<>("A");
        node2.connectedNodes.add(new Node<>("B"));

        assertEquals(node1.hashCode(), node2.hashCode());
    }

    // Verifies that toString output includes both the node's data and its neighbour data
    @Test
    void toStringShouldContainDataAndConnections() {
        Node<String> node = new Node<>("A");
        node.connectedNodes.add(new Node<>("B"));

        String result = node.toString();
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
    }

    // Verifies getData() returns the correct value; also exercises Node<Integer> to validate generics
    @Test
    void getDataShouldReturnCorrectValue() {
        Node<Integer> node = new Node<>(42);
        assertEquals(42, node.getData());
    }

}
