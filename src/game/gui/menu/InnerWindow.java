package game.gui.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Inner window for controls and sub menus
 */
public class InnerWindow extends BorderPane {
    /**
     * @param content a pane that is filled with the content of the panel
     * @param onClose handles the close (the x in the corner is pressed)
     */
    public InnerWindow(Pane content, EventHandler<ActionEvent> onClose) {
        this.setCenter(content);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, new CornerRadii(5), new Insets(3))));
        this.setPadding(new Insets(10));
        Button close = new Button("X");
        close.setOnAction(onClose);
        this.setTop(close);
        close.setStyle("{\n" +
                "    -fx-padding: 8 15 15 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 8;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
                "        #9d4024,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 1.1em;}\n");
        setAlignment(close, Pos.TOP_RIGHT);
        this.setLayoutX((1920 - this.getScaleX()) / 2);
        this.setLayoutY((1080 - this.getScaleY()) / 2);

    }
}
