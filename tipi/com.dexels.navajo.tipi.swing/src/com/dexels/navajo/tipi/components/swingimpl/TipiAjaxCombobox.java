package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.swingclient.components.remotecombobox.AjaxComboBox;
import com.dexels.navajo.tipi.swingclient.components.remotecombobox.RemoteRefreshFilter;

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
	private static final long serialVersionUID = -8756127082376871184L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiAjaxCombobox.class);
	
	private AjaxComboBox myCombo;

	private String currentSelection = null;

	protected Object selectedValue;

	public Object createContainer() {
		myCombo = new AjaxComboBox();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		myCombo.setCurrentRemoteRefresh(new RemoteRefreshFilter() {
			public Navajo getNavajo(String filterString) {
				logger.debug("Gettin: " + filterString);
				currentSelection = filterString;
				Map<String, Object> m = new HashMap<String, Object>();
				selectedValue = myCombo.getSelectedValue();
				getAttributeProperty("selectedValue")
						.setAnyValue(selectedValue);
				m.put("selectedValue", selectedValue);
				m.put("text", currentSelection);
				try {
					performTipiEvent("onChange", m, true);
				} catch (TipiException e) {
					logger.error("Error detected",e);
				}
				return null;
			}
		});
		myCombo.setVisible(true);

		myCombo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {
				try {
					String sel = (String) myCombo.getSelectedItem();
					Map<String, Object> m = new HashMap<String, Object>();
					selectedValue = myCombo.getSelectedValue();
					getAttributeProperty("selectedValue").setAnyValue(
							selectedValue);
					m.put("selectedValue", selectedValue);
					m.put("value", sel);
					performTipiEvent("onSelect", m, false);
				} catch (TipiException e) {
					logger.error("Error detected",e);
				}
			}
		});

		myCombo.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent de) {
				try {
					fireTextChange(de.getDocument().getText(0,
							de.getDocument().getLength()));
				} catch (BadLocationException e) {
					logger.error("Error detected",e);
				}
			}

			public void insertUpdate(DocumentEvent de) {
				try {
					fireTextChange(de.getDocument().getText(0,
							de.getDocument().getLength()));
				} catch (BadLocationException e) {
					logger.error("Error detected",e);
				}
			}

			public void removeUpdate(DocumentEvent de) {
				try {
					fireTextChange(de.getDocument().getText(0,
							de.getDocument().getLength()));
				} catch (BadLocationException e) {
					logger.error("Error detected",e);
				}
			}
		});

		myCombo.addEnterEventListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					myCombo.hidePopup();
					String sel = (String) myCombo.getSelectedItem();
					Map<String, Object> m = new HashMap<String, Object>();
					selectedValue = myCombo.getSelectedValue();
					getAttributeProperty("selectedValue").setAnyValue(
							selectedValue);
					m.put("selectedValue", selectedValue);
					m.put("value", sel);
					performTipiEvent("onEnter", m, false);
				} catch (TipiException e) {
					logger.error("Error detected",e);
				}
			}
		});

		return myCombo;
	}

	public void fireTextChange(String text) {
		Map<String, Object> m = new HashMap<String, Object>();
		selectedValue = myCombo.getSelectedValue();
		getAttributeProperty("selectedValue").setAnyValue(selectedValue);
		m.put("text", text);
		try {
			performTipiEvent("onTextChange", m, false);
		} catch (TipiException e) {
			logger.error("Error detected",e);
		}
	}

	// localCombo = new AjaxComboBox();
	// localCombo.setMessagePath("Club");
	// localCombo.setPropertyName("ClubName");
	// localCombo.setDelay(1000);
	// localCombo.setSyncRefresh(false);
	// localCombo.setCurrentRemoteRefresh(new RemoteRefreshFilter() {

	@Override
	public void loadData(Navajo n, String method) throws TipiException,
			TipiBreakException {
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
		if (name.equals("valuePropertyName")) {
			myCombo.setValuePropertyName((String) object);
		}
		if (name.equals("syncRefresh")) {
			myCombo.setSyncRefresh(((Boolean) object).booleanValue());
		}
		if (name.equals("refreshDelay")) {
			myCombo.setDelay(((Integer) object).intValue());
		}
		if (name.equals("text")) {
			myCombo.setText((String) object);
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

	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event) {
		if ("updateCombo".equals(name)) {
			String evalText = (String) compMeth.getEvaluatedParameter("text",
					event).value;
			Navajo evalNavajo = (Navajo) compMeth.getEvaluatedParameter(
					"navajo", event).value;
			myCombo.loadData(evalNavajo, evalText);
		}
	}

}
