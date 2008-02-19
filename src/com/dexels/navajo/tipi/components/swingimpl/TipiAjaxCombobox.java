package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.remotecombobox.*;
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
public class TipiAjaxCombobox extends TipiSwingDataComponentImpl {
	private AjaxComboBox myCombo;

	private String currentSelection = null;

	public Object createContainer() {
		myCombo = new AjaxComboBox();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		myCombo.setCurrentRemoteRefresh(new RemoteRefreshFilter() {
			public Navajo getNavajo(String filterString) {
				currentSelection = filterString;
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("text", currentSelection);
				try {
					performTipiEvent("onChange", m, true);
				} catch (TipiException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		myCombo.setVisible(true);
		
		myCombo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {

				try {
					String sel = (String) myCombo.getSelectedItem();
					Map<String,Object> m = new HashMap<String,Object>();
					m.put("value", sel);
					performTipiEvent("onSelect", m, false);
				} catch (TipiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		myCombo.addEnterEventListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					myCombo.hidePopup();
					String sel = (String) myCombo.getSelectedItem();
					Map<String,Object> m = new HashMap<String,Object>();
					m.put("value", sel);
					performTipiEvent("onEnter", m, false);
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}
		});

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
		if (name.equals("messagePath")) {
			myCombo.setMessagePath((String) object);
		}
		if (name.equals("propertyName")) {
			myCombo.setPropertyName((String) object);
		}
		if (name.equals("syncRefresh")) {
			myCombo.setSyncRefresh(((Boolean) object).booleanValue());
		}
		if (name.equals("refreshDelay")) {
			myCombo.setDelay(((Integer) object).intValue());
		}
	}

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
		if ("updateCombo".equals(name)) {
			String evalText = (String) compMeth.getEvaluatedParameter("text", event).value;
			Navajo evalNavajo = (Navajo) compMeth.getEvaluatedParameter("navajo", event).value;
			myCombo.loadData(evalNavajo, evalText);
		}
	}

}
