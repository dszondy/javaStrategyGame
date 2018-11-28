package game.gui.drawer;

import game.gui.GuiState;
import game.gui.Main;
import game.gui.menu.GameMenuButton;
import game.gui.menu.InnerWindow;
import game.gui.menu.Save;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Drawer for the game gui
 */
public class GuiDrawer {
    /**
     * Draws the guls
     * @param root the root pane to draw on
     * @param state tha game state
     */
    public void draw(AnchorPane root, GuiState state) {
        FlowPane panel = new FlowPane();
        panel.setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, new CornerRadii(5), new Insets(3))));
        panel.setMinWidth(root.getWidth() / 2);
        panel.setMinHeight(100);
        panel.setAlignment(Pos.BOTTOM_CENTER);
        panel.setLayoutY(root.getHeight() - 100);
        panel.setLayoutX(root.getWidth() / 4);
        panel.setPadding(new Insets(10));
        panel.getChildren().add(new GameMenuButton("Save", (EventHandler) event -> {
            setSaveGameWindow(state);
        }));
        panel.getChildren().add(new GameMenuButton("Exit",(EventHandler) event -> System.exit(0)));
        panel.getChildren().add(new GameMenuButton("Main menu",(EventHandler) event -> Main.mainMenu()));

        root.getChildren().add(panel);
        javafx.scene.Node window = state.getControlWindow();
        if (window != null)
            root.getChildren().add(window);
    }

    /**
     * Creates the savegame window
     * @param state the game state
     */
    void setSaveGameWindow(GuiState state){
        FlowPane pane = new FlowPane();
        TextField saveName = new TextField("Name");
        saveName.setEditable(true);
        pane.getChildren().add(saveName);
        pane.getChildren().add(new GameMenuButton("Save", event -> {Main.addSave(new Save(saveName.getText())); state.rst();}));
        InnerWindow window = new InnerWindow(pane, event -> state.rst());
        state.setControlWindow(window);
    }
}
