package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

/**
 * Draws a lumberjack
 */
public class LumberjackDrawer extends MovableDrawer {

    static private javafx.scene.image.Image defImg = new javafx.scene.image.Image("file:resources\\Lumberjack.png");

    public LumberjackDrawer(Point stdpos) {
        super(stdpos);
    }

    @Override
    public Image getImage() {
        return defImg;
    }
}
