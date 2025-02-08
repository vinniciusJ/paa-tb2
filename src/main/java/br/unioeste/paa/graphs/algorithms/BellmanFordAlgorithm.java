package br.unioeste.paa.graphs.algorithms;

import br.unioeste.paa.graphs.models.Edge;
import br.unioeste.paa.graphs.models.Graph;
import br.unioeste.paa.graphs.models.Vertex;

import java.util.HashMap;
import java.util.Map;

public class BellmanFordAlgorithm {

    // Calcula os caminhos mínimos a partir de um vértice fonte usando o algoritmo de Bellman-Ford
    // Pré-condição: O grafo deve ser orientado e não conter ciclos negativos acessíveis a partir da fonte
    // Pós-condição: Os menores caminhos da fonte para todos os outros vértices são exibidos no console
    public static void calculateShortestPaths(int sourceId, Graph graph) {
        if (!graph.isOriented()) {
            System.out.println("O grafo não é orientado. O algoritmo Bellman-Ford requer grafos orientados.");
            return;
        }

        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> predecessors = new HashMap<>();

        for (Integer vertexId : graph.getVertices().keySet()) {
            distances.put(vertexId, Integer.MAX_VALUE);
            predecessors.put(vertexId, null);
        }

        distances.put(sourceId, 0);

        for (int i = 1; i < graph.getVertices().size(); i++) {
            for (Vertex vertex : graph.getVertices().values()) {
                for (Edge edge : vertex.getEdges()) {
                    int fromId = edge.getFrom().getId();
                    int toId = edge.getTo().getId();
                    int weight = edge.getWeight();

                    if (distances.get(fromId) != Integer.MAX_VALUE && distances.get(fromId) + weight < distances.get(toId)) {
                        distances.put(toId, distances.get(fromId) + weight);
                        predecessors.put(toId, fromId);
                    }
                }
            }
        }

        // Verifica ciclos negativos
        for (Vertex vertex : graph.getVertices().values()) {
            for (Edge edge : vertex.getEdges()) {
                int fromId = edge.getFrom().getId();
                int toId = edge.getTo().getId();
                int weight = edge.getWeight();

                if (distances.get(fromId) != Integer.MAX_VALUE && distances.get(fromId) + weight < distances.get(toId)) {
                    System.out.println("O grafo contém um ciclo negativo.");
                    return;
                }
            }
        }

        System.out.println("Origem: " + sourceId);

        for (Map.Entry<Integer, Integer> entry : distances.entrySet()) {
            int targetId = entry.getKey();
            int distance = entry.getValue();

            System.out.print("Destino: " + targetId + "\tDist.: " + distance + "\tCaminho: ");
            printPath(targetId, predecessors);
            System.out.println();
        }
    }

    // Imprime o caminho mínimo de um vértice até a origem
    // Pré-condição: O mapa de predecessores deve estar corretamente preenchido
    // Pós-condição: O caminho é impresso no console
    private static void printPath(int vertexId, Map<Integer, Integer> predecessors) {
        if (predecessors.get(vertexId) == null) {
            System.out.print(vertexId);
            return;
        }

        printPath(predecessors.get(vertexId), predecessors);
        System.out.print(" - " + vertexId);
    }
}
