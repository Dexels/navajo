/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.io.*;
import java.util.*;

import metadata.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiOpenBinary extends TipiAction {

    /*
     * (non-Javadoc)
     *
     * @see com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.tipi.internal.TipiEvent)
     */
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

		try {
			if (fileNameEval == null) {
				fileNameEval = "data_";
			}
			File f = File.createTempFile(fileNameEval, "." + extString);
			TipiSaveValue.saveFile(b, f);
			DefaultBrowser.displayURL(f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		b.getHandle();
	}

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("tipi_", ""+".pdf");
//        URL u = f.toURL();
        DefaultBrowser.displayURL(f.getAbsolutePath());

    }
}
