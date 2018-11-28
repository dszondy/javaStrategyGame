package game.gui.drawer.ObjectDrawers;

import game.gui.GuiState;
import game.gui.drawer.Drawer;
import game.model.Directions;
import game.model.movables.Transporter;
import game.model.objects.Sign;
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
 * Draws a sign
 */
public class SignDrawer extends Drawer {
    static private Image defImg = new Image("file:resources\\Sign.png");
    /**
     * The directions it has
     */
    protected Set<Directions> dirs;
    /**
     * its owner
     */
    protected Sign sign;

    /**
     * Creates a sign drawer
     * @param stdpos position
     * @param lines game state
     * @param sign the object it draws
     */
    public SignDrawer(Point stdpos, Set<Directions> lines, Sign sign) {
        super(stdpos);
        dirs = lines;
        this.sign = sign;
    }

    /**
     * Calculates the position in screen
     * @param state tha game state
     * @return position
     */
    protected Point2D calculatePosition(GuiState state) {
        Point2D p = super.calculatePosition(state);
        p.setLocation(p.getX(), p.getY() - state.getZoom() * Drawer.fieldHeight);
        return p;
    }

    public Image getImage() {
        return defImg;
    }

    /**
     * Draws it road parts, with low level graphics
     * @return the image it drawn
     */
    public Image roadDraw() {
        javafx.scene.canvas.Canvas canvas = new Canvas(fieldWidth, 2 * fieldHeight);
        canvas.getGraphicsContext2D().setLineWidth(12);
        canvas.getGraphicsContext2D().setStroke(javafx.scene.paint.Color.DARKGREY);
        for (Directions dir : dirs) {
            switch (dir) {
                case L:
                    canvas.getGraphicsContext2D().strokeLine(fieldWidth / 2, fieldHeight / 2 + fieldHeight, 0, fieldHeight / 2 + fieldHeight);
                    break;
                case LU:
                    canvas.getGraphicsContext2D().strokeLine(fieldWidth / 2, fieldHeight / 2 + fieldHeight, fieldWidth / 4, fieldHeight / 8 + fieldHeight);
                    break;
                case RU:
                    canvas.getGraphicsContext2D().strokeLine(fieldWidth / 2, fieldHeight / 2 + fieldHeight, fieldWidth * 3 / 4, fieldHeight / 8 + fieldHeight);
                    break;
                case R:
                    canvas.getGraphicsContext2D().strokeLine(fieldWidth / 2, fieldHeight / 2 + fieldHeight, fieldWidth, fieldHeight / 2 + fieldHeight);
                    break;
                case RD:
                    canvas.getGraphicsContext2D().strokeLine(fieldWidth / 2, fieldHeight / 2 + fieldHeight, fieldWidth * 3 / 4, fieldHeight - fieldHeight / 8 + fieldHeight);
                    break;
                case LD:
                    canvas.getGraphicsContext2D().strokeLine(fieldWidth / 2, fieldHeight / 2 + fieldHeight, fieldWidth / 4, fieldHeight - fieldHeight / 8 + fieldHeight);
                    break;
            }
        }
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(javafx.scene.paint.Color.TRANSPARENT);
        return canvas.snapshot(sp, null);
    }

    /**
     * Draws the object and sets mouse clicks handlers
     * @param state the game state
     * @return the node
     */
    @Override
    public Node draw(GuiState state) {
        StackPane pane = new StackPane();
        ImageView img = new ImageView(getImage());
        img.setScaleX(state.getZoom());
        img.setScaleY(state.getZoom());
        Point2D p = calculatePosition(state);
        pane.setLayoutX(p.getX());
        pane.setLayoutY(p.getY());

        ImageView rd = new ImageView(roadDraw());
        rd.setPickOnBounds(true);
        rd.setScaleX(state.getZoom());
        rd.setScaleY(state.getZoom());
        pane.getChildren()
                .addAll(rd, img);
        pane.setPickOnBounds(true);
        pane.setOnMouseClicked(event -> {if(state.mode != GuiState.Mode.ROADBUILDING)
            state.roadChosen(sign);
                else if (state.getSelectedSign()!=sign){

                    Sign s = state.getSelectedSign();
                    state.rst();
                    new Transporter(sign, s);
                }});
        return pane;
    }
}
