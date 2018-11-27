package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

public class StrongholdDrawer extends BuildingDrawer {
    static private javafx.scene.image.Image defImg = new javafx.scene.image.Image("file:resources\\Stronghold.png");

    public StrongholdDrawer(Point stdpos) {
        super(stdpos, 3);
    }

    public Image getImage() {
        return defImg;
    }
}
