/*
GNU Lesser General Public License

MutableFilter
Copyright (C) 2000-2003 Howard Kistler
changes to MutableFilter
Copyright (C) 2003-2004 Karsten Pawlik

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package de.xeinfach.kafenio.component;

import java.io.File;
import javax.swing.filechooser.FileFilter;

import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for providing JFileChooser with a FileFilter
 * 
 * @author Howard Kistler
 */
public class MutableFilter extends FileFilter {

	private static LeanLogger log = new LeanLogger("MutableFilter.class");
	
	private String[] acceptableExtensions;
	private String descriptor;

	public static final String[] EXT_HTML = { "html", "htm", "shtml" };
	public static final String[] EXT_CSS  = { "css" };
	public static final String[] EXT_IMG  = { "gif", "jpg", "jpeg", "png" };
	public static final String[] EXT_RTF  = { "rtf" };
	public static final String[] EXT_BASE64  = { "b64" };
	public static final String[] EXT_SER  = { "ser" };

	/**
	 * creates a new MutableFilter Object using the given values.
	 * @param exts file extensions to accept
	 * @param desc a description.
	 */
	public MutableFilter(String[] exts, String desc) {
		acceptableExtensions = exts;
		StringBuffer strbDesc = new StringBuffer(desc + " (");
		for(int i = 0; i < acceptableExtensions.length; i++) {
			if(i > 0) strbDesc.append(", "); 
			strbDesc.append("*." + acceptableExtensions[i]);
		}
		strbDesc.append(")");
		descriptor = strbDesc.toString();
		log.debug("new MutableFilter created.");
	}

	/**
	 * @param file a File
	 * @return returns true if the given file was accepted, false otherwise.
	 */
	public boolean accept(File file) {
		if(file.isDirectory()) {
			return true;
		}
		String fileName = file.getName();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
		if(fileExt != null) {
			for(int i = 0; i < acceptableExtensions.length; i++) {
				if(fileExt.equals(acceptableExtensions[i])) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * @return returns the object's description as string.
	 */
	public String getDescription() {
		return descriptor;
	}
}

