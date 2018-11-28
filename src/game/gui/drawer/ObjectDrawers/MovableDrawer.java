package game.gui.drawer.ObjectDrawers;

import game.gui.GuiState;
import game.gui.drawer.Drawer;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Draws a moveable object
 */
public abstract class MovableDrawer extends Drawer {

    public MovableDrawer(Point stdpos) {
        super(stdpos);
    }

    /**
     * Calculates the object's position on screen
     * @param state world sate
     * @return the point it calculated
     */
    protected Point2D calculatePosition(GuiState state) {
        Point2D p = super.calculatePosition(state);
        p.setLocation(p.getX() + state.getZoom() * Drawer.fieldWidth / 4, p.getY() - state.getZoom() * Drawer.fieldHeight / 2);
        return p;
    }
}
