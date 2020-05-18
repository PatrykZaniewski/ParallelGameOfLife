package GameOfLife.IOHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHandler {

    private String entryPath;
    private String resultFilename;

    private int n;
    private int m;
    private Integer[][] board;

    public FileHandler(String entryPath, String resultFilename) {
        this.entryPath = entryPath;
        this.resultFilename = resultFilename;
    }

    public int readData() {
        File file = new File(entryPath);
        try {
            Scanner scanner = new Scanner(file);

            int readLines = 0;
            String line;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                line = line.trim();
                if (readLines == 0) {
                    if (line.split(" ").length == 2) {
                        String first = line.split(" ")[0];
                        String second = line.split(" ")[1];

                        n = Integer.parseInt(first);
                        m = Integer.parseInt(second);

                        board = new Integer[n + 2][m + 2];
                    } else {
                        return -1;
                    }
                } else {
                    String[] splitLine = line.split("");
                    if (splitLine.length != m) {
                        return -2;
                    }
                    for (int i = 0; i < splitLine.length; i++) {
                        int value = Integer.parseInt(splitLine[i]);
                        if (value == 0 || value == 1)
                            board[readLines][i + 1] = value;
                        else
                            return -1;
                    }
                }
                readLines++;
            }
            if (readLines - 1 != n) {
                return -3;
            }
        } catch (FileNotFoundException e) {
            return -4;
        } catch (NumberFormatException e) {
            return -1;
        }
        return 0;
    }

    public String getEntryPath() {
        return entryPath;
    }

    public void setEntryPath(String entryPath) {
        this.entryPath = entryPath;
    }

    public String getResultFilename() {
        return resultFilename;
    }

    public void setResultFilename(String resultFilename) {
        this.resultFilename = resultFilename;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public Integer[][] getBoard() {
        return board;
    }

    public void setBoard(Integer[][] board) {
        this.board = board;
    }
}
