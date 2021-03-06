package hkust.cse.calendar.apptstorage;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;
import hkust.cse.calendar.userstorage.UserStorageController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/* This class is for managing the Appt Storage according to different actions */
public class ApptStorageControllerImpl {

	/* Remove the Appt from the storage */
	public final static int REMOVE = 1;

	/* Modify the Appt the storage */
	public final static int MODIFY = 2;

	/* Add a new Appt into the storage */
	public final static int NEW = 3;

	/*
	 * Add additional flags which you feel necessary
	 */
	
	/* The Appt storage */
	private ApptStorage mApptStorage;


	/* Create a new object of ApptStorageControllerImpl from an existing storage of Appt */
	public ApptStorageControllerImpl(ApptStorage storage) {
		mApptStorage = storage;
	}


	/* Retrieve the Appt's in the storage for a specific user within the specific time span */
	public Appt[] RetrieveAppts(User entity, TimeSpan time) {
		return mApptStorage.RetrieveAppts(entity, time);
	}

	// overload method to retrieve appointment with the given joint appointment id
	public Appt[] RetrieveAppts(User user, int joinApptID) {
		return mApptStorage.RetrieveAppts(user,joinApptID);
	}
	
	public Appt[] RetrieveJointApptsInWaitlist() {
		return mApptStorage.RetrieveJointApptsInWaitlist();
	}	
	
	/* Manage the Appt in the storage
	 * parameters: the Appt involved, the action to take on the Appt */
	
	// part arraylist appt
	public void ManageAppt(Appt appt, int action) {

		if (action == NEW) {				// Save the Appt into the storage if it is new and non-null
			if (appt == null)
				return;
			mApptStorage.SaveAppt(appt);
		} else if (action == MODIFY) {		// Update the Appt in the storage if it is modified and non-null
			if (appt == null)
				return;
			mApptStorage.UpdateAppt(appt);
		} else if (action == REMOVE) {		// Remove the Appt from the storage if it should be removed
			mApptStorage.RemoveAppt(appt);
		} 
	}

	/* Manage the Appt in the storage
	 * parameters: the Appt involved, the action to take on the Appt */
	
	public boolean checkApptsHaveLocation(String locationName) {
		return mApptStorage.checkApptsHaveLocation(locationName);
	}

	/* Get the defaultUser of mApptStorage */
	public User getDefaultUser() {
		return mApptStorage.getDefaultUser();
	}
	public int getAssignedApptID() {
		return mApptStorage.getAssignedApptID();
	}
	public void setAssignedApptID(int id){
		mApptStorage.setAssignedApptID(id);
	}
	public int getAssignedJointID() {
		return mApptStorage.getAssignedJointID();
	}
	public void setAssignedJointID(int id){
		mApptStorage.setAssignedJointID(id);
	}
	// method used to load appointment from xml record into hash map
	public boolean checkOverLap(Appt appt, Appt entry){
		return mApptStorage.checkOverlap(appt,entry);
	}
	
	public boolean checkOverLaps(ArrayList<Appt> apptlist){
		return mApptStorage.checkOverLaps(apptlist);
	}
	/* begining of xml management functions*/
	public void loadApptFromXml(User user, HashMap<TimeSpan,Appt> appts){
		mApptStorage.loadApptFromXml(user,appts);
	}

	public void saveApptToXml(Appt appt) {
		mApptStorage.saveApptToXml(appt);
	}

	public void removeApptFromXml(Appt appt) {
		mApptStorage.removeApptFromXml(appt);
	}
	/* delete the old appts in the xml and resave all them to the */
	public void closeSaving(){
		for(Iterator<Entry<TimeSpan, Appt>>it=mApptStorage.mAppts.entrySet().iterator();it.hasNext();){
		     Entry<TimeSpan, Appt> entry = it.next();
				removeApptFromXml(entry.getValue());
		}
		for(Iterator<Entry<TimeSpan, Appt>>it=mApptStorage.mAppts.entrySet().iterator();it.hasNext();){
		     Entry<TimeSpan, Appt> entry = it.next();
				saveApptToXml(entry.getValue());
		}
	}
	/* end of xml management functions*/
	public Appt[] retrieveAllAppts(User user) {
		return mApptStorage.retrieveAllAppts(user);
	}

	public boolean checkotherApptsHaveLocation(Appt appt, String locationName) {
		return mApptStorage.checkotherApptsHaveLocation(appt, locationName);
	}
	
	public TimeSpan[] getSuggestedTimeSpan(User[] users, Timestamp stamp) {
		return mApptStorage.getSuggestedTimeSpan(users, stamp);
	}

	public boolean checkotherUsersTimespan(TimeSpan suggestedTimeSpan, User[] users) {
		return mApptStorage.checkotherUsersTimespan(suggestedTimeSpan, users);
	}
	
	public boolean checkLocationCapacityEnough(Appt appt) {
		return mApptStorage.checkLocationCapacityEnough(appt);
	}
	
	public void deleteApptWithLocationName(String locationName) {
		mApptStorage.deleteApptWithLocationName(locationName);
	}
	
	public Appt[] getApptForLocation(Location location) {
		return mApptStorage.getApptForLocation(location);
}
	
	public Appt[] getApptThatLocationInToBeDelete() {
		return mApptStorage.getApptThatLocationInToBeDelete();
	}
}
