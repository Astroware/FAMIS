package controlClasses;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
	
	//Parses the XML
	public static void parseXML() throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		XMLHandler.setDoc(XMLHandler.getInspectionDataFilePath());
		System.out.println("Before XML Parsing");
		franchisee = XMLHandler.parseFranchisee();
		System.out.println("After XML Parsing");
	}
}
