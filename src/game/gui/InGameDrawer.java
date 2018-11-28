package game.gui;

import game.gui.drawer.Drawer;
import game.gui.drawer.GuiDrawer;
import game.model.Visible;
import game.model.world.World;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Draws everything when the game is running
 */
public class InGameDrawer {
    /**
     * The "screen" it draws on
     */
    AnchorPane root;
    /**
     * Game's active world
     */
    World world;
    /**
     * The state of the game game.gui
     */
    GuiState state = new GuiState();
    /**
     * The game.gui's drawer
     */
    GuiDrawer gui;

    /**
     * sets upt he object
     * @param root the screen
     * @param world active world of the game
     */
    public InGameDrawer(AnchorPane root, World world){
        this.root = root;
        this.world = world;
        this.gui = new GuiDrawer();

        root.getScene().setOnKeyPressed(ke -> {
            boolean update = true;
            synchronized (world) {
                KeyCode code = ke.getCode();
                switch (code) {
                    case LEFT:
                        state.subX();
                        break;
                    case RIGHT:
                        state.addX();
                        break;
                    case UP:
                        state.subY();
                        break;
                    case DOWN:
                        state.addY();
                        break;
                        /*
                    case PLUS:
                        state.addZoom();
                        break;
                    case MINUS:
                        state.subZoom();
                        break;
                        */
                    default:
                        update = false;
                        break;
                }
                if (update)
                    update();
            }
        });
    }

    /**
     * Converts coords between the draw and the in game coordinate systems
     * @param p in game's coords
     * @return in drawing coords
     */
    public static Point2D convertCoords(Point p) {
        double x = p.x + (p.y % 2 == 1 ? 0.5 : 0);
        double y = (double) p.y / 4.0 * 3.0;
        return new Point2D.Double(x, y);
    }

    /**
     * Redraws all
     */
    public void update() {
        root.getChildren().clear();
        root.getChildrenUnmodifiable().clear();
        System.gc();
        List<Visible> objects = world.GetVisibleList();
        List<Drawer> drawers = convertToDrawers(objects);
        for (Drawer drawer : drawers) {
                javafx.scene.Node d = drawer.draw(state);
                d.toFront();
                root.getChildren().add(d);
                d.toFront();
        }
        gui.draw(root, state);
    }

    /**
     * Gets drawers from visibles (if they will be visible in the screen)
     * @param objects
     * @return
     */
    private List<Drawer> convertToDrawers(List<Visible> objects) {
        LinkedList<Drawer> drawers = new LinkedList<>();
        for (Visible obj : objects) {
                if(Drawer.checkInScreen(state, obj.getPoint()))
                    drawers.addLast(obj.getDrawer());
        }
        return drawers;
    }
}

