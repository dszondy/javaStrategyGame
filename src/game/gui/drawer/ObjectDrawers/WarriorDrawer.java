package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

/**
 * Draws a warrior
 */
public class WarriorDrawer extends MovableDrawer {

    static private javafx.scene.image.Image defImg = new javafx.scene.image.Image("file:resources\\Warrior.png");

    public WarriorDrawer(Point stdpos) {
        super(stdpos);
    }

    public Image getImage() {
        return defImg;
    }
}
