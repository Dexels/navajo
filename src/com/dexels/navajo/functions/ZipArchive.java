package com.dexels.navajo.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class ZipArchive extends FunctionInterface {

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

		try {
			File tempFile = File.createTempFile("zip_archive", "navajo");
			FileOutputStream fos = new FileOutputStream( tempFile );
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zo = new ZipOutputStream( fos );

			File p = new File(pathName);
			File [] entries = p.listFiles();
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
			e.printStackTrace(System.err);
			return null;
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
		za.insertOperand("/home/arjen/testzip");
		Binary o = (Binary) za.evaluate();
		System.err.println("o =" + o);
		File result = new File("/home/arjen/result.zip");
		FileOutputStream fos = new FileOutputStream(result);
		o.write(fos);
		fos.close();
	}

}
