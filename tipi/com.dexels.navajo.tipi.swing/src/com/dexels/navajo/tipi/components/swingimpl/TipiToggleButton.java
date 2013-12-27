package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;

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

public class TipiToggleButton extends TipiSwingComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8661024473629453816L;

	private JToggleButton myButton;

	private boolean iAmEnabled = true;

	@Override
	public Object createContainer() {
		myButton = new JToggleButton();
		myButton.setOpaque(false);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myButton;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				if (name.equals("text")) {
					myButton.setText((String) object);
				}
				if (name.equals("icon")) {
					myButton.setIcon(getIcon((URL) object));
				}
				if (name.equals("enabled")) {
					// Just for the record.
					iAmEnabled = ((Boolean) object).booleanValue();
				}
				if (name.equals("selected")) {
					myButton.setSelected(((Boolean) object).booleanValue());
				}
				if (name.equals("buttongroup")) {
					TipiButtonGroup group = (TipiButtonGroup) object;
					group.getButtonGroup().add(myButton);
				}
			}
		});
	}

	private ImageIcon getIcon(URL u) {
		return new ImageIcon(u);
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return myButton.getText();
		}
		if (name.equals("selected")) {
			return new Boolean(myButton.isSelected());
		}

		return super.getComponentValue(name);
	}

	// private boolean enabled = false;
	@Override
	public void eventStarted(TipiExecutable te, Object event) {
		if (Container.class.isInstance(getContainer())) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					// enabled = ( (Container) getContainer()).isEnabled();
					getSwingContainer().setEnabled(false);
				}
			});
		}
	}

	@Override
	public void eventFinished(TipiExecutable te, Object event) {
		if (Container.class.isInstance(getContainer())) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					((Container) getContainer()).setEnabled(iAmEnabled);
				}
			});
		}
	}

}
