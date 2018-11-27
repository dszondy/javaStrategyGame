package game.model.movables;

import game.model.objects.WorldObject;
import game.model.world.Field;

public abstract class FreeWalker extends WorldObject {
    FreeWalker(Field p) {
        super(p);
    }

    public Field getPlace() {
        return location;
    }

    public void setPlace(Field place) {
        this.location = place;
    }

}

