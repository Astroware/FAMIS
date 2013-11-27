//TODO: This whole activity needs to be redone so that it is creating a form dynamically - this can be done by anyone
//Trevor has an idea for how to do this, so whoever decides to work on it should discuss with him first

package com.astroware.famis;

import entityClasses.InspectionElement.Result;
import com.astroware.famis.R;

import controlClasses.EquipmentControl;

import entityClasses.Device;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExtinguisherForm extends Activity {
	
	Button button1;
    Button button2; 
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    Button button11;
    Button button12;
    Button button13;
    Button button14;
    Button button15;
    Button button16;
    Button button17;
    Button button18; 
    Button button19;
    EditText notes1;
    EditText notes2;
    EditText notes3;
    EditText notes4;
    EditText notes5; 
    EditText notes6; 
    EditText notes7; 
    EditText notes8; 
    EditText notes9;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extinguisher_form);
		
		Intent in = getIntent();
        int deviceIndex = in.getIntExtra("selectedDevice", -1);
	    if (deviceIndex != -1) {
	    	EquipmentControl.getInstance().setDevice(deviceIndex);
	    }
	        
	        button1 = (Button)findViewById(R.id.passHydro);
	        button2 = (Button)findViewById(R.id.failHydro);
	        button3 = (Button)findViewById(R.id.pass6year);
	        button4 = (Button)findViewById(R.id.fail6year);
	        button5 = (Button)findViewById(R.id.passweight);
	        button6 = (Button)findViewById(R.id.failweight);
	        button7 = (Button)findViewById(R.id.passbracket);
	        button8 = (Button)findViewById(R.id.failbracket);
	        button9 = (Button)findViewById(R.id.passgauge);
	        button10 = (Button)findViewById(R.id.failgauge);
	        button11 = (Button)findViewById(R.id.passpullpin);
	        button12 = (Button)findViewById(R.id.failpullpin);
	        button13 = (Button)findViewById(R.id.passsignage);
	        button14 = (Button)findViewById(R.id.failsignage);
	        button15 = (Button)findViewById(R.id.passcollar);
	        button16 = (Button)findViewById(R.id.failcollar);
	        button17 = (Button)findViewById(R.id.passhose);
	        button18 = (Button)findViewById(R.id.failhose);
	        button19 = (Button)findViewById(R.id.btnsubmitform);
	        notes1 = (EditText)findViewById(R.id.notesHydro);
	        notes2 = (EditText)findViewById(R.id.notes6year);
	        notes3 = (EditText)findViewById(R.id.notesweight);
	        notes4 = (EditText)findViewById(R.id.notesbracket);
	        notes5 = (EditText)findViewById(R.id.notesgauge);
	        notes6 = (EditText)findViewById(R.id.notespullpin);
	        notes7 = (EditText)findViewById(R.id.notessignage);
	        notes8 = (EditText)findViewById(R.id.notescollar);
	        notes9 = (EditText)findViewById(R.id.noteshose);
	        
	        final String[] passOrFail= new String[9];
	        for (int i=0;i<9;i++){
	        	passOrFail[i] = "default";
	        }
	        
	        button1.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button1.setBackgroundResource(R.drawable.check_box);
					button2.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[0] = "Pass";
					EquipmentControl.getInstance().getInspectionElement(0).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(0).setComplete(true);
				}
			});
	        
	        button2.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button2.setBackgroundResource(R.drawable.fail_box);
					button1.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[0] = "Fail";
					EquipmentControl.getInstance().getInspectionElement(0).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(0).setComplete(false);
				}
			});
	        
	        button3.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button3.setBackgroundResource(R.drawable.check_box);
					button4.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[1] = "Pass";
					EquipmentControl.getInstance().getInspectionElement(1).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(1).setComplete(true);
					
					
				}
			});
	        
	        button4.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button4.setBackgroundResource(R.drawable.fail_box);
					button3.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[1] = "Fail";
					EquipmentControl.getInstance().getInspectionElement(1).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(1).setComplete(false);
				}
			});
	        
	        button5.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button5.setBackgroundResource(R.drawable.check_box);
					button6.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[2] = "Pass";
					EquipmentControl.getInstance().getInspectionElement(2).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(2).setComplete(true);
				}
			});
	        
	        button6.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button6.setBackgroundResource(R.drawable.fail_box);
					button5.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[2] = "Fail";
					EquipmentControl.getInstance().getInspectionElement(2).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(2).setComplete(false);
				}
			});
	        
	        button7.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button7.setBackgroundResource(R.drawable.check_box);
					button8.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[3] = "Pass";
					EquipmentControl.getInstance().getInspectionElement(3).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(3).setComplete(true);
					
					
				}
			});
	        
	        button8.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button8.setBackgroundResource(R.drawable.fail_box);
					button7.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[3] = "Fail";
					EquipmentControl.getInstance().getInspectionElement(3).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(3).setComplete(false);
				}
			});
	        
	        button9.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button9.setBackgroundResource(R.drawable.check_box);
					button10.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[4] = "Pass";
					EquipmentControl.getInstance().getInspectionElement(4).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(4).setComplete(true);
					
				}
			});
	        
	        button10.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button10.setBackgroundResource(R.drawable.fail_box);
					button9.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[4] = "Fail";
					EquipmentControl.getInstance().getInspectionElement(4).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(4).setComplete(false);
				}
			});
	        
	        button11.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button11.setBackgroundResource(R.drawable.check_box);
					button12.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[5] = "Pass";
					EquipmentControl.getInstance().getInspectionElement(5).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(5).setComplete(true);
				}
				
				
			});
	        
	        button12.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button12.setBackgroundResource(R.drawable.fail_box);
					button11.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[5] = "Fail";
					EquipmentControl.getInstance().getInspectionElement(5).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(5).setComplete(false);
				}
			});
	        
	        button13.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button13.setBackgroundResource(R.drawable.check_box);
					button14.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[6] = "Pass";
					EquipmentControl.getInstance().getInspectionElement(6).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(6).setComplete(true);
					
					
				}
			});
	        
	        button14.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button14.setBackgroundResource(R.drawable.fail_box);
					button13.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[6] = "Fail";
					EquipmentControl.getInstance().getInspectionElement(6).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(6).setComplete(false);
				}
			});
	        
	        button15.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button15.setBackgroundResource(R.drawable.check_box);
					button16.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[7] = "Pass";
					EquipmentControl.getInstance().getInspectionElement(7).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(7).setComplete(true);
					
					
				}
			});
	        
	        button16.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button16.setBackgroundResource(R.drawable.fail_box);
					button15.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[7] = "Fail";
					EquipmentControl.getInstance().getInspectionElement(7).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(7).setComplete(false);
				}
			});
	        
	        button17.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button17.setBackgroundResource(R.drawable.check_box);
					button18.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[8] = "Pass";
					EquipmentControl.getInstance().getInspectionElement(8).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(8).setComplete(true);
					
				}
			});
	        
	        button18.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					button18.setBackgroundResource(R.drawable.fail_box);
					button17.setBackgroundResource(android.R.drawable.btn_default);
					passOrFail[8] = "Fail";
					EquipmentControl.getInstance().getInspectionElement(8).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(8).setComplete(false);
				}
			});
	        
	        button19.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					writeText();
					if (EquipmentControl.getInstance().getDevice().isComplete()) {
						finish();
						overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
					}
					else {
						Toast.makeText(getApplicationContext(), "Form not fully completed", Toast.LENGTH_SHORT).show();
					}				
				}
			});
	}

	public void writeText(){
		EquipmentControl.getInstance().getInspectionElement(0).setTestNote(notes1.getText().toString().trim());
		EquipmentControl.getInstance().getInspectionElement(1).setTestNote(notes2.getText().toString().trim());
		EquipmentControl.getInstance().getInspectionElement(2).setTestNote(notes3.getText().toString().trim());
		EquipmentControl.getInstance().getInspectionElement(3).setTestNote(notes4.getText().toString().trim());
		EquipmentControl.getInstance().getInspectionElement(4).setTestNote(notes5.getText().toString().trim());
		EquipmentControl.getInstance().getInspectionElement(5).setTestNote(notes6.getText().toString().trim());
		EquipmentControl.getInstance().getInspectionElement(6).setTestNote(notes7.getText().toString().trim());
		EquipmentControl.getInstance().getInspectionElement(7).setTestNote(notes8.getText().toString().trim());
		EquipmentControl.getInstance().getInspectionElement(8).setTestNote(notes9.getText().toString().trim());
	}

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
