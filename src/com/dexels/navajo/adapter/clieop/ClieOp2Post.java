package com.dexels.navajo.adapter.clieop;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class ClieOp2Post implements Mappable {

	public String transactionRecord;
	public String paymentCharacteristicRecord;
	public String descriptionRecord;
	public String benaficiaryNameRecord;
	public String payerNameRecord;
	
	public String paymentDescription;
	public String lastName;
	public String amount;
	private String formattedamount;
	public String accountNumberReceiver;
	public String accountNumberPayer;
	public String accountType;
	
	//new
	public boolean isPayment;
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
		if(!accountType.equals("GIRO")){
			if(isPayment){
				if(checkBankAccount(accountNumberReceiver)){
					transactionRecord = getTransactionRecord();
					paymentCharacteristicRecord = getPaymentCharacteristicRecord();
					descriptionRecord = getDescriptionRecord();
					benaficiaryNameRecord = getBenaficiaryNameRecord();
				}
			} else {
				if(checkBankAccount(accountNumberPayer)){
					transactionRecord = getTransactionRecord();
					paymentCharacteristicRecord = getPaymentCharacteristicRecord();
					descriptionRecord = getDescriptionRecord();
					benaficiaryNameRecord = getBenaficiaryNameRecord();
				}
			}
		} else {
			transactionRecord = getTransactionRecord();
			paymentCharacteristicRecord = getPaymentCharacteristicRecord();
			descriptionRecord = getDescriptionRecord();
			benaficiaryNameRecord = getPayerNameRecord();
		}
	}

	public void kill() {
	}

	public static void main(String[] args) {
		double amountDouble = Double.parseDouble("75.6");
		
		System.err.println("amountDouble = " + amountDouble);
		amountDouble = amountDouble * 100;
		System.err.println("amountDouble = " + amountDouble);
		
		String amount = String.valueOf( Math.round(amountDouble) );
		
		System.err.println("amount = " + amount);
	}
	
	public String getTransactionRecord(){
		if(isPayment) {
			transactionRecord = "0100A0005"+formattedamount+accountNumberPayer+accountNumberReceiver;
		} else {
			transactionRecord = "0100A1001"+formattedamount+accountNumberPayer+accountNumberReceiver;
		}
		return transactionRecord;
	}
	public String getPaymentCharacteristicRecord(){
		paymentCharacteristicRecord = "0150A"+paymentDescription;
		return paymentCharacteristicRecord;
	}
	public String getDescriptionRecord(){
		descriptionRecord = "0160A";
		return descriptionRecord;
	}
	public String getBenaficiaryNameRecord(){
		benaficiaryNameRecord = "0170B"+lastName;
		return benaficiaryNameRecord;
	}
	public String getPayerNameRecord(){
		payerNameRecord = "0170B"+lastName;
		return payerNameRecord;
	}
	
	public void setPaymentDescription(String paymentDescription){
		this.paymentDescription = paymentDescription;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}	
	
	public void setAmount(String a) {
		
		double amountDouble = Double.parseDouble(a);
		amountDouble = amountDouble * 100;
		
		amount = String.valueOf( Math.round(amountDouble) );
		formattedamount = amount;
		if(formattedamount.length()!=12){
			int sizeOfLoop = 12 - formattedamount.length();
			for(int i=0; i<sizeOfLoop;i++){
				formattedamount = "0"+formattedamount;
			}
		}
	}	
	
	public void setAccountNumberReceiver(String accountNumber){
		if(accountNumber.length()!= 10){
			int sizeOfLoop = 10-accountNumber.length();
			for (int i=0;i<sizeOfLoop;i++){
				accountNumber = "0"+accountNumber;
			}
		}
		this.accountNumberReceiver = accountNumber;
	}
	public void setAccountNumberPayer(String accountNumber){
		if(accountNumber.length()!= 10){
			int sizeOfLoop = 10-accountNumber.length();
			for (int i=0;i<sizeOfLoop;i++){
				accountNumber = "0"+accountNumber;
			}
		}
		this.accountNumberPayer = accountNumber;
	}
	public void setAccountType(String accountType){
		this.accountType = accountType;
	}
	public void setIsPayment(boolean isPayment){
		this.isPayment = isPayment;
	}

	/*
	 * Elfproef voor bankrekeningnummers:
	 * 1st number * 10
	 * 2nd number * 9
	 * ...
	 * 10th number * 1
	 * divide sum of all numbers by 11. if a whole number without decimals is retrieved return true
	 */
	public boolean checkBankAccount(String accountNumber) {
		if(accountNumber.length()!= 10){
			return false;
		}
		int sum=0;
		for(int i=0; i<10; i++){
			if(!accountNumber.substring(i,i+1).matches("[0-9]")){
				return false;
			}
			sum += (10-i)*(Integer.parseInt(accountNumber.substring(i,i+1)));			
		}
		if(sum % 11 == 0){
			return true;
		}
		return false;
	}
	
	
}
