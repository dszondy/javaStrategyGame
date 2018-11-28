package game.gui.drawer;

import game.gui.GuiState;
import javafx.scene.image.Image;

import java.awt.*;

/**
 * Drawer for objects that can't be drawn
 */
public class NullDrawer extends Drawer {
    static private Image defImg = new Image("file:resources\\null.png");

    public NullDrawer(Point stdpos) {
        super(stdpos);
    }

    @Override
    public Image getImage() {
        return defImg;
    }

    /**
     * Cant be drawn, never in screen
     * @param state the game state
     * @param point the point of the checking
     * @return false;
     */
    static  public boolean checkInScreen(GuiState state, Point point) {
    return  false;
    }

    }
