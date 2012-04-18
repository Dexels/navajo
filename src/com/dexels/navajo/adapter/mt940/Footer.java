package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;

public class Footer implements Serializable {
	private static final long serialVersionUID = 6642413657119026305L;
	private ClosingBalance closingBalance;
	private AvailableBalance availableBalance;
	private String additionalInformation;
	
	public Footer() {
		this.setClosingBalance(new ClosingBalance());
		this.setAvailableBalance(new AvailableBalance());
	}
    
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("** Start of Footer object **" + '\n');
		output.append("AvailableBalance      : \n" + this.getAvailableBalance().toString() + '\n');
		output.append("ClosingBalance        : \n" + this.getClosingBalance().toString() + '\n');
		output.append("AdditionalInformation : " + this.getAdditionalInformation() + '\n');
		output.append("** End of Footer object **");
		return output.toString();
	}
	
	public ClosingBalance getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(ClosingBalance closingBalance) {
		this.closingBalance = closingBalance;
	}
	public AvailableBalance getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(AvailableBalance availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
}
