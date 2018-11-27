package game.model;

import game.gui.drawer.Drawer;

import java.awt.*;

/**
 * Interface for those game object's that will be visible in the game and shown on the screen.
 */
public interface Visible {
    /**
     * Gets the objects drawers, that can create it's view (in my case a javafx node)
     * @return an appropriate Drawer object for it's type and inner state.
     */
    Drawer getDrawer();

    /**
     * returns it's location in the game's coordinate system (see more at InGameDrawer)
     * @return Point of the position
     */
    Point getPoint();

    /**
     * Returns dhe Visible object's Y coordinate, so that it can be sorted by that for drawing order.
     * @return Y coordinate of the object's position.
     */
    double getY();
}
