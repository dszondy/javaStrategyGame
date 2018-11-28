package game.gui.drawer.ObjectDrawers;

import com.sun.javafx.collections.ObservableListWrapper;
import game.gui.GuiState;
import game.gui.menu.InnerWindow;
import game.model.objects.buildings.MainBuilding;
import game.model.resources.Resource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Draws the main building
 */
public class MainBuildingDrawer extends BuildingDrawer {
    static private javafx.scene.image.Image defImg = new javafx.scene.image.Image("file:resources\\MainBuilding.png");
    private MainBuilding m;

    public MainBuildingDrawer(Point stdpos, MainBuilding m) {
        super(stdpos, 3);
        this.m = m;
    }

    public Image getImage() {
        return defImg;
    }

    /**
     * Lists the resources it stores
     * @return the resources it has.
     */
    public static java.util.List<ResourcePair> getResoures(){
        Map<Resource, Integer> map = MainBuilding.getAddress().getResources();
        java.util.List list = new ArrayList<ResourcePair>();
        for(Resource r: map.keySet()){
            if(r!=Resource.NONE){
                 list.add(new ResourcePair(r, map.get(r)));
            }
        }
        return list;
    }

    /**
     * the resources it has
     */
    static class ResourcePair{
        ResourcePair(Resource r, Integer c){
            resource = new  SimpleStringProperty(r.toString());
            count = new SimpleStringProperty(c.toString());
        }
        SimpleStringProperty  resource;

        public SimpleStringProperty getResource() {
            return resource;
        }

        public SimpleStringProperty getCount() {
            return count;
        }

        SimpleStringProperty count;
    }

    /**
     * draws the resources, and sets the cilck handler to create window which shows the resources
     * @param state the game state
     * @return the node
     */
    @Override
    public Node draw(GuiState state) {
        Node d = super.draw(state);
        TableView<ResourcePair> table = new TableView();
        TableColumn ResCol = new TableColumn("Resources");
        TableColumn CntCol =  new TableColumn("Count");
        ResCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ResourcePair, String>, ObservableValue<String>>) c -> c.getValue().getResource());
        CntCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ResourcePair, String>, ObservableValue<String>>) c -> c.getValue().getCount());
        table.getColumns().addAll(ResCol, CntCol);
        table.setEditable(false);
        java.util.List<ResourcePair> ls = getResoures();
        table.setItems(new ObservableListWrapper<>(ls));
        d.setOnMouseClicked(event -> state.setControlWindow(new InnerWindow(new Pane(new ScrollPane(table)), new javafx.event.EventHandler<ActionEvent>() {
            GuiState s = state;

            public void handle(ActionEvent event) {
                s.setControlWindow(null);
            }
        })));
        return d;
    }
}
