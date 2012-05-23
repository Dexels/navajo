package com.dexels.navajo.tipi.vaadin.application.eval;

import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

import tipi.TipiApplicationInstance;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.ParameterHandler;
import com.vaadin.terminal.URIHandler;

public class EvalHandler implements URIHandler, ParameterHandler {

	private static final long serialVersionUID = 2451838360127835405L;
	private Map<String, String[]> parameters = null;
	private final TipiApplicationInstance application;

	public EvalHandler(TipiApplicationInstance tai) {
		this.application = tai;
	}

	@Override
	public void handleParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;

	}

	@Override
	public DownloadStream handleURI(URL context, String relativeUri) {
		application.setEvalUrl(context,relativeUri);
		application.setContextUrl(context);
		if (!relativeUri.startsWith("eval")) {
			System.err.println("Not matching: " + relativeUri);
		}

		if (parameters == null) {
			return null;
		}
		String[] evals = parameters.get("evaluate");
		if (evals==null || evals.length != 1) {
			return null;
		}

		try {
			String eval = evals[0];
			String decoded = URLDecoder.decode(eval, "UTF-8");
			Operand o = null;
			o = application.getCurrentContext().evaluate(decoded, application.getCurrentContext().getDefaultTopLevel(),
					null);
			if (o == null) {
				return null;
			} else {
				if (o.value instanceof Binary) {
					Binary b = (Binary) o.value;
					String contentType = b.guessContentType();
					InputStream is = b.getDataAsStream();
					DownloadStream downloadStream = new DownloadStream(is, contentType, "file." + b.getExtension());
					if (contentType != null && contentType.indexOf("html") == -1) {
						downloadStream.setParameter("Content-Disposition", "attachment; filename=file." + b.getExtension());
					}
					return downloadStream;
				} else {
					System.err.println("Not evaluating non binary.");
					return null;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
