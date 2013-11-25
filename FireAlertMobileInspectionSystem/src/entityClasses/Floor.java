package entityClasses;

import java.util.ArrayList;
import java.util.List;

public class Floor {
	private String m_name;
	private Boolean m_complete;
	
	public List<Room> m_rooms;
	
	public Floor(String name) {
		m_name = name;
		m_complete = false;
		
		m_rooms = new ArrayList<Room>();
	}
	
	public Boolean isComplete() {
		if (m_complete == false) {
			
			for (Room room: m_rooms) {
				if (!room.isComplete()) {
					return m_complete;
				}
			}
			
			m_complete = true;
		}
		
		return m_complete;
	}
	
	public String getName() {
		return m_name;
	}

	public void setComplete() {
		m_complete = true;
	}
}
