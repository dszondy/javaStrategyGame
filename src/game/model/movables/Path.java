package game.model.movables;

import game.model.world.Field;

import java.io.Serializable;
import java.util.*;

public class Path<T> implements Serializable {
    private LinkedList<Field> path;
    private T destObj;

    public Path(LinkedList<Field> path, T destObj) {
        this.path = path;
        this.destObj = destObj;
    }

    public static <T> Path<T> FindPath(Field f, T dest, int max) {
        Map<Field, SimpleProbe> finished = new HashMap<>();
        PriorityQueue<SimpleProbe> notUsed = new PriorityQueue<>(new Comparator<SimpleProbe>() {
            public int compare(SimpleProbe o1, SimpleProbe o2) {
                return new Integer(o1.distance).compareTo(o2.distance);
            }
        });
        SimpleProbe tp = new SimpleProbe(null, f, 0);
        if (tp.dest == dest)
            return new Path<>(new LinkedList(), dest);


        for (Field field : f.getNeighbours()) {
            notUsed.add(new SimpleProbe(f, field, 1));
        }
        SimpleProbe probe;
        for (probe = notUsed.poll(); probe != null && probe.distance <= max && probe.dest != dest; probe = notUsed.poll()) {
            if (probe.isSteppable && !finished.containsKey(probe.to)) {
                for (Field field : probe.to.getNeighbours()) {
                    notUsed.add(new SimpleProbe(probe.to, field, probe.distance + 1));
                }
                finished.put(probe.to, probe);
            }
        }
        if (probe!=null && probe.dest == dest) {
            LinkedList<Field> ls = new LinkedList();
            while (probe.from != f) {
                ls.addFirst(probe.to);
                probe = finished.get(probe.from);
            }
            return new Path<>(ls, dest);
        }
        return null;
    }

    public Field Next() {
        try {
            Field next = path.getFirst();
            path.remove(0);
            return next;
        } catch (Exception e) {
        }
        return null;
    }

    public T getObj() {
        return destObj;
    }

}
