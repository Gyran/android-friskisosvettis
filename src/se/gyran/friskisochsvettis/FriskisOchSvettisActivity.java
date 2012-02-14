package se.gyran.friskisochsvettis;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

import se.gyran.android.friskisochsvettis.R;
import se.gyran.android.friskisochsvettis.cities.City;
import se.gyran.android.friskisochsvettis.cities.Norrkoping;
import se.gyran.se.android.friskisochsvettis.adapters.WorkoutsAdapter;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class FriskisOchSvettisActivity extends ListActivity {
	private static final String TAG = "Main";
	
	private City city;
	private WorkoutsAdapter adapter;
	
    private Date date;
    
    /**/
    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    
    static final int DATE_DIALOG_ID = 0;
     /**/
    
    
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_workouts);
        
        // capture view elements
        this.mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        this.mPickDate = (Button) findViewById(R.id.pickDate);
        
        // add listener
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        
        // current date
        date = new Date();
        mYear = date.getYear();
        mMonth = date.getMonth();
        mDay = date.getDate();
        
        city = new Norrkoping();

        this.adapter = new WorkoutsAdapter(this, R.layout.workout_list_item, city.getWorkouts());
        
        updateDisplay();
    }
    
 // updates the date in the TextView
    private void updateDisplay() {
    	mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mYear).append("-")
                        .append(mMonth).append("-")
                        .append(mDay).append(" "));
    	
        try {
        	city.updateWorkouts(date);
			//city.updateWorkouts(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setListAdapter(this.adapter);
        
    }
    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
            
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }
    
}