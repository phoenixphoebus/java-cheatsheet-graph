package com.aditya.java.util;


import com.aditya.java.pojo.Node;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphUtilTest {

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

    @Test
    void convertConnectedNodesToSetOfDataShouldReturnSetOfDataInConnectedNodes(){
        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");
        nodeA.connectedNodes.add(nodeB);
        nodeA.connectedNodes.add(nodeC);
        assertEquals(Set.of("B","C"), GraphUtil.convertConnectedNodesToSetOfData(nodeA));
    }


}
