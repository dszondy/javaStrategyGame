package game.model.objects;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.objects.buildings.Building;
import game.model.world.Field;

/**
 * Entry for a player controlled building.
 */
public class BuildingSign extends Sign {
    public Building getB() {
        return b;
    }

    /**
     * The building it enters
     */
    Building b;

    /**
     * Creates one
     * @param p The position of this sign
     * @param b the building it is used for.
     */
    public BuildingSign(Field p, Building b) {
        super(p);
        p.add(this);
        this.b = b;
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }
}
