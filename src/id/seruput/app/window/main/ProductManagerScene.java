package id.seruput.app.window.main;

import id.seruput.api.SeruputTeh;
import id.seruput.api.data.product.Product;
import id.seruput.api.data.user.User;
import id.seruput.api.exception.DataValidationException;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProductManagerScene extends MainWindow {

    private final Label title;
    private final Label welcomeMessage;
    private final Label productDetail;
    private final Label price;
    private final Button addProductButton;

    /* Label */
    private final HBox productEditBox;

    private final Button productUpdateButton;
    private final Button productRemoveButton;

    /* End of Label */

    /* Add Product */
    private final VBox addProductBox;

    private final Label productNameLabel;
    private final Label productPriceLabel;
    private final Label productDescriptionLabel;

    private final HBox productButtonBox;
    private final Button addProduct;
    private final Button addProductBack;

    private final TextField productName;
    private final TextField productPrice;
    private final TextArea productDescription;

    /* End of Add Product */

    /* Remove Product */
    private final VBox removeProductBox;
    private final Label removeProductLabelConfirm;

    private final HBox removeProductButtonBox;

    private final Button removeProductConfirmButton;
    private final Button removeProductCancelButton;
    /* End of Remove Product */

    /* Update Product */
    private final VBox updateProductBox;

    private final HBox updateProductUpdateButtonBox;

    private final Label updateProductLabel;

    private final TextField updateProductPrice;

    private final Button updateProductConfirmButton;
    private final Button updateProductCancelButton;
    /* End of Update Product */

    private final ListView<Product> list;

    protected ProductManagerScene(SeruputTeh seruputTeh, Stage primaryStage) {
        super(seruputTeh, primaryStage);

        gridPane.setVgap(10);

        User user = currentUser().orElseThrow();

        title = new Label("Manage Products");
        title.setFont(Font.font("System", FontWeight.BOLD, 35));

        welcomeMessage = new Label("Welcome, " + user.username());
        welcomeMessage.setFont(Font.font("System", FontWeight.BOLD, 13));

        productDetail = new Label("Select a product to Update");
        productDetail.setWrapText(true);
        productDetail.setMaxWidth(400);

        price = new Label("Price: ");
        price.setVisible(false);

        addProductButton = new Button("Add Product");

        /* Label */

        productEditBox = new HBox(10);
        productEditBox.setVisible(false);

        productUpdateButton = new Button("Update Product");
        productRemoveButton = new Button("Remove Product");

        productEditBox.getChildren().addAll(productUpdateButton, productRemoveButton);

        /* End of Label */

        /* Add Product Box */
        addProductBox = new VBox(10);
        addProductBox.managedProperty().bind(addProductBox.visibleProperty());
        addProductBox.setMaxWidth(400);

        productNameLabel = new Label("Input Product Name");
        productNameLabel.setFont(Font.font("System", FontWeight.BOLD, 13));

        productPriceLabel = new Label("Input Product Price");
        productPriceLabel.setFont(Font.font("System", FontWeight.BOLD, 13));


        productDescriptionLabel = new Label("Input Product Description...");
        productDescriptionLabel.setFont(Font.font("System", FontWeight.BOLD, 13));


        productName = new TextField();
        productName.setPromptText("Input product name");

        productPrice = new TextField();
        productPrice.setPromptText("Input product price");

        productDescription = new TextArea();
        productDescription.setPromptText("Input product description");
        productDescription.setPrefHeight(200);

        productButtonBox = new HBox(10);
        addProduct = new Button("Add Product");
        addProductBack = new Button("Back");

        productButtonBox.getChildren().addAll(addProduct, addProductBack);

        addProductBox.getChildren().addAll(productNameLabel, productName, productPriceLabel, productPrice, productDescriptionLabel, productDescription, productButtonBox);

        addProductBox.setVisible(false);
        /* End of Add Product Box */

        /* Remove Product */
        removeProductBox = new VBox(10);
        removeProductBox.managedProperty().bind(removeProductBox.visibleProperty());
        removeProductBox.setVisible(false);

        removeProductLabelConfirm = new Label("Are you sure, you want to remove this product?");
        removeProductLabelConfirm.setFont(Font.font("System", FontWeight.BOLD, 13));

        removeProductButtonBox = new HBox(10);

        removeProductConfirmButton = new Button("Remove Product");
        removeProductCancelButton = new Button("Back");

        removeProductButtonBox.getChildren().addAll(removeProductConfirmButton, removeProductCancelButton);
        removeProductBox.getChildren().addAll(removeProductLabelConfirm, removeProductButtonBox);
        /* End of Remove Product */

        /* Update Product */
        updateProductBox = new VBox(10);
        updateProductBox.managedProperty().bind(updateProductBox.visibleProperty());
        updateProductBox.setVisible(false);
        updateProductBox.setMaxWidth(400);

        updateProductLabel = new Label("Update Product");
        updateProductLabel.setFont(Font.font("System", FontWeight.BOLD, 13));

        updateProductUpdateButtonBox = new HBox(10);

        updateProductPrice = new TextField();
        updateProductPrice.setPromptText("Input new price");

        updateProductConfirmButton = new Button("Update Product");
        updateProductCancelButton = new Button("Back");

        updateProductUpdateButtonBox.getChildren().addAll(updateProductConfirmButton, updateProductCancelButton);
        updateProductBox.getChildren().addAll(updateProductLabel, updateProductPrice, updateProductUpdateButtonBox);
        /* End of Update Product */

        list = new ListView<>();
        list.setPrefWidth(400);
        list.setMinHeight(400);

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

        list.getItems().addAll(productManager().products());

    }

    @Override
    protected void setup() {

        gridPane.add(title, 0, 1);
        GridPane.setMargin(title, new Insets(0, 0, 0, 15));

        gridPane.add(list, 0, 2, 1, 8);
        GridPane.setMargin(list, new Insets(0, 15, 15, 15));

        gridPane.add(welcomeMessage, 1, 2);

        gridPane.add(productDetail, 1, 3);

        gridPane.add(price, 1, 4);

        gridPane.add(addProductButton, 1, 5);

        gridPane.add(addProductBox, 1, 5, 2, 4);
        gridPane.add(removeProductBox, 1, 5, 2, 4);
        gridPane.add(updateProductBox, 1, 5, 2, 4);

        gridPane.add(productEditBox, 1, 6);
    }

    @Override
    protected void registerEvent() {
        super.registerEvent();
        list.getSelectionModel().selectedItemProperty().addListener(this::onProductSelected);

        /* Add Product */
        addProductButton.setOnAction(this::onProductCreate);
        addProductBack.setOnAction(this::onProductCreateBack);
        addProduct.setOnAction(this::onProductCreateConfirm);
        productPrice.textProperty().addListener((observable, oldValue, newValue) -> textFieldNumber(observable, oldValue, newValue, productPrice));
        /* End of Add Product */

        /* Remove Product */
        productRemoveButton.setOnAction(this::onRemoveProduct);
        removeProductConfirmButton.setOnAction(this::onRemoveProductConfirm);
        removeProductCancelButton.setOnAction(this::onRemoveProductBack);
        /* End of Remove Product */

        /* Update Product */
        productUpdateButton.setOnAction(this::onProductUpdate);
        updateProductConfirmButton.setOnAction(this::onProductUpdateConfirm);
        updateProductCancelButton.setOnAction(this::onProductUpdateBack);
        updateProductPrice.textProperty().addListener((observable, oldValue, newValue) -> textFieldNumber(observable, oldValue, newValue, updateProductPrice));
        /* End of Update Product */
    }

    protected void onProductSelected(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
        if (newValue != null) {
            productDetail.setText(newValue.description());
            productDetail.setVisible(true);

            price.setText("Price: Rp. " + newValue.price());
            price.setVisible(true);

            productEditBox.setVisible(!addProductBox.isVisible());
            addProductButton.setVisible(!addProductBox.isVisible());

            removeProductBox.setVisible(false);
            updateProductBox.setVisible(false);

        } else {
            productDetail.setText("Select a product to view");
            productDetail.setVisible(true);

            price.setVisible(false);
            productEditBox.setVisible(false);
        }
    }

    /* Remove Product */

    protected void onRemoveProduct(ActionEvent event) {
        Product product = list.getSelectionModel().getSelectedItem();

        if (product != null) {
            removeProductBox.setVisible(true);
            addProductButton.setVisible(false);
            productEditBox.setVisible(false);
        } else {
            createAlert("Select a product to remove", null);
        }
    }

    protected void onRemoveProductConfirm(ActionEvent event) {
        Product product = list.getSelectionModel().getSelectedItem();

        if (product != null) {
            try {
                productManager().removeProduct(product);
                list.getItems().remove(product);
                createAlert(Alert.AlertType.INFORMATION, "Message", "Product removed successfully", null);
            } catch (RuntimeException e) {
                createAlert("Failed to remove product", null);
            }
        }

        list.getSelectionModel().clearSelection();
        onRemoveProductBack(event);
    }

    protected void onRemoveProductBack(ActionEvent mouseEvent) {
        removeProductBox.setVisible(false);
        addProductButton.setVisible(true);

        Product product = list.getSelectionModel().getSelectedItem();
        if (product != null) {
            productEditBox.setVisible(true);
        }
    }

    /* End of Remove Product */

    /* Add Product */

    protected void onProductCreate(ActionEvent mouseEvent) {
        addProductBox.setVisible(true);
        addProductButton.setVisible(false);
        productEditBox.setVisible(false);
    }

    protected void onProductCreateConfirm(ActionEvent event) {
        String name = productName.getText();
        int price = Integer.parseInt(productPrice.getText());
        String description = productDescription.getText();

        try {
            Product product = productManager().addProduct(name, price, description);
            list.getItems().add(product);
            list.refresh();
        } catch (RuntimeException e) {
            createAlert("Failed to add product", null);
        } catch (DataValidationException e) {
            createAlert(e.getMessage(), null);
        }

        createAlert(Alert.AlertType.INFORMATION, "Message", "Product added successfully", null);

        onProductCreateBack(event);
    }

    protected void onProductCreateBack(ActionEvent event) {
        addProductBox.setVisible(false);
        addProductButton.setVisible(true);

        productName.clear();
        productPrice.clear();
        productDescription.clear();

        Product product = list.getSelectionModel().getSelectedItem();
        if (product != null) {
            productEditBox.setVisible(true);
        }
    }

    /* End of Add Product */

    /* Product Update */

    protected void onProductUpdate(ActionEvent event) {
        Product product = list.getSelectionModel().getSelectedItem();
        if (product != null) {
            updateProductBox.setVisible(true);
            addProductButton.setVisible(false);
            productEditBox.setVisible(false);
        } else {
            createAlert("Select a product to update", null);
        }
    }

    protected void onProductUpdateConfirm(ActionEvent event) {
        Product product = list.getSelectionModel().getSelectedItem();
        int price = Integer.parseInt(updateProductPrice.getText());
        if (product != null) {
            long oldPrice = product.price();
            product.price(price);
            try {
                productManager().updateProduct(product);
            } catch (RuntimeException e) {
                product.price(oldPrice); // rollback
                createAlert("Failed to update product", null);
                return;
            }
            createAlert(Alert.AlertType.INFORMATION, "Message", "Product updated successfully", null);
            this.price.setText("Price: Rp. " + list.getSelectionModel().getSelectedItem().price());
        } else {
            createAlert("Select a product to update", null);
        }
        list.refresh();
        onProductUpdateBack(event);
    }

    protected void onProductUpdateBack(ActionEvent event) {
        updateProductBox.setVisible(false);
        addProductButton.setVisible(true);
        productEditBox.setVisible(true);

        updateProductPrice.clear();
    }

    private void textFieldNumber(ObservableValue<? extends String> observable, String oldValue, String newValue, TextField field) {
        if (newValue != null && !newValue.isEmpty()) {
            try {
                Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                logger.error("Failed to parse number: " + newValue);
                field.setText(oldValue);
            }
        }
    }

    /* End of Update Product */

}
