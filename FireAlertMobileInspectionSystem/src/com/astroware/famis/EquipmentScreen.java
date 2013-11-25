//TODO: Many functions need to be restructured and taken out in this activity in order to integrate parsing
//All parse functions should be part of the XMLParser class

package com.astroware.famis;

import java.util.ArrayList;
import java.util.List;

import entityClasses.*;
import entityClasses.Device.DeviceType;
import controlClasses.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class EquipmentScreen extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment_screen);
        
        //Create a new editable text field that will allow the user to enter a device id in manually
        final EditText enterManual = (EditText)findViewById(R.id.entermanually);
        
        //Create a button that will allow the user to submit a manually entered device id
        Button searchManual =(Button)findViewById(R.id.buttonentermanally);
        
        //Create a button that will allow the user to submit the inspection
        Button submitInspection =(Button)findViewById(R.id.inspectiondone);
        
        //Receiving the intent and the passed index for the location of the selected ServiceAddress from the previous activity
        Intent in = getIntent();
        int locationIndex = in.getIntExtra("selectedLocation", -1);
	    if (locationIndex != -1) {
	    	EquipmentControl.getInstance().setLocation(locationIndex);
	    }
        
        //This scanner object is created so that its listener is running during this activity
        Scanner scanner = new Scanner(); 
        
        //When the user manually searches for a device, if the bar code number is found, then the
        //inspection form for that device is brought up. If the bar code number is not found,
        //then a message should be displayed to the user
        searchManual.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				if (!(enterManual.getText().toString().equals("Manually Enter ID"))) {
					
					//TODO: write a function to get the text from enterManual and 
					//search for that bar code ID in the floor (or the entire building?)
					//and then call that function here (have the function return a boolean
					//and make the call inside of an if statement)
					//!!! The function will also need to be called from the onReceive function
					//in the Scanner class
					
					//Make an intent to move to a new activity based on the device entered
					//TODO: Move this code into a function so that it can be called from here and
					//from the Scanner class (onReceive function)
					
					EquipmentControl.getInstance().setFloor(0);
					
					Intent in = new Intent(EquipmentScreen.this, ExtinguisherForm.class);
					startActivity(in);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					
				}
			}
		});
	}
        
        

	protected void onStart() {
		super.onStart();
		Scanner scanner = new Scanner();
		//TODO: This needs to be changed so that it is not hard coded
		EquipmentControl.getInstance().setFloor(0);
        createTables();	
	}
	
	//This class is used to retrieve the input from the bar code scanner
	//content is the actual value of the input (will be the bar code number)
	public class Scanner {
		private String ACTION_CONTENT_NOTIFY = "android.intent.action.CONTENT_NOTIFY";
		private String content = null;
		
		//TODO: This function needs a call to the function that will be created
		//to check to see if a bar code is found or not
		//TODO: This function also needs a call to the function that will make
		//the intent and call to the next activity
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_CONTENT_NOTIFY)) {
				Bundle bundle = new Bundle();
				bundle  = intent.getExtras();
				content = bundle.getString("CONTENT");
				Intent in= new Intent(EquipmentScreen.this, ExtinguisherForm.class);
				in.putExtra("message", "This Is Being Sent");
				startActivity(in);
			}
		}
	}
		
	//Create an array to hold all of the floors at a service address. The xml document is parsed for all the floors at 
	//a service location and they are placed into the array
	public List<Floor> parseLocation(ServiceAddress serviceAddress) {
		serviceAddress.m_floors = new ArrayList<Floor>();
		for (int i=0; i<1/*# of floors in location*/; i++) {
			serviceAddress.m_floors.add(new Floor("First Floor"));
			parseFloor(serviceAddress.m_floors.get(i));
		}
		
		return serviceAddress.m_floors;
	}
	
	//Parse the xml document for the rooms that are on each floor and add them to an array
	public void parseFloor(Floor floor) {
		//parse # first
		floor.m_rooms = new ArrayList<Room>();
		for (int i=0; i<1/*# of rooms in floor*/; i++) {
			floor.m_rooms.add(new Room("R1", "0"));
			parseRoom(floor.m_rooms.get(i));
		}
	}
	
	//Parse the xml document to find all devices that are in a room and put them into an array
	public void parseRoom(Room room) {
		//parse # first
		room.m_devices = new ArrayList<Device>();
		DeviceType dType;
		for (int i=0; i<4/*# of devices in room*/; i++) {
			
			//note: the reference to "element" below is going to be an XML element
			//if (element == "Extinguisher") {
			if (i==0) {
				dType = DeviceType.EXTINGUISHER;
				room.m_devices.add(new Extinguisher(33101, dType, "East Stair", 10, "ABC", 
													"Amerex", "s123", "dd/mm/yyyy"));
			}	
			//if (element == "Extinguisher") {
			else if (i==1) {
				dType = DeviceType.EXTINGUISHER;
				room.m_devices.add(new Extinguisher(33102, dType, "Elev Lobby", 10, "ABC", 
													"Amerex", "s123", "dd/mm/yyyy"));	
			}	
			//else if (element == "FireHoseCabinet") {
			else if (i==2) {
				dType = DeviceType.FIRE_HOSE_CABINET;
				room.m_devices.add(new FireHoseCabinet(77207, dType, "Staircase D", "dd/mm/yyyy"));	
			}		
			//else if (element == "EmergencyLight") {
			else if (i==3) {
				dType = DeviceType.EMERGENCY_LIGHT;
				room.m_devices.add(new EmergencyLight(88103, dType, "Some where", 2341, "Chubb",
														4, "10W", "10V"));
			}	
			//else if (element == "Kitchen") {	
			//}	
			else {
				//error
			}
		}
	}
	
	//Rhys - can you comment each block in this section?
	//The spacing also needs to be fixed for this activity
	public void createTables() {
		
		for (int i=0; i<EquipmentControl.getInstance().getRoomListSize(); i++) {
			EquipmentControl.getInstance().setRoom(i);
			
			TableLayout MainLayout = (TableLayout)findViewById(R.id.make_rooms_here);
		 	MainLayout.removeAllViewsInLayout();
			
		 	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			LayoutParams buttonParams = new LayoutParams(DigitsToPixels.dpToPixel(50, getBaseContext()),DigitsToPixels.dpToPixel(50, getBaseContext()));
			lp.rightMargin = DigitsToPixels.dpToPixel(10, getBaseContext());
			lp.leftMargin =  DigitsToPixels.dpToPixel(10, getBaseContext());
		 	
			TableRow titleRow= new TableRow(this);
		 	titleRow.setGravity(android.view.Gravity.CENTER);
		 	
		 	TextView title = new TextView(this);
		 	title.setText("Room : " + EquipmentControl.getInstance().getRoom().getId());
		 	title.setGravity(android.view.Gravity.CENTER);
		 	title.setTextSize(25);
		 	title.setTypeface(null, Typeface.BOLD_ITALIC);
		 	
		 	titleRow.addView(title, lp);
		 	MainLayout.addView(titleRow, lp);
		 	
		 	TableRow subtitleRow = new TableRow(this);
		 	TextView subtitleName = new TextView(this);
		 	subtitleName.setText("Equipment");
		 	subtitleName.setGravity(android.view.Gravity.CENTER);
		 	subtitleName.setTextSize(20);
		 	subtitleName.setTypeface(null, Typeface.BOLD);
		 	
		 	TextView subtitleLocation = new TextView(this);
		 	subtitleLocation.setText("Location");
		 	subtitleLocation.setGravity(android.view.Gravity.CENTER);
		 	subtitleLocation.setTextSize(20);
		 	subtitleLocation.setTypeface(null, Typeface.BOLD);
		 	
		 	TextView subtitlePassOrFail = new TextView(this);
		 	subtitlePassOrFail.setText("Completed");
		 	subtitlePassOrFail.setGravity(android.view.Gravity.CENTER);
		 	subtitlePassOrFail.setTextSize(20);
		 	subtitlePassOrFail.setTypeface(null, Typeface.BOLD);
		 	
		 	subtitleRow.addView(subtitleName, lp);
		 	subtitleRow.addView(subtitleLocation, lp);
		 	subtitleRow.addView(subtitlePassOrFail, lp);
		 	MainLayout.addView(subtitleRow, lp);
	        
		 	for (int j=0; j<EquipmentControl.getInstance().getDeviceListSize(); j++) {
		 		EquipmentControl.getInstance().setDevice(j);
	        	
		 		TableRow currentRow = new TableRow(this);
	        	
		 		TextView equipmentName = new TextView(this);
	        	equipmentName.setGravity(android.view.Gravity.CENTER);
	        	equipmentName.setText(EquipmentControl.getInstance().getDevice().toString());
	        	equipmentName.setTextSize(15);
	        	
	        	TextView location = new TextView(this);
	        	location.setText(EquipmentControl.getInstance().getDevice().getLocation());
	        	location.setGravity(android.view.Gravity.CENTER);
	        	location.setTextSize(15);
	        	
	        	Button checkOrX = new Button(this);
	        	if (EquipmentControl.getInstance().getDevice().isComplete()) {
	        		checkOrX.setText("Complete");
	        	}
	        	else {
	        		checkOrX.setText("Incomplete");
	        	}
	        	
	        	checkOrX.setGravity(android.view.Gravity.CENTER);
	        	checkOrX.setTextSize(15);
	        	checkOrX.setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
	        	checkOrX.setHeight(DigitsToPixels.dpToPixel(50, getBaseContext()));
	        	
	        	currentRow.addView(equipmentName,lp);
	        	currentRow.addView(location,lp);
	        	currentRow.addView(checkOrX,buttonParams);
			 	currentRow.setGravity(android.view.Gravity.CENTER);
	        	MainLayout.addView(currentRow,lp);
	        } 
		}
	}
	

	
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_screen, menu);
        return true;
    }
    
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
    }
 
}