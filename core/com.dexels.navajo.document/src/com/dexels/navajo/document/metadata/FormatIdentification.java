package com.dexels.navajo.document.metadata;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static helper class that tries to identify the file format
 * for a given file or byte array representing the first bytes of a file.
 * <h3>Usage</h3>
 * <pre>
 * FormatDescription desc = FormatIdentification.identify(new File("testfile.zip"));
 * if (desc != null) {
 *   System.out.println(desc.getShortName());
 * }
 * </pre>
 * @author Marco Schmidt
 */
public class FormatIdentification implements Serializable
{

	
	private final static Logger logger = LoggerFactory.getLogger(FormatIdentification.class);
	private static final long serialVersionUID = 7824735272127450235L;
	private static List<FormatDescription> descriptions;
	private static int minBufferSize;
	private final static String ZIP_EXCEL_CONTENTTYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private final static String ZIP_WORD_CONTENTTYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	private final static String ZIP_POWERPOINT_CONTENTTYPE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
	

	static
	{
		init();
	}

	/**
	 * Private empty constructor to avoid instantiations.	 *
	 */
	private FormatIdentification()
	{
	}

	public static FormatDescription identify(byte[] data) {
		return identify(data, null);
	}
	
	public static FormatDescription identify(byte[] data, File file)
	{
		if (data == null || data.length < 1)
		{
			return null;
		}
		Iterator<FormatDescription> iter = descriptions.iterator();
		while (iter.hasNext())
		{
			FormatDescription desc = iter.next();
			if (desc.matches(data))
			{
				// When it concerns a ZIP archive, it might be some kinda office type doc
				// So check some stuff before returning ZIP as the answer
				if (desc.getLongName().equalsIgnoreCase("PKWare Zip (ZIP)")) {
					FormatDescription newDesc = identifyZipByContent(data, file);
					if (newDesc != null) {
						return newDesc;
					} else {
						return desc;
					}
				} else {
					return desc;
				}
			}
		}
		return null;
	}

	public static FormatDescription identify(File file)
	{
		if (!file.isFile())
		{
			return null;
		}
		long size = file.length();
		int numBytes;
		if (size > 1024)
		{
			numBytes = 1024;
		}
		else
		{
			numBytes = (int) size;
		}
		byte[] data = new byte[ numBytes + 1 ];
		RandomAccessFile in = null;
		try
		{
			in = new RandomAccessFile(file, "r");
			in.read( data, 0, numBytes );
			in.close();
		}
		catch (IOException ioe)
		{
			logger.error("Error: ", ioe);
			return null;
		}
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			}
			catch (IOException ioe)
			{
				logger.error("Error: ", ioe);
			}
		}
		return identify(data, file);
	}
	
	private static File createTempFile(byte[] data) {
		// Create a tmp file for the method to use.
		// Location is the java.io.tmp
		File file = null;
		FileOutputStream fos;
		try {
			UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString();						
			file = File.createTempFile(fileName, ".zip");
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			logger.error("Error: ", e);
			return file;
		} catch (IOException e) {
			logger.error("Error: ", e);
			return file;
		}
		return file;
	}
	
	@SuppressWarnings("resource")
	private static FormatDescription identifyZipByContent (byte[] data, File file) {
		FormatDescription newDesc = null;
		InputStream input = null;
		BufferedReader br = null;
	    ZipInputStream zipInput = null;
	    ZipFile zipFile = null;
	    boolean deleteFile = false;

		try {
			if (file == null) {
				file = createTempFile(data);
				deleteFile = true;
			}
		    zipFile = new ZipFile(file);
		    final Enumeration<? extends ZipEntry> entries = zipFile.entries();

            // Create label for breaking
            readentries:
		    while (entries.hasMoreElements()) {
		        final ZipEntry zipEntry = entries.nextElement();
		        if (!zipEntry.isDirectory()) {
		            final String fileName = zipEntry.getName();
		            if (fileName.endsWith(".xml")) {
		                //zipInput = new ZipInputStream(new FileInputStream(fileName));
		                input = zipFile.getInputStream(zipEntry);
		                br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		                
		                String line;
		                while((line = br.readLine()) != null) {
		                	String descriptionMatcher = null;
		                	// Check if a known contenttype is found
		                	if (line.contains(FormatIdentification.ZIP_EXCEL_CONTENTTYPE)) {
		                		descriptionMatcher = FormatIdentification.ZIP_EXCEL_CONTENTTYPE;
		                	} else if (line.contains(FormatIdentification.ZIP_WORD_CONTENTTYPE)) {
		                		descriptionMatcher = FormatIdentification.ZIP_WORD_CONTENTTYPE;
		                	} else if (line.contains(FormatIdentification.ZIP_POWERPOINT_CONTENTTYPE)) {
		                		descriptionMatcher = FormatIdentification.ZIP_POWERPOINT_CONTENTTYPE;
		                	}
		                	
		                	// When something is found, then find the correct description for it
		                	if (descriptionMatcher != null) {
			            		Iterator<FormatDescription> iter = descriptions.iterator();
			            		while (iter.hasNext()) {
			            			FormatDescription desc = iter.next();
			            			if (desc.getMimeType().equalsIgnoreCase(descriptionMatcher)) {
			            				newDesc = desc;
			            				break readentries;
			            			}
			            		}
		                	}
		                }
		                br.close();
		                zipInput.closeEntry();
		                input.close();
		            }
		        }
		    }
		} catch (final IOException ioe) {
			logger.error("Error: ", ioe);
			return null;
		} finally {
			// Clean up
			try {
				if (br != null) {
					br.close();
				}
				if (zipInput != null) {
					zipInput.closeEntry();
				}
				if (input != null) {
					input.close();
				}
				if (zipFile != null) {
				    zipFile.close();
				}
				if (deleteFile) {
					file.delete();
				}
			} catch (IOException e) {
				logger.error("Error: ", e);
				return newDesc;
			}
		}

		return newDesc;
	}

	private static void init()
	{
		descriptions = new ArrayList<FormatDescription>();
		minBufferSize = 1;
		try
		{
			InputStream input =  FormatIdentification.class.getResource("formats.txt").openStream();
			if (input == null)
			{
				return;
			} else {
                        }
			FormatDescriptionReader in = new FormatDescriptionReader(new InputStreamReader(input));
			FormatDescription desc;
			while ((desc = in.read()) != null)
			{
				byte[] magic = desc.getMagicBytes();
				Integer offset = desc.getOffset();
				if (magic != null && offset != null && offset.intValue() + magic.length > minBufferSize)
				{
					minBufferSize = offset.intValue() + magic.length;
				}
				descriptions.add(desc);
			}
			input.close();
		}
		catch (Exception e)
		{
			logger.error("Error: ", e);
		}
	}

	
	public static void main(String[] args) {
		FormatIdentification.init();
		FormatDescription sd = FormatIdentification.identify(new File("C:/Users/Erik/Desktop/Workbook1.xls"));
		logger.info("Result: "+sd);
		sd = FormatIdentification.identify(new File("C:/Users/Erik/Desktop/bestellijst_aarse.doc"));
		logger.info("Result: "+sd);
		sd = FormatIdentification.identify(new File("C:/Users/Erik/Desktop/20130425_uitgevoerde tests KNKV.xlsx"));
		logger.info("Result: "+sd);
		sd = FormatIdentification.identify(new File("C:/Users/Erik/Desktop/OOWriterTest.odt"));
		logger.info("Result: "+sd);
		sd = FormatIdentification.identify(new File("C:/Users/Erik/Desktop/OOCalcTest.ods"));
		logger.info("Result: "+sd);
	}
}
