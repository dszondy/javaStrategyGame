package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

public class HorseDrawer extends MovableDrawer {
    static private javafx.scene.image.Image defImg = new javafx.scene.image.Image("file:resources\\Horse.png");

    public HorseDrawer(Point stdpos) {
        super(stdpos);
    }

    @Override
    public Image getImage() {
        return defImg;
    }
}

