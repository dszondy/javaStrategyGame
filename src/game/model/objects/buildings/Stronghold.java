package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.CarriableResource;
import game.model.movables.Ork;
import game.model.movables.Warrior;
import game.model.world.Field;

import java.util.ArrayList;

import static game.model.resources.Resource.SWORD;

/**
 * Stronghold for the player.
 * Warriors are in it, it automatically sends them to attack enemies.
 * For createing warriors it need's SWORD-s.
 */
public class Stronghold extends BigBuilding {
    /**
     * The cound of the warriors inside the stronghold
     */
    private int warriorsInside = 0;

    /**
     * Creates an empty stronghold
     * @param p
     */
    public Stronghold(Field p) {
        super(p);
        priceToEnable(5, 15);
    }

    /**
     * If it has more than 4 warriors it sends 1 to attack, and asks for resource to train a new one.
     * If has less, it collect the SWORDS from the entry.
     * @return
     */
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

    /**
     * A warrior returned, it adds it to the warriors inside.
     * @param warrior
     */
    public void acceptReturning(Warrior warrior) {
        warriorsInside++;
        warrior.die();
    }

    /**
     * if an ork attacks it, it kills the ork, and if has no warriorsInside, dies.
     * @param ork
     */
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

