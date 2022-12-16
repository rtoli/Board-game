package edu.kit.informatik;

/**
 * This enum has the game squares and the resource for each of them.
 * 
 * @author Rezart Toli
 * @version 1.0
 */
public enum Square {
    /**
     * The chicken hen square and the resource it produces.
     */
    H("egg"), 
    /**
     * The mill square and the resource it produces.
     */
    M("flour"), 
    /**
     * The cow pasture square and the resource it produces.
     */
    C("milk");

    /**
     * String that can be returned to show the resource a square has.
     */
    public final String resource;

    private Square(String resource) {
        this.resource = resource;
    }
}
