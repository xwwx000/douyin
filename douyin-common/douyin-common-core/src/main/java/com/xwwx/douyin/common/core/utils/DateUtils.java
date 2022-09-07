package com.xwwx.douyin.common.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * @author: 可乐罐
 * @date: 2022/3/19 19:52
 * @description:日期工具类
 */
public class DateUtils {
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM = "yyyy-MM";
    public final static String YYYY = "yyyy";
    public final static String HH_MM_SS = "HH:mm:ss";
    public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public final static String HH_MM = "HH:mm";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String YYYYMMddHHmmss = "yyyyMMddHHmmss";
    public final static String YYYYMMdd = "yyyyMMdd";
    public final static String YYYYMMdd2 = "yyyy/MM/dd";
    public final static String YYYYMMdd3 = "yyyy年MM月dd日";
    public static final String YMD = "yyyyMMdd";
    public final static String YYYYMMDD = "yyyyMMdd";

    public static final long DAY = 24 * 60 * 60 * 1000L;
    public static final long DAY_1 = 8 * 60 * 60 * 1000L;
    public static final int BIRTHDATA_LENGTH_LIMITED = 10;

    public static final String AM_WORK_TIME = "09:30";
    public static final String PM_WORK_TIME = "18:30";
    private static final Map<String, DateFormat> DFS = new HashMap<String, DateFormat>();

    public static String getDateFormat() {
        return Format(new Date(), YYYY_MM_DD);
    }

    public static String getDateTimeFormat() {
        return Format(new Date(), YYYY_MM_DD_HH_MM_SS);
    }

    public static String getDateFormatYYYYMMDD(){
        return Format(new Date(),YYYYMMDD);
    }

