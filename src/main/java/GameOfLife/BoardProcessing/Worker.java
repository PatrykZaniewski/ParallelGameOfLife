package GameOfLife.BoardProcessing;

import java.util.Arrays;
import java.util.Random;

public class Worker implements Runnable {

    private int[][] board;
    private int[][] newBoard;

    private int right;
    private int left;

    public Worker(int[][] board, int left, int right) {
        this.board = board;
        this.newBoard = new int[board.length][board[0].length];
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        for (int i = left; i < right; i++) {
            int row = i / board[0].length;
            int column = i % board[0].length;
            if (column != 0 && column != board[0].length - 1) {
                int counter = 0;
                if (board[row - 1][column - 1] == 1) {
                    counter++;
                }
                if (board[row - 1][column] == 1) {
                    counter++;
                }
                if (board[row - 1][column + 1] == 1) {
                    counter++;
                }
                if (board[row][column - 1] == 1) {
                    counter++;
                }
                if (board[row][column + 1] == 1) {
                    counter++;
                }
                if (board[row + 1][column - 1] == 1) {
                    counter++;
                }
                if (board[row + 1][column] == 1) {
                    counter++;
                }
                if (board[row + 1][column + 1] == 1) {
                    counter++;
                }
                if (board[row][column] == 0 && counter == 3) {
                    newBoard[row][column] = 1;
                }
                if (board[row][column] == 1) {
                    if (counter != 2 && counter != 3) {
                        newBoard[row][column] = 0;
                    } else {
                        newBoard[row][column] = 1;
                    }
                }
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
        this.newBoard = new int[board.length][board[0].length];
    }

    public int[][] getNewBoard() {
        return newBoard;
    }

    public void setNewBoard(int[][] newBoard) {
        this.newBoard = newBoard;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }
}
