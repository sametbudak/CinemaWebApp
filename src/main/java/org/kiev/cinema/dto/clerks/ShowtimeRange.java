package org.kiev.cinema.dto.clerks;

import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ShowtimeRange {
    private static final double MIN = 9.00;
    private static final double MAX = 23.00;
    private double fromTime;
    private double toTime;
    public static final Map<Integer, ShowtimeRange> SHOWTIME_RANGE_MAP;
    static {
        Map<Integer, ShowtimeRange> temp = new HashMap<>(8, 1.1f);
        temp.put(0, new ShowtimeRange(9.00, 12.00));
        temp.put(1, new ShowtimeRange(12.00, 15.00));
        temp.put(2, new ShowtimeRange(15.00, 18.00));
        temp.put(3, new ShowtimeRange(18.00, 21.00));
        temp.put(4, new ShowtimeRange(21.00, 23.00));
        temp.put(5, new ShowtimeRange(9.00, 15.00));
        temp.put(6, new ShowtimeRange(15.00, 23.00));
        //temp.put(7, new ShowtimeRange(9.00, 23.00));
        SHOWTIME_RANGE_MAP = temp;
    }

    public ShowtimeRange(double fromTime, double toTime) {
        setTime(fromTime, toTime);
    }

    public double getFromTime() {
        return fromTime;
    }

    public Time startTime() {
        return getTime(fromTime);
    }

    public double getToTime() {
        return toTime;
    }

    public Time endTime() {
        return getTime(toTime);
    }

    private Time getTime(double time) {
        int hour = (int)time;
        int minute = (int)((time-hour)*100);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return new Time(calendar.getTimeInMillis());
    }

    private void setTime(double fromTime, double toTime) {
        double min = (fromTime < toTime) ? fromTime : toTime;
        double max = (fromTime > toTime) ? fromTime : toTime;
        setFromTime(min);
        setToTime(max);
    }

    private void setToTime(double toTime) {
        if(toTime > MAX) {
            toTime = MAX;
        } else {
            this.toTime = toTime;
        }
    }

    private void setFromTime(double fromTime) {
        if(fromTime < MIN) {
            this.fromTime = MIN;
        } else {
            this.fromTime = fromTime;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%02.2f - %02.2f", fromTime, toTime).replace(".", ":");
    }
}
