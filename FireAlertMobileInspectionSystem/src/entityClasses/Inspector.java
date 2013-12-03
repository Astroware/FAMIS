package entityClasses;

public class Inspector {
	private String m_uname;
	private String m_pword;
	private String m_name;
	private String m_id;
	private Boolean m_flag;
	
	public Inspector(String id, String name, String uname, String pword, Boolean flag){
		m_id = id;
		m_name = name;
		m_uname = uname;
		m_pword = pword;
		m_flag = flag;
	}
	
	//------------------------------------Gets----------------------------------------------
	
	public String getUsername()
	{
		return m_uname;
	}
	
	public String getPassword()
	{
		return m_pword;
	}
	
	public String getName()
	{
		return m_name;
	}
	
	public String getId()
	{
		return m_id;
	}
	public Boolean getFlag()
	{
		return m_flag;
	}
	//------------------------------------Sets----------------------------------------------
	public void setInspector(String id, String name, String username, String password, Boolean flag)
	{
		m_id = id;
		m_name = name;
		m_uname = username;
		m_pword = password;
		m_flag = flag;
	}
	
	
}
