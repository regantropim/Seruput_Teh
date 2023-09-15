package id.seruput.app;

import id.seruput.api.SeruputTeh;
import id.seruput.api.util.logger.Logger;
import id.seruput.app.menu.LoginPage;
import id.seruput.core.SeruputTehCore;
import javafx.application.Application;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    private static Main INSTANCE = null;

    private final SeruputTeh seruputTeh;

    public Main(SeruputTeh seruputTeh) {
        this.seruputTeh = seruputTeh;
    }

    public static void main(String[] args) {

        System.out.println(startupLogo());

        String password = null;
        if (args[0].startsWith("--password=")) {
            password = args[0].substring(11);
        }
        SeruputTeh seruputTeh = SeruputTehCore.create(password);

        INSTANCE = new Main(seruputTeh);

        Application.launch(LoginPage.class, args);

    }

    public SeruputTeh seruputTeh() {
        return seruputTeh;
    }

    private static String startupLogo() {
        return " _____                     _      _____     _                       Written By:\n" +
                "|   __|___ ___ _ _ ___ _ _| |_   |_   _|___| |_       Farrell Alexander Hasudungan Komansilan\n" +
                "|__   | -_|  _| | | . | | |  _|    | | | -_|   |              Muhammad Stiven Pratama\n" +
                "|_____|___|_| |___|  _|___|_|      |_| |___|_|_|                 Reyhan Imanullah\n" +
                "                  |_|       v 0.0.1 - SNAPSHOT                      Ilham Haris\n  ";
    }

    static Main getInstance() {
        if (INSTANCE == null) {
            throw new RuntimeException("Main class not initialized");
        }
        return INSTANCE;
    }

}