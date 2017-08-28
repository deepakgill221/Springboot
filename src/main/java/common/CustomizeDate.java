package common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
public class CustomizeDate 
{
	public String addYear(String date1, String date2)
	{
		String finalDate="";
		try{
			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dr = myFormat.parse(date1);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dr);
			cal.add(Calendar.YEAR, Integer.parseInt(date2));
			Date nextYear = cal.getTime();
			finalDate=myFormat.format(nextYear);
			System.out.println(finalDate);
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return finalDate;
	}
	public  String DateFormat(String converdate) throws Exception
	{
		String str1="1st April "; String str2="31 March "; String str3="till"; String month = "";
		Date date = new Date();
		int year = 0;
		int finalYear = 0;
		String responseDate = "";
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat myFormat2 = new SimpleDateFormat("dd-MMM-yyyy");
		String currentDate=myFormat2.format(date);
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
		}
		else
		{
			String addYear2=year+"";
			responseDate=str1+addYear2+str3+currentDate;
		}
		return responseDate;
	}



	public String DaysDifference(String dateStart, String dateStop)
	{
		String returnDate="";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
			long diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			int year=(int) (diffDays/365);
			int yearRem=(int) (diffDays%365);
			int month=yearRem/30;
			int monReminder=yearRem%30;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnDate;
	}

	public int getMonth(String dateStart, String dateStop)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		Date d2 = null;
		int month=0;
		try {
			d1 = format.parse(dateStop);
			d2 = format.parse(dateStart);
			long diff = d2.getTime() - d1.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
		    month=(int) (diffDays/30.4);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return month;
	}
	
	public int getYear(String dateStart, String dateStop)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Date d1 = null;
		Date d2 = null;
		int year=0;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
			long diff = d2.getTime() - d1.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			year=(int) (diffDays/365);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return year;
	}
	public int comparetwoDates(String firstDate, String secondDate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(firstDate);
		Date date2 = sdf.parse(secondDate);
		System.out.println("date1 : " + sdf.format(date1));
		System.out.println("date2 : " + sdf.format(date2));

		if (date1.compareTo(date2) > 0)
		{
			return 0;
		} else if (date1.compareTo(date2) < 0) {
			return 1;
		} else if (date1.compareTo(date2) == 0) {
			return 2;
		} else {
			System.out.println("How to get here?");
		}
		return 3;
	}
	public String subtractMonth(String date1, String variable)
	{
		String finalDate="";
		try{
			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dr = myFormat.parse(date1);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dr);
			cal.add(Calendar.MONTH, -Integer.parseInt(variable));
			Date nextYear = cal.getTime();
			finalDate=myFormat.format(nextYear);
			System.out.println(finalDate);
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return finalDate;
	}


}
