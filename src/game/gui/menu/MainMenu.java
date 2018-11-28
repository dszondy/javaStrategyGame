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
import javafx.scene.Scene;
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
 * Singleton
 */
public class MainMenu {
    /**
     * App's defauult window
     * @return  the active window
     */
    static private Stage GetWindow(){
        return Main.getPrimaryStage();
    }
    static private void SetWindow(Stage stage){
         Main.setPrimaryStage(stage);
    }
    /**
     * Panel in the center of screen
     */
    static private FlowPane midPanel = new FlowPane();
    /**
     * Root panel
     */
    static private BorderPane panel = new BorderPane();

    /**
     * Creates a new instance
     * @param w main window
     */
    static public void SetUp(Stage w) {
        SetWindow( w);
        Menu();
    }

    /**
     * Loads the main menu screen
     */
    static private void Menu() {
        panel = new BorderPane();
        midPanel = new FlowPane();
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
        GetWindow().close();
        SetWindow( new Stage());
        GetWindow().setScene(new Scene(panel,1920, 1080));
        GetWindow().show();
    }

    /**
     * Adds then new game button to the center.
     */
    static private void addNewGameButton() {
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
    static private void addExitButton() {
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
    static private void addLoadGameButton() {
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
    static private void goLoadGame() {
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
    static public void gameLost(){
        midPanel = new FlowPane();
        panel = new BorderPane();
        midPanel.getChildrenUnmodifiable().clear();
        midPanel.setVgap(10);
        midPanel.setOrientation(Orientation.VERTICAL);
        midPanel.setAlignment(Pos.CENTER);
        TextField t = new TextField("Sorry, you lost the game");
        t.setEditable(false);
        t.setAlignment(Pos.CENTER);
        midPanel.getChildren().add(t);
        midPanel.getChildren().add(new GameMenuButton("Ok, go back to the menu", event -> Menu()));
        midPanel.getChildren().add(new GameMenuButton("I'm mad, exit ", event -> System.exit(0)));
        Image bgImage = new Image("file:resources\\MenuBg.jpg");
        BorderPane.setAlignment(midPanel, Pos.CENTER);
        panel.setCenter(midPanel);
        panel.setBackground(new Background(new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        GetWindow().close();
        SetWindow( new Stage());
        GetWindow().setScene(new Scene(panel, 1920, 1080));
        GetWindow().show();
    }
    /**
     * Shows the game lost screen
     */
    static public void gameWon(){
        midPanel = new FlowPane();
        panel = new BorderPane();
        midPanel.getChildrenUnmodifiable().clear();
        midPanel.setVgap(10);
        midPanel.setOrientation(Orientation.VERTICAL);
        midPanel.setAlignment(Pos.CENTER);
        TextField t = new TextField("Yeey you won");
        t.setEditable(false);
        t.setAlignment(Pos.CENTER);
        midPanel.getChildren().add(t);
        midPanel.getChildren().add(new GameMenuButton("Awesome, go to the menu", event -> Menu()));
        midPanel.getChildren().add(new GameMenuButton("Thx, that's enough for now", event -> System.exit(0)));
        Image bgImage = new Image("file:resources\\MenuBg.jpg");
        BorderPane.setAlignment(midPanel, Pos.CENTER);
        panel.setCenter(midPanel);
        panel.setBackground(new Background(new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        GetWindow().close();
        SetWindow( new Stage());
        GetWindow().setScene(new Scene(panel, 1920, 1080));
        GetWindow().show();
    }
}
