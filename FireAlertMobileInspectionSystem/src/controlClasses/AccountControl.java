package controlClasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import entityClasses.Inspector;

public class AccountControl {
	 private static ArrayList<Inspector> m_inspectors;
	 
	 public AccountControl()
	 {
		 m_inspectors = LoginControl.getInspectors();
	 }
	 
	 public void addInspector(int id, String name, String username, String password, Boolean flag) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
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
	 
	 public void modifyInspector(int index, int id, String name, String username, String password, Boolean flag) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
	 {
		XMLParse.setDoc(XMLParse.getInspectorFilePath());
		m_inspectors.get(index).setInspector(id, name, username, password, flag);
		XMLParse.removeInspector(m_inspectors.get(index));
		XMLParse.addInspector(m_inspectors.get(index)); 
	 }
}
