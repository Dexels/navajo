package com.dexels.navajo.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.adapter.mt940.*;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Erik Versteeg
 * @version 1.0
 */
public class MT940Map implements Serializable {
	private static final long serialVersionUID = 922784734690300149L;
    private static final Logger logger = LoggerFactory.getLogger(MT940Map.class);
    private MT940Layout mt940Layout;
	private TransactionCodes transactionCodes;
	private BookingKeys bookingKeys;
	private CurrencyCodes currencyCodes;
	private Binary fileContent;
	private List<Entry> entryList = new ArrayList<Entry>();
	private boolean printOutputToConsole = false; // this boolean is only used for testpurposes. Set to true to print to console too
	private Calendar cal = Calendar.getInstance(Locale.getDefault());

    public MT940Map() {
    	 this.setTransactionCodes(new TransactionCodes());
    	 this.setBookingKeys(new BookingKeys());
    	 this.setCurrencyCodes(new CurrencyCodes());
    }
    public MT940Map(Binary fileContent) {
    	this();
    	this.setFileContent(fileContent);
    	// Set the layout values after trying to get the bank from the filecontent
    	this.setMt940Layout();
    }

	public static void main(String[] args) throws Exception {
    	// Read the file
//		String fileName = "C:\\Temp\\ABN_MT940.sta";
		String fileName = "C:\\Temp\\MT940_EXAMPLE.sta";
//		String fileName = "C:\\Temp\\MT940_EXAMPLE2.sta";
    	MT940Map mt940 = new MT940Map(new Binary(new File(fileName)));
    	mt940.setPrintOutputToConsole(true);
    	System.out.println("Selected bank: " + mt940.getMt940Layout().getSelectedBank());
    	mt940.importFile();
    	mt940.printEntries();
    }
	