    //明天
    public static String getTomorrowDateFormat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return Format(calendar.getTime(), YYYY_MM_DD);
    }

    public static String getqDateFormat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        return Format(calendar.getTime(), YYYY_MM_DD);
    }

    public static String Format(Date date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String  toDayString(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        return year + "年" + month + "月" + day + "日";
    }



    /**
     * 以给定格式返回date类型
     */
    public static Date getDate(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期处理：得到N月后日期 （仅限java1.8使用）
     * 时区：东8区（中国北京时间）
     * @param setoffMonth 月份偏移量
     * @return 日期格式：YYYY-MM-DD
     */
    public static String plusMonth(int setoffMonth) {
        return LocalDate.now(// 时区设置
                ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).plusMonths(setoffMonth) + "";
    }

    /**
     * 获取当前系统时间
     */
    public static String currentSysTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        return time;
    }

    public static DateFormat getFormat(String pattern) {
        DateFormat format = DFS.get(pattern);
        if (format == null) {
            format = new SimpleDateFormat(pattern);
            DFS.put(pattern, format);
        }
        return format;
    }

    public static Date parse(String source, String pattern) {
        if (source == null) {
            return null;
        }
        Date date;
        try {
            date = getFormat(pattern).parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return getFormat(pattern).format(date);
    }

    /**
     * @param year  年
     * @param month 月(1-12)
     * @param day   日(1-31)
     * @return 输入的年、月、日是否是有效日期
     */
    public static boolean isValid(int year, int month, int day) {
        if (month > 0 && month < 13 && day > 0 && day < 32) {
            // month of calendar is 0-based
            int mon = month - 1;
            Calendar calendar = new GregorianCalendar(year, mon, day);
            if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == mon
                    && calendar.get(Calendar.DAY_OF_MONTH) == day) {
                return true;
            }
        }
        return false;
    }

    public static Calendar convert(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 返回指定年数位移后的日期
     */
    public static Date yearOffset(Date date, int offset) {
        return offsetDate(date, Calendar.YEAR, offset);
    }

    /**
     * 返回指定月数位移后的日期
     */
    public static Date monthOffset(Date date, int offset) {
        return offsetDate(date, Calendar.MONTH, offset);
    }

    /**
     * 返回指定天数位移后的日期
     */
    public static Date dayOffset(Date date, int offset) {
        return offsetDate(date, Calendar.DATE, offset);
    }


    /**
     * 返回指定小时位移后的日期
     */
    public static Date hourOffset(Date date, int offset) {
        return offsetDate(date, Calendar.HOUR, offset);
    }

    /**
     * 返回指定分钟位移后的日期
     */
    public static Date minuteOffset(Date date, int offset) {
        return offsetDate(date, Calendar.MINUTE, offset);
    }

    /**
     * 返回指定时间位移一秒的时间
     */
    public static Date secondOffset(Date date, int offset) {
        return offsetDate(date, Calendar.SECOND, offset);
    }

    /**
     * 返回指定日期相应位移后的日期
     *
     * @param date   参考日期
     * @param field  位移单位，见 {@link Calendar}
     * @param offset 位移数量，正数表示之后的时间，负数表示之前的时间
     * @return 位移后的日期
     */
    public static Date offsetDate(Date date, int field, int offset) {
        Calendar calendar = convert(date);
        calendar.add(field, offset);
        return calendar.getTime();
    }

    /**
     * 返回当月第一天的日期
     */
    public static Date firstDay(Date date) {
        Calendar calendar = convert(date);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 返回当月最后一天的日期
     */
    public static Date lastDay(Date date) {
        Calendar calendar = convert(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * 往后推迟n年后，当月最后一天
     *
     * @param date
     * @param n
     * @return
     */
    public static Date lastDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + n);
        Date newDate = calendar.getTime();
        return lastDay(newDate);
    }

    /**
     * 返回date1与date2之间的天数差异，正数表示date1在date2之后，0表示两个日期相差不到24小时，负数表示date1在date2之前<br/>
     * Ex:<br/>
     * dayDiff(2011-08-21 12:30,2011-08-22 13:00) -1<br/>
     * dayDiff(2011-08-21 12:30,2011-08-22 11:00) 0<br/>
     * dayDiff(2011-08-22 12:30,2011-08-21 13:00) 0<br/>
     * dayDiff(2011-08-22 14:30,2011-08-21 13:00) 1<br/>
     * <p style="color:red;font-weight:bold">如果要判断两个日期是否在同年同月同日，不能用此方法，应该用{@link #isInSameDay(Date, Date)}</p>
     *
     * @param date1
     * @param date2
     * @return date1与date2之间的天数差异，正数表示date1在date2之后，0表示两个日期相差不到24小时，负数表示date1在date2之前
     */
    public static int dayDiff(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        return (int) (diff / DAY);
    }

    /**
     * 判断两个日期是否在同年同月同日
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isInSameDay(Date date1, Date date2) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(date1);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(date2);
        return d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR) && d1.get(Calendar.MONTH) == d2.get(Calendar.MONTH)
                && d1.get(Calendar.DATE) == d2.get(Calendar.DATE);
    }

    /*
     * 将当前日期加减n年数。 如传入字符型"-5" 意为将当前日期减去5年的日期 如传入字符型"5" 意为将当前日期加上5年后的日期 返回字串
     * 例(2000-02-01)
     */
    @SuppressWarnings("static-access")
    public static String dateAdd(String to) {
        // 日期处理模块 (将日期加上某些年或减去年数)返回字符串
        int strTo;
        try {
            strTo = Integer.parseInt(to);
        } catch (Exception e) {
            System.out.println("日期标识转换出错!:\n:::" + to + "不能转为数字型");
            e.printStackTrace();
            strTo = 0;
        }
        Calendar strDate = Calendar.getInstance();// java.util包
        strDate.add(strDate.YEAR, strTo); // 日期减 如果不够减会将月变动
        // 生成 (年-月-日) 字符串
        String meStrDate = strDate.get(strDate.YEAR) + "-" + String.valueOf(strDate.get(strDate.MONTH) + 1) + "-"
                + strDate.get(strDate.DATE);
        return meStrDate;
    }

    /**
     * 获取当天0时0分0秒
     *
     * @return
     */
    public static Date today() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        return c.getTime();
    }

    /**
     * 获取某天的0时0分0秒
     *
     * @return
     */
    public static Date today(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        return c.getTime();
    }

    /**
     * 获取某天23时59分59秒
     *
     * @param date
     * @return
     */
    public static Date endTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static float getHolidays(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null) {
            return 0;
        } else {
            float time = 0;
            time = toDate.getTime() - fromDate.getTime();
            time = time / DAY_1;
            return time;
        }
    }

    /**
     * 根据给定的年、月、日新建一个日期并返回，除年、月、日外，其余栏位均为0
     *
     * @param year  年份，如 2010
     * @param month 月份，1-12
     * @param date  日期，1-31
     * @return
     */
    public static Date newDate(int year, int month, int date) {
        Calendar instance = Calendar.getInstance();
        instance.clear();
        instance.set(year, month - 1, date);
        return instance.getTime();
    }

    /**
     * 获取 YYMMDD
     *
     * @param date
     * @return
     */
    public static String getYYMMDD(Date date) {
        String dt = "";
        if (date != null) {
            SimpleDateFormat yymmdd = new SimpleDateFormat(YMD);
            dt = yymmdd.format(date);
            dt = dt.substring(2);
        }
        return dt;
    }


    /**
     * 获取 YYMM
     *
     * @param date
     * @return
     */
    public static String getYYMM(Date date) {
        String dt = "";
        if (date != null) {
            SimpleDateFormat yymmdd = new SimpleDateFormat(YMD);
            dt = yymmdd.format(date);
            dt = dt.substring(2, 6);
        }
        return dt;
    }

    /**
     * UNIX时间戳, 10位
     */
    public static int timestamp(Date date) {
        Long timestamp = date.getTime() / 1000;
        return new Long(timestamp).intValue();
    }

    public static int calLastedTime(Date startDate) {
        long a = new Date().getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }

    public static String getEndDate(Date date, int x) throws ParseException {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat
                ("yyyy/MM/dd");
        String safedate = bartDateFormat.format(date);
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date d = format.parse(safedate);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(c.DATE, 30);
        Date temp_date = c.getTime();
        return format.format(temp_date);
    }

    /**
     * 返回string 指定时间位移天数后的月份
     */
    public static String getMonth(Date date, int x) {
        String time = "";
        try {
            time = getEndDate(new Date(), 30);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] s = time.split("/");
        String month = s[1];
        return month;
    }

    /**
     * 返回string 指定时间位移天数后的日期
     */
    public static String getDay(Date date, int x) {
        String time = "";
        try {
            time = getEndDate(new Date(), 30);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] s = time.split("/");
        String day = s[2];
        return day;
    }

    public static Date calToDate(Calendar calendar) {
        if (calendar != null) {
            Date date = calendar.getTime();
            return date;
        } else {
            return null;
        }
    }

    public static Calendar dateToCal(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            return calendar;
        }
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     */
    public static int dayForWeek(String pTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(parse(pTime, YYYY_MM_DD));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    //获取系统年份
    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }


    /**
     * 获取当年的第一天
     * @return
     */
    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     * @return
     */
    public static Date getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    public static Date setHHMMss( Date date ,int hour,int minute,int second){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH), hour, minute, second);
        Date setDate = cal.getTime();
        return setDate;
    }

    public static void main(String[] args) {
        System.out.println(getqDateFormat());
        int YEAR = 2020;
        int MONTH = 9;
        int DAY = 4;
        int HOUR = 22;
        int MINUTE = 00;
        int SECOND = 00;
        String overTime = YEAR+"-"+MONTH+"-"+DAY+" "+HOUR+":"+MINUTE+":"+SECOND;
        Date date9422 =DateUtils.parse(overTime,DateUtils.YYYY_MM_DD_HH_MM_SS);
        Date date95 =DateUtils.parse(YEAR+"-"+MONTH+"-"+DAY+" "+21+":"+59+":"+SECOND,DateUtils.YYYY_MM_DD_HH_MM_SS);
        System.out.println(Format(date95,YYYY_MM_DD_HH_MM_SS));
//        System.out.println(Format(today(new Date()),YYYY_MM_DD_HH_MM_SS));

        if(!date95.before(date9422)){
            System.out.println("1");
        }else {
            System.out.println("0");
        }
    }
}
