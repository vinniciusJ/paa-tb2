package br.unioeste.paa.graphs.utils;

import br.unioeste.paa.graphs.models.Edge;
import br.unioeste.paa.graphs.models.Graph;
import br.unioeste.paa.graphs.models.Vertex;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class GraphUtils {

    // Lê um grafo a partir de um arquivo
    // Pré-condição: O arquivo deve existir e estar no formato correto
    // Pós-condição: Retorna um objeto Graph representando o grafo lido, ou null em caso de erro
    public static Graph readGraph(String fileName) {
        String path = "src/main/resources/" + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            boolean isOriented = br.readLine().equalsIgnoreCase("orientado=sim");
            int verticesNumber = Integer.parseInt(br.readLine().split("=")[1]);

            Graph graph = new Graph(isOriented, verticesNumber);

            appendVertices(graph);
            appendEdges(graph, br);

            return graph;
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return null;
    }

    // Salva um grafo em um arquivo de imagem
    // Pré-condição: O grafo deve ser válido e o caminho do arquivo deve ser acessível
    // Pós-condição: O grafo é salvo como uma imagem no caminho especificado
    private static void saveGraphToFile(MutableGraph graph, String filename) {
        String path = "src/main/resources/" + filename;

        try {
            Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File(path));
            System.out.println("Grafo renderizado com sucesso em: " + path);
        } catch (Exception e) {
            System.err.println("Erro ao salvar o grafo: " + e.getMessage());
        }
    }

    // Gera uma imagem do grafo
    // Pré-condição: O grafo deve ser válido
    // Pós-condição: Uma imagem do grafo é gerada e salva no arquivo especificado
    public static void generateGraphImage(Graph graph, String filename) {
        MutableGraph g = mutGraph("G").setDirected(graph.isOriented());
        Map<Integer, MutableNode> nodeMap = new HashMap<>();
        Set<String> addedEdges = new HashSet<>();

        appendVertices(graph, g, nodeMap);
        appendEdges(graph, nodeMap, addedEdges, graph.isOriented());

        saveGraphToFile(g, filename);
    }

    // Gera uma imagem do grafo com arestas destacadas
    // Pré-condição: O grafo e a lista de arestas destacadas devem ser válidos
    // Pós-condição: Uma imagem do grafo com as arestas destacadas é gerada e salva no arquivo especificado
    public static void generateGraphImageWithHighlightedEdges(Graph graph, List<Edge> highlightedEdges, String filename) {
        MutableGraph g = mutGraph("G").setDirected(graph.isOriented());
        Map<Integer, MutableNode> nodeMap = new HashMap<>();
        Set<String> addedEdges = new HashSet<>();

        appendVertices(graph, g, nodeMap);
        appendEdgesWithHighlight(graph, nodeMap, addedEdges, highlightedEdges);

        saveGraphToFile(g, filename);
    }

    // Gera uma imagem do grafo com componentes fortemente conexas destacados
    // Pré-condição: O grafo e a lista de componentes fortemente conexas devem ser válidos
    // Pós-condição: Uma imagem do grafo com os componentes fortemente conexas destacados é gerada e salva no arquivo especificado
    public static void generateGraphImageWithStronglyConnectedComponents(Graph graph, List<List<Integer>> stronglyConnectedComponents, String filename) {
        MutableGraph g = mutGraph("G").setDirected(graph.isOriented());
        Map<Integer, MutableNode> nodeMap = new HashMap<>();
        Map<Integer, Color> vertexColors = new HashMap<>();
        Set<String> addedEdges = new HashSet<>();

        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PURPLE, Color.YELLOW, Color.CYAN};
        int colorIndex = 0;

        for (List<Integer> scc : stronglyConnectedComponents) {
            Color color = colors[colorIndex % colors.length];
            colorIndex++;
            for (int vertexId : scc) {
                vertexColors.put(vertexId, color);
            }
        }

        appendVerticesWithColor(graph, g, nodeMap, vertexColors);
        appendEdges(graph, nodeMap, addedEdges, graph.isOriented());

        saveGraphToFile(g, filename);
    }

    // Adiciona vértices ao grafo
    // Pré-condição: O grafo de origem e o grafo de destino devem ser válidos
    // Pós-condição: Os vértices são adicionados ao grafo de destino e ao mapa de nós
    private static void appendVertices(Graph originGraph, MutableGraph destinyGraph, Map<Integer, MutableNode> nodeMap) {
        for (Vertex vertex : originGraph.getVertices().values()) {
            MutableNode node = mutNode(String.valueOf(vertex.getId()));
            destinyGraph.add(node);
            nodeMap.put(vertex.getId(), node);
        }
    }

    // Adiciona vértices ao grafo com cores específicas
    // Pré-condição: O grafo de origem, o grafo de destino e o mapa de cores devem ser válidos
    // Pós-condição: Os vértices são adicionados ao grafo de destino com as cores especificadas e ao mapa de nós
    private static void appendVerticesWithColor(Graph originGraph, MutableGraph destinyGraph, Map<Integer, MutableNode> nodeMap, Map<Integer, Color> vertexColors) {
        for (Vertex vertex : originGraph.getVertices().values()) {
            MutableNode node = mutNode(String.valueOf(vertex.getId())).add(vertexColors.get(vertex.getId()));
            destinyGraph.add(node);
            nodeMap.put(vertex.getId(), node);
        }
    }

    // Adiciona vértices ao grafo
    // Pré-condição: O grafo deve ser válido
    // Pós-condição: Os vértices são adicionados ao grafo
    private static void appendVertices(Graph graph) {
        for (int i = 0; i < graph.getVerticesNumber(); i++) {
            graph.appendVertex(i);
        }
    }

    // Adiciona arestas ao grafo a partir de um BufferedReader
    // Pré-condição: O grafo e o BufferedReader devem ser válidos
    // Pós-condição: As arestas são adicionadas ao grafo
    private static void appendEdges(Graph graph, BufferedReader br) throws IOException {
        String edgeLine;

        while ((edgeLine = br.readLine()) != null) {
            String[] parts = edgeLine.replace("(", "").replace(")", "").split(":");
            String[] vertices = parts[0].split(",");

            int from = Integer.parseInt(vertices[0].trim());
            int to = Integer.parseInt(vertices[1].trim());
            int weight = Integer.parseInt(parts[1].trim());

            graph.appendEdge(from, to, weight);
        }
    }

    // Adiciona arestas ao grafo com destaque
    // Pré-condição: O grafo, o mapa de nós, o conjunto de arestas adicionadas e a lista de arestas destacadas devem ser válidos
    // Pós-condição: As arestas são adicionadas ao grafo com as arestas destacadas em verde
    private static void appendEdgesWithHighlight(Graph graph, Map<Integer, MutableNode> nodeMap, Set<String> addedEdges, List<Edge> highlightedEdges) {
        for (Vertex vertex : graph.getVertices().values()) {
            for (Edge edge : vertex.getEdges()) {
                int fromId = edge.getFrom().getId();
                int toId = edge.getTo().getId();

                String edgeKey = graph.isOriented()
                        ? fromId + "->" + toId
                        : Math.min(fromId, toId) + "--" + Math.max(fromId, toId);

                if (!addedEdges.contains(edgeKey)) {
                    addedEdges.add(edgeKey);

                    MutableNode from = nodeMap.get(fromId);
                    MutableNode to = nodeMap.get(toId);

                    if (from != null && to != null) {
                        Link link = Link.to(to).with(Label.of(String.valueOf(edge.getWeight())));

                        if (highlightedEdges.contains(edge)) {
                            link = link.with(Color.GREEN1);
                        }

                        from.addLink(link);
                    }
                }
            }
        }
    }

    // Adiciona arestas ao grafo
    // Pré-condição: O grafo, o mapa de nós e o conjunto de arestas adicionadas devem ser válidos
    // Pós-condição: As arestas são adicionadas ao grafo
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