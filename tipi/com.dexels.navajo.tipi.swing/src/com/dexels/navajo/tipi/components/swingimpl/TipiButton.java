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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiButton.class);
	
	private boolean iAmEnabled = true;

	private AbstractAction buttonAction;

	@Override
	public Object createContainer() {
		final TipiSwingButton myButton = new TipiSwingButton(this);
		 myButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logger.info("Button clicked");
                    doFireAction(myButton);
            }
        });

		return myButton;
	}

	@Override
	public final void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				if (name.equals("text")) {
					((JButton) getContainer()).setText((String) object);
				}
				if (name.equals("icon")) {
					((JButton) getContainer()).setIcon(getIcon(object));
				}
				if (name.equals("borderVisible")) {
				    ((JButton) getContainer()).setBorderPainted(false);
                    ((JButton) getContainer()).setBorder(null);
                    ((JButton) getContainer()).setContentAreaFilled(false);
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
				logger.error("Error detected",e);
			}
		}
		return null;
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return ((JButton) getContainer()).getText();
		}
		return super.getComponentValue(name);
	}

	@Override
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
	@Override
	public void eventStarted(TipiExecutable te, Object event) {
		// Disabling happened at the doFireAction already. However, just to be sure,
	    // we disable it again in case we got here from elsewhere
	    if (Container.class.isInstance(getContainer())) {
            runSyncInEventThread(new Runnable() {
                @Override
                public void run() {
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

	private void doFireAction(final JComponent myButton) {
		final JRootPane root = myButton.getRootPane();
		setWaitCursor(true, root);

		// Disable button straight away to prevent duplicate clicks
        if (Container.class.isInstance(getContainer())) {
            runSyncInEventThread(new Runnable() {
                @Override
                public void run() {
                    getSwingContainer().setEnabled(false);
                }
            });
        }
        logger.info("About to call onActionPerformed" );
		performTipiEvent("onActionPerformed", null, false, new Runnable() {
			@Override
			public void run() {
			    logger.info("Finished onActionPerformed!");
				setWaitCursor(false, root);
			}
		});

	}
}
