package dev.skz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
         Graph graph = new Graph(9);
         graph.addEdge(7, 1);
         graph.addEdge(4, 7);
         graph.addEdge(1, 4);
         graph.addEdge(9, 7);
         graph.addEdge(6, 9);
         graph.addEdge(3, 6);
         graph.addEdge(9, 3);
         graph.addEdge(8, 6);
         graph.addEdge(2, 8);
         graph.addEdge(5, 2);
         graph.addEdge(8, 5);

//        Graph graph = readGraphFromFile();
        StronglyConnectedComponentComputer sccc = new StronglyConnectedComponentComputer(graph);

        HashMap<Integer, HashSet<Integer>> components = sccc.getStronglyConnectedComponentsAsHashSets();
        System.out.println("components: " + components);

        // List<Integer> sizes = sccc.computeSizeOfStronglyConnectedComponents();
        // List<Integer> top5sizes = sizes.subList(0, 5);
        // System.out.println("top 5 sizes: " + top5sizes);
    }

    private static int[] getIntTokens(String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    private static int getNumberOfNodes(File file) {
        HashSet<Integer> uniqueNodes = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                int[] tokens = getIntTokens(line);
                int u = tokens[0];
                int v = tokens[1];
                uniqueNodes.add(u);
                uniqueNodes.add(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uniqueNodes.size();
    }

    private static Graph readGraphFromFile() {
        File file = Paths.get("data/SCC.txt").toFile();
        int numNodes = getNumberOfNodes(file);

        Graph graph = new Graph(numNodes);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                int[] tokens = getIntTokens(line);
                int u = tokens[0];
                int v = tokens[1];
                graph.addEdge(u, v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }
}