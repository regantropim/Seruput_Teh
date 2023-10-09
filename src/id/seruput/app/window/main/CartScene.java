package id.seruput.app.window.main;

import id.seruput.api.SeruputTeh;
import id.seruput.api.data.cart.Cart;
import id.seruput.api.data.product.Product;
import id.seruput.api.data.user.User;
import id.seruput.app.window.popup.PurchaseConfirmationWindow;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class CartScene extends MainWindow {

    private final Label title;
    private final Label welcomeUserLabel;
    private final Label productNameLabel;
    private final Label productDetailLabel;
    private final Label priceLabel;
    private final Label quantityLabel;
    private final Label totalPriceLabel;
    private final Label totalPriceSingleLabel;
    private final Label orderInformationLabel;
    private final Label usernameLabel;
    private final Label phoneNumberLabel;
    private final Label addressLabel;
    private final Label emptyCartTitleLabel;
    private final Label emptyCartDetailLabel;

    private final HBox quantityBox;
    private final HBox editCartBox;

    private final Button updateButton;
    private final Button removeButton;

    private final Button purchaseButton;
    private final Spinner<Integer> quantitySpinner;

    private final ListView<Cart> list;

    protected CartScene(SeruputTeh seruputTeh, Stage primaryStage) {
        super(seruputTeh, primaryStage);

        gridPane.setVgap(10);

        User currentUser = currentUser().orElseThrow();

        title = new Label(currentUser.username() + "'s Cart");
        title.setStyle("-fx-font-size: 35px; -fx-font-weight: bold;");

        list = new ListView<>();
        list.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Cart> call(ListView<Cart> param) {
                return new ListCell<>() {

                    @Override
                    protected void updateItem(Cart item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : formatProduct(item));
                    }

                };
            }
        });

        List<Cart> cart = seruputTeh.cartManager().findCart(currentUser.id());

        list.getItems().addAll(cart);

        welcomeUserLabel = new Label("Welcome, " + currentUser.username());
        welcomeUserLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        welcomeUserLabel.setVisible(!cart.isEmpty());

        productNameLabel = new Label("Product Name");
        productNameLabel.setVisible(false);


        emptyCartTitleLabel = new Label("No item in cart");
        emptyCartTitleLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        emptyCartTitleLabel.setVisible(cart.isEmpty());

        emptyCartDetailLabel = new Label("Consider adding one");
        emptyCartDetailLabel.setVisible(cart.isEmpty());

        productDetailLabel = new Label("Select a product to add and remove");
        productDetailLabel.setMaxWidth(350);
        productDetailLabel.setWrapText(true);
        productDetailLabel.setVisible(!cart.isEmpty());

        priceLabel = new Label("Price: ");
        priceLabel.setVisible(false);

        quantityLabel = new Label("Quantity : ");
        quantityLabel.setVisible(false);

        totalPriceLabel = new Label("Total: Rp. " + seruputTeh.cartManager().calculateTotalPrice(currentUser.id(), productManager()));

        totalPriceSingleLabel = new Label("Total: Rp. ");
        totalPriceSingleLabel.setVisible(false);

        orderInformationLabel = new Label("Order Information");
        orderInformationLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        usernameLabel = new Label("Username: " + currentUser.username());
        phoneNumberLabel = new Label("Phone Number: " + currentUser.phone());

        addressLabel = new Label("Address: " + currentUser.address());
        addressLabel.setWrapText(true);
        addressLabel.setMaxWidth(300);

        editCartBox = new HBox(10);

        updateButton = new Button("Update Cart");
        updateButton.setMinWidth(160);
        updateButton.setVisible(false);

        removeButton = new Button("Remove from Cart");
        removeButton.setMinWidth(160);
        removeButton.setVisible(false);

        editCartBox.getChildren().addAll(updateButton, removeButton);

        purchaseButton = new Button("Make Purchase");
        purchaseButton.setMinWidth(160);
        purchaseButton.setVisible(!cart.isEmpty());

        quantitySpinner = new Spinner<>(Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        quantitySpinner.setVisible(false);

        quantityBox = new HBox(10);
        quantityBox.getChildren().addAll(quantityLabel, quantitySpinner, totalPriceSingleLabel);
    }

    @Override
    protected void setup() {
        title.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(title, 0, 1);
        GridPane.setMargin(title, new Insets(0, 0, 5, 15));
        GridPane.setValignment(title, VPos.TOP);

        list.setMinWidth(350);
        list.setPrefHeight(180);
        gridPane.add(list, 0, 2, 1, 6);
        GridPane.setMargin(list, new Insets(0, 15, 15, 15));

        gridPane.add(welcomeUserLabel, 1, 2);
        gridPane.add(productNameLabel, 1, 2);
        gridPane.add(emptyCartTitleLabel, 1, 2);

        gridPane.add(productDetailLabel, 1, 3);
        gridPane.add(emptyCartDetailLabel, 1, 3);

        gridPane.add(quantityBox, 1, 4);

        gridPane.add(editCartBox, 1, 6);

        gridPane.add(totalPriceLabel, 0, 8);
        GridPane.setMargin(totalPriceLabel, new Insets(0, 0, 0, 15));

        gridPane.add(orderInformationLabel, 0, 9);
        GridPane.setMargin(orderInformationLabel, new Insets(0, 0, 0, 15));

        gridPane.add(usernameLabel, 0, 10);
        GridPane.setMargin(usernameLabel, new Insets(0, 0, 0, 15));

        gridPane.add(phoneNumberLabel, 0, 11);
        GridPane.setMargin(phoneNumberLabel, new Insets(0, 0, 0, 15));

        gridPane.add(addressLabel, 0, 12);
        GridPane.setMargin(addressLabel, new Insets(0, 0, 0, 15));

        gridPane.add(purchaseButton, 0, 13);
        GridPane.setMargin(purchaseButton, new Insets(0, 0, 0, 15));

    }

    @Override
    protected void registerEvent() {
        super.registerEvent();
        purchaseButton.setOnMouseClicked(this::onProductPurchase);
        removeButton.setOnMouseClicked(this::onProductRemove);
        updateButton.setOnMouseClicked(this::onProductUpdate);
        quantitySpinner.valueProperty().addListener(this::onQuantityChange);
        list.getSelectionModel().selectedItemProperty().addListener(this::onProductSelected);
    }

    protected void onProductRemove(MouseEvent event) {
        Cart cart = list.getSelectionModel().getSelectedItem();
        if (cart != null) {
            seruputTeh.cartManager().removeCart(cart);
            list.getItems().remove(cart);

            totalPrice();
        }

        // check if cart is empty
        if (list.getItems().isEmpty()) {
            emptyCartTitleLabel.setVisible(true);
            emptyCartDetailLabel.setVisible(true);
            productNameLabel.setVisible(false);
            productDetailLabel.setVisible(false);
            priceLabel.setVisible(false);
            quantityLabel.setVisible(false);
            quantitySpinner.setVisible(false);
            updateButton.setVisible(false);
            removeButton.setVisible(false);
        }

        list.getSelectionModel().clearSelection();
        createAlert(Alert.AlertType.INFORMATION, "Information", "Deleted from cart", null);
    }

    protected void onProductUpdate(MouseEvent event) {
        Cart cart = list.getSelectionModel().getSelectedItem();
        int spinnerValue = quantitySpinner.getValue();

        if (spinnerValue == 0) {
            createAlert("Not a valid amount", null);
            return;
        }

        if (cart != null) {
            int newQuantity = cart.quantity() + spinnerValue;
            if (spinnerValue < 0) {
                if (newQuantity > 0) {
                    cart.quantity(newQuantity);
                    seruputTeh.cartManager().updateCart(cart);
                    list.refresh();
                } else if (newQuantity == 0) {
                    onProductRemove(event);
                } else {
                    createAlert("Not a valid amount", null);
                    return;
                }
            } else {
                cart.quantity(newQuantity);
                seruputTeh.cartManager().updateCart(cart);
                list.refresh();
            }
        }

        totalPrice();
        quantitySpinner.getValueFactory().setValue(1);
        list.getSelectionModel().clearSelection();
        onProductSelected(null, null, null);

        createAlert(Alert.AlertType.INFORMATION, "Message", "Updated Cart", null);
    }

    private void onQuantityChange(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
        if (newValue == 0) {
            totalPriceSingleLabel.setText("Total: Rp. 0");
            return;
        }

        Cart cart = list.getSelectionModel().getSelectedItem();
        if (cart != null) {
            Product product = cart.product(productManager()).orElseThrow();
            totalPriceSingleLabel.setText("Total: Rp. " + product.price() * newValue);
        }

    }

    protected void onProductSelected(ObservableValue<? extends Cart> observable, Cart oldValue, Cart newValue) {
        if (newValue != null) {
            Product product = newValue.product(productManager()).orElseThrow();

            welcomeUserLabel.setVisible(false);

            productNameLabel.setText(product.name());
            productNameLabel.setVisible(true);

            productDetailLabel.setText(product.description());
            productDetailLabel.setVisible(true);

            priceLabel.setText("Price: Rp. " + product.price());
            priceLabel.setVisible(true);

            quantityLabel.setVisible(true);

            quantitySpinner.setVisible(true);
            quantitySpinner.getValueFactory().setValue(1);

            totalPriceSingleLabel.setText("Total: Rp. " + product.price());
            totalPriceSingleLabel.setVisible(true);

            updateButton.setVisible(true);
            removeButton.setVisible(true);

            orderInformationLabel.setVisible(true);

            usernameLabel.setVisible(true);

            phoneNumberLabel.setVisible(true);

            addressLabel.setVisible(true);
        } else {
            productNameLabel.setVisible(false);
            productDetailLabel.setVisible(false);
            priceLabel.setVisible(false);
            quantityLabel.setVisible(false);
            quantitySpinner.setVisible(false);
            totalPriceSingleLabel.setVisible(false);
            updateButton.setVisible(false);
            removeButton.setVisible(false);
        }
    }

    private void onProductPurchase(MouseEvent event) {
        Stage popup = new Stage();
        PurchaseConfirmationWindow window = new PurchaseConfirmationWindow(seruputTeh, popup, () -> {
            new CartScene(seruputTeh, primaryStage).scene();
        });
        window.scene();
    }

    private String formatProduct(Cart cart) {
        Product product = cart.product(productManager()).orElseThrow();
        return String.format("%dx %s (Rp. %d)", cart.quantity(), product.name(), product.price() * cart.quantity());
    }

    private void totalPrice() {
        totalPriceLabel.setText("Total: Rp. " + seruputTeh.cartManager().calculateTotalPrice(currentUser().orElseThrow().id(), productManager()));
    }

}
