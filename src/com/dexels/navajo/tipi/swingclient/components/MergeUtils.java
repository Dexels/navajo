package com.dexels.navajo.tipi.swingclient.components;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.swingclient.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class MergeUtils {
	private static String filename = "c:/merge.dat";
	private static String delimiter = "\t";

	public MergeUtils() {
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		MergeUtils.delimiter = delimiter;
	}

	public static void exportMergeData(MessageTable t, String delimiter) {
		try {
			File f = new File(filename);
			FileWriter fw = new FileWriter(f);
			int cols = t.getColumnCount();
			String header = "";
			for (int i = 0; i < cols; i++) {
				String v = t.getColumnName(i);
				if ("null".equals(v) || v == null) {
					v = "unknown";
				}
				header = header + v + delimiter;
			}
			fw.write(header + "\n");
			for (int j = 0; j < t.getRowCount(); j++) {
				String data = "";
				for (int k = 0; k < cols; k++) {
					String value = t.getValueAt(j, k).toString();
					if ("null".equals(value) || value == null) {
						value = "";
					}
					data = data + value + delimiter;
				}
				if (j == t.getRowCount() - 1) {
					fw.write(data);
				} else {
					fw.write(data + "\n");
				}
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exportMergeData(String mergeFile, Message m, String delimiter) {
		setMergeFile(mergeFile);
		exportMergeData(m, delimiter);
	}

	public static void exportMergeData(Message m, String delimiter) {
		try {
			File f = new File(filename);
			FileWriter fw = new FileWriter(f);
			if (m.getType().equals(Message.MSG_TYPE_ARRAY)) {
				if (m.getArraySize() > 0) {
					String header = "";
					Message h = m.getMessage(0);
					ArrayList props = h.getAllProperties();
					for (int i = 0; i < props.size(); i++) {
						Property p = (Property) props.get(i);
						String head = p.getDescription();
						if ("null".equals(head) || head == null) {
							head = "";
						}
						header = header + head + delimiter;
					}
					fw.write(header + "\n");

					for (int i = 0; i < m.getArraySize(); i++) {
						String line = "";
						Message current = m.getMessage(i);
						ArrayList prop = current.getAllProperties();
						for (int j = 0; j < prop.size(); j++) {
							Property p = (Property) prop.get(j);
							if (p.getType().equals(Property.SELECTION_PROPERTY)) {
								String value = p.getSelected().getName();
								if ("null".equals(value) || value == null) {
									value = "";
								}
								line = line + value + delimiter;
							} else if (p.getType().equals(Property.DATE_PROPERTY)) {
								String value = p.toString();
								if ("null".equals(value) || value == null) {
									value = "";
								}
								line = line + value + delimiter;
							} else {
								String value = p.getValue();
								if ("null".equals(value) || value == null) {
									value = "";
								}
								line = line + value + delimiter;
							}
						}
						if (i < m.getArraySize() - 1) {
							fw.write(line + "\n");
						} else {
							fw.write(line);
						}
					}
				}
			} else {
				String header = "";
				String line = "";
				ArrayList props = m.getAllProperties();
				for (int i = 0; i < props.size(); i++) {
					Property p = (Property) props.get(i);
					header = header + p.getDescription() + delimiter;
					String value = p.getValue();
					if ("null".equals(value) || value == null) {
						value = "";
					}
					line = line + value + delimiter;
				}
				fw.write(header + "\n");
				fw.write(line);
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setMergeFile(String file) {
		filename = file;
	}

	public static void openDocument(MessageTable t, String docFile) {
		exportMergeData(t, delimiter);
		openDocument(docFile);
	}

	public static void openDocument(Message m, String docFile) {
		exportMergeData(m, delimiter);
		openDocument(docFile);
	}

	public static void openDocument(String mergeFile, Message m, String docFile) {
		exportMergeData(mergeFile, m, delimiter);
		openDocument(docFile);
	}

	public static void sendEmail(String columnName, Message data) {
		try {

			String separator = ",";

			if (SwingClient.getUserInterface().showQuestionDialog("Outlook?")) {
				separator = ";";
			}

			System.err.println("Sending mail: " + columnName);
			ArrayList recepients = new ArrayList();
			for (int i = 0; i < data.getArraySize(); i++) {
				String address = data.getMessage(i).getProperty(columnName).getValue();
				System.err.println("Got: " + address);
				if (address != null && address.indexOf("@") > 0) {
					System.err.println("Adding: " + address);
					recepients.add(address);
				}
			}

			String mailString = "mailto:?bcc=";

			for (int j = 0; j < recepients.size(); j++) {
				mailString = mailString + (String) recepients.get(j) + separator;
			}
			mailString = mailString.substring(0, mailString.length() - 1);
			System.err.println("Calling openDoc: " + mailString);
			openDocument(mailString);
		} catch (Exception e) {
			System.err.println("Could not send email: " + e.getMessage());
		}

	}

	public static void openDocument(Binary b, String extensionHint) {
		String extension = null;
		if(extensionHint!=null) {
			extension = b.getExtension();
		} else {
			extension = b.getExtension();
		}
		
		System.err.println("Ext: " + extension);
		try {
			File f = File.createTempFile("datadump", "." + extension);
			System.err.println("File: "+f.getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(f);
			b.write(fos);
			fos.flush();
			fos.close();
			openDocument(f.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void openDocument(Binary b) {
		openDocument(b, null);
	}

	public static void openDocument(String docFile) {
		try {
			Process p = null;
			if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
				p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + docFile);
			} else { 
				if(System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) {
					p = Runtime.getRuntime().exec("open " + docFile);
				} else{
				// non-Windows platform, assume Linux/Unix
				String[] cmd = new String[2];

				if (docFile.toLowerCase().endsWith(".doc") || docFile.toLowerCase().endsWith(".rtf")
						|| docFile.toLowerCase().endsWith(".xls") || docFile.toLowerCase().endsWith(".csv")
						|| docFile.toLowerCase().endsWith(".ppt")) {
					cmd[0] = "ooffice";
				} else if (docFile.toLowerCase().endsWith(".txt")) {
					cmd = new String[4]; // resize
					cmd[0] = "xterm";
					cmd[1] = "-e";
					cmd[2] = "vi";
				} else if (docFile.toLowerCase().endsWith(".jpg") || docFile.toLowerCase().endsWith(".gif")
						|| docFile.toLowerCase().endsWith(".png") || docFile.toLowerCase().endsWith(".tif")
						|| docFile.toLowerCase().endsWith(".tiff")) {
					cmd[0] = "display";
				} else if (docFile.toLowerCase().endsWith(".pdf")) {
					cmd[0] = "xpdf";
				} else { // we don't have a clue, try a plain web browser
					cmd[0] = "mozilla";
				}

				cmd[(docFile.toLowerCase().endsWith(".txt")) ? 3 : 1] = docFile;

				if (cmd[0] != null) {
					 p = Runtime.getRuntime().exec(cmd);
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
