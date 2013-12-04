package controlClasses;

import com.astroware.famis.TCPScreen;

import entityClasses.TCPModel;
import android.util.Log;


public class TCPController {
	String ServerIP = null;
	int ServerPort = 0;

	TCPModel _TCPModel = null;

	// TCPController constructor
	private TCPScreen _view;

	public TCPController(TCPScreen view) {
		_view = view;
	}

	// connect button click function
	public void ConnectBtnClick() {
		ServerPort = _view.GetServerPort();
		ServerIP = _view.GetServerIP();
		try {
			_TCPModel = new TCPModel(ServerIP, ServerPort);

			_view.setConnectBtnFalse();
			_view.setSendBtnTrue();
			_view.setDisconnectBtnTrue();

		} catch (Exception e) {
			Log.d("Error in ConnectBtnClick", e.toString());
		}
	}

	// Send button click function
	public void SendBtnClick() {
		String txt = XMLParse.getXMLFile();
		_TCPModel.RTSPSend(txt); // send message to server
	}

	// Disconnect button click function
	public void DisconnectBtnClick() {
		_TCPModel.close();
		_view.setConnectBtnTrue();
		_view.setSendBtnFalse();
		_view.setDisconnectBtnFalse();
	}
}
