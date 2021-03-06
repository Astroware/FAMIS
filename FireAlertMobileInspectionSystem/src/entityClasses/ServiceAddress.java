package entityClasses;

import java.util.ArrayList;
import java.util.List;

public class ServiceAddress {
	private String m_id;
	private String m_address;
	private String m_postalCode;
	private String m_contact;
	private String m_city;
	private String m_province;
	private String m_country;
	private String m_inspectorId;
	private String m_testTimeStamp;
	private String m_contractId;
	private int m_contractNo;
	private Boolean m_complete;
	
	public List<Floor> m_floors;
	
	public ServiceAddress(String id, String address, String postalCode, String contact, String city, 
							String province, String country, String inspectorId, String contractId, int contractNo) {
		m_id = id;
		m_address = address;
		m_postalCode = postalCode;
		m_contact = contact;
		m_city = city;
		m_province = province;
		m_country = country;
		m_inspectorId = inspectorId;
		m_testTimeStamp = null;
		m_contractId = contractId;
		m_contractNo = contractNo;
		m_complete = false;
		
		m_floors = new ArrayList<Floor>();
	}
	
	//Checks if all floors within the service address have been completed
	public Boolean isComplete() {
		if (m_complete == false) {
			
			for (Floor floor: m_floors) {
				if (!floor.isComplete()) {
					return m_complete;
				}
			}
			
			m_complete = true;
		}
		
		return m_complete;
	}
	
	//------------------------------------Gets----------------------------------------------
	
	public String getId() {
		return m_id;
	}
	
	public String getAddress() {
		return m_address;
	}
	
	public String getPostalCode() {
		return m_postalCode;
	}
	
	public String getContact() {
		return m_contact;
	}
	
	public String getCity() {
		return m_city;
	}
	
	public String getProvince() {
		return m_province;
	}
	
	public String getCountry() {
		return m_country;
	}
	
	public String getInspectorId() {
		return m_inspectorId;
	}
	
	public String getTestTimeStamp() {
		return m_testTimeStamp;
	}
	
	public String getContractId() {
		return m_contractId;
	}
	
	public int getContractNo() {
		return m_contractNo;
	}
	
	//------------------------------------Sets----------------------------------------------
	
	public void setTestTimeStamp(String testTimeStamp) {
		m_testTimeStamp = testTimeStamp;
	}
}
