//TODO: This whole activity needs to be redone so that it is creating a form dynamically - this can be done by anyone
//Trevor has an idea for how to do this, so whoever decides to work on it should discuss with him first

package com.astroware.famis;

import java.util.ArrayList;
import java.util.List;

import entityClasses.InspectionElement.Result;

import com.astroware.famis.R;

import controlClasses.DigitsToPixels;
import controlClasses.EquipmentControl;
import entityClasses.Device;
import entityClasses.Device.DeviceType;
import entityClasses.InspectionElement.Result;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class ExtinguisherForm extends Activity {
	
	Button check;
    Button fail; 
    Button na;
    Button submit;
    Button back;
	List <EditText> notes;
	List <Button> column1;
	List <Button> column2;
	List <Button> column3;
	
	TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extinguisher_form);
		back = (Button)findViewById(R.id.buttonback);
		submit = (Button)findViewById(R.id.btnsubmitform);
		notes= new ArrayList<EditText>();
		column1= new ArrayList<Button>();
		column2= new ArrayList<Button>();
		column3= new ArrayList<Button>();
		Intent in = getIntent();
		
		EquipmentControl.getInstance().getDevice().setIncomplete();
		
		title = (TextView)findViewById(R.id.formTitle);
	    
		selectTable();

	    submit.setOnClickListener(new View.OnClickListener() {	
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
	    back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
			}
		});
	}

	private void createExtinguisherTable() {
		title.setText("Extinguisher " + EquipmentControl.getInstance().getDevice().getId() + " Form");
		TableLayout tl = (TableLayout)findViewById(R.id.inspectionelementtable);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		LayoutParams lprow =  new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, 1f);
		LayoutParams buttonParams = new LayoutParams(DigitsToPixels.dpToPixel(50, getBaseContext()),DigitsToPixels.dpToPixel(50, getBaseContext()));
	    System.out.println("After CreateTable!");
		// TODO Auto-generated method stub
		for (int i=0; i<EquipmentControl.getInstance().getInspectionElementListSize(); i++)
		{
			TableRow currentRow = new TableRow(this);
			TextView name = new TextView(this);
		    System.out.println("textview made:"+i);
			name.setText(EquipmentControl.getInstance().getInspectionElement(i).getName());
			check = new Button(this);
			check.setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
			fail = new Button(this);
			fail.setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
			EditText inspectionNote = new EditText(this);
			inspectionNote.setSingleLine(true);
			System.out.println(name.getText());
			switch (EquipmentControl.getInstance().getInspectionElement(i).getTestResult())
			{
			case PASS:
				check.setBackgroundResource(R.drawable.check_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i).getTestNote());
				break;
			case FAIL:
				fail.setBackgroundResource(R.drawable.fail_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i).getTestNote());
				break;
			default:
				break;
			}
			currentRow.addView(name, lprow);
			currentRow.addView(check, buttonParams);
			currentRow.addView(fail, buttonParams);
			System.out.println("three views added:"+i);
			column1.add(check);
			column2.add(fail);
			notes.add(inspectionNote);
			inspectionNote.setTransformationMethod(null);
			inspectionNote.setWidth(DigitsToPixels.dpToPixel(100, getBaseContext()));
			currentRow.addView(inspectionNote, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, 2f));
			tl.addView(currentRow);
			System.out.println("added current row:"+i);
			final int j;
			j = i;
			column1.get(i).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					column1.get(j).requestFocus();
					column1.get(j).setBackgroundResource(R.drawable.check_box);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column2.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.PASS);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(true);
				}
			});
			
	        column2.get(i).setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					column2.get(j).requestFocus();
					column2.get(j).setBackgroundResource(R.drawable.fail_box);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column1.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.FAIL);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(false);
				}
			});
	        
		}
	}

	private void createHoseCabinetTable() {
		title.setText("Fire Hose Cabinet " + EquipmentControl.getInstance().getDevice().getId() + " Form");
		TableLayout tl = (TableLayout)findViewById(R.id.inspectionelementtable);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		tl.removeAllViewsInLayout();
		LayoutParams lprow =  new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, 1f);
		LayoutParams buttonParams = new LayoutParams(DigitsToPixels.dpToPixel(50, getBaseContext()),DigitsToPixels.dpToPixel(50, getBaseContext()));
	    System.out.println("After CreateTable!");
		TableRow titleRow = new TableRow(this);
		TextView titlename = new TextView(this);
		titlename.setText("Test Name");
	 	titlename.setGravity(android.view.Gravity.CENTER);
		titlename.setTextSize(15);
		titlename.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView good = new TextView(this);
	 	good.setGravity(android.view.Gravity.CENTER);
		good.setText("Good");
		good.setTextSize(15);
		good.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView poor = new TextView(this);
		poor.setText("Poor");
	 	poor.setGravity(android.view.Gravity.CENTER);
		poor.setTextSize(15);
		poor.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView titlena = new TextView(this);
		titlena.setText("N/A");
	 	titlena.setGravity(android.view.Gravity.CENTER);
		titlena.setTextSize(15);
		titlena.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView titlenotes = new TextView(this);
		titlenotes.setText("Note");
	 	titlenotes.setGravity(android.view.Gravity.CENTER);
		titlenotes.setTextSize(15);
		titlenotes.setTypeface(null, Typeface.BOLD_ITALIC);
		titleRow.addView(titlename, lprow);
		titleRow.addView(good, lprow);
		titleRow.addView(poor, lprow);
		titleRow.addView(titlena, lprow);
		titleRow.addView(titlenotes, lprow);
		tl.addView(titleRow, lprow);
		for (int i=0; i<2; i++)
		{
			TableRow currentRow = new TableRow(this);
			TextView name = new TextView(this);
		    System.out.println("textview made:"+i);
			name.setText(EquipmentControl.getInstance().getInspectionElement(i).getName());
			check = new Button(this);
			check.setWidth(DigitsToPixels.dpToPixel(45, getBaseContext()));
			fail = new Button(this);
			fail.setWidth(DigitsToPixels.dpToPixel(45, getBaseContext()));
			na = new Button(this);
			na.setWidth(DigitsToPixels.dpToPixel(45, getBaseContext()));
			EditText inspectionNote = new EditText(this);
			inspectionNote.setSingleLine(true);
			inspectionNote.setWidth(DigitsToPixels.dpToPixel(75, getBaseContext()));//c
			System.out.println(name.getText());
			currentRow.addView(name, lprow);
			currentRow.addView(check, buttonParams);
			currentRow.addView(fail, buttonParams);
			currentRow.addView(na, buttonParams);
			column1.add(check);
			column2.add(fail);
			column3.add(na);
			switch (EquipmentControl.getInstance().getInspectionElement(i).getTestResult())
			{
			case GOOD:
				check.setBackgroundResource(R.drawable.check_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i).getTestNote());
				break;
			case POOR:
				fail.setBackgroundResource(R.drawable.fail_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i).getTestNote());
				break;
			case NA:
				na.setBackgroundResource(R.drawable.na_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i).getTestNote());
				break;
			default:
				break;
			}
			notes.add(inspectionNote);
			inspectionNote.setSingleLine(true);
			currentRow.addView(inspectionNote, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, 2f));
			tl.addView(currentRow);
			System.out.println("added current row:"+i);
			final int j;
			j = i;
			

			
			column1.get(i).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					column1.get(j).requestFocus();
					column1.get(j).setBackgroundResource(R.drawable.check_box);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column2.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column3.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column3.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.GOOD);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(true);
				}
			});
			
	        column2.get(i).setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					column2.get(j).requestFocus();
					column2.get(j).setBackgroundResource(R.drawable.fail_box);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column1.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column3.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column3.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.POOR);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(true);
				}
			});
	       
	        column3.get(i).setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					column3.get(j).requestFocus();
					column3.get(j).setBackgroundResource(R.drawable.na_box);//c
					column3.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column2.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column1.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.NA);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(true);
				}
			});
	        tl.requestFocus();
		}
		TableRow titleRow2 = new TableRow(this);
		TextView titlename2 = new TextView(this);
		titlename2.setText("Test Name");
	 	titlename2.setGravity(android.view.Gravity.CENTER);
		titlename2.setTextSize(20);
		titlename2.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView pass = new TextView(this);
	 	pass.setGravity(android.view.Gravity.CENTER);
		pass.setText("Yes");
		pass.setTextSize(20);
		pass.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView failtitle = new TextView(this);
		failtitle.setText("No");
	 	failtitle.setGravity(android.view.Gravity.CENTER);
		failtitle.setTextSize(20);
		failtitle.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView titlenotes2 = new TextView(this);
		titlenotes2.setText("Notes");
	 	titlenotes2.setGravity(android.view.Gravity.CENTER);
		titlenotes2.setTextSize(20);
		titlenotes2.setTypeface(null, Typeface.BOLD_ITALIC);
		titleRow2.addView(titlename2, lprow);
		titleRow2.addView(pass, lprow);
		titleRow2.addView(failtitle, lprow);
		titleRow2.addView(titlenotes2, lprow);
		tl.addView(titleRow2, lprow);
		for (int i=0; i<2; i++)
		{
			TableRow currentRow = new TableRow(this);
			TextView name = new TextView(this);
		    System.out.println("textview made:"+i+2);
			name.setText(EquipmentControl.getInstance().getInspectionElement(i+2).getName());
			check = new Button(this);
			check.setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
			fail = new Button(this);
			fail.setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
			EditText inspectionNote = new EditText(this);
			inspectionNote.setSingleLine(true);
			//inspectionNote.setTransformationMethod(null);//c
			inspectionNote.setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));//c
			System.out.println(name.getText());
			currentRow.addView(name, lprow);
			currentRow.addView(check, buttonParams);
			currentRow.addView(fail, buttonParams);
			System.out.println("three views added:"+i);
			switch (EquipmentControl.getInstance().getInspectionElement(i+2).getTestResult())
			{
			case YES:
				check.setBackgroundResource(R.drawable.check_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i+2).getTestNote());
				break;
			case NO:
				fail.setBackgroundResource(R.drawable.fail_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i+2).getTestNote());
				break;
			default:
				break;
			}
			column1.add(check);
			column2.add(fail);
			notes.add(inspectionNote);
			currentRow.addView(inspectionNote, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, 2f));
			tl.addView(currentRow,lprow);
			System.out.println("added current row:"+i);
			final int j;
			j = i+2;
			column1.get(i+2).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					column1.get(j).requestFocus();
					column1.get(j).setBackgroundResource(R.drawable.check_box);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column2.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.YES);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(true);
				}
			});
			
	        column2.get(i+2).setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					column2.get(j).requestFocus();
					column2.get(j).setBackgroundResource(R.drawable.fail_box);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column1.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.NO);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(true);
				}
			});
	        
		}
	}
	
	private void createEmergencyLightTable() {
		title.setText("Emergency Light " + EquipmentControl.getInstance().getDevice().getId() + " Form");
		TableLayout tl = (TableLayout)findViewById(R.id.inspectionelementtable);
		tl.removeAllViewsInLayout();
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		LayoutParams lprow =  new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, 1f);
		LayoutParams buttonParams = new LayoutParams(DigitsToPixels.dpToPixel(50, getBaseContext()),DigitsToPixels.dpToPixel(50, getBaseContext()));TableRow titlerow = new TableRow(this);
		TableRow titleRow = new TableRow(this);
		TextView titlename = new TextView(this);
		titlename.setText("Test Name");
	 	titlename.setGravity(android.view.Gravity.CENTER);
		titlename.setTextSize(15);
		titlename.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView good = new TextView(this);
	 	good.setGravity(android.view.Gravity.CENTER);
		good.setText("Good");
		good.setTextSize(15);
		good.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView poor = new TextView(this);
		poor.setText("Poor");
	 	poor.setGravity(android.view.Gravity.CENTER);
		poor.setTextSize(15);
		poor.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView titlena = new TextView(this);
		titlena.setText("N/A");
	 	titlena.setGravity(android.view.Gravity.CENTER);
		titlena.setTextSize(15);
		titlena.setTypeface(null, Typeface.BOLD_ITALIC);
		TextView titlenotes = new TextView(this);
		titlenotes.setText("Note");
	 	titlenotes.setGravity(android.view.Gravity.CENTER);
		titlenotes.setTextSize(15);
		titlenotes.setTypeface(null, Typeface.BOLD_ITALIC);
		titleRow.addView(titlename, lprow);
		titleRow.addView(good, lprow);
		titleRow.addView(poor, lprow);
		titleRow.addView(titlena, lprow);
		titleRow.addView(titlenotes, lprow);
		tl.addView(titleRow, lprow);
		
	    System.out.println("After CreateTable!");
		// TODO Auto-generated method stub
		for (int i=0; i<EquipmentControl.getInstance().getInspectionElementListSize(); i++)
		{
			TableRow currentRow = new TableRow(this);
			TextView name = new TextView(this);
		    System.out.println("textview made:"+i);
			name.setText(EquipmentControl.getInstance().getInspectionElement(i).getName());
			name.setWidth(100);
			check = new Button(this);
			check.setWidth(DigitsToPixels.dpToPixel(45, getBaseContext()));
			fail = new Button(this);
			fail.setWidth(DigitsToPixels.dpToPixel(45, getBaseContext()));
			na = new Button(this);
			na.setWidth(DigitsToPixels.dpToPixel(45, getBaseContext()));
			EditText inspectionNote = new EditText(this);
			inspectionNote.setSingleLine(true);
			inspectionNote.setWidth(DigitsToPixels.dpToPixel(75, getBaseContext()));//c
			System.out.println(name.getText());
			currentRow.addView(name, lprow);
			currentRow.addView(check, buttonParams);
			currentRow.addView(fail, buttonParams);
			currentRow.addView(na, buttonParams);
			column1.add(check);
			column2.add(fail);
			column3.add(na);
			switch (EquipmentControl.getInstance().getInspectionElement(i).getTestResult())
			{
			case GOOD:
				check.setBackgroundResource(R.drawable.check_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i).getTestNote());
				break;
			case POOR:
				fail.setBackgroundResource(R.drawable.fail_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i).getTestNote());
				break;
			case NA:
				na.setBackgroundResource(R.drawable.na_box);
				inspectionNote.setText(EquipmentControl.getInstance().getInspectionElement(i).getTestNote());
				break;
			default:
				break;
			}
			notes.add(inspectionNote);
			currentRow.addView(inspectionNote, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, 2f));
			tl.addView(currentRow);
			System.out.println("added current row:"+i);
			final int j;
			j = i;
			column1.get(i).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					column1.get(j).requestFocus();
					column1.get(j).setBackgroundResource(R.drawable.check_box);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column2.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column3.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column3.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.GOOD);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(true);
				}
			});
			
	        column2.get(i).setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					column2.get(j).requestFocus();
					column2.get(j).setBackgroundResource(R.drawable.fail_box);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column1.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column3.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column3.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.POOR);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(true);
				}
			});
	       
	        column3.get(i).setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					column3.get(j).requestFocus();
					column3.get(j).setBackgroundResource(R.drawable.na_box);
					column3.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column2.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column2.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					column1.get(j).setBackgroundResource(android.R.drawable.btn_default);
					column1.get(j).setWidth(DigitsToPixels.dpToPixel(50, getBaseContext()));
					EquipmentControl.getInstance().getInspectionElement(j).setTestResult(Result.NA);
					EquipmentControl.getInstance().getInspectionElement(j).setComplete(true);
				}
			});
	        tl.requestFocus();
		}
	}

	public void writeText(){
		for (int i=0;i<EquipmentControl.getInstance().getInspectionElementListSize();i++)
		{
			EquipmentControl.getInstance().getInspectionElement(i).setTestNote(notes.get(i).getText().toString().trim());
		}

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
	
	private void selectTable() {
		switch (EquipmentControl.getInstance().getDevice().getDeviceType()) {
		case EXTINGUISHER:
			createExtinguisherTable();
			break;
		case FIRE_HOSE_CABINET:
			createHoseCabinetTable();
			break;
		case EMERGENCY_LIGHT:
			createEmergencyLightTable();
			break;
		default:
			System.out.println("Invalid device type!");
				
			
		}
	}
	
	public boolean dispatchTouchEvent(MotionEvent event) {

	    View v = getCurrentFocus();
	    boolean ret = super.dispatchTouchEvent(event);

	    if (v instanceof EditText) 
	    {
	    	v.clearFocus();
	    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
	    }
	return ret;
	}

}
