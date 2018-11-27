package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.CarriableResource;
import game.model.resources.Resource;
import game.model.world.Field;

import java.util.ArrayList;

public class Blacksmith extends MedBuilding {
    public Blacksmith(Field p) {
        super(p);
        priceToEnable(4, 3);
    }

    private Integer ongo = 0;
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
