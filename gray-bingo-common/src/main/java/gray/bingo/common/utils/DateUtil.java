package gray.bingo.common.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 日期处理工具类
 */
@UtilityClass
public class DateUtil {

    public static final String SDF_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String SDF_YYYYMMDD = "yyyyMMdd";


    /**
     * 创建日期
     *
     * @return {@link  Date}
     */
    public Date now() {
        return new Date();
    }

    /**
     * 时间追加
     *
     * @param date     时间 不为null
     * @param time     时间 不能小于1
     * @param timeUnit 时间类型 不为空
     * @return 时间完后时间
     */
    public Date add(Date date, long time, TimeUnit timeUnit) {
        Date result;

        int timeIntValue;

        if (time > Integer.MAX_VALUE) {
            timeIntValue = Integer.MAX_VALUE;
        } else {
            timeIntValue = Long.valueOf(time).intValue();
        }

        switch (timeUnit) {
            case DAYS:
                result = addDays(date, timeIntValue);
                break;
            case HOURS:
                result = addHours(date, timeIntValue);
                break;
            case MINUTES:
                result = addMinutes(date, timeIntValue);
                break;
            case SECONDS:
                result = addSeconds(date, timeIntValue);
                break;
            case MILLISECONDS:
                result = addMilliseconds(date, timeIntValue);
                break;
            default:
                result = date;
        }
        return result;
    }

    /**
     * 追加年数
     *
     * @param date   日期，不为空
     * @param amount 追加的年数
     * @return 以追加的新日期
     */
    public Date addYears(final Date date, final int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 追加月数
     *
     * @param date   日期，不为空
     * @param amount 追加的周数
     * @return 以追加的新日期
     */
    public Date addMonths(final Date date, final int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 追加周数
     *
     * @param date   日期，不为空
     * @param amount 追加的周数
     * @return 以追加的新日期
     */
    public Date addWeeks(final Date date, final int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    /**
     * 追加天数
     *
     * @param date   日期，不为空
     * @param amount 追加的天数
     * @return 以追加的新日期
     */
    public Date addDays(final Date date, final int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 追加小时
     *
     * @param date   日期，不为空
     * @param amount 追加的分钟数
     * @return 以追加的新日期
     */
    public Date addHours(final Date date, final int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 追加分钟数
     *
     * @param date   日期，不为空
     * @param amount 追加的分钟数
     * @return 以追加的新日期
     */
    public Date addMinutes(final Date date, final int amount) {
        return add(date, Calendar.MINUTE, amount);

    }

    /**
     * 追加秒数
     *
     * @param date   日期，不为空
     * @param amount 追加的秒数
     * @return 以追加的新日期
     */
    public Date addSeconds(final Date date, final int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 追加毫秒数，原对象不变
     *
     * @param date   日期不为空
     * @param amount 追加的毫秒数
     * @return 以追加的新日期
     */
    public Date addMilliseconds(final Date date, final int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }


    private static Date add(Date date, int calendarField, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static String format(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
