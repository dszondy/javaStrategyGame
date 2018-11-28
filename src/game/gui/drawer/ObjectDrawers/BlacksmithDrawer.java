package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

/**
 * Draws the blacksmith
 */
public class BlacksmithDrawer extends BuildingDrawer {
    public BlacksmithDrawer(Point stdpos) {
        super(stdpos, 2);
    }
    static private Image defImg = new Image("file:resources\\Armory.png");

    @Override
    public Image getImage() {
        return defImg;
    }
}
