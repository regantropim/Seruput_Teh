package id.seruput.app.window.main;

import id.seruput.api.SeruputTeh;
import id.seruput.api.data.product.Product;
import id.seruput.api.data.user.User;
import id.seruput.api.exception.DataValidationException;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HomeScene extends MainWindow {
    private final Label title;
    private final Label welcomeUserLabel;
    private final Label productNameLabel;
    private final Label productDetailLabel;
    private final Label priceLabel;

    private HBox quantityBox;
    private Label quantityLabel;
    private Label totalPriceLabel;
    private Spinner<Integer> quantitySpinner;


    private Button addCartButton;

    private final ListView<Product> list;

    public HomeScene(SeruputTeh seruputTeh, Stage primaryStage) {
        super(seruputTeh, primaryStage);
        primaryStage.setTitle("Home");

        gridPane.setVgap(10);

        title = new Label("SeRuput Teh");
        title.setStyle("-fx-font-size: 35px; -fx-font-weight: bold;");

        list = new ListView<>();
        list.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Product> call(ListView<Product> param) {
                return new ListCell<>() {

                    @Override
                    protected void updateItem(Product item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : item.name());
                    }

                };
            }
        });

        list.getItems().addAll(seruputTeh.productManager().products());

        welcomeUserLabel = new Label("Welcome, Ilham");
        welcomeUserLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        productNameLabel = new Label("Product Name");
        productNameLabel.setVisible(false);
        productNameLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        productDetailLabel = new Label("Select a product to view");
        productDetailLabel.setWrapText(true);
        productDetailLabel.setMaxWidth(350);

        priceLabel = new Label("Price: Rp.");
        priceLabel.setVisible(false);

        if (!isAdmin) {
            quantityLabel = new Label("Quantity : ");
            quantityLabel.setVisible(false);

            totalPriceLabel = new Label("Total Price: Rp.");
            totalPriceLabel.setVisible(false);

            quantitySpinner = new Spinner<>();
            quantitySpinner.setVisible(false);
            quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));

            addCartButton = new Button("Add to Cart");
            addCartButton.setMinWidth(160);
            addCartButton.setVisible(false);

            quantityBox = new HBox(10);
            quantityBox.getChildren().addAll(quantityLabel, quantitySpinner, totalPriceLabel);
        }
    }

    @Override
    public void setup() {
        title.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(title, 0, 1);
        GridPane.setMargin(title, new Insets(15, 15, 5, 15));

        list.setMinWidth(400);
        list.setPrefHeight(400);
        gridPane.add(list, 0, 2, 1, 7);
        GridPane.setMargin(list, new Insets(0, 15, 15, 15));

        gridPane.add(welcomeUserLabel, 1, 2);
        GridPane.setHalignment(welcomeUserLabel, HPos.LEFT);
        GridPane.setValignment(welcomeUserLabel, VPos.TOP);

        gridPane.add(productNameLabel, 1, 2);
        GridPane.setHalignment(productNameLabel, HPos.LEFT);
        GridPane.setValignment(productNameLabel, VPos.TOP);

        gridPane.add(productDetailLabel, 1, 3, 4, 1);
        GridPane.setHalignment(productDetailLabel, HPos.LEFT);
        GridPane.setValignment(productDetailLabel, VPos.TOP);

        gridPane.add(priceLabel, 1, 4);
        GridPane.setHalignment(priceLabel, HPos.LEFT);
        GridPane.setValignment(priceLabel, VPos.TOP);

        if (!isAdmin) {
            gridPane.add(quantityBox, 1, 5);

            gridPane.add(addCartButton, 1, 6);
            GridPane.setHalignment(addCartButton, HPos.LEFT);
            GridPane.setValignment(addCartButton, VPos.TOP);
        }

    }

    @Override
    protected void registerEvent() {
        super.registerEvent();

        if (!isAdmin) {
            addCartButton.setOnMouseClicked(this::onAddToCart);
            quantitySpinner.valueProperty().addListener(this::onQuantityChange);
        }

        list.getSelectionModel().selectedItemProperty().addListener(this::onProductSelected);
    }

    private void onAddToCart(MouseEvent mouseEvent) {
        User user = currentUser().orElseThrow();
        Product product = list.getSelectionModel().getSelectedItem();
        int quantity = quantitySpinner.getValue();

        try {
            cartManager().addCart(user.id(), product.productId(), quantity);
            createAlert(Alert.AlertType.INFORMATION, "Information", "Added to Cart", null);
            quantitySpinner.getValueFactory().setValue(1);
        } catch (DataValidationException e) {
            createAlert("Failed to add to cart", e.getMessage());
        }
    }

    private void onQuantityChange(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
        if (newValue == 1) {
            totalPriceLabel.setVisible(false);
            return;
        }

        if (list.getSelectionModel().getSelectedItem() != null) {
            Product product = list.getSelectionModel().getSelectedItem();
            totalPriceLabel.setText("Total Price: Rp. " + product.price() * newValue);
            totalPriceLabel.setVisible(true);
        }

    }

    private void onProductSelected(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
        if (newValue != null) {
            productNameLabel.setVisible(true);
            welcomeUserLabel.setVisible(false);

            productNameLabel.setText(newValue.name());
            productDetailLabel.setText(newValue.description());

            priceLabel.setVisible(true);
            priceLabel.setText("Price: Rp. " + newValue.price());

            if (!isAdmin) {
                quantitySpinner.getValueFactory().setValue(1);

                quantityLabel.setVisible(true);
                quantitySpinner.setVisible(true);
                addCartButton.setVisible(true);

                totalPriceLabel.setVisible(false);
            }

        } else {
            welcomeUserLabel.setVisible(true);
            productNameLabel.setVisible(false);
            priceLabel.setVisible(false);

            if (!isAdmin) {
                quantityLabel.setVisible(false);
                totalPriceLabel.setVisible(false);
                quantitySpinner.setVisible(false);
                addCartButton.setVisible(false);
            }

        }
    }

    @Override
    protected void onHomeMenuItemClick() {
        homeMenuItem.setOnAction(event -> {}); // ignore since we are already in home
    }

}