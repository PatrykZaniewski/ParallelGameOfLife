package GameOfLife;

import GameOfLife.IOHandling.FileHandler;

public class Main {
    public static void main(String [] args){
        FileHandler fileHandler = new FileHandler(args[1], args[2]);
        fileHandler.readData();
    }
}
