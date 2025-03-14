package br.unioeste.paa.graphs.algorithms;

import br.unioeste.paa.graphs.models.Edge;
import br.unioeste.paa.graphs.models.Graph;
import br.unioeste.paa.graphs.models.Vertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class GraphSearchAlgorithms {

    // Executa a busca em profundidade (DFS) a partir de um vértice inicial
    // Pré-condição: O vértice inicial deve existir no grafo
    // Pós-condição: Os vértices acessíveis são visitados e impressos na ordem da DFS
    public static void dfs(int startVertexId, Graph graph) {
        Set<Integer> visited = new HashSet<>();
        dfs(startVertexId, visited, graph, true);
        System.out.println();
    }

    // Método auxiliar recursivo para a DFS que mostra o passo a passo se print for verdadeiro
    // Pré-condição: O conjunto de vértices visitados deve estar inicializado
    // Pós-condição: Todos os vértices acessíveis a partir do vértice atual são visitados
    private static void dfs(int vertexId, Set<Integer> visited, Graph graph, Boolean print) {
        visited.add(vertexId);
        if(print) System.out.print(" - " + vertexId);

        Vertex currentVertex = graph.findById(vertexId);

        if (currentVertex != null) {
            for (Edge edge : currentVertex.getEdges()) {
                Vertex neighbor = edge.getTo();
                int neighborId = neighbor.getId();

                if (!visited.contains(neighborId)) {
                    dfs(neighborId, visited, graph, print);
                }
            }
        }
    }

    // Executa a busca em largura (BFS) a partir de um vértice inicial
    // Pré-condição: O vértice inicial deve existir no grafo
    // Pós-condição: Os vértices acessíveis são visitados e impressos na ordem da BFS
    public static void bfs(int startVertexId, Graph graph) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startVertexId);
        visited.add(startVertexId);

        while (!queue.isEmpty()) {
            int currentVertexId = queue.poll();
            System.out.print(" - " + currentVertexId);

            Vertex currentVertex = graph.findById(currentVertexId);

            if (currentVertex != null) {
                for (Edge edge : currentVertex.getEdges()) {
                    Vertex neighbor = edge.getTo();
                    int neighborId = neighbor.getId();

                    if (!visited.contains(neighborId)) {
                        queue.add(neighborId);
                        visited.add(neighborId);
                    }
                }
            }
        }

        System.out.println();
    }

    // Verifica se um grafo não orientado é conexo
    // Pré-condição: O grafo deve ser não orientado
    // Pós-condição: Retorna true se o grafo for conexo, false caso contrário
    public static boolean isConnected(Graph graph) {
        if (graph.getVertices().isEmpty()) {
            return true;
        }

        Set<Integer> visited = new HashSet<>();
        int startVertexId = graph.getVertices().values().iterator().next().getId();

        dfs(startVertexId, visited, graph, false);

        return visited.size() == graph.getVertices().size();
    }

}
