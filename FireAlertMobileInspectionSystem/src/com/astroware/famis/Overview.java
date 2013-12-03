package com.astroware.famis;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Overview extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview_screen);
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.overview, menu);
	    return true;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
	}
}
