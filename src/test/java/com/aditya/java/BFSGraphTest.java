package com.aditya.java;

import com.aditya.java.BFSGraph;
import com.aditya.java.util.GraphUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class BFSGraphTest {

    // Verifies BFS order on a 4-node cycle; uses anyOf to account for nondeterministic Set iteration
    @Test
    void iterativeBFSShouldReturnCorrectBFSOrder(){
        // A - B
        // |   |
        // C - D

        Map<String, Set<String>> toConvertToGraph = Map.ofEntries(
                Map.entry("A", Set.of("B", "C")),
                Map.entry("B", Set.of("A", "D")),
                Map.entry("C", Set.of("A", "D")),
                Map.entry("D", Set.of("B", "C"))
        );

        List<String> bfs = BFSGraph.iterativeBFS(GraphUtil.convertMapToGraph(toConvertToGraph));
        assertThat(bfs, anyOf(
                is(List.of("D", "C", "B", "A")),
                is(List.of("D", "B", "C", "A")),
                is(List.of("A", "B", "C", "D")),
                is(List.of("A", "C", "B", "D")),
                is(List.of("C", "A", "D", "B")),
                is(List.of("C", "D", "A", "B")),
                is(List.of("B", "D", "A", "C")),
                is(List.of("B", "D", "A", "C"))
                ));
    }

    // Verifies BFS on a single isolated node returns a one-element list
    @Test
    void iterativeBFSShouldReturnSingleElementForSingleNodeGraph() {
        List<String> result = BFSGraph.iterativeBFS(GraphUtil.convertMapToGraph(Map.of("A", Set.of())));
        assertThat(result, is(List.of("A")));
    }

    // Verifies BFS traverses a 3-node linear chain visiting each node exactly once
    @Test
    void iterativeBFSShouldTraverseLinearChain() {
        // A - B - C
        Map<String, Set<String>> map = Map.ofEntries(
                Map.entry("A", Set.of("B")),
                Map.entry("B", Set.of("A", "C")),
                Map.entry("C", Set.of("B"))
        );
        List<String> result = BFSGraph.iterativeBFS(GraphUtil.convertMapToGraph(map));
        assertThat(result, anyOf(
                is(List.of("A", "B", "C")),
                is(List.of("B", "A", "C")),
                is(List.of("B", "C", "A")),
                is(List.of("C", "B", "A"))
        ));
    }

    // Verifies BFS visits all nodes of a tree graph regardless of start node or iteration order
    @Test
    void iterativeBFSShouldTraverseTreeGraph() {
        //     R
        //    / \
        //   A   B
        //  / \
        // C   D
        Map<String, Set<String>> map = Map.ofEntries(
                Map.entry("R", Set.of("A", "B")),
                Map.entry("A", Set.of("R", "C", "D")),
                Map.entry("B", Set.of("R")),
                Map.entry("C", Set.of("A")),
                Map.entry("D", Set.of("A"))
        );
        List<String> result = BFSGraph.iterativeBFS(GraphUtil.convertMapToGraph(map));
        assertThat(result, containsInAnyOrder("R", "A", "B", "C", "D"));
    }

    // Verifies BFS works with Integer-typed nodes, exercising the generic Node<T> parameter
    @Test
    void iterativeBFSShouldWorkWithIntegerNodes() {
        Map<Integer, Set<Integer>> map = Map.ofEntries(
                Map.entry(1, Set.of(2)),
                Map.entry(2, Set.of(1))
        );
        List<Integer> result = BFSGraph.iterativeBFS(GraphUtil.convertMapToGraph(map));
        assertThat(result, anyOf(is(List.of(1, 2)), is(List.of(2, 1))));
    }

}
