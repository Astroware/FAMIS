package controlClasses;

import java.util.ArrayList;
import entityClasses.Inspector;

public class AccountControl {
	 private static ArrayList<Inspector> m_inspectors;
	 
	 public AccountControl()
	 {
		 m_inspectors = LoginControl.getInspectors();
	 }
	 
	 public void addInspector(int id, String name, String username, String password, Boolean flag)
	 {
		 Inspector newInspector = new Inspector(id,name,username,password,flag);
		 XMLParse.setDoc(XMLParse.getInspectorFilePath());
		 XMLParse.addInspector(newInspector);
		 m_inspectors.add(newInspector);
	 }
	 
	 public void deleteInspector(int index)
	 {
		 XMLParse.setDoc(XMLParse.getInspectorFilePath());
		 XMLParse.removeInspector(m_inspectors.get(index));
		 m_inspectors.remove(index);
	 }
	 
	 public void modifyInspector(int index, int id, String name, String username, String password, Boolean flag)
	 {
		XMLParse.setDoc(XMLParse.getInspectorFilePath());
		m_inspectors.get(index).setInspector(id, name, username, password, flag);
		XMLParse.removeInspector(m_inspectors.get(index));
		XMLParse.addInspector(m_inspectors.get(index)); 
	 }
}
