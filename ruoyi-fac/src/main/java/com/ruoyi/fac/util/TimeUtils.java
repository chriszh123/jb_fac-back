package com.ruoyi.fac.util;

/**
 * 日期时间转换类
 * Created by zgf
 * Date 2019/1/6 15:42
 * Description
 */

import com.ruoyi.common.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtils {
    public static final String DEFAULT_DATE_TIME_FORMAT_HH_MM_SS_MS = "yyyy-MM-dd_HH-mm-ss-SSS";
    public static final String DEFAULT_DATE_TIME_FORMAT_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_TIME_FORMAT_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT_YYYY_MM = "yyyy-MM";
    public static final String DEFAULT_DATE_TIME_HH_MM = "HH:mm";
    public static final String DEFAULT_DATE_FORMAT_MM_DD = "MM-dd";
    public static final String DATE_TIME_BEGIN_AT = " 00:00:00";
    public static final String DEFAULT_DATE_FORMAT_MDHM = "MM月dd日HH:00";
    public static final String DEFAULT_DATE_FORMAT_YMDHM = "yyyy年MM月dd日HH:00";
    public static final String DEFAULT_DATE_TIME_FORMAT_HH_00 = "yyyy-MM-dd HH:00";

    public static final String getCurrentDate() {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(new Date(System.currentTimeMillis()));
    }

    public static final String getCurrentTime() {
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_HH_MM_SS).format(new Date(System.currentTimeMillis()));
    }

    public static final String getCurrentTimeSSS() {
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_HH_MM_SS_MS).format(new Date(System.currentTimeMillis()));
    }

    public static final String getDateStr(Date date) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
    }

    public static final String getTimeStr(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_HH_MM_SS).format(date);
    }

    public static final String getTimeMinuteStr(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_HH_MM).format(date);
    }

    public static final Date transDateMinute(Date date) throws ParseException {
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_HH_MM)
                .parse(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_HH_MM_SS).format(date));
    }

    public static final Date parseDate(String dateString) throws ParseException {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(dateString);
    }

    public static final Date parseTime(String timeString) throws ParseException {
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_HH_MM_SS).parse(timeString);
    }

    public static final Date parseCurrentMinute(String dateString) throws ParseException {
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_HH_MM).parse(dateString);
    }

    /**
     * 取指定时间前几个小时或后几个小时的时间
     *
     * @param hours
     * @return
     */
    public static Date getDateByHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 校验指定日期值是否满足指定日期格式
     *
     * @param dateValue 当前日期值
     * @param regExp    指定日期格式
     * @return true/false
     */
    public static Boolean checkDateValid(String dateValue, String regExp) {
        if (StringUtils.isBlank(dateValue) || StringUtils.isBlank(dateValue.trim())) {
            return true;
        }
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(regExp);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(dateValue);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 将指定日期转换成指定格式字符串形式
     *
     * @param date   指定日期
     * @param format 日期格式
     * @return String
     */
    public static String date2Str(Date date, String format) {
        if (null == date) {
            return "";
        }
        if (StringUtils.isBlank(format)) {
            format = DEFAULT_DATE_FORMAT;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 当前时间点
     *
     * @return Date
     * @throws ParseException ParseException
     */
    public static Date getCurrTime() throws ParseException {
        Date date = new Date(System.currentTimeMillis());
        date = new SimpleDateFormat(DEFAULT_DATE_TIME_HH_MM).parse(date2Str(date, DEFAULT_DATE_TIME_HH_MM));
        return date;
    }

    /**
     * 当前日期时间点
     *
     * @return Date
     * @throws ParseException
     */
    public static Date getCurrDate(String format) throws ParseException {
        if (StringUtils.isBlank(format)) {
            format = DEFAULT_DATE_TIME_FORMAT_HH_MM_SS;
        }
        Date date = new Date(System.currentTimeMillis());
        date = new SimpleDateFormat(format).parse(date2Str(date, format));
        return date;
    }

    public static final Date parseTime(String dateStr, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        // 防止SimpleDateFormat会比较宽松地验证日期
        dateFormat.setLenient(false);
        return dateFormat.parse(dateStr);
    }

    public static final Date parseTime(Date date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date2Str(date, format));
    }

    public static final String getTimeStr(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final String formatDateStr(String dateStr, String format) throws ParseException {
        String dstDateStr = dateStr;
        Date date = new SimpleDateFormat(format).parse(dstDateStr);
        return date2Str(date, format);
    }

    /**
     * 两个日期范围内的日期对象Date
     * 已经按升序排好序
     *
     * @param startDate
     * @param endDate
     * @return yyyy-MM-dd
     */
    public static List<Date> getStaticDates(Date startDate, Date endDate) throws ParseException {
        List<Date> dateList = new ArrayList<>();
        if (startDate == null || endDate == null) {
            return dateList;
        }
        startDate = parseTime(startDate, DEFAULT_DATE_FORMAT);
        endDate = parseTime(endDate, DEFAULT_DATE_FORMAT);
        if (startDate.compareTo(endDate) == 0) {
            dateList.add(startDate);
            return dateList;
        }
        List<String> existDates = new ArrayList<>();
        dateList.add(startDate);
        existDates.add(date2Str(startDate, ""));
        Date tempDate = getDateByHours(startDate, 24);
        while (tempDate.compareTo(endDate) <= 0) {
            dateList.add(tempDate);
            tempDate = getDateByHours(tempDate, 24);
            existDates.add(date2Str(tempDate, ""));
        }
        if (!existDates.contains(date2Str(endDate, ""))) {
            dateList.add(endDate);
        }

        return dateList;
    }
}