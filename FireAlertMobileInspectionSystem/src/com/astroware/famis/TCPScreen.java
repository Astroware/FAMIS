package com.astroware.famis;

import controlClasses.TCPController;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TCPScreen extends Activity {
	private Button connectButton;
	private Button sendButton;
	private Button disconnectButton;

	private EditText portNumText;
	private EditText ipAddressText;
	private TextView XMLBoxText;

	TCPController _controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tcpscreen);
		
		//map the variables to the view widgets
				connectButton = (Button)findViewById(R.id.connectTCP);
				sendButton = (Button)findViewById(R.id.sendTCP);
				disconnectButton = (Button)findViewById(R.id.closeTCP);
		 
				portNumText = (EditText)findViewById(R.id.portdata);
				ipAddressText = (EditText)findViewById(R.id.ipdata);

				this.sendButton.setEnabled(false);
				this.disconnectButton.setEnabled(false);
				
				_controller = new TCPController(this);

				
				//on "connectButton" click
				connectButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						_controller.ConnectBtnClick();	
					}
				});

						
				//on "sendButton" click
				sendButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						_controller.SendBtnClick();
					}
				});

				//on "disconnectButton" click
				disconnectButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						_controller.DisconnectBtnClick();
					}
				});
	}
	public String GetServerIP() {
		return this.ipAddressText.getText().toString();
	}

	public String GetXMLText() {
		return XMLBoxText.getText().toString();
	}

	public int GetServerPort() {
		return Integer.parseInt(this.portNumText.getText().toString());
	}
	
	public void setConnectBtnFalse() {
		this.connectButton.setEnabled(false);
	}

	public void setConnectBtnTrue() {
		this.connectButton.setEnabled(true);
	}
	
	public void setSendBtnTrue() {
		this.sendButton.setEnabled(true);
	}

	public void setDisconnectBtnTrue() {
		this.disconnectButton.setEnabled(true);
	}
	
	public void setSendBtnFalse() {
		this.sendButton.setEnabled(false);
	}

	public void setDisconnectBtnFalse() {
		this.disconnectButton.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tcpscreen, menu);
		return true;
	}

}
