package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;

import org.jdesktop.animation.transitions.*;
import org.jdesktop.animation.transitions.EffectsManager.*;
import org.jdesktop.animation.transitions.effects.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;

import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

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
//	private TipiSwingButton myButton;

	private boolean iAmEnabled = true;
	private AbstractAction buttonAction;

	public Object createContainer() {
		TipiSwingButton myButton = new TipiSwingButton();
		buttonAction = new AbstractAction("onActionPerformed"){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					performTipiEvent("onActionPerformed", null, false);
				} catch (TipiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}};
		myButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				buttonAction.actionPerformed(e);
			}});
		
		return myButton;
	}

	public final void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
		runSyncInEventThread(new Runnable() {
			public void run() {
				if (name.equals("text")) {
					((JButton)getContainer()).setText((String) object);
				}
				if (name.equals("icon")) {
					if (object == null) {
						System.err.println("Ignoring null icon");
					} else {
						if (object instanceof URL) {
							((JButton)getContainer()).setIcon(getIcon(object));
						}
					}
				}
				if (name.equals("enabled")) {
					// Just for the record.
					if (object==null) {
						iAmEnabled = false;
					} else {
						iAmEnabled = ((Boolean) object).booleanValue();
					}
				}
				if (name.equals("accelerator")) {
					setAccelerator((String)object);
				}
			}
		});
	}

	 protected void setAccelerator(String text) {
	        KeyStroke ks = null;
	       JButton myButton = (JButton)getContainer();
	        InputMap imap = myButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	        ActionMap amap = myButton.getActionMap();
	        ks = KeyStroke.getKeyStroke(text);
	        imap.put(ks, buttonAction.getValue(Action.NAME));
	        amap.put(buttonAction.getValue(Action.NAME), buttonAction);		
					
	}

	protected ImageIcon getIcon(Object u) {
		 if(u==null) {
			 return null;
		 }
		 if(u instanceof URL) {
			   return new ImageIcon((URL) u);
		 }
		 if(u instanceof Binary) {
			 Image i;
			try {
				i = ImageIO.read(((Binary) u).getDataAsStream());
				 ImageIcon ii = new ImageIcon(i);
				 return ii;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 return null;
	  }

	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return ((JButton)getContainer()).getText();
		}
		return super.getComponentValue(name);
	}

	// private boolean enabled = false;
	public void eventStarted(TipiExecutable te, Object event) {
		if (Container.class.isInstance(getContainer())) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					// enabled = ( (Container) getContainer()).isEnabled();
					getSwingContainer().setEnabled(false);
				}
			});
		}
	}

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
		if ("fireAction".equals(name)) {
			for (int i = 0; i < getEventList().size(); i++) {
				final int j = i;
				TipiEvent current = getEventList().get(j);
				if (current.isTrigger("onActionPerformed", "aap")) {
					try {
						myContext.execute(new Runnable() {

							public void run() {
								TipiEvent c2 = getEventList().get(j);
								try {
									c2.performAction(c2, c2, 0);
								} catch (TipiException ex) {
									ex.printStackTrace();
								} catch (TipiBreakException e) {
									e.printStackTrace();
								}
							}
						});
					} catch (TipiException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void eventFinished(TipiExecutable te, Object event) {
		if (Container.class.isInstance(getContainer())) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					((Container) getContainer()).setEnabled(iAmEnabled);
				}
			});
		}
	}
}
