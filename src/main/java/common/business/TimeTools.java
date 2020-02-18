package common.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class TimeTools {
    public static boolean overlap(Date start, Date end, Date otherStart, Date otherEnd) {
        return overlap(start.getTime(), end.getTime(), otherStart.getTime(), otherEnd.getTime());
    }

    public static boolean overlap(float start, float end, float otherStart, float otherEnd) {
        return (start < otherEnd && end > otherStart) ||
                (start > otherStart && end > otherEnd) ||
                (otherStart < start && otherEnd > start) ||
                (otherStart < end && otherEnd > end);
    }

    public static boolean overlap(LocalTime start, LocalTime end, LocalTime otherStart, LocalTime otherEnd) {
        return (start.isBefore(otherEnd) && end.isAfter(otherStart)) ||
                (start.isAfter(otherStart) && end.isAfter(otherEnd)) ||
                (otherStart.isBefore(start) && otherEnd.isAfter(start)) ||
                (otherStart.isBefore(end) && otherEnd.isAfter(end));
    }

    public static LocalTime time(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return LocalTime.of(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }


    public static Date stringToDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
    }

    public static LocalTime stringToLocalTime(String time) throws ParseException {
        return LocalTime.parse(time);
    }

    public static String dateToString(Date date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        } catch (Exception e) {
            return "";
        }
    }

}
