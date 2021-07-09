package voicelauncher;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

class Main {

    public static void main(String[] args) {
        Launcher.main(args);
    }

}

public class Launcher extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Launcher.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("VoiceLauncher");

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/ICON1.png")));
        primaryStage.setScene(new Scene(root, 660, 480));
        MainController mainController = loader.getController();
        mainController.setStage(primaryStage);

        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

}
