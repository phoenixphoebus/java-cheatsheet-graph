package com.aditya.java;

import com.aditya.java.BFSGraph;
import com.aditya.java.util.GraphUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class BFSGraphTest {

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

}
