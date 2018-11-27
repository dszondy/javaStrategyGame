package game.gui.drawer.ObjectDrawers;

import game.gui.GuiState;
import game.gui.drawer.Drawer;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class MovableDrawer extends Drawer {

    public MovableDrawer(Point stdpos) {
        super(stdpos);
    }

    protected Point2D calculatePosition(GuiState state) {
        Point2D p = super.calculatePosition(state);
        p.setLocation(p.getX() + state.getZoom() * Drawer.fieldWidth / 4, p.getY() - state.getZoom() * Drawer.fieldHeight / 2);
        return p;
    }
}
