package GameOfLife.BoardProcessing;

import java.util.ArrayList;

public class Worker implements Runnable {

    private int[][] board;
    private int[][] newBoard;

    private int id = 0;
    private int maxId;
    private ArrayList<Integer> ids;

    public Worker(int[][] board, ArrayList<Integer> ids){
        this.board = board;
        this.newBoard = new int[board.length][board[0].length];
        this.ids = ids;
    }

    private int getOne(){
        synchronized (Worker.class){
            return ids.get(0);
        }
    }

    private int getRemove(){
        synchronized (Worker.class) {
            return ids.remove(0);
        }
    }

    private void setValue(int row, int column, int value){
        synchronized (Worker.class){
            newBoard[row][column] = value;
        }
    }

    @Override
    public void run() {
        //System.out.println(this.getRemove() + " " + this.getOne());
        int left = this.getRemove();
        int right = this.getOne();
        for(int i = left; i < right; i++){
            if(i % 7 != 0 && i + 1 % 7 != 0){
                int row = i / board[0].length;
                int column = i % board[0].length;
                int counter = 0;
                if(board[row - 1][column - 1] == 1){
                    counter++;
                }
                if(board[row - 1][column] == 1){
                    counter++;
                }
                if(board[row - 1][column + 1] == 1){
                    counter++;
                }
                if(board[row][column - 1] == 1){
                    counter++;
                }
                if(board[row][column + 1] == 1){
                    counter++;
                }
                if(board[row + 1][column - 1] == 1){
                    counter++;
                }
                if(board[row + 1][column] == 1){
                    counter++;
                }
                if(board[row + 1][column + 1] == 1){
                    counter++;
                }
                if(board[row][column] == 0 && counter == 3){
                    setValue(row, column, 1);
                }
                if(board[row][column] == 1 && counter != 2 && counter != 3){
                    setValue(row, column, 0);
                }
            }
        }
    }

    public int[][] rewriteBorders(){
        //TODO to mozna podpiac pod ostatni watek zeby zwiekszyc wydajnosc i nie blokowac programu
        int m = newBoard.length - 1;
        int n = newBoard[0].length - 1;
        for(int i = 1; i <= m; i++){
            newBoard[0][i] = newBoard[n][i];
            newBoard[n+1][i] = newBoard[1][i];
        }

        for(int i = 1; i <= n; i++){
            newBoard[i][0] = newBoard[i][m];
            newBoard[i][m + 1] = newBoard[i][1];
        }
        newBoard[0][0] = newBoard[newBoard.length - 2][newBoard[0].length - 2];
        newBoard[0][newBoard[0].length - 1] = newBoard[newBoard.length - 2][1];
        newBoard[newBoard.length - 1][newBoard[0].length - 1] = newBoard[1][1];
        newBoard[newBoard.length - 1][0] = newBoard[1][newBoard[0].length - 2];

        return newBoard;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[][] getNewBoard() {
        return newBoard;
    }

    public void setNewBoard(int[][] newBoard) {
        this.newBoard = newBoard;
    }
}
