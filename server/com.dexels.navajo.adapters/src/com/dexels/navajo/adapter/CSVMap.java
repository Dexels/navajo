package com.dexels.navajo.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.csvmap.CSVEntryMap;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * <p>
 * Title: Navajo Product Project
 * </p>
 * <p>
 * Description: This is the official source for the Navajo server
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author Arjen Schoneveld
 * @version 1.0
 * 
 * This mappable object can be used to read a comma-separated-file.
 * 
 * $Id$
 * 
 */

public class CSVMap implements Mappable {

	public CSVEntryMap[] entries;
	public String fileName;
	public Binary fileContent;
	public String separator;
	public int entryCount;
	public boolean includeEmpty;
	public boolean skipFirstRow;
	public int maximumImportCount;

	private boolean update = false;
	private List<CSVEntryMap> draftEntries = null;
	
	private final static Logger logger = LoggerFactory.getLogger(CSVMap.class);
	
	public void load(Access access) throws MappableException, UserException {

	}

	public void setEntry(CSVEntryMap newEntry) {
		if (draftEntries == null)
			draftEntries = new ArrayList<CSVEntryMap>();
		draftEntries.add(newEntry);
		update = true;
	}

	public void setEntries(CSVEntryMap[] newEntries) {
		update = true;
		entries = newEntries;
	}

	public int getEntryCount() {
		return entries.length;
	}
	
	public int getEntryFieldCount() {
	    int result = -1;
	    if (entries != null && entries.length > 0) {
	        CSVEntryMap e = entries[0];
	        result = e.getEntrySize();
	    }
	    return result;
	}

	public CSVEntryMap[] getEntries() throws UserException {
		try {
			Reader f = null;
			if (fileContent != null) {
				f = new InputStreamReader(fileContent.getDataAsStream());
			} else {
				f = new FileReader(fileName);
			}

			BufferedReader buffer = new BufferedReader(f);
			String line = "";
			int lineCount = 0;
			List<CSVEntryMap> entryList = new ArrayList<CSVEntryMap>();
			while ((line = buffer.readLine()) != null) {
			    if (maximumImportCount == 0 || (lineCount <= maximumImportCount)) {
					if (isSkipFirstRow() && lineCount == 0) {
					    // First line will be skipped. Probably contains headers
					} else {
    				    if (includeEmpty) {
    						parseLineWithEmpty(line, entryList);
    					} else {
    						parseLineDefault(line, entryList);
    					}
					}
			    }
				lineCount++;
			}
//				entries = new CSVEntryMap[entryList.size()];
//				entries = entryList.toArray(entries);
			entries = new CSVEntryMap[entryList.size()];
			int i = 0;
			for (CSVEntryMap ce : entryList) {
				entries[i++] = ce;
			}
		} catch (java.io.IOException ioe) {
			throw new UserException(-1, ioe.getMessage());
		}
		return entries;
	}

