package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.SelectFieldEx;

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
public class TipiAjaxCombobox extends TipiEchoDataComponentImpl {

	private static final long serialVersionUID = 1271208183816280271L;

	private SelectFieldEx myCombo;

//	private String currentSelection = null;

	public Object createContainer() {
		myCombo = new SelectFieldEx();
//		TipiHelper th = new TipiE();
//		th.initHelper(this);
//		addHelper(th);
//		myCombo.setCurrentRemoteRefresh(new RemoteRefreshFilter() {
//			public Navajo getNavajo(String filterString) {
//				currentSelection = filterString;
//				Map m = new HashMap();
//				m.put("text", currentSelection);
//				try {
//					performTipiEvent("onChange", m, true);
//				} catch (TipiException e) {
//					e.printStackTrace();
//				}
//				return null;
//			}
//		});
		myCombo.setVisible(true);
		
//		myCombo.addItemListener(new ItemListener() {
//
//			public void itemStateChanged(ItemEvent arg0) {
//
//				try {
//					String sel = (String) myCombo.getSelectedItem();
//					Map m = new HashMap();
//					m.put("value", sel);
//					performTipiEvent("onSelect", m, false);
//				} catch (TipiException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//
//		myCombo.addEnterEventListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					myCombo.hidePopup();
//					String sel = (String) myCombo.getSelectedItem();
//					Map m = new HashMap();
//					m.put("value", sel);
//					performTipiEvent("onEnter", m, false);
//				} catch (TipiException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		
		
		
//		final JTextComponent editor = (JTextComponent) (myCombo.getEditor().getEditorComponent());

		// does not appear to work...
//		editor.addFocusListener(new FocusListener(){
//			public void focusGained(FocusEvent arg0) {
//				
//			}
//
//			public void focusLost(FocusEvent arg0) {
//				SwingUtilities.invokeLater(new Runnable(){
//
//					public void run() {
//						myCombo.hidePopup();
//						System.err.println("njama");
//					}});
//			}});
		return myCombo;
	}

	// localCombo = new AjaxComboBox();
	// localCombo.setMessagePath("Club");
	// localCombo.setPropertyName("ClubName");
	// localCombo.setDelay(1000);
	// localCombo.setSyncRefresh(false);
	// localCombo.setCurrentRemoteRefresh(new RemoteRefreshFilter() {

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		// myCombo.loadData(n, currentSelection);
	}

	public final void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
//		if (name.equals("messagePath")) {
//			myCombo.setMessagePath((String) object);
//		}
//		if (name.equals("propertyName")) {
//			myCombo.setPropertyName((String) object);
//		}
//		if (name.equals("syncRefresh")) {
//			myCombo.setSyncRefresh(((Boolean) object).booleanValue());
//		}
//		if (name.equals("refreshDelay")) {
//			myCombo.setDelay(((Integer) object).intValue());
//		}
	}

	
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
		if ("updateCombo".equals(name)) {
//			String evalText = (String) compMeth.getEvaluatedParameter("text", event).value;
//			Navajo evalNavajo = (Navajo) compMeth.getEvaluatedParameter("navajo", event).value;
//			myCombo.loadData(evalNavajo, evalText);
		}
	}

}
