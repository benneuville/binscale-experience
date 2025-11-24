package fr.unice.scale.latencyaware.controller.entity.graph;

import java.util.Objects;

public class Vertex<T> implements NamedVertex {
    private final String name;
    private final T group;

    public Vertex(String name, T group) {
        this.name = name;
        this.group = group;
    }

    public T getGroup() {
        return group;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Vertex{" + name + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedVertex)) return false;
        NamedVertex vertex = (NamedVertex) o;
        return Objects.equals(this.getName(), vertex.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}