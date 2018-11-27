package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.CarriableResource;
import game.model.movables.Ork;
import game.model.movables.Warrior;
import game.model.world.Field;

import java.util.ArrayList;

import static game.model.resources.Resource.SWORD;

public class Stronghold extends BigBuilding {

    private int warriorsInside = 0;
    public Stronghold(Field p) {
        super(p);
        priceToEnable(3, 10);
    }

    @Override
    public boolean tick() {
        if (isAlive()) {
            if(!isEnabled()){
                tryGetResource();
                if(isEnabled())
                    for(int i=0; i<6; i++){
                    MainBuilding.getAddress().askResource(SWORD, this);
                    }
                return true;
            }
            if (warriorsInside > 4) {
                new Warrior(this);
                warriorsInside--;
                if(warriorsInside<=6)
                    MainBuilding.getAddress().askResource(SWORD, this);
            }else{
                for(CarriableResource resource : new ArrayList<>(getEntry().getResources())){
                    if(resource.getDestination()==this && resource.getR()==SWORD){
                        entry.pick(resource);
                        warriorsInside++;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void acceptReturning(Warrior warrior) {
        warriorsInside++;
        warrior.die();
    }
    public void OrkEnters(Ork ork){
        ork.die();
        if(warriorsInside<=0)
            die();
        else
            warriorsInside--;
    }
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }
}

