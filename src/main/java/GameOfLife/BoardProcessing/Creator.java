package GameOfLife.BoardProcessing;

import java.util.ArrayList;
import java.util.Arrays;

public class Creator {
    public int startComputing(int[][] board, int threads, int generations){
        return 0;
    }

    ArrayList<Integer> indexes = new ArrayList<>();
    int[][] board;
    int threadsNumber;

    public Creator(int[][] board, int threadsNumber){
        this.board = board;
        this.threadsNumber = threadsNumber;
    }

    public int prepareData(int[][] board){
        int last = board[0].length + 1;
        indexes.add(board[0].length + 1);
        int cellsNumber = (board[0].length * board.length - board[0].length - 2) - (board[0].length + 1) + 1;
        int surplus = cellsNumber % threadsNumber;
        for(int i = 0; i < threadsNumber; i++){
            int operations = cellsNumber / threadsNumber;
            if(surplus > 0){
                operations++;
                surplus--;
            }
            indexes.add(last + operations);
            last += operations;
        }
//        for(Integer i: indexes){
//            System.out.println(i);
//        }
        return 0;
    }

    public int[][] compute(int generations){
        Worker worker = new Worker(board, new ArrayList<>(indexes));
        for (int i = 1; i <= generations; i++) {
            ArrayList<Thread> threadArrayList = new ArrayList<>();
            for(int j = 1; j <= threadsNumber; j++) {
                Thread thread = new Thread(worker);
                threadArrayList.add(thread);
            }
            for(int j = 0; j < threadsNumber; j++) {
                threadArrayList.get(j).start();
                try {
                    threadArrayList.get(j).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            worker.rewriteBorders();
            worker.setBoard(worker.getNewBoard());
            worker.setIds(new ArrayList<>(indexes));
        }
        for(int i = 0; i < worker.getNewBoard().length; i++){
            for(int j = 0; j < worker.getNewBoard()[0].length; j++){
                System.out.print(worker.getBoard()[i][j]);
            }
            System.out.println();
        }
        return worker.getNewBoard();
    }
}
