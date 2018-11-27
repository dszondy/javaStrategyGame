package game.model.world;

import game.gui.InGameDrawer;
import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.Directions;
import game.model.GameObject;
import game.model.Visible;
import game.model.movables.*;
import game.model.objects.WorldObject;
import game.model.resources.Resource;
import game.model.resources.ResourceDeposit;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *A field on the map
 */
public class Field extends GameObject implements Visible, Visitable {
    /**
     * Thing that owns it (eg. tree in the field of building)
     */
    private WorldObject obj;
    /**
     * If stores a resources, it holds it in a resource deposit
     */
    private ResourceDeposit d;
    /**
     * It's location (in the array of the world)
     */
    private Point location;

    /**
     * Creates an empty field at location with random resource
     * @param location
     */
    public Field(Point location) {
        this(location, new Random().nextInt(10)<=1 ? new ResourceDeposit(Resource.IRON, new Random().nextInt(45)+5): null);
    }

    /**
     * Creates an empty field at location with random resource
     * @param location
     * @param d the deposit to set it's data
     */
    public Field(Point location, ResourceDeposit d) {
        this.obj = null;
        this.d = d;
        this.location = location;
    }

    /**
     * Get the location point
     * @return
     */
    public Point getLocation() {
        return (Point) location.clone();
    }

    /**
     * @return true if nothing occupies it, false if something is on it
     */
    public boolean isClear() {
        return obj == null;
    }

    /**
     * Removes the object from 'obj', so it's clear now
     */
    public void clear() {
        obj = null;
    }

    /**
     * @return true if the player can step on it
     */
    public boolean isSteppable() {
        return isClear() || obj.canWalkTrough();
    }

    /**
     * @return Returns all neighbour fields in an array
     */
    public Field[] getNeighbours() {
        ArrayList<Field> f = new ArrayList<>();
        for (Directions d : Directions.values()) {
            if (getNeighbour(d) != null)
                f.add(getNeighbour(d));
        }
        Field[] fa = new Field[f.size()];
        for (int i = 0; i < fa.length; i++) {
            fa[i] = f.get(i);
        }
        return fa;
    }

    /**
     * Returns the neighbour in the chosen dir
     * @param d the direction of the neighbour
     * @return null if out of bounds
     */
    public Field getNeighbour(Directions d) {
        return world.getLocNextTo(location, d);
    }

    /**
     * Sets the object that occupies it
     * @param obj
     */
    public void add(WorldObject obj) {
        this.obj = obj;
    }

    /**
     * @return the type of the resource it holds (NONE if no resource)
     */
    public Resource hasResource() {
        if (d != null)
            return d.GetType();
        return Resource.NONE;
    }

    /**
     * @return the ammount it holds
     */
    public int resourceAmmount(){
        if(d==null)
            return 0;
        return d.checkAmmount();
    }

    /**
     * To mine some resource
     * @return 1 if successful, 0 if not
     */
    public int getResource() {
        if (hasResource() != Resource.NONE) {
            return d.getResource(1);
        } else return 0;
    }


    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    /**
     * Probes for finding paths and objects
     * @param probe
     */
    @Override
    public void acceptProbe(StoneMiner.StoneProbe probe) {
        probe.addInfo(isSteppable());
        if (obj != null)
            obj.acceptProbe(probe);
    }
    /**
     * Probes for finding paths and objects
     * @param probe
     */
    @Override
    public void acceptProbe(Lumberman.LumberProbe probe) {
        probe.addInfo(isSteppable());
        if (obj != null)
            obj.acceptProbe(probe);
    }
    /**
     * Probes for finding paths and objects
     * @param probe
     */
    @Override
    public void acceptProbe(Warrior.AttackProbe probe) {
        probe.addInfo(isSteppable());
        if (obj != null)
            obj.acceptProbe(probe);
    }
    /**
     * Probes for finding paths and objects
     * @param probe
     */
    public void acceptProbe(SimpleProbe probe) {
        probe.addInfo(isSteppable());
        if (obj != null)
            obj.acceptProbe(probe);
    }
    /**
     * Probes for finding paths and objects
     * @param probe
     */
    public void acceptProbe(Transporter.RoadProbe probe) {
        probe.addInfo(isClear());
        if (obj != null)
            obj.acceptProbe(probe);
    }
    /**
     * Probes for finding paths and objects
     * @param probe
     */
    public void acceptProbe(Ork.OrkProbe probe) {
        probe.addInfo(isSteppable());
        if (obj != null)
            obj.acceptProbe(probe);
    }


    @Override
    public double getY() {
        return InGameDrawer.convertCoords(location).getY();
    }
    public Point getPoint(){
        return getLocation();
    }
}
