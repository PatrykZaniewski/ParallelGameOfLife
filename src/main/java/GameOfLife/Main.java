package GameOfLife;

import GameOfLife.BoardProcessing.Creator;
import GameOfLife.IOHandling.FileHandler;

public class Main {

    private static void handleError(int code) {
        switch (code) {
            case -1:
                System.err.println("Podany plik zawierał błędy");
                break;
            case -2:
                System.err.println("Liczba kolumn w pliku jest nieprawidłowa.");
                break;
            case -3:
                System.err.println("Liczba rzedów w pliku jest nieprawidłowa");
                break;
            case -4:
                System.err.println("Podany plik nie istnieje lub nie może zostać otwarty.");
                break;
            case -5:
                System.err.println("Nie podano wymaganych argumentów.");
                break;
            case 666:
                System.err.println("Nie można zapisać pliku .png. Sprawdź prawa do zapisu");
                break;
            default:
                System.err.println("Wystąpił nieznany błąd.");
                break;
        }
        if (code < 0) {
            System.err.println("Argumenty to:");
            System.err.println("<plik wejścia> <nazwa wyjścia> <liczba wątków> <liczba generacji>");
        }
    }

    public static void main(String[] args) {
        if (args.length < 4) {
            handleError(-5);
            System.exit(-5);
        }
        FileHandler fileHandler = new FileHandler(args[0], args[1]);
        int error = fileHandler.readData();
        if (error != 0) {
            handleError(error);
            System.exit(error);
        }

        int[][] board = fileHandler.getBoard();

        for (int i = 0; i <= fileHandler.getN() + 1; i++) {
            for (int j = 0; j <= fileHandler.getM() + 1; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        Creator creator = new Creator(board, Integer.parseInt(args[2]));
        creator.prepareData(board);
        board = creator.compute(Integer.parseInt(args[3]));
        fileHandler.saveData(board);
        int outputFilesError = fileHandler.makeOutputFiles(creator.getBoards());
        if (outputFilesError != 0) {
            handleError(outputFilesError);
        }

        //TODO fredy i inne takie
    }
}
