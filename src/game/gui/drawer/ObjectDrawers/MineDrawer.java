package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

/**
 * Draws a mine
 */
public class MineDrawer extends BuildingDrawer {
    static private javafx.scene.image.Image defImg = new javafx.scene.image.Image("file:resources\\Mine.png");

    public MineDrawer(Point stdpos) {
        super(stdpos, 1);
    }
    @Override
    public Image getImage() {
        return defImg;
    }

}
