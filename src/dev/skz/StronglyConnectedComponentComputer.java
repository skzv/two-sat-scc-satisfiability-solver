package dev.skz;

import java.util.*;

public class StronglyConnectedComponentComputer {

    private final Graph graph;
    private final int n;
    private final int[] primaryKeyByFinishingTime;
    private final HashSet<Integer> leaders;
    private final HashMap<Integer, Integer> componentSize;
    private boolean[] explored;
    private int t;
    private int s;
    private int[] leadersByPrimaryKey;
    private final HashMap<Integer, HashSet<Integer>> componentsByLeader;

    public StronglyConnectedComponentComputer(Graph graph) {
        this.graph = graph;
        this.n = graph.getSize();
        explored = new boolean[n];
        primaryKeyByFinishingTime = new int[n];
        leadersByPrimaryKey = new int[n];
        leaders = new HashSet<>();
        componentSize = new HashMap<>();
        componentsByLeader = new HashMap<>();
    }

    private void resetExploredArray() {
        explored = new boolean[n];
    }

    private boolean isNodeExplored(int n) {
        return explored[n - 1];
    }

    private void setExplored(int n) {
        explored[n - 1] = true;
    }

    public void setFinishingTime(int primaryKey, int finishingTime) {
        primaryKeyByFinishingTime[finishingTime - 1] = primaryKey;
    }

    public int getNodeByFinishingTime(int finishingTime) {
        return primaryKeyByFinishingTime[finishingTime - 1];
    }

    /**
     * Compute strongly connected components
     *
     * @return list of nodes pointing to leaders of each strongly connected component
     */
    public List<Integer> computeStronglyConnectedComponents() {
        DFSFirstPassLoop();
        DFSSecondPassLoop();
        return new ArrayList<>(leaders);
    }

    public HashMap<Integer, HashSet<Integer>> getStronglyConnectedComponentsAsHashSets() {
        DFSFirstPassLoop();
        DFSSecondPassLoop();
        return new HashMap<>(componentsByLeader);
    }

    /**
     * Sizes of the strongly connected components.
     *
     * @return list of the sizes of the strongly connected components, in decreasing order
     */
    public List<Integer> computeSizeOfStronglyConnectedComponents() {
        DFSFirstPassLoop();
        DFSSecondPassLoop();
        List<Integer> sizes = new ArrayList<>(componentSize.values());
        sizes.sort(Comparator.reverseOrder());
        return sizes;
    }

    private void DFSFirstPassLoop() {
        t = 0;

        for (int i = n; i > 0; i--) {
            if (!isNodeExplored(i)) {
                DFSFirstPassHelper(i);
            }
        }
    }

    private void DFSFirstPassHelper(int i) {
        setExplored(i);
        Node node = graph.getNodeByPrimaryKey(i);
        for (Integer j : node.getIngoingEdges()) {
            if (!isNodeExplored(j)) {
                DFSFirstPassHelper(j);
            }
        }
        t++;
        setFinishingTime(i, t);
    }

    private void DFSSecondPassLoop() {
        resetExploredArray();
        s = 0;

        for (int i = n; i > 0; i--) {
            int k = getNodeByFinishingTime(i);
            if (!isNodeExplored(k)) {
                s = k;
                DFSSecondPassHelper(k);
            }
        }
    }

    private void DFSSecondPassHelper(int i) {
        setExplored(i);
        setLeader(i, s);
        Node node = graph.getNodeByPrimaryKey(i);
        for (Integer j : node.getOutgoingEdges()) {
            if (!isNodeExplored(j)) {
                DFSSecondPassHelper(j);
            }
        }
    }

    private void setLeader(int i, int s) {
        leadersByPrimaryKey[i - 1] = s;
        leaders.add(s);

        if (!componentSize.containsKey(s)) {
            componentSize.put(s, 0);
        }
        componentSize.put(s, componentSize.get(s) + 1);

        if (!componentsByLeader.containsKey(s)) {
            componentsByLeader.put(s, new HashSet<>());
        }

        componentsByLeader.get(s).add(i);
    }
}