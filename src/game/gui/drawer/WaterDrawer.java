package game.gui.drawer;

import game.model.world.Field;
import javafx.scene.image.Image;

import java.awt.*;

public class WaterDrawer extends FieldDrawer {

    static private Image img = new Image("file:resources\\waterField.png");

    public WaterDrawer(Point stdpos, Field f) {
        super(stdpos, f);
    }

    public Image getImage() {
        return img;
    }
}
