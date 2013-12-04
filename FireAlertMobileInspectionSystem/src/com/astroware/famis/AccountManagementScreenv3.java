package com.astroware.famis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controlClasses.AccountControl;
import controlClasses.DigitsToPixels;
import controlClasses.PasswordHash;
import entityClasses.Inspector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableRow.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AccountManagementScreenv3 extends Activity {

	private ArrayList<Button> accountButtons;
	private ArrayList<Button> updateAccountButtons;
	private ArrayList<Button> deleteAccountButtons;
	EditText field1;
	EditText field2;
	EditText field3;
	EditText field4;
	
	Point p;
	
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
			if (p != null)
				showPopup(AccountManagementScreenv3.this, p);
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
			tl.invalidate();
			ArrayList<Inspector> inspect = new ArrayList<Inspector>();
			inspect = AccountControl.getInspectorList();
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,DigitsToPixels.dpToPixel(50, getBaseContext()), 1f);
			LayoutParams bp = new LayoutParams(100,DigitsToPixels.dpToPixel(50, getBaseContext()));
			for (int i=0; i<inspect.size(); i++) {
				TableRow accountRow= new TableRow(this);
				Button user = new Button (this);
				Button update = new Button (this);
				Button delete = new Button (this);
				user.setText(inspect.get(i).getName());
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
				final int j=i;
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
						try {
							AccountControl.deleteInspector(j);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						createButtons();
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
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	 
	   int[] location = new int[2];
	   Button button = (Button) findViewById(R.id.accountlistadd);
	 
	   // Get the x, y location and store it in the location[] array
	   // location[0] = x, location[1] = y.
	   button.getLocationOnScreen(location);
	 
	   //Initialize the Point with x, and y positions
	   p = new Point();
	   p.x = location[0];
	   p.y = location[1];
	}
	 
	// The method that displays the popup.
	private void showPopup(final Activity context, Point p) {

	 
	   // Inflate the popup_layout.xml
	   LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
	   LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   View layout = layoutInflater.inflate(R.layout.popup_add_account, viewGroup);
	 
	   // Creating the PopupWindow
	   final PopupWindow popup = new PopupWindow(context);
	   popup.setContentView(layout);
	   Display display = getWindowManager().getDefaultDisplay();
	   int popupWidth = display.getWidth();
	   int popupHeight = 550;
	   popup.setWidth(popupWidth);
	   popup.setHeight(popupHeight);
	   popup.setFocusable(true);
	 
	   // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
	   int OFFSET_X = 0;
	   int OFFSET_Y = 60;
	   field1 = (EditText)layout.findViewById(R.id.field1);
	   field2 = (EditText)layout.findViewById(R.id.field2);
	   field3 = (EditText)layout.findViewById(R.id.field3);
	   field4 = (EditText)layout.findViewById(R.id.field4);
	   // Clear the default translucent background
	   popup.setBackgroundDrawable(getBaseContext().getWallpaper());
	   // Displaying the popup at the specified location, + offsets.
	   popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
	 
	   // Getting a reference to Close button, and close the popup when clicked.
	   Button close = (Button) layout.findViewById(R.id.close);
	   close.setOnClickListener(new OnClickListener(){
		 @Override
		 public void onClick(View v) {
			 String password = field4.getText().toString();
			 String hashedpassword = "";
			 try {
				hashedpassword = PasswordHash.createHash(password);
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidKeySpecException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			 try {
				AccountControl.addInspector(field1.getText().toString(), field2.getText().toString(), field3.getText().toString(), hashedpassword, false);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 popup.dismiss();
			 createButtons();
		 }
	   });
	   
	}
}
