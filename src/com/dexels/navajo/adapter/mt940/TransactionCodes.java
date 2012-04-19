package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class TransactionCodes implements Serializable {
	private static final long serialVersionUID = 6704219603632619051L;
	private Map<Integer, String> transactionCodes = new TreeMap<Integer, String>();
	
	public TransactionCodes() {
		fillTransactionCodesMap();
	}

	public static void main(String[] args) throws Exception {
		TransactionCodes transactionCodes = new TransactionCodes();
		System.out.println("Code (0): " + transactionCodes.getTransactionCodeDescription(0));
		System.out.println("Code (1): " + transactionCodes.getTransactionCodeDescription(1));
		System.out.println("Code (7): " + transactionCodes.getTransactionCodeDescription(7));
		System.out.println("Code (80): " + transactionCodes.getTransactionCodeDescription(80));
		System.out.println("Code (406): " + transactionCodes.getTransactionCodeDescription(406));
	}

	/**
	 * Gets the description from the TreeMap
	 * Returns null if not in the list
	 * @param transactionCode
	 * @return String
	 */
	public String getTransactionCodeDescription(int transactionCode) {
		return this.getTransactionCodes().get(transactionCode);
	}
	
	/**
	 * Fills the TreeMap with all the entries
	 * 0XX DOMESTIC PAYMENT TRAFFIC
	 * 1XX DOMESTIC PAYMENT TRAFFIC
	 * 2XX INTERNATIONAL PAYMENT TRAFFIC
	 * 3XX SECURITIES TRAFFIC
	 * 4XX FOREIGN - EXCHANGE TRAFFIC
	 * 5XX MAOBE
	 * 6XX CREDIT TRANSACTION
	 * 7XX RESERVED
	 * 8XX MISCELLANEOUS
	 * 9XX UNSTRUCTURED CONTENTS
	 */
	private void fillTransactionCodesMap() {
		this.transactionCodes.put(1, "Personal cheque (except eurocheque)");
		this.transactionCodes.put(2, "Draft or money order");
		this.transactionCodes.put(3, "DM traveller's cheque");
		this.transactionCodes.put(4, "Direct debit (preauthorised payment procedure)");
		this.transactionCodes.put(5, "Direct debit (automatic debit transfer)");
		this.transactionCodes.put(6, "Other debits");
		this.transactionCodes.put(8, "Standing order");
		this.transactionCodes.put(9, "Return (debit) for debit instruction, reversing transfer of data carrier file interchange, debit (return) - DTA -");
		this.transactionCodes.put(10, "Return invoice (debit) for - direct return; - BSE cheque not redeemed");
		this.transactionCodes.put(11, "Eurocheque");
		this.transactionCodes.put(12, "Invoice payment instruction");
		this.transactionCodes.put(14, "Debit for foreign-currency eurocheque / debit for foreign cheques cleared through GZS");
		this.transactionCodes.put(15, "International funds transfer without text message");
		this.transactionCodes.put(17, "Remittance instruction: blank remittance/payment form with checksum-protected processing instructions");
		this.transactionCodes.put(18, "Remittance instruction: blank remittance/payment form");
		this.transactionCodes.put(19, "Remittance instruction: blank charitable contribution remittance/payment form");
		this.transactionCodes.put(20, "Remittance instruction");
		this.transactionCodes.put(51, "Remittance credit");
		this.transactionCodes.put(52, "Standing order credit");
		this.transactionCodes.put(53, "Wages, salaries, pension credit");
		this.transactionCodes.put(54, "Employment benefit capital savings scheme credit");
		this.transactionCodes.put(56, "Public treasury remittance");
		this.transactionCodes.put(58, "Interbank payment (remittance credit)");
		this.transactionCodes.put(59, "Return (credit) of undeliverable remittance, credit (reversing transfer) - DTA -");
		this.transactionCodes.put(65, "Remittance credit (international funds transfer without text message)");
		this.transactionCodes.put(66, "Cheque redemption credit E.v. (export cheque processing via GZS)");
		this.transactionCodes.put(67, "Credit: blank remittance/payment form with checksum-protected processing instructions");
		this.transactionCodes.put(68, "Credit: blank remittance/payment form EZÜ");
		this.transactionCodes.put(69, "Credit: blank charitable contribution remittance/payment form EZÜ");
		this.transactionCodes.put(70, "Cheque redemption");
		this.transactionCodes.put(71, "Debit redemption");
		this.transactionCodes.put(72, "Presentation of bill of exchange");
		this.transactionCodes.put(73, "Bill of exchange");
		this.transactionCodes.put(74, "TC (cheque debit)");
		this.transactionCodes.put(75, "BSE cheque");
		this.transactionCodes.put(76, "Telephone instruction");
		this.transactionCodes.put(77, "BTX remittance");
		this.transactionCodes.put(78, "Remittance (maintenance payment credit)");
		this.transactionCodes.put(79, "Collective");
		this.transactionCodes.put(80, "Salary");
		this.transactionCodes.put(81, "Compensation");
		this.transactionCodes.put(82, "Payment on account");
		this.transactionCodes.put(83, "Withdrawal");
		this.transactionCodes.put(84, "BTX direct debit instruction");
		this.transactionCodes.put(87, "Remittance instruction with fixed foreign exchange");
		this.transactionCodes.put(88, "Remittance credit with fixed foreign exchange");
		this.transactionCodes.put(89, "Wired remittance instruction with fixed foreign exchange");
		this.transactionCodes.put(90, "Wired remittance credit with fixed foreign exchange");
		this.transactionCodes.put(91, "DATA submission: remittances");
		this.transactionCodes.put(92, "DATA submission: direct debits");
		this.transactionCodes.put(93, "Discount bill");
		this.transactionCodes.put(94, "Rediscount bill");
		this.transactionCodes.put(95, "Bank guarantee credit (domestic)");
		this.transactionCodes.put(96, "Carryover (debit)");
		this.transactionCodes.put(97, "Carryover (credit)");
		this.transactionCodes.put(98, "Cash card (electronic wallet transactions)");
		this.transactionCodes.put(99, "Cash card (dealer commission for payment guarantee)");
		this.transactionCodes.put(201, "Payment instruction");
		this.transactionCodes.put(202, "Foreign compensation");
		this.transactionCodes.put(203, "Collection");
		this.transactionCodes.put(204, "Letter of credit");
		this.transactionCodes.put(205, "Bank guaranty credit");
		this.transactionCodes.put(206, "International remittance");
		this.transactionCodes.put(207, "not assigned");
		this.transactionCodes.put(208, "Documentary acceptance credit");
		this.transactionCodes.put(209, "Cheque payment");
		this.transactionCodes.put(210, "Electronic payment");
		this.transactionCodes.put(211, "Electronic payment receipt");
		this.transactionCodes.put(212, "Standing order");
		this.transactionCodes.put(213, "International direct debit");
		this.transactionCodes.put(214, "Documentary collection (Import)");
		this.transactionCodes.put(215, "Documentary collection (Export)");
		this.transactionCodes.put(216, "Bill of exchange collection (Import)");
		this.transactionCodes.put(217, "Bill of exchange collection (Export)");
		this.transactionCodes.put(218, "Import letter of credit");
		this.transactionCodes.put(219, "Export letter of credit");
		this.transactionCodes.put(220, "Redemption of international cheque e.V.");
		this.transactionCodes.put(221, "Credit for international cheque collection");
		this.transactionCodes.put(222, "Debit for international cheque");
		this.transactionCodes.put(223, "Debit for international ec cheque");
		this.transactionCodes.put(224, "Foreign exchange buy");
		this.transactionCodes.put(225, "Foreign exchange sell");
		this.transactionCodes.put(301, "Collection");
		this.transactionCodes.put(302, "Coupons / dividends");
		this.transactionCodes.put(303, "Stocks and bonds");
		this.transactionCodes.put(304, "Carryover");
		this.transactionCodes.put(305, "Registered bond");
		this.transactionCodes.put(306, "Note");
		this.transactionCodes.put(307, "Securities subscription");
		this.transactionCodes.put(308, "Stock rights trade");
		this.transactionCodes.put(309, "Premium rights trade");
		this.transactionCodes.put(310, "Options trade");
		this.transactionCodes.put(311, "Futures trade");
		this.transactionCodes.put(320, "Securities trading fees");
		this.transactionCodes.put(321, "Custodian fees");
		this.transactionCodes.put(330, "Securities income");
		this.transactionCodes.put(340, "Credit for securities at maturity");
		this.transactionCodes.put(399, "Reversal");
		this.transactionCodes.put(401, "Direct exchange");
		this.transactionCodes.put(402, "Forward exchange");
		this.transactionCodes.put(403, "Travel exchange");
		this.transactionCodes.put(404, "Foreign currency cheque");
		this.transactionCodes.put(405, "Financial innovation");
		this.transactionCodes.put(406, "");
		this.transactionCodes.put(407, "");
		this.transactionCodes.put(408, "");
		this.transactionCodes.put(409, "");
		this.transactionCodes.put(410, "");
		this.transactionCodes.put(411, "Spot exchange buy");
		this.transactionCodes.put(412, "Spot exchange sell");
		this.transactionCodes.put(413, "Forward exchange buy");
		this.transactionCodes.put(414, "Forward exchange sell");
		this.transactionCodes.put(415, "FW - Day money – active");
		this.transactionCodes.put(416, "FW - Day money – passive");
		this.transactionCodes.put(417, "FW - Time deposit – active");
		this.transactionCodes.put(418, "FW - Time deposit – passive");
		this.transactionCodes.put(419, "Call money: active");
		this.transactionCodes.put(420, "Call money: passive");
		this.transactionCodes.put(421, "Options");
		this.transactionCodes.put(422, "Swap");
		this.transactionCodes.put(423, "Precious metals: buy");
		this.transactionCodes.put(424, "Precious metals: sell");
		this.transactionCodes.put(601, "Debit of instalments/annuities");
		this.transactionCodes.put(602, "Transfer of instalments/annuities");
		this.transactionCodes.put(603, "Redemption");
		this.transactionCodes.put(604, "Interest on loans");
		this.transactionCodes.put(605, "Interest on loans with additional services");
		this.transactionCodes.put(801, "Cheque card");
		this.transactionCodes.put(802, "Chequebook");
		this.transactionCodes.put(803, "Custodianship");
		this.transactionCodes.put(804, "Standing order charge");
		this.transactionCodes.put(805, "Closing");
		this.transactionCodes.put(806, "Postage and handling");
		this.transactionCodes.put(807, "Fees and expenses");
		this.transactionCodes.put(808, "Fees");
		this.transactionCodes.put(809, "Commission");
		this.transactionCodes.put(810, "Dunning charges");
		this.transactionCodes.put(811, "Credit cost");
		this.transactionCodes.put(812, "Interest on delinquent taxes");
		this.transactionCodes.put(813, "Discount");
		this.transactionCodes.put(814, "Interest");
		this.transactionCodes.put(815, "Capitalised interest");
		this.transactionCodes.put(816, "Interest rate change");
		this.transactionCodes.put(817, "Interest correction");
		this.transactionCodes.put(818, "Debit");
		this.transactionCodes.put(819, "Remuneration");
		this.transactionCodes.put(820, "Carryover");
		this.transactionCodes.put(821, "Telephone");
		this.transactionCodes.put(822, "Payment plan");
		this.transactionCodes.put(823, "Fixed-term deposits");
		this.transactionCodes.put(824, "Loan monies");
		this.transactionCodes.put(825, "General loan");
		this.transactionCodes.put(826, "Time-adjusted savings");
		this.transactionCodes.put(827, "Surplus savings");
		this.transactionCodes.put(828, "Savings certificate");
		this.transactionCodes.put(829, "Savings plan");
		this.transactionCodes.put(830, "Bonus");
		this.transactionCodes.put(831, "Old invoice");
		this.transactionCodes.put(832, "Mortgage");
		this.transactionCodes.put(833, "Cash concentrating: main account booking");
		this.transactionCodes.put(834, "Cash concentrating: advice for subsidiary account");
		this.transactionCodes.put(835, "Other transactions");
		this.transactionCodes.put(836, "Claim booking");
		this.transactionCodes.put(888, "Book transfer due to euro conversion");
		this.transactionCodes.put(899, "Reversal");
		this.transactionCodes.put(997, "List of portfolio securities");
		this.transactionCodes.put(999, "Unstructured assignment of multi-purpose field '86'");
	}

	public Map<Integer, String> getTransactionCodes() {
		return transactionCodes;
	}

	public void setTransactionCodes(Map<Integer, String> transactionCodes) {
		this.transactionCodes = transactionCodes;
	}
}
