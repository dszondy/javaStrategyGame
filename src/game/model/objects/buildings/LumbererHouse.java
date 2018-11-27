package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.Lumberman;
import game.model.world.Field;

public class LumbererHouse extends CollectorHouse<Lumberman> {


    public LumbererHouse(Field p) {
        super(p, 2);
        LumbererHouse self = this;
        setCreator(new Creator(){
            @Override
            protected void create() {
                new Lumberman(self);
            }
        });
    }

    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }
}
