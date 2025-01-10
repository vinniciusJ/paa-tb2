package br.unioeste.paa.graphs.algorithms;

import br.unioeste.paa.graphs.models.Edge;
import br.unioeste.paa.graphs.models.Graph;
import br.unioeste.paa.graphs.models.UnionFind;
import br.unioeste.paa.graphs.models.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KruskalAlgorithm {
    public static void showMinimumSpanningTree(Graph graph) {
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

        System.out.println("Peso total: " + totalWeight);
        System.out.print("Arestas: ");

        for (Edge edge : mstEdges) {
            System.out.print("(" + edge.getFrom().getId() + "," + edge.getTo().getId() + ") ");
        }

        System.out.println();

    }
}
