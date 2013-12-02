package com.astroware.famis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controlClasses.ClientControl;
import controlClasses.DigitsToPixels;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

public class ClientScreen extends Activity {
	private ArrayList<Button> clientButtons;
	//Create the search bar at the top of the screen
	private EditText searchbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_screen);

			//Create a button that allows the user to go back to the previous page
			Button back = (Button)findViewById(R.id.button67);
			
			//Create a button that allows the user to search through the list for a specified search requirement
			Button search = (Button)findViewById(R.id.buttonsearch);
			
			searchbar= (EditText)findViewById(R.id.searchbar);
			Boolean flag = true;
			System.out.println("what");
			//Parse the XML document to get all the necessary information
			try {
				ClientControl.parseXML();
			} catch (FileNotFoundException e) {
				Toast.makeText(getApplicationContext(), "The Inspection data file required to run this program is missing", Toast.LENGTH_LONG).show();
				System.out.println("here");
				flag = false;
				e.printStackTrace();
			} catch (SAXException e) {
				flag = false;
				e.printStackTrace();
			} catch (IOException e) {
				flag = false;
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				flag = false;
				e.printStackTrace();
			}
			
			if(flag == true)
			{
			
			//Create an array of buttons that will hold all of the clients that the franchisee has
			createButtons();
			
			//Create a listener for when the search button is clicked
			search.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), searchbar.getText().toString().trim(), Toast.LENGTH_LONG).show();
					createButtons();
				}
			});
			}
			//Create a listener for when the back button is pressed
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
					overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
				}
			});
		}
		
		//Each client will have a new button created for them and will be displayed in the screen. These buttons will all be 
		//similar and have the client name on them
		public void createButtons() {
			
			clientButtons = new ArrayList<Button>();

			TableLayout tl = (TableLayout)findViewById(R.id.customertable);
			tl.removeAllViewsInLayout();
			clientButtons.removeAll(clientButtons);
			tl.invalidate();
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, DigitsToPixels.dpToPixel(50, getBaseContext()));
			for (int i=0; i<ClientControl.getInstance().getClientListSize(); i++) {
				clientButtons.add(new Button(this));
				if (ClientControl.getInstance().getClient(i).getName().toLowerCase().startsWith(searchbar.getText().toString().toLowerCase().trim())){	
					clientButtons.get(i).setText(ClientControl.getInstance().getClient(i).getName());
					clientButtons.get(i).setBackgroundResource(R.drawable.client_button);
					clientButtons.get(i).setTextColor(Color.parseColor("white"));
					clientButtons.get(i).setTypeface(null, Typeface.BOLD_ITALIC);
					clientButtons.get(i).setTextSize(20);
					tl.addView(clientButtons.get(i), lp);
					//final int j was created because a final int is required inside of the onClick function
					final int j = i;
					//create an onClick function for each clientButton that moves to the LocationScreen based on the selected client
					clientButtons.get(i).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent in= new Intent(ClientScreen.this, LocationScreen.class);
							in.putExtra("selectedClient", j);
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
			getMenuInflater().inflate(R.menu.client_screen, menu);
			return true;
		}

	    //When the back button is pressed the screen state will switch from the List of Clients screen to the login screen
	    @Override
	    public void onBackPressed() {
	        super.onBackPressed();
	        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
	    }
	    
	    public boolean onTouchEvent(MotionEvent event)
	    {
	    	super.onTouchEvent(event);
	    	InputMethodManager IMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    	IMM.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	    	return true;
	    }
	 
	}
