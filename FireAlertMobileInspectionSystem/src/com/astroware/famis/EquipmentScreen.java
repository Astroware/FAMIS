//TODO: Many functions need to be restructured and taken out in this activity in order to integrate parsing
//All parse functions should be part of the XMLParser class

package com.astroware.famis;

import controlClasses.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class EquipmentScreen extends Activity {

	private TextView floornum;
	private int currentfloor=1;
	TableLayout MainLayout;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment_screen);
		MainLayout = (TableLayout)findViewById(R.id.make_rooms_here);
        //Create a new editable text field that will allow the user to enter a device id in manually
        final EditText enterManual = (EditText)findViewById(R.id.entermanually);
        //Create a button that will allow the user to submit a manually entered device id
        Button searchManual =(Button)findViewById(R.id.buttonentermanally);
        Button back = (Button)findViewById(R.id.buttonbacktolocation);
        Button swiperight = (Button)findViewById(R.id.swiperight);
        Button swipeleft = (Button)findViewById(R.id.swipeleft);
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
				if (!((enterManual.getText().toString().equals("Manually Enter ID")) || (enterManual.getText().toString().trim().isEmpty()))) {
					
					int barcode = 0;
					boolean cont = true;
					
					try {
						String num = enterManual.getText().toString();
						barcode = Integer.parseInt(num.trim().replaceAll("\\s",""));
					} catch (NumberFormatException e) {
						Toast.makeText(getApplicationContext(), ("Number is too large!"), Toast.LENGTH_SHORT).show();
						cont = false;
					}
					
					if (cont) {
						if (EquipmentControl.getInstance().checkDevice(barcode)) {
							openDeviceForm();
						}
						
						else {
							Toast.makeText(getApplicationContext(), ("Device Not Found!"), Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
			}
		});
		swiperight.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		    	if (currentfloor==EquipmentControl.getInstance().getFloorListSize())
		    	{
		    		EquipmentControl.getInstance().setFloor(0);
		    		currentfloor =1;
		    	}
		    	else
		    		
		    	{
		    		currentfloor+=1;
		    		EquipmentControl.getInstance().setFloor(currentfloor-1);
		    	}
		        createTables();
		    }
		});
		swipeleft.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (currentfloor==1)
		    	{
		    		currentfloor =EquipmentControl.getInstance().getFloorListSize();
		    		EquipmentControl.getInstance().setFloor(currentfloor-1);
		    	}
		    	else
		    	{
		    		currentfloor-=1;
		    		EquipmentControl.getInstance().setFloor(currentfloor-1);
		    	}
		        createTables();
			}
		});
		
		findViewById(R.id.equipscreen).setOnTouchListener(new SwipeControl(this) {
		    public void onSwipeLeft() {
		    	if (currentfloor==EquipmentControl.getInstance().getFloorListSize())
		    	{
		    		EquipmentControl.getInstance().setFloor(0);
		    		currentfloor =1;
		    	}
		    	else
		    		
		    	{
		    		currentfloor+=1;
		    		EquipmentControl.getInstance().setFloor(currentfloor-1);
		    	}
		        createTables();
		    }
		    public void onSwipeRight() {
		    	if (currentfloor==1)
		    	{
		    		currentfloor =EquipmentControl.getInstance().getFloorListSize();
		    		EquipmentControl.getInstance().setFloor(currentfloor-1);
		    	}
		    	else
		    	{
		    		currentfloor-=1;
		    		EquipmentControl.getInstance().setFloor(currentfloor-1);
		    	}
		        createTables();
		    }
		});
		
		submitInspection.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(EquipmentControl.getInstance().getLocation().isComplete()) {
					Toast.makeText(getBaseContext(), "Submitting Inspection", Toast.LENGTH_SHORT).show();
					finish();
				}
				else {
					Toast.makeText(getBaseContext(), "Inspection not complete!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
        
	protected void onStart() {
		super.onStart();
		//should this scanner be here? What about the one in onCreate()?
		Scanner scanner = new Scanner();
		//TODO: This needs to be changed so that it is not hard coded
		EquipmentControl.getInstance().setFloor(currentfloor-1);
		
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
				
				if (EquipmentControl.getInstance().checkDevice(Integer.parseInt(content))) {
					openDeviceForm();
				}
				
				else {
					Toast.makeText(getApplicationContext(), ("Device Not Found!"), Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
	//Rhys - can you comment each block in this section?
	//The spacing also needs to be fixed for this activity
	public void createTables() {
		
        floornum = (TextView)findViewById(R.id.equipmenttitle);
		floornum.setText(EquipmentControl.getInstance().getFloor().getName());
		
		//empty what was previously within this layout
		if (MainLayout != null) {
			MainLayout.removeAllViewsInLayout();
			MainLayout.invalidate();
		}
		
		//make layout of the table
		 for (int i=0; i<EquipmentControl.getInstance().getRoomListSize();i++) {
			EquipmentControl.getInstance().setRoom(i);
			
		 	//set how things in the new layout should appear (Parameters) and add margins
		 	LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT, 1f);
		 	LayoutParams lprow =  new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, 1f);
			LayoutParams buttonParams = new LayoutParams(DigitsToPixels.dpToPixel(50, getBaseContext()),DigitsToPixels.dpToPixel(50, getBaseContext()));
			lp.rightMargin = DigitsToPixels.dpToPixel(5, getBaseContext());
			lp.leftMargin =  DigitsToPixels.dpToPixel(5, getBaseContext());
			//Make the Title Row with the name of the room
			TableRow titleRow= new TableRow(this);
		 	titleRow.setGravity(android.view.Gravity.CENTER);
		 	TextView title = new TextView(this);
		 	title.setText("Room : " + EquipmentControl.getInstance().getRoom().getId());
		 	title.setGravity(android.view.Gravity.CENTER);
		 	title.setTextSize(25);
		 	title.setTypeface(null, Typeface.BOLD_ITALIC);
		 	titleRow.addView(title, lprow);
		 	MainLayout.addView(titleRow, lprow);
		 	
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
		 	subtitleRow.addView(subtitleName, lprow);
		 	subtitleRow.addView(subtitleLocation, lprow);
		 	subtitleRow.addView(subtitlePassOrFail, lprow);
		 	MainLayout.addView(subtitleRow, lprow);
	        
		 	//For loop adding all of the pieces of equipment into the table
		 	for (int j=0; j<EquipmentControl.getInstance().getDeviceListSize(); j++) {
		 		EquipmentControl.getInstance().setDevice(j);
		 		
		 		TableRow currentRow = new TableRow(this);
		 		TextView equipmentName = new TextView(this);
	        	equipmentName.setGravity(android.view.Gravity.CENTER);
	        	equipmentName.setText(EquipmentControl.getInstance().getDevice().toString());
	        	equipmentName.setTextSize(12);
	        	
	        	TextView location = new TextView(this);
	        	location.setText(EquipmentControl.getInstance().getDevice().getLocation());
	        	location.setGravity(android.view.Gravity.CENTER);
	        	location.setTextSize(12);
	        	
	        	Button checkOrX = new Button(this);
	        	if (EquipmentControl.getInstance().getDevice().isComplete()) {
	        		checkOrX.setText("Complete");
	        	}
	        	else {
	        		checkOrX.setText("Incomplete");
	        	}
	        	
	        	checkOrX.setGravity(android.view.Gravity.CENTER);
	        	checkOrX.setTextSize(12);
	        	checkOrX.setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
	        	checkOrX.setHeight(DigitsToPixels.dpToPixel(50, getBaseContext()));
	        	currentRow.addView(equipmentName,lprow);
	        	currentRow.addView(location,lprow);
	        	currentRow.addView(checkOrX,lprow);
	        	MainLayout.addView(currentRow,lprow);
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
    
    public void openDeviceForm() {
    	Intent in = new Intent(EquipmentScreen.this, ExtinguisherForm.class);
		startActivity(in);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
    
    public boolean onTouchEvent(MotionEvent event)
    {
    	InputMethodManager IMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	IMM.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    	return true;
    }
 
}

