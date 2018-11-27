package game.model.resources;

import java.io.Serializable;

/**
 * The in game resource types, that the player could need and use.
 */
public enum Resource implements Serializable {
    /**
     * For blacksmith to make SWORD
     */
    IRON,
    /**
     * For building
     */
    WOOD,
    /**
     * For building
     */
    STONE,
    /**
     * For strongholds to build
     */
    SWORD,
    /**
     * Just a 'null object' for easier use.
     */
    NONE
}
