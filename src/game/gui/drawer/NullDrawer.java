package game.gui.drawer;

import javafx.scene.image.Image;

import java.awt.*;

public class NullDrawer extends Drawer {
    static private Image defImg = new Image("file:resources\\null.png");

    public NullDrawer(Point stdpos) {
        super(stdpos);
    }

    @Override
    public Image getImage() {
        return defImg;
    }
}
