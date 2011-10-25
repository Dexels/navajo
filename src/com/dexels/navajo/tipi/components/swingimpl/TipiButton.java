package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingButton;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class TipiButton extends TipiSwingComponentImpl {

	private static final long serialVersionUID = -6376459170529922811L;

	private boolean iAmEnabled = true;

	private AbstractAction buttonAction;

	public Object createContainer() {
		final TipiSwingButton myButton = new TipiSwingButton(this);
		buttonAction = new AbstractAction("onActionPerformed") {

			private static final long serialVersionUID = 706723341030407319L;

			public void actionPerformed(ActionEvent e) {
				doFireAction(myButton);
			}
		};
		myButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				buttonAction.actionPerformed(e);
			}
		});

		return myButton;
	}

	public final void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
		runSyncInEventThread(new Runnable() {
			public void run() {
				if (name.equals("text")) {
					((JButton) getContainer()).setText((String) object);
				}
				if (name.equals("icon")) {
					if (object == null) {
						System.err.println("Ignoring null icon");
					} else {
						((JButton) getContainer()).setIcon(getIcon(object));
					}
				}
				if (name.equals("enabled")) {
					// Just for the record.
					if (object == null) {
						iAmEnabled = false;
					} else {
						iAmEnabled = ((Boolean) object).booleanValue();
					}
				}
				if (name.equals("accelerator")) {
					setAccelerator((String) object);
				}
			}
		});
	}

	protected void setAccelerator(String text) {
		KeyStroke ks = null;
		JButton myButton = (JButton) getContainer();
		InputMap imap = myButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap amap = myButton.getActionMap();
		ks = KeyStroke.getKeyStroke(text);
		imap.put(ks, buttonAction.getValue(Action.NAME));
		amap.put(buttonAction.getValue(Action.NAME), buttonAction);

	}

	protected ImageIcon getIcon(Object u) {
		if (u == null) {
			return null;
		}
		if (u instanceof URL) {
			return new ImageIcon((URL) u);
		}
		if (u instanceof Binary) {
			Image i;
			try {
				i = ImageIO.read(((Binary) u).getDataAsStream());
				ImageIcon ii = new ImageIcon(i);
				return ii;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return ((JButton) getContainer()).getText();
		}
		return super.getComponentValue(name);
	}

	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event) {
		if ("fireAction".equals(name)) {
			for (int i = 0; i < getEventList().size(); i++) {
				final int j = i;
				TipiEvent current = getEventList().get(j);
				if (current.isTrigger("onActionPerformed")) {
					doFireAction((TipiSwingButton) getSwingContainer());
				}
			}
		}
	}

	// private boolean enabled = false;
	public void eventStarted(TipiExecutable te, Object event) {
		if (Container.class.isInstance(getContainer())) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					// enabled = ( (Container) getContainer()).isEnabled();
					getSwingContainer().setEnabled(false);
					// setCursor(Cursor.WAIT_CURSOR);
				}
			});
		}
	}

	public void eventFinished(TipiExecutable te, Object event) {
		if (Container.class.isInstance(getContainer())) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					((Container) getContainer()).setEnabled(iAmEnabled);
					// setCursor(Cursor.DEFAULT_CURSOR);
				}
			});
		}
	}

	private void doFireAction(final JComponent myButton) {
		final JRootPane root = myButton.getRootPane();
		setWaitCursor(true, root);

		performTipiEvent("onActionPerformed", null, false, new Runnable() {
			public void run() {
				setWaitCursor(false, root);
			}
		});

	}
}
