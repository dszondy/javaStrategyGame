package game.model.objects;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.StoneMiner;
import game.model.world.Field;

/**
 * A rock object from the game. It has STONE (1 ore more)
 */
public class Rock extends WorldObject {
    /**
     * the ammount of STONE left
     */
    private int amount;

    /**
     * Creates a Rock with 100 STONE resources.
     * @param p the field where it will be created
     */
    public Rock(Field p) {
        this(p, 100);
    }

    /**
     * Creates a Rock with the amount we want.
     * @param p The location
     * @param amm the ammount of STONE inside
     */
    Rock(Field p, int amm) {
        super(p);
        amount = amm;
        location.add(this);
    }

    /**
     * Fills the prove with its pointer, so the stone miner can find it.
     * @param probe the probe that visits it
     */
    public void acceptProbe(StoneMiner.StoneProbe probe) {
        probe.addInfo(canWalkTrough(), this);
    }


    @Override
    public boolean isAlive() {
        return amount > 0 && isAlive;
    }

    /**
     * Returns true if it managed to mine stone (had STONE)
     * @return true if successfull
     */
    public boolean mineStone() {
        boolean success = false;
        if (amount > 0) {
            amount--;
            success = true;
        }
        if (!isAlive()) {
            die();
        }
        return success;
    }

    public void die() {
        location.clear();
        super.die();
    }

    /**
     * Rocks don't do anything by their self.
     * @return returns false.
     */
    @Override
    public boolean tick() {
        return false;
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }


}
