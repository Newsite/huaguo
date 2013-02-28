package com.touco.huaguo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	private static Calendar calendar = Calendar.getInstance(Locale.CHINA);

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM");

	private static SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy");

	private static SimpleDateFormat calDavDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public static Date getDate3(String dateString) {
		try {
			return dateFormat3.parse(dateString);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String getDateString3(Date date) {
		return dateFormat3.format(date);
	}

	public static Date getDate4(String dateString) {
		try {
			return dateFormat4.parse(dateString);
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date getDate(String dateString) {
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date getDate(int year, int month, int day) {
		calendar.clear();
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}

	public static int getWeekday(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(7);
	}

	public static int getWeekday(int year, int month, int day) {
		calendar.clear();
		calendar.set(year, month - 1, day);
		return calendar.get(7);
	}

	public static boolean isSaturday(Date date) {
		return getWeekday(date) == 7;
	}

	public static boolean isSunday(Date date) {
		return getWeekday(date) == 1;
	}

	public static boolean isWeekend(Date date) {
		return (isSaturday(date)) || (isSunday(date));
	}

	public static Date getDateTime(String dateTimeString) {
		try {
			return dateTimeFormat.parse(dateTimeString);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String getDateTime(Date time) {
		return dateTimeFormat.format(time);
	}

	public static String getDateString(Date date) {
		return dateFormat.format(date);
	}

	public static String getDateString2(Date date) {
		return dateFormat2.format(date);
	}

	public static Date getDateString2(String dateString) {
		try {
			return dateFormat2.parse(dateString);
		} catch (ParseException e) {
		}
		return null;
	}

	public static int getYear(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(1);
	}

	public static int getMonth(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(2) + 1;
	}

	public static int getDay(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(5);
	}

	public static int getDaysCount(int year, int month) {
		calendar.clear();
		calendar.set(1, year);
		calendar.set(2, month - 1);
		return calendar.getActualMaximum(5);
	}

	public static void main(String[] args) throws ParseException {
		Date date1 = getDate("2009-3-1");
		Date date2 = getDate(2009, 3, 1);
		System.out.println(date1);
		System.out.println(date2);
	}

	public static Date addDays(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(5, calendar.get(5) + offset);

		return calendar.getTime();
	}

	public static Date addHours(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(11, calendar.get(11) + offset);

		return calendar.getTime();
	}

	public static Date reduceDays(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(5, calendar.get(5) - offset);

		return calendar.getTime();
	}

	public static Date reduceHours(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(11, calendar.get(11) - offset);

		return calendar.getTime();
	}

	public static Date reduceMinute(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(12, calendar.get(12) - offset);
		return calendar.getTime();
	}

	public static Date reduceMonth(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(2, calendar.get(2) - offset);
		return calendar.getTime();
	}

	public static int getMinute(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(12);
	}

	public static int getHour(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(11);
	}

	public static String getDMYDate() {
		Date date = new Date();
		return calDavDateFormat.format(date);
	}

	public static Long getDaysBetween(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(11, 0);
		fromCalendar.set(12, 0);
		fromCalendar.set(13, 0);
		fromCalendar.set(14, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(11, 0);
		toCalendar.set(12, 0);
		toCalendar.set(13, 0);
		toCalendar.set(14, 0);

		return Long.valueOf((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / 86400000L);
	}

	public static int getHoursBetween(int starth, int endh) {
		if (endh < starth) {
			return 24 + endh - starth;
		}
		return endh - starth;
	}

	public static int getMinutessBetween(int startm, int endm) {
		if (endm < startm) {
			return 60 + endm - startm;
		}
		return endm - startm;
	}
}
