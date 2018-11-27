package game.gui.drawer.ObjectDrawers;

import game.model.Directions;
import game.model.objects.Sign;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class BuildingSignDrawer extends SignDrawer {
    static private Image defImg = new Image("file:resources\\BuildingSign.png");
    public BuildingSignDrawer(Point stdpos, Set<Directions> lines, Sign sign) {
        super(stdpos, lines, sign);
        dirs = new HashSet<>(dirs);//to modify
        dirs.add(Directions.LU);
    }


    public Image getImage() {
        return defImg;
    }
}
