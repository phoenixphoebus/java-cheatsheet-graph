package com.aditya.java.util;


import com.aditya.java.pojo.Node;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphUtilTest {

    // Verifies a manually-built 4-node cycle graph is correctly serialized to an adjacency map
    @Test
    void convertGraphToMapShouldCreateMapCorrectly() {

        Map<String, Set<String>> expectedMap = Map.ofEntries(
                Map.entry("A", Set.of("B", "C")),
                Map.entry("B", Set.of("A", "D")),
                Map.entry("C", Set.of("A", "D")),
                Map.entry("D", Set.of("B", "C"))
        );

        Node<String> nodeA = createBasicGraphManually();
        Map<String, Set<String>> mapOfGraph = GraphUtil.convertGraphToMap(nodeA);

        assertEquals(expectedMap, mapOfGraph);
    }

    // Verifies that converting a map to a graph and back produces the same adjacency map
    @Test
    void convertMapToGraphShouldCreateGraphCorrectly() {

        Node<String> nodeA = createBasicGraphManually();
        Map<String, Set<String>> mapOfManuallyCreatedGraph = GraphUtil.convertGraphToMap(nodeA);

        Map<String, Set<String>> toConvertToGraph = Map.ofEntries(
                Map.entry("A", Set.of("B", "C")),
                Map.entry("B", Set.of("A", "D")),
                Map.entry("C", Set.of("A", "D")),
                Map.entry("D", Set.of("B", "C"))
        );
        Map<String, Set<String>> mapOfGraphOfMap = GraphUtil.convertGraphToMap(
                GraphUtil.convertMapToGraph(toConvertToGraph));

        assertEquals(mapOfManuallyCreatedGraph, mapOfGraphOfMap);

    }

    private static Node<String> createBasicGraphManually() {
        // A - B
        // |   |
        // C - D

        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");
        Node<String> nodeD = new Node<>("D");
        nodeA.connectedNodes.add(nodeB);
        nodeA.connectedNodes.add(nodeC);
        nodeB.connectedNodes.add(nodeA);
        nodeB.connectedNodes.add(nodeD);
        nodeC.connectedNodes.add(nodeA);
        nodeC.connectedNodes.add(nodeD);
        nodeD.connectedNodes.add(nodeB);
        nodeD.connectedNodes.add(nodeC);
        return nodeA;
    }

    // Verifies that connected node objects are correctly mapped to a set of their data values
    @Test
    void convertConnectedNodesToSetOfDataShouldReturnSetOfDataInConnectedNodes(){
        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");
        nodeA.connectedNodes.add(nodeB);
        nodeA.connectedNodes.add(nodeC);
        assertEquals(Set.of("B","C"), GraphUtil.convertConnectedNodesToSetOfData(nodeA));
    }

    // Verifies that an empty map input returns null
    @Test
    void convertMapToGraphShouldReturnNullForEmptyMap() {
        Map<String, Set<String>> emptyMap = new HashMap<>();
        Node<String> result = GraphUtil.convertMapToGraph(emptyMap);
        assertNull(result);
    }

    // Verifies the single-node special case: a map with one isolated entry is handled without throwing
    @Test
    void convertMapToGraphShouldHandleSingleIsolatedNode() {
        Node<String> result = GraphUtil.convertMapToGraph(Map.of("A", Set.of()));
        assertNotNull(result);
        assertEquals("A", result.getData());
        assertTrue(result.connectedNodes.isEmpty());
    }

    // Verifies that a disconnected node in a multi-node graph throws IllegalArgumentException
    @Test
    void convertMapToGraphShouldThrowForDisconnectedNode() {
        Map<String, Set<String>> map = Map.ofEntries(
                Map.entry("A", Set.of("B")),
                Map.entry("B", Set.of("A")),
                Map.entry("C", Set.of())
        );
        assertThrows(IllegalArgumentException.class, () -> GraphUtil.convertMapToGraph(map));
    }

    // Verifies that a neighbour referenced in values but absent as a key is created with no connections
    @Test
    void convertMapToGraphShouldHandleNodeReferencedButMissingAsKey() {
        Map<String, Set<String>> map = Map.of("A", Set.of("B"));
        Node<String> result = GraphUtil.convertMapToGraph(map);

        assertEquals("A", result.getData());
        assertEquals(1, result.connectedNodes.size());
        Node<String> bNode = result.connectedNodes.iterator().next();
        assertEquals("B", bNode.getData());
        assertTrue(bNode.connectedNodes.isEmpty());
    }

    // Verifies that a lone node with no connections converts to a map entry with an empty set
    @Test
    void convertGraphToMapShouldHandleSingleNode() {
        Node<String> node = new Node<>("A");
        Map<String, Set<String>> result = GraphUtil.convertGraphToMap(node);
        assertEquals(Map.of("A", Set.of()), result);
    }

    // Verifies that a node with no neighbours returns an empty set
    @Test
    void convertConnectedNodesToSetOfDataShouldReturnEmptySetForIsolatedNode() {
        Node<String> node = new Node<>("A");
        assertEquals(Set.of(), GraphUtil.convertConnectedNodesToSetOfData(node));
    }

    // Verifies the round-trip map->graph->map works for Integer-typed nodes, exercising generics
    @Test
    void convertMapToGraphShouldWorkWithIntegerNodes() {
        Map<Integer, Set<Integer>> map = Map.ofEntries(
                Map.entry(1, Set.of(2)),
                Map.entry(2, Set.of(1))
        );
        Map<Integer, Set<Integer>> expected = Map.ofEntries(
                Map.entry(1, Set.of(2)),
                Map.entry(2, Set.of(1))
        );
        assertEquals(expected, GraphUtil.convertGraphToMap(GraphUtil.convertMapToGraph(map)));
    }


}
