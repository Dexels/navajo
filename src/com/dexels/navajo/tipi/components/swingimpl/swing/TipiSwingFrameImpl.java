package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;

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
public class TipiSwingFrameImpl extends JFrame implements TopLevel, TipiSwingFrame {
	// TipiContext c;

	BorderLayout borderLayout1 = new BorderLayout();

	// private TipiSwingDataComponentImpl me;

	public TipiSwingFrameImpl(TipiSwingDataComponentImpl me) {
		// final TipiSwingFrameImpl tsf = this;
		setVisible(false);
		getContentPane().setLayout(borderLayout1);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void setIconImage(ImageIcon i) {
		super.setIconImage(i.getImage());
	}

	public void setTipiMenubar(final TipiMenubar tm) {
		setJMenuBar((JMenuBar) tm.getContainer());
	}

	public void setModal(boolean b) {
		// ignored for frames
	}

}
