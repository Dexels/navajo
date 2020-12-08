/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.metadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

/**
 * Read {@link com.dexels.navajo.document.metadata.FormatDescription} objects from a semicolon-separated text file.
 * @author Marco Schmidt
 */
public class FormatDescriptionReader implements Serializable
{
	
	private static final long serialVersionUID = -6016285390695170319L;
	private transient BufferedReader in;

	public FormatDescriptionReader() {
		
	}
	
	public FormatDescriptionReader(Reader reader)
	{
		in = new BufferedReader(reader);
	}

	public FormatDescription read() throws IOException
	{
		String line;
		do
		{
			line = in.readLine();
			if (line == null)
			{
				return null;
			}
		}
		while (line.length() < 1 || line.charAt(0) == '#');
		String[] items = line.split(";");
		FormatDescription desc = new FormatDescription();
		desc.setGroup(items[0]);
		desc.setShortName(items[1]);
		desc.setLongName(items[2]);
		desc.addMimeTypes(items[3]);
		desc.addFileExtensions(items[4]);
		desc.setOffset(Integer.valueOf(items[5]));
		desc.setMagicBytes(items[6]);
		desc.setMinimumSize(Integer.valueOf(items[7]));
		return desc;
	}
}
