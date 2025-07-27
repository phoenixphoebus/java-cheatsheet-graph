package com.aditya.java;

import com.aditya.java.pojo.Node;
import com.aditya.java.util.GraphUtil;

import java.util.*;

public class DFSGraph {

    public static void main(String[] args) {
        // A - B
        // |   |
        // C - D

        Map<String, Set<String>> toConvertToGraph = Map.ofEntries(
                Map.entry("A", Set.of("B", "C")),
                Map.entry("B", Set.of("A", "D")),
                Map.entry("C", Set.of("A", "D")),
                Map.entry("D", Set.of("B", "C"))
        );
        System.out.println(iterativeDFS(GraphUtil.convertMapToGraph(toConvertToGraph)));
    }

    public static <T> List<T> iterativeDFS(Node<T> node) {
        List<T> toReturn = new LinkedList<>();

        Set<T> visited = new HashSet<>();

        Stack<Node<T>> stack = new Stack<>();
        stack.push(node);

        do {
            Node<T> currentNode = stack.pop();
            //skip processing if node is visited
            if (!visited.contains(currentNode.data)) {
                //add node to dfs
                toReturn.add(currentNode.data);
                //add children that have not been visited to queue
                currentNode.connectedNodes.stream().filter(item -> !visited.contains(item.data)).forEach(stack::push);
                visited.add(currentNode.data);
            }
        } while (!stack.isEmpty());

        return toReturn;

    }

}
