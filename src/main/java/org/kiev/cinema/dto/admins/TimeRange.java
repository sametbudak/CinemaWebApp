package org.kiev.cinema.dto.admins;

import org.kiev.cinema.date.TimeAsHHmm;

import java.sql.Timestamp;

public class TimeRange implements Cloneable {
    private TimeAsHHmm from;
    private TimeAsHHmm to;

    public TimeRange(TimeAsHHmm from, TimeAsHHmm add) {
        this.from = from;
        this.to = from.add(add);
    }

    public boolean isWithin(Timestamp timeStart, Integer minutes) {

        Timestamp timeEnd = new Timestamp(timeStart.getTime() + minutes*60*1000);

        TimeAsHHmm start = new TimeAsHHmm(timeStart);
        TimeAsHHmm end = new TimeAsHHmm(timeEnd);

        if(this.from.equals(start) || this.from.equals(end) || this.to.equals(start) || this.to.equals(end))  {
            return true;
        }

        if( (this.from.less(start) && this.to.more(start))
                || (this.from.less(end) && this.to.more(end))
                ) {
            return true;
        }

        return false;
    }

    public TimeAsHHmm getFrom() {
        return from;
    }

    public TimeAsHHmm getTo() {
        return to;
    }

    @Override
    public String toString() {
        return from + "-" + to;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

