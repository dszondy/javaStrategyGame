package game.model.movables;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.objects.BuildingSign;
import game.model.objects.WorldObject;
import game.model.objects.buildings.CollectorHouse;
import game.model.world.Field;

import java.io.Serializable;

public abstract class ResourceCollector<H extends CollectorHouse, O extends WorldObject> extends FreeWalker {
    protected H home;
    protected Path<O> plan = null;
    protected Path<BuildingSign> hPath = null;

    protected void setAction(Action u) {
        this.util = u;
    }

    protected Action util;
    protected CarriableResource carries;
        static abstract class Action implements Serializable {
            protected ResourceCollector worker;
            abstract public CarriableResource get(WorldObject obj);
        }
        public ResourceCollector(H home) {
            super(home.getEntry().getLocation());
            this.home = home;
        }

        public boolean hasResource() {
            return carries!=null;
        }
        public CarriableResource pickResource(){
            CarriableResource r = carries;
            carries = null;
            return r;
        }

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
                    hPath = Path.FindPath(getPlace(), home.getEntry(), Integer.MAX_VALUE);
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
                this.setPlace(next);
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
                this.setPlace(next);
                return true;
            }
            return true;
        }

        public Path<O> FindObject(){
            return FindObject(12);
        }
        public abstract Path<O> FindObject(int max);

        @Override
        public boolean canEverTick() {
            return true;
        }

        @Override
        public Drawer getDrawer() {
            return DrawerCreator.getDrawer(this);
        }
    }
