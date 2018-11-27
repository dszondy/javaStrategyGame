package game.model;

import game.model.world.World;

import java.io.Serializable;

/**
 * Simple superclass for all game elements. Stores the world's reference and serializable for saving.
 */
public abstract class GameObject implements Serializable {
    /**
     * @return The active game's world.
     */
    public static World getWorld() {
        return world;
    }

    static protected World world;

    /**
     * Set the world for the actual game.
     * @param w The world used.
     */
    public static void setWorld(World w) {
        world = w;
    }

}
