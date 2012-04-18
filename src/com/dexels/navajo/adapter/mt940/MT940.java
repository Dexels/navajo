package com.dexels.navajo.adapter.mt940;

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


/**
 * Reads MT940 file and returns the entries in a map
 * @author Erik Versteeg
 */
public class MT940 implements Serializable {
	private static final long serialVersionUID = 922784734690300149L;
    private static final Logger logger = LoggerFactory.getLogger(MT940.class);
    Binary fileContent;
	List<Entry> entrieList = new ArrayList<Entry>();
    
    public MT940() {}
    public MT940(Binary fileContent){
    	this.setFileContent(fileContent);
    }

	public static void main(String[] args) throws Exception {
    	// Read the file
//		String fileName = "C:\\Temp\\ABN_MT940.sta";
		String fileName = "C:\\Temp\\MT940_EXAMPLE.sta";
    	MT940 mt940 = new MT940();
    	mt940.setFileContent(new Binary(new File(fileName)));
    	mt940.importFile();
    	mt940.printEntries();
//    	System.out.println("Amount of entries: " + mt940.getEntrieList().size());
    	System.out.println("Amount of entries array: " + mt940.getEntries().length);
    }
	
	public void printEntries() throws Exception {
		if (this.getEntrieList() != null && this.getEntrieList().size() != 0) {
			try {
		    	System.out.println("\nAmount read: " + this.getEntrieList().size());
		    	for (int i = 0; i < this.getEntrieList().size(); i++){
		    		Entry entry = (Entry)this.getEntrieList().get(i);
		    		logger.info("Entry output (" + i + ")\n" + entry.toString());
		    		System.out.println("Entry output (" + i + ")\n" + entry.toString());
		    	}
			} catch (Exception e) {
				throw new Exception (e);
			}
		}
	}
    
