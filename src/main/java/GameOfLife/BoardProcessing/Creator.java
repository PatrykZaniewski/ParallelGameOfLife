package GameOfLife.BoardProcessing;

import java.util.ArrayList;
import java.util.List;

public class Creator {

    List<Integer> indexes = new ArrayList<>();
    List<int[][]> boards;
    int[][] board;
    int threadsNumber;

    public Creator(int[][] board, int threadsNumber) {
        this.board = board;
        this.threadsNumber = threadsNumber;
        boards = new ArrayList<>();
        int[][] tmp = new int[board.length][board[0].length];
        for (int j = 0; j < board.length; j++) {
            tmp[j] = board[j].clone();
        }
        boards.add(tmp);
    }

    public void prepareData(int[][] board) {
        int last = board[0].length + 1;
        indexes.add(board[0].length + 1);
        int cellsNumber = (board[0].length * board.length - board[0].length - 2) - (board[0].length + 1) + 1;
        int surplus = cellsNumber % threadsNumber;
        for (int i = 0; i < threadsNumber; i++) {
            int operations = cellsNumber / threadsNumber;
            if (surplus > 0) {
                operations++;
                surplus--;
            }
            indexes.add(last + operations);
            last += operations;
        }
    }

    public int[][] compute(int generations) {
        Thread thread;
        Worker worker;
        long start = System.currentTimeMillis();
        for (int i = 1; i <= generations; i++) {
            List<Thread> threadArrayList = new ArrayList<>();
            List<Worker> workerArrayList = new ArrayList<>();
            for (int j = 1; j <= threadsNumber; j++) {
                worker = new Worker(board, indexes.get(j - 1), indexes.get(j));
                thread = new Thread(worker, String.valueOf(j));
                thread.start();
                threadArrayList.add(thread);
                workerArrayList.add(worker);
            }
            for (int j = 0; j < threadsNumber; j++) {
                try {
                    threadArrayList.get(j).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 0; j < threadsNumber; j++) {
                worker = workerArrayList.get(j);
                for (int k = worker.getLeft(); k < worker.getRight(); k++) {

                    int row = k / board[0].length;
                    int column = k % board[0].length;
                    board[row][column] = worker.getNewBoard()[row][column];
                }
            }
            this.rewriteBorders();

            int[][] tmp = new int[board.length][board[0].length];
            for (int j = 0; j < board.length; j++) {
                tmp[j] = board[j].clone();
            }
            boards.add(tmp);
        }
        long finish = System.currentTimeMillis();
        System.out.println("Liczenie plansz: " + (finish - start) + " ms");
        return board;
    }

    public List<int[][]> getBoards() {
        return boards;
    }

    public void rewriteBorders() {
        int n = board.length - 2;
        int m = board[0].length - 2;
        for (int i = 1; i <= m; i++) {
            board[0][i] = board[n][i];
            board[n + 1][i] = board[1][i];
        }

        for (int i = 1; i <= n; i++) {
            board[i][0] = board[i][m];
            board[i][m + 1] = board[i][1];
        }
        board[0][0] = board[board.length - 2][board[0].length - 2];
        board[0][board[0].length - 1] = board[board.length - 2][1];
        board[board.length - 1][board[0].length - 1] = board[1][1];
        board[board.length - 1][0] = board[1][board[0].length - 2];
    }
}
