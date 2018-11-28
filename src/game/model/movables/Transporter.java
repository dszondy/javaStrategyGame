package game.model.movables;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.Directions;
import game.model.objects.Road;
import game.model.objects.Sign;
import game.model.world.Field;

import java.util.*;

/**
 * Little donkey(or horse or mule or whatever) for transportation at roads
 * (it's really clever because it builds it's roads.
 */
public class Transporter extends FreeWalker {
    /**
     * The index of the field it is on at road
     */
    private Integer position = 0;
    /**
     * the direction it goes
     */
    private Integer direction = -1;
    /**
     * The road it is responsible for
     */
    private LinkedList<Road> road = null;
    /**
     * The end signs of the roads
     */
    private Sign[] ends = new Sign[2];
    /**
     * The resourceit carries
     */
    private CarriableResource carry = null;

    /**
     * Creates a transporter and builds it's road
     * @param start the start of the road
     * @param end the end of the road
     */
    public Transporter(Sign start, Sign end) {
        super(start.getLocation());
        ends[0] = start;
        ends[1] = end;
        try {
            FindRoad(start.getLocation(), end, 15);
        }catch (Exception e){
            die();
        }
    }

    /**
     * If it des it unregisters the roads at the signs and clears it
     */
    public void die(){
        super.die();
        if(road!=null) {
            for (Road r : road) {
                r.die();
            }
            ends[0].remove(ends[1]);
            ends[1].remove(ends[0]);
        }
    }

    /**
     * it live so it ticks
     * @return true
     */
    public boolean canEverTick() {
        return true;
    }

    /**
     * Finds a path for the road and builds it
     * @param f  the starting field
     * @param dest the destination
     * @param max the max length of the road
     */
    public void FindRoad(Field f, Sign dest, int max) {
        Map<Field, RoadProbe> finished = new HashMap<>();
        PriorityQueue<RoadProbe> notUsed = new PriorityQueue<>(new Comparator<RoadProbe>() {
            public int compare(RoadProbe o1, RoadProbe o2) {
                return new Integer(o1.distance).compareTo(o2.distance);
            }
        });
        for (Directions dir : Directions.values()) {
            if (f.getNeighbour(dir) != null)
                notUsed.add(new RoadProbe(f, f.getNeighbour(dir), 1, dir));
        }
        RoadProbe probe;
        for (probe = notUsed.poll(); probe != null && probe.distance <= max && probe.dest != dest; probe = notUsed.poll()) {
            if (probe.isSteppable && !finished.containsKey(probe.to)) {
                for (Directions dir : Directions.values()) {
                    if (probe.to.getNeighbour(dir) != null)
                        notUsed.add(new RoadProbe(probe.to, probe.to.getNeighbour(dir), probe.distance + 1, dir));
                }
                finished.put(probe.to, probe);
            }
        }
        if (probe.dest == dest) {
            ends[1].add(ends[0], probe.getEntryDirection().flip(), this);
            LinkedList<Road> ls = new LinkedList();
            while (probe.from != f) {
                Road road = new Road(probe.from, probe.getEntryDirection(), finished.get(probe.from).getEntryDirection().flip());
                ls.addFirst(road);
                probe = finished.get(probe.from);
            }
            ends[0].add(ends[1], probe.getEntryDirection(), this);
            road = ls;
        }
    }

    /**
     * If the road had been build it just goes from one end to the other, and carries a package if it can.
     *@return true while alive
     */
    @Override
    public boolean tick() {
        if(!isAlive())
            return false;
        if(road == null || road.size()<1) {
            die();
            return false;
        }
      if(direction == -1 && position == 0){
          List<CarriableResource> ls = ends[0].getResources();
          if(this.carry!=null)
              ends[0].place(carry);
          carry = null;
          if(ls.size()>0) {
              CarriableResource r = ls.get(0);
              for (Integer i = 1; r.getNext()!=ends[1] && i<ls.size(); r=ls.get(i), i++);
              if(r.getNext()==ends[1]){
                  ends[0].pick(r);
                  this.carry = r;
                  r.pickedUp(this);
              }
          }
          direction = 1;
      }
       else if(direction == 1 && position == road.size()-1){
            List<CarriableResource> ls = ends[1].getResources();
            if(this.carry!=null)
                ends[1].place(carry);
            carry = null;
            if(ls.size()>0) {
                CarriableResource r = ls.get(0);
                for (Integer i = 1; r.getNext()!=ends[0] && i<ls.size(); r=ls.get(i), i++);
                if(r.getNext()==ends[0]){
                    ends[1].getResources().remove(r);
                    this.carry = r;
                    r.pickedUp(this);
                }
            }
          direction = -1;
      }
      else{
          position+=direction;
      }
      location = road.get(position).location;
      return true;
    }

    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    /**
     * probe for collectiong field info.
     * this one needs clear fields insted of steppable ones.
     */
    public class RoadProbe extends FreeProbe<Sign> {
        /**
         * Stores the dir it come from
         */
        Directions entryDirection;

        /**
         * Creates a probe
         * @param from last field
         * @param to actual field
         * @param distance the distance from start
         * @param dir the direction it come form
         */
        public RoadProbe(Field from, Field to, int distance, Directions dir) {
            super(from, to, distance);
            entryDirection = dir;
            to.acceptProbe(this);
        }

        /**
         * @return the entry direction
         */
        public Directions getEntryDirection() {
            return entryDirection;
        }
    }

}

