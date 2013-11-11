package com.astroware.famis;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import entityClasses.*;

public class LoginScreen extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_login_screen);
	        
	        //Creating submit button
	        Button btn = (Button)findViewById(R.id.btnsubmit);
	        
	        //Creating username and password textfields
	        final EditText usernameBox = (EditText) findViewById(R.id.editTextusername);
	     	final EditText passwordBox = (EditText) findViewById(R.id.editTextpassword);
	     	
	     	//Create an on click listener for when user has filled in the text fields and wants to submit
	        btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					//These if statements are used to verify that there is information present in both of the text fields
					if (usernameBox.getText().toString().trim().length()>0)					
					 { 
						 if (passwordBox.getText().toString().trim().length()>0)
						 {
							 //Convert the username and password textfields into strings
							 String username=usernameBox.getText().toString();
							 String password=passwordBox.getText().toString();
							 
							 //TODO: Instead of checking for hard coded values, a function needs to be called
							 //here that checks a text file that will be saved on the Android device
							 
							 //If the username and password match the required strings move into users account
							 if (username.equals("user") && password.equals("password")){
								 Toast.makeText(getApplicationContext(), ("Entering "+username+"'s account"), Toast.LENGTH_SHORT).show();
								 
								 //Move into the next screen state (List of Clients) screen
								 startActivity(new Intent (LoginScreen.this, ClientScreen.class));
								 
								 //Current screen will slide to the left
								 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
							 }
							 else
								 Toast.makeText(getApplicationContext(), "Invalid Account", Toast.LENGTH_SHORT).show(); 	 
						 }
						 else
							 Toast.makeText(getApplicationContext(), "Please fill in your password", Toast.LENGTH_LONG).show();
					 }
					 else
						 Toast.makeText(getApplicationContext(), "Please fill in your username", Toast.LENGTH_LONG).show();	 
					 		
				}
			});
	        
	        
	    }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.login_screen, menu);
	        return true;
	    }

}
