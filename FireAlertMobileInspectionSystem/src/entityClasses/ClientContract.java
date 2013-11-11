package entityClasses;

public class ClientContract {
	private int m_id;
	private int m_no;
	private String m_startDate;
	private String m_endDate;
	private String m_terms;
	
	public ClientContract(int id, int no, String startDate, String endDate, String terms) {
		m_id = id;
		m_no = no;
		m_startDate = startDate;
		m_endDate = endDate;
		m_terms = terms;
	}
	
	public int getId() {
		return m_id;
	}
	
	public int getNo() {
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
