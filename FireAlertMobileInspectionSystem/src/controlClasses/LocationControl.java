package controlClasses;

import entityClasses.Client;
import entityClasses.ServiceAddress;

public class LocationControl {
	private static LocationControl m_instance = null;
	private static Client client;
	
	private LocationControl() {
	}
	
	public static synchronized LocationControl getInstance() {
		if (m_instance == null)
			m_instance = new LocationControl();
		
		return m_instance;
	}
	
	public void setClient(int i) {
		client = ClientControl.getInstance().getClient(i);
	}
	
	public Client getClient() {
		return client;
	}
	
	public int getLocationListSize() {
		return client.m_serviceAddress.size();
	}
	
	public ServiceAddress getLocation(int i) {
		return client.m_serviceAddress.get(i);
	}
}
