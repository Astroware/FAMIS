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
	
	private ServiceAddress currentLocation;
	private List<Floor> floors;
	private int times = 0;
	private TextView floornum;
	private int currentfloor=1;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment_screen);
        floornum = (TextView)findViewById(R.id.equipmenttitle);
		floornum.setText("Floor "+currentfloor);
        //Create a new editable text field that will allow the user to enter a device id in manually
        final EditText enterManual = (EditText)findViewById(R.id.entermanually);
        //Create a button that will allow the user to submit a manually entered device id
        Button searchManual =(Button)findViewById(R.id.buttonentermanally);
        
        //Create a button that will allow the user to submit the inspection
        Button submitInspection =(Button)findViewById(R.id.inspectiondone);
        
        //Receiving the intent and the passed ServiceAddress object from the previous activity
        Intent in = getIntent();
        currentLocation = (ServiceAddress)in.getSerializableExtra("selectedLocation");
        
        //The xml document will be parsed for all floors that are at the current service location
        floors= parseLocation(currentLocation);
        
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
					
					Intent fillform = new Intent();
					Bundle bundle = new Bundle();
					
					bundle.putSerializable("device", floors.get(0).m_rooms.get(0).m_devices.get(0));
					
					fillform.putExtras(bundle);
					fillform.setClass(EquipmentScreen.this, ExtinguisherForm.class);
					
					startActivity(fillform);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					
				}
			}
		});
	}
        
        

	protected void onStart() {
		times=times+1;
		super.onStart();
		Scanner scanner = new Scanner();
        createTables(floors.get(0));	
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
			floor.m_rooms.add(new Room("R1", 0));
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
	public void createTables(Floor floor) {
		for (int i=0; i<floor.m_rooms.size();i++) {
			//make layout of the table
			TableLayout MainLayout = (TableLayout)findViewById(R.id.make_rooms_here);
			//empty what was previously within this layout
		 	MainLayout.removeAllViewsInLayout();
		 	//set how things in the new layout should appear (Parameters) and add margins
		 	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			LayoutParams buttonParams = new LayoutParams(DigitsToPixels.dpToPixel(50, getBaseContext()),DigitsToPixels.dpToPixel(50, getBaseContext()));
			lp.rightMargin = DigitsToPixels.dpToPixel(5, getBaseContext());
			lp.leftMargin =  DigitsToPixels.dpToPixel(5, getBaseContext());
		 	
			//Make the Title Row with the name of the room
			TableRow titleRow= new TableRow(this);
		 	titleRow.setGravity(android.view.Gravity.CENTER);
		 	TextView title = new TextView(this);
		 	title.setText("Room : "+floor.m_rooms.get(i).getId());
		 	title.setGravity(android.view.Gravity.CENTER);
		 	title.setTextSize(25);
		 	title.setTypeface(null, Typeface.BOLD_ITALIC);
		 	titleRow.addView(title, lp);
		 	MainLayout.addView(titleRow, lp);
		 	
		 	//Add the second row within the table including how what hte text should look like
		 	TableRow subtitleRow = new TableRow(this);
		 	TextView subtitleName = new TextView(this);
		 	subtitleName.setText("Equipment");
		 	subtitleName.setGravity(android.view.Gravity.CENTER);
		 	subtitleName.setTextSize(15);
		 	subtitleName.setTypeface(null, Typeface.BOLD);
		 	TextView subtitleLocation = new TextView(this);
		 	subtitleLocation.setText("Location");
		 	subtitleLocation.setGravity(android.view.Gravity.CENTER);
		 	subtitleLocation.setTextSize(15);
		 	subtitleLocation.setTypeface(null, Typeface.BOLD);
		 	TextView subtitlePassOrFail = new TextView(this);
		 	subtitlePassOrFail.setText("Completed");
		 	subtitlePassOrFail.setGravity(android.view.Gravity.CENTER);
		 	subtitlePassOrFail.setTextSize(15);
		 	subtitlePassOrFail.setTypeface(null, Typeface.BOLD);
		 	
		 	//Add the second row to the table layout
		 	subtitleRow.addView(subtitleName, lp);
		 	subtitleRow.addView(subtitleLocation, lp);
		 	subtitleRow.addView(subtitlePassOrFail, lp);
		 	MainLayout.addView(subtitleRow, lp);
	        
		 	//For loop adding all of the pieces of equipment into the table
		 	for (int j=0;j<floor.m_rooms.get(i).m_devices.size();j++) {  
	        	
		 		TableRow currentRow = new TableRow(this);
	        	
		 		TextView equipmentName = new TextView(this);
	        	equipmentName.setGravity(android.view.Gravity.CENTER);
	        	equipmentName.setText(floor.m_rooms.get(i).m_devices.get(j).toString()+" ");
	        	equipmentName.setTextSize(12);
	        	
	        	TextView location = new TextView(this);
	        	location.setText(floor.m_rooms.get(i).m_devices.get(j).getLocation());
	        	location.setGravity(android.view.Gravity.CENTER);
	        	location.setTextSize(12);
	        	
	        	Button checkOrX = new Button(this);
	        	if (/*floor.m_rooms.get(i).m_devices.get(j).isComplete()*/times>=2 && j==0) {
	        		checkOrX.setText("Complete");
	        	}
	        	else {
	        		checkOrX.setText("Incomplete");
	        	}
	        	
	        	checkOrX.setGravity(android.view.Gravity.CENTER);
	        	checkOrX.setTextSize(12);
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