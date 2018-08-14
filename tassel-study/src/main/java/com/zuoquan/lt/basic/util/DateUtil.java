package com.zuoquan.lt.basic.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
    private static final ThreadLocal<SimpleDateFormat> dateFmtInstance = new ThreadLocal() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
    private static ThreadLocal<Calendar> calInstance = new ThreadLocal() {
        protected Calendar initialValue() {
            return Calendar.getInstance();
        }
    };

    public DateUtil() {
    }

    public static Date addDay(Date date, int day) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = (Calendar)calInstance.get();
            calendar.setTime(date);
            calendar.add(5, day);
            return calendar.getTime();
        }
    }

    public static Date rollDay(Date date, int day) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = (Calendar)calInstance.get();
            calendar.setTime(date);
            calendar.roll(5, day);
            return calendar.getTime();
        }
    }

    public static Integer getLastDay(Date date) {
        Integer year = Integer.valueOf(getYear(date));
        Integer month = Integer.valueOf(getMonth(date));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(1, year.intValue());
        cal.set(2, month.intValue());
        cal.set(5, 1);
        cal.add(5, -1);
        String day_last = df.format(cal.getTime());
        StringBuffer endStr = (new StringBuffer()).append(day_last).append(" 23:59:59");
        Date date2 = strToDate(endStr.toString());
        return Integer.valueOf(getDayDate(date2));
    }

    public static Long addDay(Long date, int day) {
        if(date == null) {
            return null;
        } else {
            Date realDate = addDay(toRealDate(date), day);
            return toLongDate(realDate);
        }
    }

    public static Date addMin(Date date, int minutes) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = (Calendar)calInstance.get();
            calendar.setTime(date);
            calendar.add(12, minutes);
            return calendar.getTime();
        }
    }

    public static Date addHour(Date date, int hours) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = (Calendar)calInstance.get();
            calendar.setTime(date);
            calendar.add(10, hours);
            return calendar.getTime();
        }
    }

    public static int get(Date date, int field) {
        if(date == null) {
            return -1;
        } else {
            Calendar cal = (Calendar)calInstance.get();
            cal.setTime(date);
            return cal.get(field);
        }
    }

    public static int get(Long date, int field) {
        return get(toRealDate(date), field);
    }

    public static Date toRealDate(Long date) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = (Calendar)calInstance.get();
            int ss = (int)(date.longValue() - date.longValue() / 100L * 100L);
            calendar.set(13, ss);
            int mi = (int)((date.longValue() - date.longValue() / 10000L * 10000L) / 100L);
            calendar.set(12, mi);
            int hh = (int)((date.longValue() - date.longValue() / 1000000L * 1000000L) / 10000L);
            calendar.set(11, hh);
            int dd = (int)((date.longValue() - date.longValue() / 100000000L * 100000000L) / 1000000L);
            calendar.set(5, dd);
            int yy = (int)(date.longValue() / 10000000000L);
            calendar.set(1, yy);
            int noYear = (int)(date.longValue() - (long)yy * 10000000000L);
            int mm = noYear / 100000000;
            calendar.set(2, mm - 1);
            return calendar.getTime();
        }
    }

    public static Integer toYmdh(Date date) {
        return date != null?Integer.valueOf(Integer.parseInt((new SimpleDateFormat("yyyyMMddHH")).format(date))):null;
    }

    public static Integer toYmd(Date date) {
        return date != null?Integer.valueOf(Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(date))):null;
    }

    public static Long toLongDate(Date date) {
        return date == null?null:Long.valueOf(((SimpleDateFormat)dateFmtInstance.get()).format(date));
    }

    public static Date addMonth(Date date, int month) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = (Calendar)calInstance.get();
            calendar.setTime(date);
            calendar.add(2, month);
            return calendar.getTime();
        }
    }

    public static Date setDayOfMonth(Date date, int value) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, value);
        return c.getTime();
    }

    public static Long addMonth(Long date, int month) {
        if(date == null) {
            return null;
        } else {
            Date realDate = addMonth(toRealDate(date), month);
            return toLongDate(realDate);
        }
    }

    public static Date clearTime(Date date) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = (Calendar)calInstance.get();
            calendar.setTime(date);
            int y = calendar.get(1);
            int m = calendar.get(2);
            int d = calendar.get(5);
            calendar.clear();
            calendar.set(1, y);
            calendar.set(2, m);
            calendar.set(5, d);
            return calendar.getTime();
        }
    }

    public static Date setTime(Date date, int hour, int minute, int second) {
        Calendar cal = (Calendar)calInstance.get();
        cal.setTime(date);
        if(hour >= 0) {
            cal.set(11, hour);
        }

        if(minute >= 0) {
            cal.set(12, minute);
        }

        if(second >= 0) {
            cal.set(13, second);
        }

        return cal.getTime();
    }

    public static Long clearTime(Long date) {
        return date == null?null:Long.valueOf(date.longValue() / 1000000L * 1000000L);
    }

    public static String dateToStr(Date date, String pattern) {
        if(date != null && pattern != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } else {
            return null;
        }
    }

    public static String dateToStrFormat(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        if(date != null && pattern != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } else {
            return null;
        }
    }

    public static String dateTimeToStr(Date date) {
        return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateToStr(Date date) {
        return dateToStr(date, "yyyy-MM-dd");
    }

    public static Date dateToDatePattern(Date date, String pattern) {
        String dateStr = dateToStr(date, pattern);
        return strToDate(dateStr, pattern);
    }

    public static Date strToDate(String date, String pattern) {
        if(date != null && pattern != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            try {
                return sdf.parse(date);
            } catch (ParseException var4) {
                throw new RuntimeException(var4);
            }
        } else {
            return null;
        }
    }

    public static Date safeStrToDate(String date, String pattern) {
        if(date != null && pattern != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            try {
                return sdf.parse(date);
            } catch (ParseException var4) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static int dateDiff(Date d1, Date d2) {
        if(d1 != null && d2 != null) {
            d1 = setTime(d1, 0, 0, 0);
            d2 = setTime(d2, 0, 0, 0);
            Long diff = Long.valueOf((d1.getTime() - d2.getTime()) / 1000L / 60L / 60L / 24L);
            return diff.intValue();
        } else {
            throw new NullPointerException("dateDiff方法两个参数不能为null!");
        }
    }

    public static int dateMonth(Date start, Date end) {
        if(start.after(end)) {
            Date startCalendar = start;
            start = end;
            end = startCalendar;
        }

        Calendar startCalendar1 = Calendar.getInstance();
        startCalendar1.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(5, 1);
        int year = endCalendar.get(1) - startCalendar1.get(1);
        int month = endCalendar.get(2) - startCalendar1.get(2);
        return startCalendar1.get(5) == 1 && temp.get(5) == 1?year * 12 + month + 1:(startCalendar1.get(5) != 1 && temp.get(5) == 1?year * 12 + month:(startCalendar1.get(5) == 1 && temp.get(5) != 1?year * 12 + month:(year * 12 + month - 1 < 0?0:year * 12 + month)));
    }

    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec.longValue());
        return sdf.format(date);
    }

    public static Long dateDiffLong(Date d1, Date d2) {
        if(d1 != null && d2 != null) {
            d1 = setTime(d1, 0, 0, 0);
            d2 = setTime(d2, 0, 0, 0);
            Long diff = Long.valueOf((d1.getTime() - d2.getTime()) / 1000L / 60L / 60L / 24L);
            if(diff.longValue() < 0L) {
                diff = Long.valueOf(0L);
            }

            return diff;
        } else {
            return Long.valueOf(0L);
        }
    }

    public static int dateDiff(Long d1, Long d2) {
        return dateDiff(toRealDate(d1), toRealDate(d2));
    }

    public static Date strToDate(String date) {
        return strToDate(date, "yyyy-MM-dd");
    }

    public static int getWeekOfDate(Date dt) {
        int[] weekDays = new int[]{0, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(7) - 1;
        if(w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    public static Boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

        try {
            Date e = sdf.parse(date);
            String tmp = dateToStr(e, "yyyy.MM.dd");
            if(date.equals(tmp)) {
                return Boolean.valueOf(true);
            }
        } catch (ParseException var4) {
            throw new RuntimeException(var4);
        }

        return Boolean.valueOf(false);
    }

    public static Date getValidDate(int year, int month, int day) {
        int v_year = year;
        int v_month = month;
        int v_day = day;
        String v_date = "";
        Date validDate = null;

        while(v_date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                v_date = v_year + "-" + v_month + "-" + v_day;
                validDate = sdf.parse(v_date);
                String e = dateToStr(validDate, "yyyy-M-d");
                if(v_date.equals(e)) {
                    return validDate;
                }

                --v_day;
            } catch (ParseException var10) {
                throw new RuntimeException(var10);
            }
        }

        return null;
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(1);
        return year;
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(2);
        return month + 1;
    }

    public static int getDayDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(5);
        return day;
    }

    public static Date getEndDate(Date date, int payday) {
        return getValidDate(getYear(date), getMonth(date), payday);
    }

    public static Date addDayWithTail(Date date, int day) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = (Calendar)calInstance.get();
            calendar.setTime(date);
            calendar.add(5, day - 1);
            return calendar.getTime();
        }
    }

    public static Date addMonthWithTail(Date date, int month) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = (Calendar)calInstance.get();
            calendar.setTime(date);
            calendar.add(2, month);
            calendar.add(5, -1);
            return calendar.getTime();
        }
    }

    public static String returnDateStr(Date date) {
        new String();
        String dataString = getYear(date) + "年" + getMonth(date) + "月" + getDayDate(date) + "日";
        return dataString;
    }

    public static String timeStampToDate(String seconds, String format) {
        if(seconds != null && !seconds.isEmpty() && !seconds.equals("null")) {
            if(format == null || format.isEmpty()) {
                format = "yyyy-MM-dd HH:mm:ss";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date(Long.valueOf(seconds + "000").longValue()));
        } else {
            return "";
        }
    }

    public static String changeToDateStr(Long seconds) {
        if(seconds != null && seconds.longValue() != 0L && seconds.longValue() >= 0L) {
            long day = seconds.longValue() / 86400L;
            long hour = seconds.longValue() % 86400L / 3600L;
            long minute = seconds.longValue() % 3600L / 60L;
            long second = seconds.longValue() % 60L;
            String res = "";
            if(day > 0L) {
                res = res + day + "天 ";
            }

            if(hour > 0L) {
                res = res + hour + "小时 ";
            }

            if(minute > 0L) {
                res = res + minute + "分 ";
            }

            if(second > 0L) {
                res = res + second + "秒 ";
            }

            return res;
        } else {
            return null;
        }
    }

    public static String dateFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = null;
        if(date != null) {
            dateString = dateFormat.format(date);
        }

        return dateString;
    }

    public static SimpleDateFormat dateFormat4yyyyMMddHHmmss1() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }


	/**
     * 日期字符串转化成需要的日期格式的日期
     * 
     * @param date 传入日期字符串
     * @param pattern 转换格式
     * @return 转化结果
     */
    public static Date dateStrToDate(String date, String pattern) {
        if (date == null || pattern == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        }catch (ParseException e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    /**
     * 日期转化成需要的日期格式的日期字符串
     * 
     * @param date 传入日期
     * @param pattern 转换格式
     * @return 转化结果
     */
    public static String dateToDateStr(Date date, String pattern){
    	 if (date == null || pattern == null){
             return null;
         }
    	 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	 return sdf.format(date);
    }

    /**
     * 得到此时间的前一秒..
     * @param date
     * @return
     */
    public static Date getDateBeforSecond(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND,-1);
        return calendar.getTime();
    }

}

