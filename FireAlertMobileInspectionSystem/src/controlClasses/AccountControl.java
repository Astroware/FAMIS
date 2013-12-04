package controlClasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import entityClasses.Inspector;

public class AccountControl {
	 private ArrayList<Inspector> m_inspectors;
	 private static AccountControl m_instance;
	 
	 private AccountControl()
	 {
		 m_inspectors = LoginControl.getInstance().getInspectors();
	 }
	 
	 public static synchronized AccountControl getInstance() {
			if (m_instance == null)
				m_instance = new AccountControl();
			
			return m_instance;
		}
	 
	 public void addInspector(String id, String name, String username, String password, Boolean flag) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
	 {
		 Inspector newInspector = new Inspector(id,name,username,password,flag);
		 XMLParse.setDoc(XMLParse.getInspectorFilePath());
		 XMLParse.addInspector(newInspector);
		 m_inspectors.add(newInspector);
	 }
	 
	 public void deleteInspector(int index) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
	 {
		 XMLParse.setDoc(XMLParse.getInspectorFilePath());
		 XMLParse.removeInspector(m_inspectors.get(index));
		 m_inspectors.remove(index);
	 }
	 
	 public void modifyInspector(int index, String id, String name, String username, String password, Boolean flag) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
	 {
		XMLParse.setDoc(XMLParse.getInspectorFilePath());
		m_inspectors.get(index).setInspector(id, name, username, password, flag);
		XMLParse.removeInspector(m_inspectors.get(index));
		XMLParse.addInspector(m_inspectors.get(index)); 
	 }
	 public ArrayList <Inspector> getInspectorList () {
		 m_inspectors = LoginControl.getInstance().getInspectors();
		 return m_inspectors;
	 }
}
