package com.dexels.navajo.tipi.jxlayer;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.effect.BufferedImageOpEffect;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;
import com.dexels.navajo.tipi.jxlayer.impl.BusyPainterUI;
import com.jhlabs.image.BlurFilter;

public class TipiLockedPanel extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = 8221840436194974384L;
	private JPanel myPanel;
	private LockableUI blurUI;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiLockedPanel.class);
	
	@Override
	public Object createContainer() {
		myPanel = new JPanel();
		JXLayer<JComponent> layer = new JXLayer<JComponent>(myPanel);
	     blurUI = getLockableUI("blur");
        
	
		layer.setUI(blurUI);
		blurUI.setLockedCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		return layer;
	}

	@Override
	public void removeFromContainer(final Object c) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				myPanel.remove((Component) c);
			}
		});
	}

	@Override
	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				myPanel.setLayout((LayoutManager) layout);
			}
		});
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				myPanel.add((Component) c, constraints);
			}
		});
	}

	@Override
	protected void setComponentValue(String name, final Object object) {
		super.setComponentValue(name, object);
		if (name.equals("lock")) {
			final boolean lck = ((Boolean) object).booleanValue();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					blurUI.setLocked(lck);
					logger.info("Setting lock: "+lck);
				
				}
			});
			
	
		}
	}
	
	
	private LockableUI getLockableUI(String effect) {
		if(effect==null) {
			effect="busy";
		}
		if(effect.equals("blur")) {
			return new LockableUI(new BufferedImageOpEffect(new BlurFilter()));
		}
		if(effect.equals("busy")) {
			logger.info("Busy detected!");
			return new BusyPainterUI();
		}
		return getLockableUI("blur");
	}
	


}
