package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.Lumberjack;
import game.model.world.Field;

/**
 * Medium building for wood cutting
 */
public class LumbererHouse extends CollectorHouse<Lumberjack> {
    /**
     * Creates a woodcutter.
     * @param p the entry's location
     */
    public LumbererHouse(Field p) {
        super(p, 2);
        LumbererHouse self = this;
        setCreator(new Creator(){
            @Override
            protected void create() {
                new Lumberjack(self);
            }
        });
    }

    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }
}
