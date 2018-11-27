package game.gui.menu;

import com.sun.javafx.collections.ObservableListWrapper;
import game.gui.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import static game.gui.Main.LoadGame;
import static game.gui.Main.saves;

/**
 * Main menu screens defined here
 */
public class MainMenu {
    /**
     * App's defauult window
     */
    private Stage window;
    /**
     * Panel in the center of screen
     */
    private FlowPane midPanel = new FlowPane();
    /**
     * Root panel
     */
    private BorderPane panel = new BorderPane();

    /**
     * Creates a new instance
     * @param window main window
     */
    public MainMenu(Stage window) {
        this.window = window;
        Menu();
    }

    /**
     * Loads the main menu screen
     */
    private void Menu() {

        midPanel.setVgap(10);
        midPanel.setOrientation(Orientation.VERTICAL);
        midPanel.setAlignment(Pos.CENTER);

        addNewGameButton();
        addLoadGameButton();
        addExitButton();

        Image bgImage = new Image("file:resources\\MenuBg.jpg");
        BorderPane.setAlignment(midPanel, Pos.CENTER);
        panel.setCenter(midPanel);
        panel.setBackground(new Background(new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        window.getScene().setRoot(panel);
        window.show();
    }

    /**
     * Adds then new game button to the center.
     */
    private void addNewGameButton() {
        GameMenuButton button = new GameMenuButton("New Game",
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Main.newGame();
                    }
                });
        midPanel.getChildren().add(button);
    }

    /**
     * Adds the exit button
     */
    private void addExitButton() {
        GameMenuButton button = new GameMenuButton("Exit Game",
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        System.exit(0);
                    }
                });
        midPanel.getChildren().add(button);
    }
/**
 * Adds the Load game button
 */
    private void addLoadGameButton() {
        GameMenuButton button = new GameMenuButton("Load Game", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                goLoadGame();
            }
        });
        midPanel.getChildren().add(button);
    }

    /**
     * Opens the game loading table.
     */
    private void goLoadGame() {
        BorderPane frame = new BorderPane();
        TableView table = new TableView();
        table.setEditable(false);
        TableColumn date = new TableColumn("Date");
        TableColumn name = new TableColumn("Name");
        date.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Save, String>, ObservableValue<String>>) c -> new SimpleStringProperty(c.getValue().getDate().toString()));
        name.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Save, String>, ObservableValue<String>>) c -> new SimpleStringProperty(c.getValue().getName()));

        table.getColumns().addAll(name, date);
        table.setItems(new ObservableListWrapper(Main.saves));

        table.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(5), new Insets(3))));
        ScrollPane tableFrame = new ScrollPane(table);
        tableFrame.setFitToHeight(true);
        tableFrame.setFitToWidth(true);
        frame.setCenter(tableFrame);

        InnerWindow window = new InnerWindow(frame, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panel.setCenter(midPanel);
            }
        });
        window.setMaxSize(panel.getWidth() / 3, panel.getHeight() / 3);
        panel.setCenter(window);
        BorderPane.setAlignment(window, Pos.CENTER);
        table.addEventHandler(KeyEvent.ANY, (EventHandler<KeyEvent>) event -> {
            System.out.print("Called\n");
            LoadGame(saves.get(table.getSelectionModel().getSelectedIndex()).getIdentier());
        });
    }

    /**
     * Shows the game lost screen
     */
    public void gameLost(){
        midPanel.getChildrenUnmodifiable().clear();
        midPanel.setVgap(10);
        midPanel.setOrientation(Orientation.VERTICAL);
        midPanel.setAlignment(Pos.CENTER);
        TextField t = new TextField("Sorry, you lost the game");
        midPanel.getChildren().add(t);
        midPanel.getChildren().add(new GameMenuButton("Ok, go back to the menu", event -> Menu()));
        midPanel.getChildren().add(new GameMenuButton("I'm mad, exit ", event -> System.exit(0)));
        Image bgImage = new Image("file:resources\\MenuBg.jpg");
        BorderPane.setAlignment(midPanel, Pos.CENTER);
        panel.setCenter(midPanel);
        panel.setBackground(new Background(new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        window.getScene().setRoot(panel);
        window.show();
    }

}
