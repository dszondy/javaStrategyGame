package game.model.movables;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.Directions;
import game.model.objects.buildings.Building;
import game.model.objects.buildings.EnemyStronghold;
import game.model.world.Field;

import java.util.*;

/**
 * Enemy that destroys the players buildings
 */
public class Ork extends FreeWalker {
    /**
     * The place it came from
     */
    private EnemyStronghold home;
    /**
     * THe path it walks on
     */
    private Path<Building> plan;

    /**
     * Creates an ork
     * @param p it's home
     */
    public Ork(EnemyStronghold p) {
        super(p.getLocation().getNeighbour(Directions.RD));
        home = p;
    }

    /**
     * Finds a path to one of the player's buildings or it's home and moves on it.
     * @return true while alive
     */
    @Override
    public boolean tick() {
        if (!home.isAlive()) {
            die();
            return false;
        }
        if (!this.isAlive())
            return false;
        if (plan == null) {
            if (new Random().nextGaussian() > 0) {
                plan = FindAttackable(16);
                if (plan == null)
                    plan = Path.FindPath(this.getLocation(), home, Integer.MAX_VALUE);
            } else
                plan = Path.FindPath(this.getLocation(), home, Integer.MAX_VALUE);
        }
        if (plan != null) {
            Field next = plan.Next();
            if (next == null) {
                if (plan.getObj().isAlive())
                    plan.getObj().OrkEnters(this);
                plan = null;
                return true;
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
     * Searches for a buildin it can attack
     * @param max maximum distance from the starting positin
     * @return a path to an object or null if not found
     */
    public Path<Building> FindAttackable(int max) {
        try {
            Field start = this.getLocation();
            Map<Field, OrkProbe> finished = new HashMap<>();
            PriorityQueue<OrkProbe> notUsed = new PriorityQueue<>(new Comparator<OrkProbe>() {
                public int compare(OrkProbe o1, OrkProbe o2) {
                    return new Integer(o1.distance).compareTo(o2.distance);
                }
            });

            for (Field field : start.getNeighbours()) {
                notUsed.add(new OrkProbe(start, field, 1));
            }
            OrkProbe probe;
            for (probe = notUsed.poll(); probe != null && probe.distance <= max && probe.dest == null; probe = notUsed.poll()) {
                if (probe.isSteppable && !finished.containsKey(probe.to)) {
                    for (Field field : probe.to.getNeighbours()) {
                        notUsed.add(new OrkProbe(probe.to, field, probe.distance + 1));
                    }
                    finished.put(probe.to, probe);
                }
            }
            if (probe.dest != null) {
                Building dest = probe.dest;
                LinkedList<Field> ls = new LinkedList();
                probe = finished.get(probe.from);
                while (probe.from != start) {
                    ls.addFirst(probe.to);
                    probe = finished.get(probe.from);
                }
                ls.addFirst(probe.to);
                return new Path<Building>(ls, dest);
            }
        }catch(Exception e){}
        return null;
    }

    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    /**
     * Probe object for collection info of fields
     */
    public class OrkProbe extends FreeProbe<Building> {
        public OrkProbe(Field from, Field to, int distance) {
            super(from, to, distance);
            to.acceptProbe(this);
        }
    }
}
