package controlClasses;

import java.io.File;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

import android.os.Environment;
import android.util.Log;

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
	
    public static boolean getDoc()
    {
    	try
    	{
    		//InputStream in = new FileInputStream(new File(Environment.getExternalStorageDirectory(),"InspectionData.xml"));

	    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	        Document doc = docBuilder.parse (new File(Environment.getExternalStorageDirectory(),"/InspectionData.xml"));
	        doc.getDocumentElement().normalize();
	        m_doc=doc;
	        
    	}catch (SAXParseException err) {
            System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());
            return false;

            }catch (SAXException e) {
            Exception x = e.getException ();
            ((x == null) ? e : x).printStackTrace ();
            Log.w("SAXException", "not sure");
            return false;

            }catch (Throwable t) {
            t.printStackTrace ();
            Log.w("Other Exception", "not sure");
            return false;
            
            }
    	
    	return true;
    }
    
    public static Franchisee parseFranchisee()
    {
    	Franchisee franchisee = new Franchisee(0, null);
    	
    	//TODO: Inform the user that the XML file could not be found
    	if(!getDoc())
    		return franchisee;
    	
    	String id;	
    	String name;
        
        NodeList listOfFranchisees = m_doc.getElementsByTagName("Franchisee");
        
    	Element FranchID = (Element)listOfFranchisees.item(0);;
    	NamedNodeMap Franch = FranchID.getAttributes();
    	Node Franchisee = listOfFranchisees.item(0);
    	
    	if(Franchisee.getNodeType() == Node.ELEMENT_NODE)
    	{
            Node nodeFranchID = Franch.getNamedItem("id");
            id = nodeFranchID.getNodeValue();
            
            Node nodeFranchID2 = Franch.getNamedItem("name");
            name = nodeFranchID2.getNodeValue();
            
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
	            id = ClientNodeID.getNodeValue();
	            
	            Node ClientNodeID2 = ClientAttr.getNamedItem("name");
	            name = ClientNodeID2.getNodeValue();
	            
	            Node ClientNodeID3 = ClientAttr.getNamedItem("address");
	            address = ClientNodeID3.getNodeValue();
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
    	String contractId="";
    	String contractNo="";
    	int contractNoInt=0;
    	
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
			    id = serviceAddNode.getNodeValue();
			    
			    Node serviceAddNode2 = ServiceAddressAttrs.getNamedItem("address");
			    address = serviceAddNode2.getNodeValue();
			    
			    Node serviceAddNode3 = ServiceAddressAttrs.getNamedItem("postalCode");
			    postalCode = serviceAddNode3.getNodeValue();
			    
			    Node serviceAddNode4 = ServiceAddressAttrs.getNamedItem("contact");
			    contact = serviceAddNode4.getNodeValue();
			    
			    Node serviceAddNode5 = ServiceAddressAttrs.getNamedItem("city");
			    city = serviceAddNode5.getNodeValue();
			    
			    Node serviceAddNode6 = ServiceAddressAttrs.getNamedItem("province");
			    province = serviceAddNode6.getNodeValue();
			    
			    Node serviceAddNode7 = ServiceAddressAttrs.getNamedItem("country");
			    country = serviceAddNode7.getNodeValue();
			    
			    Node serviceAddNode8 = ServiceAddressAttrs.getNamedItem("InspectorID");
			    inspectorId = serviceAddNode8.getNodeValue();
			    
			    NodeList listOfContracts = contract.getElementsByTagName("clientContract");
		        
				Element ClientContracts = (Element)listOfContracts.item(k);;
			 	NamedNodeMap ClientContractsAttrs = ClientContracts.getAttributes();
			 	Node ClientContract = listOfContracts.item(k);
			 	tempNode2 = ClientContract;
			 	
			 	if(ClientContract.getNodeType() == Node.ELEMENT_NODE)
			 	{
				    Node ClientContractNode = ClientContractsAttrs.getNamedItem("id");
				    contractId = ClientContractNode.getNodeValue();
				    
				    Node ClientContractNode2 = ClientContractsAttrs.getNamedItem("No");
				    contractNo = ClientContractNode2.getNodeValue();	
				    
				    contractNoInt = Integer.parseInt(contractNo);
			 	}
		 	}
		 	client.m_serviceAddress.add(new ServiceAddress(id, address, postalCode, contact, city, province, country, inspectorId, contractId, contractNoInt));
		 	getFloors(client.m_serviceAddress.get(k));
		}
    }
    
    public static void getFloors(ServiceAddress serAdd)
    {
    	String name="";
    	
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
			    name = ServiceAddressNode.getNodeValue();
		 	}
		 	
		 	serAdd.m_floors.add(new Floor(name));
		 	getRooms(serAdd.m_floors.get(k));
		}
    }
    
    public static void getRooms(Floor floor)
    {
    	String id="";
    	String No="";
    	
    	Element room = (Element)tempNode3;
        NodeList listOfRooms = room.getElementsByTagName("Room");
        
        for(int k=0; k<listOfRooms.getLength() ; k++)
		{
			Element rooms = (Element)listOfRooms.item(k);;
		 	NamedNodeMap roomAttr = rooms.getAttributes();
		 	Node RoomNode = listOfRooms.item(k);
		 	tempNode4 = RoomNode;
		 	
		 	if(RoomNode.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node Room = roomAttr.getNamedItem("id");
			    id = Room.getNodeValue();
			    
			    Node Room2 = roomAttr.getNamedItem("No");
			    No = Room2.getNodeValue();
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
			    id = ex.getNodeValue();
			    exID = Integer.parseInt(id);
			    
			    Node exLoc = exAttr.getNamedItem("location");
			    location = exLoc.getNodeValue();
			    
			    Node ex3 = exAttr.getNamedItem("size");
			    size = ex3.getNodeValue();
			    exSize = Integer.parseInt(size);
			    
			    Node ex4 = exAttr.getNamedItem("type");
			    type = ex4.getNodeValue();
			    
			    Node ex5 = exAttr.getNamedItem("model");
			    model = ex5.getNodeValue();
			    
			    Node ex6 = exAttr.getNamedItem("serialNo");
			    serialNo = ex6.getNodeValue();
			    
			    Node ex7 = exAttr.getNamedItem("manufacturingDate");
			    if (ex7!=null) {
			    	manufacturingDate = ex7.getNodeValue();
			    }
		 	}
		 	room.m_devices.add(new Extinguisher(exID, DeviceType.EXTINGUISHER, location, exSize, type, model, serialNo, manufacturingDate));
		 	getInspectionElement(tempExNode, room.m_devices.get(k));
		}
        
        String cabID="";
        int cabIdInt=0;
        String cabLocation="";
        String cabManufacturingDate="";
        
        int a=listOfExtinguishers.getLength();
        NodeList listOfCabinets = device.getElementsByTagName("FireHoseCabinet");
        
        for(int i=0, k=a; k<listOfCabinets.getLength()+a ; i++, k++)
		{
			Element cabinets = (Element)listOfCabinets.item(i);;
		 	NamedNodeMap cabAttr = cabinets.getAttributes();
		 	Node cabNode = listOfCabinets.item(i);
		 	
		 	tempCabNode = cabNode;
			
		 	if(cabNode.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node cab = cabAttr.getNamedItem("id");
			    cabID = cab.getNodeValue();
			    cabIdInt = Integer.parseInt(cabID);
			    
			    Node cab2 = cabAttr.getNamedItem("location");
			    cabLocation = cab2.getNodeValue();
			    
			    Node cab3 = cabAttr.getNamedItem("manufacturingDate");
			    cabManufacturingDate = cab3.getNodeValue();
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
        int b = listOfCabinets.getLength()+a;
        NodeList listOfLights = device.getElementsByTagName("EmergencyLight");
        
        for(int i=0, k=b; k<listOfLights.getLength()+b ; i++, k++)
		{
			Element lights = (Element)listOfLights.item(i);;
		 	NamedNodeMap lightsAttr = lights.getAttributes();
		 	Node lightNode = listOfLights.item(i);
		 	
		 	tempLightNode = lightNode;
		 	
		 	if(lightNode.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node light  = lightsAttr.getNamedItem("id");
			    lightID = light.getNodeValue();
			    lightIDInt = Integer.parseInt(lightID);
			    
			    Node light2 = lightsAttr.getNamedItem("location");
			    lightLocation = light2.getNodeValue();
			    
			    Node light3 = lightsAttr.getNamedItem("model");
			    lightModel = light3.getNodeValue();
			    lightModelInt = Integer.parseInt(lightModel);
			    
			    Node light4 = lightsAttr.getNamedItem("make");
			    lightMake = light4.getNodeValue();
			    
			    Node light5 = lightsAttr.getNamedItem("numHeads");
			    lightNumHeads = light5.getNodeValue();
			    lightNumHeadsInt = Integer.parseInt(lightNumHeads);
			    
			    Node light6 = lightsAttr.getNamedItem("totalPower");
			    totalPower = light6.getNodeValue();
			    
			    Node light7 = lightsAttr.getNamedItem("voltage");
			    voltage = light7.getNodeValue();
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
    	
    	Element InspecEle = (Element)node;
    	NodeList listOfInspecEle = InspecEle.getElementsByTagName("inspectionElement");
    	
    	for(int k=0; k<listOfInspecEle.getLength() ; k++)
		{
			Element InspectionEle = (Element)listOfInspecEle.item(k);;
		 	NamedNodeMap IEAttr = InspectionEle.getAttributes();
		 	Node InspecEleNode = listOfInspecEle.item(k);
		 	
		 	if(InspecEleNode.getNodeType() == Node.ELEMENT_NODE)
		 	{
		 		Node InspecElement  = IEAttr.getNamedItem("name");
			    name = InspecElement.getNodeValue();
			    
			    Node InspecElement2  = IEAttr.getNamedItem("testResult");
			    testResult = InspecElement2.getNodeValue();
			    
			    Node InspecElement3  = IEAttr.getNamedItem("testNote");
			    testNote = InspecElement3.getNodeValue();
		 	}
		 	device.m_inspectionElements.add(new InspectionElement(name, device.getDeviceType(), testResult, testNote));
		}
    }
}