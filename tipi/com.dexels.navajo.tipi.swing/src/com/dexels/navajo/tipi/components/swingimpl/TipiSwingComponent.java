package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.util.List;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiExecutable;
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

public interface TipiSwingComponent extends TipiComponent {
	public Container getSwingContainer();

	public void showPopup(MouseEvent e);

	public void setPaint(Paint p);

	public void animateTransition(TipiEvent te,
			TipiExecutable executableParent, List<TipiExecutable> exe,
			int duration) throws TipiBreakException;
}
