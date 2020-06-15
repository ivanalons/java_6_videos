package com.videos.tools;

import java.util.Calendar;
import java.util.Date;

public class Tools {
	
	public static int[] getDateArray(Date date) { 
		
		int[] dateArray = new int[3];
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		dateArray[0] = cal.get(Calendar.DAY_OF_MONTH);
		dateArray[1] = cal.get(Calendar.MONTH)+1;
		dateArray[2] = cal.get(Calendar.YEAR);

		return dateArray;
	}
	
	public static String dateArrayToString(int[] dateArray) {
		
		return dateArray[0] + " / " + dateArray[1] + " / " + dateArray[2];
		
	}
	
	public static String parseDateToString(Date date) {
		
		int[] array = getDateArray(date);
		String s = dateArrayToString(array);
		
		return s;
	}
	
}
