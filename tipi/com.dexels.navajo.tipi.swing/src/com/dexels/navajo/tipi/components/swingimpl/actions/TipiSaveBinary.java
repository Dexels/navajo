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
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.metadata.FormatDescription;
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
public class TipiSaveBinary extends TipiAction {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSaveBinary.class);
	
	private static final long serialVersionUID = -6668690071553956247L;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.
	 * tipi.internal.TipiEvent)
	 */
	private File f = null;
	private int result;

	@Override
	protected void execute(TipiEvent event) throws TipiBreakException,
			TipiException {

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
			throw new TipiException(
					"TipiOpenBinary: Type of value is not Binary, but: "
							+ value.value.getClass());
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
			if (fd != null) {
				List<String> extensions = fd.getFileExtensions();
				if (!extensions.isEmpty()) {
					ext = extensions.get(0);

				}
			}
			if (mime != null) {
				if (mime.indexOf("/") != -1) {
					StringTokenizer st = new StringTokenizer(mime, "/");
					String major = st.nextToken();
					String minor = st.nextToken();
					logger.debug("Binary type: " + major + " and minor: "
							+ minor);
					if (ext != null) {
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

			@Override
			public void run() {
				Container c = null;
				doShowSaveDialog(c, fname + "." + e);

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

	public static void saveFile(Binary value, File f)
			throws TipiBreakException, TipiException {
		if (f == null) {
			throw new TipiBreakException(-3);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			value.write(fos);
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
