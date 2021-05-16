package br.com.ftt.ec6.seniorLiving.utils;

import java.util.ArrayList;
import java.util.List;

public enum MaritalStatus {

	SOLTEIRO("Solteiro (a)"),
	CASADO("Casado (a)"),
	DIVORCIADO("Divorciado (a)"),
	VIUVO("Viúvo (a)");
	
	private String marialStatus;
	
	MaritalStatus(String marialStatus){
		this.marialStatus = marialStatus;
	}
	
	public String getMarialStatus() {
		return marialStatus;
	}
	
	public List<String> getAllMarialStatus(){
		MaritalStatus[] marialStatusList = this.getDeclaringClass().getEnumConstants();
		
		List<String> marialStatusListString = new ArrayList<String>();
		
		for (MaritalStatus marialStatus : marialStatusList) {
			marialStatusListString.add(marialStatus.getMarialStatus());
		}
		
		return marialStatusListString;
	}
	
	@Override
	public String toString() {
		return getMarialStatus();
	}
}
