package game.gui.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

/**
 * Nice big orange button for the menu
 */
public class GameMenuButton extends Button {
    private final int buttonW = 300;
    private final int buttonH = 50;

    /**
     * Creates the button
     * @param text
     * @param clickHandler
     */
    public GameMenuButton(String text, EventHandler<ActionEvent> clickHandler) {
        super(text);
        this.setPrefSize(buttonW, buttonH);
        this.setStyle("{\n" +
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
        this.setAlignment(Pos.CENTER);
        this.setOnAction(clickHandler);
    }
}
