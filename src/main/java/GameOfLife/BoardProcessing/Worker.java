package GameOfLife.BoardProcessing;

public class Worker implements Runnable {

    private int[][] board;
    private int[][] newBoard;

    private int id = 0;
    private int maxId;

    public Worker(int[][] board, int[][] newBoard, int maxId){
        this.board = board;
        this.newBoard = newBoard;
        this.maxId = maxId;
    }

    private void increment(){
        synchronized (Worker.class){
            id++;
        }
    }

    private int getId(){
        synchronized (Worker.class) {
            this.id ++;
            return this.id;
        }
    }

    @Override
    public void run() {
        System.out.println(this.getId() + " " + Thread.currentThread().getId());
        //this.increment();
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
