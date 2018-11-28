package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.CarriableResource;
import game.model.resources.Resource;
import game.model.world.Field;

import java.util.ArrayList;

/**
 * In this medium sized building we can create Swords from iron
 * Costs 4 wood and 3 stone to build.
 */
public class Blacksmith extends MedBuilding {
    /**
     * Creates a blacksmith building
     * @param p
     */
    public Blacksmith(Field p) {
        super(p);
        priceToEnable(5, 4);
    }

    /**
     * Counts the asked resources.
     */
    private Integer ongo = 0;

    /**
     * Asks for iron and if one had arrived it creates a sword and sends back to the main building
     * @return true while it's alive
     */
    @Override
    public boolean tick() {
        if(!isAlive())
            return false;
        if(!isEnabled()){
            tryGetResource();
            return true;
        }
        if(ongo<3){
            MainBuilding.getAddress().askResource(Resource.IRON, this);
            ongo++;
        }
        for(CarriableResource r: new ArrayList<>(getEntry().getResources())){
            if(r.getDestination()==this&&r.getR()==Resource.IRON){
                getEntry().pick(r);
                r.die();
                ongo--;
                getEntry().place(new CarriableResource(Resource.SWORD, getEntry(), MainBuilding.getAddress()));
            }
        }
        return true;
    }

    public Drawer getDrawer(){return DrawerCreator.getDrawer(this); }
}
