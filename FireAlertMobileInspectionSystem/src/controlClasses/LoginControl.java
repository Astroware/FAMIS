package controlClasses;

import java.util.ArrayList;

import entityClasses.Inspector;

public class LoginControl {
	private static LoginControl m_instance = null;
	private static ArrayList<Inspector> m_inspectors = new ArrayList<Inspector>();
	private static int m_index;
	
	private LoginControl() {
	}
	
	public static synchronized LoginControl getInstance() {
		if (m_instance == null)
			m_instance = new LoginControl();
		
		return m_instance;
	}
	
	public static void parseInspectors()
	{
		XMLParse.setDoc(XMLParse.getInspectorFilePath());
		XMLParse.getInspectors(m_inspectors);
	}
	
	public static Boolean checkLogin(String uname)
	{
		
		 for(int i =0; i<m_inspectors.size();i++)
			 if(uname.equals(m_inspectors.get(i).getUsername()))
			 {
				 m_index = i;
				 return true;
			 }
		 		
		
		return false;
	}
	
	public static Inspector getCurrentInspector()
	{
		return m_inspectors.get(m_index);
	}
	public static ArrayList<Inspector> getInspectors()
	{
		return m_inspectors;
	}
	
	

}
