package br.unioeste.paa.graphs.utils;

import br.unioeste.paa.graphs.models.Edge;
import br.unioeste.paa.graphs.models.Graph;
import br.unioeste.paa.graphs.models.Vertex;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphUtils {
    public static Graph readGraph(String fileName) {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            boolean isOriented = br.readLine().equalsIgnoreCase("orientado=sim");
            int verticesNumber = Integer.parseInt(br.readLine().split("=")[1]);

            Graph graph = new Graph(isOriented, verticesNumber);

            appendVertices(graph);
            appendEdges(graph, br);

            return graph;
        }
        catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return null;
    }

    public static MutableGraph toGraphviz(Graph graph){
        MutableGraph g = Factory.mutGraph("G").setDirected(graph.isOriented());
        Map<Integer, MutableNode> nodeMap = new HashMap<>();
        Set<String> addedEdges = new HashSet<>();

        appendVertices(graph, g, nodeMap);
        appendEdges(graph, nodeMap, addedEdges, graph.isOriented());

        return g;
    }

    public static void exportToPNG(Graph graph, String outputFilePath) {
        MutableGraph g = toGraphviz(graph);

        try{
            Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(outputFilePath));

            System.out.println("Grafo renderizado com sucesso em: " + outputFilePath);
        }
        catch (Exception e) {
            System.err.println("Erro ao salvar o grafo: " + e.getMessage());
        }
    }


    private static void appendVertices(Graph originGraph, MutableGraph destinyGraph, Map<Integer, MutableNode> nodeMap){
        for(Vertex vertex : originGraph.getVertices().values()){
            MutableNode node = Factory.mutNode(String.valueOf(vertex.getId()));

            destinyGraph.add(node);
            nodeMap.put(vertex.getId(), node);
        }
    }

    private static void appendVertices(Graph graph){
        for(int i = 0; i < graph.getVerticesNumber(); i++){
            graph.appendVertex(i);
        }
    }

    private static void appendEdges(Graph graph, BufferedReader br) throws IOException {
        String edgeLine;

        while ((edgeLine = br.readLine()) != null){
            String[] parts = edgeLine.replace("(", "").replace(")", "").split(":");
            String[] vertices = parts[0].split(",");

            int from = Integer.parseInt(vertices[0].trim());
            int to = Integer.parseInt(vertices[1].trim());
            int weight  = Integer.parseInt(parts[1].trim());

            graph.appendEdge(from, to, weight);
        }
    }

    private static void appendEdges(Graph graph, Map<Integer, MutableNode> nodeMap, Set<String> addedEdges, boolean isOriented) {
        for (Vertex vertex : graph.getVertices().values()) {
            for (Edge edge : vertex.getEdges()) {
                int fromId = edge.getFrom().getId();
                int toId = edge.getTo().getId();

                String edgeKey = isOriented
                        ? fromId + "->" + toId
                        : Math.min(fromId, toId) + "--" + Math.max(fromId, toId);

                if (!addedEdges.contains(edgeKey)) {
                    addedEdges.add(edgeKey);

                    MutableNode from = nodeMap.get(fromId);
                    MutableNode to = nodeMap.get(toId);

                    if (from != null && to != null) {
                        from.addLink(Link.to(to).with(Label.of(String.valueOf(edge.getWeight()))));
                    }
                }
            }
        }
    }
}
