package br.unioeste.paa.graphs.models;

public class UnionFind {
    private final int[] parent;
    private final int[] rank;

    // Construtor que inicializa a estrutura Union-Find com um determinado tamanho
    // Pré-condição: O tamanho deve ser um valor positivo
    // Pós-condição: Cada elemento é seu próprio representante no início
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        for(int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Encontra o representante do conjunto ao qual um elemento pertence (com compressão de caminho)
    // Pré-condição: O elemento x deve estar dentro do intervalo válido da estrutura
    // Pós-condição: Retorna o representante do conjunto contendo x, com otimização por compressão de caminho
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Realiza a união de dois conjuntos distintos, aplicando a heurística de união por rank
    // Pré-condição: Os elementos x e y devem estar dentro do intervalo válido da estrutura
    // Pós-condição: Os conjuntos contendo x e y são fundidos, mantendo uma estrutura balanceada
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
