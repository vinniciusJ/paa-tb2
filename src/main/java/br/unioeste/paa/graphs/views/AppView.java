package br.unioeste.paa.graphs.views;

import br.unioeste.paa.graphs.utils.ViewUtils;

import java.util.Map;

public class AppView {
    public static int inputMenuOption(){
        ViewUtils.showMenu(
                "[0] - Carregar outro grafo",
                "[1] - Gerar arquivo PNG do grafo carregado",
                "[2] - Aplicar algoritmo de busca em profundida",
                "[3] - Aplicar algoritmo de busca em largura",
                "[4] - Aplicar algoritmo de Bellman-Ford",
                "[5] - Aplicar algoritmo de Kruskal",
                "[6] - Listar as componentes conexas",
                "[7] - Listar as componentes formemente conexas",
                "[8] - Sair"
        );

        return ViewUtils.input("Escolha uma das opções acima: ", Integer::parseInt);
    }

}
