package org.kiev.cinema.date;

import org.kiev.cinema.CinemaConstants;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class DateUtils {

    public static Date parseDateFromEEEMMMd(String dateStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d yyyy", Locale.ENGLISH);
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        java.util.Date date = df.parse(dateStr + " " + year.format(new Date(System.currentTimeMillis())));
        return new Date(date.getTime());
    }

    public static Date parseDateFromyyyyMMdd(String dateStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        java.util.Date date = df.parse(dateStr);
        return new Date(date.getTime());
    }

    public static Timestamp parseTimestampFromyyyyMMddHHmm(String yyyyMMdd, String HHmm) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        String dateStr = yyyyMMdd + " " + HHmm;
        java.util.Date date = df.parse(dateStr);
        return new Timestamp(date.getTime());
    }

    public static Timestamp parseTimestampFromyyyyMMddHHmm(String yyyyMMddHHmm) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        java.util.Date date = df.parse(yyyyMMddHHmm);
        return new Timestamp(date.getTime());
    }

    public static String formatAsEEEMMMd(Timestamp timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d", Locale.ENGLISH);
        return df.format(timestamp);
    }

    public static String formatAsEEEMMMdyyyy(Timestamp timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.ENGLISH);
        return df.format(timestamp);
    }

    public static String formatAsEEEMMMdyyyy(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.ENGLISH);
        return df.format(date);
    }

    public static String formatAsEEEMMMd(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d", Locale.ENGLISH);
        return df.format(date);
    }

    public static String formatAsdMMMyyyyEEEE(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("d MMM, yyyy (EEEE)", Locale.ENGLISH);
        return df.format(date);
    }

    public static String formatAsdMMMyyyyEEEE(Timestamp timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("d MMM, yyyy (EEEE)", Locale.ENGLISH);
        return df.format(timestamp);
    }

    public static String formatAsyyyyMMdd(Timestamp timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return df.format(timestamp);
    }

    public static String formatAsyyyyMMddHHmmss(Timestamp timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return df.format(timestamp);
    }

    public static String formatAsyyyyMMdd(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return df.format(date);
    }

    public static String formatAsHHmm(Timestamp timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        return df.format(timestamp);
    }

    public static List<Date> getDatesFromToday(Integer daysInterval) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = GregorianCalendar.getInstance();
        Date date = new Date(calendar.getTime().getTime());
        dates.add(date);
        for(int i = 1; i < daysInterval; i++) {
            calendar.add(Calendar.DATE, 1);
            date = new Date(calendar.getTime().getTime());
            dates.add(date);
        }
        return dates;
    }

    public static Calendar getTodayCalendarWithZeroTime(){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeZone(CinemaConstants.TIME_ZONE);
        calendar.set(Calendar.HOUR_OF_DAY, 0); // HOUR_OF_DAY uses 24 hours, while HOUR 12
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar getCalendarForNextDayWithZeroTime(){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeZone(CinemaConstants.TIME_ZONE);
        calendar.set(Calendar.HOUR_OF_DAY, 0); // HOUR_OF_DAY uses 24 hours, while HOUR 12
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, 1);
        return calendar;
    }

    public static Calendar getTodayCalendarAtTheEndOfTheDay(){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeZone(CinemaConstants.TIME_ZONE);
        calendar.set(Calendar.HOUR_OF_DAY, 23); // HOUR_OF_DAY uses 24 hours, while HOUR 12
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Date getTodayDateWithZeroTime(){
        Calendar calendar = getTodayCalendarWithZeroTime();
        return new Date(calendar.getTime().getTime());
    }

    public static Date getLastDateWithZeroTime(Date firstDate, Integer daysInterval){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeZone(CinemaConstants.TIME_ZONE);
        calendar.setTime(firstDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0); // HOUR_OF_DAY uses 24 hours, while HOUR 12
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, daysInterval-1);
        return new Date(calendar.getTime().getTime());
    }

    public static Date setTimeToZero(Date date){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(CinemaConstants.TIME_ZONE);
        calendar.set(Calendar.HOUR_OF_DAY, 0); // HOUR_OF_DAY uses 24 hours, while HOUR 12
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTime().getTime());
    }
}
