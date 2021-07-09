package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import voice.VoiceSystem;

import java.net.URL;
import java.util.ResourceBundle;

public class VoiceWindowController implements Initializable {

    private MainController mainController;

    private VoiceSystem voiceSystem;

    @FXML
    private ImageView microphoneButton;

    @FXML
    private Label statusLabel;

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        microphoneButton.setImage(new Image("images/microOFF.png"));
    }

    public void addToTextArea(String text) {
        textArea.setText(textArea.getText() + text + "\n");
        textArea.setScrollTop(Double.MAX_VALUE);
    }

    public void changeLabel(boolean enabled) {
        /* This is used because other threads cannot change the GUI so it comes back to it later when the main thread is free.
           What is weird though is that they can change the colour of the label but not the text.                              */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (enabled) {
                    statusLabel.setText("on");
                    statusLabel.setTextFill(Color.GREEN);
                } else {
                    statusLabel.setText("off");
                    statusLabel.setTextFill(Color.RED);
                }
            }
        });

    }

    @FXML
    private void mouseOverMic(MouseEvent mouseEvent) {
        microphoneButton.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    private void mouseNotOverMic(MouseEvent mouseEvent) {
        microphoneButton.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void onMicClick(MouseEvent mouseEvent) {
        // Gotta check both otherwise it can break and cause problems if it tries to start or close while already starting
        if (!voiceSystem.isThreadActive() && !voiceSystem.isSystemStarting()) {
            voiceSystem.updateGrammar(mainController.getProgramNames());
            microphoneButton.setImage(new Image("images/microON.png"));
            mainController.getMainStage().getIcons().clear();
            mainController.getMainStage().getIcons().add(new Image(getClass().getResourceAsStream("/images/ICON2.png")));
            voiceSystem.startSystem();
        } else if (voiceSystem.isThreadActive() && !voiceSystem.isSystemStarting()){
            microphoneButton.setImage(new Image("images/microOFF.png"));
            mainController.getMainStage().getIcons().clear();
            mainController.getMainStage().getIcons().add(new Image(getClass().getResourceAsStream("/images/ICON1.png")));
            voiceSystem.closeSystem();
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        voiceSystem = new VoiceSystem(mainController, this);
    }

}
