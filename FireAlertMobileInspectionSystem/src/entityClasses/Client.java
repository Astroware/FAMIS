package entityClasses;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private String m_id;
	private String m_name;
	private String m_address;
	
	public ClientContract m_clientContract;
	public List<ServiceAddress> m_serviceAddress;
	
	public Client(String id, String name, String address) {
		m_id = id;
		m_name = name;
		m_address = address;
		
		m_serviceAddress = new ArrayList<ServiceAddress>();
	}
	
	public String getId() {
		return m_id;
	}
	
	public String getName() {
		return m_name;
	}
	
	public String getAddress() {
		return m_address;
	}
}
