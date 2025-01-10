package br.unioeste.paa.graphs.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data @ToString @Builder
public class Edge {
    private Vertex from;
    private Vertex to;
    private int weight;
}
