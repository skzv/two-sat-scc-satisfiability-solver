package dev.skz;

import java.util.HashMap;
import java.util.HashSet;

public class TwoSatSatisfiabilitySolver {
    private final Graph graph;

    public TwoSatSatisfiabilitySolver(int numVariables) {
        graph = new Graph(2 * numVariables);
    }

    public void addClause(int a, int b) {
        if (a > 0) {
            if (b > 0) {
                // a v b
                graph.addEdge(-a, b);
                graph.addEdge(-b, a);
            } else {
                // a v -b
                graph.addEdge(a, b);
                graph.addEdge(-a, -b);
            }
        } else {
            if ( b > 0) {
                // -a v b
                graph.addEdge(-a, -b);
                graph.addEdge(b, a);
            } else {
                // -a v -b
                graph.addEdge(a, -b);
                graph.addEdge(b, -a);
            }
        }
    }

    public boolean isSatisfiable() {
        StronglyConnectedComponentComputer sccc = new StronglyConnectedComponentComputer(graph);
        HashMap<Integer, HashSet<Integer>> components = sccc.getStronglyConnectedComponentsAsHashSets();
        System.out.println("components: " + components);

        // The constraints are not satisfiable if the SCC contains contradicting implications.
        for (HashSet<Integer> component : components.values()) {
            for (int vertex : component) {
                if (component.contains(-vertex)) {
                    return false;
                }
            }
        }

        return true;
    }
}
