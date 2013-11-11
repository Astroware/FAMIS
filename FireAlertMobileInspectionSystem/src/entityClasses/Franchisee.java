package entityClasses;

import java.util.ArrayList;
import java.util.List;

public class Franchisee {
	private int m_id;
	private String m_name;
	
	public List<Client> m_clientList;
	
	public Franchisee(int id, String name) {
		m_id = id;
		m_name = name;
		m_clientList = new ArrayList<Client>();
	}
	
	public int getId() {
		return m_id;
	}
	
	public String getName() {
		return m_name;
	}
}
