package game.model;

/**
 * Interface for objects that can tick in every "turn"
 */
public interface Tickable {
    /**
     * Single function for giving control to an object for it's step.
     * @return Returns false, if it won't tick anymore. Then it shouldn't be called again.
     */
    boolean tick();
}
