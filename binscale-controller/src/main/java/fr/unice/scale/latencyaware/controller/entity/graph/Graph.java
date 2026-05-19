package fr.unice.scale.latencyaware.controller.entity.graph;


import com.google.common.collect.Sets;
import fr.unice.scale.latencyaware.common.error.exception.NotFoundException;
import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.NamedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Graph<T extends NamedEntity> {

    private Logger log = LoggerFactory.getLogger(Graph.class);
    private Map<String, Vertex<T>> vertices = new HashMap<>();
    private Map<Vertex<T>, Set<BranchingFactor<T>>> childMap = new HashMap<>();

    private Map<Vertex<T>, Set<BranchingFactor<T>>> parentMap = new HashMap<>();

    private List<Vertex<T>> topologicalOrderList = null;

    public Graph() {
    }

    public void addVertex(String name, T group) {
        Vertex<T> v = new Vertex<>(name, group);
        vertices.put(name, v);
        childMap.putIfAbsent(v, new HashSet<>());
        parentMap.putIfAbsent(v, new HashSet<>());
        topologicalOrderList = null;
    }

    public void addEdge(String from, String to, Double factor) {
        Vertex<T> src = vertices.get(from);
        Vertex<T> dest = vertices.get(to);
        if (src == null || dest == null)
            throw new IllegalArgumentException("Vertex not found : " + from + " to " + to);
        childMap.get(src).add(new BranchingFactor<>(dest, factor));
        parentMap.get(dest).add(new BranchingFactor<>(src, factor));
        topologicalOrderList = null;
    }

    public void setAssignments(Map<T, List<Consumer>> assignments) {
        for (Map.Entry<T, List<Consumer>> entry : assignments.entrySet()) {
            Vertex<T> vertex = getVertex(entry.getKey());
            if (vertex != null) {
                entry.getKey().setAssignment(entry.getValue());
            }
        }
    }

    public Set<Vertex<T>> getChildVertices(String name) {
        return this.getChildVertices(vertices.get(name));
    }

    public Set<Vertex<T>> getChildVertices(Vertex<T> vertex) {
        return childMap.getOrDefault(vertex, Collections.emptySet()).stream()
                .map(BranchingFactor::getVertex)
                .collect(Collectors.toSet());
    }

    public Set<BranchingFactor<T>> getChildBranchingFactors(Vertex<T> vertex) {
        log.info("Child branching factors : {} : {}", vertex, childMap.getOrDefault(vertex, Collections.emptySet()).toString());
        return childMap.getOrDefault(vertex, Collections.emptySet());
    }

    public List<Vertex<T>> roots() {
        Set<Vertex<T>> children = childMap.values().stream().flatMap(Set::stream).map(BranchingFactor::getVertex).collect(Collectors.toSet());
        return vertices.values().stream().filter(v -> !children.contains(v)).collect(Collectors.toList());
    }

    public List<Vertex<T>> dfs(String startName) {
        Vertex<T> start = vertices.get(startName);
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> result = new ArrayList<>();
        dfsHelper(start, visited, result);
        return result;
    }

    private void dfsHelper(Vertex<T> v, Set<Vertex<T>> visited, List<Vertex<T>> result) {
        if (v == null || visited.contains(v)) return;
        visited.add(v);
        result.add(v);
        for (Vertex<T> adj : this.getChildVertices(v)) {
            dfsHelper(adj, visited, result);
        }
    }

    public Set<BranchingFactor<T>> getDirectParent(Vertex<T> child) {
        return parentMap.getOrDefault(child, Collections.emptySet());
    }

    public List<Vertex<T>> getChildVerticesNotIn(Vertex<T> current, Set<Vertex<T>> visited) {
        return this.getChildVertices(current).stream()
                .filter(v -> !visited.contains(v))
                .collect(Collectors.toList());
    }

    public Set<Vertex<T>> branchingFactorToVertixSet(Set<BranchingFactor<T>> bfs) {
        return bfs.stream().map(BranchingFactor::getVertex).collect(Collectors.toSet());
    }

    public Set<Vertex<T>> allDirectParentsOfChildVertexInSet(Vertex<T> child, Set<Vertex<T>> visited) {
        Set<Vertex<T>> parents = branchingFactorToVertixSet(this.getDirectParent(child));
        return Sets.difference(parents, visited);
    }

    public Vertex<T> getVertex(T group) {
        for (Vertex<T> v : vertices.values()) {
            if (v.getGroup().equals(group)) {
                return v;
            }
        }
        return null;
    }

    public Vertex<T> getVertex(String name) {
        return vertices.values().stream().filter(v -> v.getGroup().getGroupName().equals(name)).findFirst().orElseThrow(() -> new NotFoundException("ConsumerGroup with name " + name + " not found"));
    }

    public List<Vertex<T>> getVertices() {
        return vertices.values().stream().collect(Collectors.toList());
    }

    public List<Vertex<T>> topologicalSort() {
        if (topologicalOrderList != null) {
            return topologicalOrderList;
        }

        Map<Vertex<T>, Integer> inDegree = new HashMap<>();
        for (Vertex<T> node : getVertices()) {
            inDegree.putIfAbsent(node, 0);
        }

        for (Vertex<T> parent : childMap.keySet()) {
            for (BranchingFactor<T> child : childMap.get(parent)) {
                inDegree.put(child.getVertex(), Math.max(inDegree.get(child.getVertex()), inDegree.get(parent)) + 1);
            }
        }

        Queue<Vertex<T>> queue = new LinkedList<>(roots());

        List<Vertex<T>> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Vertex<T> node = queue.poll();
            result.add(node);

            inDegree.forEach((k, v) -> {
                inDegree.put(k, v - 1);
                if (v - 1 == 0) {
                    queue.add(k);
                }
            });
        }

        System.out.println("Topological Sort Result : " + result);

        if (result.size() != childMap.size()) {
            throw new IllegalStateException("The graph contains at least one cycle, topologic sort impossible.");
        }

        topologicalOrderList = result;
        return result;
    }
}

