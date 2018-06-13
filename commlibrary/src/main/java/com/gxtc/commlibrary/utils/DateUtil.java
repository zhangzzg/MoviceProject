package com.gxtc.commlibrary.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DateUtil {

    public static String[] timeFormat(long ms) {// 将毫秒数换算成x分x秒x毫秒
        String[] times     = new String[2];
        int      ss        = 1000;
        int      mi        = ss * 60;
        long     minute    = (ms) / mi;
        long     second    = (ms - (minute * mi)) / ss;
        String   strMinute = minute < 10 ? "0" + minute : "" + minute;
        String   strSecond = second < 10 ? "0" + second : "" + second;
        times[0] = strMinute;
        times[1] = strSecond;
        return times;
    }

    /**
     * Java 毫秒转换为（天：时：分：秒）方法
     *
     * @param ms 毫秒
     * @return 一个装了天 时 分 秒的数组
     */
    public static String[] timeFormatMore(long ms) {// 将毫秒数换算成x天x时x分x秒x毫秒
        String[] times = new String[4];
        int      ss    = 1000;
        int      mi    = ss * 60;
        int      hh    = mi * 60;
        int      dd    = hh * 24;

        long   day       = ms / dd;
        long   hour      = (ms - day * dd) / hh;
        long   minute    = (ms - day * dd - hour * hh) / mi;
        long   second    = (ms - day * dd - hour * hh - minute * mi) / ss;
        String strDay    = day < 10 ? "0" + day : "" + day;
        String strHour   = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        times[0] = strDay;
        times[1] = strHour;
        times[2] = strMinute;
        times[3] = strSecond;
        return times;
    }

    /**
     * 获取当前时间
     *
     * @param formatType yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurTime(String formatType) {
        Calendar         calendar = new GregorianCalendar();
        Date             date     = calendar.getTime();
        SimpleDateFormat format   = new SimpleDateFormat(formatType);
        // SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }

    /**
     * @param time
     * @param formatType yyyy/MM/dd
     * @return
     */
    public static String formatTime(Long time, String formatType) {
        String format;
        try {
            SimpleDateFormat formater = new SimpleDateFormat(formatType);
            format = formater.format(new Date(time));
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param date
     * @param formatType 例 yyyy/MM/dd
     * @return
     */
    public static String formatDate(Date date, String formatType) {
        return formatTime(date.getTime(), formatType);
    }

    //    Wed Nov 30 00:00:00 GMT+00:00 2016
    public static String formatDate(String date, String formatType) {
        String format = null;
        try {
            SimpleDateFormat formater = new SimpleDateFormat(formatType);
            ParsePosition    pos      = new ParsePosition(0);
            Date             parse    = formater.parse(date, pos);
//            Log.e("tag", "....: " + parse);
            format = String.valueOf(parse);
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //    EEE MMM dd hh:mm:ss  ->   yyyy转yyyy-MM-dd
    public static String formatYMD(String date) {
        String format = null;
        try {
            SimpleDateFormat formater = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy",
                    Locale.ENGLISH);
            Date parse = formater.parse(date);
            Log.e("tag", "....: " + parse);
            SimpleDateFormat sdf   = new SimpleDateFormat("yyyy-MM-dd");
            String           time2 = sdf.format(parse);
            format = String.valueOf(time2);
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间+/-addDate天
     *
     * @param addDate
     * @param formatType
     * @return
     */
    public static String getCurTimeAddND(int addDate, String formatType) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, addDate);
        Date             date   = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        return format.format(date);
    }

    /**
     * 获取当前时间+/-hour
     *
     * @param hour
     * @param formatType
     * @return
     */
    public static String getCurTimeAddH(int hour, String formatType) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.HOUR_OF_DAY, hour);
        Date             date   = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        return format.format(date);
    }


    /*
   * 将时间转换为时间戳
   */
    public static String dateToStamp(String s) {
        String           res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date             date             = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }


    /**
     * 获取当前时间前30分钟
     *
     * @return
     */
    public static String getCurTimeAdd30M() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, 30);
        Date             date   = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }

    /**
     * 获取当前时间前+20分钟
     *
     * @return
     */
    public static String getCurTimeAdd20M() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, 20);
        Date             date   = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }

    /**
     * 获取当前时间前+40分钟
     *
     * @return
     */
    public static String getCurTimeAdd40M() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, 40);
        Date             date   = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }

    /**
     * @param datestr 2009-01-12 09:12:22
     * @return 星期几
     */
    public static String getweekdayBystr(String datestr) {
        if ("".equals(datestr)) {
            return "";
        }
        int y = Integer.valueOf(datestr.substring(0, 4));
        int m = Integer.valueOf(datestr.substring(5, 7)) - 1;
        int d = Integer.valueOf(datestr.substring(8, 10));

        Calendar cal = Calendar.getInstance();
        cal.set(y, m, d);
        int    dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;// 今天是星期几
        String wstr      = null;
        switch (dayOfWeek) {
            case 1:
                wstr = "一";
                break;
            case 2:
                wstr = "二";
                break;
            case 3:
                wstr = "三";
                break;
            case 4:
                wstr = "四";
                break;
            case 5:
                wstr = "五";
                break;
            case 6:
                wstr = "六";
                break;
            case 0:
                wstr = "日";
                break;
            default:
                wstr = "";
                break;
        }
        return "星期" + wstr;
    }

    /**
     * 获取今天星期几
     */
    public static String getCurrWeekday() {
        Calendar cal = Calendar.getInstance();
        int    dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;// 今天是星期几
        String wstr      = null;

        switch (dayOfWeek) {
            case 1:
                wstr = "一";
                break;
            case 2:
                wstr = "二";
                break;
            case 3:
                wstr = "三";
                break;
            case 4:
                wstr = "四";
                break;
            case 5:
                wstr = "五";
                break;
            case 6:
                wstr = "六";
                break;
            case 0:
                wstr = "日";
                break;
            default:
                wstr = "";
                break;
        }
        return wstr;
    }

    /**
     * 今天的序号
     *
     * @return
     */
    public static int getDayofWeekIndex() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayOfWeekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;// 今天是星期几
        return dayOfWeekIndex;
    }

    /**
     * 获取时间串
     *
     * @param longstr 秒
     * @return 1月前 1周前 1天前 1小时前 1分钟前
     */
    public static String getTimeStrByLong(String longstr) {

        Calendar calendar = new GregorianCalendar();
        Date     date     = calendar.getTime();
        Long     clv      = date.getTime();
        Long     olv      = Long.valueOf(longstr);

        calendar.setTimeInMillis(olv * 1000); // 转毫秒
        Date date2 = calendar.getTime();

        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String           curtime = format.format(date2);

        Long   belv   = clv - olv * 1000;
        String retStr = "";
        // 24 * 60 * 60 * 1000;
        Long daylong  = Long.valueOf("86400000");
        Long hourlong = Long.valueOf("3600000");
        Long minlong  = Long.valueOf("60000");

        if (belv >= daylong * 30) {// 月

            Long mul = belv / (daylong * 30);
            retStr = retStr + mul + "月";
            belv = belv % (daylong * 30);
            return retStr + "前";
        }
        if (belv >= daylong * 7) {// 周

            Long mul = belv / (daylong * 7);
            retStr = retStr + mul + "周";
            belv = belv % (daylong * 7);
            return retStr + "前";
        }
        if (belv >= daylong) {// 天

            Long mul = belv / daylong;
            retStr = retStr + mul + "天";
            belv = belv % daylong;
            return retStr + "前";
        }
        if (belv >= hourlong) {// 时
            Long mul = belv / hourlong;
            retStr = retStr + mul + "小时";
            belv = belv % hourlong;
            return retStr + "前";
        }
        if (belv >= minlong) {// 分
            Long mul = belv / minlong;
            retStr = retStr + mul + "分钟";
            return retStr + "前";
        }
        return "";
    }

    /**
     * 今天明天后天
     *
     * @param todaystr
     * @return
     */
    public static String getTodayZh(String todaystr) {
        Calendar         calendar = new GregorianCalendar();
        Date             date     = calendar.getTime();
        SimpleDateFormat format   = new SimpleDateFormat("yyyy-MM-dd");
        String           today    = format.format(date);
        if (today.equals(todaystr)) return "今天";

        calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        format = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = format.format(date);
        if (tomorrow.equals(todaystr)) return "明天";

        calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 2);
        date = calendar.getTime();
        format = new SimpleDateFormat("yyyy-MM-dd");
        String aftertomo = format.format(date);
        if (aftertomo.equals(todaystr)) return "后天";
        return "";
    }

    /**
     * 判断时间是否过期
     *
     * @param deadLine
     * @return
     */
    public static boolean isDeadLine(String deadLine) {

        long currentTimeMillis = System.currentTimeMillis();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = sdf.parse(deadLine);
//            long anotherTimeMillis = date.getTime();
        // 大于
        return (currentTimeMillis - Long.valueOf(deadLine)) < 0;

    }

    /**
     * 是否是今天
     *
     * @param timeStr
     * @param pattern
     * @return
     */
    public static boolean isToday(String timeStr, String pattern) {
        try {
            SimpleDateFormat formater   = new SimpleDateFormat(pattern);
            Date             date1      = formater.parse(timeStr);
            Date             date       = new Date();
            String           otherStr   = formater.format(date1);
            String           curtimeStr = formater.format(date);
            return otherStr.equals(curtimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 日期格式化转换
     *
     * @param oldDateStr
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String changeFormater(String oldDateStr, String oldPattern, String newPattern) {
        if ("".equals(oldDateStr)) {
            return "";
        }
        try {
            SimpleDateFormat oldFormater = new SimpleDateFormat(oldPattern);
            SimpleDateFormat newFormater = new SimpleDateFormat(newPattern);
            Date             date        = oldFormater.parse(oldDateStr);
            return newFormater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 一个星期后的7天
     *
     * @return
     */
    public static String getNextWeekDayStrNew() {
        StringBuffer sb  = new StringBuffer();
        Calendar     cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 7);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        sb.append("-");
        cal.add(Calendar.DAY_OF_MONTH, 6);
        day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        return sb.toString();
    }


    public static Date getNetDate(Date date, int addDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, addDate);
        return cal.getTime();
    }

    public static String getCurWeekDayStrNew() {
        StringBuffer sb  = new StringBuffer();
        Calendar     cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        sb.append("-");
        cal.add(Calendar.DAY_OF_MONTH, 6);
        day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        return sb.toString();
    }


    /**
     * 倒计时
     *
     * @param time
     * @param curTime
     * @return
     */
    public static String[] downTime(Long time, Long curTime) {
        String[]     strings = new String[4];
        long         l       = time - curTime;
        long         day     = l / (24 * 60 * 60 * 1000);
        long         hour    = (l / (60 * 60 * 1000) - day * 24);
        long         min     = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long         s       = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuffer sBuffer = new StringBuffer();
        strings[0] = day < 10 ? "0" + day : "" + day;
        strings[1] = hour < 10 ? "0" + hour : "" + hour;
        strings[2] = min < 10 ? "0" + min : "" + min;
        strings[3] = s < 10 ? "0" + s : "" + s;
        return strings;
    }


    public static String[] countDown(long millisUntilFinished) {
        String[]     strings = new String[4];
        long         l       = millisUntilFinished;
        long         day     = l / (24 * 60 * 60 * 1000);
        long         hour    = (l / (60 * 60 * 1000) - day * 24);
        long         min     = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long         s       = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuffer sBuffer = new StringBuffer();
        strings[0] = day < 10 ? "0" + day : "" + day;
        strings[1] = hour < 10 ? "0" + hour : "" + hour;
        strings[2] = min < 10 ? "0" + min : "" + min;
        strings[3] = s < 10 ? "0" + s : "" + s;
        return strings;
    }


    public static String[] countDownNotAddZero(long millisUntilFinished) {
        String[]     strings = new String[4];
        long         l       = millisUntilFinished;
        long         day     = l / (24 * 60 * 60 * 1000);
        long         hour    = (l / (60 * 60 * 1000) - day * 24);
        long         min     = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long         s       = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuffer sBuffer = new StringBuffer();
        strings[0] = /*day < 10 ? "0" + day : */"" + day;
        strings[1] =/* hour < 10 ? "0" + hour : */"" + hour;
        strings[2] = /*min < 10 ? "0" + min :*/ "" + min;
        strings[3] = /*s < 10 ? "0" + s : */"" + s;
        return strings;
    }

    /**
     * 多少时间之前
     *
     * @param time "operatorTime":"2014-02-18 16:39:37"
     * @return
     */
    public static String diffCurTime(long time) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date2 = df.parse(curTime);
//            Date date1 = df.parse(time);
            Date         date2   = new Date(System.currentTimeMillis());
            Date         date1   = new Date(time);
            long         l       = date2.getTime() - date1.getTime();
            long         day     = l / (24 * 60 * 60 * 1000);
            long         hour    = (l / (60 * 60 * 1000) - day * 24);
            long         min     = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long         s       = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            StringBuffer sBuffer = new StringBuffer();
//			if (day > 0) {
//				sBuffer.append(day + "天");
//			}
//			if (hour > 0) {
//				sBuffer.append(hour + "小时");
//			}
//			if (min > 0) {
//				sBuffer.append(min + "分");
//			}
//			if (s > 0) {
//				sBuffer.append(s + "秒");
//			}
            if (day > 2) {
                return null;
            }
            if (day > 0 && day < 2) {
                return day + "天前";
            }
            if (hour > 0) {
                return hour + "小时前";
            }
            if (min > 0) {
                return min + "分前";
            }
            if (s > 0) {
                return s + "秒前";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String diffCurTime(long time, String format) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
//            Date date2 = df.parse(curTime);
//            Date date1 = df.parse(time);
            Date         date2   = new Date(System.currentTimeMillis());
            Date         date1   = new Date(time);
            long         l       = date2.getTime() - date1.getTime();
            long         day     = l / (24 * 60 * 60 * 1000);
            long         hour    = (l / (60 * 60 * 1000) - day * 24);
            long         min     = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long         s       = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            StringBuffer sBuffer = new StringBuffer();
//			if (day > 0) {
//				sBuffer.append(day + "天");
//			}
//			if (hour > 0) {
//				sBuffer.append(hour + "小时");
//			}
//			if (min > 0) {
//				sBuffer.append(min + "分");
//			}
//			if (s > 0) {
//				sBuffer.append(s + "秒");
//			}
            if (day > 2) {
                return df.format(new Date(time));
            }
            if (day > 0 && day < 2) {
                return day + "天前";
            }
            if (hour > 0) {
                return hour + "小时前";
            }
            if (min > 0) {
                return min + "分前";
            }
            if (s > 0) {
                return s + "秒前";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        if (!TextUtils.isEmpty(s)) {
            String           res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long             lt               = new Long(s);
            Date             date             = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } else return "";

    }

    public static String stampToDate(String s,String format) {
        if (!TextUtils.isEmpty(s)) {
            String           res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);//yyyy-MM-dd
            long             lt               = new Long(s);
            Date             date             = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } else return "";

    }

    /**
     * 将时间戳转成多少 天 时 分钟 前        本项目用的是这个方法
     *
     * @param s 时间戳
     * @return
     */
    public static String showTimeAgo(String s) {
        if (!"".equals(s)) {
            String           curTime = DateUtil.stampToDate(s);
            SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = format.parse(curTime);
                return RelativeDateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "";
        } else return "";
    }

    /**
     * 将长时间格式时间转换为字符串 MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter  = new SimpleDateFormat("MM-dd");
        String           dateString = formatter.format(dateDate);
        return dateString;
    }

    //时间差
    public static String getDatePoor(long entTime, long startTime) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = entTime - startTime;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;

        if (day != 0) {
            return day + "天" + hour + "小时" + min + "分" + sec + "秒";
        } else if (hour != 0) {
            return hour + "小时" + min + "分" + sec + "秒";
        } else if (min != 0) {
            return min + "分" + sec + "秒";
        } else if (sec != 0){
            return sec + "秒";
        }
        return "";
    }


    /**
     * 仿微信聊天时间格式化显示
     */

    /**
     *  时间戳格式转换
     */
    static String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public static String getFormatChatTime(long timesamp) {
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timesamp);

        String timeFormat="M月d日 HH:mm";
        String yearTimeFormat="yyyy年M月d日 HH:mm";
        String am_pm="";
        int hour=otherCalendar.get(Calendar.HOUR_OF_DAY);
        if(hour>=0&&hour<6){
            am_pm="凌晨";
        }else if(hour>=6&&hour<12){
            am_pm="早上";
        }else if(hour==12){
            am_pm="中午";
        }else if(hour>12&&hour<18){
            am_pm="下午";
        }else if(hour>=18){
            am_pm="晚上";
        }
        timeFormat="M月d日 "+ am_pm +"HH:mm";
        yearTimeFormat="yyyy年M月d日 "+ am_pm +"HH:mm";

        boolean yearTemp = todayCalendar.get(Calendar.YEAR)==otherCalendar.get(Calendar.YEAR);
        if(yearTemp){
            int todayMonth=todayCalendar.get(Calendar.MONTH);
            int otherMonth=otherCalendar.get(Calendar.MONTH);
            if(todayMonth==otherMonth){//表示是同一个月
                int temp=todayCalendar.get(Calendar.DATE)-otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timesamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timesamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth=todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if(dayOfMonth==todayOfMonth){//表示是同一周
                            int dayOfWeek=otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if(dayOfWeek!=1){//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK)-1] + getHourAndMin(timesamp);
                            }else{
                                result = getTime(timesamp,timeFormat);
                            }
                        }else{
                            result = getTime(timesamp,timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timesamp,timeFormat);
                        break;
                }
            }else{
                result = getTime(timesamp,timeFormat);
            }
        }else{
            result=getYearTime(timesamp,yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time,String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time,String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }
}
