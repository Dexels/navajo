/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.components.swingimpl.TipiApplet;
import com.dexels.navajo.tipi.components.swingimpl.swing.DefaultBrowser;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiOpenBrowser extends TipiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2652162769648210598L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.
	 * tipi.internal.TipiEvent)
	 */
	protected void execute(TipiEvent event) throws TipiBreakException,
			TipiException {
		Operand url = getEvaluatedParameter("url", event);
		String urlVal = (String) url.value;
		TipiApplet rr = ((SwingTipiContext) myContext).getAppletRoot();
		if (rr != null) {
			try {
				rr.getAppletContext().showDocument(new URL(urlVal), "_blank");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			DefaultBrowser.displayURL(urlVal);
		}
	}

}
