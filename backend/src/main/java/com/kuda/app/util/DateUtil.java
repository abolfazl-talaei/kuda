package com.kuda.app.util;


import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	public static Date getNextDays(Integer days) {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
}
