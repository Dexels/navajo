package com.dexels.navajo.tipi.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.echoimpl.EchoTipiContext;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiServeBinary extends TipiAction {

	private static final long serialVersionUID = 1048386067132114825L;

	public TipiServeBinary() {
	}

	public void execute(TipiEvent e) {
		String expression = (String) getEvaluatedParameterValue("expression", e);
		
		Operand newWindow = getEvaluatedParameter("newWindow", e);
		boolean newWin = false;
		if(newWindow!=null && newWindow.value!=null) {
			Boolean b = (Boolean) newWindow.value;
			newWin = b.booleanValue();
		}
			try {
			if (expression != null) {
				openBinary(expression,newWin);

			} else {
				legacyOpenBinary(e);
			}

		} catch (TipiBreakException e1) {
			e1.printStackTrace();
		} catch (TipiException e1) {
			e1.printStackTrace();
		}

	}

	private void legacyOpenBinary(TipiEvent e) {
		try {
			EchoTipiContext ee = (EchoTipiContext) myContext;
			File baseDir = ee.getDynamicResourceBaseDir();
			Operand binary = getEvaluatedParameter("binary", e);
			if (binary == null) {
				binary = getEvaluatedParameter("value", e);
			}
			URL baseUrl = null;

			System.err.println("Opening binary: " + binary.value);

			Operand baseUrlOperand = getEvaluatedParameter("baseUrl", e);
			if (baseUrlOperand != null && baseUrlOperand.value != null && !"".equals(baseUrlOperand.value)) {
				baseUrl = new URL((String) baseUrlOperand.value);
			}

			Binary b = null;
			if (binary.value instanceof URL) {
				URL u = (URL) binary.value;
				InputStream is = u.openStream();
				b = new Binary(is, false);
				is.close();
			} else {
				if (binary.value instanceof Binary) {
					b = (Binary) binary.value;
				} else {
					System.err.println("Binary class: " + binary.value.getClass());
					System.err.println(">>>>>>>>>>>>>>>>\n" + binary.value);
					b = new Binary(new StringReader((String) binary.value));
				}
			}
			if (b == null) {
				System.err.println("No binary found!");
				myContext.showInfo("can not open binary property!", "info");
				return;
			}
			String extension = b.getExtension();
			String random = new String("" + Math.random()).substring(2, 7);
			File xx = new File(baseDir, "binary" + random + "." + extension);
			FileOutputStream fos = new FileOutputStream(xx);
			b.write(fos);
			fos.flush();
			fos.close();
			URL result = null;

			if (baseUrl != null) {
				result = new URL(baseUrl, xx.getName());
			} else {
				result = ee.getDynamicResourceBaseUrl(xx.getName());
			}

			System.err.println("Resulting url: " + result);
			// URL result = new
			// URL(baseUrl.toString()+"/binary"+random+"."+extension);

			Command brc = new BrowserOpenWindowCommand(result.toString(), "reports" + random, "_blank");
			ApplicationInstance.getActive().enqueueCommand(brc);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void openBinary(String expression, boolean newWin) throws TipiBreakException, TipiException {
		System.err.println("My context: "+getContext()+" my context: "+myContext);
		EchoTipiContext ee = (EchoTipiContext) getContext();
		String result = ee.createExpressionUrl(expression);
	
		openBrowser(result,newWin);

	}

	protected void openBrowser(String url, boolean newWin) throws TipiBreakException, TipiException {
		if(newWin) {
			Command brc = new BrowserOpenWindowCommand(url,"","directories=no,location=yes,menubar=yes,personalbar=yes,resizable=yes,scrollbars=yes,status=yes,titlebar=yes,toolbar=yes, width=640,height=480");
	        ApplicationInstance.getActive().enqueueCommand(brc);
		} else {
			Command brc = new BrowserRedirectCommand(url);
	        ApplicationInstance.getActive().enqueueCommand(brc);
		}

	}
	

}
