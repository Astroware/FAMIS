package com.astroware.famis;

import controlClasses.EquipmentControl;
import entityClasses.ServiceAddress;
import android.app.Activity;
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
		createTable();
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.overview, menu);
	    return true;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);
	}
	public void createTable()
	{
		TableLayout tl = (TableLayout)findViewById(R.id.Overviewtable);
		tl.removeAllViewsInLayout();
		ServiceAddress current = EquipmentControl.getInstance().getLocation();
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT, 1f);
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
