package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.components.remotecombobox.*;

public class EditableComboBox extends JComboBox {

	private final ArrayList enterEventListeners = new ArrayList();
	private boolean invalidFirstItem;
	private String currentText;
	private Selection currentSelection = null;
	private Property currentProperty;
	private Object selectedSelection;

	public EditableComboBox() {
		super();
		super.setEditable(true);

		
		final JTextComponent editor = (JTextComponent) getEditor().getEditorComponent();
		editor.getDocument().addDocumentListener(new DocumentListener(){

			public void changedUpdate(DocumentEvent arg0) {
				updateListLater();
			}

			public void insertUpdate(DocumentEvent arg0) {
				updateListLater();
			}

			public void removeUpdate(DocumentEvent arg0) {
				updateListLater();
				
			}});
		
			editor.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(final KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable(){

					public void run() {
						final JTextComponent editor = (JTextComponent) getEditor().getEditorComponent();
						currentText = editor.getText();
						//		

						
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							((EditableComboBoxModel) getModel()).commit(selectedSelection);
//							fireItemStateChanged();
							
//							System.err.println("AAA: "+ getSelectedItem());
							editor.setText(""+selectedSelection);
							setPopupVisible(false);
							
							transferFocus();
							return;
						}
						if (!e.isActionKey() || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
						}
						
						
					}});
			
			}

			public void keyTyped(KeyEvent e) {
			}

		});
	}

	
	public void updateListLater() {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				updateList();
			}});
	}
	public void updateList() {
		selectedSelection = null;
		final JTextComponent editor = (JTextComponent) getEditor().getEditorComponent();
		String currentText = editor.getText();		
		if(currentText==null) {
			return;
		}
		
//		System.err.println("NONACTION! " + KeyEvent.getKeyText(e.getKeyCode()));
		try {
			((EditableComboBoxModel) getModel()).updateVisibleList(currentText);
		} catch (NavajoException ex) {
			ex.printStackTrace();
		}
		if (isShowing()) {
			setPopupVisible(true);
		} else {
			System.err.println("NOPE!");
		}
		
		setMaximumRowCount(10);
		showPopup();
		if(getComponentPopupMenu()!=null) {
//			getComponentPopupMenu().revalidate();
			Component c = getComponentPopupMenu().getComponent(0);
			System.err.println("CLAZZ: "+c.getClass());
		} else {
			System.err.println("Bongobong"+getEditor().getEditorComponent().getClass());
		}
		((JTextField)getEditor().getEditorComponent()).setSelectionStart(0);
		((JTextField)getEditor().getEditorComponent()).setSelectionEnd(0);
		((JTextField)getEditor().getEditorComponent()).setCaretPosition(currentText.length());

	}
	
	//public void
	
	public void setProperty(Property p) {
		currentProperty = p;
		EditableComboBoxModel ecmb = new EditableComboBoxModel(p);
		setModel(ecmb);
		try {
			ecmb.updateVisibleList("");
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addItemListener(new ItemListener(){


			public void itemStateChanged(ItemEvent ie) {
				if(ie.getStateChange()==ItemEvent.SELECTED) {
					System.err.println("AFFE: "+getSelectedItem()+" == "+ie.getID()+" st: "+ie.getStateChange()+" param: "+ie.paramString());
					
					if(selectedSelection instanceof Selection) {
						
						selectedSelection = getSelectedItem();
					}
					try {
						Selection sss = currentProperty.getSelection(""+getSelectedItem());
						if(sss!=null&& !Selection.DUMMY_SELECTION.equals(sss.getName())) {
							System.err.println("Yessssssssssssss");
							selectedSelection = sss;
						}
					} catch (NavajoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}});
	}

	public Property getProperty() {
		return currentProperty;
	}

	public void setEnabled(boolean b) {

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

	public static void main(String[] args) throws ClientException, NavajoException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		EditableComboBox localCombo;
	//	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame t = new JFrame("Test");

		t.setSize(400, 200);
		t.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ClientInterface cc = NavajoClientFactory.createDefaultClient();
		cc.setServerUrl("penelope1.dexels.com/sportlink/knvb/servlet/Postman");
		cc.setUsername("");
		cc.setPassword("");

		final Navajo init = NavajoClientFactory.getClient().doSimpleSend("club/InitUpdateClub");
		// init.write(System.err);

		// t.getContentPane().add(new JButton("a"));
		Property pp = init.getProperty("Club/ClubIdentifier");
		pp.setValue("BBFW63X");
		Navajo pr = NavajoClientFactory.getClient().doSimpleSend(init, "club/ProcessQueryClub");
		// pr.write(System.err);
		localCombo = new EditableComboBox();

		Property result = pr.getProperty("ClubData/TypeOfAddress");
		localCombo.setProperty(result);
		// localCombo.setMessagePath("Club");
		// localCombo.setPropertyName("ClubName");
		// localCombo.setDelay(1000);
		// localCombo.setSyncRefresh(false);
		// localCombo.setCurrentRemoteRefresh(new RemoteRefreshFilter() {
		//
		// public Navajo getNavajo(String filterString) {
		// Property p = init.getProperty("ClubSearch/SearchName");
		// p.setValue(filterString);
		// Navajo nn;
		// try {
		// nn = NavajoClientFactory.getClient().doSimpleSend(init,
		// "club/ProcessSearchClubs");
		// return nn;
		// } catch (ClientException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return null;
		// }
		// });

		JToolBar tb = new JToolBar();
		t.getContentPane().add(tb, BorderLayout.SOUTH);
		tb.add(localCombo);
		
		t.getContentPane().add(new JButton("Zomaar wat"),BorderLayout.CENTER);
		t.setVisible(true);
	}
}
