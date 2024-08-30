package id.seruput.app.window.main;

import id.seruput.api.SeruputTeh;
import id.seruput.api.data.product.Product;
import id.seruput.api.data.transaction.Transaction;
import id.seruput.api.data.transaction.TransactionDetail;
import id.seruput.api.data.user.User;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class PurchaseHistoryScene extends MainWindow {

    private final Label title;
    private final ListView<Transaction> transactionsHistory;

    private final Label selectToViewLabel;

    private final VBox transactionDetailBox;
    private final Label transactionIdLabel;
    private final Label usernameLabel;
    private final Label phoneNumberLabel;
    private final Label addressLabel;
    private final Label totalPriceLabel;
    private final ListView<TransactionDetail> transactionDetailsHistory;

    protected PurchaseHistoryScene(SeruputTeh seruputTeh, Stage primaryStage) {
        super(seruputTeh, primaryStage);
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        User user = currentUser().orElseThrow();

        title = new Label(user.username() + "'s Purchase History");
        title.setFont(Font.font("System", FontWeight.BOLD, 35));

        transactionsHistory = new ListView<>();
        transactionsHistory.setMaxWidth(250);
        transactionsHistory.setMaxHeight(320);
        transactionsHistory.getItems().addAll(transactionManager().fetchTransaction(user));
        transactionsHistory.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Transaction> call(ListView<Transaction> param) {
                return new ListCell<>() {

                    @Override
                    protected void updateItem(Transaction transaction, boolean empty) {
                        super.updateItem(transaction, empty);
                        setText(empty ? "" : transaction.transactionId().asString());
                    }

                };
            }
        });

        selectToViewLabel = new Label("Select a transaction to view details");
        selectToViewLabel.setFont(Font.font("System", FontWeight.BOLD, 13));

        transactionDetailBox = new VBox();

        transactionIdLabel = new Label("Transaction ID :");
        transactionIdLabel.setFont(Font.font("System", FontWeight.BOLD, 13));

        usernameLabel = new Label("Username : " + user.username());
        usernameLabel.setFont(Font.font("System", FontWeight.BOLD, 13));

        phoneNumberLabel = new Label("Phone Number : " + user.phone());
        addressLabel = new Label("Address : " + user.address());
        totalPriceLabel = new Label("Total Price : ");

        transactionDetailsHistory = new ListView<>();
        transactionDetailsHistory.setMaxWidth(350);
        transactionDetailsHistory.setMaxHeight(150);
        transactionDetailsHistory.setCellFactory(new Callback<>() {

            @Override
            public ListCell<TransactionDetail> call(ListView<TransactionDetail> param) {
                return new ListCell<>() {

                    @Override
                    protected void updateItem(TransactionDetail detail, boolean empty) {
                        super.updateItem(detail, empty);
                        if (detail == null)
                            setText("");
                        else productManager().fetchProduct(detail.productId())
                             .ifPresentOrElse(
                                     product -> setText(empty ? "" : detail.quantity() + "x " + product.name() + " (Rp." + product.price() + ")"),
                                     () -> setText(empty ? "" : "Product not found: " + detail.productId())
                             );
                    }

                };
            }

        });

        transactionDetailBox.getChildren().addAll(
                transactionIdLabel, usernameLabel,
                phoneNumberLabel, addressLabel, totalPriceLabel,
                transactionDetailsHistory
        );

        VBox.setMargin(transactionDetailsHistory, new Insets(10, 0, 0, 0));

        transactionDetailBox.setVisible(false);
        transactionDetailBox.setSpacing(10);

    }

    @Override
    protected void setup() {
        gridPane.add(title, 0, 1, 2, 1);
        GridPane.setValignment(title, VPos.TOP);
        GridPane.setMargin(title, new Insets(0, 0, 5, 15));

        gridPane.add(transactionsHistory, 0, 2);
        GridPane.setMargin(transactionsHistory, new Insets(0, 15, 15, 15));

        gridPane.add(selectToViewLabel, 1, 2);
        GridPane.setValignment(selectToViewLabel, VPos.TOP);

        gridPane.add(transactionDetailBox, 1, 2, 2, 7);

    }

    @Override
    protected void registerEvent() {
        super.registerEvent();
        transactionsHistory.getSelectionModel().selectedItemProperty().addListener(this::onProductSelection);
    }

    private void onProductSelection(ObservableValue<? extends Transaction> observableValue, Transaction oldValue, Transaction newValue) {
        if (newValue != null) {
            List<TransactionDetail> details = transactionManager().fetchTransactionDetail(newValue);
            transactionDetailsHistory.getItems().clear();
            transactionDetailsHistory.getItems().addAll(details);
            transactionDetailsHistory.refresh();

            transactionIdLabel.setText("Transaction ID : " + newValue.transactionId().asString());
            totalPriceLabel.setText("Total Price : Rp." + calculateTotalPrice(details));
            transactionDetailBox.setVisible(true);
            selectToViewLabel.setVisible(false);
        } else {
            selectToViewLabel.setVisible(true);
            transactionDetailBox.setVisible(false);
        }
    }

    private long calculateTotalPrice(List<TransactionDetail> details) {
        long totalPrice = 0;
        for (TransactionDetail detail : details) {
            Product product = productManager().fetchProduct(detail.productId()).orElseThrow();
            totalPrice += product.price() * detail.quantity();
        }
        return totalPrice;
    }

}
