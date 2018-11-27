package game.model.movables;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.Directions;
import game.model.objects.Road;
import game.model.objects.Sign;
import game.model.world.Field;

import java.util.*;

public class Transporter extends FreeWalker {
    private Integer position = 0;
    private Integer direction = -1;
    private LinkedList<Road> road = null;
    private Sign[] ends = new Sign[2];
    private CarriableResource carry = null;

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

    public boolean canEverTick() {
        return true;
    }

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

    public class RoadProbe extends FreeProbe<Sign> {
        Directions entryDirection;

        public RoadProbe(Field from, Field to, int distance, Directions dir) {
            super(from, to, distance);
            entryDirection = dir;
            to.acceptProbe(this);
        }

        public Directions getEntryDirection() {
            return entryDirection;
        }
    }

}

