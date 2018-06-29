package com.temp.sample;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataUtil 
{

	private static DataUtil dataUtil = null;

	private Map<String, Object> details = new HashMap<String, Object>();
	
	

	private DataUtil() 
	{
		
		loginDetails();
		
				
	}

	
	public static DataUtil getInstance()
	{
		if (dataUtil == null) 
		{
			dataUtil = new DataUtil();
		}
		return dataUtil;
	}

	public Map<String, Object> getDetails() 
	{
		return details;
	}

	public void setDetails(Map<String, Object> details) 
	{
		this.details = details;
	}
	
	/*=================================== Admin Login Start Here ====================================== */
	
	private void loginDetails()
	
	{
		details.put(Constants.login.VERIFYLOGINWITHBLANK,new MethodDetails("TC_Login_001",Constants.AUDITTRAILNOTREQUIRED,Constants.KEY));	
		
	}
}
