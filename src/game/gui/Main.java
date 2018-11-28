package game.gui;

import game.gui.menu.MainMenu;
import game.gui.menu.Save;
import game.model.GameObject;
import game.model.objects.Rock;
import game.model.objects.Tree;
import game.model.objects.buildings.Building;
import game.model.objects.buildings.EnemyStronghold;
import game.model.objects.buildings.MainBuilding;
import game.model.world.World;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *Main class. Contains the functions for starting and setting up the game.
 */
public class Main extends Application {
    /**
     * List of saved games
     */
    public static ArrayList<Save> saves;
    /**
     * default screen width
     */
    static int screenW;
    /**
     * default screen height
     */
    static int screenH;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.show();
        primaryStage.setFullScreen(true);
    }

    private static Stage primaryStage;
    private static AnchorPane root;
    static private Timeline tick;

    /**
     * Loads the "Main menu" screen and resets the inner state of the program.
     */
    public static void mainMenu() {
        if (tick != null) {
            tick.stop();
            GameObject.setWorld(null);
            System.gc();
        }
        MainMenu.SetUp(primaryStage);
    }

    /**
     * Loads the "Main menu" screen and resets the inner state of the program.
     */
    public static void GameOver() {
        if (tick != null) {
            tick.stop();
            GameObject.setWorld(null);
            System.gc();
        }
        MainMenu.gameLost();
    }
    /**
     * Starts the game loop. every 1 sec ticks the world and redraws.
     *
     * @param world - the loaded world of the game
     */
    public static void playGame(World world) {
        InGameDrawer drawer = new InGameDrawer(root, world);
        tick = TimelineBuilder.create()//creates a new Timeline
                .keyFrames(
                        new KeyFrame(
                                new Duration(1000),//This is how often it updates in milliseconds
                                new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent t) {
                                        synchronized (world) {
                                            root.getChildren().clear();
                                            drawer.update();
                                            world.tickAll();
                                        }
                                    }
                                }
                        )
                )
                .cycleCount(Timeline.INDEFINITE)
                .build();
        tick.play();//Starts the timeline        InGameDrawer drawer = new InGameDrawer(root, world);
    }

    /**
     * Generates a new world for the game, then starts (call's playGame)
     */
    public static void newGame() {
        primaryStage.close();
        setPrimaryStage(new Stage());
        root = new AnchorPane();
        primaryStage.setScene(new Scene(root, screenW, screenH));
        World w = new World();
        GameObject.setWorld(w);
        Building main = new MainBuilding(w.getFieldAtLocation(new Point(64, 64)));
        Random r = new Random();
        for (int i = 20 * Math.abs(1 - (int) Math.abs(r.nextGaussian()) / 2); i > 0; i--) {
            int x = r.nextInt(60);
            int y = r.nextInt(60);
            if (x > 30)
                x = 128 - x;
            if (y > 30)
                y = 128 - y;
            Point p = new Point(x, y);
            if (EnemyStronghold.checkBuildable(w.getFieldAtLocation(p)))
                new EnemyStronghold(w.getFieldAtLocation(p));
        }
        for (int i = 300 * (1 + Math.abs(2 - (int) Math.abs(r.nextGaussian()))); i > 0; i--) {
            Point p = new Point(r.nextInt(128), r.nextInt(128));
            if (w.getFieldAtLocation(p) != null && w.getFieldAtLocation(p).isClear())
                new Rock(w.getFieldAtLocation(p));
        }
        for (int i = 300 * (1 + Math.abs(2 - (int) Math.abs(r.nextGaussian()))); i > 0; i--) {
            Point p = new Point(r.nextInt(128), r.nextInt(128));
            if (w.getFieldAtLocation(p) != null && w.getFieldAtLocation(p).isClear())
                new Tree(w.getFieldAtLocation(p));
        }

        playGame(w);
    }

    /**
     * Loads the given game and starts it (call's playGame)
     *
     * @param s - identifier of the saved game
     */
    public static void LoadGame(String s) {
        primaryStage.close();
        setPrimaryStage(new Stage());
        root = new AnchorPane();
        primaryStage.setScene(new Scene(root, screenW, screenH));
        try {
            ObjectInputStream is = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File("savedGames/" + s + ".bin"))));
            World w = (World) is.readObject();
            GameObject.setWorld(w);
            playGame(w);
        } catch (Exception e) {
            e.printStackTrace();
            mainMenu();
        }
    }

    /**
     * Main function
     *
     * @param args
     */
    public static void main(String[] args) {
        screenH = 1080;
        screenW = 1920;
        launch(args);
        return;
    }

    public static void addSave(Save save) {
        saves.add(save);
        Save(save.getIdentier(), GameObject.getWorld());

        try (ObjectOutputStream os = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File("saves.bin"))))) {
            os.writeObject(saves);
        } catch (Exception e) {
            saves = new ArrayList<Save>();
            try {
                ObjectOutputStream os = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("saves.bin")));
                os.writeObject(saves);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Saces the world
     * @param s the name of the save
     * @param w the world it saves
     */
    public static void Save(String s, World w){
        synchronized (w){
        try (ObjectOutputStream os = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File("savedGames/" + s + ".bin"))))) {
            os.writeObject(w);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    /**
     * Start method for javafx
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        setPrimaryStage(stage);
        root = new AnchorPane();
        primaryStage.setTitle("StrateGame");
        primaryStage.setScene(new Scene(root, screenW, screenH));
        primaryStage.show();
        LoadSavedGamesList();
        mainMenu();
    }

    /**
     * Loads the list of the saved games (list of "Save" objects)
     */
    public void LoadSavedGamesList() {
        try (ObjectInputStream is = new ObjectInputStream(new GZIPInputStream(new FileInputStream("saves.bin")))) {
            saves = (ArrayList<Save>) is.readObject();

        } catch (Exception e) {
            saves = new ArrayList<Save>();
            e.printStackTrace();
        }
    }
}
