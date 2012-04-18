package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Transaction implements Serializable {
	private static final long serialVersionUID = -7179549146421305022L;
	// tag 61
	private Date valueDate;
	private String bookingDate;
	private String debitCreditIndicator;
	private String thirdCharacterCurrencyCode;
	private BigDecimal amount;
	private String valueField;
	private String transactionCodeDescription;
	// under tag 61 - NS tag
	private String bookingTime;
	// tag 86
	private int transactionCode;
	private String transactionType; // <00
	private String sequenceNumber; // <10
	private String transactionTitle; // <20 - <26
	private String counterPartyNameAndAddress; // <27 - <29 + <60
	private String technicalField; // <30 - <33
	private String counterPartyAccount; // <31
	private String reconciliationCode; // <61 - <62 + <64
	private String referenceNumber; // <63
	
	public Transaction() {}

	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("\t\t** Start of Transaction **" + '\n');
		output.append("\t\t*** Tag 61 ***" + '\n');
		output.append("\t\t\tValueDate                  : " + this.getValueDate() + '\n');
		output.append("\t\t\tBookingDate                : " + this.getBookingDate() + '\n');
		output.append("\t\t\tDebitCreditIndicator       : " + this.getDebitCreditIndicator() + '\n');
		output.append("\t\t\tThirdCharacterCurrencyCode : " + this.getThirdCharacterCurrencyCode() + '\n');
		output.append("\t\t\tAmount                     : " + this.getAmount() + '\n');
		output.append("\t\t\tValueField                 : " + this.getValueField() + '\n');
		output.append("\t\t\tTransactionCodeDescription : " + this.getTransactionCodeDescription() + '\n');
		
		output.append("\t\t*** Tag 61-NS ***" + '\n');
		output.append("\t\t\tBookingTime                : " + this.getBookingTime() + '\n');
		output.append("\t\t*** Tag 86 ***" + '\n');
		output.append("\t\t\tTransactionCode            : " + this.getTransactionCode() + '\n');
		output.append("\t\t\tTransactionType            : " + this.getTransactionType() + '\n');
		output.append("\t\t\tSequenceNumber             : " + this.getSequenceNumber() + '\n');
		output.append("\t\t\tTransactionTitle           : " + this.getTransactionTitle() + '\n');
		output.append("\t\t\tCounterPartyNameAndAddress : " + this.getCounterPartyNameAndAddress() + '\n');
		output.append("\t\t\tCounterPartyAccount        : " + this.getCounterPartyAccount() + '\n');
		output.append("\t\t\tTechnicalField             : " + this.getTechnicalField() + '\n');
		output.append("\t\t\tReconciliationCode         : " + this.getReconciliationCode() + '\n');
		output.append("\t\t\tReferenceNumber            : " + this.getReferenceNumber() + '\n');
		output.append("\t\t** End of Transaction **" + '\n');
		return output.toString();
	}

	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getDebitCreditIndicator() {
		return debitCreditIndicator;
	}
	public void setDebitCreditIndicator(String debitCreditIndicator) {
		this.debitCreditIndicator = debitCreditIndicator;
	}
	public String getThirdCharacterCurrencyCode() {
		return thirdCharacterCurrencyCode;
	}
	public void setThirdCharacterCurrencyCode(String thirdCharacterCurrencyCode) {
		this.thirdCharacterCurrencyCode = thirdCharacterCurrencyCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public String getTransactionCodeDescription() {
		return transactionCodeDescription;
	}
	public void setTransactionCodeDescription(String transactionCodeDescription) {
		this.transactionCodeDescription = transactionCodeDescription;
	}
	public String getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	public int getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(int transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getTransactionTitle() {
		return transactionTitle;
	}
	public void setTransactionTitle(String transactionTitle) {
		this.transactionTitle = transactionTitle;
	}
	public String getCounterPartyNameAndAddress() {
		return counterPartyNameAndAddress;
	}
	public void setCounterPartyNameAndAddress(String counterPartyNameAndAddress) {
		this.counterPartyNameAndAddress = counterPartyNameAndAddress;
	}
	public String getTechnicalField() {
		return technicalField;
	}
	public void setTechnicalField(String technicalField) {
		this.technicalField = technicalField;
	}
	public String getCounterPartyAccount() {
		return counterPartyAccount;
	}
	public void setCounterPartyAccount(String counterPartyAccount) {
		this.counterPartyAccount = counterPartyAccount;
	}
	public String getReconciliationCode() {
		return reconciliationCode;
	}
	public void setReconciliationCode(String reconciliationCode) {
		this.reconciliationCode = reconciliationCode;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
}
