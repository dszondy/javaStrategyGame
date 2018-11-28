package game.gui.drawer.ObjectDrawers;

import game.gui.GuiState;
import game.gui.InGameDrawer;
import game.gui.drawer.Drawer;
import game.model.Directions;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Set;

/**
 * for drawing roads
 */
public class RoadDrawer extends Drawer {
    Set<Directions> lines;

    /**
     * Creates a road drawe
     * @param stdpos its position in the game
     * @param lines the directions of the road
     */
    public RoadDrawer(Point stdpos, Set<Directions> lines) {
        super(stdpos);
        this.lines = lines;
    }

    public Point2D calculatePosition(GuiState state) {
        Point2D p = InGameDrawer.convertCoords(stdpos);
        return super.calculatePosition(state);
    }


    public boolean checkInScreen(GuiState state) {
        Point2D p = calculatePosition(state);
        return !(p.getX() < 0 - fieldWidth || p.getX() > state.getScreenW() + fieldWidth || p.getY() < 0 - fieldHeight || p.getY() > state.getScreenH() + fieldHeight);
    }

    /**
     * Creates it's drawe
     * @param state game state
     * @return the nopde
     */
    public Node draw(GuiState state) {
        StackPane pane = new StackPane();
        ImageView img = new ImageView(getImage());
        img.setSmooth(true);
        img.setPickOnBounds(true);
        img.setScaleX(state.getZoom());
        img.setScaleY(state.getZoom());
        Point2D p = calculatePosition(state);
        pane.setLayoutX(p.getX());
        pane.setLayoutY(p.getY());
        Integer x = stdpos.x;
        Integer y = stdpos.y;

        pane.getChildren()
                .addAll(img);
        return pane;
    }

    /**
     * Creates it's image with low level graphics
     * @return
     */
    public Image getImage() {
        Canvas canvas = new Canvas(256, 128);
        canvas.getGraphicsContext2D().setLineWidth(12);
        canvas.getGraphicsContext2D().setStroke(javafx.scene.paint.Color.DARKGREY);
        for (Directions dir : lines) {
            switch (dir) {
                case L:
                    canvas.getGraphicsContext2D().strokeLine(128, 64, 0, 64);
                    break;
                case LU:
                    canvas.getGraphicsContext2D().strokeLine(128, 64, 64, 16);
                    break;
                case RU:
                    canvas.getGraphicsContext2D().strokeLine(128, 64, 256 - 64, 16);
                    break;
                case R:
                    canvas.getGraphicsContext2D().strokeLine(128, 64, 256, 64);
                    break;
                case RD:
                    canvas.getGraphicsContext2D().strokeLine(128, 64, 256 - 64, 128 - 16);
                    break;
                case LD:
                    canvas.getGraphicsContext2D().strokeLine(128, 64, 64, 128 - 16);
                    break;
            }
        }
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(javafx.scene.paint.Color.TRANSPARENT);
        return canvas.snapshot(sp, null);
    }
}
