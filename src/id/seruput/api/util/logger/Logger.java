package id.seruput.api.util.logger;

import id.seruput.api.util.date.DateFormatter;

public class Logger {

    private final Class<?> clazz;

    public Logger(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    private void log(LogLevel level, String message) {
        String currThread = Thread.currentThread().getName();
        String currClass = clazz.getName();
        String currTime = DateFormatter.formatDateFromEpoch(System.currentTimeMillis());

        if (currThread.length() > 15) {
            if (currThread.equals("JavaFX Application Thread")) {
                currThread = "JavaFX Thread";
            } else {
                currThread = currThread.substring(0, 15);
            }
        }

        message = String.format(
                "%s %7s [%15s] %-45s : %s", currTime, level.name(), currThread, currClass, message
        );
        if (level == LogLevel.ERROR) {
            System.err.println(message);
            return;
        }
        System.out.println(message);

    }

}
