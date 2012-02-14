package se.gyran.android.friskisochsvettis.cities;

import java.util.Date;

import se.gyran.android.friskisochsvettis.provider.IWorkoutStatuses;
import se.gyran.android.friskisochsvettis.provider.IWorkoutTypes;

public class Workout implements IWorkoutTypes, IWorkoutStatuses{
	private static final String TAG = "Workout";

	private Date startDate;
	private Date endDate;
	private String name;
	private String facility;
	private String instructor;
	private int type;
	
	private int slotsMax;
	private int slotsLeft;
	
	private int status;
	
	public Workout(){
		
	}
	
	public Workout(Date startDate, Date endDate, String name, String unit,String instructor, int type,
			int seatsMax, int seatsLeft, int status) {
		this.startDate = startDate;
		this.setEndDate(endDate);
		this.name = name;
		this.facility = unit;
		this.instructor = instructor;
		this.type = type;
		this.slotsMax = seatsMax;
		this.slotsLeft = seatsLeft;
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date start) {
		this.startDate = start;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSlotsMax() {
		return slotsMax;
	}

	public void setSlotsMax(int seatsMax) {
		this.slotsMax = seatsMax;
	}

	public int getSlotsLeft() {
		return slotsLeft;
	}

	public void setSlotsLeft(int seatsLeft) {
		this.slotsLeft = seatsLeft;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	

}
