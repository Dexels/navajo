/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.Container;
import java.io.File;

import javax.swing.JFileChooser;

import com.dexels.navajo.document.Operand;
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
public class TipiAskFile extends TipiAction {

	private static final long serialVersionUID = -18547321878009976L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.
	 * tipi.internal.TipiEvent)
	 */
	@Override
	protected void execute(TipiEvent event) throws TipiBreakException,
			TipiException {
		Operand globalvalue = getEvaluatedParameter("value", event);
		JFileChooser jf = new JFileChooser();
		Object o = getComponent().getContainer();
		Container c = null;
		if (o instanceof Container) {
			c = (Container) o;
		}
		try {
			jf.setCurrentDirectory(new File(System.getProperty("user.home")));
		} catch (SecurityException e) {
			throw new TipiException("No file access allowed. Sorry.");
		}
		int result = jf.showSaveDialog(c);
		if (result != JFileChooser.APPROVE_OPTION) {
			throw new TipiBreakException(0);
		}
		File f = jf.getSelectedFile();

		myContext.setGlobalValue((String) globalvalue.value,
				"" + f.getAbsolutePath());
	}

}
