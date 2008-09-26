package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

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
	public void performAction(TipiEvent te, TipiExecutable parent, int index) throws TipiBreakException, TipiException;

	
	public int getExecutableChildCount();

	public TipiExecutable getExecutableChild(int index);

	public TipiComponent getComponent();

	public TipiEvent getEvent();

	public void setEvent(TipiEvent e);

	// public TipiEvent getEvent();

	public String getBlockParam(String key);

	public TipiStackElement getStackElement();

	public void setStackElement(TipiStackElement myStackElement);

	public void dumpStack(String message);
}
