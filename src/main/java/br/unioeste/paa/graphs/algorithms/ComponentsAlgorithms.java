package br.unioeste.paa.graphs.algorithms;

import br.unioeste.paa.graphs.models.Edge;
import br.unioeste.paa.graphs.models.Graph;
import br.unioeste.paa.graphs.models.Vertex;

import java.util.*;

public class ComponentsAlgorithms {
    public static void showConnectedComponents(Graph graph) {
        Map<Integer, Boolean> visited = new HashMap<>();

        for (int vertexId : graph.getVertices().keySet()) {
            visited.put(vertexId, false);
        }

        List<List<Integer>> components = new ArrayList<>();

        for (int vertexId : graph.getVertices().keySet()) {
            if (!visited.get(vertexId)) {
                List<Integer> component = new ArrayList<>();
                dfs(vertexId, visited, component, graph);
                components.add(component);
            }
        }

        components.sort((a, b) -> Integer.compare(b.size(), a.size()));

        for (int i = 0; i < components.size(); i++) {
            System.out.println("Componente " + (i + 1) + ": " + components.get(i));
        }
    }

    public static void showStronglyConnectedComponents(Graph graph) {
        if (!graph.isOriented()) {
            showConnectedComponents(graph);
            return;
        }

        Stack<Integer> finishingOrder = new Stack<>();
        Map<Integer, Boolean> visited = new HashMap<>();

        for (int vertexId : graph.getVertices().keySet()) {
            visited.put(vertexId, false);
        }

        for (int vertexId : graph.getVertices().keySet()) {
            if (!visited.get(vertexId)) {
                dfs(vertexId, visited, finishingOrder, graph);
            }
        }

        Graph transposedGraph = transposeGraph(graph);

        visited.replaceAll((key, value) -> false);
        List<List<Integer>> stronglyConnectedComponents = new ArrayList<>();

        while (!finishingOrder.isEmpty()) {
            int vertexId = finishingOrder.pop();

            if (!visited.get(vertexId)) {
                List<Integer> component = new ArrayList<>();
                dfs(vertexId, visited, component, transposedGraph);
                stronglyConnectedComponents.add(component);
            }
        }

        stronglyConnectedComponents.sort((a, b) -> Integer.compare(b.size(), a.size()));

        for (int i = 0; i < stronglyConnectedComponents.size(); i++) {
            System.out.println("Componente " + (i + 1) + ": " + stronglyConnectedComponents.get(i));
        }
    }

    private static Graph transposeGraph(Graph graph) {
        Graph transposed = new Graph(graph.isOriented(), graph.getVerticesNumber());

        for (int vertexId : graph.getVertices().keySet()) {
            transposed.appendVertex(vertexId);
        }

        for (Vertex vertex : graph.getVertices().values()) {
            for (Edge edge : vertex.getEdges()) {
                transposed.appendEdge(edge.getTo().getId(), edge.getFrom().getId(), edge.getWeight());
            }
        }

        return transposed;
    }

    private static void dfs(int currentVertexId, Map<Integer, Boolean> visited, List<Integer> component, Graph graph) {
        visited.put(currentVertexId, true);
        component.add(currentVertexId);

        Vertex vertex = graph.findById(currentVertexId);

        if (vertex != null) {
            for (Edge edge : vertex.getEdges()) {
                int neighborId = edge.getTo().getId();

                if (!visited.get(neighborId)) {
                    dfs(neighborId, visited, component, graph);
                }
            }
        }
    }

    private static void dfs(int currentVertexId, Map<Integer, Boolean> visited, Stack<Integer> finishingOrder, Graph graph) {
        visited.put(currentVertexId, true);

        Vertex vertex = graph.findById(currentVertexId);

        if (vertex != null) {
            for (Edge edge : vertex.getEdges()) {
                int neighborId = edge.getTo().getId();

                if (!visited.get(neighborId)) {
                    dfs(neighborId, visited, finishingOrder, graph);
                }
            }
        }

        finishingOrder.push(currentVertexId);
    }
}
