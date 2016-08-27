package com.joker.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DatetimeUtil {
	public static final String DATEANDTIME = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORTDATE = "yy-MM-dd";
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_TIME_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_TIME_MILLISECOND_NO_OPERATE = "yyyyMMddHHmmssSSS";

	public static String nowToString() {
		Calendar cal = Calendar.getInstance();
		return dateToString(cal, DATEANDTIME);

	}

	/**
	 * 获取格式化后的当前时间
	 * 
	 * @param outputFormat
	 *            需要格式成的形式，如果为空，则默认格式化为"YYYY-MM-DD hh:mm:ss"
	 * @return date String
	 */
	public static String nowToString(String outputFormat) {
		Calendar cal = Calendar.getInstance();
		if (outputFormat == null)
			return dateToString(cal, DATEANDTIME);
		else
			return dateToString(cal, outputFormat);
	}

	public static String dateToString(Calendar cal, String outputFormat) {
		if (cal == null)
			return "";
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (month.length() < 2)
			month = "0" + month;
		String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		if (date.length() < 2)
			date = "0" + date;
		String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		if (hour.length() < 2)
			hour = "0" + hour;
		String minute = String.valueOf(cal.get(Calendar.MINUTE));
		if (minute.length() < 2)
			minute = "0" + minute;
		String second = String.valueOf(cal.get(Calendar.SECOND));
		if (second.length() < 2)
			second = "0" + second;
		String millisecond = String.valueOf(cal.get(Calendar.MILLISECOND));
		switch (millisecond.length()) {
		case 2:
			millisecond = "0" + millisecond;
			break;
		case 1:
			millisecond = "00" + millisecond;
			break;
		default:
			break;
		}

		String output = "";
		char mask;

		// outputFormat will be null if there are no resources.
		int n = outputFormat != null ? outputFormat.length() : 0;
		for (int i = 0; i < n; i++) {
			mask = outputFormat.charAt(i);
			switch (mask) {
			case 'M':
				output += month;
				i++;
				break;
			case 'd':
				output += date;
				i++;
				break;
			case 'y':
				if (outputFormat.charAt(i + 2) == 'y') {
					output += year;
					i += 3;
				} else {
					output += year.substring(2, 4);
					i++;
				}
				break;
			case 'H':
				output += hour;
				i++;
				break;
			case 'm':
				output += minute;
				i++;
				break;
			case 's':
				output += second;
				i++;
				break;
			case 'S':
				output += millisecond;
				i += 2;
				break;
			default:
				output += mask;
				break;
			}
		}
		return output;
	}

	public static Date toDate(String dateStr, String format) {
		if(StringUtils.isEmpty(dateStr)){
			return null;
		}
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }
	
	/**
	 * 为日期加上时间,不然统计会少一天
	 */
	public static String dateToDateTime(String date, boolean beginOfTheDate) {
		if (beginOfTheDate) {
			return date + " 00:00:00";
		} else {
			return date + " 23:59:59";
		}
	}

	/**
	 * 查询报表时，只传送月份，所以把月份转为日期类型
	 */
	public static String monthToDate(String year, String month,
			boolean isBeginOfMonth) {
		String tempYear = "";
		if (year == null || year.trim().equals("")) {
			Date d = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			tempYear = Integer.toString(cal.get(Calendar.YEAR));
		} else {
			tempYear = year;
		}

		if (isBeginOfMonth)
			return tempYear + "-" + month + "-01";
		else
			return tempYear + "-" + month + "-"
					+ getLastDayOfMonth(year, month);
	}

	/**
	 * 取给定月份的最后一天
	 */
	public static String getLastDayOfMonth(String year, String month) {
		Calendar cal = Calendar.getInstance();
		// 年
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		// 月，因为Calendar里的月是从0开始，所以要-1
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		// 月份加一，得到下个月的一号
		cal.add(Calendar.MONTH, 1);
		// 下一个月减一为本月最后一天
		cal.add(Calendar.DATE, -1);
		return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获得月末是几号
	}

	/**
	 * 当前年份
	 */
	public static Integer getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 当前月份
	 */
	public static Integer getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 当前日期天
	 */
	public static Integer getCurrentDate() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * 字符串转换为Date
	 * 
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Date strToDate(String str) {
		if (str.trim().length() == 10)
			str = dateToDateTime(str, true);
		DateFormat df = new SimpleDateFormat(DATEANDTIME);
		Date date = null;
		try {
			date = df.parse(str.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 两个日期之间相隔天数的共通
	 * 
	 * @param dateFrom
	 *            String
	 * @param dateEnd
	 *            String
	 * @return 天数 Long
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Long getDays(String dateFrom, String dateEnd) {
		Date dtFrom = null;
		Date dtEnd = null;
		dtFrom = strToDate(dateFrom);// yyyy-MM-dd
		dtEnd = strToDate(dateEnd);

		long begin = dtFrom.getTime();
		long end = dtEnd.getTime();
		long inter = end - begin;
		if (inter < 0)
			inter = inter * (-1);
		long dateMillSec = 24 * 60 * 60 * 1000;
		long dateCnt = inter / dateMillSec;
		long remainder = inter % dateMillSec;

		if (remainder != 0)
			dateCnt++;
		return dateCnt;
	}

	/**
	 * 两个日期年份相差
	 * 
	 * @param dateFrom
	 *            String
	 * @return 年数 Integer
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Integer getYears(String dateFrom) {
		Date dtFrom = null;
		Calendar calFrom = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		dtFrom = strToDate(dateFrom);// yyyy-MM-dd
		calFrom.setTime(dtFrom);
		return (calEnd.get(Calendar.YEAR) - calFrom.get(Calendar.YEAR));
	}
	
	public static String formatDateToString(Date date,String format) {
        if(date==null){
            date=new Date();
        }
        if(format==null){
           format="MM月dd日";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        String time = df.format(date);
        return time;
    }

	/**
	 * 格式化
	 * 
	 * @param date
	 * @param format
	 * @return (formatString)String
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static String fomat(String date, String format) {
		Date d = null;
		String str = null;
		try {
			if (format == null) {
				format = "yyyy-MM-dd";
			}
			DateFormat df = new SimpleDateFormat(format);
			d = df.parse(date);
			str = df.format(d);
		} catch (ParseException e) {

		}
		return str;
	}

	/**
	 * 比较日期大小--大于
	 * 
	 * @param date1
	 *            String 起始日期
	 * @param date2
	 *            String 结束日期
	 * @return boolean
	 * @throws ParseException
	 */
	public static boolean isLargeThan(String date1, String date2) {
		Date temp1 = strToDate(date1);
		Date temp2 = strToDate(date2);
		if (temp1.compareTo(temp2) > 0)
			return true;
		return false;
	}

	/**
	 * 比较日期大小--大于等于
	 * 
	 * @param date1
	 *            String 起始日期
	 * @param date2
	 *            String 结束日期
	 * @return boolean *
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static boolean isLargeEqual(String date1, String date2) {
		Date temp1 = strToDate(date1);
		Date temp2 = strToDate(date2);
		return isLargeEqual(temp1, temp2);
	}

	/**
	 * 比较日期大小--大于等于
	 * 
	 * @param date1
	 *            String 起始日期
	 * @param date2
	 *            String 结束日期
	 * @return boolean *
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static boolean isLargeEqual(Date date1, Date date2) {
		if (date1.compareTo(date2) == 0 || date1.compareTo(date2) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获得两个时间时间差
	 * 
	 * @param startdate
	 *            Date 起始日期
	 * @param enddate
	 *            Date 结束日期
	 * @return int
	 */
	public static int getSeconds(Date startdate, Date enddate) {
		long time = enddate.getTime() - startdate.getTime();
		int totalS = new Long(time / 1000).intValue();
		return totalS;
	}

	/**
	 * 把时间往后移.
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();

		try {
			cal.setTime(date);
			cal.add(Calendar.DATE, days);

		} catch (Exception e) {
			// e.printStackTrace();
		}
		return cal.getTime();

	}
	
	/**
	 * 获取当前日期中的天.
	 * @return
	 */
	public static int getDay(Date date){
		Calendar now = Calendar.getInstance();  
		now.setTime(date);
		return now.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前日期中的天.
	 * @return
	 */
	public static int getWeek(Date date){
		Calendar now = Calendar.getInstance();  
		now.setTime(date);
		int result= now.get(Calendar.DAY_OF_WEEK)-1;
		return result<=0 ? 7 : result;
	}
	
	/**
	 * 比较2个时间的大小.
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int compareTime(String time1,String time2){
		if(StringUtils.isEmpty(time1) || StringUtils.isEmpty(time2)){
			return 0;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date d1 = df.parse("2016-07-06 "+time1);
			Date d2 = df.parse("2016-07-06 "+time2);
			return d1.getTime()<=d2.getTime() ? 1 :-1;
		} catch (ParseException e) {
			
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println(DatetimeUtil.nowToString(DATE));
		System.out.println(DatetimeUtil.nowToString(DATE_TIME_MILLISECOND));
		System.out.println(DatetimeUtil
				.nowToString(DATE_TIME_MILLISECOND_NO_OPERATE));
		System.out.println(DatetimeUtil.nowToString(SHORTDATE));
		System.out.println(DatetimeUtil.nowToString(DATEANDTIME));
		System.out.println(strToDate("2011-11-11"));
		System.out.println(monthToDate("2013", "01", false));
		System.out.println(getCurrentYear());
		System.out.println(getYears("2000-01-01 23:23:23"));
		System.out.println(nowToString());
		System.out.println(compareTime("09:00","10:00"));
	}
}
