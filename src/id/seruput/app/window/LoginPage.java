package id.seruput.app.window;

import id.seruput.api.SeruputTeh;
import id.seruput.api.data.user.User;
import id.seruput.api.exception.CredentialErrorException;
import id.seruput.api.util.logger.Logger;
import id.seruput.app.window.main.HomeScene;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginPage extends Window {

    private static final Logger logger = Logger.getLogger(LoginPage.class);

    private final Label title;
    private final Label usernameLabel;
    private final TextField username;
    private final Label passwordLabel;
    private final PasswordField password;
    private final Label accountAsk;
    private final Label register;
    private final Button loginButton;

    public LoginPage(SeruputTeh seruputTeh, Stage stage) {
        super(seruputTeh, stage);

        title = new Label("Login");
//        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
        title.setFont(Font.font("System", FontWeight.BOLD, 40));

        usernameLabel = new Label("Username :");
        username = new TextField();
        username.prefWidth(300);
        username.setPromptText("Username");

        passwordLabel = new Label("Password  :");
        passwordLabel.prefWidth(300);
        password = new PasswordField();
        password.setPromptText("Password");

        accountAsk = new Label("Don't have account yet?");
        register = new Label("Register here");
//        register.setStyle("-fx-text-fill: #007bff;");
        register.setTextFill(Color.web("#007bff"));

        loginButton = new Button("Login");
        loginButton.setPrefWidth(150);
    }

    @Override
    public void setup() {
        HBox accountAskBox = new HBox(accountAsk, register);
        accountAskBox.setSpacing(5);

        HBox loginBox = new HBox(10, loginButton);

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // To align elements in the center
        gridPane.setAlignment(Pos.CENTER);

        GridPane.setHalignment(title, HPos.LEFT);
        gridPane.add(title, 1, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(username, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(password, 1, 2);

        GridPane.setHalignment(accountAskBox, HPos.LEFT);
        gridPane.add(accountAskBox, 1, 3);

        GridPane.setHalignment(loginButton, HPos.LEFT);
        gridPane.add(loginBox, 1, 4);

        primaryStage.setTitle("Login");
    }

    @Override
    protected void registerEvent() {
        super.registerEvent();
        register.setOnMouseClicked(this::register);
        loginButton.setOnMouseClicked(this::login);
    }

    private void register(MouseEvent event) {
        new RegisterPage(seruputTeh, primaryStage).scene();
    }

    private void login(MouseEvent event) {
        SeruputTeh seruputTeh = seruputTeh();
        String username = this.username.getText();
        String password = this.password.getText();

        try {
            User user = seruputTeh.userManager().login(username, password);
            seruputTeh.currentUser(user);
            new HomeScene(seruputTeh, primaryStage).scene();
        } catch (CredentialErrorException e) {
            logger.trace(e);
            createAlert("Failed to login", e.getMessage());
        }
    }

}
