package com.dexels.navajo.document.metadata;

import java.io.*;

/**
 * Read {@link com.dexels.navajo.document.metadata.FormatDescription} objects from a semicolon-separated text file.
 * @author Marco Schmidt
 */
public class FormatDescriptionReader implements Serializable
{
	
	private static final long serialVersionUID = -6016285390695170319L;
	private BufferedReader in;

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
		desc.setOffset(new Integer(items[5]));
		desc.setMagicBytes(items[6]);
		desc.setMinimumSize(new Integer(items[7]));
		return desc;
	}
}
