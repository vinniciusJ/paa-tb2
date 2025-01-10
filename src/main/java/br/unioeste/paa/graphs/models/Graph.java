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

    public Graph(boolean isOriented, int vertexesNumber) {
        this.isOriented = isOriented;
        this.verticesNumber = vertexesNumber;

        this.vertices = new HashMap<>();
    }

    public Vertex appendVertex(int id) {
        Vertex vertex = new Vertex(id);

        vertices.put(id, vertex);

        return vertex;
    }

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

    public Vertex findById(int id){
        return vertices.get(id);
    }
}
