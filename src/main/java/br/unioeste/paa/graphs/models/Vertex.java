package br.unioeste.paa.graphs.models;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data @ToString
public class Vertex {
    private int id;
    private List<Edge> edges;

    public Vertex(int id) {
        this.id = id;
        this.edges = new ArrayList<Edge>();
    }

    public void appendEdge(Edge edge) {
        edges.add(edge);
    }
}
