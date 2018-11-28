package game.model.movables;

import game.model.objects.WorldObject;
import game.model.world.Field;

/**
 * Probe fof finding known destinations (not for types)
 */
public class SimpleProbe extends FreeProbe<WorldObject> {
    /**
     * Constructor
     * @param from the field it comes from
     * @param to the field it checks
     * @param distance the distance from the start field
     */
    public SimpleProbe(Field from, Field to, int distance) {
        super(from, to, distance);
        to.acceptProbe(this);
    }
}