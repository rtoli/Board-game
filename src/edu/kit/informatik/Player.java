package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * The player class contains information about the player and its functions.
 * 
 * @author Rezart Toli
 * @version 1.0
 */
public class Player {
    private boolean hasMoved = false;
    private boolean hasBought = false;
    private boolean hasHarvested = false;
    private boolean craftsman = false;
    private int position = 0;
    private int flourCount;
    private int eggCount;
    private int milkCount;
    private int gold = 20;
    private List<String> usedRecipes = new ArrayList<String>();
    
    /**
     * This method moves the player in the board according to the die roll.
     * @param roll the amount by which the player will move
     * @param gameBoard the game board where the player will move
     * @return text with the game square where the player lands and his gold amount
     */
    public String move(int roll, List<String> gameBoard) {
        if (hasMoved) {
            return "Error, you have already moved!";
        } else {
            hasMoved = true;
            position += roll;
            if (position > gameBoard.size() - 1) {
                position -= gameBoard.size();
            }
            
            if (position == 0) {
                gold += 5;
            }
            return gameBoard.get(position) + ";" + gold;
        }
    }
    
    /**
     * This method tries to see if a resource can be harvested, 
     * adds it to the market if the action is possible and gives 
     * the player a reward.
     * @param resource the resource the player is trying to harvest
     * @param market the market where the player is selling the harvested resource
     * @return a string with the harvested resource and new gold amount or an error
     */
    public String harvest(String resource, Market market) {
        if (!hasMoved || hasBought || hasHarvested) {
            return "Error, harvesting not possible right now!";
        }
        int reward = market.addResource(resource);
        if (reward == 1) {
            gold += reward;
            hasHarvested = true;
            return resource + ";" + gold;
        } else {
            return "Error, the market is full!";
        }
    }
    
    /**
     * This method checks to see if the player fulfills the requirement
     * for buying and call another method to calculate expenses.
     * @param resource the resource the player is trying to buy
     * @param market the market from where the player is buying the resource
     * @return the text from the helper method "expenseCalculator()"
     */
    public String buy(String resource, Market market) {
        if (!hasMoved || hasHarvested) {
            return "Error, can't buy right now!";
        }
        if (resource.equals(Square.M.resource)) {
            return expenseCalculation(resource, market);
        } else if (resource.equals(Square.H.resource)) {
            return expenseCalculation(resource, market);
        } else {
            return expenseCalculation(resource, market);
        }
    }
    
    /**
     * This is a helper method that calculates if the player can 
     * afford a resource from the market (if it is available) and it deducts
     * the price from the gold amount the player has.
     * @param resource the resource being bought
     * @param market the market where the resource is being bought from
     * @return a text with the cost and the current gold or an error
     */
    public String expenseCalculation(String resource, Market market) {
        int cost = 0;
        cost = market.getResourcePrice(resource);
        if (cost != -1) {
            if (cost <= gold) {
                gold -= cost;
                hasBought = true;
                market.removeResource(resource);
                if (resource.equals(Square.M.resource)) {
                    flourCount++;
                } else if (resource.equals(Square.H.resource)) {
                    eggCount++;
                } else {
                    milkCount++;
                }
                return cost + ";" + gold;
            } else {
                return "Error, not enough money!";
            }
            
        } else {
            if (resource.equals(Square.M.resource)) {
                return "Error, no flour on market available";
            } else if (resource.equals(Square.H.resource)) {
                return "Error, no eggs on market available"; 
            } else {
                return "Error, no milk on market available";
            }
        }
    }
    
