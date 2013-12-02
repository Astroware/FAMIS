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
import android.widget.PopupWindow;
import android.widget.TableRow.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class AccountManagementScreenv3 extends Activity {

	private ArrayList<Button> accountButtons;
	private ArrayList<Button> updateAccountButtons;
	private ArrayList<Button> deleteAccountButtons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_management_screenv3);

	
	Button back = (Button)findViewById(R.id.accountback);
	Button home = (Button)findViewById(R.id.accounthome);
	Button search = (Button)findViewById(R.id.accountsearch);
	Button add = (Button)findViewById(R.id.accountlistadd);
	
	back.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		}
	});
	
	home.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AccountManagementScreenv3.this, ClientScreen.class);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    startActivity(intent);
			//finish();
			//overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		}
	});
	
	search.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
		}
	});
	
	add.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
		}
	});
	
	createButtons();
	
	}
	
	//Each account will have a new button created for them and will be displayed in the screen.
		//These buttons will all be similar 
		public void createButtons() {
			accountButtons = new ArrayList<Button>();
			updateAccountButtons = new ArrayList<Button>();
			deleteAccountButtons = new ArrayList<Button>();
			TableLayout tl = (TableLayout)findViewById(R.id.accounttable);
			//empty what was previously within this layout
			tl.removeAllViewsInLayout();
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,DigitsToPixels.dpToPixel(50, getBaseContext()), 1f);
			LayoutParams bp = new LayoutParams(100,DigitsToPixels.dpToPixel(50, getBaseContext()));
			for (int i=0; i<25; i++) { //3 is a placeholder for number of accounts
				TableRow accountRow= new TableRow(this);
				Button user = new Button (this);
				Button update = new Button (this);
				Button delete = new Button (this);
				user.setText("User");
				user.setBackgroundResource(R.drawable.red);
				user.setTextColor(Color.parseColor("white"));
				user.setTypeface(null, Typeface.BOLD_ITALIC);
				user.setTextSize(20);
				accountRow.addView(user,lp);
				accountButtons.add(user);
				
				update.setText("");
				update.setBackgroundResource(R.drawable.update_button);
				update.setTextColor(Color.parseColor("white"));
				update.setTypeface(null, Typeface.BOLD_ITALIC);
				update.setTextSize(20);
				accountRow.addView(update,bp);
				updateAccountButtons.add(update);

				delete.setText("");
				delete.setBackgroundResource(R.drawable.delete_button);
				delete.setTextColor(Color.parseColor("white"));
				delete.setTypeface(null, Typeface.BOLD_ITALIC);
				delete.setTextSize(20);
				accountRow.addView(delete,bp);
				deleteAccountButtons.add(delete);
				tl.addView(accountRow);
				
				update.setOnClickListener(new View.OnClickListener() {			
					@Override
					public void onClick(View v) {
						//PopupWindow updating = new PopupWindow();
						//
					}
				});
				
				delete.setOnClickListener(new View.OnClickListener() {	
					@Override
					public void onClick(View v) {
						
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

}
