package com.dexels.navajo.tipi.swingclient.components.remotecombobox;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;

public class AjaxComboBox extends JComboBox {
	private String messagePath;
	private String propertyName;
	private String valuePropertyName;
	private int minCharCount = 1;
	private final Vector propertyList = new Vector();

	private Message selectedMessage;

	private final Map<String,Navajo> cacheMap = new HashMap<String,Navajo>();
	private boolean syncRefresh = false;

	private Thread myRemoteRefreshThread = null;
	private long delay = 1000;

	// private RefreshFilter currentLocalRefresh = null;
	private RemoteRefreshFilter currentRemoteRefresh = null;

	private final ArrayList<ActionListener> enterEventListeners = new ArrayList<ActionListener>();
	private boolean invalidFirstItem;
	private Message loadMessage;
	
	public AjaxComboBox() {
		setEditable(true);
		final JTextComponent editor = (JTextComponent) getEditor().getEditorComponent();
		editor.setDocument(new AjaxEditorDocument(this));
		editor.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					int i = getSelectedIndex();
					if(i>0 ) {
						fireEnterEvent();
					}
				    hidePopup();
				}		
			}

		
		});
//		editor.addPropertyChangeListener(new PropertyChangeListener(){
//
//			public void propertyChange(PropertyChangeEvent evt) {
//				if(evt.getPropertyName().equals("document")) {
//					System.err.println("Property CHANGED! "+evt.getNewValue());
//					Thread.dumpStack();
//				}
//			}});
	}
	
	public Document getDocument() {
		JTextComponent editor = (JTextComponent) getEditor().getEditorComponent();
		return editor.getDocument();
	}
	
	@Override
	public void setSelectedItem(Object s) {
		final JTextComponent editor = (JTextComponent) getEditor().getEditorComponent();
		Document d = editor.getDocument();
		if(d instanceof AjaxEditorDocument) {
			AjaxEditorDocument ae = (AjaxEditorDocument)editor.getDocument();
			ae.setFireEvents(false);
			super.setSelectedItem(s);
			ae.setFireEvents(true);
		}
	}


	public void setText(String text) {
		final JTextComponent editor = (JTextComponent) getEditor().getEditorComponent();
		AjaxEditorDocument ae = (AjaxEditorDocument)editor.getDocument();
		ae.setFireEvents(false);
		editor.setText(text);
		editor.setSelectionStart(0);
		editor.setSelectionEnd(text.length());
		editor.setCaretPosition(text.length());
		ae.setFireEvents(true);
	}
	private void fireEnterEvent() {
		for (int i = 0; i < enterEventListeners.size(); i++) {
			ActionListener al = enterEventListeners.get(i);
			al.actionPerformed(new ActionEvent(this,1,"ENTER"));
		}
		System.err.println("Enter fired!");
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

	
	public void scheduleAjaxRefresh(final String overrideString, final AjaxEditorDocument d) {
		Navajo nnn = cacheMap.get(overrideString);
		d.setFireEvents(false);
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
		d.setFireEvents(true);

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
		loadMessage = m;
		final Thread t = Thread.currentThread();
		try {
			Runnable r = new Runnable() {

				public void run() {
					if(t.isInterrupted()) {
						return;
					}
					DefaultComboBoxModel model = new DefaultComboBoxModel(propertyList);
					JTextComponent textComponent = ((JTextComponent) getEditor().getEditorComponent());
					AjaxEditorDocument aed = (AjaxEditorDocument)textComponent.getDocument();
					aed.setFireEvents(false);
					setModel(model);
					textComponent.setDocument(aed);
					if (f2 == -1) {
						setSelectedIndex(0);

					} else {
						setSelectedIndex(f2);
						if (loadMessage != null) {
							selectedMessage = loadMessage;
						}
					}
					final JTextComponent ed = (JTextComponent) getEditor().getEditorComponent();
					if(ed.hasFocus()) {
						setPopupVisible(true);
					} else {
						setPopupVisible(false);
					}
//					ed.setDocument(new AjaxEditorDocument(AjaxComboBox.this));
					
					ed.setCaretPosition(ed.getText().length());
//					ed.getDocument().addDocumentListener(new DocumentListener(){
//
//						public void changedUpdate(DocumentEvent e) {
//							System.err.println("up");
//						}
//						public void insertUpdate(DocumentEvent e) {
//							System.err.println("in");
//						}
//						public void removeUpdate(DocumentEvent e) {
//							System.err.println("re");
//						}});

					aed.setFireEvents(true);
					
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
	public void setValuePropertyName(String propertyName) {
		this.valuePropertyName = propertyName;		
	}

	public Message getSelectedMessage() {
		int sel = getSelectedIndex();
		if(sel>=0) {
			sel = invalidFirstItem?sel-1:sel;
			if(sel<0) {
				return null;
			}
			Message message = loadMessage.getMessage(sel);
//			try {
//				message.write(System.err);
//			} catch (NavajoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return message;
		}
		System.err.println("huh");
		return selectedMessage;
	}
	
	public Object getSelectedValue() {
		if(getSelectedMessage()==null) {
			return null;
		}
		if(valuePropertyName==null) {
			return null;
		}
		Property pp = getSelectedMessage().getProperty(valuePropertyName);
		if(pp==null) {
			System.err.println("Warning: property does not exist");
			return null;
		}
		return pp.getTypedValue();
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
		scheduleAjaxRefresh(editor.getText(), (AjaxEditorDocument) editor.getDocument());
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

	@Override
	public void setEnabled(boolean b) {
		
	}
	
	public static void main(String[] args) throws ClientException {
		final AjaxComboBox localCombo;
		JFrame t = new JFrame("Test");

		t.setSize(400, 500);
		t.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ClientInterface cc = NavajoClientFactory.createDefaultClient();
		cc.setServerUrl("penelope1.dexels.com/sportlink/knvb/servlet/Postman");
		cc.setUsername("");
		cc.setPassword("");

		final JButton myValue = new JButton("nada");
		final Navajo init = NavajoClientFactory.getClient().doSimpleSend("club/InitSearchClubs");
		// t.getContentPane().add(new JButton("a"));
		localCombo = new AjaxComboBox();
		localCombo.setMessagePath("Club");
		localCombo.setValuePropertyName("ClubShortName");
		localCombo.setPropertyName("ClubName");
		localCombo.setDelay(500);
		localCombo.setSyncRefresh(false);
		localCombo.setText("Abcoude");
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
		
		localCombo.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent arg0) {
				System.err.println("BAAA "+localCombo.getSelectedItem()+" index: "+localCombo.getSelectedIndex());
				myValue.setText(""+localCombo.getSelectedValue());
			}});

		t.getContentPane().add(localCombo, BorderLayout.NORTH);
		t.getContentPane().add(myValue,BorderLayout.CENTER);
		t.setVisible(true);
	}
}
