package com.aditya.java.util;

import com.aditya.java.pojo.Node;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class GraphUtil {

    public static <T> Node<T> convertMapToGraph(Map<T, Set<T>> map) {
        Map<T, Node<T>> nodeCache = new HashMap<>();
        Node<T> toReturn = null;

        for (Entry<T, Set<T>> entry : map.entrySet()) {
            if (entry.getValue().isEmpty() && map.size()!=1) {
                throw new IllegalArgumentException(
                        "Found Node that isn't connected to any other nodes: " + entry.getKey());
            } else {
                Node<T> currentNode = nodeCache.computeIfAbsent(entry.getKey(), (T) -> new Node<>(entry.getKey()));
                toReturn = currentNode;
                // Connect to node that exist and create new ones if they don't exist
                currentNode.connectedNodes = entry.getValue().stream()
                        .map(item -> nodeCache.computeIfAbsent(item, (T) -> new Node<>(item)))
                        .collect(Collectors.toSet());
            }
        }

        return toReturn;
    }
    
    public static <T> Map<T, Set<T>> convertGraphToMap(Node<T> node){
        Map<T, Set<T>> toReturn = new HashMap<>();
        Set<T> visited = new HashSet<>();

        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(node);

        do{
            Node<T> currentNode = queue.poll();
            //get node and write entry to map
            toReturn.put(currentNode.data, GraphUtil.convertConnectedNodesToSetOfData(currentNode));
            //its connected nodes go into queue if they are not visited set
            currentNode.connectedNodes.stream()
                    .filter(item -> !visited.contains(item.data))
                    .forEach(queue::add);
            //add currentNode to visited set
            visited.add(currentNode.data);
        }while(!queue.isEmpty());
        return toReturn;
    }

    public static <T> Set<T> convertConnectedNodesToSetOfData(Node<T> node) {
        return node.connectedNodes.stream().map(item -> item.data).collect(Collectors.toSet());
    }

}
