package com.astroware.famis;

import controlClasses.EquipmentControl;
import entityClasses.ServiceAddress;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

public class Overview extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview_screen);
		
		Button back = (Button)findViewById(R.id.overbackview);
		Button home = (Button)findViewById(R.id.overhomeview);
		
		//Button back = (Button)findViewById(R.id.);
		
		//Creates a listener for when the home button is pressed
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Overview.this, ClientScreen.class);
			    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    startActivity(intent);
			}
		});
		
		//Creates a listener for when the back button is pressed
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);
			}
		});
		
		createTable();
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.overview, menu);
	    return true;
	}
	
	//Overrides when the back button is pressed
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);
	}
	
	//Creates the overview table that lists all of the floors and the rooms
	public void createTable()
	{
		TableLayout tl = (TableLayout)findViewById(R.id.Overviewtable);
		tl.removeAllViewsInLayout();
		ServiceAddress current = EquipmentControl.getInstance().getLocation();
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT, 1f);
		
		//goes through all of the floors and creates a row for the floor
		for (int i=0; i<current.m_floors.size();i++)
		{
			TableRow floorRow = new TableRow (this);
			Button floorName = new Button(this);
			floorName.setText(current.m_floors.get(i).getName());
		 	floorName.setTextSize(25);
		 	floorName.setTypeface(null, Typeface.BOLD_ITALIC);
		 	floorName.setTextColor(Color.WHITE);
		 	if (current.m_floors.get(i).isComplete())
		 		floorName.setBackgroundResource(R.drawable.complete);
		 	else
		 		floorName.setBackgroundResource(R.drawable.incomplete);
		 	floorName.setWidth(100);
		 	floorName.setHeight(50);
		 	floorRow.addView(floorName,lp);
		 	
		 	//Goes through all of the rooms in the floor and inflates the table even more
		 	for (int j = 0; j < current.m_floors.get(i).m_rooms.size();j++)
		 	{
		 		TableRow roomRow = new TableRow (this);
		 		Button spacer = new Button(this);
		 		spacer.setVisibility(View.INVISIBLE);
				Button roomName = new Button(this);
				roomName.setText("Room "+current.m_floors.get(i).m_rooms.get(j).getId());
			 	roomName.setTextSize(25);
			 	roomName.setTypeface(null, Typeface.BOLD_ITALIC);
			 	roomName.setTextColor(Color.WHITE);
			 	if (current.m_floors.get(i).m_rooms.get(j).isComplete())
			 		roomName.setBackgroundResource(R.drawable.complete);
			 	else
			 		roomName.setBackgroundResource(R.drawable.incomplete);
			 	roomName.setWidth(100);
			 	roomName.setHeight(50);
			 	roomRow.addView(spacer);
			 	if (j==0)
			 	{
			 		floorRow.addView(roomName);
			 		tl.addView(floorRow,lp);
			 	}
			 	else 
			 	{
			 		roomRow.addView(roomName,lp);
			 		tl.addView(roomRow,lp);
			 	}
		 	}
		}
	}
}
