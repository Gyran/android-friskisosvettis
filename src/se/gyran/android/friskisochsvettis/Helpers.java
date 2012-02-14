package se.gyran.android.friskisochsvettis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {
	
	public static SimpleDateFormat DATEFORMATYMD = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat DATEFORMATHM = new SimpleDateFormat("HH:mm");
	
	public static Date setTime(Date date, String hours, String minutes){
		return setTime(date, Integer.parseInt(hours), Integer.parseInt(minutes));
	}
	// Set the hour and minutes of the date
	public static Date setTime(Date date, int hours, int minutes){
		Date tmpDate = new Date(date.getTime());
		tmpDate.setHours(hours);
		tmpDate.setMinutes(minutes);
		tmpDate.setSeconds(0);
		
		return tmpDate;
	}
	
	public static String readStream(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String str;
		while ((str = br.readLine()) != null) {
	        // str is one line of text; readLine() strips the newline character(s)
			sb.append(str);
	    }
	    br.close();
	    
	    return sb.toString();
	}
}
