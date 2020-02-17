package common.business;

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
}
