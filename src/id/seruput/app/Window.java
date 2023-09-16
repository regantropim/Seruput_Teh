package id.seruput.app;

import id.seruput.api.SeruputTeh;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public abstract class Window {

    protected final SeruputTeh seruputTeh;
    protected final Stage primaryStage;

    protected Window(SeruputTeh seruputTeh, Stage primaryStage) {
        this.seruputTeh = seruputTeh;
        this.primaryStage = primaryStage;
    }

    public abstract void scene();

    protected SeruputTeh seruputTeh() {
        return seruputTeh;
    }

    protected void createAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == okButton) {
            alert.close();
        }
    }

}
