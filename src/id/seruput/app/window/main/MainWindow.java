package id.seruput.app.window.main;

import id.seruput.api.SeruputTeh;
import id.seruput.app.window.Window;
import id.seruput.app.window.LoginPage;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class MainWindow extends Window {

    protected final MenuBar menuBar;

    protected final Menu homeMenu;
    protected final MenuItem homeMenuItem;

    protected final Menu cartMenu;
    protected final MenuItem cartMenuItem;

    protected final Menu profileMenu;
    protected final MenuItem purchaseHistoryMenuItem;
    protected final MenuItem logoutMenuItem;

    protected boolean isAdmin;
    
    protected MainWindow(SeruputTeh seruputTeh, Stage primaryStage) {
        super(seruputTeh, primaryStage);
        gridPane.setVgap(10);

        menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        menuBar.setMaxWidth(Double.MAX_VALUE);

        homeMenu = new Menu("Home");
        homeMenuItem = new MenuItem("Home Page");

        cartMenu = new Menu("Cart");
        cartMenuItem = new MenuItem("My Cart");

        profileMenu = new Menu("Profile");
        purchaseHistoryMenuItem = new MenuItem("Purchase History");
        logoutMenuItem = new MenuItem("Logout");

        homeMenu.getItems().add(homeMenuItem);
        cartMenu.getItems().add(cartMenuItem);
        profileMenu.getItems().addAll(purchaseHistoryMenuItem, logoutMenuItem);

        menuBar.getMenus().addAll(homeMenu, cartMenu, profileMenu);
    }

    @Override
    public void scene() {

        logger.info("Setting up scene");
        setup();

        logger.info("Setting up MenuBar");
        gridPane.add(menuBar, 0, 0);
        GridPane.setColumnSpan(menuBar, maxColumn());
        GridPane.setValignment(menuBar, VPos.TOP);

        logger.info("Setting scene to stage");
        primaryStage.setScene(scene);

        registerEvent();

        logger.info("Showing stage");
        primaryStage.show();

    }

    @Override
    protected void registerEvent() {
        super.registerEvent();

        this.onHomeMenuItemClick();
        this.onLogoutMenuItemClick();
    }

    protected void onHomeMenuItemClick() {
        homeMenuItem.setOnAction(event -> new HomeScene(seruputTeh, primaryStage).scene());
    }
    
    protected void onLogoutMenuItemClick() {
        logoutMenuItem.setOnAction(event -> new LoginPage(seruputTeh, primaryStage).scene());
    }

    private int maxColumn() {
        int maxColumns = 0;

        for (Node node : gridPane.getChildren()) {
            Integer colIndex = GridPane.getColumnIndex(node);
            if(colIndex != null && colIndex + 1 > maxColumns) {
                maxColumns = colIndex + 1;
            }
        }

        return maxColumns;
    }
    
}
