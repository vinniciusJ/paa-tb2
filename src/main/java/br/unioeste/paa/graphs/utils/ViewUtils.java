package br.unioeste.paa.graphs.utils;

import de.vandermeer.asciitable.AsciiTable;

import java.util.Scanner;
import java.util.function.Function;

public class ViewUtils {
    private static final Scanner scanner = new Scanner(System.in);

    // Solicita uma entrada do usuário
    // Pré-condição: O rótulo (label) deve ser uma string válida
    // Pós-condição: Retorna a string inserida pelo usuário
    public static String input(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }

    // Solicita uma entrada do usuário e a converte para um tipo específico
    // Pré-condição: O rótulo (label) deve ser uma string válida e o parser deve ser uma função que converte String para T
    // Pós-condição: Retorna o valor convertido ou solicita novamente a entrada em caso de erro de formatação
    public static <T> T input(String label, Function<String, T> parser) {
        String input = input(label);

        try {
            return parser.apply(input);
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido. Por favor, tente novamente.");
            return input(label, parser);
        }
    }

    // Exibe um menu de opções formatado em uma tabela
    // Pré-condição: As opções (options) devem ser strings válidas
    // Pós-condição: O menu é exibido no console
    public static void showMenu(String... options) {
        AsciiTable menu = new AsciiTable();

        menu.addRule();

        for (String option : options) {
            menu.addRow(option);
            menu.addRule();
        }

        System.out.println(menu.render());
    }

    // Aguarda que o usuário pressione Enter para continuar
    // Pré-condição: Nenhuma
    // Pós-condição: O programa aguarda até que o usuário pressione Enter
    public static void waitForEnter() {
        System.out.println("\nPressione Enter para continuar...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}