package com.temp.sample;

public class MethodDetails {

	
	private String testCaseID;
	private String auditTrail;
	private String translationKey;
	
	public MethodDetails(String testCaseID, String auditTrail,String translationKey ) {
		this.testCaseID = testCaseID;
		this.auditTrail=auditTrail;
		this.translationKey=translationKey;
	}

	
	public String getAuditTrail() {
		return auditTrail;
	}


	public void setAuditTrail(String auditTrail) {
		this.auditTrail = auditTrail;
	}


	public String getTranslationKey() {
		return translationKey;
	}


	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}


	

	public String getTestCaseID() 
	{
		return testCaseID;
	}


	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}
	
	
}
