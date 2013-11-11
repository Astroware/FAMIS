package entityClasses;

import java.util.ArrayList;

public class Extinguisher extends Device {
	private int m_size;
	private String m_type;
	private String m_model;
	private String m_serialNo;
	private String m_manufacturingDate;
	
	public Extinguisher(int id, DeviceType deviceType, String location, int size, String type, 
							String model, String serialNo, String manufacturingDate) {
		super(id, deviceType, location);
		m_size = size;
		m_type = type;
		m_model = model;
		m_serialNo = serialNo;
		m_manufacturingDate = manufacturingDate;
		constructElements();
	}
	
	public void constructElements() {
		super.m_inspectionElements = new ArrayList<InspectionElement>();
		//assuming inspection elements are constant across the board
		super.m_inspectionElements.add(new InspectionElement("Hydro Test", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("6 Year Insp", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Weight", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Bracket", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Gauge", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Pull Pin", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Signage", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Collar", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Hose", super.getDeviceType()));
	}
	
	//-----------------------------gets-------------------------------------
	
	public int getSize() {
		return m_size;
	}
	
	public String getExtType() {
		return m_type;
	}
	
	public String getModel() {
		return m_model;
	}
	
	public String getSerialNo() {
		return m_serialNo;
	}
	
	public String getManufacturingDate() {
		return m_manufacturingDate;
	}

}
