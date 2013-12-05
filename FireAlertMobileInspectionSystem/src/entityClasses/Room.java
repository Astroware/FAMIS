package entityClasses;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private String m_id;
	private String m_no;
	private Boolean m_complete;
	
	public List<Device> m_devices;
	
	public Room(String id, String no) {
		m_id = id;
		m_no = no;
		m_complete = false;
		
		m_devices = new ArrayList<Device>();
	}
	
	//Checks if all devices within the Room are completed
	public Boolean isComplete() {
		if (m_complete == false) {
			
			for (Device device: m_devices) {
				if (!device.isComplete()) {
					return m_complete;
				}
			}
			
			m_complete = true;
		}
		
		return m_complete;
	}
	
	public String getId() {
		return m_id;
	}
	
	public String getNo() {
		return m_no;
	}
	
	public void setComplete() {
		m_complete = true;
	}
}