    /**
     * This method calculates what recipes the player can prepare
     * with the resources he has.
     * @return text with recipes that can be prepared
     */
    public String canPrepare() {
        String list = "";
        
        if (flourCount == 0 && eggCount == 0 && milkCount == 0) {
            return list;
        }
        
        if (flourCount >= 3 && eggCount >= 3 && milkCount >= 3) {
            for (Recipe r : Recipe.values()) {
                list += r.nameLowerCase() + "\n";
            }
            return list.substring(0, list.length() - 1);
        }
        
        if (milkCount >= 3) {
            list += Recipe.YOGHURT.nameLowerCase() + "\n";
        }
        
        if (eggCount >= 3) {
            list += Recipe.MERINGUE.nameLowerCase() + "\n";
        }
        
        if (flourCount >= 3) {
            list += Recipe.BREAD.nameLowerCase() + "\n";
        }
        
        if (flourCount >= 2 && eggCount >= 2 && milkCount >= 2) {
            int count = 0;
            for (Recipe r : Recipe.values()) {
                count++;
                if (count > 3) {
                    list += r.nameLowerCase() + "\n";
                }
            }
            return list.substring(0, list.length() - 1);
        }
        
        if (flourCount >= 2 && milkCount >= 1) {
            list += Recipe.BUN.nameLowerCase() + "\n";
        }
        
        if (flourCount >= 1 && eggCount >= 2) {
            list += Recipe.CREPE.nameLowerCase() + "\n";
        }
        
        if (eggCount >= 1 && milkCount >= 2) {
            list += Recipe.PUDDING.nameLowerCase() + "\n";
        }
        
        if (list.length() == 0) {
            return "";
        } else {
            return list.substring(0, list.length() - 1);
        } 
    }
    
    /**
     * This method checks if a the desired recipe can be prepared
     * and consumes the necessary resources for it.
     * @param recipe the recipe that will be prepared
     * @return integer of current gold amount
     */
    public int prepareRecipe(String recipe) {
        if (!hasMoved) {
            return -1; //This is returned to indicate that the player hasn't moved yet
        }
        if (canPrepare().contains(recipe)) {
            if (recipe.equals(Recipe.YOGHURT.nameLowerCase())) {
                milkCount -= 3;
                return prepareYoghurt(recipe);
            } else if (recipe.equals(Recipe.MERINGUE.nameLowerCase())) {
                eggCount -= 3;
                return prepareMeringue(recipe);
            } else if (recipe.equals(Recipe.BREAD.nameLowerCase())) {
                flourCount -= 3;
                return prepareBread(recipe);
            } else if (recipe.equals(Recipe.BUN.nameLowerCase())) {
                flourCount -= 2;
                milkCount--;
                return prepareBun(recipe);
            } else if (recipe.equals(Recipe.CREPE.nameLowerCase())) {
                flourCount--;
                eggCount -= 2;
                return prepareCrepe(recipe);
            } else if (recipe.equals(Recipe.PUDDING.nameLowerCase())) {
                eggCount--;
                milkCount -= 2;
                return preparePudding(recipe);
            } else if (recipe.equals(Recipe.CAKE.nameLowerCase())) {
                flourCount -= 2;
                eggCount -= 2;
                milkCount -= 2;
                return prepareCake(recipe);
            }
        }
        return -2; // This is returned in case the recipe doesn't exist
    }
    
    /**
     * This gives the appropriate reward to the player whether it is only 
     * for preparing yoghurt or for attaining his certificate too.
     * @param recipe the recipe for yoghurt
     * @return integer of current gold amount
     */
    private int prepareYoghurt(String recipe) {
        if (!usedRecipes.contains(recipe)) {
            usedRecipes.add(recipe);
            if (certificateReward()) {
                gold += Recipe.YOGHURT.price + 25;
                return gold;
            } else {
                gold += Recipe.YOGHURT.price;
                return gold;
            }
        } else {
            gold += Recipe.YOGHURT.price;
            return gold;
        }
    }
    
    /**
     * This gives the appropriate reward to the player whether it is only 
     * for preparing meringue or for attaining his certificate too.
     * @param recipe the recipe for meringue
     * @return integer of current gold amount
     */
    private int prepareMeringue(String recipe) {
        if (!usedRecipes.contains(recipe)) {
            usedRecipes.add(recipe);
            if (certificateReward()) {
                gold += Recipe.MERINGUE.price + 25;
                return gold;
            } else {
                gold += Recipe.MERINGUE.price;
                return gold;
            }
        } else {
            gold += Recipe.MERINGUE.price;
            return gold;
        }
    }
    
