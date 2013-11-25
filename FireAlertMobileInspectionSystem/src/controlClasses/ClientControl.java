package controlClasses;

import java.util.ArrayList;
import java.util.List;

import entityClasses.Client;
import entityClasses.Franchisee;

public class ClientControl {
	private static ClientControl m_instance = null;
	private static Franchisee franchisee;
	
	private ClientControl() {
	}
	
	public static synchronized ClientControl getInstance() {
		if (m_instance == null)
			m_instance = new ClientControl();
		
		return m_instance;
	}
	
	public Franchisee getFranchisee() {
		return franchisee;
	}
	
	public int getClientListSize() {
		return franchisee.m_clientList.size();
	}
	
	public Client getClient(int i) {
		return franchisee.m_clientList.get(i);
	}
	
	public void parseXML() {
		//TODO: uncomment this line when the XMLParse class has been completed. 
		//franchisee = XMLParse.parseFranchisee();
		franchisee = parseFranchisee();
	}
	
	//Used as a current placeholder using hard coded xml until the parser works
	public Franchisee parseFranchisee(){
		Franchisee franchisee = new Franchisee(1001, "Darwin Fleming");
		franchisee.m_clientList.add(new Client("1001-01", "North Bay Inc.", "North Bay, Muskoka, Parry Sound, Orillia"));
		return franchisee;
	}
}
