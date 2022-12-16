package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * The game class handles the market, players the board and the interactions between them.
 * 
 * @author Rezart Toli\
 * @version 1.0
 */
public class Game {
    
    private boolean gameOver = false;
    private int playerTurn = 0;
    private int playerCount;
    private Market market = new Market();
    private List<Player> players =  new ArrayList<Player>();
    private List<String> gameBoard = new ArrayList<String>();
    
    /**
     * The constructor builds the game board and sets how many players are in the game
     * according to the parameters given to it.
     * @param board the board setup
     * @param playerCount the number of player participating
     */
    public Game(String[] board, int playerCount) {
        this.playerCount = playerCount;
        
        for (String b : board) {
            gameBoard.add(b.toUpperCase());
        }
        
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player());
        }
    }
    
    /**
     * This method handles the roll function for the game 
     * and tells the player to move by the rolled amount.
     * @param roll the number rolled
     * @return a text with the players position on the board or an error
     */
    public String roll(int roll) {
        if (roll < 1 || roll > 6) {
            return "Error, only rolls between 1-6 are valid";
        }
        return players.get(playerTurn).move(roll, gameBoard);
    }
    
    /**
     * Invoke a players harvest method and give him the resource he can
     * harvest depending on what square on the board he is.
     * @return the resource harvested and the players gold or an error
     */
    public String harvest() {
        String resource = gameBoard.get(players.get(playerTurn).getPosition());
        String harvest = "";
        for (Square r : Square.values()) {
            if (r.toString().equals(resource)) {
                resource = r.resource;
            }
        }
        
        if (resource.equals("start")) {
            return "Error, there's nothing to harvest here";
        } else {
            harvest = players.get(playerTurn).harvest(resource, market);
            setGameOver(players.get(playerTurn).hasWon());
            return harvest;
        }
        
    }
    
    /**
     * Invoke the players buy method.
     * @param resource the resource we want to buy
     * @return the cost of the resource and the players gold or an error
     */
    public String buy(String resource) {
        for (Square r : Square.values()) {
            if (r.resource.equals(resource)) {
                return players.get(playerTurn).buy(resource, market);
            }
        }
        return "Error, the resource doesnt exist!";
    }
    
    /**
     * Invoke the players prepare method.
     * @param recipe the recipe we want to prepare
     * @return the players gold or a negative integer to indicate an error
     */
    public int prepare(String recipe) {
        int gold;
        for (Recipe r : Recipe.values()) {
            if (r.nameLowerCase().equals(recipe)) {
                gold = players.get(playerTurn).prepareRecipe(recipe);
                setGameOver(players.get(playerTurn).hasWon());
                return gold;
            }
        }
        return -3;
    }
    
    /**
     * Gets the list of what a player can prepare.
     * @return the list of recipes a player can prepare
     */
    public String canPrepare() {
        return players.get(playerTurn).canPrepare();
    }
    
    /**
     * Gets the information about the market.
     * @return text with market information
     */
    public String showMarket() {
        return market.toString();
    }
    
    /**
     * Gives information about the requested player.
     * @param player the player whose info we want to know
     * @return information about the player
     */
    public String showPlayer(int player) {
        if (player < 1 || player > playerCount ) {
            return "Error, player doesn't exist";
        }
        return players.get(player - 1).toString();
    }
    
    /**
     * Ends the turn for a player and gives it to the next one.
     * @return text indicating whose turn it is or an error
     */
    public String turn() {
        if (players.get(playerTurn).endTurn()) {
            if (playerTurn + 1 != playerCount) {
                playerTurn++;
            } else {
                playerTurn = 0;  
                
            }
        } else {
            return "Error, you must roll first";
        }
        return "P" + (playerTurn + 1);
    }

    /**
     * Getter method to check if the game has ended.
     * @return if the game is over or not
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Setter method thats used to end the game.
     * @param gameOver boolean to indicate if the game is over
     */
    private void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    /**
     * Method that is called if the game is over to declare the winner.
     * @return the winner of the game
     */
    public String winner() {
        return "P"  + (playerTurn + 1) + " wins";
    }
}