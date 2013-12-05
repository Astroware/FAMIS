package entityClasses;

import java.util.ArrayList;
import java.util.List;

public abstract class Device {
	private int m_id;
	private DeviceType m_deviceType;
	private String m_location;
	private Boolean m_complete;
	
	public List<InspectionElement> m_inspectionElements;
	
	public enum DeviceType {
		EXTINGUISHER, FIRE_HOSE_CABINET, EMERGENCY_LIGHT
	}
	
	public Device(int id, DeviceType deviceType, String location) {
		m_id = id;
		m_deviceType = deviceType;
		m_location = location;
		m_complete = false;
		
		m_inspectionElements = new ArrayList<InspectionElement>();
	}
	
	//Checks if the device has been fully completed, if all inspection elements are complete
	public Boolean isComplete() {
		if (m_complete == false) {
			
			for (InspectionElement element: m_inspectionElements) {
				if (!element.isComplete()) {
					return m_complete;
				}
			}
			
			m_complete = true;
		}
		
		return m_complete;
	}
	
	public String toString() {
		switch (m_deviceType) {
		case EXTINGUISHER:
			return "Extinguisher";
		case FIRE_HOSE_CABINET:
			return "Fire Hose Cabinet";
		case EMERGENCY_LIGHT:
			return "Emergency Light";
		default:
			return null;
				
		}
	}
	
	//---------------------------------gets--------------------------------------------
	
	public int getId() {
		return m_id;
	}
	
	public DeviceType getDeviceType() {
		return m_deviceType;
	}
	
	public String getLocation() {
		return m_location;
	}
	
	//------------------------------sets--------------------------------------------------
	
	public void setComplete() {
		m_complete = true;
	}
	
	public void setIncomplete() {
		m_complete = false;
	}
}
