package game.model.movables;

import game.model.objects.WorldObject;
import game.model.world.Field;

/**
 * Logical superclass for all moveing objects. (does nothing important right now)
 */
public abstract class FreeWalker extends WorldObject {
    FreeWalker(Field p) {
        super(p);
    }

    public Field getLocation() {
        return location;
    }

    public void setLocation(Field place) {
        this.location = place;
    }

}

