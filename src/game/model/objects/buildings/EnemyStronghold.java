package game.model.objects.buildings;

import game.gui.drawer.Drawer;
import game.gui.drawer.DrawerCreator;
import game.model.movables.Ork;
import game.model.movables.Warrior;
import game.model.world.Field;

import java.util.Random;

public class EnemyStronghold extends BigBuilding {
    int orksInside = 6;

    public EnemyStronghold(Field p) {
        super(p, false);
    }

    @Override
    public boolean tick() {
        if (!isAlive)
            return false;
        if (new Random().nextInt(30) < orksInside) {
            orksInside--;
            new Ork(this);
        }
        if(orksInside<5 && new Random().nextGaussian()>1.8){
            orksInside++;
        }
        return true;
    }

    public void OrkEnters(Ork o) {
        o.die();
        this.orksInside++;
    }

    public void AttackedBy(Warrior warrior) {
        orksInside--;
        warrior.die();
        if (orksInside < 0)
            die();
    }

    public void acceptProbe(Ork.OrkProbe probe) {
        probe.addInfo(false);
    }

    public void acceptProbe(Warrior.AttackProbe probe) {
        probe.addInfo(false, this);
    }

    @Override
    public Drawer getDrawer() {
        return DrawerCreator.getDrawer(this);
    }
}


