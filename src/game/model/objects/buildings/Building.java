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
 *
 *  <>   <-Building
 *   <> <-entry sign
 *  */

public abstract class Building extends WorldObject {
    /**
     * The entry sing
     */
    protected BuildingSign entry;
    /**
     * When a building needs to be enabled first, the resources needed for building.
     */
    protected Map<Resource, Integer> forEnable;

    /**
     * Checks if the building has ben build yet
     * @return true if it has been build
     */
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

    /**
     * Sets the building's resource requirements, and asks for delivery of those
     * @param wood number of wood needed
     * @param stone number of stone needed
     */
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

    /**
     * Tries to collect resources from entry and completes it's build progress.
     */
    public void tryGetResource(){
        for(CarriableResource r: new ArrayList<>(getEntry().getResources())){
            if(r.getDestination()==this){
                getEntry().pick(r);
                forEnable.put(r.getR(), forEnable.get(r.getR())-1);
                r.die();
            }
        }
    }

    /**
     * Creates a building at a location, and it's sign
     * @param p location of the entry sign(right down drom the building)
     */
    Building(Field p) {
        super(p.getNeighbour(Directions.LU));
        entry = new BuildingSign(p, this);
    }

    /**
     * Creates a building
     * @param p location of the entry sign(right down drom the building)
     * @param createFlag if it should create an entry
     */
    Building(Field p, boolean createFlag) {
        super(p.getNeighbour(Directions.LU));
        if (createFlag)
            entry = new BuildingSign(p, this);
    }

    /**
     * Returns the fields it would take up
     * @param p the building left down corner
     * @return the fields
     */
    public static Field[] getOccupy(Field p) {
        Field[] f = new Field[1];
        f[0] = p;
        return f;
    }

    /**
     * True if the building can be build there
     * @param p the location of the entry sign
     * @return tru if ok
     */
    public static boolean checkBuildable(Field p) {
        if (!p.isClear())
            return false;
        try {
            if(getOccupy(p.getNeighbour(Directions.LU))==null)
                return false;
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

    /**
     * Returns the entry sign
     * @return
     */
    public BuildingSign getEntry() {
        return entry;
    }

    /**
     * Sets the building enabled
     */
    public void enable() {
        forEnable = null;
    }

    /**
     * The building dies and removes itself
     */
    public void die() {
        super.die();
        Field[] fields = getOccupy(this.location);
        for (Field field : fields) {
            field.clear();
        }
        if(entry.isAlive())
            entry.die();
    }

    /**
     * To make orks find it
     * @param probe
     */
    public void acceptProbe(Ork.OrkProbe probe) {
        probe.addInfo(false, this);
    }

    /**
     * An ork enters the building(in most cases it kills a building)
     * @param o the ork
     */
    public void OrkEnters(Ork o) {
        die();
    }

}
