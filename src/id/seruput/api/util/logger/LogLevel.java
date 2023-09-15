package id.seruput.api.util.logger;

public enum LogLevel {
    DEBUG,
    INFO,
    WARN("\\u001B[33m"),
    ERROR("\\033[0;31m");

    private final String color;

    LogLevel() {
        this.color = "";
    }

    LogLevel(String color) {
        this.color = color;
    }

    public String color() {
        return color;
    }

}
