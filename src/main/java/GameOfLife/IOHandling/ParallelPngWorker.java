package GameOfLife.IOHandling;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ParallelPngWorker implements Runnable {

    private final List<int[][]> boards;
    private final String resultFilename;
    private int index;

    public ParallelPngWorker(List<int[][]> boards, String resultFilename, int startIndex) {
        this.boards = boards;
        this.resultFilename = resultFilename;
        index = startIndex;
    }

    @Override
    public void run() {
        for (int[][] board : boards) {
            int cellSizeI = board[0].length - 2 > 800 ? 1 : 800 / (board[0].length - 2);
            int cellSizeJ = board.length - 2 > 800 ? 1 : 800 / (board.length - 2);
            BufferedImage image = new BufferedImage(cellSizeI * (board.length - 2), cellSizeJ * (board[0].length - 2), BufferedImage.TYPE_INT_RGB);
            Graphics2D imageInterior = image.createGraphics();
            for (int i = 1; i < board.length - 1; i++) {
                for (int j = 1; j < board[0].length - 1; j++) {
                    if (board[i][j] == 1) {
                        imageInterior.setColor(Color.BLACK);
                    } else {
                        imageInterior.setColor(Color.WHITE);
                    }
                    imageInterior.fillRect((j - 1) * cellSizeJ, (i - 1) * cellSizeI, cellSizeJ, cellSizeI);
                }
            }
            try {
                ImageIO.write(image, "png", new File(resultFilename + index + ".png"));
            } catch (IOException e) {
                System.err.println("Nie można zapisać pliku .png. Sprawdź prawa do zapisu");
                System.exit(666);
            }
            index++;
        }
    }
}
