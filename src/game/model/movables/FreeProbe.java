package game.model.movables;

import game.model.objects.WorldObject;
import game.model.world.Field;

import java.awt.*;
import java.io.Serializable;

/**
 * it collects field info of every possible destination
 * @param <T>  the type of object it looks for.
 */
public class FreeProbe<T extends WorldObject> implements Serializable {
    /**
     * The last field
     */
    Field from;
    /**
    The field it checks
     */
    Field to;
    /**
     * Distance from it's starting point
     */
    int distance;

    /**
     * stores if the field is steppable
     */
    boolean isSteppable = false;
    /**
     * stores the object it found
     */
    T dest = null;

    /**
     * Creates a probe
     * @param from last field
     * @param to field it checks
     * @param distance distance from start
     */
    public FreeProbe(Field from, Field to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    /**
     * Adds info
     * @param isSteppable true if it can step on the field
     * @param dest the destination object if valid
     */
    public void addInfo(boolean isSteppable, T dest) {
        addInfo(isSteppable);
        this.dest = dest;
    }

    /**
     * Adds info
     * @param dest the destination object if valid
     */
    public void addInfo(boolean b) {
        isSteppable = b;
    }
}
