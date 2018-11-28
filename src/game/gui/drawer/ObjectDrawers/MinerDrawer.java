package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

/**
 * Draws a miner
 */
public class MinerDrawer extends MovableDrawer {
    static private javafx.scene.image.Image defImg = new javafx.scene.image.Image("file:resources\\StoneMiner.png");

    public MinerDrawer(Point stdpos) {
        super(stdpos);
    }

    @Override
    public Image getImage() {
        return defImg;
    }
}