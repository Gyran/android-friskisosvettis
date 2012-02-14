package se.gyran.android.friskisochsvettis.cities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpConnection;
import org.apache.http.client.ClientProtocolException;

import se.gyran.android.friskisochsvettis.provider.ICitiesIds;

public abstract class City implements ICitiesIds {
		protected static String TAG = "City";
		protected static String NAME = "City";
		protected static String SHORT_NAME = "city";
		protected static int CITYID;
		protected static String URL;
	
	protected String username;
	protected String password;
	
	protected ArrayList<Workout> workouts = new ArrayList<Workout>();
	
	/**********  Getters and setters *************/
	public String getUsername()
	{
		return this.username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public ArrayList<Workout> getWorkouts()
	{
		return this.workouts;
	}
	
	public void addWorkout(Workout workout)
	{
		this.workouts.add(workout);
	}
	
	/*********** Getters and setters end ******************/
	
	public void updateWorkouts() throws ClientProtocolException, IOException
	{
		this.updateWorkouts(new Date());
	}
	
	public void updateWorkouts(Date date) throws ClientProtocolException, IOException
	{
		
	}
}

