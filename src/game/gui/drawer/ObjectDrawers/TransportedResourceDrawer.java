package game.gui.drawer.ObjectDrawers;

import game.model.resources.Resource;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class TransportedResourceDrawer extends MovableDrawer {
    static Map<Resource, Image> imageMap = null;
    private Resource type;

    public TransportedResourceDrawer(Point stdpos, Resource r) {
        super(stdpos);
        type = r;
        if(imageMap==null){
            imageMap = new HashMap<>();
            imageMap.put(Resource.IRON, new javafx.scene.image.Image("file:resources\\Iron.png"));
            imageMap.put(Resource.WOOD, new javafx.scene.image.Image("file:resources\\Wood.png"));
                    imageMap.put(Resource.STONE,new javafx.scene.image.Image("file:resources\\Stone.png"));
            imageMap.put(Resource.SWORD, new javafx.scene.image.Image("file:resources\\Sword.png"));
                                    imageMap.put(Resource.NONE,new javafx.scene.image.Image("file:resources\\null.png"));
        }
    }

    @Override
    public javafx.scene.image.Image getImage() {
        return imageMap.get(type);
    }
}
