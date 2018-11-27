package game.gui.menu;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Class for indexing save files
 */
public class Save implements Serializable{
    /**
     * Time of creation
     */
    public final String date;

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    /**
     * Name of save
     */
    public final String name;

    /**
     * Creates a save object. Time is set to sysdate.
     * @param n
     */
    public Save(String n) {
        name = n;
        date = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss").format(GregorianCalendar.getInstance().getTime());
    }

    /**
     * Concatenates the name and date for a unique identifier.
     * @return - unique identifier string
     */
    public String getIdentier(){
        return name+"_"+date;
    }
}
