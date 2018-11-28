package game.gui.drawer.ObjectDrawers;

import game.gui.GuiState;
import game.gui.drawer.FieldDrawer;
import game.gui.menu.GameMenuButton;
import game.gui.menu.InnerWindow;
import game.model.GameObject;
import game.model.objects.Sign;
import game.model.objects.buildings.*;
import game.model.world.Field;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Draws an empty field (can be clicked for building
 */
public class EmptyFieldDrawer extends FieldDrawer {
    public EmptyFieldDrawer(Point stdpos, Field f) {
        super(stdpos, f);
    }

    /**
     * Builds the object with the given name.
     * @param name
     */
    void Build(String name) {
        synchronized (GameObject.getWorld()){
        if (checkBuildables().contains(name))
        switch (name) {
            case "Sign":
                new Sign(field);
                break;
            case "Mine":
                new Mine(field);
                break;
            case "LumbererHouse":
                new LumbererHouse(field);
                break;
            case "StoneMine":
                new StoneMine(field);
                break;
            case "Blacksmith":
                new Blacksmith(field);
                break;
            case "Stronghold":
                new Stronghold(field);
                break;
            default:
                break;
        }
        }
    }

    /**
     * Creates a list of the object that can be build on it
     * @return the list of buildable obbjects
     */
    public List<String> checkBuildables() {
        List<String> buildables = new ArrayList<>();
        if (field.isClear())
            buildables.add("Sign");
        if (Mine.checkBuildable(field))
            buildables.add("Mine");
        if (LumbererHouse.checkBuildable(field))
            buildables.add("LumbererHouse");
        if (StoneMine.checkBuildable(field))
            buildables.add("StoneMine");
        if (Blacksmith.checkBuildable(field))
            buildables.add("Blacksmith");
        if (Stronghold.checkBuildable(field))
            buildables.add("Stronghold");
        return buildables;
    }

    /**
     * Fills the building window
     * @return building window pane
     */
    public Pane getWindowData() {
        FlowPane pane = new FlowPane();
        pane.setOrientation(Orientation.VERTICAL);
        for (String s : checkBuildables()) {
            GameMenuButton b = new GameMenuButton(s, (EventHandler) event ->  Build(s));
            pane.getChildren().add(b);
        }
        return pane;
    }

    /**
     * sets the click handler too
     * @param state the game state for drawing
     * @return the node
     */
    public Node draw(GuiState state) {
        Node d = super.draw(state);
        d.setOnMouseClicked(event -> state.setControlWindow(new InnerWindow(getWindowData(), new javafx.event.EventHandler<ActionEvent>() {
            GuiState s = state;

            public void handle(ActionEvent event) {
                s.setControlWindow(null);
            }
        })));
        return d;
    }

}

