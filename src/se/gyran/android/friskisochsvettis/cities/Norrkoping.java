package se.gyran.android.friskisochsvettis.cities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import se.gyran.android.friskisochsvettis.Helpers;
import se.gyran.android.friskisochsvettis.provider.ICitiesIds;
import se.gyran.android.friskisochsvettis.provider.IWorkoutStatuses;
import se.gyran.android.friskisochsvettis.provider.IWorkoutTypes;
import android.util.Log;

public class Norrkoping extends City {
		private static final String TAG = "Norrköpping";
		private static final String NAME = "Norrköping";
		private static final String SHORT_NAME = "Nrk";
		private static final int CITYID = ICitiesIds.NORRKOPING;
		private static final String HOMEPAGE = "http://nrk.friskissvettis.se/";
	
		private Pattern reRow = Pattern.compile("PassRow(Odd)?\">(.+?)</tr>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reTime = Pattern.compile("PassCellTime[^>]+?>([0-9]{2}):([0-9]{2})-([0-9]{2}):([0-9]{2})<", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reName = Pattern.compile("PassCellName[^>]+?>(.+?)<", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reUnit = Pattern.compile("PassCellUnit[^>]+?>(.+?)<", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reInstructor = Pattern.compile("PassCellLeader[^>]+?>(.+?)<", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reDropin = Pattern.compile("PassCellDropin[^>]+?>(.+?)<", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reSlots = Pattern.compile("PassCellSlots\">(.+?)<", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reSlotsLeft = Pattern.compile("PassCellSlotsLeft[^>]+?>(.+?)<", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reStatus = Pattern.compile("PassCellStatus[^>]+?>(.+?)<", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reButton = Pattern.compile("PassCellButton[^>]+?>(.+?)<", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		private Pattern reViewState = Pattern.compile("__VIEWSTATE\"\\s+value=\"([^\"]+)\"");
		
		private HttpPackage http;
		
		private void preScrape()
		{			
			this.http = new HttpPackage();
			
			try {
				this.http.setUrl("http://v3143.bokning11.pastelldata.se/start.aspx?GUID=1419&newuser=1");
				this.http.openGet();
				this.http.saveCookies();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void scrape()
		{
			try {
				this.http.setUrl("http://v3143.bokning11.pastelldata.se/booking/ClassBookingExtForm.aspx");
				this.http.openGet();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Matcher workoutMatcher = reRow.matcher(this.http.getResponse());
			while(workoutMatcher.find())
			{
				this.addWorkout(this.matchWorkout(workoutMatcher.group(2).trim()));
			}
		}

	public void updateWorkouts(Date date) throws ClientProtocolException, IOException{
		this.preScrape();
		this.scrape();	
	}
		
	@Override
	public void updateWorkouts() throws ClientProtocolException, IOException {
		this.updateWorkouts(new Date());
	}
	
	private Workout matchWorkout(String row)
	{
		Matcher m;
		
		Date date = new Date();
		
		Workout w = new Workout();
		// Name
		m = this.reName.matcher(row);
		if(m.find())
			w.setName(m.group(1));
		
		/* Time
		 * Group 	- content
		 * 1		- start hours
		 * 2		- start minutes
		 * 3		- end hours
		 * 4		- end minutes
		 */
	
		m = this.reTime.matcher(row);
		if(m.find()){
			w.setStartDate(Helpers.setTime(date, m.group(1), m.group(2)));
			w.setEndDate(Helpers.setTime(date, m.group(3), m.group(4)));
		}
		
		// Unit
		m = this.reUnit.matcher(row);
		if(m.find())
			w.setFacility(m.group(1));
		
		// Instructor
		m = this.reInstructor.matcher(row);
		if(m.find())
			w.setInstructor(m.group(1));
		
		// Dropin
		// See how many dropin slots there is and set the type of the workout
		m = this.reDropin.matcher(row);
		if(m.find()){
			int dropinSlots = Integer.parseInt(m.group(1)); 
			if(dropinSlots >= 0){
				w.setType(IWorkoutTypes.DROPIN);
				w.setSlotsMax(dropinSlots);
			}
		}
		
		// Bookable slots
		m = this.reSlots.matcher(row);
		if(m.find()){
			int bookableSlots = Integer.parseInt(m.group(1));
			if(bookableSlots >= 0){
				w.setType(IWorkoutTypes.BOOKING);
				w.setSlotsMax(bookableSlots);
			}
		}
		
		// SlotsLeft
		m = this.reSlotsLeft.matcher(row);
		if(m.find()){
			w.setSlotsLeft(Integer.parseInt(m.group(1)));
		}
		
		/* Status
		 * The different statuses:
		 * "Platser kvar" 
		 * "Fullt"
		 */

		m = this.reStatus.matcher(row);
		if(m.find()){	
			w.setStatus(this.getStatus(m.group(1)));
		}
		
		Log.v(Norrkoping.TAG, w.getName());
		
		return w;
	}
	
	
	/* Returns the statusnumber depending on the status */
	private int getStatus(String s){
		int status;
		
		if(s.equalsIgnoreCase("Platser kvar")){
			status = IWorkoutStatuses.SEATSLEFT;
		}else if(s.equalsIgnoreCase("Fullt")){
			status = IWorkoutStatuses.FULL;
		}else if(s.equalsIgnoreCase("Drop-in")){	
			status = IWorkoutStatuses.DROPIN;
		}else if(s.equalsIgnoreCase("Ej bokbar")){	
			status = IWorkoutStatuses.UNBOOKABLE;
		}else{
			status = IWorkoutStatuses.UNKNOWN;
		}
		
		return status;
	}
	
	public static class HttpPackage
	{
		private String cookies;
		private URL url;
		private HttpURLConnection http;
		private String response;
		/* private något args */
			
		public String getCookies() {
			return cookies;
		}
		public void saveCookies() {
			this.cookies = this.http.getHeaderField("Set-Cookie");
			
		}
		public void openGet() throws IOException {
			this.http = (HttpURLConnection) this.url.openConnection();
			
			if(cookies != null)
			{
				this.http.setRequestProperty("Cookie", this.cookies);
			}
			
			this.response = Helpers.readStream(new BufferedInputStream(this.http.getInputStream()));
		}
		
		public void doPost() {
			
		}
		
		public void setCookies(String cookies) {
			this.cookies = cookies;
		}
		public URL getUrl() {
			return url;
		}
		public void setUrl(String url) throws MalformedURLException {
			this.url = new URL(url);
		}
		public HttpURLConnection getHttp() {
			return http;
		}
		public void setHttp(HttpURLConnection http) {
			this.http = http;
		}
		public String getResponse() {
			return response;
		}
		public void setResponse(String response) {
			this.response = response;
		}
	}
	
}	