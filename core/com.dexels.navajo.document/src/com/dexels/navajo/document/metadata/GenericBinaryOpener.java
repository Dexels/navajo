package com.dexels.navajo.document.metadata;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.BinaryOpener;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.types.Binary;


public class GenericBinaryOpener implements BinaryOpener {

	private final static Logger logger = LoggerFactory
			.getLogger(GenericBinaryOpener.class);
	// private static final String UNIX_PATH = "netscape";
	// private static final String UNIX_FLAG = "-remote openURL";

	public GenericBinaryOpener() {
		super();
	}
	
	@Override
	public boolean mail(String url)
	{
		try {
			Desktop.getDesktop().mail(new URI("mailto", url, null));
			return true;
		} catch (IOException e) {
			logger.error("Could not open mail, uri= {}", url, e);
		} catch (URISyntaxException e) {
		    logger.error("Could not open mail, uri= {}", url, e);
		}
		return false;
	}
	
	@Override
	public boolean open(String s)
	{
		return open(new File(s));
	}
	@Override
	public boolean open(Binary b)
	{
		return open(b.getFile());
	}
	@Override
	public boolean open(File f)
	{
		try {
			Desktop.getDesktop().open(f);
			return true;
		} catch (Throwable e) {
			logger.error("Error opening file: {}",f.getAbsolutePath(), e);
			return false;
		}
	}

	@Override
	public boolean browse(String url)
	{
		if (url.contains(":"))
		{
			return browse(url.substring(0, url.indexOf(":")), url.substring(url.indexOf(":") + 1, url.length()));
		}
		else
		{
			return browse("http", url);
		}
	}
	@Override
	public boolean browse(String scheme, String url)
	{
		try {
			Desktop.getDesktop().browse(new URI (scheme, url, null));
			return true;
		} catch (Throwable e) {
	         logger.error("Could not open browser, scheme={} uri={}", scheme, url, e);
		} 
		return false;
	}
	
	@Override
	public boolean exportCsv(String fileName, Message m, String delimiter)
	{
	    Writer out = null;
		try {
			File f = new File(fileName);
			out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f), "UTF-8"));
		    m.writeAsCSV(out, delimiter);
			return open(f);
		} catch (Throwable e) {
			logger.error("Error exporting {} to CSV ", fileName, e);
		} finally {
		    if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // whatever
                }
            }
		}
		return false;
	}
}
