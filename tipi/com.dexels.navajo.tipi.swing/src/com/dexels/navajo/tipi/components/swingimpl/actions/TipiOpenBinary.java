/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.metadata.FormatDescription;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.swing.DefaultBrowser;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiOpenBinary extends TipiAction {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiOpenBinary.class);
	
	private static final long serialVersionUID = 90381883062864115L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.
	 * tipi.internal.TipiEvent)
	 */
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

		try {
			if (fileNameEval == null) {
				fileNameEval = "data_";
			}
			File f = File.createTempFile(fileNameEval, "." + extString);
			TipiSaveValue.saveFile(b, f);
			DefaultBrowser.displayURL(f.getAbsolutePath());
		} catch (IOException e) {
			logger.error("Error detected",e);
		}
		b.getHandle();
	}

	public static void main(String[] args) throws Exception {
		File f = File.createTempFile("tipi_", "" + ".pdf");
		// URL u = f.toURL();
		DefaultBrowser.displayURL(f.getAbsolutePath());

	}
}
