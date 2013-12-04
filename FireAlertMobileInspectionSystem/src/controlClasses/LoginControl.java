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
	
	public void parseInspectors() throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
	{
		XMLParse.setDoc(XMLParse.getInspectorFilePath());
		XMLParse.getInspectors(m_inspectors);
	}
	
	public Boolean checkLogin(String uname)
	{
		
		 for(int i =0; i<m_inspectors.size();i++)
			 if(uname.equals(m_inspectors.get(i).getUsername()))
			 {
				 m_index = i;
				 System.out.println("Login");
				 System.out.println("Inspector index number:" + i);
				 System.out.println("Inspector array size:" + m_inspectors.size());
				 return true;
			 }
		 		
		
		return false;
	}
	
	public Inspector getCurrentInspector()
	{
		System.out.println("Get current inspector");
		System.out.println("Inspector index number:" + m_index);
		System.out.println("Inspector array size:" + m_inspectors.size());
		return m_inspectors.get(m_index);
	}
	public ArrayList<Inspector> getInspectors()
	{
		return m_inspectors;
	}
	
	

}
