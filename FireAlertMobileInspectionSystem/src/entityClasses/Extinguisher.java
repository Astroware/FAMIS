package entityClasses;

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
