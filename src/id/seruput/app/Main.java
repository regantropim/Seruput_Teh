package id.seruput.app;

import id.seruput.api.SeruputTeh;
import id.seruput.core.SeruputTehCore;

public class Main {

    private final SeruputTeh seruputTeh;

    public Main(SeruputTeh seruputTeh) {
        this.seruputTeh = seruputTeh;
    }

    public static void main(String[] args) {
        SeruputTeh seruputTeh = SeruputTehCore.create();
        Main main = new Main(seruputTeh);
        main.start();
    }

    public void start() {

    }

    public SeruputTeh seruputTeh() {
        return seruputTeh;
    }

}