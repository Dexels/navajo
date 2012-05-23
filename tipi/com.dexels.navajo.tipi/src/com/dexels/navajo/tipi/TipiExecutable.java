package com.dexels.navajo.tipi;

import java.util.List;

import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiStackElement;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/**
 * This is an interface to identify executable tipi elements, typically either
 * TipiAction derived classes, or TipiActionBlock blocks
 */
public interface TipiExecutable {
	// public void performAction(TipiEvent te) throws TipiBreakException,
	// TipiException;
	public void performAction(TipiEvent te, TipiExecutable parent, int index)
			throws TipiBreakException, TipiException, TipiSuspendException;

	// public int getExecutableChildCount();

	// public TipiExecutable getExecutableChild(int index);

	public TipiComponent getComponent();

	public void setComponent(TipiComponent c);

	public TipiEvent getEvent();

	public void setEvent(TipiEvent e);

	// public TipiEvent getEvent();

	public String getBlockParam(String key);

	public TipiStackElement getStackElement();

	public void setStackElement(TipiStackElement myStackElement);

	public void dumpStack(String message);

	public void setExecutionIndex(int i);

	public int getExecutionIndex();

	public List<TipiExecutable> getExecutables();
	public void continueAction(TipiEvent original)
			throws TipiBreakException, TipiException, TipiSuspendException ;

	public void setParent(TipiExecutable tipiAbstractExecutable);
	public TipiExecutable getParent();
	public int getExeIndex(TipiExecutable child);
}
