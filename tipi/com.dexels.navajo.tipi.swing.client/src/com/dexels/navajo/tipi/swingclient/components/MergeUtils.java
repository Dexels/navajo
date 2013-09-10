package com.dexels.navajo.tipi.swingclient.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.BinaryOpenerFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public class MergeUtils {
	private static String filename = "c:/merge.dat";
	private static String delimiter = "\t";
	
	private final static Logger logger = LoggerFactory
			.getLogger(MergeUtils.class);
	
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
			logger.error("Error: ",e);
		}
	}

	public static void exportMergeData(String mergeFile, Message m,
			String delimiter) {
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
					List<Property> props = h.getAllProperties();
					for (int i = 0; i < props.size(); i++) {
						Property p = props.get(i);
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
						List<Property> prop = current.getAllProperties();
						for (int j = 0; j < prop.size(); j++) {
							Property p = prop.get(j);
							if (p.getType().equals(Property.SELECTION_PROPERTY)) {
								String value = p.getSelected().getName();
								if ("null".equals(value) || value == null) {
									value = "";
								}
								line = line + value + delimiter;
							} else if (p.getType().equals(
									Property.DATE_PROPERTY)) {
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
				List<Property> props = m.getAllProperties();
				for (int i = 0; i < props.size(); i++) {
					Property p = props.get(i);
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
			logger.error("Error: ",e);
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

/*			if (SwingClient.getUserInterface().showQuestionDialog("Outlook?")) {
				separator = ";";
			} */

			logger.info("Sending mail: " + columnName);
			List<String> recepients = new ArrayList<String>();
			for (int i = 0; i < data.getArraySize(); i++) {
				String address = data.getMessage(i).getProperty(columnName)
						.getValue();
				logger.info("Got: " + address);
				if (address != null && address.indexOf("@") > 0) {
					logger.info("Adding: " + address);
					recepients.add(address);
				}
			}

			// mailto: is added by the BinaryOpener.
			String mailString = "?bcc=";

			for (int j = 0; j < recepients.size(); j++) {
				mailString = mailString + recepients.get(j) + separator;
			}
			mailString = mailString.substring(0, mailString.length() - 1);
			logger.info("Calling openDoc: " + mailString);
			BinaryOpenerFactory.getInstance().mail(mailString);
		} catch (Exception e) {
			logger.info("Could not send email: " + e.getMessage());
		}

	}

	public static void openDocument(Binary b, String extensionHint) {
		String extension = null;
		if (extensionHint != null) {
			extension = b.getExtension();
		} else {
			extension = b.getExtension();
		}

		logger.info("Ext: " + extension);
		try {
			File f = File.createTempFile("datadump", "." + extension);
			logger.info("File: " + f.getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(f);
			b.write(fos);
			fos.flush();
			fos.close();
			BinaryOpenerFactory.getInstance().open(f);
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
	}

	public static void openDocument(Binary b) {
		openDocument(b, null);
	}

	public static void openDocument(String docFile) {
		BinaryOpenerFactory.getInstance().open(docFile);
	}

}
