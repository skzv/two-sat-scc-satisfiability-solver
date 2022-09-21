package dev.skz;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Math.abs;

public class TwoSatSatisfiabilitySolver {
    private final Graph graph;
    private final int numVariables;

    public TwoSatSatisfiabilitySolver(int numVariables) {
        this.numVariables = numVariables;
        graph = new Graph(2 * numVariables);
    }

    public void addClause(int a, int b) {
        int A = abs(a);
        int B = abs(b);
        if (a > 0) {
            if (b > 0) {
                // Case: a v b
                // Implications:
                // -a => b
                // -b => a
                graph.addEdge(mapVariableToGraphKey(-A), mapVariableToGraphKey(B));
                graph.addEdge(mapVariableToGraphKey(-B), mapVariableToGraphKey(A));
            } else {
                // Case: a v -b
                // Implications:
                // b => a
                // -a => -b
                graph.addEdge(mapVariableToGraphKey(B), mapVariableToGraphKey(A));
                graph.addEdge(mapVariableToGraphKey(-A), mapVariableToGraphKey(-B));
            }
        } else {
            if ( b > 0) {
                // Case: -a v b
                // Implications:
                // -a => -b
                // b => a
                graph.addEdge(mapVariableToGraphKey(A), mapVariableToGraphKey(B));
                graph.addEdge(mapVariableToGraphKey(-B), mapVariableToGraphKey(-A));
            } else {
                // Case: -a v -b
                // a => -b
                // b => -a
                graph.addEdge(mapVariableToGraphKey(A), mapVariableToGraphKey(-B));
                graph.addEdge(mapVariableToGraphKey(B), mapVariableToGraphKey(-A));
            }
        }
    }

    private int mapGraphKeyToVariable(int key) {
        int a = key - numVariables;
        if (a <= 0) {
            a -= 1;
        }
        return a;
    }

    private int mapVariableToGraphKey(int a) {
        // numVariables -> 2 * numVariables
        // -numVariables -> 1
        // -1 -> numVariables

        // e.g. numVariables = 3
        // -3 -2 -1  1  2  3
        //  1  2  3  4  5  6
        int key;
        if (a < 0) {
            key = a + numVariables + 1;
        } else {
            key = a + numVariables;
        }
        return key;
    }

    private void printComponents(Collection<HashSet<Integer>> components) {
        for (HashSet<Integer> component : components) {
            System.out.print("[ ");
            for (int vertex : component) {
                int variable = mapGraphKeyToVariable(vertex);
                System.out.print(variable + ",");
            }
            System.out.print(" ]");
        }
    }

    public boolean isSatisfiable() {
        StronglyConnectedComponentComputer sccc = new StronglyConnectedComponentComputer(graph);
        HashMap<Integer, HashSet<Integer>> components = sccc.getStronglyConnectedComponentsAsHashSets();
        //printComponents(components.values());

        // The constraints are not satisfiable if the SCC contains contradicting implications.
        for (HashSet<Integer> component : components.values()) {
            for (int vertex : component) {
                int variable = mapGraphKeyToVariable(vertex);
                int key = mapVariableToGraphKey(-variable);
                if (component.contains(key)) {
                    return false;
                }
            }
        }

        return true;
    }
}
