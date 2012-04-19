package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to hold the entries
 * @author Erik Versteeg
 */
public class Entry implements Serializable {
	private static final long serialVersionUID = 5225157750310265204L;
	private Date processingDate;
	private Header header;
	private List<Transaction> transactions;
	private Footer footer;

	public Entry() {
		this.processingDate = new Date();
		this.setHeader(new Header());
		this.setTransactions(new ArrayList<Transaction>());
		this.setFooter(new Footer());
	}

	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("\t**** Processing date = " + this.processingDate + " ****" + '\n');
		output.append(this.getHeader().toString() + '\n');
		output.append("\t*** Amount of transactions in this entry : " + this.getTransactions().size() + " ***" + '\n');
		int i = 1;
		for (Transaction transaction : this.getTransactions()) {
			output.append("\t*** Transaction (" + i + ") ***\n" + transaction.toString() + '\n');
			i++;
		}
		output.append(this.getFooter().toString() + '\n');
		return output.toString();
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Footer getFooter() {
		return footer;
	}

	public void setFooter(Footer footer) {
		this.footer = footer;
	}
}
