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

    private void exportToPNG(){
        String filename = ViewUtils.input("Informe o nome do arquivo para ser exportado: ");

        GraphUtils.exportToPNG(graph, "src/main/resources/" + filename);
    }

    private void applyDFSAlgorithm(){
        Integer startVertex = ViewUtils.input("Informe o vértice inicial: ", Integer::parseInt);

        GraphSearchAlgorithms.dfs(startVertex, graph);
    }

    private void applyBFSAlgorithm(){
        Integer startVertex = ViewUtils.input("Informe o vértice inicial: ", Integer::parseInt);

        GraphSearchAlgorithms.bfs(startVertex, graph);
    }

    private void applyBellmanFordAlgorithm(){
        Integer startVertex = ViewUtils.input("Informe o vértice inicial: ", Integer::parseInt);

        BellmanFordAlgorithm.calculateShortestPaths(startVertex, graph);
    }

    private void showConnectedComponents(){
        ComponentsAlgorithms.showConnectedComponents(graph);
    }

    private void showStronglyConnectedComponents(){
        ComponentsAlgorithms.showStronglyConnectedComponents(graph);
    }

    private void showMinimumSpanningTree(){
        KruskalAlgorithm.showMinimumSpanningTree(graph);
    }

    private void handleGraphOptions(){
        int option = AppView.inputMenuOption();

        switch (option){
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

        handleGraphOptions();
    }

    public void bootstrap(){
        String filename = ViewUtils.input("Informe o caminho para o arquivo de texto do grafo: ");

        graph = GraphUtils.readGraph("src/main/resources/" + filename);

        handleGraphOptions();
    }

    public static void start(){
        App app = new App();

        app.bootstrap();
    }
}
