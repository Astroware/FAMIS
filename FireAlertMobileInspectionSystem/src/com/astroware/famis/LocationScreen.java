package com.astroware.famis;

import java.util.ArrayList;

import controlClasses.DigitsToPixels;

import entityClasses.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

public class LocationScreen extends Activity {

	private Client currentClient;
	private ArrayList<Button> locationButtons;
	private EditText searchbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_screen);
		searchbar = (EditText)findViewById(R.id.searchbar2);
		Button back = (Button)findViewById(R.id.locationback);
		Button search =(Button)findViewById(R.id.buttonsearch);
	//Receive the intent from the previous activity and retrieve the passed Client object
    Intent in =getIntent();
    currentClient = (Client)in.getSerializableExtra("selectedClient");
    //The following function will parse the xml document for all locations associated with the current client selected
    locationParse(currentClient);
    createButtons();
	back.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LocationScreen.this, ClientScreen.class));
		}
	});
	
	search.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			createButtons();
		}
	});
	
	}

	//Currently a placeholder for the xml parse for client locations
	public void locationParse(Client currentClient) {
		currentClient.m_serviceAddress = new ArrayList<ServiceAddress>();
		currentClient.m_serviceAddress.add(new ServiceAddress("S1","123 Sesame Street","N6G 2P4", "", "London", "Ontario", "Canada","ID001","20131009 09:49PM"));
	}
	
	//Create a an array of buttons for each location under the current client. Each button will be the same and will have 
	//the service address on it
	public void createButtons() {
		locationButtons =  new ArrayList<Button>();
		TableLayout tl = (TableLayout)findViewById(R.id.locationtable);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,DigitsToPixels.dpToPixel(50, getBaseContext()));
		tl.removeAllViews();
		for (int i=0; i<currentClient.m_serviceAddress.size(); i++) {
			if (currentClient.m_serviceAddress.get(i).getAddress().toLowerCase().startsWith(searchbar.getText().toString().toLowerCase().trim())){
				locationButtons.add(new Button(this));
				locationButtons.get(i).setText(currentClient.m_serviceAddress.get(i).getAddress());
				locationButtons.get(i).setBackgroundResource(R.drawable.client_button);
				locationButtons.get(i).setTextColor(Color.parseColor("white"));
				locationButtons.get(i).setTypeface(null, Typeface.BOLD_ITALIC);
				locationButtons.get(i).setTextSize(20);
				tl.addView(locationButtons.get(i), lp);
			
				//final int j was created because a final int is required inside of the onClick function
				final int j=i;
			
				locationButtons.get(i).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent in= new Intent(LocationScreen.this, EquipmentScreen.class);
						in.putExtra("selectedLocation", currentClient.m_serviceAddress.get(j));
						startActivity(in);
						overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					}
				});
			}
		}
	}
	
	@Override
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
