package id.seruput.api.util.date;

public class DateFormatter {
    private static final long MILLISECONDS_IN_SECOND = 1000;
    private static final long SECONDS_IN_MINUTE = 60;
    private static final long MINUTES_IN_HOUR = 60;
    private static final long HOURS_IN_DAY = 24;
    private static final long DAYS_IN_YEAR_NON_LEAP = 365;
    private static final long MILLISECONDS_IN_MINUTE = MILLISECONDS_IN_SECOND * SECONDS_IN_MINUTE;
    private static final long MILLISECONDS_IN_HOUR = MILLISECONDS_IN_MINUTE * MINUTES_IN_HOUR;
    private static final long MILLISECONDS_IN_DAY = MILLISECONDS_IN_HOUR * HOURS_IN_DAY;
    private static final long MILLISECONDS_IN_YEAR = MILLISECONDS_IN_DAY * DAYS_IN_YEAR_NON_LEAP;

    private static final int START_YEAR = 1970;

    private static final int[] DAYS_IN_MONTH_NON_LEAP = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    // Accept input as milliseconds
    public static String formatDateFromEpoch(long epochMillis) {
        return formatDateFromEpoch(epochMillis, 0);
    }

    public static String formatDateFromEpoch(long epochMillis, int utcOffset) {
        epochMillis += calculateUtcOffset(utcOffset);
        int year = START_YEAR;

        while (epochMillis >= MILLISECONDS_IN_YEAR) {
            if (isLeapYear(year)) {
                epochMillis -= MILLISECONDS_IN_DAY; // subtract extra day for a leap year
            }
            epochMillis -= MILLISECONDS_IN_YEAR;
            year++;
        }

        int month = 0;
        while (epochMillis >= MILLISECONDS_IN_DAY * DAYS_IN_MONTH_NON_LEAP[month]) {
            epochMillis -= MILLISECONDS_IN_DAY * (isLeapYear(year) && month == 1 ? 29 : DAYS_IN_MONTH_NON_LEAP[month]);
            month++;
        }

        int day = 1 + (int)(epochMillis / MILLISECONDS_IN_DAY);
        epochMillis %= MILLISECONDS_IN_DAY;

        int hour = (int)(epochMillis / MILLISECONDS_IN_HOUR);
        epochMillis %= MILLISECONDS_IN_HOUR;

        int minute = (int)(epochMillis / MILLISECONDS_IN_MINUTE);
        epochMillis %= MILLISECONDS_IN_MINUTE;

        int second = (int)(epochMillis / MILLISECONDS_IN_SECOND);
        epochMillis %= MILLISECONDS_IN_SECOND;

        int millis = (int)epochMillis;

        return String.format("%04d-%02d-%02d %02d:%02d:%02d.%03d", year, month+1, day, hour, minute, second, millis);
    }

    private static long calculateUtcOffset(int utcOffset) {
        return utcOffset * MILLISECONDS_IN_HOUR;
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
}