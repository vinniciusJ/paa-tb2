package br.unioeste.paa.graphs.algorithms;

import br.unioeste.paa.graphs.models.Edge;
import br.unioeste.paa.graphs.models.Graph;
import br.unioeste.paa.graphs.models.UnionFind;
import br.unioeste.paa.graphs.models.Vertex;
import br.unioeste.paa.graphs.utils.GraphUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KruskalAlgorithm {

    // Exibe a árvore geradora mínima de um grafo usando o algoritmo de Kruskal
    // Pré-condição: O grafo deve ser conexo e possuir vértices e arestas corretamente definidos
    // Pós-condição: A árvore geradora mínima é exibida no console e uma imagem é gerada
    public static void showMinimumSpanningTree(Graph graph, String filename) {
        if (graph.isOriented()) {
            System.out.println("O grafo é orientado. O algoritmo Kruskal requer grafos não orientados.");
            return;
        }

        if(!GraphSearchAlgorithms.isConnected(graph)) {
            System.out.println("O grafo é desconexo. O algoritmo Kruskal requer grafos conexos.");
            return;
        }

        List<Edge> edges = new ArrayList<>();

        for(Vertex vertex : graph.getVertices().values()){
            edges.addAll(vertex.getEdges());
        }

        edges.sort(Comparator.comparingInt(Edge::getWeight));

        UnionFind unionFind = new UnionFind(graph.getVerticesNumber());

        int totalWeight = 0;
        List<Edge> mstEdges = new ArrayList<>();

        for(Edge edge : edges){
            int from = edge.getFrom().getId();
            int to = edge.getTo().getId();

            if(unionFind.find(from) != unionFind.find(to)){
                unionFind.union(from, to);
                mstEdges.add(edge);
                totalWeight += edge.getWeight();
            }
        }

        System.out.println();
        System.out.println("Peso total: " + totalWeight);
        System.out.print("Arestas: ");

        for (Edge edge : mstEdges) {
            System.out.print("(" + edge.getFrom().getId() + "," + edge.getTo().getId() + ") ");
        }

        System.out.println();

        GraphUtils.generateGraphImageWithHighlightedEdges(graph, mstEdges, filename);
    }
}
