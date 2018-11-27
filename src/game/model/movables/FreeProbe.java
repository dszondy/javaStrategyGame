package game.model.movables;

import game.model.objects.WorldObject;
import game.model.world.Field;

public class FreeProbe<T extends WorldObject> {
    Field from;
    Field to;
    int distance;
    boolean isSteppable = false;
    T dest = null;

    public FreeProbe(Field from, Field to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public void addInfo(boolean isSteppable, T dest) {
        addInfo(isSteppable);
        this.dest = dest;
    }

    public void addInfo(boolean b) {
        isSteppable = b;
    }
}
