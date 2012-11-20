package com.dexels.navajo.tipi.components.swingimpl.embed;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;
import com.dexels.navajo.tipi.internal.TipiLayout;

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

public class TipiSwingStandaloneToplevel extends TipiPanel implements
		RootPaneContainer {

	private static final long serialVersionUID = -4454167651998605776L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingStandaloneToplevel.class);
	
	private JComponent myPanel; // = new JPanel();
	private final BorderLayout myLayout = new BorderLayout();

	public TipiSwingStandaloneToplevel(final JComponent jj) {
		// myPanel.setLayout(myLayout);
		super.setName("init");
		runSyncInEventThread(new Runnable() {
			public void run() {
				if (jj == null) {
					myPanel = new JPanel();
					myPanel.setLayout(new BorderLayout());
				} else {
					myPanel = jj;
				}
				setId("init");
				initContainer();
			}
		});
	}

	public TipiSwingStandaloneToplevel() {
		this(null);
	}

	public void addToContainer(final Object c, final Object constraints) {
		if (myPanel != null) {
			// logger.debug("Adding to toplevel: "+c.getClass()+
			// " -- "+c.hashCode());

			logger.debug("BEWArE: Tiplet hack");
			runSyncInEventThread(new Runnable() {

				public void run() {
					// myPanel.add((Component)c,BorderLayout.CENTER);
				}
			});
		}
	}

	public void setLayout(TipiLayout tl) {
		// no way jose
	}

	public Object getContainerLayout() {
		return myLayout;
	}

	public void setContainerLayout(Object o) {
		// no way jose
	}

	public Object createContainer() {
		return myPanel;
	}

	public Component getGlassPane() {
		return null;
	}

	public void setGlassPane(Component glassPane) {
	}

	public Container getContentPane() {
		return myPanel;
	}

	public void setContentPane(Container contentPane) {
	}

	public JLayeredPane getLayeredPane() {
		logger.debug("GETTING LAYERED PANE. BEWARE");
		return null;
	}

	public void setLayeredPane(JLayeredPane layeredPane) {
	}

	public JRootPane getRootPane() {
		logger.debug("GETTING ROOT PANE. BEWARE");
		return null;
	}

}
