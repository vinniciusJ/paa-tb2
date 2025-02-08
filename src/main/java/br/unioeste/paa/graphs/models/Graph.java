package br.unioeste.paa.graphs.models;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data @ToString
public class Graph {
    private boolean isOriented;
    private int verticesNumber;
    private Map<Integer, Vertex> vertices;

    // Construtor para inicializar o grafo
    // Pré-condição: Deve-se especificar se o grafo é orientado e o número de vértices
    // Pós-condição: O grafo é criado e pronto para uso
    public Graph(boolean isOriented, int vertexesNumber) {
        this.isOriented = isOriented;
        this.verticesNumber = vertexesNumber;
        this.vertices = new HashMap<>();
    }

    // Adiciona um vértice ao grafo
    // Pré-condição: O ID do vértice deve ser único
    // Pós-condição: O vértice é adicionado ao grafo
    public Vertex appendVertex(int id) {
        Vertex vertex = new Vertex(id);
        vertices.put(id, vertex);
        return vertex;
    }

    // Adiciona uma aresta ao grafo
    // Pré-condição: Os vértices de origem e destino devem existir
    // Pós-condição: A aresta é adicionada ao grafo, e se não for orientado, uma aresta inversa também é adicionada
    public void appendEdge(int fromVertex, int toVertex, int weight) {
        Vertex from = vertices.get(fromVertex);
        Vertex to = vertices.get(toVertex);

        if (from != null && to != null) {
            Edge edgeFromTo = Edge.builder().from(from).to(to).weight(weight).build();
            from.appendEdge(edgeFromTo);

            if (!isOriented) {
                Edge edgeToFrom = Edge.builder().from(to).to(from).weight(weight).build();
                to.appendEdge(edgeToFrom);
            }
        }
    }

    // Retorna um vértice pelo seu ID
    // Pré-condição: O ID deve existir no grafo
    // Pós-condição: Retorna o vértice correspondente ou null se não existir
    public Vertex findById(int id){
        return vertices.get(id);
    }
}
