package com.dexels.navajo.tipi.jxlayer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.plaf.effect.*;
import org.jdesktop.jxlayer.plaf.ext.*;


import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.jxlayer.impl.*;
import com.jhlabs.image.*;

public class TipiLockedPanel extends TipiSwingDataComponentImpl {

	private JPanel myPanel;
	private LockableUI blurUI;

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
			public void run() {
				myPanel.remove((Component) c);
			}
		});
	}

	@Override
	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				myPanel.setLayout((LayoutManager) layout);
			}
		});
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		runSyncInEventThread(new Runnable() {
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
				public void run() {
					blurUI.setLocked(lck);
					System.err.println("Setting lock: "+lck);
				
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
			System.err.println("Busy detected!");
			return new BusyPainterUI();
		}
		return getLockableUI("blur");
	}
	


}
