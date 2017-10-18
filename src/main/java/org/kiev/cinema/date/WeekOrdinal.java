package org.kiev.cinema.date;

import java.sql.Date;
import java.util.*;

public enum WeekOrdinal {
    CURRENT(0),
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8),
    NINTH(9),
    TENTH(10);

    private int index;

    private WeekOrdinal(int index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public Date getMondayDateForComingWeek() {
        Calendar calendar = DateUtils.getTodayCalendarWithZeroTime();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Monday is the second day of the week in java.util.GregorianCalendar
        calendar.add(Calendar.DATE, ( this.index*7 - (dayOfWeek-2)) );
        return new Date(calendar.getTime().getTime());
    }
    public Date getSundayDateForComingWeek() {
        Calendar calendar = DateUtils.getTodayCalendarWithZeroTime();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Monday is the second day of the week in java.util.GregorianCalendar
        calendar.add(Calendar.DATE, ( this.index*7 -(dayOfWeek-2) + 6 ) );
        return new Date(calendar.getTime().getTime());
    }

    public List<Date> getDateListForComingWeek() {
        List<Date> dateList = new ArrayList<>();
        Calendar calendar = DateUtils.getTodayCalendarWithZeroTime();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Monday is the second day of the week in java.util.GregorianCalendar
        calendar.add(Calendar.DATE, ( this.index*7 - (dayOfWeek-2)) );
        dateList.add(new Date(calendar.getTime().getTime()));
        for(int i = 1; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            dateList.add(new Date(calendar.getTime().getTime()));
        }
        return dateList;
    }
}
