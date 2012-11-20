/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiSaveValue extends TipiAction {

	private static final long serialVersionUID = -7529816552777424125L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSaveValue.class);
	protected void execute(TipiEvent event) throws TipiBreakException,
			TipiException {
		Operand globalvalue = getEvaluatedParameter("value", event);
		JFileChooser jf = new JFileChooser();
		Object o = getComponent().getContainer();
		Container c = null;
		if (o instanceof Container) {
			c = (Container) o;
		}
		if (globalvalue == null) {
			throw new TipiBreakException(-1);
		}
		Object value = globalvalue.value;
		if (value == null) {
			throw new TipiBreakException(-4);
		}

		// if (o==null) {
		// o =event.getContext().getDefaultTopLevel()
		// }
		try {
			jf.setCurrentDirectory(new File(System.getProperty("user.home")));
		} catch (SecurityException e) {
			throw new TipiException("No file access allowed. Sorry.");
		}
		int result = jf.showSaveDialog(c);
		if (result != JFileChooser.APPROVE_OPTION) {
			throw new TipiBreakException(-2);
		}
		File f = jf.getSelectedFile();
		saveFile(value, f);

	}

	public static void saveFile(Object value, File f)
			throws TipiBreakException, TipiException {
		if (f == null) {
			throw new TipiBreakException(-3);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			if (value instanceof Binary) {
				Binary b = (Binary) value;
				b.write(fos);
			} else {
				fos.write(value.toString().getBytes());
			}
		} catch (FileNotFoundException e) {
			logger.error("Error detected",e);
			throw new TipiException("File not found: " + e);
		} catch (IOException e) {
			logger.error("Error detected",e);
			throw new TipiException("IO Error: " + e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("Error detected",e);
				}
			}
		}
	}

}
