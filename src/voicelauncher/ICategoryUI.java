package voicelauncher;

import javafx.collections.ObservableList;
import javafx.scene.Node;


public interface ICategoryUI {

    void addTile(Node tile);

    ObservableList<Node> getTiles();

    String getName();

    void setName(String name);

    boolean isExpanded();

    void expand(boolean expanded);

    void clear();

}
