package com.aditya.java;

import com.aditya.java.util.GraphUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class DFSGraphTest {

    // Verifies DFS order on a 4-node cycle; uses anyOf to account for nondeterministic Set iteration
    @Test
    void iterativeDFSShouldReturnCorrectDFSOrder(){
        // A - B
        // |   |
        // C - D

        Map<String, Set<String>> toConvertToGraph = Map.ofEntries(
                Map.entry("A", Set.of("B", "C")),
                Map.entry("B", Set.of("A", "D")),
                Map.entry("C", Set.of("A", "D")),
                Map.entry("D", Set.of("B", "C"))
        );

        List<String> dfs = DFSGraph.iterativeDFS(GraphUtil.convertMapToGraph(toConvertToGraph));
        assertThat(dfs, anyOf(
                is(List.of("A", "B", "D", "C")),
                is(List.of("B", "D", "C", "A")),
                is(List.of("D", "C", "A", "B")),
                is(List.of("C", "A", "B", "D")),
                is(List.of("A", "C", "D", "B")),
                is(List.of("C", "D", "B", "A")),
                is(List.of("D", "B", "A", "C")),
                is(List.of("B", "A", "C", "D"))
        ));
    }

    // Verifies DFS on a single isolated node returns a one-element list
    @Test
    void iterativeDFSShouldReturnSingleElementForSingleNodeGraph() {
        List<String> result = DFSGraph.iterativeDFS(GraphUtil.convertMapToGraph(Map.of("A", Set.of())));
        assertThat(result, is(List.of("A")));
    }

    // Verifies DFS traverses a 3-node linear chain visiting each node exactly once
    @Test
    void iterativeDFSShouldTraverseLinearChain() {
        // A - B - C
        Map<String, Set<String>> map = Map.ofEntries(
                Map.entry("A", Set.of("B")),
                Map.entry("B", Set.of("A", "C")),
                Map.entry("C", Set.of("B"))
        );
        List<String> result = DFSGraph.iterativeDFS(GraphUtil.convertMapToGraph(map));
        assertThat(result, anyOf(
                is(List.of("A", "B", "C")),
                is(List.of("B", "A", "C")),
                is(List.of("B", "C", "A")),
                is(List.of("C", "B", "A"))
        ));
    }

    // Verifies DFS visits all nodes of a tree graph regardless of start node or iteration order
    @Test
    void iterativeDFSShouldTraverseTreeGraph() {
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
        List<String> result = DFSGraph.iterativeDFS(GraphUtil.convertMapToGraph(map));
        assertThat(result, containsInAnyOrder("R", "A", "B", "C", "D"));
    }

    // Verifies DFS works with Integer-typed nodes, exercising the generic Node<T> parameter
    @Test
    void iterativeDFSShouldWorkWithIntegerNodes() {
        Map<Integer, Set<Integer>> map = Map.ofEntries(
                Map.entry(1, Set.of(2)),
                Map.entry(2, Set.of(1))
        );
        List<Integer> result = DFSGraph.iterativeDFS(GraphUtil.convertMapToGraph(map));
        assertThat(result, anyOf(is(List.of(1, 2)), is(List.of(2, 1))));
    }

}
