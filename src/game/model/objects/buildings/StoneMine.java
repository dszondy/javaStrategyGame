package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.StoneMiner;
import game.model.world.Field;

public class StoneMine extends CollectorHouse<StoneMiner> {
    /**
     * Creates a stne mine
 * @param p the entry's location
     */
    public StoneMine(Field p) {
        super(p, 2);
        StoneMine self = this;
        setCreator(new Creator(){
            @Override
            protected void create() {
                new StoneMiner(self);
            }
        });
    }

    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }
}
