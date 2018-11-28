package game.model.movables;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.objects.BuildingSign;
import game.model.objects.WorldObject;
import game.model.objects.buildings.CollectorHouse;
import game.model.world.Field;

import java.io.Serializable;

/**
 * Worker, it searches for something that gives resource and collects it then retuns to it's hme with it
 * @param <H> type of it's home
 * @param <O> type of object it searches
 */
public abstract class ResourceCollector<H extends CollectorHouse, O extends WorldObject> extends FreeWalker {
    /**
     * Home of the object
     */
    protected H home;
    /**
     * The path for the next collectable object
     */
    protected Path<O> plan = null;
    /**
     * Path home
     */
    protected Path<BuildingSign> hPath = null;

    /**
     * Sets the action it does with the object it found
     * @param u action class
     */
    protected void setAction(Action u) {
        this.util = u;
    }

    /**
     * Stores the action
     */
    protected Action util;
    /**
     * The resource it carries
     */
    protected CarriableResource carries;

    /**
     * The class that stores it behaviour when reaches it's destination
     */
    static abstract class Action implements Serializable {
            protected ResourceCollector worker;
            abstract public CarriableResource get(WorldObject obj);
        }

    /**
     * Creates a resource collector
     * @param home the home of the object
     */
    public ResourceCollector(H home) {
            super(home.getEntry().getLocation());
            this.home = home;
        }


    /**
     * @return true if carries a resource
     */
    public boolean hasResource() {
            return carries!=null;
        }

    /**
     * clears it's resource
     * @return the resource it stored
     */
    public CarriableResource pickResource(){
            CarriableResource r = carries;
            carries = null;
            return r;
        }

    /**
     * Steps, searches for a resource, goes to it, and when it has a resource goes back to it's home
     * @return
     */
    public boolean tick() {
            if (!home.isAlive()) {
                die();
                return false;
            }
            if (!this.isAlive())
                return false;
            if (plan == null && hPath == null) {
                if (!hasResource())
                    plan = FindObject();
                if (plan == null) {
                    hPath = Path.FindPath(getLocation(), home.getEntry(), Integer.MAX_VALUE);
                }
            }

            if (plan != null) {
                Field next = plan.Next();
                if (next == null) {
                    carries = util.get(plan.getObj());
                    plan = null;
                    return true;
                }
                if (!next.isSteppable()) {
                    plan = null;
                    return true;
                }
                this.setLocation(next);
                return true;
            }
            if (hPath != null) {
                Field next = hPath.Next();
                if (next == null) {
                    home.acceptReturning(this);
                    return false;
                }
                if (!next.isSteppable()) {
                    plan = null;
                    return true;
                }
                this.setLocation(next);
                return true;
            }
            return true;
        }

    /**
     * Searches for a destination object with a default value
     * @return path to an object
     */
    public Path<O> FindObject(){
            return FindObject(12);
        }

    /**
     * Searches for it's destination object
     * @param max max distance
     * @return path to the object
     */
    public abstract Path<O> FindObject(int max);

    /**
     * It lives it ticks
     * @return
     */
        @Override
        public boolean canEverTick() {
            return true;
        }

        @Override
        public Drawer getDrawer() {
            return DrawerCreator.getDrawer(this);
        }
    }
