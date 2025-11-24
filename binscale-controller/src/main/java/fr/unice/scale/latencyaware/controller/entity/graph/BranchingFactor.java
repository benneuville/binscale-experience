package fr.unice.scale.latencyaware.controller.entity.graph;

public class  BranchingFactor<T> {
    private Vertex<T> vertex;
    private Double factor;

    public BranchingFactor(Vertex<T> vertex, Double factor) {
        this.vertex = vertex;
        this.factor = factor;
    }

    public Vertex<T> getVertex() {
        return vertex;
    }

    public Double getFactor() {
        return factor;
    }
}
