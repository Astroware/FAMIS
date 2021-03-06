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
import controlClasses.LoginControl;
import controlClasses.PasswordHash;
import entityClasses.Inspector;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Toast;

public class AccountManagementScreenv3 extends Activity {
	//These arrays are used to hold the buttons that are being dynamically added to the screen. 
	//They are needed so that they system can tell which button is being clicked within the onclick listener 
	private ArrayList<Button> accountButtons;
	private ArrayList<Button> updateAccountButtons;
	private ArrayList<Button> deleteAccountButtons;
	//These text fields are used for getting information within the popup that occurs for adding and modifying accounts
	EditText field1;
	EditText field2;
	EditText field3;
	EditText field4;
	//The title of password needs to bee removed from the screen if the inspector is updating the account not adding an account
	TextView passwordtitle;
	
	Point p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_management_screenv3);

	
	Button back = (Button)findViewById(R.id.accountback);
	Button home = (Button)findViewById(R.id.accounthome);
	Button search = (Button)findViewById(R.id.accountsearch);
	Button add = (Button)findViewById(R.id.accountlistadd);
	search.setVisibility(View.INVISIBLE);
	
	//This is the onclick listener for the back button on the screen, it allows the user to move back to the previous screen
	back.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		}
	});
	
	//This is the onclick listener for the home button on the screen, it allows the user to move back to the client screen
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
	
	//This is the onclick listener for the add user button on the screen, it allows the inspector to create a new account
	//by opening up a popup window for account information
	add.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (p != null)
				showPopup(AccountManagementScreenv3.this, p, "add", 0);
		}
	});
	

	//This is called to initialize the screen and show all dynamically created GUI elements.
	createButtons();
	
	}
	
	    //Each account will have a new button created for them and will be displayed in the screen.
		//These buttons will all be similar, however their names will be different. Beside each user will be a
		//modify button and a delete button so that the franchisee can properly manage accounts.
		public void createButtons() {
			accountButtons = new ArrayList<Button>();
			updateAccountButtons = new ArrayList<Button>();
			deleteAccountButtons = new ArrayList<Button>();
			TableLayout tl = (TableLayout)findViewById(R.id.accounttable);
			//empty what was previously within this layout
			tl.removeAllViewsInLayout();
			tl.invalidate();
			ArrayList<Inspector> inspect = new ArrayList<Inspector>();
			inspect = AccountControl.getInstance().getInspectorList();
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
				
				//This onclick listener will display a popup for modifying an inspectors account
				update.setOnClickListener(new View.OnClickListener() {			
					@Override
					public void onClick(View v) {
						if (p != null)
							showPopup(AccountManagementScreenv3.this, p, "update", j);
					}
				});
				
				//This onclick listener will display a confirmation message for deleteing an inspectors account and allows deletion
				delete.setOnClickListener(new View.OnClickListener() {	
					@Override
					public void onClick(View v) {
						AlertDialog.Builder alertdelete = new AlertDialog.Builder(AccountManagementScreenv3.this);
						// Setting Dialog Title
						alertdelete.setTitle("Confirmation");
					
						// Setting Dialog Message
						alertdelete.setMessage("Are you sure you would like to delete this account?");
					
						// Setting Icon to Dialog
						alertdelete.setIcon(R.drawable.overview);
					
						// Setting OK Button
						alertdelete.setPositiveButton("Delete Account", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							try {
								AccountControl.getInstance().deleteInspector(j);
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
						alertdelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {					
							@Override
							public void onClick(DialogInterface dialog, int which) 
							{		
							}
						});
						alertdelete.show();
						
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
	private void showPopup(final Activity context, Point p, final String type, final int j) {

	 
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
	   passwordtitle = (TextView)layout.findViewById(R.id.passwordpopuptitle);
	   field4.setVisibility(View.VISIBLE);
	   if (type.equals("update"))
	   {
		   field1.setText(LoginControl.getInstance().getInspectors().get(j).getId());
		   field2.setText(LoginControl.getInstance().getInspectors().get(j).getName());
		   field3.setText(LoginControl.getInstance().getInspectors().get(j).getUsername());
		   field4.setVisibility(View.GONE);
		   passwordtitle.setVisibility(View.GONE);
	   }
	   // Clear the default translucent background and change it to a white background
	   popup.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
	   // Displaying the popup at the specified location, + offsets.
	   popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
	 
	   // Getting a reference to Close button, and close the popup when clicked.
	   Button close = (Button) layout.findViewById(R.id.close);
	   close.setOnClickListener(new OnClickListener(){
		 @Override
		 public void onClick(View v) {
	     if (field1.getText().toString().trim().length()>0 &&
	         field2.getText().toString().trim().length()>0 &&
	         field3.getText().toString().trim().length()>0){
			 if (type.equals("add") && field4.getText().toString().trim().length()>0) {
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
					AccountControl.getInstance().addInspector(field1.getText().toString(), field2.getText().toString(), field3.getText().toString(), hashedpassword, false);
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
			 else if (type.equals("update"))
			 {
				 try {
						AccountControl.getInstance().modifyInspector(j,field1.getText().toString(), field2.getText().toString(), field3.getText().toString(), LoginControl.getInstance().getInspectors().get(j).getPassword(), false);
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
			 else 
			 {
				 Toast.makeText(getBaseContext(), "Ensure all fields are Completed", Toast.LENGTH_SHORT).show();
			 }
	     }
	     else 
		 {
			 Toast.makeText(getBaseContext(), "Ensure all fields are Completed", Toast.LENGTH_SHORT).show();
		 }
		 }
	   });
   
	}
}
