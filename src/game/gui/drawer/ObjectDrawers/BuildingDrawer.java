package game.gui.drawer.ObjectDrawers;

import game.gui.GuiState;
import game.gui.InGameDrawer;
import game.gui.drawer.Drawer;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class BuildingDrawer extends Drawer {
    int size;

    public BuildingDrawer(Point stdpos, int size) {
        super(stdpos);
        this.size = size;
    }

    protected Point2D calculatePosition(GuiState state) {
        Point2D p = InGameDrawer.convertCoords(stdpos);
        return new Point2D.Double(
                (p.getX() * fieldWidth * state.getZoom() - state.getX() * state.getZoom() - (size - 0.5) * Drawer.fieldWidth * state.getZoom()),
                (p.getY() * fieldHeight * state.getZoom() - state.getY()) * state.getZoom() - (2 * size - 1) * Drawer.fieldHeight * state.getZoom());
    }
}
