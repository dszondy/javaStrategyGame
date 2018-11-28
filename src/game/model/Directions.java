package game.model;

/**
 * Enum for diretions in the hexagonal game mape
 */
public enum Directions {
    /**
     * Right
     */
    R(0),
    /**
     * Right-up
     */
    RU(1),
    /**
     * Left-up
     */
    LU(2),
    /**
     * Left
     */
    L(3),
    /**
     * Left-down
     */
    LD(4),
    /**
     * Right-down
     */
    RD(5);
    /**
     * Has an integer value associated to all directions for easier use
     */
    private final int Int;

    /**
     * Setting up the enum form an integer
     * @param d int represents a direction
     */
    Directions(int d) {
        Int = d;
    }

    /**
     * For casting back to integer
     * @return integer value of the enum
     */
    int toInt() {
        return Int;
    }

    /**
     * Changes a direction to it's opposite:
     *     Left-Right
     *     Up-Down
     * @return The opposite direction
     */
    public Directions flip() {
        switch (this) {
            case L:
                return R;
            case LU:
                return RD;
            case RU:
                return LD;
            case R:
                return L;
            case RD:
                return LU;
            case LD:
                return RU;
        }
        return null;
    }
}
