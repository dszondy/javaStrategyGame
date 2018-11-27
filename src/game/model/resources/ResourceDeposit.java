package game.model.resources;

import java.io.Serializable;

/**
 * Hidden resource pack, for fields. It stores an ammount of one resource.
 * Mostly IRON.
 */
public class ResourceDeposit implements Serializable {
    /**
     * The resource type (in my case IRON)
     */
    private Resource r;
    /**
     * the ammount it has
     */
    private int ammount;

    /**
     * Creats a new deposit with given type and ammount.
     * @param type the type of the resource
     * @param ammount count of the resource
     */
    public ResourceDeposit(Resource type, int ammount){
        r = type;
        this.ammount = ammount;
    }

    /**
     * Creates an iron deposit.
     * @param ammount count of the resource
     */
    public ResourceDeposit( int ammount){
        this(Resource.IRON, ammount);
    }

    /**
     * Returns what kind of resource it holds.
     * @return teh type.
     */
    public Resource GetType() {
        return r;
    }

    /**
     * Mines resource
     * @param x The ammount we want.
     * @return How many we mined successfully.
     */
    public int getResource(int x) {
        if (x < ammount) {
            ammount -= x;
            return x;
        }
        int tmp = ammount;
        ammount = 0;
        return tmp;
    }

    /**
     * @return The amount left.
     */
    public int checkAmmount() {
        return ammount;
    }
}
