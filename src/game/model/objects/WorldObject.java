package game.model.objects;

import game.gui.InGameDrawer;
import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.GameObject;
import game.model.Tickable;
import game.model.Visible;
import game.model.movables.*;
import game.model.world.Field;

import java.awt.*;

/**
 * Avstract class for object's in the world
 */
public abstract class WorldObject extends GameObject implements Tickable, Visible, Visitable {
    /**
     * Shows if the object can tick
     */
    protected boolean canEverTick = true;
    public boolean canEverTick() {
        return canEverTick;
    }

    /**
     * ticks and returns false if it won't tick anymore
     * @return true if it can tick
     */
    public boolean tick() {
        return canEverTick();
    }

    /**
     * the field it's on
     */
    public Field location;
    /**
     * if this is alive
     */
    protected boolean isAlive = true;

    /**
     * Creates a worldobject
     * @param p the object's location
     */
    protected WorldObject(Field p) {
        location = p;
        world.addObject(this);
        world.addToTickList(this);
    }

    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    /**
     * @return true if the building is alive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Dies and removes itself from the world
     */
    public void die() {
        world.removeObject(this);
        canEverTick = false;
        isAlive = false;
    }

    /**
     * @return true if an object can walk trough this
     */
    public boolean canWalkTrough() {
        return false;
    }


    /**
     * @return the object's location
     */
    public Field getLocation() {
        return location;
    }

    /**
     * probes for pathfinding
     * fills with   "canWalkTrough"
     * @param probe the probe that visits it
     */
    @Override
    public void acceptProbe(StoneMiner.StoneProbe probe) {
        probe.addInfo(canWalkTrough());
    }
    /**
     * probes for pathfinding
     * fills with   "canWalkTrough"
     * @param probe the probe that visits it
     */
    @Override
    public void acceptProbe(Lumberjack.LumberProbe probe) {
        probe.addInfo(canWalkTrough());
    }
    /**
     * probes for pathfinding
     * fills with   "canWalkTrough"
     * @param probe the probe that visits it
     */
    @Override
    public void acceptProbe(Warrior.AttackProbe probe) {
        probe.addInfo(canWalkTrough());
    }
    /**
     * probes for pathfinding
     * fills with   "canWalkTrough" and it's reference
     * @param probe the probe that visits it
     */
    @Override
    public void acceptProbe(SimpleProbe probe) {
        probe.addInfo(canWalkTrough(), this);
    }
    /**
     * probes for pathfinding
     * fills with   "canWalkTrough"
     * @param probe the probe that visits it
     */
    public void acceptProbe(Transporter.RoadProbe probe) {
        probe.addInfo(false);
    }
    /**
     * probes for pathfinding
     * fills with   "canWalkTrough"
     * @param probe the probe that visits it
     */
    public void acceptProbe(Ork.OrkProbe probe) {
        probe.addInfo(false);
    }

    /**
     *
     * @return the Y cooridnate
     */
    @Override
    public double getY() {
        return InGameDrawer.convertCoords(this.getLocation().getLocation()).getY();
    }

    /**
     * @return the location as point
     */
    public Point getPoint(){
        return getLocation().getLocation();
    }

}
