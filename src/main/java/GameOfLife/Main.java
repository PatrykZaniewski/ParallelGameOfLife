package GameOfLife;

import GameOfLife.BoardProcessing.Creator;
import GameOfLife.IOHandling.FileHandler;

import java.util.Arrays;

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
                System.err.println("Wprowadzona wartość odpowiadająca za liczbę wątków i/lub generacji nie jest liczbą");
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

        if (args.length < 4) {
            handleError(-5);
            System.exit(-5);
        }
        FileHandler fileHandler = new FileHandler(args[0], args[1]);
        if (args[0].equals("generate")) {
            try {
                fileHandler.generate(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            int error = fileHandler.readData();
            if (error != 0) {
                handleError(error);
                System.exit(error);
            }

            int[][] board = fileHandler.getBoard();

            //TODO Integer.parseInt koniecznie sprawdzamy numberformat!
            try {
                Creator creator = new Creator(board, Integer.parseInt(args[2]));
                creator.prepareData(board);

                board = creator.compute(Integer.parseInt(args[3]));

//            for(int[][] board1: creator.getBoards()){
//                System.out.println(Arrays.deepToString(board1));
//            }

                fileHandler.saveData(board);
                fileHandler.makeOutputFiles(creator.getBoards(), Integer.parseInt(args[2]));
                long t2 = System.currentTimeMillis();
                System.out.println((t2 - start) + " www");
            }
            catch (NumberFormatException e){
                handleError(-6);
                System.exit(-6);
            }
        }

    }
}
