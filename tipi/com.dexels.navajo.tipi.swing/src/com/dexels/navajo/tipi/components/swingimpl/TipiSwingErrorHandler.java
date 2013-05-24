package com.dexels.navajo.tipi.components.swingimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.internal.BaseTipiErrorHandler;


@Deprecated
public class TipiSwingErrorHandler extends BaseTipiErrorHandler {

	private static final long serialVersionUID = 7477014764317028204L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingErrorHandler.class);
	
	public TipiSwingErrorHandler() {
		// setContainer(createContainer);
	}

	public void showError() {
		TipiContext c = getContext();
		if (c != null) {
			showErrorDialog(getErrorMessage());
		} else {
			logger.debug("DefaultTipiErrorHandler, context not set!! ");
		}
	}

	public void showError(String text) {
		showErrorDialog(text);
	}

	public void showError(Exception e) {
		TipiContext c = getContext();
		c.showError(e.getMessage(), "", null);
	}

	public void showErrorDialog(final String error) {
		getContext().showError(error, "", null);
	}
	// public Object createContainer(){
	// return new JPanel();
	// }
}
