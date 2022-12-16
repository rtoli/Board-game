package edu.kit.informatik;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class processes the commands and prints the output.
 * 
 * @author Rezart Toli
 * @version 1.0
 */
public class GameInterface {
    /*
     * These are all the command patterns that are used 
     * to check if the given command is correct.
     */
    private final String rollPattern = "roll\\s[0-9]";
    private final String harvestPattern = "harvest";
    private final String buyPattern = "buy\\s[a-z]*";
    private final String preparePattern = "prepare\\s[a-z]*";
    private final String canPreparePattern = "can-prepare\\?";
    private final String showMarketPattern = "show-market";
    private final String showPlayerPattern = "show-player\\sP[0-4]";
    private final String turnPattern = "turn";
    private final String quitPattern = "quit";
    private Game game;
    private boolean hasQuit = false;
    private boolean canStart = false;
    private String input;
    private int playerNumber;
    private String[] boardSquares;
    
    /**
     * The constructor creates the game with the given parameters if the setup
     * is correct otherwise it prints an error message.
     * @param players the amount of players participating
     * @param boardSetup the setup of the game board
     */
    public GameInterface(String players, String boardSetup) {
        if (correctSetup(players, boardSetup)) {
            game = new Game(boardSquares, playerNumber);
            canStart = true;
        } else {
            Terminal.printError("incorrect game setup!");
        }
    }
    
    /**
     * This method starts the game loop and executes the commands that are given.
     */
    public void startGame() {
        while (!game.isGameOver() && !hasQuit) {
            input = Terminal.readLine();
            String[] inputDetails = input.split("\\s");
            if (input.matches(rollPattern)) {
                roll(inputDetails[1]);
            } else if (input.matches(harvestPattern)) {
                Terminal.printLine(game.harvest());
            } else if (input.matches(buyPattern)) {
                Terminal.printLine(game.buy(inputDetails[1]));
            } else if (input.matches(preparePattern)) {
                prepare(inputDetails[1]);
            } else if (input.matches(canPreparePattern)) {
                canPrepare();
            } else if (input.matches(showMarketPattern)) {
                Terminal.printLine(game.showMarket());
            } else if (input.matches(showPlayerPattern)) {
                showPlayer(inputDetails[1]);
            } else if (input.matches(turnPattern)) {
                Terminal.printLine(game.turn());
            } else if (input.matches(quitPattern)) {
                hasQuit = true;
            } else {
                Terminal.printError("incorrect input!");
            }
        }
        //When the game is over the winner is announced.
        if (game.isGameOver()) {
            Terminal.printLine(game.winner());
        }
        //Player can still use the allowed commands after the game ends until he quits.
        while (!hasQuit) {
            input = Terminal.readLine();
            String[] inputDetails = input.split("\\s");
            
            if (input.matches(showMarketPattern)) {
                Terminal.printLine(game.showMarket());
            } else if (input.matches(showPlayerPattern)) {
                showPlayer(inputDetails[1]);
            } else if (input.matches(quitPattern)) {
                hasQuit = true;
            } else {
                Terminal.printError("incorrect input");
            }
        }
    }
    
    /**
     * This method checks if the game is correctly set up.
     * @param players the amount of players in the game
     * @param setup the setup of the game board
     * @return true if the set up is correct false otherwise
     */
    private boolean correctSetup(String players, String setup) {
        boardSquares = setup.split(";");
        String pattern1 = "S;(M|H|C);(M|H|C);(M|H|C)(;(M|H|C))*";
        Pattern invalidPatterns = Pattern.compile("M;M|H;H|C;C|M;H;C;H;C|M;C;H;C;H"
                                        + "|H;M;C;M;C|H;C;M;C;M|C;M;H;M;H|C;H;M;H;M");
        Matcher m = invalidPatterns.matcher(setup);
        
        try {
            playerNumber = Integer.parseInt(players);
        } catch (NumberFormatException e) {
            return false;
        }
        
        if (playerNumber < 2 || playerNumber > 4) {
            return false;
        }
        if (!setup.matches(pattern1) || !allSquareTypes(boardSquares)) {
            return false;
        }
        if (m.find()) {
            return false;
        }
        return true;
    }
    
    /**
     * This method checks if all the types of game squares are included
     * in the setup.
     * @param squares an array of the game squares in the board
     * @return true if they are included and false if they aren't
     */
    private boolean allSquareTypes(String[] squares) {
        int mSquares = 0;
        int hSquares = 0;
        int cSquares = 0;
        for (String s : squares) {
            if (s.equals(Square.M.toString())) {
                mSquares++;
            } else if (s.equals(Square.H.toString())) {
                hSquares++;
            } else {
                cSquares++;
            }
        }
        if (mSquares >= 1 && hSquares >= 1 && cSquares >= 1) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * This method converts the roll into an integer and prints the returned value
     * from the roll in the game command.
     * @param numRolled the number rolled
     */
    private void roll(String numRolled) {
        int roll = Integer.parseInt(numRolled);
        Terminal.printLine(game.roll(roll));
    }
    
    /**
     * This method invokes the return command form the game and it prints the appropriate
     * text depending on the output.
     * @param recipe the recipe to be prepared
     */
    private void prepare(String recipe) {
        int gold = game.prepare(recipe);
        if (gold == -1) {
            Terminal.printError("you must roll first!");
        } else if (gold == -2) {
            Terminal.printError("not enough ingridients!");
        } else if (gold == -3) {
            Terminal.printError("invalid recipe!");
        } else {
            Terminal.printLine(gold);
        }
    }
    
    /**
     * This method invokes the canPrepare method from the game.
     * If there's not return then it does nothing otherwise it prints the returned string.
     */
    private void canPrepare() {
        String output = game.canPrepare();
        if (output.length() == 0) {
            return;
        }
        Terminal.printLine(output);
    }
    
    /**
     * This method converts the player number into a string and invokes the showPlayer method.
     * @param player the player we want to know more about
     */
    private void showPlayer(String player) {
        int p = Integer.parseInt(player.substring(1, 2));
        Terminal.printLine(game.showPlayer(p));
    }

    /**
     * Getter that indicates if the game can be started or not.
     * @return boolean value true if the game can start, false otherwise.
     */
    public boolean getCanStart() {
        return canStart;
    }
}