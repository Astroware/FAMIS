package entityClasses;

public class ClientContract {
	private String m_id;
	private String m_no;
	private String m_startDate;
	private String m_endDate;
	private String m_terms;
	
	public ClientContract(String id, String no, String startDate, String endDate, String terms) {
		m_id = id;
		m_no = no;
		m_startDate = startDate;
		m_endDate = endDate;
		m_terms = terms;
	}
	
	public String getId() {
		return m_id;
	}
	
	public String getNo() {
		return m_no;
	}
	
	public String getStartDate() {
		return m_startDate;
	}
	
	public String getEndDate() {
		return m_endDate;
	}
	
	public String getTerms() {
		return m_terms;
	}
}
