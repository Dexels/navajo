package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

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

public interface TipiSwingComponent extends TipiComponent {
	public Container getSwingContainer();

	public void showPopup(MouseEvent e);

	public void setPaint(Paint p);

	public void animateTransition(TipiEvent te, TipiExecutable executableParent, List<TipiExecutable> exe, int duration)
			throws TipiBreakException;
}
