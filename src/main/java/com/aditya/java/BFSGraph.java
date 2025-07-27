package com.aditya.java;

import com.aditya.java.pojo.Node;
import com.aditya.java.util.GraphUtil;

import java.util.*;

public class BFSGraph {

     public static void main(String[] args){
         // A - B
         // |   |
         // C - D

         Map<String, Set<String>> toConvertToGraph = Map.ofEntries(
                 Map.entry("A", Set.of("B", "C")),
                 Map.entry("B", Set.of("A", "D")),
                 Map.entry("C", Set.of("A", "D")),
                 Map.entry("D", Set.of("B", "C"))
         );
         System.out.println(iterativeBFS(GraphUtil.convertMapToGraph(toConvertToGraph)));
     }

     public static <T> List<T> iterativeBFS(Node<T> node){
         List<T> toReturn = new LinkedList<>();

         Set<T> visited = new HashSet<>();

         Queue<Node<T>> queue = new LinkedList<>();
         queue.offer(node);

         do{
             Node<T> currentNode = queue.poll();
             //skip processing if node is visited
             if(!visited.contains(currentNode.data)){
                 //add node to bfs
                 toReturn.add(currentNode.data);
                 //add children that have not been visited to queue
                currentNode.connectedNodes.stream().filter(item-> !visited.contains(item.data)).forEach(queue::offer);
                 visited.add(currentNode.data);
             }
         }while(!queue.isEmpty());

         return toReturn;

     }

}
