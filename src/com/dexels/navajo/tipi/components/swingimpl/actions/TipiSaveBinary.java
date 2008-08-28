/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import metadata.*;

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

		Operand value = getEvaluatedParameter("value", event);
		Operand extension = getEvaluatedParameter("extension", event);
		Operand fileName = getEvaluatedParameter("fileName", event);
		if (value == null) {
			throw new TipiException("TipiOpenBinary: no value supplied");
		}
		if (value.value == null) {
			throw new TipiException("TipiOpenBinary: null value supplied");
		}
		if (!(value.value instanceof Binary)) {
			throw new TipiException("TipiOpenBinary: Type of value is not Binary, but: " + value.value.getClass());
		}
		String extString = null;
		if (extension != null) {
			extString = (String) extension.value;
		}
		String fileNameEval = null;
		if (fileName != null) {
			fileNameEval = (String) fileName.value;
		} else {
			throw new TipiException("TipiOpenBinary: fileName value required");
		}
		Binary b = (Binary) value.value;
		if (extString == null) {
			String mime = b.guessContentType();
			String ext = null;
			FormatDescription fd = b.getFormatDescription();
			if(fd!=null) {
				List<String> extensions = fd.getFileExtensions();
				if(!extensions.isEmpty()) {
					ext = extensions.get(0);

				}
			}
			if (mime != null) {
				if (mime.indexOf("/") != -1) {
					StringTokenizer st = new StringTokenizer(mime, "/");
					String major = st.nextToken();
					String minor = st.nextToken();
					System.err.println("Binary type: " + major + " and minor: " + minor);
					if(ext!=null) {
						extString = ext;
					} else {
						extString = minor;
					}
				}
			}
		}
		final String fname = fileNameEval;
		final String e = extString;
		if (fileNameEval == null) {
			fileNameEval = "data_";
		}
		myContext.runSyncInEventThread(new Runnable() {

			public void run() {
				Container c = null;
				doShowSaveDialog(c, fname+"."+e);

			}
		});
		if (result != JFileChooser.APPROVE_OPTION) {
			throw new TipiBreakException(-2);
		}

		saveFile(b, f);
		
		
		
		
		
		
		
		

	}

	private void doShowSaveDialog(Container c, String description) {
		JFileChooser jf = new JFileChooser();
		jf.setCurrentDirectory(new File(System.getProperty("user.home")));
		if (description != null) {
			jf.setSelectedFile(new File(description));
		}
		result = jf.showSaveDialog(c);
		f = jf.getSelectedFile();
	}

	public static void saveFile(Binary value, File f) throws TipiBreakException, TipiException {
		if (f == null) {
			throw new TipiBreakException(-3);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			value.write(fos);
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
