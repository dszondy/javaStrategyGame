package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

public class OrkStrongholdDrawer extends BuildingDrawer {
    static private Image defImg = new Image("file:resources\\WoodFortress.png");

    public OrkStrongholdDrawer(Point stdpos) {
        super(stdpos, 3);
    }

    public Image getImage() {
        return defImg;
    }
}
