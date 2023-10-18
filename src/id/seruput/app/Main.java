package id.seruput.app;

import id.seruput.api.SeruputTeh;
import id.seruput.app.window.LoginPage;
import id.seruput.core.SeruputTehCore;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println(startupLogo());
        Application.launch(args);
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

        SeruputTeh seruputTeh = SeruputTehCore.create(password);

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