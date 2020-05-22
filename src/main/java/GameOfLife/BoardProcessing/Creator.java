package GameOfLife.BoardProcessing;

import java.util.ArrayList;

public class Creator {
    public int startComputing(int[][] board, int threads, int generations){
        return 0;
    }

    ArrayList<Integer> indexes = new ArrayList<>();

    public int prepareData(int[][] board, int threadsNumber){
        indexes.add()
        int cellsNumber = (board.length - 2) * (board[0].length - 2);
        int surplus = cellsNumber % threadsNumber;
        for(int i = 0; i < threadsNumber; i++){
            int operations = cellsNumber / threadsNumber;
            if(surplus > 0){
                operations++;
                surplus--;
            }
            indexes.add(operations);
        }
    }
}
