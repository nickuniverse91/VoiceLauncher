package voicelauncher;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;


public class CategoryUI extends VBox implements ICategoryUI{

    private TilePane tilePane;

    public CategoryUI() {
        HBox hBox = new HBox();
        hBox.prefHeight(36);
        hBox.prefWidth(658);
        hBox.setSpacing(5);

        Polygon polygon = new Polygon(0, 0, 20, 0, 10, 20);
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeType(StrokeType.INSIDE);
        polygon.setTranslateY(7);

        Label label = new Label("Applications");
        label.setFont(new Font("Arial Narrow Bold", 27));

        hBox.getChildren().addAll(polygon, label);

        tilePane = new TilePane();
        tilePane.setHgap(50);
        tilePane.setVgap(50);
        tilePane.setPadding(new Insets(20, 20, 20, 20));

        setMargin(this, new Insets(0, 0, 0, 5));

        this.getChildren().addAll(hBox, tilePane);
/*
        for (int i = 1; i < 50; i++) {
            tilePane.getChildren().add(new LaunchProgramButton("das", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\Chrome.exe", 100));
        }
*/
    }

    @Override
    public void addTile(Node tile) {
        tilePane.getChildren().add(tile);
    }

    @Override
    public ObservableList<Node> getTiles() {
        return tilePane.getChildren();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public boolean isExpanded() {
        return false;
    }

    @Override
    public void expand(boolean expanded) {

    }

    @Override
    public void clear() {
        tilePane.getChildren().clear();
    }
}

/*
<VBox prefHeight="452.0" prefWidth="658.0" BorderPane.alignment="CENTER">
             <children>
               <HBox prefHeight="36.0" prefWidth="658.0" spacing="5.0">
                  <children>
                     <Polygon stroke="BLACK" strokeType="INSIDE" translateY="7.0">
                       <points>
                         <Double fx:value="0" />
                         <Double fx:value="0" />
                         <Double fx:value="20" />
                         <Double fx:value="0" />
                         <Double fx:value="10.0" />
                         <Double fx:value="20.0" />
                       </points>
                     </Polygon>
                       <Label text="Applications">
                           <font>
                               <Font name="Arial Narrow Bold" size="27.0" />
                           </font>
                     </Label>
                  </children>
                  <VBox.margin>
                     <Insets left="5.0" />
                  </VBox.margin>
               </HBox>
                 <TilePane fx:id="tilePane" hgap="50.0" vgap="50.0">
                     <padding>
                         <Insets bottom="20.0" left="20.0" right="20.0" top="20.0" />
                     </padding>
                 </TilePane>
             </children>
            </VBox>
 */