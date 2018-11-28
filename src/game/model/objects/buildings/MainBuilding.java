package game.model.objects.buildings;

import game.gui.Main;
import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.Directions;
import game.model.movables.CarriableResource;
import game.model.resources.Resource;
import game.model.world.Field;

import java.util.*;

public class MainBuilding extends BigBuilding {
    private static MainBuilding address;

    private Map<Resource, Integer> resources = new HashMap<>();
    private Map<Resource, List<Building>> toDeliver = new HashMap<>();

    public MainBuilding(Field p) {
        super(p);
        address = this;
        for (Resource r : Resource.values()) {
            resources.put(r, 100);
        }
        for (Resource r : Resource.values()) {
            toDeliver.put(r, new LinkedList<>());
        }
        world.setPlayersMainBuilding(this);
    }

    public static MainBuilding getAddress() {
        return world.getPlayersMainBuilding();
    }

    public static boolean checkBuildable(Field p) {
        if (!p.getNeighbour(Directions.LD).isClear())
            return false;
        for (Field f : getOccupy(p)) {
            if (!f.isClear())
                return false;
        }
        return true;
    }

    public Map<Resource, Integer> getResources() {
        return resources;
    }

    public void askResource(Resource r, Building to) {
        toDeliver.get(r).add(to);
    }

    public boolean tick() {
        for(CarriableResource r: new ArrayList<>(getEntry().getResources())){
            if(r.getDestination()==this){
                getEntry().pick(r);
                resources.put(r.getR(), resources.get(r.getR())+1);
                r.die();
            }
        }
        for (Resource r : Resource.values()) {
            while (resources.get(r) > 0 && toDeliver.get(r).size() > 0) {
                Building to = toDeliver.get(r).remove(0);
                if (to.isAlive()) {
                    entry.place(new CarriableResource(r, getEntry(), to));
                    resources.put(r, resources.get(r)-1);
                }
            }
        }
        return true;
    }

    public void die(){
        super.die();
        Main.GameOver();
    }
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }
}
