package game.model.objects.buildings;

import game.model.Directions;
import game.model.world.Field;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class MedBuilding extends Building {
    MedBuilding(Field p) {
        super(p);
        for (Field f : getOccupy(this.getLocation())) {
            f.add(this);
        }
    }

    public static Field[] getOccupy(Field p) {
        List<Field> l = new LinkedList<Field>();
        l.add(p);
        l.add(p.getNeighbour(Directions.L));
        l.add(p.getNeighbour(Directions.LU));
        l.add(p.getNeighbour(Directions.LU).getNeighbour(Directions.L));
        return Arrays.copyOf(l.toArray(), l.size(), Field[].class);
    }

    public static boolean checkBuildable(Field p) {
        if (!p.isClear())
            return false;
        try {
            for (Field f : getOccupy(p.getNeighbour(Directions.LU))) {
                if (!f.isClear())
                    return false;
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public void die() {
        super.die();
        Field[] fields = getOccupy(this.location);
        for (Field field : fields) {
            field.clear();
        }
    }

}