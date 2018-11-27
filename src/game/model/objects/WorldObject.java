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

public abstract class WorldObject extends GameObject implements Tickable, Visible, Visitable {
    protected boolean canEverTick = true;

    public boolean canEverTick() {
        return canEverTick;
    }

    public boolean tick(double deltaTime) {
        return canEverTick();
    }

    public Field location;
    protected boolean isAlive = true;

    protected WorldObject(Field p) {
        location = p;
        world.addObject(this);
        world.addToTickList(this);
    }

    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void die() {
        world.removeObject(this);
        canEverTick = false;
        isAlive = false;
    }

    public boolean canWalkTrough() {
        return false;
    }

    public Field getLocation() {
        return location;
    }

    @Override
    public void acceptProbe(StoneMiner.StoneProbe probe) {
        probe.addInfo(canWalkTrough());
    }

    @Override
    public void acceptProbe(Lumberman.LumberProbe probe) {
        probe.addInfo(canWalkTrough());
    }

    @Override
    public void acceptProbe(Warrior.AttackProbe probe) {
        probe.addInfo(canWalkTrough());
    }

    @Override
    public void acceptProbe(SimpleProbe probe) {
        probe.addInfo(canWalkTrough(), this);
    }

    public void acceptProbe(Transporter.RoadProbe probe) {
        probe.addInfo(false);
    }

    public void acceptProbe(Ork.OrkProbe probe) {
        probe.addInfo(false);
    }

    @Override
    public double getY() {
        return InGameDrawer.convertCoords(this.getLocation().getLocation()).getY();
    }
    public Point getPoint(){
        return getLocation().getLocation();
    }

}
