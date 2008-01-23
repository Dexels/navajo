package com.dexels.navajo.adapter.clieop;

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
	public String beneficiaryNameRecord;
	public String beneficiaryPlaceRecord;

	public String paymentDescription;
	public String lastName;
	public String amount;

	private String formattedamount;

	public String city;
	public String payerAccountNumber;
	public String payerAccountType;
	public String beneficiaryAccountNumber;
	public String beneficiaryAccountType;
	public String description;
	public String transactionType;

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
		if (!beneficiaryAccountType.equals("GIRO")) {
			if (checkBankAccount()) {
				transactionRecord = getTransactionRecord();
				paymentCharacteristicRecord = getPaymentCharacteristicRecord();
				descriptionRecord = getDescriptionRecord();
				beneficiaryNameRecord = getBeneficiaryNameRecord();
				beneficiaryPlaceRecord = getBeneficiaryPlaceRecord();
			}
		} else {
			transactionRecord = getTransactionRecord();
			paymentCharacteristicRecord = getPaymentCharacteristicRecord();
			descriptionRecord = getDescriptionRecord();
			beneficiaryNameRecord = getBeneficiaryNameRecord();
			beneficiaryPlaceRecord = getBeneficiaryPlaceRecord();
		}
	}

	public void kill() {
	}

	public String getTransactionRecord() {
		return "0100A" + transactionType + formattedamount + payerAccountNumber + beneficiaryAccountNumber;
	}

	public String getPayerNameRecord() {
		return "0110B" + lastName;
	}

	public String getPayerPlaceRecord() {
		return "0113B";
	}

	public String getPaymentCharacteristicRecord() {
		return "0150A" + paymentDescription;
	}

	public String getDescriptionRecord() {
		return "0160A" + description;
	}

	public String getBeneficiaryNameRecord() {
		return "0170B" + lastName;
	}

	public String getBeneficiaryPlaceRecord() {
		beneficiaryPlaceRecord = "0173B" + city;
		int currentLen = beneficiaryPlaceRecord.length();
		// Fil up to 29 characters.
		for (int i = 0; i < (29 - currentLen); i++) {
			beneficiaryPlaceRecord = beneficiaryPlaceRecord + " ";
		}
		return beneficiaryPlaceRecord;
	}

	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAmount(String a) {

		double amountDouble = Double.parseDouble(a);
		amountDouble = amountDouble * 100;

		amount = String.valueOf(Math.round(amountDouble));
		formattedamount = amount;
		if (formattedamount.length() != 12) {
			int sizeOfLoop = 12 - formattedamount.length();
			for (int i = 0; i < sizeOfLoop; i++) {
				formattedamount = "0" + formattedamount;
			}
		}
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPayerAccountNumber(String accountNumber) {
		if (accountNumber.length() != 10) {
			int sizeOfLoop = 10 - accountNumber.length();
			for (int i = 0; i < sizeOfLoop; i++) {
				accountNumber = "0" + accountNumber;
			}
		}
		this.payerAccountNumber = accountNumber;
	}

	public void setPayerAccountType(String accountType) {
		this.payerAccountType = accountType;
	}

	public void setBeneficiaryAccountNumber(String accountNumber) {
		if (accountNumber.length() != 10) {
			int sizeOfLoop = 10 - accountNumber.length();
			for (int i = 0; i < sizeOfLoop; i++) {
				accountNumber = "0" + accountNumber;
			}
		}
		this.beneficiaryAccountNumber = accountNumber;
	}

	public void setBeneficiaryAccountType(String accountType) {
		this.beneficiaryAccountType = accountType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTransactionType(String type) {
		this.transactionType = type;
	}

	/*
	 * Elfproef voor bankrekeningnummers: 1st number * 10 2nd number * 9 ...
	 * 10th number * 1 divide sum of all numbers by 11. if a whole number
	 * without decimals is retrieved return true
	 */
	public boolean checkBankAccount() {
		if (beneficiaryAccountNumber.length() != 10) {
			return false;
		}

		int sum = 0;

		for (int i = 0; i < 10; i++) {
			if (!beneficiaryAccountNumber.substring(i, i + 1).matches("[0-9]")) {
				return false;
			}
			sum += (10 - i) * (Integer.parseInt(beneficiaryAccountNumber.substring(i, i + 1)));
		}

		return (sum % 11 == 0);
	}

	public static void main(String[] args) {
		double amountDouble = Double.parseDouble("75.6");

		System.err.println("amountDouble = " + amountDouble);
		amountDouble = amountDouble * 100;
		System.err.println("amountDouble = " + amountDouble);

		String amount = String.valueOf(Math.round(amountDouble));

		System.err.println("amount = " + amount);
	}

}
