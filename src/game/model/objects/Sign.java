package game.model.objects;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.Directions;
import game.model.movables.CarriableResource;
import game.model.movables.Transporter;
import game.model.world.Field;

import java.util.*;

/**
 * A road sign, represents a road crossing. The game's road system is build by joined signs.
 */
public class Sign extends WorldObject {
    /**
     * The signs that can be reached through this one, and the dir where the road starts.
     */
    Map<Sign, Directions> neighbours = new HashMap<>();
    /**
     * The transporters responsible for the roads to a direction.
     */
    Map<Directions, Transporter> dirs = new HashMap<>();
    /**
     * A list where the resources which are being transported ar waiting for pick up.
     */
    List<CarriableResource> resources = new ArrayList<>();


    /**
     * Gives the list of resources
     * @return The resource list. Not copy, so could be modified.
     */
    public List<CarriableResource> getResources() {
        return resources;
    }


    /**
     * Creates an unconnected sign at the given location.
     * @param p the field where we want to create the sign.
     */
    public Sign(Field p) {
        super(p);
        location.add(this);
    }

    /**
     * Gives the directions of the roads leaving the sign.
     * Useful for drawing this.
     * @return the directions from dirs.
     */
    public Set<Directions> getDirections() {
        return dirs.keySet();
    }

    /**
     * Could die. It removes every road what it connects to, clears the field and unregisters itself from the world.
     */
    public void die() {
        location.clear();
        super.die();
        for(Transporter transporter : dirs.values()){
            transporter.die();
        }
        for (CarriableResource resource :resources){
            resource.die();
        }
    }

    /**
     * Returns  signs that can be reached through this one.
     * @return A collection of the fields.
     */
    public Collection<Sign> getNeighbours() {
        return neighbours.keySet();
    }

    /**
     * To place a resource at it's waiting slot (resources)
     * @param r the wrapped resource we want to add.
     */
    public void place(CarriableResource r) {
        r.DroppedDown(this);
        resources.add(r);
    }

    /**
     * Adds a new direction to the sign
     * @param s The sign it reaches.
     * @param d The direction it goes from
     * @param t The transporter responsible for the road.
     */
    public void add(Sign s, Directions d, Transporter t) {
        neighbours.put(s, d);
        dirs.put(d, t);
    }

    /**
     * Pick's up a resource form the sign. Removes it from the resources.
     * @param r the resource wrapper we want to remove
     */
    public void pick(CarriableResource r){
        resources.remove(r);
    }

    /**
     * Removes a road.
     * @param s The sign it leads to.
     */
    public void remove(Sign s) {
        if(neighbours.containsKey(s)) {
            dirs.remove(neighbours.get(s));
            neighbours.remove(s);
        }
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    public void acceptProbe(Transporter.RoadProbe probe) {
        probe.addInfo(false, this);
    }

    @Override
    /***
     * Doesn't do anything, so return's false to not be called anymore.
     */
    public boolean tick() {
        return false;
    }

    /**
     * @return True
     */
    public boolean canWalkTrough(){
        return true;
    }
}
