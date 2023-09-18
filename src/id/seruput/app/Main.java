package id.seruput.app;

import id.seruput.api.SeruputTeh;
import id.seruput.api.util.logger.Logger;
import id.seruput.app.window.LoginPage;
import id.seruput.core.SeruputTehCore;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    private static final Logger logger = Logger.getLogger(Main.class);

    private static Main INSTANCE = null;

    private SeruputTeh seruputTeh;

    public Main() {

    }

    public static void main(String[] args) {

        System.out.println(startupLogo());
        Application.launch(args);

    }

    public SeruputTeh seruputTeh() {
        return seruputTeh;
    }

    static Main getInstance() {
        if (INSTANCE == null) {
            throw new RuntimeException("Main class not initialized");
        }
        return INSTANCE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String password = null;
        List<String> args = getParameters().getRaw();
        if (!args.isEmpty()) {
            if (args.get(0).startsWith("--password=")) {
                password = args.get(0).substring(11);
            }
        }

        seruputTeh = SeruputTehCore.create(password);

        INSTANCE = this;

        primaryStage.show();

        new LoginPage(seruputTeh, primaryStage).scene();
    }

    private static String startupLogo() {
        return " _____                     _      _____     _                       Written By:\n" +
                "|   __|___ ___ _ _ ___ _ _| |_   |_   _|___| |_       Farrell Alexander Hasudungan Komansilan\n" +
                "|__   | -_|  _| | | . | | |  _|    | | | -_|   |              Muhammad Stiven Pratama\n" +
                "|_____|___|_| |___|  _|___|_|      |_| |___|_|_|                 Reyhan Imanullah\n" +
                "                  |_|       v 0.0.1 - SNAPSHOT                     Ilham Haris\n  ";
    }

}