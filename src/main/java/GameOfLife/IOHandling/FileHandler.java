package GameOfLife.IOHandling;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FileHandler {

    private String entryPath;
    private String resultFilename;

    private int n;
    private int m;
    private int[][] board;

    public FileHandler(String entryPath, String resultFilename) {
        this.entryPath = entryPath;
        this.resultFilename = resultFilename;
    }

    public void generate(int n, int m) {
        n += 2;
        m += 2;
        Random random = new Random();
        int[][] board = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (random.nextDouble() > 0.5) {
                    board[i][j] = 1;
                } else {
                    board[i][j] = 0;
                }
            }
        }
        saveData(board);
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

                        board = new int[n + 2][m + 2];
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
        return 0;
    }

    public int saveData(int[][] board) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(resultFilename + ".txt"));
            writer.println((board.length - 2) + " " + (board[0].length - 2));
            for (int i = 1; i <= board.length - 2; i++) {
                for (int j = 1; j <= board[0].length - 2; j++) {
                    writer.print(board[i][j]);
                }
                writer.println("");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void makeOutputFiles(List<int[][]> boards, int threadNumber) {
        //TODO robienie gifa
        int boardsNumberPerThread = boards.size() / threadNumber;
        List<Thread> pngThreads = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            List<int[][]> boardsPerThread;
            if (i == threadNumber - 1) {
                boardsPerThread = new ArrayList<>(boards.subList(i * boardsNumberPerThread, boards.size()));
            } else {
                boardsPerThread = new ArrayList<>(boards.subList(i * boardsNumberPerThread, (i + 1) * boardsNumberPerThread));
            }
            ParallelPngWorker worker = new ParallelPngWorker(boardsPerThread, resultFilename, i * boardsNumberPerThread + 1);
            Thread thread = new Thread(worker);
            pngThreads.add(thread);
        }
        for (Thread thread : pngThreads) {
            thread.start();
        }
        for (Thread thread : pngThreads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
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

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}
