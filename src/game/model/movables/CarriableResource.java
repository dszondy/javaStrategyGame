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

/**
 * Wrapper class for resources. Stores 1 resource, from one type. It is for transportation.
 * When placed on the road system, it can plan it's way to it's destination.
 */
public class CarriableResource extends WorldObject {
    /**
     * the resource it stores
     */
    private final Resource r;

    /**
     * @return the destination of this package or null if no destination set
     */
    public Building getDestination() {
        return destination;
    }

    /**
     * The destination
     */
    private Building destination;
    /**
     * The location of the object that holds this
     */
    private WorldObject at;

    /**
     * @return the next sign on it's way to it's destination
     */
    public Sign getNext() {
        return next;
    }

    /**
     *  next sign on it's way to it's destination
     */
    private Sign next;

    /**
     * Creates a new CarriableResource without destination
     * @param r resource type
     * @param at the object it holds
     */
    public CarriableResource(Resource r, WorldObject at) {
        super(at.getLocation());
        this.r = r;
        this.at = at;
    }

    /**
     * Creates a resource at a sign, for transportation on roads, and plan's its way to the destination
     * @param r resource type
     * @param at the sign it's placed on
     * @param dst destination
     */
    public CarriableResource(Resource r, Sign at, Building dst) {
        this(r, at);
        destination = dst;
        next = findNext(at);
    }

    /**
     * An object picks up the package
     * @param transporter the objet which picked it up
     */
    public void pickedUp(Transporter transporter){
        at = transporter;
    }

    /**
     * The object has been placed on a sign
     * @param sign the sign it's placed on
     */
    public void DroppedDown(Sign sign){
        at = sign;
        next = findNext(sign);
    }

    /**
     * Finds the next sign on the road system, towards it's destination
     * @param sign Actual sign
     * @return the next in it's way
     */
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

    /**
     * @return the type of the resource it holds
     */
    public Resource getR() {
        return r;
    }

    /**
     * The resource dies
     */
    public void die(){
        super.die();
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }

    /**
     * The package follows it's carrier's (at) location. If it can't find a way, it dies and asks a new resource.
     * @return
     */
    @Override
    public boolean tick() {
        if(next == null && destination!=null){
            MainBuilding.getAddress().askResource(getR(),destination);
            destination = MainBuilding.getAddress();
            }
        location = at.getLocation();
        return true;
    }
}

