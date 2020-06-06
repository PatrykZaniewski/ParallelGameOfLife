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
            case -6:
                System.err.println("Wprowadzona wartość odpowiadająca za liczbę wątków nie jest liczbą");
                break;
            case -7:
                System.err.println("Wprowadzona wartość odpowiadająca za liczbę generacji nie jest liczbą");
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
        long start = System.currentTimeMillis();
        int numberOfThreads = 0, numberOfGenerations = 0;
        if (args.length < 4) {
            handleError(-5);
            System.exit(-5);
        }
        FileHandler fileHandler = new FileHandler(args[0], args[1]);
        try {
            numberOfThreads = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            handleError(-6);
            System.exit(-6);
        }
        try {
            numberOfGenerations = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            handleError(-7);
            System.exit(-7);
        }

        if (args[0].equals("generate")) {
            fileHandler.generate(numberOfThreads, numberOfGenerations);
        } else {
            int error = fileHandler.readData();
            if (error != 0) {
                handleError(error);
                System.exit(error);
            }
            int[][] board = fileHandler.getBoard();
            Creator creator = new Creator(board, numberOfThreads);
            creator.prepareData(board);

            board = creator.compute(numberOfGenerations);

            fileHandler.saveData(board);
            fileHandler.makeOutputFiles(creator.getBoards(), Integer.parseInt(args[2]));
            long end = System.currentTimeMillis();
            System.out.println("Calosc: " + (end - start) + " ms");
        }

    }
}
