package game.model.objects.buildings;

import game.model.Directions;
import game.model.movables.CarriableResource;
import game.model.movables.Ork;
import game.model.objects.BuildingSign;
import game.model.objects.WorldObject;
import game.model.resources.Resource;
import game.model.world.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract building, standard way of creating buildings. In most cases a building owns an entrance(BuildingSign) which connects it to the road system.
 */
public abstract class Building extends WorldObject {
    /**
     * The entry sing
     */
    protected BuildingSign entry;
    /**
     * When a building needs to be enabled first, the resources needed for building.
     */
    protected Map<Resource, Integer> forEnable;

    public boolean isEnabled(){
        if(forEnable==null)
            return true;
        for(Integer i :forEnable.values()){
            if(i>0)
                return false;
        }
        forEnable = null;
        return true;
    }

    public void priceToEnable(Integer wood, Integer stone){
        forEnable = new HashMap<>();
        forEnable.put(Resource.WOOD, wood);
        forEnable.put(Resource.STONE, stone);
        for(int i = 0 ; i<wood; i++){
            MainBuilding.getAddress().askResource(Resource.WOOD, this);
        }
        for(int i = 0 ; i<stone; i++){
            MainBuilding.getAddress().askResource(Resource.STONE, this);
        }
    }

    public void tryGetResource(){
        for(CarriableResource r: new ArrayList<>(getEntry().getResources())){
            if(r.getDestination()==this){
                getEntry().pick(r);
                forEnable.put(r.getR(), forEnable.get(r.getR())-1);
                r.die();
            }
        }
    }

    Building(Field p) {
        super(p.getNeighbour(Directions.LU));
        entry = new BuildingSign(p, this);
    }

    Building(Field p, boolean createFlag) {
        super(p.getNeighbour(Directions.LU));
        if (createFlag)
            entry = new BuildingSign(p, this);
    }

    public static Field[] getOccupy(Field p) {
        Field[] f = new Field[1];
        f[0] = p;
        return f;
    }

    public static boolean checkBuildable(Field p) {
        if (!p.isClear())
            return false;
        try {
            for (Field f : getOccupy(p.getNeighbour(Directions.LU))) {
                if (!f.isClear())
                    return false;
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public BuildingSign getEntry() {
        return entry;
    }

    public void enable() {
        isAlive = true;
    }

    public void die() {
        super.die();
        Field[] fields = getOccupy(this.location);
        for (Field field : fields) {
            field.clear();
        }
        if(entry.isAlive())
            entry.die();
    }

    public void acceptProbe(Ork.OrkProbe probe) {
        probe.addInfo(false, this);
    }

    public void OrkEnters(Ork o) {
        die();
    }

}
