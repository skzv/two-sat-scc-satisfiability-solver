package dev.skz;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final int primaryKey;
    private final List<Integer> outgoingEdges;
    private final List<Integer> ingoingEdges;

    public Node(int primaryKey) {
        this.primaryKey = primaryKey;
        outgoingEdges = new ArrayList<>();
        ingoingEdges = new ArrayList<>();
    }

    public void addOutgoingEdge(Node v) {
        outgoingEdges.add(v.getPrimaryKey());
    }

    public void addIngoingEdge(Node v) {
        ingoingEdges.add(v.getPrimaryKey());
    }

    public List<Integer> getIngoingEdges() {
        return ingoingEdges;
    }

    public List<Integer> getOutgoingEdges() {
        return outgoingEdges;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }
}