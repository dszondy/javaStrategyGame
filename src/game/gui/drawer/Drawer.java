package game.gui.drawer;

import game.gui.GuiState;
import game.gui.InGameDrawer;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Simple class that defines the drawable objects. Every visible object creates one of it's children
 */
public abstract class Drawer {
    /**
     * Standard field width
     */
    public static final int fieldWidth = 256;
    /**
     * Standard field height
     */
    public static final int fieldHeight = 128;
    /**
     * Position
     */
    protected Point stdpos;

    /**
     * Creates a drawer with position
     * @param stdpos the position point
     */
    public Drawer(Point stdpos) {
        this.stdpos = stdpos;
    }

    /**
     * calculates the drawing pos bí coords
     * @param state
     * @return
     */
    protected Point2D calculatePosition(GuiState state) {
        Point2D p = InGameDrawer.convertCoords(stdpos);
        return new Point2D.Double(
                (p.getX() * fieldWidth * state.getZoom() - ((fieldWidth / 2)) - state.getX() * state.getZoom()),
                (p.getY() * fieldHeight * state.getZoom() - ((fieldHeight / 2) + state.getY()) * state.getZoom()));
    }

    /**
     * Calculates the expected position
     * @param state
     * @param point
     * @return
     */
    public static Point2D calculatePosition(GuiState state, Point point) {
        Point2D p = InGameDrawer.convertCoords(point);
        return new Point2D.Double(
                (p.getX() * fieldWidth * state.getZoom() - ((fieldWidth / 2)) - state.getX() * state.getZoom()),
                (p.getY() * fieldHeight * state.getZoom() - ((fieldHeight / 2) + state.getY()) * state.getZoom()));
    }


    /**
     * Returns the image of the object
     * @return image
     */
    public abstract Image getImage();

    /**
     * Creates a node with the object
     * @param state the game state
     * @return the node
     */
    public Node draw(GuiState state) {
        ImageView img = new ImageView(getImage());
        img.setSmooth(true);
        img.setPickOnBounds(true);
        img.setScaleX(state.getZoom());
        img.setScaleY(state.getZoom());
        Point2D p = calculatePosition(state);
        img.setLayoutX(p.getX());
        img.setLayoutY(p.getY());
        return img;
    }

    /**
     * checks if the object will be visible
     * @param state game state
     * @param point the object's position
     * @return false if is invisible true if it is visible
     */
    static  public boolean checkInScreen(GuiState state, Point point) {
        Point2D p = calculatePosition(state, point);
        return !(p.getX() < 0 - 4 * fieldWidth || p.getX() > state.getScreenW() + 4 * fieldWidth || p.getY() < 0 - 4 * fieldHeight || p.getY() > state.getScreenH() + 4 * fieldHeight);
    }
}
