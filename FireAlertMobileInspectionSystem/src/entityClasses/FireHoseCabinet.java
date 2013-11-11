package entityClasses;

import java.util.ArrayList;

public class FireHoseCabinet extends Device {
	private String m_manufacturingDate;
	
	public FireHoseCabinet(int id, DeviceType deviceType, String location, String manufacturingDate) {
		super(id, deviceType, location);
		m_manufacturingDate = manufacturingDate;
		constructElements();
	}
	
	//TODO
	public void constructElements() {
		super.m_inspectionElements = new ArrayList<InspectionElement>();
		//assuming inspection elements are constant across the board
		super.m_inspectionElements.add(new InspectionElement("Cabinet Condition", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Nozzle Condition", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Hose Re-Rack", super.getDeviceType()));
		super.m_inspectionElements.add(new InspectionElement("Hydrostatic Test Due", super.getDeviceType()));
	}
	
	public String getManufacturingDate() {
		return m_manufacturingDate;
	}
}