package game.model.movables;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.objects.buildings.EnemyStronghold;
import game.model.objects.buildings.Stronghold;
import game.model.world.Field;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * The players warriors. These can destroy the enemy fortresses
 */
public class Warrior extends FreeWalker {
    /**
     * road to the enemy fortress
     */
    private Path<EnemyStronghold> plan;
    /**
     * path home
     */
    private Path<Stronghold> wayHome;
    /**
     * Home of the object
     */
    private Stronghold home;
    /**
     * The enemy it wants to attack
     */
    private EnemyStronghold target;

    /**
     * creates a warrior and chooses it's target.
     * @param home
     */
    public Warrior(Stronghold home) {
        super(home.getEntry().getLocation());
        this.home = home;
        target = FindEnemy(22);
    }

    /**
     * Searches for a path to it's target, goes to it and attacks. If cant, it returns to it's home
     * @return
     */
    public boolean tick() {
        if (!this.isAlive())
            return false;
        if (target!=null&&target.isAlive()) {
            if (plan == null) {
                if (target.isAlive())
                    plan = Path.FindPath(getLocation(), target, Integer.MAX_VALUE);
            }
            if (plan != null) {
                Field next = plan.Next();
                if (next == null) {
                    if (plan.getObj().isAlive())
                        plan.getObj().AttackedBy(this);
                    plan = null;
                    return isAlive();
                }
                if (!next.isSteppable()) {
                    plan = null;
                    return true;
                }
                this.setLocation(next);
                return true;
            }
            return true;
        }
        if (!home.isAlive()) {
            die();
            return isAlive();
        }
        if (wayHome == null)
            wayHome = Path.FindPath(getLocation(), home, Integer.MAX_VALUE);
        if (wayHome != null) {
            Field next = wayHome.Next();
            if (next == null) {
                home.acceptReturning(this);
                return isAlive();
            }
            if (!next.isSteppable()) {
                plan = null;
                return true;
            }
            this.setLocation(next);
            return true;
        }
        return true;
    }

    /**
     * Finds an enemy stronghold
     * @param max max distance
     * @returnthe stronghold it found or null if none
     */
    public EnemyStronghold FindEnemy(int max) {
        Field start = this.getLocation();
        Map<Field, AttackProbe> finished = new HashMap<>();
        PriorityQueue<AttackProbe> notUsed = new PriorityQueue<>(new Comparator<AttackProbe>() {
            public int compare(AttackProbe o1, AttackProbe o2) {
                return new Integer(o1.distance).compareTo(o2.distance);
            }
        });

        for (Field field : start.getNeighbours()) {
            notUsed.add(new AttackProbe(start, field, 1));
        }
        AttackProbe probe;
        for (probe = notUsed.poll(); probe != null && probe.distance <= max && probe.dest == null; probe = notUsed.poll()) {
            if (probe.isSteppable && !finished.containsKey(probe.to)) {
                for (Field field : probe.to.getNeighbours()) {
                    notUsed.add(new AttackProbe(probe.to, field, probe.distance + 1));
                }
                finished.put(probe.to, probe);
            }
        }
        if (probe.dest != null)
            return probe.dest;
        return null;
    }

    @Override
    public boolean canEverTick() {
        return super.canEverTick();
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    /**
     * probe for collecting field info
     */
    public class AttackProbe extends FreeProbe<EnemyStronghold> {
        public AttackProbe(Field from, Field to, int distance) {
            super(from, to, distance);
            to.acceptProbe(this);
        }
    }
}
