package com.dexels.navajo.tipi.dev.core.util.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {
	
	private final static Logger logger = LoggerFactory
			.getLogger(ZipUtils.class);
	
	public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

	//	in.close();
		out.close();
	}

	public static void main(String[] args) {
		File f = new File("temp");
		unzip(new File("testzip.zip"),f);
		f.mkdir();
//		org.apache.commons.compress.utils.ArchiveUtils.
	}
	
	public static final void unzip(File f, File destination) {
//		logger.info("Unzipping file: " + f.getAbsolutePath() + " into dir: " + destination.getAbsolutePath());
		try {
			FileInputStream fis = new FileInputStream(f);
			// ZipFile zipFile = new ZipFile(f);
			ZipInputStream zis = new ZipInputStream(fis);
			// Enumeration<? extends ZipEntry> entries = zipFile.entries();

			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
		//	ZipEntry entry = zis.getNextEntry();
				// .nextElement();

//				logger.info("Enztru: " + entry.getName());
				File file = new File(destination, entry.getName());
				if (entry.isDirectory()) {
					// Assume directories are stored parents first then children.
//					logger.info("Extracting directory: " + entry.getName());
					// This is not robust, just for demonstration purposes.
					file.mkdir();
					continue;
				}
				File parentDir = file.getParentFile();
				if(!parentDir.exists()) {
					parentDir.mkdirs();
				}
//				logger.info("Extracting file: " + entry.getName());
				copyInputStream(zis, new BufferedOutputStream(new FileOutputStream(file)));
			}
			zis.close();
		} catch (IOException ioe) {
//			logger.info("Unhandled exception:");
			logger.error("Error: ",ioe);
			return;
		}
	}

	public static void zipAll(File here, File zipFile) throws IOException {
		// create a ZipOutputStream to zip the data to
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
		// assuming that there is a directory named inFolder (If there
		// isn't create one) in the same directory as the one the code
		// call the zipDir method
//		File dir = new File(here, dirsStartingWith);
		zipDir(here, zos,here);
		// close the stream
		zos.close();
	}

	
	


	// here is the code for the method
	private static void zipDir(File zipDir, ZipOutputStream zos, File base) throws IOException {
		String[] dirList = zipDir.list();
		byte[] readBuffer = new byte[2156];
		int bytesIn = 0;
		// loop through dirList, and zip the files
		for (int i = 0; i < dirList.length; i++) {
			File f = new File(zipDir, dirList[i]);
			if (f.isDirectory()) {
				// if the File object is a directory, call this
				// function again to add its content recursively
				// String filePath = f.getPath();
				zipDir(f, zos,base);
				// loop again
				continue;
			}
			FileInputStream fis = new FileInputStream(f);
			String relative = base.toURI().relativize(f.toURI()).getPath();

			ZipEntry anEntry = new ZipEntry(relative);
			zos.putNextEntry(anEntry);
			// now write the content of the file to the ZipOutputStream
			while ((bytesIn = fis.read(readBuffer)) != -1) {
				zos.write(readBuffer, 0, bytesIn);
			}
			// close the Stream
			fis.close();
		}
	}

	public static void unzip2(File zippedFile, File destination) {
		final int BUFFER = 2048;

		try {
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(zippedFile);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				logger.info("Extracting: " + entry);
				int count;
				byte data[] = new byte[BUFFER];
				// write the files to the disk
				FileOutputStream fos = new FileOutputStream(new File(destination,entry.getName()));
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
			zis.close();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}
	/**
	 * Same as the stream edition. Does not close streams!
	 * @param out
	 * @param in
	 * @throws IOException
	 */
	public static final void copyResource(Writer out, Reader in) throws IOException {
		BufferedReader bin = new BufferedReader(in);
		BufferedWriter bout = new BufferedWriter(out);
		char[] buffer = new char[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
				read = bin.read(buffer);
				if (read > -1) {
					bout.write(buffer, 0, read);
				}
			if (read <= -1) {
				ready = true;
			}
		}
		bout.flush();
	}


	public static final void copyResource(OutputStream out, InputStream in)
			throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
	}
}
