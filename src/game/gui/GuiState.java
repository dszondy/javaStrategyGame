package game.gui;

import game.model.objects.Sign;
import javafx.scene.layout.Pane;

import static game.gui.GuiState.Mode.NORMAL;
import static game.gui.GuiState.Mode.ROADBUILDING;

/**
 * Represents the games user interdace state.
 * Stores the opened windows and the offsets of the screen.
 */
public class GuiState {
    public Mode mode = Mode.NORMAL;
    public Pane controlWindow;
    private int screenW = 1920;
    private int screenH = 1080;
    private double x = (64-5)*256;
    private double y = (64-10)*128*3/4;
    private double zoom = 1;
    private boolean showHiddenResources = true;

    /**
     * When the player is selecting signs, it's the last selected (for building roads)
     * @return
     */
    public Sign getSelectedSign() {
        return selectedSign;
    }

    /**
     * Closes all open windows (not the root)
     */
    public void rst(){
        selectedSign = null;
        controlWindow = null;
        mode = NORMAL;
    }

    /**
     * The last selected sign
     */
    private Sign selectedSign = null;

    /**
     * Get X(horizontal) offset.
     * @return offset
     */
    public double getX() {
        return x;
    }
    /**
     * Increase X(horizontal) offset
     */
    public void addX() {
        x += 100 * zoom;
    }

    /**
     * Reduces X(horizontal) offset
     */
    public void subX() {
        x -= 100 * zoom;
    }
    /**
     * Get Y(vertical) offset.
     * @return offset
     */
    public double getY() {
        return y;
    }

    /**
     *Increase Y(vertical) offset
     */
    public void addY() {
        y += 100 * zoom;
    }

    /**
     *Reduces Y(vertical) offset
     */
    public void subY() {
        y -= 100 * zoom;
    }

    /**
     * Gets the zooming factor
     * @return the zoom(relative, float-> 1.0 is the default)
     */
    public double getZoom() {
        return zoom;
    }

    /**
     * Increases the zoom (multiply 1.5)
     */
    public void addZoom() {
        zoom *= 1.5;
    }
    /**
     * Reduces the zoom (divide  1.5)
     */
    public void subZoom() {
        zoom /= 1.5;
    }

    /**
     * @return the open inner controlWindow
     */
    public Pane getControlWindow() {
        return controlWindow;
    }

    /**
     * Sets an inner window
     * @param controlWindow
     */
    public void setControlWindow(Pane controlWindow) {
        rst();
        mode = Mode.NORMAL;
        this.controlWindow = controlWindow;
    }

    /**
     * sets a chosen road (for road building)
     * @param sign
     */
    public void roadChosen(Sign sign){
        controlWindow = null;
        mode = ROADBUILDING;
        selectedSign = sign;
    }


    /**
     * @return screen width in px
     */
    public int getScreenW() {
        return screenW;
    }

    /**
     * @return screen height in px
     */
    public int getScreenH() {
        return screenH;
    }

    public boolean isShowHiddenResources() {
        return showHiddenResources;
    }

    public void setShowHiddenResources(boolean showHiddenResources) {
        this.showHiddenResources = showHiddenResources;
    }

    public enum Mode {
        ROADBUILDING,
        NORMAL
    }

}
