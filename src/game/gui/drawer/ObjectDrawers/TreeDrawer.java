package game.gui.drawer.ObjectDrawers;

import game.gui.GuiState;
import game.gui.drawer.Drawer;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Draws a tree
 */
public class TreeDrawer extends Drawer {
    static private Image defImg = new Image("file:resources\\Tree1.png");

    public TreeDrawer(Point stdpos) {
        super(stdpos);
    }

    protected Point2D calculatePosition(GuiState state) {
        Point2D p = super.calculatePosition(state);
        p.setLocation(p.getX(), p.getY() - state.getZoom() * Drawer.fieldHeight);
        return p;
    }

    @Override
    public Image getImage() {
        return defImg;
    }
}
