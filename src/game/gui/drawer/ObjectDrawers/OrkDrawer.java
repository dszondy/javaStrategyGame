package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

public class OrkDrawer extends MovableDrawer {
    static private javafx.scene.image.Image defImg = new javafx.scene.image.Image("file:resources\\Ork.png");

    public OrkDrawer(Point stdpos) {
        super(stdpos);
    }

    public Image getImage() {
        return defImg;
    }
}
