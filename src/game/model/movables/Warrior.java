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


public class Warrior extends FreeWalker {
    private Path<EnemyStronghold> plan;
    private Path<Stronghold> wayHome;
    private Stronghold home;
    private EnemyStronghold target;
    public Warrior(Stronghold home) {
        super(home.getEntry().getLocation());
        home = home;
        target = FindEnemy(22);
    }

    public boolean tick() {
        if (!this.isAlive())
            return false;
        if (target!=null&&target.isAlive()) {
            if (plan == null) {
                if (target.isAlive())
                    plan = Path.FindPath(getPlace(), target, Integer.MAX_VALUE);
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
                this.setPlace(next);
                return true;
            }
            return true;
        }
        if (!home.isAlive()) {
            die();
            return isAlive();
        }
        if (wayHome == null)
            wayHome = Path.FindPath(getPlace(), home, Integer.MAX_VALUE);
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
            this.setPlace(next);
            return true;
        }
        return true;
    }

    public EnemyStronghold FindEnemy(int max) {
        Field start = this.getPlace();
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

    public class AttackProbe extends FreeProbe<EnemyStronghold> {
        public AttackProbe(Field from, Field to, int distance) {
            super(from, to, distance);
            to.acceptProbe(this);
        }
    }
}
