package com.dexels.navajo.tipi.swingclient.components.remotecombobox;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;

public class AjaxComboBox extends JComboBox {
	private String messagePath;
	private String propertyName;
	private int minCharCount = 1;
	private final Vector propertyList = new Vector();

	private Message selectedMessage;

	private final Map cacheMap = new HashMap();
	private boolean syncRefresh = false;

	private Thread myRemoteRefreshThread = null;
	private long delay = 1000;

	// private RefreshFilter currentLocalRefresh = null;
	private RemoteRefreshFilter currentRemoteRefresh = null;

	private final ArrayList enterEventListeners = new ArrayList();
	private boolean invalidFirstItem;
	
	public AjaxComboBox() {
		setEditable(true);
		final JTextComponent editor = (JTextComponent) getEditor().getEditorComponent();

		editor.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {

				if (!e.isActionKey() || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					// if (propertyList.isEmpty()) {
					fireAjaxRefresh(editor.getText());
					// } else {
					// fireLocalRefresh(editor.getText());
					// }

				}
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					int i = getSelectedIndex();
					// Not checking for invalid first items
					if(i>0 ) {
						fireEnterEvent();
					}
				    hidePopup();
				}
			}

			public void keyTyped(KeyEvent e) {
			
			}

		
		});
		// editor.getDocument().addDocumentListener(d);

	}
	private void fireEnterEvent() {
		for (int i = 0; i < enterEventListeners.size(); i++) {
			ActionListener al = (ActionListener)enterEventListeners.get(i);
			al.actionPerformed(new ActionEvent(this,1,"ENTER"));
		}
		
	}
	public String getValue() {
		if (getSelectedItem() != null) {
			return getSelectedItem().toString();
		} else {
			return null;
		}
	}

	public void addEnterEventListener(ActionListener al) {
		enterEventListeners.add(al);
		
	}

	public void removeEnterEventListener(ActionListener al) {
		enterEventListeners.remove(al);
	}

	
	protected void fireAjaxRefresh(final String overrideString) {
		Navajo nnn = (Navajo) cacheMap.get(overrideString);
		if (nnn != null) {
			loadData(nnn, overrideString);
			return;
		}
		Runnable r = new Runnable() {

			// @Override
			public void run() {
				if (!syncRefresh) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						return;
					}

				}
				synchronized (this) {
					if(Thread.interrupted()) {
						return;
					}


					if (overrideString == null || overrideString.length() < minCharCount) {
						loadData(null, overrideString);

					} else {

						Navajo nn = currentRemoteRefresh.getNavajo(overrideString);

						if(Thread.interrupted()) {
							return;
						}
						if(nn==null) {
							// This happens in tipi. It will call loadData in its own time
							return;
						}
						loadData(nn, overrideString);
					}
					myRemoteRefreshThread = null;
				}

			}
		};
		if (syncRefresh) {
			r.run();
		} else {
			if (myRemoteRefreshThread != null) {
				myRemoteRefreshThread.interrupt();
			}
			myRemoteRefreshThread = new Thread(r);
			myRemoteRefreshThread.start();
		}
	}

	public void loadData(Navajo n, String select) {
		cacheMap.put(select, n);
		propertyList.clear();
		Message m = null;
		int found = -1;

		if (n != null) {
			m = n.getMessage(messagePath);
			ArrayList ll = m.getAllMessages();
			for (int i = 0; i < ll.size(); i++) {
				Message current = (Message) ll.get(i);
				Property pp = current.getProperty(propertyName);
				if (select.equals(pp.getValue())) {
					found = i;
				}
				propertyList.add(pp.getValue());
			}
		}

		if (found == -1) {
			propertyList.insertElementAt(select, 0);
			invalidFirstItem = true;
		} else {
			invalidFirstItem = false;
		}
		final int f2 = found;
		final Message loadMessage = m;
		final Thread t = Thread.currentThread();
		try {
			Runnable r = new Runnable() {

				public void run() {
					if(t.isInterrupted()) {
						return;
					}
					setModel(new DefaultComboBoxModel(propertyList));
					if (f2 == -1) {
						setSelectedIndex(0);

					} else {
						setSelectedIndex(f2);
						if (loadMessage != null) {
							selectedMessage = loadMessage;
						}
					}
					setPopupVisible(true);
				}
			};
			if (SwingUtilities.isEventDispatchThread()) {
				r.run();
			} else {
				SwingUtilities.invokeAndWait(r);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public String getMessagePath() {
		return messagePath;
	}

	/**
	 * The message path to the array message
	 * 
	 * @param messagePath
	 */

	public void setMessagePath(String messagePath) {
		this.messagePath = messagePath;
	}

	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * The property name within the array message element.
	 * 
	 * @param propertyName
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Message getSelectedMessage() {
		return selectedMessage;
	}

	public long getDelay() {
		return delay;
	}

	/**
	 * Clears the cache and updates the combo
	 *
	 */
	public void flushCache() {
		cacheMap.clear();
		final JTextComponent editor = (JTextComponent) getEditor().getEditorComponent();
		fireAjaxRefresh(editor.getText());
	}
	/**
	 * Delay is only relevant when syncRefresh = false The delay before a
	 * refresh will be executed. When another refresh occurs, waiting ones will
	 * be aborted.
	 * 
	 * @param delay
	 *            delay in millis
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	public boolean isSyncRefresh() {
		return syncRefresh;
	}

	/**
	 * Perform the remote refresh in the event thread. Only set to true if you
	 * are sure the refresh will be very quick.
	 * 
	 * @param syncRefresh
	 */
	public void setSyncRefresh(boolean syncRefresh) {
		this.syncRefresh = syncRefresh;
	}

	// public RefreshFilter getCurrentLocalRefresh() {
	// return currentLocalRefresh;
	// }
	//
	// public void setCurrentLocalRefresh(RefreshFilter currentLocalRefresh) {
	// this.currentLocalRefresh = currentLocalRefresh;
	// }

	public RemoteRefreshFilter getCurrentRemoteRefresh() {
		return currentRemoteRefresh;
	}

	public void setCurrentRemoteRefresh(RemoteRefreshFilter currentRemoteRefresh) {
		this.currentRemoteRefresh = currentRemoteRefresh;
	}

	public void setEnabled(boolean b) {
		
	}
	
	public static void main(String[] args) throws ClientException {
		AjaxComboBox localCombo;
		JFrame t = new JFrame("Test");

		t.setSize(400, 200);
		t.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ClientInterface cc = NavajoClientFactory.createDefaultClient();
		cc.setServerUrl("penelope1.dexels.com/sportlink/knvb/servlet/Postman");
		cc.setUsername("");
		cc.setPassword("");

		final Navajo init = NavajoClientFactory.getClient().doSimpleSend("club/InitSearchClubs");
		// t.getContentPane().add(new JButton("a"));
		localCombo = new AjaxComboBox();
		localCombo.setMessagePath("Club");
		localCombo.setPropertyName("ClubName");
		localCombo.setDelay(1000);
		localCombo.setSyncRefresh(false);
		localCombo.setCurrentRemoteRefresh(new RemoteRefreshFilter() {

			public Navajo getNavajo(String filterString) {
				Property p = init.getProperty("ClubSearch/SearchName");
				p.setValue(filterString);
				Navajo nn;
				try {
					nn = NavajoClientFactory.getClient().doSimpleSend(init, "club/ProcessSearchClubs");
					return nn;
				} catch (ClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});

		t.getContentPane().add(localCombo, BorderLayout.NORTH);
		t.setVisible(true);
	}
}