	public void importFile() throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getFileContent().getDataAsStream()));
            
            boolean finished = false;
            while (!finished) {
                finished = importEntries(reader);
            }
        } catch (IOException e) {
            logger.error("Error reading file: " + e.getMessage());
        } catch (Exception e) {
        	logger.error("Error importing file: " + e.getMessage());
        }
    }
    
	/**
	 * This method reads all the lines and then determines which method is should call to break it down into readable pieces 
	 * @param reader
	 * @return boolean
	 * @throws IOException
	 */
	private boolean importEntries(BufferedReader reader) throws IOException {
		boolean finished = false;
		
		if (reader != null) {
			String line = "";
			String previousTag = ""; // this is needed to check where the 86 tag should go
        	Calendar cal = Calendar.getInstance(Locale.getDefault());
			Entry entry = new Entry();
			Transaction transaction = new Transaction();
			boolean readNextLine = true; // used to make an inner loop to get all the tag 86 values
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
					            	cal.set(Integer.parseInt(MT940Constants.getYear(value.substring(1, 3))), Integer.parseInt(value.substring(3, 5)), Integer.parseInt(value.substring(5, 7)));
					            	entry.getHeader().getOpeningBalance().setDate(cal.getTime());
					            	entry.getHeader().getOpeningBalance().setCurrency(value.substring(7, 10));
					            	BigDecimal amount = MT940Constants.stringToBigDecimal(value.substring(10, value.length()), Locale.getDefault());
					            	entry.getHeader().getOpeningBalance().setAmount(amount);
					            
					            // Transaction info
					            } else if (field.equals("61")) {
					            	// This is a special one, because it starts a new transaction within the entry
					            	// Also pay attention to the fact that this tag needs to be split into seperate parts as well.
					            	cal.set(Integer.parseInt(MT940Constants.getYear(value.substring(0, 2))), Integer.parseInt(value.substring(2, 4)), Integer.parseInt(value.substring(4, 6)));
					            	transaction.setValueDate(cal.getTime());
					            	transaction.setBookingDate(value.substring(6, 10));
					            	transaction.setDebitCreditIndicator(value.substring(10, 11));
					            	transaction.setThirdCharacterCurrencyCode(value.substring(11, 12));
					            	int i = value.indexOf("NTRF");
					            	BigDecimal amount = MT940Constants.stringToBigDecimal(value.substring(12, i), Locale.getDefault());
					            	transaction.setAmount(amount);
					            	int j = value.indexOf("//");
					            	transaction.setValueField(value.substring(i, (j + 2)));
					            	transaction.setTransactionCodeDescription(value.substring((j + 2), value.length()));

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
					            			transaction.setTransactionCode(line.startsWith(":86:") ? Integer.parseInt(getTransactionSubfield(line, ":86:")) : transaction.getTransactionCode());
						            		// looking for the subfields
						            		transaction.setTransactionType(line.contains("<00") ? getTransactionSubfield(line, "<00") : transaction.getTransactionType());
						            		transaction.setSequenceNumber(line.contains("<10") ? getTransactionSubfield(line, "<10") : transaction.getSequenceNumber());
						            		transaction.setTransactionTitle(line.contains("<20") ? getTransactionSubfield(line, "<20") : transaction.getTransactionTitle());
						            		transaction.setTransactionTitle(line.contains("<21") ? (transaction.getTransactionTitle() + getTransactionSubfield(line, "<21")) : transaction.getTransactionTitle());
						            		transaction.setTransactionTitle(line.contains("<22") ? (transaction.getTransactionTitle() + getTransactionSubfield(line, "<22")) : transaction.getTransactionTitle());
						            		transaction.setTransactionTitle(line.contains("<23") ? (transaction.getTransactionTitle() + getTransactionSubfield(line, "<23")) : transaction.getTransactionTitle());
						            		transaction.setTransactionTitle(line.contains("<24") ? (transaction.getTransactionTitle() + getTransactionSubfield(line, "<24")) : transaction.getTransactionTitle());
						            		transaction.setTransactionTitle(line.contains("<25") ? (transaction.getTransactionTitle() + getTransactionSubfield(line, "<25")) : transaction.getTransactionTitle());
						            		transaction.setTransactionTitle(line.contains("<26") ? (transaction.getTransactionTitle() + getTransactionSubfield(line, "<26")) : transaction.getTransactionTitle());
						            		transaction.setCounterPartyNameAndAddress(line.contains("<27") ? getTransactionSubfield(line, "<27") : transaction.getCounterPartyNameAndAddress());
						            		transaction.setCounterPartyNameAndAddress(line.contains("<28") ? (transaction.getCounterPartyNameAndAddress() + getTransactionSubfield(line, "<28")) : transaction.getCounterPartyNameAndAddress());
						            		transaction.setCounterPartyNameAndAddress(line.contains("<29") ? (transaction.getCounterPartyNameAndAddress() + getTransactionSubfield(line, "<29")) : transaction.getCounterPartyNameAndAddress());
						            		transaction.setTechnicalField(line.contains("<30") ? getTransactionSubfield(line, "<30") : transaction.getTechnicalField());
						            		transaction.setTechnicalField(line.contains("<31") ? (transaction.getTechnicalField() + getTransactionSubfield(line, "<31")) : transaction.getTechnicalField());
						            		transaction.setTechnicalField(line.contains("<32") ? (transaction.getTechnicalField() + getTransactionSubfield(line, "<32")) : transaction.getTechnicalField());
						            		transaction.setTechnicalField(line.contains("<33") ? (transaction.getTechnicalField() + getTransactionSubfield(line, "<33")) : transaction.getTechnicalField());
						            		transaction.setCounterPartyAccount(line.contains("<38") ? getTransactionSubfield(line, "<38") : transaction.getCounterPartyAccount());
						            		transaction.setCounterPartyNameAndAddress(line.contains("<60") ? (transaction.getCounterPartyNameAndAddress() + getTransactionSubfield(line, "<60")) : transaction.getCounterPartyNameAndAddress());
						            		transaction.setReconciliationCode(line.contains("<61") ? getTransactionSubfield(line, "<61") : transaction.getReconciliationCode());
						            		transaction.setReconciliationCode(line.contains("<62") ? (transaction.getReconciliationCode() + getTransactionSubfield(line, "<62")) : transaction.getReconciliationCode());
						            		transaction.setReferenceNumber(line.contains("<63") ? getTransactionSubfield(line, "<63") : transaction.getReferenceNumber());
						            		transaction.setReconciliationCode(line.contains("<64") ? (transaction.getReconciliationCode() + getTransactionSubfield(line, "<64")) : transaction.getReconciliationCode());
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
					            	cal.set(Integer.parseInt(MT940Constants.getYear(value.substring(1, 3))), Integer.parseInt(value.substring(3, 5)), Integer.parseInt(value.substring(5, 7)));
					            	entry.getFooter().getClosingBalance().setDate(cal.getTime());
					            	entry.getFooter().getClosingBalance().setCurrency(value.substring(7, 10));
					            	BigDecimal amount = MT940Constants.stringToBigDecimal(value.substring(10, value.length()), Locale.getDefault());
					            	entry.getFooter().getClosingBalance().setAmount(amount);
					            	
					            // Footer - Available balance
					            } else if (field.equals("64")) {
					            	// this tag is split into several seperate fields divided by position only. SUBSTRING it is
					            	entry.getFooter().getAvailableBalance().setDebitCreditIndicator(value.substring(0, 1));
					            	cal.set(Integer.parseInt(MT940Constants.getYear(value.substring(1, 3))), Integer.parseInt(value.substring(3, 5)), Integer.parseInt(value.substring(5, 7)));
					            	entry.getFooter().getAvailableBalance().setDate(cal.getTime());
					            	entry.getFooter().getAvailableBalance().setCurrency(value.substring(7, 10));
					            	BigDecimal amount = MT940Constants.stringToBigDecimal(value.substring(10, value.length()), Locale.getDefault());
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
		
		return finished;
	}
	
	/**
	 * Methode to filter a subfield from a string
	 * @param input
	 * @param subfield
	 * @return String
	 */
	private String getTransactionSubfield(String input, String subfield) {
		String result = "";
		
		// assuming that the part ends at the next < entry
		if (input != null && subfield != null) {
    		int i = input.indexOf(subfield);
    		int j = input.indexOf("<", (i + 1));
    		if (j == -1) { j = input.length(); }
    		if (i != -1) {
    			result = input.substring((i + subfield.length()), j);
    		}
		}
		return result;
	}

    /**
     * Adds an entry to the ArrayList
     * @param entry
     */
    private void addEntryToList(Entry entry) {
    	this.getEntrieList().add(entry);
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
		if (this.getEntrieList() != null && this.getEntrieList().size() != 0) {
			return this.getEntrieList().toArray();
		}
		return null;
	}
	public List<Entry> getEntrieList() {
		return this.entrieList;
	}
	public void setEntrieList(List<Entry> entrieList) {
		this.entrieList = entrieList;
	}
}
