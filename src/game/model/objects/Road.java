package game.model.objects;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.Directions;
import game.model.world.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represent single, one field sized road part between two signs.
 */
public class Road extends WorldObject {
    /**
     * The directions of the road (What direction the road enters and leaves the field)
     */
    private Map<Directions, Field> dirs = new HashMap<>();

    /**
     * Creates a new road
     * @param p where
     * @param d1 direction
     * @param d2 direction
     */
    public Road(Field p, Directions d1, Directions d2) {
        super(p);
        dirs.put(d1, p.getNeighbour(d1));
        dirs.put(d2, p.getNeighbour(d2));
        p.add(this);
    }

    /**
     * @return the road's directions
     */
    public Set<Directions> getDirections() {
        return dirs.keySet();
    }

    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    /**
     * An object can cross the road (because "It's not the deer that crosses the road, it's the road that crosses the forest")
     * @return always true.
     */
    public boolean canWalkTrough(){
        return true;
    }

    @Override
    public boolean tick() {
        return false;
    }
}
