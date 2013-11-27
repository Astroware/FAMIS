package entityClasses;

public class FireHoseCabinet extends Device {
	private String m_manufacturingDate;
	
	public FireHoseCabinet(int id, DeviceType deviceType, String location, String manufacturingDate) {
		super(id, deviceType, location);
		m_manufacturingDate = manufacturingDate;
	}
	
	public String getManufacturingDate() {
		return m_manufacturingDate;
	}
}