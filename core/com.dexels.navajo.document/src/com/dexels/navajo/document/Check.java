package com.dexels.navajo.document;

import java.io.Serializable;

public interface Check extends Serializable {
	
	public String getCode();
	
	public String getDescription();
	
	public String getCondition();
	
	public String getRule();
	
	public void setRule(String r);

}
