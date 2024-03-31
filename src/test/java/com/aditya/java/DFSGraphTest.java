package com.aditya.java;

import com.aditya.java.util.GraphUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class DFSGraphTest {

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

}
