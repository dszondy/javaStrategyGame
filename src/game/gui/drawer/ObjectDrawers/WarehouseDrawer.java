package game.gui.drawer.ObjectDrawers;

import javafx.scene.image.Image;

import java.awt.*;

public class WarehouseDrawer extends BuildingDrawer {
    static private Image defImg = new Image("file:resources\\Warehouse.png");

    public WarehouseDrawer(Point stdpos) {
        super(stdpos, 2);
    }

    public Image getImage() {
        return defImg;
    }
}
