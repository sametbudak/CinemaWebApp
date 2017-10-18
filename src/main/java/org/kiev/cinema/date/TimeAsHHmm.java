package org.kiev.cinema.date;

import org.kiev.cinema.CinemaConstants;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimeAsHHmm implements Cloneable {
    private int hours;
    private int minutes;

    public TimeAsHHmm(int hours, int minutes) {
        if(hours >= 0 && hours < 24 && minutes >=0 && minutes <60) {
            this.hours = hours;
            this.minutes = minutes;
        } else {
            throw new RuntimeException("hours must be >= 0 and < 24; minutes must be >= 0 and < 60");
        }
    }

    public TimeAsHHmm(int hours) {
        if(hours >= 0 && hours < 24) {
            this.hours = hours;
            this.minutes = 0;
        } else {
            throw new RuntimeException("hours must be >= 0 and < 24;");
        }
    }

    public TimeAsHHmm(Timestamp timestamp) {
        this(new Date(timestamp.getTime()));
    }

    public TimeAsHHmm(Date date) {
        DateFormat dfHours = new SimpleDateFormat("H");
        dfHours.setTimeZone(CinemaConstants.TIME_ZONE);
        DateFormat dfMinutes = new SimpleDateFormat("m");
        dfMinutes.setTimeZone(CinemaConstants.TIME_ZONE);
        this.hours = Integer.parseInt(dfHours.format(date));
        this.minutes = Integer.parseInt(dfMinutes.format(date));
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", this.hours, this.minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeAsHHmm that = (TimeAsHHmm) o;

        if (hours != that.hours) return false;
        return minutes == that.minutes;
    }

    @Override
    public int hashCode() {
        int result = hours;
        result = 31 * result + minutes;
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public TimeAsHHmm add(TimeAsHHmm timeAsHHmm) {
        int minutesTemp = this.minutes + timeAsHHmm.minutes;
        int hoursTemp;
        if (minutesTemp >= 60) {
            hoursTemp = this.hours + timeAsHHmm.hours + minutesTemp/60;
            minutesTemp %= 60;
        } else {
            hoursTemp = this.hours + timeAsHHmm.hours;
        }
        if(hoursTemp >= 24) {
            throw new RuntimeException("hours >= 24");
        }
        return new TimeAsHHmm(hoursTemp, minutesTemp);
    }

    public boolean less(TimeAsHHmm timeAsHHmm) {
        if(this.hours < timeAsHHmm.hours) {
            return true;
        }
        if(this.hours == timeAsHHmm.hours && this.minutes < timeAsHHmm.minutes) {
            return true;
        }
        return false;
    }

    public boolean more(TimeAsHHmm timeAsHHmm) {
        if(this.hours > timeAsHHmm.hours) {
            return true;
        }
        if(this.hours == timeAsHHmm.hours && this.minutes > timeAsHHmm.minutes) {
            return true;
        }
        return false;
    }

    public boolean sumIsWithin24Hours(TimeAsHHmm timeAsHHmm) {
        int hoursTotal = this.hours + timeAsHHmm.hours + (this.minutes+timeAsHHmm.minutes)/60;
        if(hoursTotal < 24) {
            return true;
        }
        return false;
    }
}
