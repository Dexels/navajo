package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.swingclient.components.MessageTablePanel;
import com.dexels.navajo.tipi.tipixml.XMLElement;

@Deprecated
public class TipiMultiTable extends TipiSwingDataComponentImpl {
	private static final long serialVersionUID = -7851029301125017737L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMultiTable.class);
	private JPanel myPanel = null;
	private boolean useTabs = true;
	private String outerMessageName = null;
	private String innerMessageName = null;
	private String titlePropertyName = "Title";
	private boolean columnsButtonVisible = false;
	private boolean filtersVisible = false;
	private boolean useScrollBars = true;
	private boolean headerVisible = false;
	private int rowHeight = -1;
	private final List<String> columns = new ArrayList<String>();
	private final List<Integer> columnSize = new ArrayList<Integer>();

	public TipiMultiTable() {
	}

	public Object createContainer() {
		/**
		 * @todo Implement this
		 *       com.dexels.navajo.tipi.components.core.TipiComponentImpl
		 *       abstract method
		 */
		myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout());
		return myPanel;
	}

	public void load(XMLElement elm, XMLElement instance, TipiContext context)
			throws com.dexels.navajo.tipi.TipiException {
		super.load(elm, instance, context);
		columns.clear();
		columnSize.clear();
		List<XMLElement> children = elm.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			if (child.getName().equals("column")) {
				String name = (String) child.getAttribute("name");
				columns.add(name);
				int size = child.getIntAttribute("size", -1);
				columnSize.add(new Integer(size));
			}
		}
	}

	private final void reload() {
		try {
			if (myNavajo != null) {
				// todo: replace with real
				String hh = null;
				Header h = myNavajo.getHeader();
				if (h != null) {
					hh = h.getRPCName();
				}
				loadData(getNavajo(), hh);
			} else {
				logger.debug("Can not reload, no navajo!");
				Thread.dumpStack();
			}
		} catch (TipiException ex) {
			logger.error("Error detected",ex);
		} catch (TipiBreakException e) {
			logger.error("Error detected",e);
		}
	}

	public Object getComponentValue(String name) {
		logger.debug("In getter of multitable: name: " + name);
		if (name.equals("columnsButtonVisible")) {
			return new Boolean(columnsButtonVisible);
		}
		if (name.equals("filtersVisible")) {
			return new Boolean(filtersVisible);
		}
		if (name.equals("useScrollBars")) {
			return new Boolean(useScrollBars);
		}
		if (name.equals("headerVisible")) {
			return new Boolean(headerVisible);
		}
		if (name.equals("useTabs")) {
			return new Boolean(useTabs);
		}
		if (name.equals("outerMessageName")) {
			return outerMessageName;
		}
		if (name.equals("innerMessageName")) {
			return innerMessageName;
		}
		if (name.equals("titlePropertyName")) {
			return titlePropertyName;
		}
		return super.getComponentValue(name);
	}

	// private boolean columnButtonsVisible = false;
	// private boolean filtersVisible = false;
	// private boolean useScrollBars = true;
	// private boolean headerVisible = false;
	public void setComponentValue(String name, Object object) {
		logger.debug("In setter of multitable: name: " + name
				+ " value: " + object);
		if (name.equals("columnButtonVisible")) {
			columnsButtonVisible = (Boolean.valueOf(object.toString())
					.booleanValue());
			reload();
		}
		if (name.equals("filtersVisible")) {
			filtersVisible = (Boolean.valueOf(object.toString()).booleanValue());
			reload();
		}
		if (name.equals("useScrollBars")) {
			useScrollBars = (Boolean.valueOf(object.toString()).booleanValue());
			reload();
		}
		if (name.equals("headerVisible")) {
			headerVisible = (Boolean.valueOf(object.toString()).booleanValue());
			reload();
		}
		if (name.equals("useTabs")) {
			useTabs = (Boolean.valueOf(object.toString()).booleanValue());
			reload();
		}
		if (name.equals("outerMessageName")) {
			logger.debug("Setting outerMessage to: " + object);
			outerMessageName = (String) object;
			reload();
		}
		if (name.equals("innerMessageName")) {
			logger.debug("Setting innerMessage to: " + object);
			innerMessageName = (String) object;
			reload();
		}
		if (name.equals("titlePropertyName")) {
			titlePropertyName = object.toString();
			reload();
		}
		if (name.equals("rowHeight")) {
			rowHeight = ((Integer) object).intValue();
			reload();
			// setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
		}
		super.setComponentValue(name, object);
	}

	private final void setupTable(MessageTablePanel mtp) {
		mtp.setShowRowHeaders(false);
		mtp.setColumnsVisible(columnsButtonVisible);
		mtp.setFiltersVisible(filtersVisible);
		mtp.setUseScrollBars(useScrollBars);
		mtp.setHeaderVisible(headerVisible);
		if (rowHeight > 0) {
			mtp.setRowHeight(rowHeight);
		}
	}

	private final void updateTableColumns(final MessageTablePanel mtp) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				mtp.createColumnModel();
				for (int i = 0; i < columnSize.size(); i++) {
					int ii = columnSize.get(i).intValue();
					final int index = i;
					final int value = ii;
					// logger.debug("Setting column: " + i + " to: " +
					// ii);
					mtp.setColumnWidth(index, value);
				}
			}
		});
	}

	private final void buildTabs(Navajo n) {
		JTabbedPane jt = new JTabbedPane();
		myPanel.add(jt, BorderLayout.CENTER);
		Message m = n.getMessage(outerMessageName);
		for (int i = 0; i < m.getArraySize(); i++) {
			Message current = m.getMessage(i);
			Property titleProp = current.getProperty(titlePropertyName);
			String title = titleProp.getValue();
			Message inner = current.getMessage(innerMessageName);
			MessageTablePanel mtp = new MessageTablePanel();
			setupTable(mtp);
			jt.addTab(title, mtp);
			if (inner.getArraySize() > 0) {
				Message first = inner.getMessage(0);
				for (int j = 0; j < columns.size(); j++) {
					String column = columns.get(j);
					Property p = first.getProperty(column);
					if (p != null) {
						mtp.addColumn(p.getName(), p.getDescription(),
								p.isDirIn());
					}
				}
			}
			mtp.setMessage(inner);
			updateTableColumns(mtp);
			// for (int j = 0; j < columnSize.size(); j++) {
			// int s = ((Integer)columnSize.get(j)).intValue();
			// mtp.setColumnWidth(j,s);
			// }
		}
	}

	private final void buildPanels(Navajo n) {
		JPanel jt = new JPanel();
		jt.setLayout(new GridBagLayout());
		myPanel.add(jt, BorderLayout.CENTER);
		Message m = n.getMessage(outerMessageName);
		for (int i = 0; i < m.getArraySize(); i++) {
			logger.debug("Message # " + i);
			Message current = m.getMessage(i);
			Property titleProp = current.getProperty(titlePropertyName);
			if (titleProp == null) {
				logger.debug("NO TITLEPROP FOUND. Looking for: "
						+ titlePropertyName);
				continue;
			}
			String title = titleProp.getValue();
			Message inner = current.getMessage(innerMessageName);
			MessageTablePanel mtp = new MessageTablePanel();
			setupTable(mtp);
			mtp.setBorder(BorderFactory.createTitledBorder(title));
			jt.add(mtp, new GridBagConstraints(0, i, 1, 1, 1, 1,
					GridBagConstraints.WEST, GridBagConstraints.BOTH,
					new Insets(2, 2, 2, 2), 0, 0));
			if (inner.getArraySize() > 0) {
				Message first = inner.getMessage(0);
				for (int j = 0; j < columns.size(); j++) {
					String column = columns.get(j);
					Property p = first.getProperty(column);
					if (p != null) {
						mtp.addColumn(p.getName(), p.getDescription(),
								p.isDirIn());
					}
				}
			}
			mtp.setMessage(inner);
			// for (int j = 0; j < columnSize.size(); j++) {
			// int s = ((Integer)columnSize.get(j)).intValue();
			// mtp.setColumnWidth(j,s);
			// }
		}
	}

	public void loadData(final Navajo n, String method) throws TipiException,
			TipiBreakException {
		if (outerMessageName == null) {
			logger.debug("No outermessage");
			return;
		} else {
			logger.debug("Outer: " + outerMessageName);
		}
		Message outerMessage = n.getMessage(outerMessageName);
		if (outerMessage == null) {
			return;
		}
		if (innerMessageName == null) {
			logger.debug("No innermessage");
			return;
		} else {
			logger.debug("Inner: " + innerMessageName);
		}
		// Message innerMessage = outerMessage.getMessage(innerMessageName);
		// if (outerMessage != null) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				myPanel.removeAll();
				if (useTabs) {
					buildTabs(n);
				} else {
					buildPanels(n);
				}
				myPanel.revalidate();
			}
		});
		// }
		// else {
		// logger.debug("Not loading outer message null!");
		// }
		super.loadData(n, method);
	}
}
