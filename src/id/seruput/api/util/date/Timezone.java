package id.seruput.api.util.date;

public enum Timezone {
    UTC("UTC", 0),
    WIB("WIB", 7),
    WITA("WITA", 8),
    WIT("WIT", 9);

    private final String name;
    private final int offset;

    Timezone(String name, int offset) {
        this.name = name;
        this.offset = offset;
    }

    public String asString() {
        return name;
    }

    public int offset() {
        return offset;
    }

}
