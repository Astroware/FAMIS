package controlClasses;

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
		XMLParse.setDoc(XMLParse.getInspectionDataFilePath());
		System.out.println("Before XML Parsing");
		franchisee = XMLParse.parseFranchisee();
		System.out.println("After XML Parsing");
	}
}
