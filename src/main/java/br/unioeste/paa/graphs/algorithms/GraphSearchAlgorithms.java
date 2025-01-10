package br.unioeste.paa.graphs.algorithms;

import br.unioeste.paa.graphs.models.Edge;
import br.unioeste.paa.graphs.models.Graph;
import br.unioeste.paa.graphs.models.Vertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class GraphSearchAlgorithms {
    public static void dfs(int startVertexId, Graph graph) {
        Set<Integer> visited = new HashSet<>();

        dfs(startVertexId, visited, graph);

        System.out.println();
    }

    private static void dfs(int vertexId, Set<Integer> visited, Graph graph) {
        visited.add(vertexId);

        System.out.print(" - " + vertexId);

        Vertex currentVertex = graph.findById(vertexId);

        if(currentVertex != null) {
            for(Edge edge : currentVertex.getEdges()) {
                Vertex neighbor = edge.getTo();
                int neighborId = neighbor.getId();

                if(!visited.contains(neighborId)) {
                    dfs(neighborId, visited, graph);
                }
            }
        }
    }

    public static void bfs(int startVertexId, Graph graph) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startVertexId);
        visited.add(startVertexId);

        while (!queue.isEmpty()) {
            int currentVertexId = queue.poll();

            System.out.print(" - " + currentVertexId);

            Vertex currentVertex = graph.findById(currentVertexId);

            if(currentVertex != null) {
                for(Edge edge : currentVertex.getEdges()) {
                    Vertex neighbor = edge.getTo();
                    int neighborId = neighbor.getId();

                    if(!visited.contains(neighborId)) {
                        queue.add(neighborId);
                        visited.add(neighborId);
                    }
                }
            }
        }

        System.out.println();
    }
}
