package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

import com.dexels.navajo.dnd.BinaryTransferHandler;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingButton;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingDropButton;
import com.dexels.navajo.tipi.internal.TipiEvent;


public class TipiDropButton extends TipiSwingComponentImpl {

	private boolean iAmEnabled = true;
	
	private AbstractAction buttonAction;
	public Object createContainer() {
		final TipiSwingDropButton myButton = new TipiSwingDropButton(this);
		buttonAction = new AbstractAction("onActionPerformed"){

			public void actionPerformed(ActionEvent e) {
				doFireAction(myButton);
			}};
		myButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				buttonAction.actionPerformed(e);
			}});
		myButton.setTransferHandler(new BinaryTransferHandler());
		return myButton;
	}

	public void setBinaryValue(final Binary b) {
		if(b==null) {
			System.err.println("huh? Null binary");
			return;
		}

		Map<String, Object> event = new HashMap<String, Object>();
		event.put("value",b);
		event.put("path",b.getFile());
		event.put("mime",b.getMimeType());
		
		try {
			performTipiEvent("onBinaryDropped", event, false);
		} catch (TipiBreakException e) {
			e.printStackTrace();
		} catch (TipiException e) {
			e.printStackTrace();
		}

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
							((JButton)getContainer()).setIcon(getIcon(object));
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



	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
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
	public void eventStarted(TipiExecutable te, Object event) {
		if (Container.class.isInstance(getContainer())) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					getSwingContainer().setEnabled(false);
				}
			});
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

	private void doFireAction(final JComponent myButton) {
		final JRootPane root = myButton.getRootPane();
		setWaitCursor(true,root);
			
			performTipiEvent("onActionPerformed", null, false
			, new Runnable(){
				public void run() {
					setWaitCursor(false,root);
				}});
	
	}
}
