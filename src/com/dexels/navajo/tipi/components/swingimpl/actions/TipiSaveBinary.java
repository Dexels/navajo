/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.*;
import java.io.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiSaveBinary extends TipiAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.
	 * tipi.internal.TipiEvent)
	 */
	private File f = null;
	private int result;
	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {

		Operand propertyO = getEvaluatedParameter("property", event);

		Object value = propertyO.value;
		if (value == null) {
			throw new TipiBreakException(-4);
		}
		if (!(value instanceof Property)) {
			throw new TipiException("TipiSaveBinary needs a property parameter of type property");
		}
		final Property pp = (Property) value;
		if (!(pp.getType().equals(Property.BINARY_PROPERTY))) {
			throw new TipiException("TipiSaveBinary only works for binary properties!");
		}
		Binary b = (Binary) pp.getTypedValue();
		myContext.runAsyncInEventThread(new Runnable() {

			public void run() {
				Container c = null;

				JFileChooser jf = new JFileChooser();
				jf.setCurrentDirectory(new File(System.getProperty("user.home")));
				String description = pp.getSubType("description");
				if (description != null) {
					jf.setSelectedFile(new File(description));
				}
				result = jf.showSaveDialog(c);
				f = jf.getSelectedFile();

			}
		});
		if (result != JFileChooser.APPROVE_OPTION) {
			throw new TipiBreakException(-2);
		}
	
		saveFile(b, f);

	}

	public static void saveFile(Binary value, File f) throws TipiBreakException, TipiException {
		if (f == null) {
			throw new TipiBreakException(-3);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			Binary b = (Binary) value;
			b.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new TipiException("File not found: " + e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TipiException("IO Error: " + e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