	private void parseLineWithEmpty(String line, List<CSVEntryMap> entryList) {
		String sep = separator;
		if (sep == null) {
			sep = " ";
		}
		if (sep.length() > 1) {
			throw new IllegalArgumentException("Can not include empty when separator is > 1 char.Sorry, feel free to implement.");
		}
		char sepChar = sep.charAt(0);
		int startindex = 0;
		List<String> currentLine = new ArrayList<String>();
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == sepChar) {
				if (startindex == -1) {
					currentLine.add(null);
					startindex = -1;
				} else {
					if (startindex==i) {
						currentLine.add(null);
					} else {
						String ss = line.substring(startindex, i);
						currentLine.add(ss);
					}
					startindex = i+1;
				}
			}
		}
		
		// add the last item on the line, not ended by the separator
		if ( startindex == line.length() ) {
			currentLine.add( null );
		}
		else {
			String ss = line.substring( startindex, line.length() );
			currentLine.add( ss );
		}
		CSVEntryMap csvEntry = new CSVEntryMap();
		csvEntry.entries = new String[currentLine.size()];
		for (int i = 0; i < currentLine.size(); i++) {
			csvEntry.entries[i] = currentLine.get(i);
		}
		entryList.add(csvEntry);
	}

	private void parseLineDefault(String line, List<CSVEntryMap> entryList) {
		StringTokenizer tokens = new StringTokenizer(line, separator);
		CSVEntryMap csvEntry = new CSVEntryMap();
		csvEntry.entries = new String[tokens.countTokens()];
		int index = 0;
		while (tokens.hasMoreTokens()) {
			csvEntry.entries[index++] = tokens.nextToken();
		}
		entryList.add(csvEntry);
	}

	public void setSeparator(String sep) {
		this.separator = sep;
	}

	public void setFileName(String s)  {
		this.fileName = s;
	}

	public void setFileContent(Binary b) {
		this.fileContent = b;
	}

	public void store() throws MappableException, UserException {
		if (draftEntries != null) {
			entries = new CSVEntryMap[draftEntries.size()];
			int i = 0;
			for (CSVEntryMap ce : draftEntries) {
				entries[i++] = ce;
			}
//			entries = (CSVEntryMap[]) draftEntries.toArray();
		}
		if (update) {
			try {
				// Write CSV.
				if (entries != null) {
					FileWriter writer = new FileWriter(fileName);
					for (int i = 0; i < entries.length; i++) {
						CSVEntryMap e = entries[i];
						for (int j = 0; j < e.entries.length; j++) {
							writer.write(e.getEntry(new Integer(j)));
							if (j < (e.entries.length - 1))
								writer.write(separator);
						}
						if (i < (entries.length - 1))
							writer.write("\n");
					}
					writer.close();
				}
			} catch (IOException ioe) {
				throw new UserException(-1, ioe.getMessage());
			}
		}
	}

	public void kill() {

	}

	public static void main(String[] args) throws Exception {
		Mappable csv = new CSVMap();
		((CSVMap) csv).setSeparator(";");
		((CSVMap) csv).setIncludeEmpty(true);
		((CSVMap) csv).setSkipFirstRow(false);

		Binary b = new Binary(new File("C:/Temp/LedenLIJST-vertrouwelijktest.csv"));
		((CSVMap) csv).setFileContent(b);
		Mappable[] all = ((CSVMap) csv).getEntries();
		for (int i = 0; i < all.length; i++) {
			CSVEntryMap entryMap = ((CSVEntryMap) all[i]);
			logger.info("a = >" + entryMap.getEntry(new Integer(0)) + "< - >" + 
			                             entryMap.getEntry(new Integer(1)) + "< - >" + 
                                         entryMap.getEntry(new Integer(2)) + "< - >" + 
                                         entryMap.getEntry(new Integer(3)) + "< - >" + 
                                         entryMap.getEntry(new Integer(4)) + "< - >" + 
                                         entryMap.getEntry(new Integer(5)) + "< - >" + 
                                         entryMap.getEntry(new Integer(6)) + "< - >" + 
                                         entryMap.getEntry(new Integer(7)) + "< - >" + 
                                         entryMap.getEntry(new Integer(8)) + "< - >" + 
                                         entryMap.getEntry(new Integer(9)) + "< - >" + 
                                         entryMap.getEntry(new Integer(10)) + "< - >" + 
			                             entryMap.getEntry(new Integer(11))+"<");
		}
	}

	public boolean getIncludeEmpty() {
		return includeEmpty;
	}

	public void setIncludeEmpty(boolean includeEmpty) {
		this.includeEmpty = includeEmpty;
	}

    public boolean isSkipFirstRow() {
        return skipFirstRow;
    }

    public void setSkipFirstRow(boolean skipFirstRow) {
        this.skipFirstRow = skipFirstRow;
    }

    public int getMaximumImportCount() {
        return maximumImportCount;
    }

    public void setMaximumImportCount(int maximumImportCount) {
        this.maximumImportCount = maximumImportCount;
    }
}
