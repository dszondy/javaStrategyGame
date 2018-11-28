package game.model.objects.buildings;

import game.model.Directions;
import game.model.world.Field;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
/**
 * Just another building, with size "BIG", means it's 9 field big(3*3)
 *  _____________________
 *  <><>    <-Building
 *   <><>
 *      <> <-entry sign
 */

/**
 * Creates a building with sign
 * @param p the location of the "sign"
 */
public abstract class MedBuilding extends Building {
    MedBuilding(Field p) {
        super(p);
        for (Field f : getOccupy(this.getLocation())) {
            f.add(this);
        }
    }

    /**
     * Returns the fields it wolud be on
     * @param p position right down from it
     * @return fields of null if it's out of bounds
     */
    public static Field[] getOccupy(Field p) {
        List<Field> l = new LinkedList<Field>();
        try {
            l.add(p);
            l.add(p.getNeighbour(Directions.L));
            l.add(p.getNeighbour(Directions.LU));
            l.add(p.getNeighbour(Directions.LU).getNeighbour(Directions.L));
        }catch (NullPointerException e){
            return null;
        }
        return Arrays.copyOf(l.toArray(), l.size(), Field[].class);
    }
    /**
     * check's if we can build this
     * @param p field
     * @return true if it's possible
     */
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
    /**
     * The object dies and removes itself
     */
    public void die() {
        super.die();
        Field[] fields = getOccupy(this.location);
        for (Field field : fields) {
            field.clear();
        }
    }

}