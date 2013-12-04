package controlClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import android.os.Environment;
import entityClasses.Client;
import entityClasses.Device;
import entityClasses.Device.DeviceType;
import entityClasses.EmergencyLight;
import entityClasses.Extinguisher;
import entityClasses.FireHoseCabinet;
import entityClasses.Floor;
import entityClasses.Franchisee;
import entityClasses.InspectionElement;
import entityClasses.Inspector;
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
	
	private static final String inspectorFilePath = "/Inspectors.xml";
	private static final String inspectionDataFilePath = "/InspectionData.xml";
	
	public static String getInspectorFilePath() {
		return inspectorFilePath;
	}
	
	public static String getInspectionDataFilePath() {
		return inspectionDataFilePath;
	}
	
    public static void setDoc(String filePath) throws SAXException, FileNotFoundException, IOException, ParserConfigurationException
    {	
    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse (new File(Environment.getExternalStorageDirectory(),filePath));
        //Document doc = docBuilder.parse (new File(Environment.getDataDirectory(),filePath));
        doc.getDocumentElement().normalize();
        m_doc=doc;        
    }
    
    public static Franchisee parseFranchisee()
    {
    	Franchisee franchisee = new Franchisee(0, null);
    	
    	//TODO: Inform the user that the XML file could not be found
    	
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
			Element floors = (Element)listOfFloors.item(k);
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
			Element rooms = (Element)listOfRooms.item(k);
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
			Element extinguishers = (Element)listOfExtinguishers.item(k);
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
			Element lights = (Element)listOfLights.item(i);
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
			Element InspectionEle = (Element)listOfInspecEle.item(k);
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
    
    public static void getInspectors(ArrayList<Inspector> inspectors)
	  {
		  NodeList nList = m_doc.getElementsByTagName("Inspector");
		  
		  for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					inspectors.add(new Inspector(getId(eElement),getName(eElement),getUsername(eElement),getPassword(eElement),getFranchiseeFlag(eElement)));
				}
			}
	  }
	  public static String getId(Element eElement)
	  {
		  NodeList nList = eElement.getElementsByTagName("ID");
		  String ID = nList.item(0).getFirstChild().getNodeValue();
		  return ID;
	  }
	  public static String getName(Element eElement)
	  {
		  NodeList nList = eElement.getElementsByTagName("Name");
		  String name = nList.item(0).getFirstChild().getNodeValue();
		  return name;
	  }
	  public static String getUsername(Element eElement)
	  {
		  NodeList nList = eElement.getElementsByTagName("Username");
		  String username = nList.item(0).getFirstChild().getNodeValue();
		  return username;
	  }
	  public static String getPassword(Element eElement)
	  {
		  NodeList nList = eElement.getElementsByTagName("Password");
		  String password = nList.item(0).getFirstChild().getNodeValue();
		  return password;
	  }
	  public static Boolean getFranchiseeFlag(Element eElement)
	  {
		  NodeList nList = eElement.getElementsByTagName("FranchFlag");
		  Boolean flag = Boolean.parseBoolean(nList.item(0).getFirstChild().getNodeValue());
		  return flag;
	  }
	  public static void addInspector(Inspector ins)
	  {  
		  	Element newInspector = m_doc.createElement("Inspector");
			
			NodeList root = m_doc.getElementsByTagName("InspectorList");
			root.item(0).appendChild(newInspector);
			
			Element ID = m_doc.createElement("ID");
			Text IDtext = m_doc.createTextNode(ins.getId()+"");
			ID.appendChild(IDtext);
			Element Name = m_doc.createElement("Name");
			Text Nametext = m_doc.createTextNode(ins.getName());
			Name.appendChild(Nametext);
			Element Username = m_doc.createElement("Username");
			Text Usernametext = m_doc.createTextNode(ins.getUsername());
			Username.appendChild(Usernametext);
			Element Password = m_doc.createElement("Password");
			Text Passwordtext = m_doc.createTextNode(ins.getPassword());
			Password.appendChild(Passwordtext);
			Element Flag = m_doc.createElement("FranchFlag");
			Text Flagtext = m_doc.createTextNode(ins.getFlag()+"");
			Flag.appendChild(Flagtext);
			
			newInspector.appendChild(ID);
			newInspector.appendChild(Name);
			newInspector.appendChild(Username);
			newInspector.appendChild(Password);
			newInspector.appendChild(Flag);
			
			updateDocument();
			
			return;
	  }
	  public static void removeInspector(Inspector ins)
	  {
		  NodeList nList = m_doc.getElementsByTagName("Inspector");
		  String id = ins.getId();
		
		  for (int i = 0; i < nList.getLength(); i++) {
			  Node nNode = nList.item(i);
			  NodeList children = nNode.getChildNodes();
			  for(int j = 0; j <children.getLength(); j++)
			  {
				  Node child = children.item(j);
				  if(child.getNodeType() == Node.ELEMENT_NODE)
				  {  
					  if(child.getFirstChild().getNodeValue().equals(id)) {
						  nNode.getParentNode().removeChild(nNode);
					  }	
				  }
			  }
		  }
		  XMLParse.updateDocument();
		  return;
	  }
	  public static void updateDocument()
	  {
		  try{
			 TransformerFactory transformerFactory = TransformerFactory.newInstance();
			  Transformer transformer = transformerFactory.newTransformer();
			  DOMSource source = new DOMSource(m_doc);
			  StreamResult streamResult =  new StreamResult(new File(Environment.getExternalStorageDirectory(),getInspectorFilePath()));
			  transformer.transform(source, streamResult);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
	  
	  public static void writeInspection()
	  {
		  Node tempLocationNode = null;
		  String checkID = EquipmentControl.getInstance().getLocation().getId();
		  
		  NodeList listOfAddresses = m_doc.getElementsByTagName("ServiceAddress");
		  
		  for(int k=0; k<listOfAddresses.getLength() ; k++)
		  {
			    Node ServiceAddressNode = listOfAddresses.item(k);
			 	NamedNodeMap ServiceAddressAttrs = ServiceAddressNode.getAttributes();
			 	
			 	if(ServiceAddressNode.getNodeType() == Node.ELEMENT_NODE)
			 	{
			 		String tempID;
				    tempID = ServiceAddressAttrs.getNamedItem("id").getNodeValue();
				    
				    if(checkID.equals(tempID))
				    {
				    	tempLocationNode = ServiceAddressNode;
				    	break;
				    }
			 	}
		  }
	      writeResults(tempLocationNode);
		  updateInspectionData();
	  }
	  
	  public static void updateInspectionData()
	  {
		  try{
			 TransformerFactory transformerFactory = TransformerFactory.newInstance();
			  Transformer transformer = transformerFactory.newTransformer();
			  DOMSource source = new DOMSource(m_doc);
			  StreamResult streamResult =  new StreamResult(new File(Environment.getExternalStorageDirectory(),"InspectionData.xml"));
			  
				transformer.transform(source, streamResult);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
	  
	  public static void writeResults(Node addressNode)
	  {
		  Element location = (Element)addressNode;
		  
	      NodeList listOfExtinguishers = location.getElementsByTagName("Extinguisher");
	      int extIndex = 0;
	      
	      NodeList listOfCabinets = location.getElementsByTagName("FireHoseCabinet");
	      int cabIndex = 0;
	      
	      NodeList listOfLights = location.getElementsByTagName("EmergencyLight");
	      int lightIndex = 0;
	      
		  for (int i=0; i<EquipmentControl.getInstance().getFloorListSize(); i++)
		  {
			  EquipmentControl.getInstance().setFloor(i);
			  for (int j=0; j<EquipmentControl.getInstance().getRoomListSize(); j++)
			  {
				  EquipmentControl.getInstance().setRoom(j);
				  for(int k=0; k<EquipmentControl.getInstance().getDeviceListSize(); k++)
				  {
					  EquipmentControl.getInstance().setDevice(k);
					  if(EquipmentControl.getInstance().getDevice().getDeviceType().equals(DeviceType.EXTINGUISHER))
				      {
						  Node Extinguisher = listOfExtinguishers.item(extIndex);
						  Element InspecEle = (Element)Extinguisher;
					      NodeList listOfInspecEle = InspecEle.getElementsByTagName("inspectionElement");
					      
						  for(int a=0; a<EquipmentControl.getInstance().getInspectionElementListSize(); a++)
						  {
							  ((Element)listOfInspecEle.item(a)).setAttribute("testResult", EquipmentControl.getInstance().getInspectionElement(a).getTestResult().toString());
							  ((Element)listOfInspecEle.item(a)).setAttribute("testNote", EquipmentControl.getInstance().getInspectionElement(a).getTestNote().toString()); 
						  }
						  extIndex++;
				      }
					  
					  else if(EquipmentControl.getInstance().getDevice().getDeviceType().equals(DeviceType.FIRE_HOSE_CABINET))
				      {
						  Node Cabinet = listOfCabinets.item(cabIndex);
						  Element cabinetEle = (Element)Cabinet;
						  NodeList listOfInspecEle = cabinetEle.getElementsByTagName("inspectionElement");
						  
						  for(int a=0; a<EquipmentControl.getInstance().getInspectionElementListSize(); a++)
						  {
							  ((Element)listOfInspecEle.item(a)).setAttribute("testResult", EquipmentControl.getInstance().getInspectionElement(a).getTestResult().toString());
							  ((Element)listOfInspecEle.item(a)).setAttribute("testNote", EquipmentControl.getInstance().getInspectionElement(a).getTestNote().toString()); 
						  }
						  cabIndex++;
				      }
					  
					  else if(EquipmentControl.getInstance().getDevice().getDeviceType().equals(DeviceType.EMERGENCY_LIGHT))
				      {
						  Node Lights = listOfLights.item(lightIndex);
						  Element lightsEle = (Element)Lights;
						  NodeList listOfInspecEle = lightsEle.getElementsByTagName("inspectionElement");
						  
						  for(int a=0; a<EquipmentControl.getInstance().getInspectionElementListSize(); a++)
						  {
							  ((Element)listOfInspecEle.item(a)).setAttribute("testResult", EquipmentControl.getInstance().getInspectionElement(a).getTestResult().toString());
							  ((Element)listOfInspecEle.item(a)).setAttribute("testNote", EquipmentControl.getInstance().getInspectionElement(a).getTestNote().toString()); 
						  }
						  lightIndex++;
				      }
				  }
			  }
		  }
	  }
	  
	  public static String getXMLFile() {
		
			FileInputStream inputStream;
			try {
				String filePath = Environment.getExternalStorageDirectory() + inspectionDataFilePath;
				inputStream = new FileInputStream(filePath);
				Scanner s = new Scanner(inputStream).useDelimiter("\\A");
				return s.hasNext() ? s.next() : "";
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		 
	  }
}
