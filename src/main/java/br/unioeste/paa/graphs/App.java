package br.unioeste.paa.graphs;

import br.unioeste.paa.graphs.algorithms.BellmanFordAlgorithm;
import br.unioeste.paa.graphs.algorithms.ComponentsAlgorithms;
import br.unioeste.paa.graphs.algorithms.GraphSearchAlgorithms;
import br.unioeste.paa.graphs.algorithms.KruskalAlgorithm;
import br.unioeste.paa.graphs.models.Graph;
import br.unioeste.paa.graphs.utils.GraphUtils;
import br.unioeste.paa.graphs.utils.ViewUtils;
import br.unioeste.paa.graphs.views.AppView;

public class App {
    private Graph graph;

    // Exporta o grafo atual para um arquivo PNG
    // Pré-condição: O grafo deve estar carregado e o nome do arquivo deve ser válido
    // Pós-condição: O grafo é exportado como uma imagem PNG no caminho especificado
    private void exportToPNG() {
        String filename = ViewUtils.input("Informe o nome do arquivo para ser exportado: ");
        GraphUtils.generateGraphImage(graph, filename);
    }

    // Aplica o algoritmo de DFS (Busca em profundidade) no grafo
    // Pré-condição: O grafo deve estar carregado e o vértice inicial deve ser válido
    // Pós-condição: O algoritmo DFS é executado e o resultado é exibido
    private void applyDFSAlgorithm() {
        Integer startVertex = ViewUtils.input("Informe o vértice inicial: ", Integer::parseInt);
        GraphSearchAlgorithms.dfs(startVertex, graph);
    }

    // Aplica o algoritmo de BFS (Busca em largura) no grafo
    // Pré-condição: O grafo deve estar carregado e o vértice inicial deve ser válido
    // Pós-condição: O algoritmo BFS é executado e o resultado é exibido
    private void applyBFSAlgorithm() {
        Integer startVertex = ViewUtils.input("Informe o vértice inicial: ", Integer::parseInt);
        GraphSearchAlgorithms.bfs(startVertex, graph);
    }

    // Aplica o algoritmo de Bellman-Ford no grafo
    // Pré-condição: O grafo deve estar carregado e o vértice inicial deve ser válido
    // Pós-condição: O algoritmo Bellman-Ford é executado e os caminhos mais curtos são exibidos
    private void applyBellmanFordAlgorithm() {
        Integer startVertex = ViewUtils.input("Informe o vértice inicial: ", Integer::parseInt);
        BellmanFordAlgorithm.calculateShortestPaths(startVertex, graph);
    }

    // Exibe a árvore geradora mínima (MST) do grafo usando o algoritmo de Kruskal
    // Pré-condição: O grafo deve estar carregado e o nome do arquivo deve ser válido
    // Pós-condição: A MST é exibida no terminal e o grafo é exportado destacando as arestas que compõe ela
    private void showMinimumSpanningTree() {
        String filename = ViewUtils.input("Informe o nome do arquivo para ser exportado: ");
        KruskalAlgorithm.showMinimumSpanningTree(graph, filename);
    }

    // Exibe as componentes conexas do grafo
    // Pré-condição: O grafo deve estar carregado
    // Pós-condição: As componentes conexas são exibidas
    private void showConnectedComponents() {
        ComponentsAlgorithms.showConnectedComponents(graph);
    }

    // Exibe as componentes fortemente conexas do grafo
    // Pré-condição: O grafo deve estar carregado e o nome do arquivo deve ser válido
    // Pós-condição: As componentes fortemente conexas são exibidos no terminal e grafo é exportado destacando elas
    private void showStronglyConnectedComponents() {
        String filename = ViewUtils.input("Informe o nome do arquivo para ser exportado: ");
        ComponentsAlgorithms.showStronglyConnectedComponents(graph, filename);
    }

    // Manipula as opções do menu relacionadas ao grafo
    // Pré-condição: O grafo deve estar carregado
    // Pós-condição: A ação correspondente à opção selecionada é executada
    private void handleGraphOptions() {
        int option = AppView.inputMenuOption();

        switch (option) {
            case 0 -> bootstrap();
            case 1 -> exportToPNG();
            case 2 -> applyDFSAlgorithm();
            case 3 -> applyBFSAlgorithm();
            case 4 -> applyBellmanFordAlgorithm();
            case 5 -> showMinimumSpanningTree();
            case 6 -> showConnectedComponents();
            case 7 -> showStronglyConnectedComponents();
            case 8 -> System.exit(0);
            default -> handleGraphOptions();
        }

        ViewUtils.waitForEnter();
        handleGraphOptions();
    }

    // Inicializa o grafo a partir de um arquivo de texto
    // Pré-condição: O caminho do arquivo deve ser válido e o arquivo deve estar no formato correto
    // Pós-condição: O grafo é carregado e as opções do menu são exibidas
    public void bootstrap() {
        String filename = ViewUtils.input("Informe o caminho para o arquivo de texto do grafo: ");
        graph = GraphUtils.readGraph(filename);
        handleGraphOptions();
    }

    // Inicia a aplicação
    // Pré-condição: Nenhuma
    // Pós-condição: A aplicação é iniciada e o grafo é carregado
    public static void start() {
        App app = new App();
        app.bootstrap();
    }
}