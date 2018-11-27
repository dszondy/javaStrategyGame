package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.CarriableResource;
import game.model.resources.Resource;
import game.model.world.Field;

public class Mine extends Building{
    public Mine(Field p) {
        super(p);
        priceToEnable(3, 1);
    }

    @Override
    public boolean tick() {
        if(!isAlive)
            return false;
        if(!isEnabled()){
            tryGetResource();
            return true;
        }
        else if(getLocation().hasResource()==Resource.IRON){
            if(1==getLocation().getResource()){
                this.getEntry().place(new CarriableResource(Resource.IRON, getEntry(), MainBuilding.getAddress()));
            }
        }
        return true;
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }
}
