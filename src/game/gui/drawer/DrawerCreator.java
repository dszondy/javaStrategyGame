package game.gui.drawer;

import game.gui.drawer.ObjectDrawers.*;
import game.model.movables.*;
import game.model.objects.*;
import game.model.objects.buildings.*;
import game.model.world.Field;

/**
 * Creates a drawer (the objects of the "model" package only call this form the package)
 */
public class DrawerCreator {
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Field obj) {
        return obj.isClear() ?new EmptyFieldDrawer(obj.getLocation(), obj) : new FieldDrawer(obj.getLocation(), obj);
    }

    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Sign obj) {
        return new SignDrawer(obj.getLocation().getLocation(), obj.getDirections(), obj);
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(BuildingSign obj) {
        return new BuildingSignDrawer(obj.getLocation().getLocation(), obj.getDirections(), obj, obj.getB().isEnabled());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Tree obj) {
        return new TreeDrawer(obj.getLocation().getLocation());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */

    public static Drawer getDrawer(Rock obj) {
        return new RockDrawer(obj.getLocation().getLocation());
    }


    public static Drawer getDrawer(MainBuilding obj) {
        return new MainBuildingDrawer(obj.getLocation().getLocation(), obj);
    }

    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(LumbererHouse obj) {
        return new WarehouseDrawer(obj.getLocation().getLocation());
    }

    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(StoneMine obj) {
        return new WarehouseDrawer(obj.getLocation().getLocation());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Road obj) {
        return new RoadDrawer(obj.getLocation().getLocation(), obj.getDirections());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(WorldObject obj) {
        return new NullDrawer(obj.getLocation().getLocation());
    }

    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Lumberjack obj) {
        return new LumberjackDrawer(obj.getLocation().getLocation());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(StoneMiner m) {
        return new MinerDrawer(m.getLocation().getLocation());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Transporter m) {
        return new HorseDrawer(m.getLocation().getLocation());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Ork obj) {
        return new OrkDrawer(obj.getLocation().getLocation());
    }

    public static Drawer getDrawer(EnemyStronghold obj) {
        return new OrkStrongholdDrawer(obj.getLocation().getLocation());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Warrior obj) {
        return new WarriorDrawer(obj.getLocation().getLocation());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Stronghold obj) {
        return new StrongholdDrawer(obj.getLocation().getLocation());
    }
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(CarriableResource obj){return new TransportedResourceDrawer(obj.getLocation().getLocation(), obj.getR());}
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Mine obj){return new MineDrawer(obj.getLocation().getLocation());}
    /**
     * Drawer for the obj
     * @param obj the object which we want to draw
     * @return the drawer
     */
    public static Drawer getDrawer(Blacksmith obj){return new BlacksmithDrawer(obj.getLocation().getLocation());}

}
