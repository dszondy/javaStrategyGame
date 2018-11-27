package game.model.movables;

import game.model.objects.WorldObject;
import game.model.world.Field;

public class SimpleProbe extends FreeProbe<WorldObject> {
    public SimpleProbe(Field from, Field to, int distance) {
        super(from, to, distance);
        to.acceptProbe(this);
    }
}