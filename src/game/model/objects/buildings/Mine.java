package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.CarriableResource;
import game.model.resources.Resource;
import game.model.world.Field;

/**
 * Small mine
 * Mines iron. it should be placed on iron deposit
 * !!!the building should be on an iron deposit not the sign!!!
 * Costs 3 wood and 1 stone to build
 */
public class Mine extends Building{
    /**
     * Creates a mine
     * @param p the mines entry's location
     */
    public Mine(Field p) {
        super(p);
        priceToEnable(3, 1);
    }

    /**
     * Mines 1 Iron if it can and sends to the MainBuilding
     * @return true while it's alive and can mine
     */
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
            }else
                return false;
        }
        return true;
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }
}
