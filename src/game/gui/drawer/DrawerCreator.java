package game.gui.drawer;

import game.gui.drawer.ObjectDrawers.*;
import game.model.movables.*;
import game.model.objects.*;
import game.model.objects.buildings.*;
import game.model.world.Field;

public class DrawerCreator {
    public static Drawer getDrawer(Field field) {
        return field.isClear() ?new EmptyFieldDrawer(field.getLocation(), field) : new FieldDrawer(field.getLocation(), field);
    }

    public static Drawer getDrawer(Sign sign) {
        return new SignDrawer(sign.getLocation().getLocation(), sign.getDirections(), sign);
    }

    public static Drawer getDrawer(BuildingSign sign) {
        return new BuildingSignDrawer(sign.getLocation().getLocation(), sign.getDirections(), sign);
    }

    public static Drawer getDrawer(Tree t) {
        return new TreeDrawer(t.getLocation().getLocation());
    }

    public static Drawer getDrawer(Rock t) {
        return new RockDrawer(t.getLocation().getLocation());
    }

    public static Drawer getDrawer(MainBuilding b) {
        return new MainBuildingDrawer(b.getLocation().getLocation(), b);
    }

    public static Drawer getDrawer(LumbererHouse b) {
        return new WarehouseDrawer(b.getLocation().getLocation());
    }

    public static Drawer getDrawer(StoneMine b) {
        return new WarehouseDrawer(b.getLocation().getLocation());
    }

    public static Drawer getDrawer(Road r) {
        return new RoadDrawer(r.getLocation().getLocation(), r.getDirections());
    }

    public static Drawer getDrawer(WorldObject o) {
        return new NullDrawer(o.getLocation().getLocation());
    }

    public static Drawer getDrawer(Lumberman m) {
        return new LumberjackDrawer(m.getLocation().getLocation());
    }

    public static Drawer getDrawer(StoneMiner m) {
        return new MinerDrawer(m.getLocation().getLocation());
    }

    public static Drawer getDrawer(Transporter m) {
        return new HorseDrawer(m.getLocation().getLocation());
    }

    public static Drawer getDrawer(Ork o) {
        return new OrkDrawer(o.getLocation().getLocation());
    }

    public static Drawer getDrawer(EnemyStronghold obj) {
        return new OrkStrongholdDrawer(obj.getLocation().getLocation());
    }

    public static Drawer getDrawer(Warrior obj) {
        return new WarriorDrawer(obj.getLocation().getLocation());
    }
    public static Drawer getDrawer(Stronghold obj) {
        return new StrongholdDrawer(obj.getLocation().getLocation());
    }
    public static Drawer getDrawer(CarriableResource obj){return new TransportedResourceDrawer(obj.getLocation().getLocation(), obj.getR());}
    public static Drawer getDrawer(Mine obj){return new MineDrawer(obj.getLocation().getLocation());}
    public static Drawer getDrawer(Blacksmith obj){return new BlacksmithDrawer(obj.getLocation().getLocation());}

}
