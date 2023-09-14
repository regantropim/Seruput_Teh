package id.seruput.app;

import id.seruput.api.SeruputTeh;
import id.seruput.core.SeruputTehCore;

public class Main {

    private final SeruputTeh seruputTeh;

    public Main(SeruputTeh seruputTeh) {
        this.seruputTeh = seruputTeh;
    }

    public static void main(String[] args) {

        String password = null;
        if (args[0].startsWith("--password=")) {
            password = args[0].substring(11);
        }
        SeruputTeh seruputTeh = SeruputTehCore.create(password);

        Main main = new Main(seruputTeh);
        main.start();
    }

    public void start() {

    }

    public SeruputTeh seruputTeh() {
        return seruputTeh;
    }

}