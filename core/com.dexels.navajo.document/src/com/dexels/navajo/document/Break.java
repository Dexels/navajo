package com.dexels.navajo.document;

import java.io.Serializable;

public interface Break extends Serializable {

	public String getCondition();
	
	public String getConditionId();
	
	public String getConditioDescription();
	
	public void setCondition(String condition);
	
	public void setConditionId(String id);
	
	public void setConditionDescription(String description);
	
}
