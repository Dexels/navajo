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
