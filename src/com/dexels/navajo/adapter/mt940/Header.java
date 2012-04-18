package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;

public class Header implements Serializable {
	private static final long serialVersionUID = -8613212499941678225L;
	
    private String generationDateTime; // format = DDDHHMM (DDD is day in the year)
    private String IBAN;
    private int statementNumber;
    private String accountOwnerName;
    private String accountName;
    private OpeningBalance openingBalance;
    
    public Header() {
    	this.setOpeningBalance(new OpeningBalance());
    }
    
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("\t** Start of Header object **" + '\n');
		output.append("\t\tAccountOwnerName   : " + this.getAccountOwnerName() + '\n');
		output.append("\t\tAccountName        : " + this.getAccountName() + '\n');
		output.append("\t\tIBAN               : " + this.getIBAN() + '\n');
		output.append("\t\tStatementNumber    : " + this.getStatementNumber() + '\n');
		output.append("\t\tGenerationDateTime : " + this.getGenerationDateTime() + '\n');
		output.append(this.getOpeningBalance().toString() + '\n');
		output.append("\t** End of Header object **");
		return output.toString();
	}
    
	public String getGenerationDateTime() {
		return generationDateTime;
	}
	public void setGenerationDateTime(String generationDateTime) {
		this.generationDateTime = generationDateTime;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String IBAN) {
		this.IBAN = IBAN;
	}
	public int getStatementNumber() {
		return statementNumber;
	}
	public void setStatementNumber(int statementNumber) {
		this.statementNumber = statementNumber;
	}
	public String getAccountOwnerName() {
		return accountOwnerName;
	}
	public void setAccountOwnerName(String accountOwnerName) {
		this.accountOwnerName = accountOwnerName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public OpeningBalance getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(OpeningBalance openingBalance) {
		this.openingBalance = openingBalance;
	}
}
