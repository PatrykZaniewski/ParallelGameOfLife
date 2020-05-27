package GameOfLife.BoardProcessing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatorTest {

    private int[][] board;

    @BeforeEach
    public void setUp() {
        board = new int[7][7];
        board[2][3] = 1;
        board[3][4] = 1;
        board[4][2] = 1;
        board[4][3] = 1;
        board[4][4] = 1;
    }

    @Test
    void computeOneThreadTest() {
        //Glider test
        Creator creator = new Creator(board, 1);
        creator.prepareData(board);
        creator.compute(1);
        List<int[][]> boards = creator.getBoards();
        assertEquals(2, boards.size());
        int[][] boardGen = new int[7][7];
        boardGen[2][3] = 1;
        boardGen[3][4] = 1;
        boardGen[4][2] = 1;
        boardGen[4][3] = 1;
        boardGen[4][4] = 1;
        checkBoard(boards.get(0), boardGen);
        boardGen = new int[7][7];
        boardGen[3][2] = 1;
        boardGen[3][4] = 1;
        boardGen[4][3] = 1;
        boardGen[4][4] = 1;
        boardGen[5][3] = 1;
        checkBoard(boards.get(1), boardGen);
    }

    private void checkBoard(int[][] outBoard, int[][] checkBoard) {
        for (int i = 1; i < outBoard.length - 1; i++) {
            for (int j = 1; j < outBoard[0].length - 1; j++) {
                assertEquals(checkBoard[i][j], outBoard[i][j]);
            }
        }
    }
}