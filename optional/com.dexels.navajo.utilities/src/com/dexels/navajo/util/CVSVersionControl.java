package com.dexels.navajo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CVSVersionControl {

	private String version;
	private String author;
	private Date date;	
	private String versionInfo;
	private String cvsRoot;
	private String repository;
	
	public CVSVersionControl(File f) {
		findIdTag(f);
		parseVersionInfo();
		findRoot(f);
		findRepository(f);
		if ( this.versionInfo == null ) {
			findVersionInfoFromEntries(f);
		}
	}
	
	private void findIdTag(File f) {
		BufferedReader br = null;
		try {
			br =  new BufferedReader(new FileReader(f));
			String line = null;
			while ( ( line = br.readLine() ) != null ) {
				if ( line.indexOf("$Id") != -1 ) { // Found line with $Id$.
					StringBuffer sb = new StringBuffer();
					char [] array = line.toCharArray();
					boolean start = false;
					for (int i = 0; i < array.length; i++) {
						if ( array[i] == '$' && i < array.length - 2 && array[i+1] == 'I' && array[i+2] == 'd' && !start) {
							start = true;
							sb.append(array[i]);
						} else if ( array[i] == '$' && start) {
							sb.append(array[i]);
							break;
						} else if ( start ) {
							sb.append(array[i]);
						}
					}
					this.versionInfo = sb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			if ( br != null ) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private String readFirstLineFromFile(File f) {
		BufferedReader br = null;
		String line = null;
		try {
			br =  new BufferedReader(new FileReader(f));
			line = br.readLine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}  finally {
			if ( br != null ) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return line;
	}
	
	private void findRoot(File f) {
		File p = f.getParentFile();
		File root = new File(p, "CVS/Root");
		this.cvsRoot = readFirstLineFromFile(root);
	}
	
	private void findRepository(File f) {
		File p = f.getParentFile();
		File root = new File(p, "CVS/Repository");
		this.repository = readFirstLineFromFile(root);
	}
	
	private void findVersionInfoFromEntries(File f) {
		File p = f.getParentFile();
		File root = new File(p, "CVS/Entries");
		// /ProcessQueryMatch.xml/1.120/Wed Feb 18 16:13:32 2009//
		String simpleName = f.getName();
		BufferedReader br = null;
		try {
			br =  new BufferedReader(new FileReader(root));
			boolean found = false;
			String line = null;
			while ( !found && ( line = br.readLine() ) != null ) {
				if ( line.startsWith("/" + simpleName + "/")) {
					found = true;
				}
			}
			if ( line == null ) {
				return;
			}
			String [] tokens = line.split("/");
			this.version = tokens[2];
			this.date = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy").parse(tokens[3]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}  finally {
			if ( br != null ) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void parseVersionInfo() {
		if ( this.versionInfo == null ) {
			return;
		}
		String [] tokens = this.versionInfo.split(" ");
		if ( tokens.length == 8 ) {
			this.version = tokens[2];
			try {
				this.date = new SimpleDateFormat("yyyy/MM/dd").parse(tokens[3]);
			} catch (ParseException e) {
			}
			this.author = tokens[5];
		}
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getRepository() {
		return repository;
	}
	

	public String getCvsRoot() {
		return cvsRoot;
	}
}
