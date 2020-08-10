//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.saltfishsoft.springclouddemo.common.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_DAY_FORMAT = "yyyy-MM-dd";

    public DateUtil() {
    }

    public static String toStr(GregorianCalendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

    public static String toStr1(GregorianCalendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static String syslogNowStr() {
        String SYSLOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
        SimpleDateFormat syslogDateFormat = new SimpleDateFormat(SYSLOG_DATE_FORMAT);
        return syslogDateFormat.format((new GregorianCalendar()).getTime());
    }

    public static String toDateStr(GregorianCalendar calendar) {
        if(calendar == null) {
            return "";
        } else {
            SimpleDateFormat dateDayFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateDayFormat.format(calendar.getTime());
        }
    }

    public static String toDateStr1(GregorianCalendar calendar) {
        if(calendar == null) {
            return "";
        } else {
            SimpleDateFormat dateDayFormat = new SimpleDateFormat("yyyyMMdd");
            return dateDayFormat.format(calendar.getTime());
        }
    }

    public static String toDateTimeStr(GregorianCalendar calendar) {
        if(calendar == null) {
            return "";
        } else {
            SimpleDateFormat dateDayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateDayFormat.format(calendar.getTime());
        }
    }

    public static GregorianCalendar toCalendar(String calendarStr) {
        if(calendarStr == null) {
            return null;
        } else {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateFormat.parse(calendarStr);
                GregorianCalendar rv = new GregorianCalendar();
                rv.setTime(date);
                return rv;
            } catch (ParseException var4) {
                var4.printStackTrace();
            } catch (Exception var5) {
                ;
            }

            return null;
        }
    }

    public static GregorianCalendar toDateCalendar(String calendarStr) {
        if(calendarStr == null) {
            return null;
        } else {
            try {
                SimpleDateFormat dateDayFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateDayFormat.parse(calendarStr);
                GregorianCalendar rv = new GregorianCalendar();
                rv.setTime(date);
                return rv;
            } catch (ParseException var4) {
                var4.printStackTrace();
            } catch (Exception var5) {
                ;
            }

            return null;
        }
    }

    public static Date toDate(String str) {
        if(str != null && !"".equals(str)) {
            SimpleDateFormat dateFormatTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                return dateFormatTemp.parse(str);
            } catch (ParseException var3) {
                var3.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static Date toDateSimple(String str) {
        if(str != null && !"".equals(str)) {
            SimpleDateFormat dateFormatTemp = new SimpleDateFormat("yyyy-MM-dd");

            try {
                return dateFormatTemp.parse(str);
            } catch (ParseException var3) {
                var3.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = sdf.format(new Date());
        return sDate;
    }

    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = sdf.format(new Date());
        return sDate;
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String sDate = sdf.format(new Date());
        return sDate;
    }

    public static String getTimeFormat(String strFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        String sDate = sdf.format(new Date());
        return sDate;
    }

    public static String setDateFormat(String myDate, String strFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        String sDate = sdf.format(sdf.parse(myDate));
        return sDate;
    }

    public static String formatDateTime(String strDateTime, String strFormat) {
        String sDateTime = strDateTime;

        try {
            Calendar Cal = parseDateTime(strDateTime);
            SimpleDateFormat sdf = null;
            sdf = new SimpleDateFormat(strFormat);
            sDateTime = sdf.format(Cal.getTime());
        } catch (Exception var5) {
            ;
        }

        return sDateTime;
    }

    public static Calendar parseDateTime(String baseDate) {
        Calendar cal = null;
        cal = new GregorianCalendar();
        int yy = Integer.parseInt(baseDate.substring(0, 4));
        int mm = Integer.parseInt(baseDate.substring(5, 7)) - 1;
        int dd = Integer.parseInt(baseDate.substring(8, 10));
        int hh = 0;
        int mi = 0;
        int ss = 0;
        if(baseDate.length() > 12) {
            hh = Integer.parseInt(baseDate.substring(11, 13));
            mi = Integer.parseInt(baseDate.substring(14, 16));
            ss = Integer.parseInt(baseDate.substring(17, 19));
        }

        cal.set(yy, mm, dd, hh, mi, ss);
        return cal;
    }

    public static int getDay(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(5);
    }

    public static int getMonth(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(2) + 1;
    }

    public static int getWeekDay(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(7);
    }

    public static String getWeekDayName(String strDate) {
        String[] mName = new String[]{"日", "一", "二", "三", "四", "五", "六"};
        int iWeek = getWeekDay(strDate);
        --iWeek;
        return "星期" + mName[iWeek];
    }

    public static String getWeekDayNum(String strDate) {
        String[] mName = new String[]{"7", "1", "2", "3", "4", "5", "6"};
        int iWeek = getWeekDay(strDate);
        --iWeek;
        return mName[iWeek];
    }

    public static int getYear(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(1) + 1900;
    }

    public static String dateAdd(String strDate, int iCount, int iType) {
        Calendar Cal = parseDateTime(strDate);
        int pType = 0;
        if(iType == 0) {
            pType = 1;
        } else if(iType == 1) {
            pType = 2;
        } else if(iType == 2) {
            pType = 5;
        } else if(iType == 3) {
            pType = 10;
        } else if(iType == 4) {
            pType = 12;
        } else if(iType == 5) {
            pType = 13;
        }

        Cal.add(pType, iCount);
        SimpleDateFormat sdf = null;
        if(iType <= 2) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        String sDate = sdf.format(Cal.getTime());
        return sDate;
    }

    public static String dateAdd(String strOption, int iDays, String strStartDate) {
        if(!strOption.equals("d")) {
            ;
        }

        return strStartDate;
    }

    public static GregorianCalendar addDay(GregorianCalendar dateField, int days, int hours) {
        long dateTime = dateField.getTimeInMillis();
        if(days < 0) {
            return dateField;
        } else {
            long daysTime = (long)(days * 24 * 60 * 60) * 1000L;
            long hoursTime = (long)(hours * 60 * 60 * 1000);
            long newDateTime = dateTime + daysTime + hoursTime;
            GregorianCalendar newDate = new GregorianCalendar();
            newDate.setTimeInMillis(newDateTime);
            return newDate;
        }
    }

    public static GregorianCalendar delDay(GregorianCalendar dateField, int days) {
        long dateTime = dateField.getTimeInMillis();
        if(days < 0) {
            return dateField;
        } else {
            long daysTime = (long)(days * 24 * 60 * 60) * 1000L;
            long newDateTime = dateTime - daysTime;
            GregorianCalendar newDate = new GregorianCalendar();
            newDate.setTimeInMillis(newDateTime);
            return newDate;
        }
    }

    public static GregorianCalendar delDay_new(GregorianCalendar dateField, int days) {
        if(days < 0) {
            return dateField;
        } else {
            Calendar calendar = new GregorianCalendar();
            Date date = dateField.getTime();
            calendar.setTime(date);
            calendar.add(5, -days);
            return (GregorianCalendar)calendar;
        }
    }

    public static GregorianCalendar addDay(GregorianCalendar dateField, int days, int hours, int minute) {
        long dateTime = dateField.getTimeInMillis();
        if(days < 0) {
            return dateField;
        } else {
            long daysTime = (long)(days * 24 * 60 * 60) * 1000L;
            long hoursTime = (long)(hours * 60 * 60 * 1000);
            long minuteTime = (long)(minute * 60 * 1000);
            long newDateTime = dateTime + daysTime + hoursTime + minuteTime;
            GregorianCalendar newDate = new GregorianCalendar();
            newDate.setTimeInMillis(newDateTime);
            return newDate;
        }
    }

    public static GregorianCalendar addHour(GregorianCalendar dateField, int hours, int minute) {
        System.out.println(dateField.getTime());
        long dateTime = dateField.getTimeInMillis();
        if(hours < 0) {
            return dateField;
        } else {
            long hoursTime = (long)(hours * 60 * 60 * 1000);
            long minuteTime = (long)(minute * 60 * 1000);
            long newDateTime = dateTime + hoursTime + minuteTime;
            GregorianCalendar newDate = new GregorianCalendar();
            newDate.setTimeInMillis(newDateTime);
            System.out.println("newDate:" + newDate.getTime());
            return newDate;
        }
    }

    public static long getIntervalTwoCalendar(GregorianCalendar beforDate, GregorianCalendar afterDate) {
        return afterDate.getTimeInMillis() - beforDate.getTimeInMillis();
    }

    public static GregorianCalendar addDay(GregorianCalendar dateField, long days) {
        long dateTime = dateField.getTimeInMillis();
        if(days < 0L) {
            return dateField;
        } else {
            long millis = 86400000L;
            long daysTime = days * millis;
            long newDateTime = dateTime + daysTime;
            GregorianCalendar newDate = new GregorianCalendar();
            newDate.setTimeInMillis(newDateTime);
            return newDate;
        }
    }

    public static String addDay(int days) {
        new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.add(5, days);
        Date d = ca.getTime();
        return format.format(d);
    }

    public static int dateDiff(String strDateBegin, String strDateEnd, int iType) {
        Calendar calBegin = parseDateTime(strDateBegin);
        Calendar calEnd = parseDateTime(strDateEnd);
        long lBegin = calBegin.getTimeInMillis();
        long lEnd = calEnd.getTimeInMillis();
        int ss = (int)((lBegin - lEnd) / 1000L);
        int min = ss / 60;
        int hour = min / 60;
        int day = hour / 24;
        return iType == 0?hour:(iType == 1?min:(iType == 2?day:-1));
    }

    public static boolean isLeapYear(int yearNum) {
        boolean isLeep = false;
        if(yearNum % 4 == 0 && yearNum % 100 != 0) {
            isLeep = true;
        } else if(yearNum % 400 == 0) {
            isLeep = true;
        } else {
            isLeep = false;
        }

        return isLeep;
    }

    public static int getWeekNumOfYear() {
        Calendar calendar = Calendar.getInstance();
        int iWeekNum = calendar.get(3);
        return iWeekNum;
    }

    public static int getWeekNumOfYearDay(String strDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = format.parse(strDate);
        calendar.setTime(curDate);
        int iWeekNum = calendar.get(3);
        return iWeekNum;
    }

    public static String getYearWeekFirstDay(int yearNum, int weekNum) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(1, yearNum);
        cal.set(3, weekNum);
        cal.set(7, 2);
        String tempYear = Integer.toString(yearNum);
        String tempMonth = Integer.toString(cal.get(2) + 1);
        String tempDay = Integer.toString(cal.get(5));
        String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
        return setDateFormat(tempDate, "yyyy-MM-dd");
    }

    public static String getYearWeekEndDay(int yearNum, int weekNum) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(1, yearNum);
        cal.set(3, weekNum + 1);
        cal.set(7, 1);
        String tempYear = Integer.toString(yearNum);
        String tempMonth = Integer.toString(cal.get(2) + 1);
        String tempDay = Integer.toString(cal.get(5));
        String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
        return setDateFormat(tempDate, "yyyy-MM-dd");
    }

    public static String getYearMonthFirstDay(int yearNum, int monthNum) throws ParseException {
        String tempYear = Integer.toString(yearNum);
        String tempMonth = Integer.toString(monthNum);
        String tempDay = "1";
        String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
        return setDateFormat(tempDate, "yyyy-MM-dd");
    }

    public static String getYearMonthEndDay(int yearNum, int monthNum) throws ParseException {
        String tempYear = Integer.toString(yearNum);
        String tempMonth = Integer.toString(monthNum);
        String tempDay = "31";
        if(tempMonth.equals("1") || tempMonth.equals("3") || tempMonth.equals("5") || tempMonth.equals("7") || tempMonth.equals("8") || tempMonth.equals("10") || tempMonth.equals("12")) {
            tempDay = "31";
        }

        if(tempMonth.equals("4") || tempMonth.equals("6") || tempMonth.equals("9") || tempMonth.equals("11")) {
            tempDay = "30";
        }

        if(tempMonth.equals("2")) {
            if(isLeapYear(yearNum)) {
                tempDay = "29";
            } else {
                tempDay = "28";
            }
        }

        String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
        return setDateFormat(tempDate, "yyyy-MM-dd");
    }

    public static synchronized GregorianCalendar now() {
        return new GregorianCalendar();
    }

    public static synchronized String nowStr() {
        return toStr(new GregorianCalendar());
    }

    public static String longtoTime(long times) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("2000-01-01 00:00:00", new ParsePosition(0));
        date.setTime(date.getTime() + times);
        format.applyPattern("HH时mm分ss秒");
        return format.format(date);
    }

    public static String addMonth(Date date, int amount) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(2, amount);
        date.setTime(cal.getTimeInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String addYear(Date date, int amount) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(1, amount);
        date.setTime(cal.getTimeInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String addMillisecond(Date date, int amount) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(14, amount);
        date.setTime(cal.getTimeInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String addSecond(Date date, int amount) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(13, amount);
        date.setTime(cal.getTimeInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static int daysOfTwo(Date fDate, Date oDate) {
        GregorianCalendar beforDate = new GregorianCalendar();
        beforDate.setTime(fDate);
        GregorianCalendar afterDate = new GregorianCalendar();
        afterDate.setTime(oDate);
        long timeC = getIntervalTwoCalendar(beforDate, afterDate);
        return (int)(timeC / 1000L / 60L / 60L / 24L);
    }
}
