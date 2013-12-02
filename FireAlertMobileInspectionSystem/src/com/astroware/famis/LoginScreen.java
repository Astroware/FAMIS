package com.astroware.famis;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import controlClasses.LoginControl;
import controlClasses.PasswordHash;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends Activity {
    //Creating username and password textfields
    EditText usernameBox;
 	EditText passwordBox;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
   
        usernameBox = (EditText) findViewById(R.id.editTextusername);
        passwordBox = (EditText) findViewById(R.id.editTextpassword);
        //Creating submit button
        Button btn = (Button)findViewById(R.id.btnsubmit);
        Button back = (Button)findViewById(R.id.button1);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				System.exit(0);
			}
		});
		
     	//Click Listener that listen for the submit button
        btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//These if statements are used to verify that there is information present in both of the text fields
				if (usernameBox.getText().toString().trim().length()>0 && passwordBox.getText().toString().trim().length()>0)
				{
					 //Convert the username and password textfields into strings
					 String username=usernameBox.getText().toString();
					 String password=passwordBox.getText().toString();
					 
					 LoginControl.parseInspectors();
					 if(!(LoginControl.checkLogin(username)))
						  Toast.makeText(getApplicationContext(), "User Account Does Not Exist", Toast.LENGTH_SHORT).show();
					 String storedPassword = LoginControl.getCurrentInspector().getPassword();
					 //Toast.makeText(getApplicationContext(), storedPassword, Toast.LENGTH_LONG).show();
					 try {
						 
						if(PasswordHash.validatePassword(password, storedPassword) == true)
						{
							Toast.makeText(getApplicationContext(), ("Entering "+LoginControl.getCurrentInspector().getName()+"'s account"), Toast.LENGTH_SHORT).show();
							//Move into the next screen state (List of Clients) screen
							startActivity(new Intent (LoginScreen.this, ClientScreen.class));
							//Current screen will slide to the left
							overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
						}
						else
							Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
							
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidKeySpecException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
				 }
				 else
					Toast.makeText(getApplicationContext(), "Please fill in your username and password", Toast.LENGTH_LONG).show();	
				
			}
		});
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_screen, menu);
        return true;
    }
    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        usernameBox.setText("");
        passwordBox.setText("");        
    }
    
    public boolean onTouchEvent(MotionEvent event)
    {
    	InputMethodManager IMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	IMM.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    	return true;
    }
}
