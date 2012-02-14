package se.gyran.se.android.friskisochsvettis.adapters;

import java.util.ArrayList;

import se.gyran.android.friskisochsvettis.Helpers;
import se.gyran.android.friskisochsvettis.R;
import se.gyran.android.friskisochsvettis.cities.Workout;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class WorkoutsAdapter extends ArrayAdapter<Workout> {

    private ArrayList<Workout> items;
    private Context context;

    public WorkoutsAdapter(Context context, int textViewResourceId, ArrayList<Workout> items) {
    	super(context, textViewResourceId, items);
    	this.items = items;
    	this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = LayoutInflater.from(this.context);
                v = vi.inflate(R.layout.workout_list_item, null);
            }
            Workout w = items.get(position);
            if (w != null) {
            	Log.v("ADAPTER", w.getName());
            		TextView time = (TextView) v.findViewById(R.id.time);
            		TextView name = (TextView) v.findViewById(R.id.name);
            		TextView instructor = (TextView) v.findViewById(R.id.instructor);

            		if(time != null)
            			time.setText(Helpers.DATEFORMATHM.format(w.getStartDate()) + "\n" +
            					Helpers.DATEFORMATHM.format(w.getEndDate()));
            		if(name != null)
            			name.setText(w.getName());
            		if(instructor != null)
            			instructor.setText(w.getInstructor());
            		
            }
            return v;
    }
}