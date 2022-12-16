package edu.kit.informatik;

/**
 * This enum has the recipes and the prices they reward
 * 
 * @author Rezart Toli
 * @version 1.0
 */
public enum Recipe {
    /**
     * Yoghurt and the reward price for preparing it.
     */
    YOGHURT(8), 
    /**
     * Meringue and the reward price for preparing it.
     */
    MERINGUE(9), 
    /**
     * Bread and the reward price for preparing it.
     */
    BREAD(10), 
    /**
     * Bun and the reward price for preparing it.
     */
    BUN(11), 
    /**
     * Crepe and the reward price for preparing it.
     */
    CREPE(12), 
    /**
     * Pudding and the reward price for preparing it.
     */
    PUDDING(13), 
    /**
     * Cake and the reward price for preparing it.
     */
    CAKE(22);

    /**
     * The price that's given as a reward for preparing a recipe.
     */
    public final int price;

    private Recipe(int price) {
        this.price = price;
    }
    
    /**
     * Converts to lower case.
     * @return the recipe name as a lower case string
     */
    public String nameLowerCase() {
        return name().toLowerCase();
    }
}