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

public class Lumberman extends ResourceCollector<LumbererHouse, Tree> {
    private LumbererHouse home;
    private boolean hasWood = false;
    private Path<Tree> plan = null;
    private Path<BuildingSign> hPath = null;

    public Lumberman(LumbererHouse home){      
        super(home);
        setAction(new Action());
        }
    private Lumberman self = this;

class Action extends ResourceCollector.Action {
    @Override
    public CarriableResource get(WorldObject obj) {
        if(((Tree)obj).cut())
            return new CarriableResource(Resource.WOOD, self);
        return null;
    }
}

    @Override
    public Path<Tree> FindObject(int max) {
        return FindTree(max);
    }

    public Path<Tree> FindTree(int max) {
        Field start = this.getPlace();
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

    @Override
    public boolean canEverTick() {
        return super.canEverTick();
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    public class LumberProbe extends FreeProbe<Tree> {
        public LumberProbe(Field from, Field to, int distance) {
            super(from, to, distance);
            to.acceptProbe(this);
        }
    }
}

