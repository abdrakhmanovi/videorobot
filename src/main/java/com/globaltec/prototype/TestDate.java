package com.globaltec.prototype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TestDate {
    public static void main(String[] args) throws InterruptedException, ParseException {
	
	
	Date startTime = new Date();	
	Thread.sleep(2000);
	Date newTime = new Date();
	long diffLong = newTime.getTime() - startTime.getTime();
	

	Date subtitleTime = new Date(new Date(0).getTime() + diffLong);
	
	SimpleDateFormat sdf = new SimpleDateFormat();
	sdf.setTimeZone(TimeZone.getDefault());
	Date yourUtcDate = sdf.parse(sdf.format(subtitleTime));
	
	System.out.println(yourUtcDate);
     }
}
