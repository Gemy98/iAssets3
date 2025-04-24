/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.util;

import com.iassets.common.util.Common;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public static Date getDateFromString(String dateStr) {
        try {
            return new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static Timestamp getCurrentTimeStamp() {
        return new Timestamp(new Date().getTime());
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String getDateString(Date d) {
        return DateUtil.getDateString(d, DATE_FORMAT);
    }

    public static String getDateString(Date d, String format) {
        if (d != null) {
            return new SimpleDateFormat(format).format(d);
        }
        return "";
    }

    public static Date getFirstDateInMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getTime();
    }

    public static Date getLastDateInMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        calendar.set(5, calendar.getActualMaximum(5));
        return calendar.getTime();
    }

    public static long getDateDiff(Date newest, Date oldest, TimeUnit timeUnit) {
        long diffInMillies = newest.getTime() - oldest.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Date addDaysToDate(Date date, long days) {
        long timeadj = days * 24L * 60L * 60L * 1000L;
        Date newDate = new Date(date.getTime() + timeadj);
        return newDate;
    }

    public static void main(String[] args) {
        float x = 10.6f;
        float y = 10.4f;
        System.out.println("ceil::" + Math.ceil(x) + "round::" + Math.ceil(y));
        System.out.println("round::" + Math.round(x) + "round::" + Math.round(y));
        System.out.println("month en " + DateUtil.getMonthName(11, new Locale("en")));
        System.out.println("month ar " + DateUtil.getMonthName(11, new Locale("ar")));
        Calendar cal = Calendar.getInstance();
        System.out.println("get day of month  " + cal.get(5));
        System.out.println("getActualMaximum " + cal.getActualMaximum(5));
        cal.set(1, 2014);
        cal.set(2, 1);
        System.out.println("getActualMaximum " + cal.getActualMaximum(5));
        System.out.println(" date  " + DateUtil.getDateString(cal.getTime()));
        Common.log("addMonths date:: " + DateUtil.getDateString(DateUtil.addMonthsToDate(new Date(), 1)));
        Common.log("month:: " + DateUtil.getMonth(new Date()));
        Common.log("year:: " + DateUtil.getYear(new Date()));
        Common.log(DateUtil.getDateString(new Date()));
        Common.log(DateUtil.getDateString(DateUtil.getLastDateInMonth(2, 2007)));
        Common.log(DateUtil.getDateString(DateUtil.getFirstDateInMonth(2, 2007)));
        System.out.println(DateUtil.addDaysToDate(DateUtil.getCurrentDate(), 10L));
    }

    public static Date addMonthsToDate(Date date, int monthsCount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, monthsCount);
        return cal.getTime();
    }

    public static Date addMonthsAndDaysToDate(Date date, int monthsCount, int daysCount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, monthsCount);
        cal.set(5, cal.get(5) + daysCount);
        return cal.getTime();
    }

    public static Date getClosestStartDateToSpecificDate(Date d, Date startDate, int sequenceDiffInMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        Date toBeComparedTo = d;
        double diff = DateUtil.getDiffMonthsBetweenDates(startDate, toBeComparedTo);
        diff = Math.ceil(diff / (double)sequenceDiffInMonth) * (double)sequenceDiffInMonth;
        cal.add(2, (int)diff);
        if (toBeComparedTo.getTime() > cal.getTime().getTime()) {
            cal.add(2, sequenceDiffInMonth);
        }
        return cal.getTime();
    }

    public static int getDiffMonthsBetweenDates(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int diffYear = endCalendar.get(1) - startCalendar.get(1);
        int diffMonth = diffYear * 12 + endCalendar.get(2) - startCalendar.get(2);
        return diffMonth;
    }

    public static int getMonth() {
        return DateUtil.getMonth(new Date());
    }

    public static int getYear() {
        return DateUtil.getYear(new Date());
    }

    public static int getDay() {
        return DateUtil.getDay(new Date());
    }

    public static int getMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(2) + 1;
    }

    public static int getYear(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(1);
    }

    public static int getDay(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(5);
    }

    public static String getMonthName(int month, Locale locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] monthNames = symbols.getMonths();
        return monthNames[month - 1];
    }

    public static String getMonthName(int month, String langCode) {
        Locale locale = new Locale(langCode);
        return DateUtil.getMonthName(month, locale);
    }

    public static String getDayName(int day, Locale locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] dayNames = symbols.getWeekdays();
        return dayNames[day];
    }

    public static String getDayName(int month, String langCode) {
        Locale locale = new Locale(langCode);
        return DateUtil.getDayName(month, locale);
    }
}
