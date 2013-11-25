package controlClasses;

import java.io.File;
import java.io.InputStream;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

import entityClasses.Client;
import entityClasses.Device;
import entityClasses.Device.DeviceType;
import entityClasses.EmergencyLight;
import entityClasses.Extinguisher;
import entityClasses.FireHoseCabinet;
import entityClasses.Floor;
import entityClasses.Franchisee;
import entityClasses.InspectionElement;
import entityClasses.Room;
import entityClasses.ServiceAddress;

public class XMLParse{

	private static Document m_doc;
	private static Node tempNode;
	private static Node tempNode2;
	private static Node tempNode3;
	private static Node tempNode4;
	private static Node tempExNode;
	private static Node tempCabNode;
	private static Node tempLightNode;
	
	public static void setDoc(Document doc)
	{
		m_doc=doc;
	}
	
    public static void getDoc()
    {
    	try
    	{

	    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	        Document doc = docBuilder.parse (new File("C:\\Users\\Scott\\Desktop\\InspectionData.xml"));
	        doc.getDocumentElement().normalize();
	        m_doc=doc;
	        
    	}catch (SAXParseException err) {
            System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());

            }catch (SAXException e) {
            Exception x = e.getException ();
            ((x == null) ? e : x).printStackTrace ();

            }catch (Throwable t) {
            t.printStackTrace ();
        }
    }
    
    public static Franchisee parseFranchisee()
    {
    	Franchisee franchisee = new Franchisee(0, null);
    	
    	String id;	
    	String name;
        String[] splitter;
        
        NodeList listOfFranchisees = m_doc.getElementsByTagName("Franchisee");
        
    	Element FranchID = (Element)listOfFranchisees.item(0);;
    	NamedNodeMap Franch = FranchID.getAttributes();
    	Node Franchisee = listOfFranchisees.item(0);
    	
    	if(Franchisee.getNodeType() == Node.ELEMENT_NODE)
    	{
            Node nodeFranchID = Franch.getNamedItem("id");
            String node = nodeFranchID.toString();
            splitter = node.split("=");
            id = splitter[1].replaceAll("[\"]", "");
            
            Node nodeFranchID2 = Franch.getNamedItem("name");
            String node2 = nodeFranchID2.toString();
            splitter = node2.split("=");
            name = splitter[1].replaceAll("[\"]", "");
            
            int intId = Integer.parseInt(id);
            franchisee = new Franchisee(intId,name);
    	}
    	clientParse(franchisee);
        return franchisee;
    }
    
    public static Franchisee clientParse(Franchisee franchisee)
    {
    	String id = "";
    	String name = "";
    	String address = "";
        String[] splitter;
        
        NodeList listOfClients = m_doc.getElementsByTagName("Client");
        
        for(int k=0; k<listOfClients.getLength() ; k++)
        {
        	Element ClientID = (Element)listOfClients.item(k);;
        	NamedNodeMap ClientAttr = ClientID.getAttributes();
        	Node Client = listOfClients.item(k);
        	
        	tempNode = Client;
        	
        	if(Client.getNodeType() == Node.ELEMENT_NODE)
        	{
        		Node ClientNodeID = ClientAttr.getNamedItem("id");
	            String ClientIDString = ClientNodeID.toString();
	            splitter = ClientIDString.split("=");
	            id = splitter[1].replaceAll("[\"]", "");
	            
	            Node ClientNodeID2 = ClientAttr.getNamedItem("name");
	            String ClientIDString2 = ClientNodeID2.toString();
	            splitter = ClientIDString2.split("=");
	            name = splitter[1].replaceAll("[\"]", "");
	            
	            Node ClientNodeID3 = ClientAttr.getNamedItem("address");
	            String ClientIDString3 = ClientNodeID3.toString();
	            splitter = ClientIDString3.split("=");
	            address = splitter[1].replaceAll("[\"]", "");
        	}
        	franchisee.m_clientList.add(new Client(id, name, address));
        	getServiceAddresses(franchisee.m_clientList.get(k));
        }
        return franchisee;
    }
    
    public static void getServiceAddresses(Client client)
    {
    	String id="";
    	String address="";
    	String postalCode="";
    	String contact="";
    	String city="";
    	String province="";
    	String country="";
    	String inspectorId="";
    	String testTimeStamp="";
    	String contractId="";
    	int contractIdInt=0;
    	String[] splitter;
    	
        Element contract = (Element)tempNode;
        
        NodeList listOfAddresses = contract.getElementsByTagName("ServiceAddress");
        
		for(int k=0; k<listOfAddresses.getLength() ; k++)
		{
			Element ServiceAddress = (Element)listOfAddresses.item(k);;
		 	NamedNodeMap ServiceAddressAttrs = ServiceAddress.getAttributes();
		 	Node ServiceAddressNode = listOfAddresses.item(k);
		 	tempNode2 = ServiceAddressNode;
		 	
		 	if(ServiceAddress.getNodeType() == Node.ELEMENT_NODE)
		 	{
			    Node serviceAddNode = ServiceAddressAttrs.getNamedItem("id");
			    String serviceAdd = serviceAddNode.toString();
			    splitter = serviceAdd.split("=");
			    id = splitter[1].replaceAll("[\"]", "");
			    
			    Node serviceAddNode2 = ServiceAddressAttrs.getNamedItem("address");
			    String serviceAdd2 = serviceAddNode2.toString();
			    splitter = serviceAdd2.split("=");
			    address = splitter[1].replaceAll("[\"]", "");
			    
			    Node serviceAddNode3 = ServiceAddressAttrs.getNamedItem("postalCode");
			    String serviceAdd3 = serviceAddNode3.toString();
			    splitter = serviceAdd3.split("=");
			    postalCode = splitter[1].replaceAll("[\"]", "");
			    
			    Node serviceAddNode4 = ServiceAddressAttrs.getNamedItem("contact");
			    String serviceAdd4 = serviceAddNode4.toString();
			    splitter = serviceAdd4.split("=");
			    contact = splitter[1].replaceAll("[\"]", "");
			    
			    Node serviceAddNode5 = ServiceAddressAttrs.getNamedItem("city");
			    String serviceAdd5 = serviceAddNode5.toString();
			    splitter = serviceAdd5.split("=");
			    city = splitter[1].replaceAll("[\"]", ""); 
			    
			    Node serviceAddNode6 = ServiceAddressAttrs.getNamedItem("province");
			    String serviceAdd6 = serviceAddNode6.toString();
			    splitter = serviceAdd6.split("=");
			    province = splitter[1].replaceAll("[\"]", ""); 
			    
			    Node serviceAddNode7 = ServiceAddressAttrs.getNamedItem("country");
			    String serviceAdd7 = serviceAddNode7.toString();
			    splitter = serviceAdd7.split("=");
			    country = splitter[1].replaceAll("[\"]", ""); 
			    
			    Node serviceAddNode8 = ServiceAddressAttrs.getNamedItem("InspectorID");
			    String serviceAdd8 = serviceAddNode8.toString();
			    splitter = serviceAdd8.split("=");
			    inspectorId = splitter[1].replaceAll("[\"]", ""); 
			    
			    Node serviceAddNode9 = ServiceAddressAttrs.getNamedItem("testTimeStamp");
			    String serviceAdd9 = serviceAddNode9.toString();
			    splitter = serviceAdd9.split("=");
			    testTimeStamp = splitter[1].replaceAll("[\"]", "");
			    
			    NodeList listOfContracts = contract.getElementsByTagName("clientContract");
		        
				Element ClientContracts = (Element)listOfContracts.item(k);;
			 	NamedNodeMap ClientContractsAttrs = ClientContracts.getAttributes();
			 	Node ClientContract = listOfContracts.item(k);
			 	tempNode2 = ClientContract;
			 	
			 	if(ClientContract.getNodeType() == Node.ELEMENT_NODE)
			 	{
				    Node ClientContractNode = ClientContractsAttrs.getNamedItem("id");
				    String CCIDNode = ClientContractNode.toString();
				    splitter = CCIDNode.split("=");
				    contractId = splitter[1].replaceAll("[\"]", "");
				    contractIdInt = Integer.parseInt(contractId);
			 	}
		 	}
		 	client.m_serviceAddress.add(new ServiceAddress(id, address, postalCode, contact, city, province, country, inspectorId, testTimeStamp, contractIdInt));
		 	getFloors(client.m_serviceAddress.get(k));
		}
    }
    
    public static void getFloors(ServiceAddress serAdd)
    {
    	String name="";
    	String[] splitter;
    	
    	Element floor = (Element)tempNode2;
        NodeList listOfFloors = floor.getElementsByTagName("Floor");
        
        for(int k=0; k<listOfFloors.getLength() ; k++)
		{
			Element floors = (Element)listOfFloors.item(k);;
		 	NamedNodeMap ServiceAddressAttr = floors.getAttributes();
		 	Node ServiceAddress = listOfFloors.item(k);
		 	tempNode3 = ServiceAddress;
		 	
		 	if(ServiceAddress.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node ServiceAddressNode = ServiceAddressAttr.getNamedItem("name");
			    String SerAddNode = ServiceAddressNode.toString();
			    splitter = SerAddNode.split("=");
			    name = splitter[1].replaceAll("[\"]", "");
		 	}
		 	serAdd.m_floors.add(new Floor(name));
		 	getRooms(serAdd.m_floors.get(k));
		}
    }
    
    public static void getRooms(Floor floor)
    {
    	String id="";
    	String No="";
    	String[] splitter;
    	
    	Element room = (Element)tempNode3;
        NodeList listOfRooms = room.getElementsByTagName("Floor");
        
        for(int k=0; k<listOfRooms.getLength() ; k++)
		{
			Element rooms = (Element)listOfRooms.item(k);;
		 	NamedNodeMap roomAttr = rooms.getAttributes();
		 	Node RoomNode = listOfRooms.item(k);
		 	tempNode4 = RoomNode;
		 	
		 	if(RoomNode.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node Room = roomAttr.getNamedItem("id");
			    String roomName = Room.toString();
			    splitter = roomName.split("=");
			    id = splitter[1].replaceAll("[\"]", "");
			    
			    Node Room2 = roomAttr.getNamedItem("No");
			    String roomName2 = Room2.toString();
			    splitter = roomName2.split("=");
			    No = splitter[1].replaceAll("[\"]", "");
		 	}
		 	floor.m_rooms.add(new Room(id, No));
		 	getDevices(floor.m_rooms.get(k));
		}
    }
    
    public static void getDevices(Room room)
    {
    	String id="";
    	String location="";
    	String size="";
    	String type="";
    	String model="";
    	String serialNo="";
    	String manufacturingDate="";
    	int exID=0;
    	int exSize=0;
    	String[] splitter;
    	
    	Element device = (Element)tempNode4;
        NodeList listOfExtinguishers = device.getElementsByTagName("Extinguisher");
        
        for(int k=0; k<listOfExtinguishers.getLength() ; k++)
		{
			Element extinguishers = (Element)listOfExtinguishers.item(k);;
		 	NamedNodeMap exAttr = extinguishers.getAttributes();
		 	Node exNode = listOfExtinguishers.item(k);
		 	
		 	tempExNode = exNode;
			
		 	if(exNode.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node ex = exAttr.getNamedItem("id");
			    String exName = ex.toString();
			    splitter = exName.split("=");
			    id = splitter[1].replaceAll("[\"]", "");
			    exID = Integer.parseInt(id);
			    
			    Node exLoc = exAttr.getNamedItem("location");
			    String exLo = exLoc.toString();
			    splitter = exLo.split("=");
			    location = splitter[1].replaceAll("[\"]", "");
			    
			    Node ex3 = exAttr.getNamedItem("size");
			    String exName3 = ex3.toString();
			    splitter = exName3.split("=");
			    size = splitter[1].replaceAll("[\"]", "");
			    exSize = Integer.parseInt(size);
			    
			    Node ex4 = exAttr.getNamedItem("type");
			    String exName4 = ex4.toString();
			    splitter = exName4.split("=");
			    type = splitter[1].replaceAll("[\"]", "");
			    
			    Node ex5 = exAttr.getNamedItem("model");
			    String exName5 = ex5.toString();
			    splitter = exName5.split("=");
			    model = splitter[1].replaceAll("[\"]", "");
			    
			    Node ex6 = exAttr.getNamedItem("serialNo");
			    String exName6 = ex6.toString();
			    splitter = exName6.split("=");
			    serialNo = splitter[1].replaceAll("[\"]", "");
			    
			    Node ex7 = exAttr.getNamedItem("manufacturingDate");
			    String exName7 = ex7.toString();
			    splitter = exName7.split("=");
			    manufacturingDate = splitter[1].replaceAll("[\"]", "");
		 	}
		 	room.m_devices.add(new Extinguisher(exID, DeviceType.EXTINGUISHER, location, exSize, type, model, serialNo, manufacturingDate));
		 	getInspectionElement(tempExNode, room.m_devices.get(k));
		}
        
        String cabID="";
        int cabIdInt=0;
        String cabLocation="";
        String cabManufacturingDate="";
        
        NodeList listOfCabinets = device.getElementsByTagName("FireHoseCabinet");
        
        for(int k=0; k<listOfCabinets.getLength() ; k++)
		{
			Element cabinets = (Element)listOfCabinets.item(k);;
		 	NamedNodeMap cabAttr = cabinets.getAttributes();
		 	Node cabNode = listOfCabinets.item(k);
		 	
		 	tempCabNode = cabNode;
			
		 	if(cabNode.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node cab = cabAttr.getNamedItem("id");
			    String cabName = cab.toString();
			    splitter = cabName.split("=");
			    cabID = splitter[1].replaceAll("[\"]", "");
			    cabIdInt = Integer.parseInt(cabID);
			    
			    Node cab2 = cabAttr.getNamedItem("location");
			    String cabName2 = cab2.toString();
			    splitter = cabName2.split("=");
			    cabLocation = splitter[1].replaceAll("[\"]", "");
			    
			    Node cab3 = cabAttr.getNamedItem("manufacturingDate");
			    String cabName3 = cab3.toString();
			    splitter = cabName3.split("=");
			    cabManufacturingDate = splitter[1].replaceAll("[\"]", "");
		 	}
		 	room.m_devices.add(new FireHoseCabinet(cabIdInt, DeviceType.FIRE_HOSE_CABINET, cabLocation, cabManufacturingDate));
		 	getInspectionElement(tempCabNode, room.m_devices.get(k));
		}
        
        String lightID="";
        int lightIDInt=0;
        String lightLocation="";
        String lightModel="";
        int lightModelInt=0;
        String lightMake="";
        String lightNumHeads="";
        int lightNumHeadsInt=0;
        String totalPower="";
        String voltage="";
        
        NodeList listOfLights = device.getElementsByTagName("EmergencyLight");
        
        for(int k=0; k<listOfLights.getLength() ; k++)
		{
			Element lights = (Element)listOfLights.item(k);;
		 	NamedNodeMap lightsAttr = lights.getAttributes();
		 	Node lightNode = listOfLights.item(k);
		 	
		 	tempLightNode = lightNode;
		 	
		 	if(lightNode.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node light  = lightsAttr.getNamedItem("id");
			    String lightName = light.toString();
			    splitter = lightName.split("=");
			    lightID = splitter[1].replaceAll("[\"]", "");
			    lightIDInt = Integer.parseInt(lightID);
			    
			    Node light2 = lightsAttr.getNamedItem("location");
			    String lightName2 = light2.toString();
			    splitter = lightName2.split("=");
			    lightLocation = splitter[1].replaceAll("[\"]", "");
			    
			    Node light3 = lightsAttr.getNamedItem("model");
			    String lightName3 = light3.toString();
			    splitter = lightName3.split("=");
			    lightModel = splitter[1].replaceAll("[\"]", "");
			    lightModelInt = Integer.parseInt(lightModel);
			    
			    Node light4 = lightsAttr.getNamedItem("make");
			    String lightName4 = light4.toString();
			    splitter = lightName4.split("=");
			    lightMake = splitter[1].replaceAll("[\"]", "");
			    
			    Node light5 = lightsAttr.getNamedItem("numHeads");
			    String lightName5 = light5.toString();
			    splitter = lightName5.split("=");
			    lightNumHeads = splitter[1].replaceAll("[\"]", "");
			    lightNumHeadsInt = Integer.parseInt(lightNumHeads);
			    
			    Node light6 = lightsAttr.getNamedItem("totalPower");
			    String lightName6 = light6.toString();
			    splitter = lightName6.split("=");
			    totalPower = splitter[1].replaceAll("[\"]", "");
			    
			    Node light7 = lightsAttr.getNamedItem("voltage");
			    String lightName7 = light7.toString();
			    splitter = lightName7.split("=");
			    voltage = splitter[1].replaceAll("[\"]", "");
		 	}
		 	room.m_devices.add(new EmergencyLight(lightIDInt, DeviceType.EMERGENCY_LIGHT, lightLocation, lightModelInt, lightMake, lightNumHeadsInt, totalPower, voltage));
		 	getInspectionElement(tempLightNode, room.m_devices.get(k));
		}
    }
    
    public static void getInspectionElement(Node node, Device device)
    {
    	String name="";
    	String testResult="";
    	String testNote="";
    	String[] splitter;
    	
    	Element InspecEle = (Element)node;
    	NodeList listOfInspecEle = InspecEle.getElementsByTagName("EmergencyLight");
    	
    	for(int k=0; k<listOfInspecEle.getLength() ; k++)
		{
			Element InspectionEle = (Element)listOfInspecEle.item(k);;
		 	NamedNodeMap IEAttr = InspectionEle.getAttributes();
		 	Node InspecEleNode = listOfInspecEle.item(k);
		 	
		 	if(InspecEleNode.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node InspecElement  = IEAttr.getNamedItem("name");
			    String IEName = InspecElement.toString();
			    splitter = IEName.split("=");
			    name = splitter[1].replaceAll("[\"]", "");
			    
			    Node InspecElement2  = IEAttr.getNamedItem("testResult");
			    String IEName2 = InspecElement2.toString();
			    splitter = IEName2.split("=");
			    testResult = splitter[1].replaceAll("[\"]", "");
			    
			    Node InspecElement3  = IEAttr.getNamedItem("testNote");
			    String IEName3 = InspecElement3.toString();
			    splitter = IEName3.split("=");
			    testNote = splitter[1].replaceAll("[\"]", "");
		 	}
		 	device.m_inspectionElements.add(new InspectionElement(name, device.getDeviceType(), testResult, testNote));
		}
    }
}
