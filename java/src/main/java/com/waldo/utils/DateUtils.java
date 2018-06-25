package com.waldo.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

public class DateUtils {

    private static final String shortDateAndTimeStr = "--/--/---- --:--";
    private static final String shortDateStr = "--/--/----";
    private static final String shortTimeStr = "--:--";
    private static final String monthDateStr = "--/--";
    private static final String longDateAndTimeStr = "--- ---, ---- --:--";
    private static final String longDateStr = "--- ---, ----";
    private static final String detailTimeStr = "--:--:--.---";

    public static final SimpleDateFormat shortDateAndTime = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    private static final SimpleDateFormat sqlDateAndTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    public static final SimpleDateFormat shortDate = new SimpleDateFormat("dd/MM/YYYY");
    public static final SimpleDateFormat monthDate = new SimpleDateFormat("dd/MM");
    private static final SimpleDateFormat shortTime = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat detailTime = new SimpleDateFormat("HH:mm:ss.SSS");

    private static final SimpleDateFormat longDateAndTime = new SimpleDateFormat("ddd MMM, yyyy HH:mm");
    private static final SimpleDateFormat longDate = new SimpleDateFormat("ddd MMM, yyyy");

    private static final SimpleDateFormat mySqlDateTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    public static String formatDateTime(Date date) {
        if (date != null && !date.equals(minDate())) {
            return shortDateAndTime.format(date);
        } else {
            return shortDateAndTimeStr;
        }
    }

    public static String formatTime(Date date) {
        if (date != null && !date.equals(minDate())) {
            return shortTime.format(date);
        } else {
            return shortTimeStr;
        }
    }

    public static String formatDate(Date date) {
        if (date != null && !date.equals(minDate())) {
            return shortDate.format(date);
        } else {
            return shortDateStr;
        }
    }

    public static String formatDateTimeLong(Date date) {
        if (date != null && !date.equals(minDate())) {
            return longDateAndTime.format(date);
        } else {
            return longDateAndTimeStr;
        }
    }

    public static String formatDateLong(Date date) {
        if (date != null && !date.equals(minDate())) {
            return longDate.format(date);
        } else {
            return longDateStr;
        }
    }

    public static String formatDetailTime(Date date) {
        if (date != null && !date.equals(minDate())) {
            return detailTime.format(date);
        } else {
            return detailTimeStr;
        }
    }

    public static String formatMonthDate(Date date) {
        if (date != null && !date.equals(minDate())) {
            return monthDate.format(date);
        } else {
            return monthDateStr;
        }
    }

    public static String formatMySqlDateTime(Date date) {
        if (date != null) {
            return mySqlDateTime.format(date);
        } else {
            return "";
        }
    }


    public static Date now() {
        return new Date(Calendar.getInstance().getTime().getTime());
    }

    public static Date minDate() {
        return new Date(0);
    }

    public static Date sqLiteToDate(String dateTxt) {
        Date date = minDate();
        if (dateTxt != null && !dateTxt.isEmpty()) {
            try {
                java.util.Date d = sqlDateAndTime.parse(dateTxt);
                date = new Date(d.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date stripTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long time = calendar.getTimeInMillis();
        return new Date(time);
    }

    public static Date stripDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        long time = calendar.getTimeInMillis();
        return new Date(time);
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        long time = calendar.getTimeInMillis();
        return new Date(time);
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }


    private static class DateComparator implements Comparator<java.util.Date> {
        @Override
        public int compare(java.util.Date d1, java.util.Date d2) {
            if (d1 == null) {
                d1 = DateUtils.minDate();
            }
            if (d2 == null) {
                d2 = DateUtils.minDate();
            }
            return d1.compareTo(d2);
        }
    }
}
