package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpeningBalance implements Serializable {
	private static final long serialVersionUID = -1565587869048370806L;
	private String debitCreditIndicator;
	private Date date;
	private String currency;
	private BigDecimal amount;
	
	public OpeningBalance() {}
	
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("\t** Start of OpeningBalance **" + '\n');
		output.append("\tDebitCreditIndicator : " + this.getDebitCreditIndicator() + '\n');
		output.append("\tDate                 : " + this.getDate() + '\n');
		output.append("\tCurrency             : " + this.getCurrency() + '\n');
		output.append("\tAmount               : " + this.getAmount() + '\n');
		output.append("\t** End of OpeningBalance **");
		return output.toString();
	}
	
	public String getDebitCreditIndicator() {
		return debitCreditIndicator;
	}
	public void setDebitCreditIndicator(String debitCreditIndicator) {
		this.debitCreditIndicator = debitCreditIndicator;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
