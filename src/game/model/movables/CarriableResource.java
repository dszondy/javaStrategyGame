package game.model.movables;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.objects.Sign;
import game.model.objects.WorldObject;
import game.model.objects.buildings.Building;
import game.model.objects.buildings.MainBuilding;
import game.model.resources.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class CarriableResource extends WorldObject {
    private final Resource r;

    public Building getDestination() {
        return destination;
    }

    private Building destination;
    private WorldObject at;

    public Sign getNext() {
        return next;
    }

    private Sign next;

    public CarriableResource(Resource r, WorldObject at) {
        super(at.getLocation());
        this.r = r;
        this.at = at;
    }
    public CarriableResource(Resource r, Sign at, Building dst) {
        this(r, at);
        destination = dst;
        next = findNext(at);
    }

    public void pickedUp(Transporter transporter){
        at = transporter;
    }
    public void DroppedDown(Sign sign){
        at = sign;
        next = findNext(sign);
    }

    public Sign findNext(Sign sign) {
        try{
        if(sign == destination.getEntry())
            return sign;
        class Node{
            public Sign sign;
            public Integer distance;
            public Sign prevSign;

            public Node(Sign sign, Integer distance, Sign prevSign) {
                this.sign = sign;
                this.distance = distance;
                this.prevSign = prevSign;
            }
        }

        Map<Sign, Node> finished = new HashMap<>();
        PriorityQueue<Node> notUsed = new PriorityQueue<>((o1, o2) -> new Integer(o1.distance).compareTo(o2.distance));
        Node node;
            for(node = new Node(sign, 0, sign); node.sign!=destination.getEntry()&&node.distance>=0; node = notUsed.poll()) {
                if (!finished.containsKey(node.sign)) {
                    for (Sign s : node.sign.getNeighbours()) {
                        notUsed.add(new Node(s, node.distance + 1, node.sign));
                    }
                    finished.put(node.sign, node);
                }
            }
            if (node.sign==destination.getEntry()){
                for (; node.prevSign!=sign; node = finished.get(node.prevSign));
                return node.sign;
            }
        }catch (Exception e){}
        return null;
        }

    public Resource getR() {
        return r;
    }
    public void die(){
        super.die();
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }


    @Override
    public boolean tick() {
        if(next == null && destination!=null){
            die();
            MainBuilding.getAddress().askResource(getR(),destination);
            return false;
        }
        location = at.getLocation();
        return true;
    }
}

