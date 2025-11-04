package fr.unice.scale.latencyaware.controller.graph;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;

import java.util.Stack;

public class Graph {
    private final Vertex[] V;
    private final int[][] adjMat;
    private final Stack<Vertex> s;
    private final Stack<Vertex> topoStack;
    private final int vMax;
    private final double[][] BF;
    public int nV;

    public Graph(int vMax) {
        this.vMax = vMax; // Maximum vertex can vbe added
        nV = 0; // counter for the vertices we will work with 1
        V = new Vertex[vMax];
        adjMat = new int[vMax][vMax];
        BF = new double[vMax][vMax];
        s = new Stack<>();
        topoStack = new Stack<>();
    }

    public double[][] getBF() {
        return BF;
    }

    public void setBF(int i, int j, double value) {
        BF[i][j] = value;
    }

    public int[][] getAdjMat() {
        return adjMat;
    }

    public void addVertex(int label, ConsumerGroup g) {
        V[nV] = new Vertex(label, g);
        nV++;
    }

    public void addEdge(int source, int destination) {
        adjMat[source][destination] = 1;
    }

    public Vertex getVertex(int i) {
        return V[i];
    }

    public Vertex unVisitedAdjVet(Vertex v) {
        for (int i = 1; i < nV; i++) {
            if (adjMat[v.label][i] == 1 && !V[i].isVisited) return V[i];
        }
        return null;
    }

    public Stack<Vertex> dfs(Vertex start) {
        s.push(start);
        start.isVisited = true;
        //System.out.print(start);

        while (!s.isEmpty()) {
            Vertex vet = unVisitedAdjVet(s.peek());
            if (vet != null) {
                vet.isVisited = true;
                // System.out.print(vet);
                s.push(vet);
            } else {
                topoStack.push(s.pop());
            }
        }
        return topoStack;
    }
}
