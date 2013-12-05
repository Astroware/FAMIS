package controlClasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import entityClasses.Inspector;

public class LoginControl {
	private static LoginControl m_instance = null;
	private ArrayList<Inspector> m_inspectors = new ArrayList<Inspector>();
	private int m_index;
	
	private LoginControl() {
	}
	
	public static synchronized LoginControl getInstance() {
		if (m_instance == null)
			m_instance = new LoginControl();
		
		return m_instance;
	}
	
	//passes the inspectors to the XML parser control class
	public void parseInspectors() throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
	{
		XMLHandler.setDoc(XMLHandler.getInspectorsFilePath());
		XMLHandler.getInspectors(m_inspectors);
	}
	
	//checks if the login is correct
	public Boolean checkLogin(String uname)
	{
		
		 for(int i =0; i<m_inspectors.size();i++)
			 if(uname.equals(m_inspectors.get(i).getUsername()))
			 {
				 m_index = i;
				 return true;
			 }
		 		
		
		return false;
	}
	
	//gets the inspector that is currently logged in
	public Inspector getCurrentInspector()
	{

		return m_inspectors.get(m_index);
	}
	public ArrayList<Inspector> getInspectors()
	{
		return m_inspectors;
	}
	
	

}