	/**
	 * Set the MT940 layout values.
	 * Overrides the default setter
	 */
	private void setMt940Layout() {
    	try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getFileContent().getDataAsStream()));
			this.setMt940Layout(new MT940Layout(MT940Utils.getBankNameFromFile(reader)));
	    	reader.close();
		} catch (Exception e) {
        	String message = "Error reading file when getting bankname: " + e.getMessage();
        	logger.error(message);
	    	if (this.isPrintOutputToConsole()) {
	    		System.out.println(message);
	    	}
		}
	}
	
	/**
	 * Print all the entries
	 * @throws Exception
	 */
	public void printEntries() throws Exception {
		if (this.getEntryList() != null && this.getEntryList().size() != 0) {
			try {
		    	String message = "\nAmount of entries in the list: " + this.getEntryList().size();
				if (this.isPrintOutputToConsole()) {
		    		System.out.println(message);
		    	}
	    		logger.info(message);
	    		
	    		String transactionMessage = "";
		    	for (int i = 0; i < this.getEntryList().size(); i++){
		    		Entry entry = (Entry)this.getEntryList().get(i);
		    		transactionMessage = "Entry output (" + (i + 1) + ")\n" + entry.toString();
		    		logger.info(transactionMessage);
			    	if (this.isPrintOutputToConsole()) {
			    		System.out.println(transactionMessage);
			    	}
		    	}
			} catch (Exception e) {
				throw new Exception (e);
			}
		}
	}
    
	/**
	 * Starts the import of the file
	 * @throws Exception
	 */
	public void importFile() throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getFileContent().getDataAsStream()));
            
            if (this.getMt940Layout() == null) {
            	String message = "Error importing file: no MT940 layout found";
            	logger.error(message);
    	    	if (this.isPrintOutputToConsole()) {
    	    		System.out.println(message);
    	    	}
            } else {
	            boolean finished = false;
	            while (!finished) {
	                finished = importEntries(reader);
	            }
            }
        	reader.close();
        } catch (Exception e) {
        	String message = "Error importing file: " + e.getMessage();
        	logger.error(message);
	    	if (this.isPrintOutputToConsole()) {
	    		System.out.println(message);
	    	}
        }
    }
    
	/**
	 * This method reads all the lines and then determines which method it should call to break it down into readable pieces 
	 * @param reader
	 * @return boolean
	 * @throws IOException
	 */
	private boolean importEntries(BufferedReader reader) {
		boolean finished = false;
		String line = "";
		
		try {
			if (reader != null) {
				String previousTag = ""; // this is needed to check where the 86 tag should go. Header or Transaction
				Entry entry = new Entry();
				Transaction transaction = new Transaction();
				boolean readNextLine = true; // used to make an inner loop to get all the tag 86 subfields
				while (!finished) {
					// Get the next line
					if (readNextLine) {
						line = reader.readLine();
					} else {
						// only skipping it ones
						readNextLine = true;
					}
					
					// Check what the line starts with and take the appropriate action
		            if (line == null) {
		                finished = true;
		            } else {
			            if (line.startsWith("-")) {
			            	// Start a new entry
			            	this.addEntryToList(entry);
			            	entry = new Entry();
			            } else {
							// Here we check the details
				        	if (line.startsWith(":")) {
					            int colon = line.indexOf(":", 1);
					            if (colon >= 0) {
	
						            String field = line.substring(1, colon);
						            String value = line.substring(colon + 1);
						            
						            // Header info
						            if (field.equals("20")) {
						            	entry.getHeader().setGenerationDateTime(value);
						            } else if (field.equals("25")) {
						            	entry.getHeader().setIBAN(value);
						            } else if (field.equals("28C")) {
						            	entry.getHeader().setStatementNumber(Integer.parseInt(value));
						            } else if (field.equals("NS")) {
						            	// This one is a bit weird, because it has a followup number which needs to be checked.
						            	// 19 = part of the transaction
						            	// 22 = header account owner name
						            	// 23 = header account name
						            	if (value.length() >= 2) {
						            		String tempValue = value.substring(2, value.length());
							            	if (value.startsWith("19")) {
							            		// add item to the active transaction
							            		transaction.setBookingTime(tempValue);
							            	} else if (value.startsWith("22")) {
							            		entry.getHeader().setAccountOwnerName(tempValue);
							            	} else if (value.startsWith("23")) {
							            		entry.getHeader().setAccountName(tempValue);
							            	}
						            	}
						            } else if (field.equals("60F")) {
						            	// this tag is split into several seperate fields divided by position only. SUBSTRING it is
						            	entry.getHeader().getOpeningBalance().setDebitCreditIndicator(value.substring(0, 1));
						            	entry.getHeader().getOpeningBalance().setDate(MT940Utils.getDateFromTagString(cal, value.substring(1, 7)));
						            	entry.getHeader().getOpeningBalance().setCurrency(value.substring(7, 10));
						            	// If needed, get the country with the currencycode like this
//						            	System.out.println("Currency code + country: " + entry.getHeader().getOpeningBalance().getCurrency() + " + " + this.getCurrencyCodes().getCurrencyCountry(entry.getHeader().getOpeningBalance().getCurrency()));
						            	BigDecimal amount = MT940Utils.stringToBigDecimal(value.substring(10, value.length()), Locale.getDefault());
						            	entry.getHeader().getOpeningBalance().setAmount(amount);
						            
						            // Transaction info
						            } else if (field.equals("61")) {
						            	// this tag is split into several seperate fields divided by position only. SUBSTRING it is
						            	transaction.setValueDate(MT940Utils.getDateFromTagString(cal, value.substring(0, 6)));
						            	transaction.setBookingDate(value.substring(6, 10));
						            	transaction.setDebitCreditIndicator(value.substring(10, 11));
						            	transaction.setThirdCharacterCurrencyCode(value.substring(11, 12));
						            	int i = value.indexOf("NTRF");
						            	BigDecimal amount = MT940Utils.stringToBigDecimal(value.substring(12, i), Locale.getDefault());
						            	transaction.setAmount(amount);
						            	int j = value.indexOf("//");
						            	transaction.setValueField(value.substring(i, (j + 2)));
						            	transaction.setBookingKeyDescription(value.substring((j + 2), value.length()));
	
						            // This could be part of the transaction or the footer
						            } else if (field.equals("86")) {
						            	if (previousTag.equals("64")) {
						            		// Footer item
						            		entry.getFooter().setAdditionalInformation(value);
						            	} else {
						            		// Transaction item (previousTag = 61)
						            		// Need an extra loop in order to get all the items in the transaction
						            		boolean stopReading = false;
						            		while (!stopReading) {
						            			transaction.setTransactionCode(line.startsWith(":86:") ? Integer.parseInt(MT940Utils.getTransactionSubfield(line, ":86:")) : transaction.getTransactionCode());
						            			transaction.setTransactionCodeDescription(this.getTransactionCodes().getTransactionCodeDescription(transaction.getTransactionCode()));
							            		// looking for the subfields
							            		transaction.setTransactionType(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "00") ? MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "00")) : transaction.getTransactionType());
							            		transaction.setSequenceNumber(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "10") ? MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "10")) : transaction.getSequenceNumber());
							            		transaction.setTransactionTitle(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "20") ? MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "20")) : transaction.getTransactionTitle());
							            		transaction.setTransactionTitle(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "21") ? (transaction.getTransactionTitle() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "21"))) : transaction.getTransactionTitle());
							            		transaction.setTransactionTitle(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "22") ? (transaction.getTransactionTitle() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "22"))) : transaction.getTransactionTitle());
							            		transaction.setTransactionTitle(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "23") ? (transaction.getTransactionTitle() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "23"))) : transaction.getTransactionTitle());
							            		transaction.setTransactionTitle(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "24") ? (transaction.getTransactionTitle() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "24"))) : transaction.getTransactionTitle());
							            		transaction.setTransactionTitle(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "25") ? (transaction.getTransactionTitle() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "25"))) : transaction.getTransactionTitle());
							            		transaction.setTransactionTitle(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "26") ? (transaction.getTransactionTitle() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "26"))) : transaction.getTransactionTitle());
							            		transaction.setCounterPartyNameAndAddress(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "27") ? MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "27")) : transaction.getCounterPartyNameAndAddress());
							            		transaction.setCounterPartyNameAndAddress(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "28") ? (transaction.getCounterPartyNameAndAddress() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "28"))) : transaction.getCounterPartyNameAndAddress());
							            		transaction.setCounterPartyNameAndAddress(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "29") ? (transaction.getCounterPartyNameAndAddress() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "29"))) : transaction.getCounterPartyNameAndAddress());
							            		transaction.setTechnicalField(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "30") ? MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "30")) : transaction.getTechnicalField());
							            		transaction.setTechnicalField(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "31") ? (transaction.getTechnicalField() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "31"))) : transaction.getTechnicalField());
							            		transaction.setTechnicalField(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "32") ? (transaction.getTechnicalField() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "32"))) : transaction.getTechnicalField());
							            		transaction.setTechnicalField(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "33") ? (transaction.getTechnicalField() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "33"))) : transaction.getTechnicalField());
							            		transaction.setCounterPartyAccount(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "38") ? MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "38")) : transaction.getCounterPartyAccount());
							            		transaction.setCounterPartyNameAndAddress(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "60") ? (transaction.getCounterPartyNameAndAddress() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "60"))) : transaction.getCounterPartyNameAndAddress());
							            		transaction.setReconciliationCode(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "61") ? MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "61")) : transaction.getReconciliationCode());
							            		transaction.setReconciliationCode(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "62") ? (transaction.getReconciliationCode() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "62"))) : transaction.getReconciliationCode());
							            		transaction.setReferenceNumber(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "63") ? MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "63")) : transaction.getReferenceNumber());
							            		transaction.setReconciliationCode(line.contains(this.getMt940Layout().getTag86_subfieldStartingChar() + "64") ? (transaction.getReconciliationCode() + MT940Utils.getTransactionSubfield(line, (this.getMt940Layout().getTag86_subfieldStartingChar() + "64"))) : transaction.getReconciliationCode());
							            		// next line then
							            		line = reader.readLine();
							            		if (line.startsWith(":")) {
							            			stopReading = true;
							            		}
						            		}
						            		readNextLine = false; // so the next line is not read, because we have it already
	
						            		// Save and add new transaction
						            		entry.getTransactions().add(transaction);
							            	transaction = new Transaction();
						            	}
	
						            // Footer - Closing balance
						            } else if (field.equals("62F")) {
						            	// this tag is split into several seperate fields divided by position only. SUBSTRING it is
						            	entry.getFooter().getClosingBalance().setDebitCreditIndicator(value.substring(0, 1));
						            	entry.getFooter().getClosingBalance().setDate(MT940Utils.getDateFromTagString(cal, value.substring(1, 7)));
						            	entry.getFooter().getClosingBalance().setCurrency(value.substring(7, 10));
						            	BigDecimal amount = MT940Utils.stringToBigDecimal(value.substring(10, value.length()), Locale.getDefault());
						            	entry.getFooter().getClosingBalance().setAmount(amount);
						            	
						            // Footer - Available balance
						            } else if (field.equals("64")) {
						            	// this tag is split into several seperate fields divided by position only. SUBSTRING it is
						            	entry.getFooter().getAvailableBalance().setDebitCreditIndicator(value.substring(0, 1));
						            	entry.getFooter().getAvailableBalance().setDate(MT940Utils.getDateFromTagString(cal, value.substring(1, 7)));
						            	entry.getFooter().getAvailableBalance().setCurrency(value.substring(7, 10));
						            	BigDecimal amount = MT940Utils.stringToBigDecimal(value.substring(10, value.length()), Locale.getDefault());
						            	entry.getFooter().getAvailableBalance().setAmount(amount);
						            	
						            }	            
					            
						            // Hold the previoustag
						        	previousTag = line.substring(1, colon);
					            }
				        	}
			            }
		            }
				}
			}
		} catch (Exception e) {
			String message = "Error when processing line: " + line + " -> " + "Errormessage: " + e.getMessage();
			logger.error(message);
	    	if (this.isPrintOutputToConsole()) {
				System.out.println(message);
	    	}
		}
		return finished;
	}

    /**
     * Adds an entry to the ArrayList
     * @param entry
     */
    private void addEntryToList(Entry entry) {
    	this.getEntryList().add(entry);
    }
	
	public Binary getFileContent() {
		return fileContent;
	}
	public void setFileContent(Binary fileContent) {
		this.fileContent = fileContent;
	}
	
	/**
	 * Returns the entries as array
	 */
	public Object[] getEntries() {
		if (this.getEntryList() != null && this.getEntryList().size() != 0) {
			return this.getEntryList().toArray();
		}
		return null;
	}
	public List<Entry> getEntryList() {
		return this.entryList;
	}
	public void setEntryList(List<Entry> entryList) {
		this.entryList = entryList;
	}
	
	public MT940Layout getMt940Layout() {
		return mt940Layout;
	}
	public void setMt940Layout(MT940Layout mt940Layout) {
		this.mt940Layout = mt940Layout;
	}
	
	public TransactionCodes getTransactionCodes() {
		return transactionCodes;
	}
	public void setTransactionCodes(TransactionCodes transactionCodes) {
		this.transactionCodes = transactionCodes;
	}
	
	public BookingKeys getBookingKeys() {
		return bookingKeys;
	}
	public void setBookingKeys(BookingKeys bookingKeys) {
		this.bookingKeys = bookingKeys;
	}
	
	public CurrencyCodes getCurrencyCodes() {
		return currencyCodes;
	}
	public void setCurrencyCodes(CurrencyCodes currencyCodes) {
		this.currencyCodes = currencyCodes;
	}
	public boolean isPrintOutputToConsole() {
		return printOutputToConsole;
	}
	public void setPrintOutputToConsole(boolean printOutputToConsole) {
		this.printOutputToConsole = printOutputToConsole;
	}
}
