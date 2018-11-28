package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.Ork;
import game.model.movables.Warrior;
import game.model.world.Field;

import java.util.Random;

/**
 * the main enemy(except trees)
 * It sends Orks to destroy the player, and if all ot these are destroyed, the player wins.
 * it's enabled by default
 */
public class EnemyStronghold extends BigBuilding {
    /**
     * The count of orks inside
     */
    int orksInside = 6;

    /**
     * Creates an enemy stronghold
     * it has no entry sign
     * @param p the location right down from theis building
     */
    public EnemyStronghold(Field p) {
        super(p, false);
        world.enemyCreated();
    }

    /**
     * Randomly sends orks, and if has only a few, it might increases the counr of orksInside
     * @return
     */
    @Override
    public boolean tick() {
        if (!isAlive)
            return false;
        if (new Random().nextInt(30) < orksInside) {
            orksInside--;
            new Ork(this);
        }
        if(orksInside<5 && new Random().nextGaussian()>1.8){
            orksInside++;
        }
        return true;
    }

    /**
     * If anouther ork enters it's added to the orks inside
     * @param o the ork
     */
    public void OrkEnters(Ork o) {
        o.die();
        this.orksInside++;
    }

    /**
     * If a warrior attacks it, the warrior dies, and if this has no orks, it dies too.
     * @param warrior
     *
     */
    public void AttackedBy(Warrior warrior) {
        orksInside--;
        warrior.die();
        if (orksInside < 0)
            die();
    }

    /**
     * Ork's wont find it by that
     * @param probe
     */
    public void acceptProbe(Ork.OrkProbe probe) {
        probe.addInfo(false);
    }

    /**
     * Let warriors find it
     * @param probe
     */
    public void acceptProbe(Warrior.AttackProbe probe) {
        probe.addInfo(false, this);
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    /**
     * Dies and removes it self from the world(the world counts it, so if it's the last, the player won)
     */
    @Override
    public void die() {
        super.die();
        world.enemyDied();
    }
}


