package game.model.movables;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.objects.BuildingSign;
import game.model.objects.Tree;
import game.model.objects.WorldObject;
import game.model.objects.buildings.LumbererHouse;
import game.model.resources.Resource;
import game.model.world.Field;

import java.util.*;

/**
 *Cuts wood. Goes from "Lumberer house" and cuts a tree than returns with 1 wood.
 */
public class Lumberjack extends ResourceCollector<LumbererHouse, Tree> {
    /**
     * Creates a new lumberman
     * @param home the building it comes from
     */
    public Lumberjack(LumbererHouse home){
        super(home);
        setAction(new Action());
        }

    /**Renameing it to make visible inside subclass
     *
     */
    private Lumberjack self = this;

    /**
     * "Functor" class for ResourceCollector's generic methods (superclass)
     */
    class Action extends ResourceCollector.Action {
    @Override
    public CarriableResource get(WorldObject obj) {
        if(((Tree)obj).cut())
            return new CarriableResource(Resource.WOOD, self);
        return null;
    }
}

    /**
     * Overvrites the parents method of pathfinding
     * @param max the max distance it checks
     * @return a path to a tree or null if none found
     */
    @Override
    public Path<Tree> FindObject(int max) {
        return FindTree(max);
    }

    /**
     * Searches for a tree
     * @param max the max distance it checks
     * @return a path to a tree or null if none found
     */
    public Path<Tree> FindTree(int max) {
        Field start = this.getLocation();
        Map<Field, LumberProbe> finished = new HashMap<>();
        PriorityQueue<LumberProbe> notUsed = new PriorityQueue<>(new Comparator<LumberProbe>() {
            public int compare(LumberProbe o1, LumberProbe o2) {
                return new Integer(o1.distance).compareTo(o2.distance);
            }
        });

        for (Field field : start.getNeighbours()) {
            notUsed.add(new LumberProbe(start, field, 1));
        }
        LumberProbe probe;
        for (probe = notUsed.poll(); probe != null && probe.distance <= max && probe.dest == null; probe = notUsed.poll()) {
            if (probe.isSteppable && !finished.containsKey(probe.to)) {
                for (Field field : probe.to.getNeighbours()) {
                    notUsed.add(new LumberProbe(probe.to, field, probe.distance + 1));
                }
                finished.put(probe.to, probe);
            }
        }
        if (probe!=null && probe.dest != null) {
            Tree dest = probe.dest;
            LinkedList<Field> ls = new LinkedList();
            probe = finished.get(probe.from);
            if(probe==null) {
                return new Path<Tree>(ls, dest);
            }
            while (probe.from != start) {
                ls.addFirst(probe.to);
                probe = finished.get(probe.from);
            }
            return new Path<Tree>(ls, dest);
        }
        return null;
    }

    /**
     * It ticks
     * @return true while alive
     */
    @Override
    public boolean canEverTick() {
        return super.canEverTick();
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    /**
     * Probe object for collection info of fields
     */
    public class LumberProbe extends FreeProbe<Tree> {
        public LumberProbe(Field from, Field to, int distance) {
            super(from, to, distance);
            to.acceptProbe(this);
        }
    }
}

