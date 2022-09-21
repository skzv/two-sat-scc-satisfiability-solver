package dev.skz;

public class Graph {
    private final Node[] nodes;

    public Graph(int numNodes) {
        nodes = new Node[numNodes];
        initializeNodes();
    }

    private void initializeNodes() {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i + 1);
        }
    }

    public void addEdge(int u, int v) {
        Node uNode = nodes[u - 1];
        Node vNode = nodes[v - 1];
        uNode.addOutgoingEdge(vNode);
        vNode.addIngoingEdge(uNode);
    }

    public Node getNodeByPrimaryKey(int primaryKey) {
        return nodes[primaryKey - 1];
    }

    public int getSize() {
        return nodes.length;
    }
}
