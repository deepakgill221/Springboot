package com.qc.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomizeDate 
{
	public  String DateFormat(String converdate) throws Exception
	{
		String str1="1st April "; String str2="31 March "; String str3="till"; String month = "";
		int year = 0;
		int finalYear = 0;
		String responseDate = "";
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = myFormat.parse(converdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		year = cal.get(Calendar.YEAR);
		month =cal.get(Calendar.MONTH)+"";


		if("0".equalsIgnoreCase(month) || "1".equalsIgnoreCase(month) || "2".equalsIgnoreCase(month))
		{
			finalYear = year-1;
			String addYear1=year+"";
			responseDate=str1+finalYear+str3+str2+addYear1;
			System.out.println(responseDate);

		}
		else
		{
			finalYear = year+1;
			String addYear2=year+"";
			responseDate=str1+addYear2+str3+str2+finalYear;
			System.out.println(responseDate);
		}
		return responseDate;
	}

//	public static void main(String [] adf) throws Exception
//	{
//		
//		CustomizeDate lc = new CustomizeDate();
//		lc.DaysDifference();
//	}

//	public String DaysDifference()
//	{
//
//		String dateStart = "2026-05-27";
//		String dateStop = "2016-05-27";
//		String returnDate="";
//
//		//HH converts hour in 24 hours format (0-23), day calculation
//		SimpleDateFormat format = new SimpleDateFormat("yyyy/dd/MM");
//
//		Date d1 = null;
//		Date d2 = null;
//		try {
//			d1 = format.parse(dateStart);
//			d2 = format.parse(dateStop);
//
//			//in milliseconds
//			long diff = d2.getTime() - d1.getTime();
//
//			long diffSeconds = diff / 1000 % 60;
//			long diffMinutes = diff / (60 * 1000) % 60;
//			long diffHours = diff / (60 * 60 * 1000) % 24;
//			long diffDays = diff / (24 * 60 * 60 * 1000);
//            System.out.println("Difference" + diff);
//			System.out.print(diffDays + " days, ");
//			System.out.print(diffHours + " hours, ");
//			System.out.print(diffMinutes + " minutes, ");
//			System.out.print(diffSeconds + " seconds.");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return returnDate;
//
//	}
	
}
