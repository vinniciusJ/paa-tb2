package br.unioeste.paa.graphs.models;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data @ToString
public class Vertex {
    private int id;
    private List<Edge> edges;

    // Construtor que inicializa um vértice com um identificador
    // Pré-condição: O id deve ser um número inteiro único dentro do grafo
    // Pós-condição: O vértice é criado com uma lista vazia de arestas
    public Vertex(int id) {
        this.id = id;
        this.edges = new ArrayList<Edge>();
    }

    // Adiciona uma aresta à lista de arestas do vértice
    // Pré-condição: A aresta deve ser válida e conectada a este vértice
    // Pós-condição: A aresta é adicionada à lista de arestas do vértice
    public void appendEdge(Edge edge) {
        edges.add(edge);
    }
}
