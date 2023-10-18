package id.seruput.app.window.popup;

import id.seruput.api.SeruputTeh;
import id.seruput.api.data.cart.Cart;
import id.seruput.api.data.user.User;
import id.seruput.api.util.Callback;
import id.seruput.app.window.Window;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class PurchaseConfirmationWindow extends Window {

    private final Callback callback;

    private final Label titleLabel;
    private final Label confirmationLabel;

    private final VBox confirmBox;
    private final HBox confirmationBox;
    private Button yesButton;
    private Button noButton;

    public PurchaseConfirmationWindow(SeruputTeh seruputTeh, Stage primaryStage, Callback callback) {
        super(seruputTeh, primaryStage, new Scene(new GridPane(), 350, 250));
        primaryStage.setMaxWidth(scene.getWidth());
        primaryStage.setMaxHeight(scene.getHeight());

        primaryStage.setTitle("Order Confirmation");
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#7aa8c4"), null, null)));
        gridPane.setVgap(10);
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        titleLabel = new Label("Order Confirmation");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        titleLabel.setTextFill(Color.web("#ffffff"));
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        titleLabel.setAlignment(Pos.CENTER);

        confirmBox = new VBox();

        confirmationLabel = new Label("Are your sure you want to make purchase?");

        confirmationBox = new HBox();
        confirmationBox.setSpacing(10);

        yesButton = new Button("Yes");
        yesButton.setMinWidth(100);

        noButton = new Button("No");
        noButton.setMinWidth(100);

        confirmationBox.getChildren().addAll(yesButton, noButton);
        confirmationBox.setAlignment(Pos.CENTER);
        confirmationBox.setSpacing(10);

        confirmBox.getChildren().addAll(confirmationLabel, confirmationBox);
        confirmBox.setAlignment(Pos.CENTER);
        confirmBox.setSpacing(10);

        this.callback = callback;
    }

    @Override
    protected void setup() {
        titleLabel.setMinWidth(scene.getWidth());
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        GridPane.setValignment(titleLabel, VPos.TOP);
        GridPane.setHgrow(titleLabel, Priority.ALWAYS);
        gridPane.add(titleLabel, 0, 0);

        // Empty Label to provide space
        Label emptyLabel = new Label();
        GridPane.setHgrow(emptyLabel, Priority.ALWAYS);
        GridPane.setVgrow(emptyLabel, Priority.ALWAYS);
        gridPane.add(emptyLabel, 0, 1);

        GridPane.setHalignment(confirmBox, HPos.CENTER);
        GridPane.setValignment(confirmBox, VPos.CENTER);
        gridPane.add(confirmBox, 0, 2);

        // Empty Label to provide space
        Label emptyLabel2 = new Label();
        GridPane.setHgrow(emptyLabel2, Priority.ALWAYS);
        GridPane.setVgrow(emptyLabel2, Priority.ALWAYS);
        gridPane.add(emptyLabel2, 0, 3);
    }

    @Override
    protected void registerEvent() {
        super.registerEvent();
        yesButton.setOnMouseClicked(this::onYesButton);
        noButton.setOnMouseClicked(this::onNoButton);
    }

    protected void onYesButton(MouseEvent event) {
        User user = currentUser().orElseThrow();
        List<Cart> cart = seruputTeh().cartManager().findCart(user.id());

        if (cart.isEmpty()) {
            createAlert("Failed to make transaction", null);
            return;
        }

        try {
            seruputTeh.transactionManager().purchaseItem(user, cart, seruputTeh.productManager());
            seruputTeh.cartManager().clearCart(user.id());
        } catch (Exception e) {
            logger.trace(e);
            createAlert("Failed to make transaction", null);
            return;
        }

        createAlert(Alert.AlertType.INFORMATION, "Message", "Successfully Purchased", null);
        callback.call();
        primaryStage.close();
    }

    protected void onNoButton(MouseEvent event) {
        primaryStage.close();
    }

}
