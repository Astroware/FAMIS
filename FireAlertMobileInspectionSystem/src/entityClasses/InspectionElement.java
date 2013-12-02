package entityClasses;

import entityClasses.Device.DeviceType;

public class InspectionElement {
	private String m_name;
	private DeviceType m_deviceType;
	private InputType m_inputType;
	private Result m_testResult;
	private String m_testNote;
	private Boolean m_complete;
	
	public enum InputType {
		PASS_FAIL, GOOD_POOR, YES_NO
	}
	
	public enum Result {
		EMPTY, PASS, FAIL, GOOD, POOR, NA, YES, NO
	}
	
	public InspectionElement(String name, DeviceType deviceType, String result, String testNote) {
		m_name = name;
		m_deviceType = deviceType;
		m_testResult = getResult(result);
		m_testNote = testNote;
		m_complete = false;
		
		switch (m_deviceType) {
		case EXTINGUISHER:	
			m_inputType = InputType.PASS_FAIL;
			break;
		case FIRE_HOSE_CABINET:
			if (m_name == "Cabinet Condition" || m_name == "Nozzle Condition") {
				m_inputType = InputType.GOOD_POOR;
			}
			else {
				m_inputType = InputType.YES_NO;
			}
			break;
		case EMERGENCY_LIGHT:
			m_inputType = InputType.YES_NO;
			break;
		default:
			System.out.println("Error with device type.");
			break;
		}
	}
	
	public Boolean isComplete() {
		if (m_complete == false) {
			if (m_testResult != Result.EMPTY && m_testNote != null && m_testNote.trim().length() > 0) {
				m_complete = true;
			}
		}
		
		return m_complete;
	}
	
	public Result getResult(String result)
	{
		if(result.equalsIgnoreCase(""))
		{
			return Result.EMPTY;
		}
		
		else if(result.equalsIgnoreCase("PASS"))
		{
			return Result.PASS;
		}
		
		else if(result.equalsIgnoreCase("FAIL"))
		{
			return Result.FAIL;
		}
		
		else if(result.equalsIgnoreCase("GOOD"))
		{
			return Result.GOOD;
		}
		
		else if(result.equalsIgnoreCase("POOR"))
		{
			return Result.POOR;
		}
		
		else if(result.equalsIgnoreCase("NA"))
		{
			return Result.NA;
		}
		
		else if(result.equalsIgnoreCase("YES"))
		{
			return Result.YES;
		}
		
		else if(result.equalsIgnoreCase("NO"))
		{
			return Result.NO;
		}
		else
			return Result.EMPTY;
	}
	
	//---------------------------------gets---------------------------------------------
	
	public String getName() {
		return m_name;
	}
	
	public Result getTestResult() {
		return m_testResult;
	}
	
	public String getTestNote() {
		return m_testNote;
	}
	
	public InputType getInputType() {
		return m_inputType;
	}
	
	//---------------------------------sets---------------------------------------------
	
	//call this function when the user selects one of the radio button options
	//Don't forget to check the testNote attribute if the testResult is FAIL before calling this function
	public void setComplete(Boolean bool) {
		m_complete = bool;
	}
	
	public void setTestResult(Result result) {
		m_testResult = result;
	}
	
	public void setTestNote (String note) {
		m_testNote = note;
	}
	
}
