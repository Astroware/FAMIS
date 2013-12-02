package com.astroware.famis;

import java.util.ArrayList;

import controlClasses.DigitsToPixels;

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
import android.widget.TableRow;
import android.widget.Toast;

public class AccountManagementScreen extends Activity {
	
	private ArrayList<Button> accountButtons;
	private ArrayList<Button> updateAccountButtons;
	private ArrayList<Button> deleteAccountButtons;
	 

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_management_screen);
		
		
		
		//create a button that will add accounts
		Button addbtn = (Button)findViewById(R.id.btnadd);
		

		
		//Create a button that allows the user to go back to the previous page
		Button back = (Button)findViewById(R.id.button67);
		
		//Create a button that allows the user to search through the list for a specified search requirement
		Button search = (Button)findViewById(R.id.buttonsearch);
		
		//Create an array of buttons that will hold all of the accounts
		createButtons();
		
		//addbtn.setOnClickListener(new 
		
		//Create a listener for when the back button is pressed
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent (AccountManagementScreen.this, ClientScreen.class));	
			}
		});
		
		//Create a listener for when the search button is clicked
		search.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Searching...", Toast.LENGTH_LONG).show();
			}
		});
		
	}
		
		
	//Each account will have a new button created for them and will be displayed in the screen.
	//These buttons will all be similar 
	public void createButtons() {
				
				accountButtons = new ArrayList<Button>();
				updateAccountButtons = new ArrayList<Button>();
				deleteAccountButtons = new ArrayList<Button>();
				
				
				TableLayout tl = (TableLayout)findViewById(R.id.addingtable);
				//empty what was previously within this layout
				tl.removeAllViewsInLayout();
				
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, DigitsToPixels.dpToPixel(50, getBaseContext()));
				
				for (int i=0; i<3; i++) { //3 is a placeholder for number of accounts
					
					accountButtons.add(new Button(this));
					accountButtons.get(i).setText("User");
					accountButtons.get(i).setBackgroundResource(R.drawable.red);
					accountButtons.get(i).setTextColor(Color.parseColor("white"));
					accountButtons.get(i).setTypeface(null, Typeface.BOLD_ITALIC);
					accountButtons.get(i).setTextSize(20);
					tl.addView(accountButtons.get(i), lp);
					
					
					updateAccountButtons.add(new Button(this));
					updateAccountButtons.get(i).setText("");
					updateAccountButtons.get(i).setBackgroundResource(R.layout.update_button);
					updateAccountButtons.get(i).setTextColor(Color.parseColor("white"));
					updateAccountButtons.get(i).setTypeface(null, Typeface.BOLD_ITALIC);
					updateAccountButtons.get(i).setTextSize(20);
					tl.addView(updateAccountButtons.get(i), lp);
					
					deleteAccountButtons.add(new Button(this));
					deleteAccountButtons.get(i).setText("");
					deleteAccountButtons.get(i).setBackgroundResource(R.layout.delete_button);
					deleteAccountButtons.get(i).setTextColor(Color.parseColor("white"));
					deleteAccountButtons.get(i).setTypeface(null, Typeface.BOLD_ITALIC);
					deleteAccountButtons.get(i).setTextSize(20);
					tl.addView(deleteAccountButtons.get(i), lp);
					
					
					/*
					TableRow currentRow = (TableRow)findViewById(R.id.ButtonRows);
					currentRow.addView(accountButtons.get(i), lp);
					
					currentRow.addView(updateAccountButtons.get(i), lp);
					
					currentRow.addView(deleteAccountButtons.get(i), lp);
					
					tl.addView(currentRow, lp);
					*/
					
					/*TableLayout tl2 = (TableLayout)findViewById(R.id.updatetable);
					LayoutParams lp2 = new LayoutParams(LayoutParams.MATCH_PARENT, DigitsToPixels.dpToPixel(50, getBaseContext()));
					tl2.addView(updateAccountButtons.get(i), lp2);
					
					TableLayout tl3 = (TableLayout)findViewById(R.id.deletetable);
					LayoutParams lp3 = new LayoutParams(LayoutParams.MATCH_PARENT, DigitsToPixels.dpToPixel(50, getBaseContext()));
					tl3.addView(deleteAccountButtons.get(i),lp3);*/
					
					//final int j was created because a final int is required inside of the onClick function
					final int j = i;
					
					//create an onClick function for each accountButton that moves to the LocationScreen based on the selected client
					/*accountButtons.get(i).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent in= new Intent(AccountManagementScreen.this, LocationScreen.class);
							in.putExtra("selectedClient", "User");//user is placeholder for all users
							startActivity(in);
							overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
						}
					});*/
					
					
				}
			}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_management_screen, menu);
		return true;
	}

}
