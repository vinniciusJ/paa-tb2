package br.unioeste.paa.graphs.utils;

import de.vandermeer.asciitable.AsciiTable;

import java.util.Scanner;
import java.util.function.Function;

public class ViewUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String input(String label){
        System.out.print(label);
        return scanner.nextLine();
    }

    public static <T> T input(String label, Function<String, T> parser){
        String input = input(label);

        try{
            return parser.apply(input);
        }
        catch (NumberFormatException e){
            System.out.println("Formato inválido. Por favor, tente novamente.");
            return input(label, parser);
        }
    }

    public static void showMenu(String... options){
        AsciiTable menu = new AsciiTable();

        menu.addRule();

        for(String option : options){
            menu.addRow(option);
            menu.addRule();
        }

        System.out.println(menu.render());
    }

    public void showMessage(String message){
        System.out.println(message);
    }

    public void showError(Exception exception){
        System.out.println(exception.getMessage());
    }
}
