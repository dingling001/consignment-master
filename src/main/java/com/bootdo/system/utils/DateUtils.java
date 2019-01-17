package com.bootdo.system.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 一分钟多少毫秒
     */
    protected static final int MIN_TO_MILS = 60000;

    /**
     * 获取日期时间字符串格式:yyMMdd
     *
     * @param time
     * @return
     */
    public static String getMonthStr(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(time);
    }

    /**
     * 获取日期时间字符串格式:yyMMdd
     *
     * @param time
     * @return
     */
    public static String getDateStr(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(time);
    }

    /**
     * 获取日期时间字符串格式:yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String getDateTimeFormat(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 获取日期时间字符串格式:HH:mm用于GPS预签成功时间提示
     *
     * @param time
     * @return
     */
    public static String getWorkTimeFormat(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }

    /**
     * 获取日期时间字符串格式:yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String getDateTimeFormat(Date time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(time);
    }

    /**
     * 解析字符串获得日期,格式:yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Date getDateTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException("DateUtil.getDateTime(time = " + time + ", format = yyyy-MM-dd HH:mm:ss)");
        }
    }

    /**
     * 解析字符串获得日期,格式:yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Date getDateTimeByFormat(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException("DateUtil.getDateTime(time = " + time + ", format = " + format + ")");
        }
    }

    /**
     * 解析字符串获得日期,格式支持无数种
     *
     * @param time
     * @return
     */
    public static Date getDateTimeByFormat(String time, String... formats) {
        for (String format : formats) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            try {
                return simpleDateFormat.parse(time);
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 获取日期字符串格式:yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String getDateFormat(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 获取日期字符串格式:yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String getDateFormat(Date time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(time);
    }

    /**
     * 解析字符串获得日期,格式:yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static Date getDate(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException("DateUtil.getDateTime(time = " + time + ", format = yyyy-MM-dd)");
        }
    }

    /**
     * 获取日期字符串格式
     *
     * @param time
     * @param format yyyy-MM-dd
     * @return
     */
    public static String getDateFormat(Date time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(time);
    }

    /**
     * 获取日期字符串格式
     *
     * @param time
     * @param format
     * @return
     */
    public static Date getDateByFormat(Date time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String str = simpleDateFormat.format(time);
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("DateUtil.getDateTime(time = " + time + ", format = " + format);
        }
    }


    /****
     * 根据传入的字符串转为date
     *
     * @param datStr
     *            datStr,格式必须为yyyy-MM-dd
     * @return
     */
    public static Date getDateByStr(String datStr) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(datStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;

    }

    /**
     * 获取两个时间之间的天数
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     */
    public static int getDiffDays(Date beginDate, Date endDate) {
        int days = 0;
        if (beginDate != null && endDate != null) {
            days = (int) ((endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000));
        }
        return days;
    }

    /**
     * 获取两个时间之间的年数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDiffYears(Date beginDate, Date endDate) {

        if (beginDate.compareTo(endDate) == 0) {
            return 0;
        } else if (beginDate.compareTo(endDate) > 0) {
            Date tmp = beginDate;
            beginDate = endDate;
            endDate = tmp;
        }

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.setTime(beginDate);
        cal2.setTime(endDate);
        int years = -1;
        while (cal.getTime().compareTo(cal2.getTime()) <= 0) {
            cal.add(Calendar.YEAR, 1);
            years++;
        }

        return years;
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    public static Date addYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /****
     * 根据传入的字符串转为dateTime
     *
     * @param datStr
     *            datStr,格式必须为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getDateTimeByStr(String datStr) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(datStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 获取指定月份的日历信息
     *
     * @param month 月，如(2014-05)
     * @return
     */
    public static Date[] getDates(String month) {
        try {
            // 获得第一天
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date first = sdf.parse(month);

            // 获取本月天数
            Calendar cal = Calendar.getInstance();
            cal.setTime(first);
            int maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            Date[] dates = new Date[maxDate];
            for (int i = 0; i < maxDate; i++) {
                dates[i] = new Date(cal.getTime().getTime());
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
            return dates;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定日期月份最大天数
     *
     * @param date
     * @return
     */
    public int getMaxDays(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String getLastTime(Date dateBegin, Date dateEnd) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = null;
        try {
            begin = dfs.parse(getDateTimeFormat(dateBegin));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date end = null;
        try {
            end = dfs.parse(getDateTimeFormat(dateEnd));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60 / 60;
        String retString = hour1 + "小时" + minute1 + "分" + second1 + "秒";
        return retString;
    }

    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static String getWeek2(Date date) {
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static boolean isWeekEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index == 0 || week_index == 6;
    }


    public static int getMins(Date endDate, Date startDate) {
        return (int) (endDate.getTime() / MIN_TO_MILS - startDate.getTime() / MIN_TO_MILS);
    }

    public static int getSeconds(Date endDate, Date startDate) {
        return (int) (endDate.getTime() / 1000 - startDate.getTime() / 1000);
    }

    /**
     * 获取当天剩余秒数
     *
     * @return
     */
    public static int surplusSecond() {
        String endTime = getDateFormat(new Date()) + " 23:59:59";
        return getSeconds(getDateTimeByStr(endTime), new Date());
    }
}
