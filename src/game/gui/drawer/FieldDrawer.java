package game.gui.drawer;

import game.gui.GuiState;
import game.gui.InGameDrawer;
import game.model.world.Field;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Draws a field
 */
public class FieldDrawer extends Drawer {
    static private Image defImg = new Image("file:resources\\grassField.png");
    protected Field field;

    public FieldDrawer(Point stdpos, Field f) {
        super(stdpos);
        field = f;
    }

    public Image getImage() {
        return defImg;
    }

    public Point2D calculatePosition(GuiState state) {
        Point2D p = InGameDrawer.convertCoords(stdpos);
        return super.calculatePosition(state);
    }

    public boolean checkInScreen(GuiState state) {
        Point2D p = calculatePosition(state);
        return !(p.getX() < 0 - fieldWidth || p.getX() > state.getScreenW() + fieldWidth || p.getY() < 0 - fieldHeight || p.getY() > state.getScreenH() + fieldHeight);
    }

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
        Integer x = new Integer(stdpos.x);
        Integer y = new Integer(stdpos.y);
        if (state.isShowHiddenResources()) {
            ColorAdjust f = new ColorAdjust();
            f.setBrightness(-(double)field.resourceAmmount()/100.0);
            img.setEffect(f);
        }
        pane.getChildren()
                .addAll(img, new Text(x.toString() + ", " + y.toString()));
        return pane;
    }
}
