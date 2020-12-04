/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.TipiComponent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public interface MessageComponent extends TipiComponent {

	public String getMessageName();

	// Don't know if I'll need those:
	// public void addTipiEventListener(TipiEventListener listener);
	// public void addTipiEvent(TipiEvent te);

	public Message getMessage();

	public void setMessage(Message m);

	public int getMessageIndex();
}
