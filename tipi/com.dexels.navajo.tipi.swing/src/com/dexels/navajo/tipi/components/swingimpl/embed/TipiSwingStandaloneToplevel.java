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
			@Override
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

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		if (myPanel != null) {
			// logger.debug("Adding to toplevel: "+c.getClass()+
			// " -- "+c.hashCode());

			logger.debug("BEWArE: Tiplet hack");
			runSyncInEventThread(new Runnable() {

				@Override
				public void run() {
					// myPanel.add((Component)c,BorderLayout.CENTER);
				}
			});
		}
	}

	@Override
	public void setLayout(TipiLayout tl) {
		// no way jose
	}

	@Override
	public Object getContainerLayout() {
		return myLayout;
	}

	@Override
	public void setContainerLayout(Object o) {
		// no way jose
	}

	@Override
	public Object createContainer() {
		return myPanel;
	}

	@Override
	public Component getGlassPane() {
		return null;
	}

	@Override
	public void setGlassPane(Component glassPane) {
	}

	@Override
	public Container getContentPane() {
		return myPanel;
	}

	@Override
	public void setContentPane(Container contentPane) {
	}

	@Override
	public JLayeredPane getLayeredPane() {
		logger.debug("GETTING LAYERED PANE. BEWARE");
		return null;
	}

	@Override
	public void setLayeredPane(JLayeredPane layeredPane) {
	}

	@Override
	public JRootPane getRootPane() {
		logger.debug("GETTING ROOT PANE. BEWARE");
		return null;
	}

}
