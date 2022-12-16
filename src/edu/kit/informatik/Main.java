package edu.kit.informatik;

/**
 * Main class starts the game.
 * 
 * @author Rezart Toli
 * @version 1.0
 */
public class Main {
    /**
     * Reads the command line arguments.
     * @param args
     */
    public static void main(String[] args) {
        String players = args[0];
        String setup = args[1];
        GameInterface game = new GameInterface(players, setup);
        if (game.getCanStart()) {
            game.startGame();
        }
    }
}