    /**
     * This gives the appropriate reward to the player whether it is only 
     * for preparing bread or for attaining his certificate too.
     * @param recipe the recipe for bread
     * @return integer of current gold amount
     */
    private int prepareBread(String recipe) {
        if (!usedRecipes.contains(recipe)) {
            usedRecipes.add(recipe);
            if (certificateReward()) {
                gold += Recipe.BREAD.price + 25;
                return gold;
            } else {
                gold += Recipe.BREAD.price;
                return gold;
            }
        } else {
            gold += Recipe.BREAD.price;
            return gold;
        }
    }
    
    /**
     * This gives the appropriate reward to the player whether it is only 
     * for preparing bun or for attaining his certificate too.
     * @param recipe the recipe for bun
     * @return integer of current gold amount
     */
    private int prepareBun(String recipe) {
        if (!usedRecipes.contains(recipe)) {
            usedRecipes.add(recipe);
            if (certificateReward()) {
                gold += Recipe.BUN.price + 25;
                return gold;
            } else {
                gold += Recipe.BUN.price;
                return gold;
            }
        } else {
            gold += Recipe.BUN.price;
            return gold;
        }
    }
    
    /**
     * This gives the appropriate reward to the player whether it is only 
     * for preparing crepe or for attaining his certificate too.
     * @param recipe the recipe for crepe
     * @return integer of current gold amount
     */
    private int prepareCrepe(String recipe) {
        if (!usedRecipes.contains(recipe)) {
            usedRecipes.add(recipe);
            if (certificateReward()) {
                gold += Recipe.CREPE.price + 25;
                return gold;
            } else {
                gold += Recipe.CREPE.price;
                return gold;
            }
        } else {
            gold += Recipe.CREPE.price;
            return gold;
        }
    }
    
    /**
     * This gives the appropriate reward to the player whether it is only 
     * for preparing pudding or for attaining his certificate too.
     * @param recipe the recipe for pudding
     * @return integer of current gold amount
     */
    private int preparePudding(String recipe) {
        if (!usedRecipes.contains(recipe)) {
            usedRecipes.add(recipe);
            if (certificateReward()) {
                gold += Recipe.PUDDING.price + 25;
                return gold;
            } else {
                gold += Recipe.PUDDING.price;
                return gold;
            }
        } else {
            gold += Recipe.PUDDING.price;
            return gold;
        }
    }
    
    /**
     * This gives the appropriate reward to the player whether it is only 
     * for preparing cake or for attaining his certificate too.
     * @param recipe the recipe for cake
     * @return integer of current gold amount
     */
    private int prepareCake(String recipe) {
        if (!usedRecipes.contains(recipe)) {
            usedRecipes.add(recipe);
            if (certificateReward()) {
                gold += Recipe.CAKE.price + 25;
                return gold;
            } else {
                gold += Recipe.CAKE.price;
                return gold;
            }
        } else {
            gold += Recipe.CAKE.price;
            return gold;
        }
    }
    
    /**
     * This method is called every time a recipe is made
     * to see if the player will get a reward for attaining
     * his certificate.
     * @return boolean indicating if a reward should be given or not
     */
    private boolean certificateReward() {
        if (usedRecipes.size() == 7 && !craftsman) {
            craftsman = true;
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * This methods checks whether the player can end the turn or not.
     * If the player can end the turn the variables used to check a players 
     * legal actions are reset.
     * @return boolean value indicating if the player has ended his turn or not
     */
    public boolean endTurn() {
        if (hasMoved) {
            hasMoved = false;
            hasBought = false;
            hasHarvested = false;
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * This method checks if the winning condition is fulfilled.
     * @return boolean value showing if the player has won or not
     */
    public boolean hasWon() {
        if (gold >= 100) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * This methods returns a text with details about the player.
     */
    @Override
    public String toString() {
        return gold + ";" + flourCount + ";" + eggCount + ";" + milkCount;
    }

    /**
     * This method gets the players position on the board.
     * @return position as an integer
     */
    public int getPosition() {
        return position;
    }

    /**
     * This method sets the players new position.
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }
}