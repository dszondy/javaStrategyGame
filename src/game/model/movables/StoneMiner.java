package game.model.movables;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.objects.BuildingSign;
import game.model.objects.Rock;
import game.model.objects.WorldObject;
import game.model.objects.buildings.StoneMine;
import game.model.world.Field;

import java.util.*;

import static game.model.resources.Resource.STONE;

public class StoneMiner extends ResourceCollector<StoneMine, Rock> {
    private boolean hasRock = false;
    private Path<Rock> plan = null;
    private Path<BuildingSign> hPath = null;

    public StoneMiner(StoneMine home) {
        super(home);
        setAction(new Action());
    }
    private StoneMiner self = this;

    class Action extends ResourceCollector.Action {
        @Override
        public CarriableResource get(WorldObject obj) {
            if(((Rock)obj).mineStone())
                return new CarriableResource(STONE, self);
            return null;
        }
    }

    @Override
    public Path<Rock> FindObject(int max) {
        return FindRock(max);
    }

    public Path<Rock> FindRock(int max) {
        Field start = this.getPlace();
        Map<Field, StoneProbe> finished = new HashMap<>();
        PriorityQueue<StoneProbe> notUsed = new PriorityQueue<>(new Comparator<StoneProbe>() {
            public int compare(StoneProbe o1, StoneProbe o2) {
                return new Integer(o1.distance).compareTo(o2.distance);
            }
        });

        for (Field field : start.getNeighbours()) {
            notUsed.add(new StoneProbe(start, field, 1));
        }
        StoneProbe probe;
        for (probe = notUsed.poll(); probe != null && probe.distance <= max && probe.dest == null; probe = notUsed.poll()) {
            if (probe.isSteppable && !finished.containsKey(probe.to)) {
                for (Field field : probe.to.getNeighbours()) {
                    notUsed.add(new StoneProbe(probe.to, field, probe.distance + 1));
                }
                finished.put(probe.to, probe);
            }
        }
        if (probe!= null && probe.dest != null) {
            Rock dest = probe.dest;
            LinkedList<Field> ls = new LinkedList();
            probe = finished.get(probe.from);
            while (probe.from != start) {
                ls.addFirst(probe.to);
                probe = finished.get(probe.from);
            }
            ls.addFirst(probe.to);
            return new Path<Rock>(ls, dest);
        }
        return null;
    }

    @Override
    public boolean canEverTick() {
        return true;
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }


    public class StoneProbe extends FreeProbe<Rock> {
        public StoneProbe(Field from, Field to, int distance) {
            super(from, to, distance);
            to.acceptProbe(this);
        }
    }
}
