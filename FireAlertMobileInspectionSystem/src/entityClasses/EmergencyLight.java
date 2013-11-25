package entityClasses;

public class EmergencyLight extends Device {
	private String m_location;
	private int m_model;
	private String m_make;
	private int m_numHeads;
	private String m_totalPower;
	private String m_voltage;
	
	public EmergencyLight(int id, DeviceType deviceType, String location, int model, String make, 
							int numHeads, String totalPower, String voltage) {
		super(id, deviceType, location);
		m_location = location;
		m_model = model;
		m_make = make;
		m_numHeads = numHeads;
		m_totalPower = totalPower;
		m_voltage = voltage;
	}

	public String getLocation() {
		return m_location;
	}
	
	public int getmodel() {
		return m_model;
	}
	
	public String getMake() {
		return m_make;
	}
	
	public int getNumHeads() {
		return m_numHeads;
	}
	
	public String getTotalPower() {
		return m_totalPower;
	}
	
	public String getVoltage() {
		return m_voltage;
	}
}