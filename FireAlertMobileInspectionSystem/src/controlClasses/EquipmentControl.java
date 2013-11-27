package controlClasses;

import entityClasses.Device;
import entityClasses.Floor;
import entityClasses.Room;
import entityClasses.ServiceAddress;
import entityClasses.InspectionElement;

public class EquipmentControl {
	private static EquipmentControl m_instance = null;
	private static ServiceAddress location;
	private static Floor floor;
	private static Room room;
	private static Device device;
	
	private EquipmentControl() {
	}
	
	public static synchronized EquipmentControl getInstance() {
		if (m_instance == null)
			m_instance = new EquipmentControl();
		
		return m_instance;
	}
	
	public void setLocation(int i) {
		location = LocationControl.getInstance().getLocation(i);
	}
	
	public ServiceAddress getLocation() {
		return location;
	}
	
	public void setFloor(int i) {
		floor = location.m_floors.get(i);
	}
	
	public Floor getFloor() {
		return floor;
	}
	
	public int getFloorListSize() {
		return location.m_floors.size();
	}
	
	public void setRoom(int i) {
		room = floor.m_rooms.get(i);
	}
	
	public Room getRoom() {
		return room;
	}
	
	public int getRoomListSize() {
		return floor.m_rooms.size();
	}
	
	public void setDevice(int i) {
		device = room.m_devices.get(i);
	}
	
	public Device getDevice() {
		return device;
	}
	
	public int getDeviceListSize() {
		return room.m_devices.size();
	}
	
	public InspectionElement getInspectionElement(int i) {
		return device.m_inspectionElements.get(i);
	}
	
	public int getInspectionElementListSize() {
		return device.m_inspectionElements.size();
	}
}
