package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.adapter.csvmap.CSVEntryMap;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.Binary;

import java.io.*;
import java.io.InputStreamReader;
import java.util.*;

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

	private boolean update = false;
	private ArrayList draftEntries = null;

	public void load(Access access) throws MappableException, UserException {

	}

	public void setEntry(CSVEntryMap newEntry) {
		if (draftEntries == null)
			draftEntries = new ArrayList();
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

	public CSVEntryMap[] getEntries() throws UserException {
		try {
			Reader f = null;
			if (fileContent != null) {
				f = new InputStreamReader(fileContent.getDataAsStream());
			} else {
				f = new FileReader(fileName);
			}

			if (f != null) {
				BufferedReader buffer = new BufferedReader(f);
				String line = "";
				ArrayList entryList = new ArrayList();
				while ((line = buffer.readLine()) != null) {
					if (includeEmpty) {
						parseLineWithEmpty(line, entryList);
					} else {
						parseLineDefault(line, entryList);
					}
				}
				entries = new CSVEntryMap[entryList.size()];
				entries = (CSVEntryMap[]) entryList.toArray(entries);
			}
		} catch (java.io.IOException ioe) {
			throw new UserException(-1, ioe.getMessage());
		}
		return entries;
	}

	private void parseLineWithEmpty(String line, ArrayList entryList) {
		// TODO Auto-generated method stub
		String sep = separator;
		if (sep == null) {
			sep = " ";
		}
		if (sep.length() > 1) {
			throw new IllegalArgumentException("Can not include empty when separator is > 1 char.Sorry, feel free to implement.");
		}
		char sepChar = sep.charAt(0);
		int startindex = 0;
		ArrayList currentLine = new ArrayList();
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
			csvEntry.entries[i] = (String) currentLine.get(i);
		}
		entryList.add(csvEntry);
	}

	private void parseLineDefault(String line, ArrayList entryList) {
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

	public void setFileName(String s) throws UserException {
		this.fileName = s;
	}

	public void setFileContent(Binary b) throws UserException {
		this.fileContent = b;
	}

	public void store() throws MappableException, UserException {
		if (draftEntries != null) {
			entries = new CSVEntryMap[draftEntries.size()];
			entries = (CSVEntryMap[]) draftEntries.toArray();
		}
		if (update) {
			try {
				// Write CSV.
				if (entries != null) {
					FileWriter writer = new FileWriter(fileName);
					for (int i = 0; i < entries.length; i++) {
						CSVEntryMap e = (CSVEntryMap) entries[i];
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
		((CSVMap) csv).setIncludeEmpty(false);

		Binary b = new Binary(new File("c:/csvtheanimals.csv"));
		((CSVMap) csv).setFileContent(b);
		Mappable[] all = ((CSVMap) csv).getEntries();
		for (int i = 0; i < all.length; i++) {
			CSVEntryMap entryMap = ((CSVEntryMap) all[i]);
			System.err.println("a = >" + entryMap.getEntry(new Integer(0)) + "< - >" + entryMap.getEntry(new Integer(1)) + "< - >"
					+ entryMap.getEntry(new Integer(2))+"<");
		}
	}

	public boolean getIncludeEmpty() {
		return includeEmpty;
	}

	public void setIncludeEmpty(boolean includeEmpty) {
		this.includeEmpty = includeEmpty;
	}
}
