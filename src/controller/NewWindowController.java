package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ProgramData;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class NewWindowController implements Initializable {

    private MainController mainController;

    @FXML
    private Pane newWindowPane;

    @FXML
    private TextField enterName;

    @FXML
    private TextField enterPath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterName.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue); // Change this to check in Dictionary (and also for rearranging tiles)
        });
    }

    @FXML
    private void browseButtonHandler(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Find Program");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("EXE", "*.exe"));
        File file = fileChooser.showOpenDialog(newWindowPane.getScene().getWindow());
        if (file != null)
            enterPath.setText(file.getPath());
    }

    @FXML
    private void okButtonHandler(ActionEvent actionEvent) {
        String path = enterPath.getText();
        File file = new File(path);
        String name = !enterName.getText().equals("") ? enterName.getText() : file.getName();

        if (path.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Path");
            alert.setHeaderText(null);
            alert.setContentText("Path to application must be entered");
            alert.showAndWait();
        } else if (!file.exists()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Path Error");
            alert.setHeaderText(null);
            alert.setContentText("Path does not exist");
            alert.showAndWait();
        } else {

            mainController.addProgram(new ProgramData(name, path));

            Stage stage = (Stage) newWindowPane.getScene().getWindow();
            stage.close();
        }

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
