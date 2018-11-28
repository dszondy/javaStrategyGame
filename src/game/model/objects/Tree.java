package game.model.objects;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.Directions;
import game.model.movables.Lumberjack;
import game.model.world.Field;

import java.util.Random;

/**
 * A tree for the game. Occupies one single field, and give a wood if cut.
 */
public class Tree extends WorldObject {
    /**
     * Constructor with a location
     * @param p the field it will be placed on.
     */
    public Tree(Field p) {
        super(p);
        location.add(this);
    }

    /**
     * For cutting the tree. It will die and give wood.
     * @return true if successful(it was valid so could be cut)
     */
    public boolean cut() {
        if (!isAlive())
            return false;
        else die();
        return true;
    }

    public boolean canEverTick() {
        return true;
    }

    /**
     * Creates randomly trees in it's neighbours.
     * @return If it tries ti create a tree, but fails, it might go passive randomly, so sometimes could return false.
     */
    public boolean tick() {
        if (!isAlive())
            return false;
        if (new Random().nextGaussian() > 2.4) {
            Field f = location.getNeighbour(Directions.values()[new Random().nextInt(6)]);
            if (f!=null && f.isClear())
                f.add(new Tree(f));
            else if(new Random().nextGaussian() > 2)
                return false;
        }
        return canEverTick();
    }

    /**
     * Simple die, eg. from cut.
     */
    public void die() {
        location.clear();
        super.die();
    }
    @Override
    /**
     * Handles a lumberman probe, so the lumberman could find a tree.
     */
    public void acceptProbe(Lumberjack.LumberProbe probe) {
        probe.addInfo(canWalkTrough(), this);
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

}
