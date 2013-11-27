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
import android.widget.TableLayout;
import android.widget.Toast;

public class ClientScreen extends Activity {

	private Franchisee franchisee;
	private ArrayList<Button> clientButtons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_screen);

			//Create a button that allows the user to go back to the previous page
			Button back = (Button)findViewById(R.id.button67);
			
			//Create a button that allows the user to search through the list for a specified search requirement
			Button search = (Button)findViewById(R.id.buttonsearch);
			
			//Rhys - need to get rid of this button - remember this was hidden to help with 
			//the spacing of the dynamically created buttons. It should be removed.
			Button client1 = (Button)findViewById(R.id.button4c);
			
			//placeholder for the real button
			Button temp = (Button)findViewById(R.id.buttonTemp);
			
			//Create a new Franchisee object and set it equal to parse(). Parse() is a function that parses the xml document and 
			//returns the value of the name of the franchisee in the xml document.
			franchisee = parseFranchisee();
			
			//Create an array of buttons that will hold all of the clients that the franchisee has
			createButtons();
			
				//Rhys - is there any reason to keep this commented code??
				//Button client2 = new Button(this);
				//client2.setText("Client 2");
				//client2.setTextColor(@android:color/white);
				//client2.setBackgroundResource(R.drawable.backgroundbehindclientlist);
				//TableLayout tl = (TableLayout)findViewById(R.id.customertable);
				//LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
				//tl.addView(client2, lp);
			
			//goesto account management screen when pressed
			temp.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent (ClientScreen.this, AccountManagementScreen.class));	
				}
			});
			
			//Create a listener for when the back button is pressed
				back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent (ClientScreen.this, LoginScreen.class));	
				}
			});
				
			//Create a listener for when the search button is clicked
			search.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "Searching...", Toast.LENGTH_LONG).show();
				}
			});
			client1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "Client 1", Toast.LENGTH_LONG).show();
					
				}
			});
			//Rhys - I believe this goes with the previously mentioned commented code
			//client2.setOnClickListener(new View.OnClickListener() {
				
			//	@Override
			//	public void onClick(View v) {
			//		Toast.makeText(getApplicationContext(), "Client 2", Toast.LENGTH_LONG).show();
					
			//	}
			//});
		}
		
		//Used as a current placeholder using hard coded xml until the parser works
		public Franchisee parseFranchisee(){
			Franchisee franchisee = new Franchisee(1001, "Darwin Fleming");
			franchisee.m_clientList.add(new Client("1001-01", "North Bay Inc.", "North Bay, Muskoka, Parry Sound, Orillia"));
			return franchisee;
		}
		
		//Each client will have a new button created for them and will be displayed in the screen. These buttons will all be 
		//similar and have the client name on them
		public void createButtons() {
			
			clientButtons = new ArrayList<Button>();
			
			for (int i=0; i<franchisee.m_clientList.size(); i++) {
				
				clientButtons.add(new Button(this));
				clientButtons.get(i).setText(franchisee.m_clientList.get(i).getName());
				clientButtons.get(i).setBackgroundResource(R.drawable.client_button);
				clientButtons.get(i).setTextColor(Color.parseColor("white"));
				clientButtons.get(i).setTypeface(null, Typeface.BOLD_ITALIC);
				clientButtons.get(i).setTextSize(20);
				
				TableLayout tl = (TableLayout)findViewById(R.id.customertable);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, DigitsToPixels.dpToPixel(50, getBaseContext()));
				tl.addView(clientButtons.get(i), lp);
				
				//final int j was created because a final int is required inside of the onClick function
				final int j = i;
				
				//create an onClick function for each clientButton that moves to the LocationScreen based on the selected client
				clientButtons.get(i).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent in= new Intent(ClientScreen.this, LocationScreen.class);
						in.putExtra("selectedClient", franchisee.m_clientList.get(j));
						startActivity(in);
						overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					}
				});
			}
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.client_screen, menu);
			return true;
		}

	    //When the back button is pressed the screen state will switch from the List of Clients screen to the login screen
	    @Override
	    public void onBackPressed() {
	        super.onBackPressed();
	        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
	    }
	 
	}
