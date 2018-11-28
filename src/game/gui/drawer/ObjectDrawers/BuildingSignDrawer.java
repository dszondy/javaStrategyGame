package game.gui.drawer.ObjectDrawers;

import game.gui.GuiState;
import game.model.Directions;
import game.model.objects.Sign;
import javafx.scene.image.Image;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

/**
 * Draws the bulding sign
 */
public class BuildingSignDrawer extends SignDrawer {
    /**it's image
     */
    static private Image defImg = new Image("file:resources\\BuildingSign.png");
    /**
     * image for disabled buildings
     */
    static private Image enableImg = new Image("file:resources\\Disabled.png");
    /**
     * If it's building is enabled
     */
    private boolean isEnabled=true;

    /**
     * creates a building sign drawer
     * @param stdpos positin
     * @param lines the directions of the sign
     * @param sign itself (for selection)
     */
    public BuildingSignDrawer(Point stdpos, Set<Directions> lines, Sign sign) {
        super(stdpos, lines, sign);
        dirs = new HashSet<>(dirs);//to modify
        dirs.add(Directions.LU);
    }
    /**
     * creates a building sign drawer
     * @param stdpos positin
     * @param lines the directions of the sign
     * @param sign itself (for selection)
     * @param isEnabled if it's building is enabled
     */
    public BuildingSignDrawer(Point stdpos, Set<Directions> lines, Sign sign, boolean isEnabled) {
        this(stdpos, lines, sign);
        this.isEnabled=isEnabled;
    }

    public Image getImage() {
        return defImg;
    }

    public Node draw(GuiState state) {
        StackPane n = (StackPane)super.draw(state);
        if(!isEnabled) {
            ImageView img = new ImageView(enableImg);
            img.toFront();
            img.setScaleX(state.getZoom());
            img.setScaleY(state.getZoom());
            n.getChildren().add(img);
        }
        return n;
    }
}
