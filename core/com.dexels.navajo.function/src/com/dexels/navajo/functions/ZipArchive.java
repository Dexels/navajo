package com.dexels.navajo.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class ZipArchive extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(ZipArchive.class);
	
	private final void addFiles(ZipOutputStream zo, String path, File f) throws Exception {
		if ( f.isFile() ) {
			ZipEntry entry = new ZipEntry(path + "/" + f.getName());
			zo.putNextEntry(entry);
			// Write file content
			Binary bfi = new Binary(new FileInputStream(f));
			bfi.write(zo);
		} else {
			path = (!path.equals("") ? path + "/" : "") + f.getName();
			File [] entries = f.listFiles();
			for (int i = 0; i < entries.length; i++) {
				addFiles(zo, path, entries[i]);
			}
		}
	}
	
	@Override
	public Object evaluate() throws TMLExpressionException {

		String pathName = (String) getOperand(0);
		ZipOutputStream zo = null;

		try {
			File tempFile = File.createTempFile("zip_archive", "navajo");
			FileOutputStream fos = new FileOutputStream( tempFile );
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			zo = new ZipOutputStream( fos );

			File p = new File(pathName);
			File [] entries = p.listFiles();
			if(entries==null) {
				return null;
			}
			for (int i = 0; i < entries.length; i++) {
				addFiles(zo, "", entries[i]);
			}
			zo.closeEntry();
			zo.close();
			fos.flush();
			fos.close();

			Binary b = new Binary( tempFile );

			return b;
		} catch (Exception e) {
			logger.error("Error: ", e);
			return null;
		} finally {
			if(zo!=null) {
				try {
					zo.close();
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
			}
		}
	}

	@Override
	public String remarks() {
		return "Archives an entire directory (including subdirs) as a zip file";
	}

	@Override
	public String usage() {
		return "ZipArchive([path])";
	}
	
	public static void main(String [] args) throws Exception {
		ZipArchive za = new ZipArchive();
		za.reset();
		za.insertOperand("testzip");
		Binary o = (Binary) za.evaluate();
		System.err.println("o =" + o);
		File result = new File("result.zip");
		FileOutputStream fos = new FileOutputStream(result);
		o.write(fos);
		fos.close();
	}

}
