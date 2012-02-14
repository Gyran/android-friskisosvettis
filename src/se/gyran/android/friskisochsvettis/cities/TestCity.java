package se.gyran.android.friskisochsvettis.cities;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

import se.gyran.android.friskisochsvettis.provider.ICitiesIds;
import android.util.Log;

public class TestCity extends City {
	private final String TAG = "Testcity";
	private final String NAME = "Testcity";
	private final String SHORT_NAME = "tc";
	private final int CITYID = ICitiesIds.TESTCITY;
	private final String URL = "http://blaha";
	
	@Override
	public void updateWorkouts() throws ClientProtocolException, IOException {
		Workout w;
		Date d;
		for(int i = 0; i<10;i++){
			w = new Workout();
			d = new Date();
			d.setHours(9 + i);
			w.setStartDate(d);
			d = new Date();
			d.setHours(10 + i);
			w.setEndDate(d);
			w.setName("Workout nr " + i);
			w.setInstructor("Emma Kolm");
			
			workouts.add(w);
		}
	}

}
