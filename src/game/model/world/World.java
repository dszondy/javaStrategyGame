package game.model.world;

import game.model.Directions;
import game.model.Tickable;
import game.model.Visible;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Main control and storage object of the game.
 * Stores the fields, visible and steppable objects.
 * Also tick's them every turn.
 */
public class World implements Serializable {
    /**
     * The fields of the map
     */
    private Field[][] fields;
    /**
     * Set of the tickable objects (set, so none will be stepped twice in one turn)
     */
    private Set<Tickable> tickables = new HashSet<Tickable>();
    /**
     * Set of the visible objects (so no will be drawn twice)
     */
    private Set<Visible> visibles = new HashSet<>();

    /**
     * Creates a new empty world at a given size.
     * @param size A dimension of the fields(so the real field count is size^2)
     */
    World(int size) {
        fields = new Field[size][size];
        for (int x = 0; x < fields.length; x++)
            for (int y = 0; y < fields.length; y++)
                fields[x][y] = new Field(new Point(x, y));
    }

    /**
     * Creates a new world with a simple 128*128 size.
     */
    public World() {
        this(128);
    }

    /**
     * Ticks every steppable object. It catches all kind of Exception inside it's loop,
     * so when one class fails, that does not affect other objects.
     * If an object doesn't want to be ticked anymore (returned false) it removes it from the "tickables" set.
     */
    public void tickAll() {
        long now = System.currentTimeMillis();
        Set<Tickable> cpy = new HashSet<Tickable>(tickables);
        for (Tickable t : cpy) {
            try {
                if (!t.tick())
                    tickables.remove(t);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * Adds an object to the "tickables" set, so next turn it will be ticked too.
     * @param o
     */
    public void addToTickList(Tickable o) {
        tickables.add(o);
    }

    /**
     * returns a field at the given location.
     * @param loc point of the object's position in the 2d array
     * @return the field, or null if out of bounds
     */
    public Field getFieldAtLocation(Point loc) {
        if (loc.x < 0 || loc.x >= fields[0].length || loc.y < 0 || loc.y >= fields.length)
            return null;
        return fields[loc.x][loc.y];
    }

    /**
     * It can find a fields neighbour in a given direction. It should be used in the game, because the hexagonal
     * map structure is pretty complicated.
     * @param loc the field whose neighbour we are looking for
     * @param d the direction of the neighbour
     * @return returns the object we asked for (or null if out of bounds)
     */
    public Field getLocNextTo(Point loc, Directions d) {
        if(loc == null)
            return null;
        switch (d) {
            case L:
                return getFieldAtLocation(new Point(loc.x - 1, loc.y));
            case LU:
                return getFieldAtLocation(new Point(loc.x + ((loc.y % 2 == 1) ? 0 : -1), loc.y - 1));
            case RU:
                return getFieldAtLocation(new Point(loc.x + ((loc.y % 2 == 1) ? 1 : 0), loc.y - 1));
            case R:
                return getFieldAtLocation(new Point(loc.x + 1, loc.y));
            case RD:
                return getFieldAtLocation(new Point(loc.x + ((loc.y % 2 == 1) ? 1 : 0), loc.y + 1));
            case LD:
                return getFieldAtLocation(new Point(loc.x + ((loc.y % 2 == 1) ? 0 : -1), loc.y + 1));
            default:
                return null;
        }
    }

    /**
     * Registers a Visible object for drawing.
     * @param o the object we want to add.
     */
    public void addObject(Visible o) {
        visibles.add(o);
    }

    /**
     * Removes a visible object, so it won't be drawn anymore.
     * @param o The object we want to remove.
     */
    public void removeObject(Visible o) {
        visibles.remove(o);
    }

    /**
     * Returns a fill list of every objects we need to draw, in drawing order(first in the lis should be drawn first)
     * @return the list of all visible objects
     */
    public List<Visible> GetVisibleList() {
        List<Visible> l = new ArrayList<Visible>();
        for (Field[] x : fields) {
            for (Field y : x) {
                l.add(y);
            }
        }
        PriorityQueue<Visible> v = new PriorityQueue<>(new Comparator<Visible>() {
            @Override
            public int compare(Visible o1, Visible o2) {
                return new Double(o2.getY()).compareTo(o1.getY());
            }
        });
        v.addAll(visibles);
        l.addAll(v);
        return l;
    }


}
