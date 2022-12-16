package edu.kit.informatik;

import java.util.Stack;

/**
 * The market class holds a limited list of resources that can be bought.
 * 
 * @author Rezart Toli
 * @version 1.0
 */
public class Market {
    
    /**
     * The default starting price for products
     */
    final int defaultPrice = 5;
    /**
     * The counter with milk.
     */
    Stack<String> milk; 
    /**
     * The counter with flour.
     */
    Stack<String> flour; 
    /**
     * The counter with eggs.
     */
    Stack<String> egg;
    
     /**
      * Creates the resource stacks and fills them with 2 resources each.
      */
    public Market() {
        milk = new Stack<String>();
        flour = new Stack<String>();
        egg = new Stack<String>();
        for (int i = 0; i < 2; i++) {
            milk.push(Square.C.resource);
            flour.push(Square.M.resource);
            egg.push(Square.H.resource);
        }
    }

    /**
     * This method is called whenever the player tries to harvest something.
     * It adds the harvested resource if there's space and rewards the player for it.
     * @param resource the resource to be added
     * @return integer to indicate reward if positive or failure if negative
     */
    public int addResource(String resource) {
        int reward = 1;
        int maxCapacity = 5;
        if (resource.equals(Square.H.resource) && egg.size() != maxCapacity) {
            egg.push(resource);
        } else if (resource.equals(Square.M.resource) && flour.size() != maxCapacity) {
            flour.push(resource);
        } else if (resource.equals(Square.C.resource) && milk.size() != maxCapacity) {
            milk.push(resource);
        } else {
            reward = -1;
        }
        return reward;
    }
    
    /**
     * This method is called whenever a player wants to buy a resource
     * and it checks to see if the resource exists or is available.
     * @param resource the resource whose price we want
     * @return an integer as the price of the resource
     */
    public int getResourcePrice(String resource) {
        int price = defaultPrice;
        if (resource.equals(Square.H.resource) && !egg.empty()) {
            return price - egg.size() + 1;
        } else if (resource.equals(Square.M.resource) && !flour.empty()) {
            return price - flour.size() + 1;
        } else if (resource.equals(Square.C.resource) && !milk.empty()) {
            return price - milk.size() + 1;
        } else {
            return -1;
        }
    }
    
    /**
     * This method removes a resource from the market.
     * @param resource the resource to be removed
     */
    public void removeResource(String resource) {
        if (resource.equals(Square.H.resource) && !egg.empty()) {
            egg.pop();
        } else if (resource.equals(Square.M.resource) && !flour.empty()) {
            flour.pop();
        } else if (resource.equals(Square.C.resource) && !milk.empty()) {
            milk.pop();
        }
    }
    
    /**
     * This method returns information about the market.
     */
    @Override
    public String toString() {
        String marketInfo;
        
        marketInfo = egg.size() + ";" + Square.H.resource + "\n" 
                + milk.size() + ";" + Square.C.resource + "\n" 
                + flour.size() + ";" + Square.M.resource;
        
        return marketInfo;
    }
}
