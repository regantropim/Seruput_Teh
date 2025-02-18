package id.seruput.app.window;

import id.seruput.api.SeruputTeh;
import id.seruput.api.data.user.UserGender;
import id.seruput.api.data.user.UserRole;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.util.logger.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Optional;

import static id.seruput.core.util.Language.FIELDS_EMPTY;

public class RegisterPage extends Window {

    private static final Logger logger = Logger.getLogger(RegisterPage.class);

    private final Label title;
    private final Label usernameLabel;
    private final Label passwordLabel;
    private final Label confirmPasswordLabel;
    private final Label emailLabel;
    private final Label phoneNumberLabel;
    private final Label addressLabel;
    private final Label genderLabel;
    private final Label accountAsk;
    private final Label login;
    private final ToggleGroup genderGroup;
    private final RadioButton genderMale;
    private final RadioButton genderFemale;
    private final CheckBox termsAndConditions;
    private final Button registerButton;

    private final TextField username;
    private final PasswordField password;
    private final PasswordField confirmPassword;
    private final TextField email;
    private final TextField phoneNumber;
    private final TextArea address;

    public RegisterPage(SeruputTeh seruputTeh, Stage stage) {
        super(seruputTeh, stage);

        title = new Label("Register");
//        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
        title.setFont(Font.font("System", FontWeight.BOLD, 40));


        usernameLabel = new Label("Username :");
        passwordLabel = new Label("Password :");
        confirmPasswordLabel = new Label("Confirm Password :");
        emailLabel = new Label("Email :");
        phoneNumberLabel = new Label("Phone Number :");
        genderLabel = new Label("Gender :");
        addressLabel = new Label("Address :");
        accountAsk = new Label("Already have account?");

        login = new Label("Login here");
        login.setTextFill(Color.web("#007bff"));

        genderGroup = new ToggleGroup();

        genderMale = new RadioButton(UserGender.MALE.getName());
        genderFemale = new RadioButton(UserGender.FEMALE.getName());

        genderMale.setToggleGroup(genderGroup);
        genderFemale.setToggleGroup(genderGroup);

        termsAndConditions = new CheckBox("I agree to all terms and condition");

        registerButton = new Button("Register");

        username = new TextField();
        username.setPrefWidth(300);

        password = new PasswordField();
        password.setPrefWidth(300);

        confirmPassword = new PasswordField();
        confirmPassword.setPrefWidth(300);

        email = new TextField();
        email.setPrefWidth(300);

        phoneNumber = new TextField();
        phoneNumber.setPrefWidth(300);

        address = new TextArea();
        address.setWrapText(true);
        address.setPrefWidth(300);
        address.setPrefHeight(150);

        stage.setTitle("Register");
    }

    @Override
    public void setup() {
        gridPane.setAlignment(Pos.CENTER);

        gridPane.setVgap(10);
        gridPane.setHgap(10);

        GridPane.setHalignment(title, HPos.LEFT);
        gridPane.add(title, 1, 0, 2, 1);

        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(username, 1, 1);

        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(password, 1, 2);

        gridPane.add(confirmPasswordLabel, 0, 3);
        gridPane.add(confirmPassword, 1, 3);

        gridPane.add(emailLabel, 0, 4);
        gridPane.add(email, 1, 4);

        gridPane.add(phoneNumberLabel, 0, 5);
        gridPane.add(phoneNumber, 1, 5);

        gridPane.add(addressLabel, 0, 7);
        gridPane.add(address, 1, 7);

        HBox genderBox = new HBox(10);
        genderBox.getChildren().addAll(genderMale, genderFemale);
        gridPane.add(genderLabel, 0, 8);
        gridPane.add(genderBox, 1, 8);

        gridPane.add(termsAndConditions, 1, 9);

        HBox accountAskBox = new HBox(accountAsk, login);
        accountAskBox.setSpacing(5);

        gridPane.add(accountAskBox, 1, 10);

        gridPane.add(registerButton, 1, 11);

    }

    @Override
    protected void registerEvent() {
        super.registerEvent();
        login.setOnMouseClicked(this::login);
        registerButton.setOnAction(this::register);
    }

    private void login(Event event) {
        LoginPage loginPage = new LoginPage(seruputTeh, primaryStage);
        loginPage.scene();
    }

    private void register(ActionEvent event) {
        try {
            if (!termsAndConditions.isSelected()) {
                throw new DataValidationException("Please accept the terms and condition");
            }

            ToggleButton genderToggle = (ToggleButton) genderGroup.getSelectedToggle();
            if (genderToggle == null) {
                throw new DataValidationException(FIELDS_EMPTY);
            }

            userManager().register(
                    email.getText(),
                    username.getText(),
                    password.getText(),
                    confirmPassword.getText(),
                    phoneNumber.getText(),
                    address.getText(),
                    UserGender.fromName(genderToggle.getText()).orElseThrow(),
                    UserRole.USER
            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Registered successfully!");

            ButtonType okButton = new ButtonType("Ok");
            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElseThrow() == okButton) {
                login(event);
            }

        } catch (DataValidationException e) {
            logger.trace(e);
            createAlert("Failed to Register", e.getMessage());
        }
    }

}
