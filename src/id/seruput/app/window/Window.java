package id.seruput.app.window;

import id.seruput.api.SeruputTeh;
import id.seruput.api.data.cart.CartManager;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.data.transaction.TransactionManager;
import id.seruput.api.data.user.User;
import id.seruput.api.data.user.UserManager;
import id.seruput.api.util.logger.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public abstract class Window {

    protected final SeruputTeh seruputTeh;
    protected final Stage primaryStage;
    protected final GridPane gridPane;
    protected final Scene scene;
    protected final Logger logger = Logger.getLogger(getClass());

    protected Window(SeruputTeh seruputTeh, Stage primaryStage) {
        this(seruputTeh, primaryStage, new Scene(new GridPane(), 900, 800));
    }

    protected Window(SeruputTeh seruputTeh, Stage primaryStage, Scene scene) {
        this.seruputTeh = seruputTeh;
        this.primaryStage = primaryStage;
        this.scene = scene;
        this.gridPane = (GridPane) scene.getRoot();
    }

    public void scene() {

        logger.info("Setting up scene");
        setup();

        logger.info("Setting scene to stage");
        primaryStage.setScene(scene);

        registerEvent();

        logger.info("Showing stage");
        primaryStage.show();

    }

    protected abstract void setup();

    protected void registerEvent() {
        logger.info("Registering event");
    }

    protected SeruputTeh seruputTeh() {
        return seruputTeh;
    }

    protected void createAlert(String header, String content) {
        createAlert(Alert.AlertType.ERROR, "Error", header, content);
    }

    protected void createAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElseThrow() == okButton) {
            alert.close();
        }
    }

    protected UserManager userManager() {
        return seruputTeh.userManager();
    }

    protected ProductManager productManager() {
        return seruputTeh.productManager();
    }

    protected CartManager cartManager() {
        return seruputTeh.cartManager();
    }

    protected Optional<User> currentUser() {
        return seruputTeh.currentUser();
    }

    protected TransactionManager transactionManager() {
        return seruputTeh.transactionManager();
    }

}
