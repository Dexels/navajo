package com.dexels.navajo.adapter;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class ClieOpPost implements Mappable {

	public String transactionRecord;
	public String paymentCharacteristicRecord;
	public String descriptionRecord;
	public String benaficiaryNameRecord;
	public String benaficiaryPlaceRecord;
	
	public String paymentDescription;
	public String lastName;
	public String amount;
	public String city;
	public String accountNumber;
	public String accountType;
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
		if(!accountType.equals("GIRO")){
			if(checkBankAccount()){
				transactionRecord = getTransactionRecord();
				paymentCharacteristicRecord = getPaymentCharacteristicRecord();
				descriptionRecord = getDescriptionRecord();
				benaficiaryNameRecord = getBenaficiaryNameRecord();
				benaficiaryPlaceRecord = getBenaficiaryPlaceRecord();
			}
		} else {
			transactionRecord = getTransactionRecord();
			paymentCharacteristicRecord = getPaymentCharacteristicRecord();
			descriptionRecord = getDescriptionRecord();
			benaficiaryNameRecord = getBenaficiaryNameRecord();
			benaficiaryPlaceRecord = getBenaficiaryPlaceRecord();
		}
	}

	public void kill() {
	}

	public static void main(String[] args) {
	}
	
	public String getTransactionRecord(){
		transactionRecord = "0100A0005"+amount+"0670000132"+accountNumber;
		return transactionRecord;
	}
	public String getPaymentCharacteristicRecord(){
		paymentCharacteristicRecord = "0150A"+paymentDescription;
		return paymentCharacteristicRecord;
	}
	public String getDescriptionRecord(){
		descriptionRecord = "0160A"+paymentDescription;
		return descriptionRecord;
	}
	public String getBenaficiaryNameRecord(){
		benaficiaryNameRecord = "0170B"+lastName;
		return benaficiaryNameRecord;
	}
	public String getBenaficiaryPlaceRecord(){
		benaficiaryPlaceRecord = "0173B"+benaficiaryPlaceRecord;
		return benaficiaryPlaceRecord;
	}
	
	public void setPaymentDescription(String paymentDescription){
		this.paymentDescription = paymentDescription;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}	
	public void setAmount(String amount){
		if(amount.length()!=12){
			int sizeOfLoop = 12 - amount.length();
			for(int i=0; i<sizeOfLoop;i++){
				amount = "0"+amount;
			}
		}
		this.amount = amount;
	}	
	public void setCity(String city){
		this.city = city;
	}
	public void setAccountNumber(String accountNumber){
		if(accountNumber.length()!= 10){
			int sizeOfLoop = 10-accountNumber.length();
			for (int i=0;i<sizeOfLoop;i++){
				accountNumber = "0"+accountNumber;
			}
		}
		this.accountNumber = accountNumber;
	}
	public void setAccountType(String accountType){
		this.accountType = accountType;
	}

	/*
	 * Elfproef voor bankrekeningnummers:
	 * 1st number * 10
	 * 2nd number * 9
	 * ...
	 * 10th number * 1
	 * divide sum of all numbers by 11. if a whole number without decimals is retrieved return true
	 */
	public boolean checkBankAccount() {
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
