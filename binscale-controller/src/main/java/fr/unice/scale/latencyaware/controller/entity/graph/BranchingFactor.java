package fr.unice.scale.latencyaware.controller.entity.graph;

public class BranchingFactor<T> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BranchingFactor<?> that = (BranchingFactor<?>) o;

        return vertex.equals(that.vertex);
    }

    @Override
    public String toString() {
        return "BranchingFactor{" +
                "vertex=" + vertex +
                ", factor=" + factor +
                '}';
    }
}
