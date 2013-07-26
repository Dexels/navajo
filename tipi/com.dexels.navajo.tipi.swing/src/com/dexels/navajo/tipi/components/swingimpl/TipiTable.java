package com.dexels.navajo.tipi.components.swingimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0();
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.swing.ConditionalRemark;
import com.dexels.navajo.tipi.components.swingimpl.swing.MessageTableFooterRenderer;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiMessageTablePanel;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingColumnAttributeParser;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.swingclient.components.ColumnAttribute;
import com.dexels.navajo.tipi.swingclient.components.MessageTableModel;
import com.dexels.navajo.tipi.swingclient.components.MessageTablePanel;
import com.dexels.navajo.tipi.tipixml.XMLElement;

public class TipiTable extends TipiSwingDataComponentImpl implements
		ChangeListener {

	private static final long serialVersionUID = 1181069408393438266L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTable.class);
	
	private String messagePath = "";
	private MessageTablePanel mm;
	private Map<String, ColumnAttribute> columnAttributes = new HashMap<String, ColumnAttribute>();

	private final Map<Integer, Integer> columnSize = new HashMap<Integer, Integer>();
	private final List<String> columnCondition = new ArrayList<String>();
	private MessageTableFooterRenderer myFooterRenderer = null;
	private final List<ConditionalRemark> conditionalRemarks = new ArrayList<ConditionalRemark>();
	private final Map<Integer, Double> columnDividers = new HashMap<Integer, Double>();
	private Message myMessage = null;
	private JPanel remarkPanel = null;
	private String remarkBorder = null;

	private Message columnMessage = null;

	// use with care. Here for threading probs
	private int selectedMessageIndex = -1;
	private List<XMLElement> columnList = new ArrayList<XMLElement>();

	public Object createContainer() {
		mm = new TipiMessageTablePanel(myContext, this);
		mm.setShowRowHeaders(false);
		// Don't register actionPerformed, that is done elsewhere.
		mm.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					
					messageTableSelectionChanged(e);
					Message selectedMessage = mm.getSelectedMessage();
					getAttributeProperty("selectedMessage").setAnyValue(
							selectedMessage);
					Object typedValue = getAttributeProperty("selectedMessage").getTypedValue();
					logger.debug("Atr: "+typedValue);
				}
			}
		});
		mm.setFocusable(false);
		mm.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				if (e.getOppositeComponent() != null) {
				}
			}

			public void focusLost(FocusEvent e) {
				if (e.getOppositeComponent() != null) {

				}
			}
		});

		mm.addChangeListener(this);
		// TipiHelper th = new TipiSwingHelper();
		// th.initHelper(this);
		// addHelper(th);

		mm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				messageTableActionPerformed(e);
			}
		});

		mm.getTable().addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "typed");

				try {
					performTipiEvent("onKey", m, true);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			public void keyPressed(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "pressed");
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						logger.debug("Enterrrr!");
						// Consume is important, otherwise selection will be
						// changed.
						e.consume();
						performTipiEvent("onEnter", m, true);
					} catch (TipiException e1) {
						logger.error("Error detected",e1);
					}
				}
				try {
					performTipiEvent("onKey", m, true);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			public void keyReleased(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "released");

				try {
					performTipiEvent("onKey", m, true);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			public Map<String, Object> getEventMap(KeyEvent e) {
				Map<String, Object> hm = new HashMap<String, Object>();
				hm.put("code", new Integer(e.getKeyCode()));
				hm.put("modifiers",
						KeyEvent.getKeyModifiersText(e.getModifiers()));
				hm.put("key", KeyEvent.getKeyText(e.getKeyCode()));
				return hm;
			}
		});
		mm.doLayout();
		return mm;
	}

	@Override
	public void showPopup(MouseEvent e) {
		Point p = e.getPoint();
		logger.debug("TablePopup: " + p);
		// get the row index that contains that coordinate
		int rowNumber = mm.getTable().rowAtPoint(p);
		mm.setSelectedRow(rowNumber);
		// Get the ListSelectionModel of the JTable
		// ListSelectionModel model = table.getSelectionModel();

		// set the selected interval of rows. Using the "rowNumber"
		// variable for the beginning and end selects only that one row.
		// model.setSelectionInterval( rowNumber, rowNumber );

		super.showPopup(e);
	}

	public final void load(XMLElement elm, XMLElement instance,
			TipiContext context) throws com.dexels.navajo.tipi.TipiException {
		// mm = (MessageTablePanel) getContainer();
		mm.removeAllColumns();
		removeAllAggregate();
		columnSize.clear();
		mm.setFooterRenderer(null);
		TipiSwingColumnAttributeParser cap = new TipiSwingColumnAttributeParser();
		messagePath = (String) elm.getAttribute("messagepath");
		if (messagePath != null) {
			if (messagePath.startsWith("'") && messagePath.endsWith("'")) {
				messagePath = messagePath
						.substring(1, messagePath.length() - 1);
			}
		}
		super.load(elm, instance, context);

		columnMessage = NavajoFactory.getInstance().createMessage(
				myContext.getStateNavajo(), "Columns", Message.MSG_TYPE_ARRAY);
		getStateMessage().addMessage(columnMessage);

		List<XMLElement> children = elm.getChildren();
		// int columnCount = 0;

		columnList.clear();
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			if (child.getName().equals("column")) {
				columnList.add(child);
				try {
					loadColumn(i, child, columnMessage, null);
				} catch (NavajoException e) {
					throw new TipiException("Error loading columns... ", e);
				}
			}

			if (child.getName().equals("column-attribute")) {
				String name = (String) child.getAttribute("name");
				String type = (String) child.getAttribute("type");
				if (name != null && type != null && !name.equals("")
						&& !type.equals("")) {
					columnAttributes.put(name, cap.parseAttribute(child));
				}
			}
			if (child.getName().equals("remarks")) {
				remarkBorder = (String) child.getAttribute("border");
				List<XMLElement> remarks = child.getChildren();
				for (int j = 0; j < remarks.size(); j++) {
					XMLElement remark = remarks.get(j);
					String condition = (String) remark
							.getAttribute("condition");
					String remarkString = (String) remark
							.getAttribute("remark");
					String colorString = (String) remark.getAttribute("color");
					String fontString = (String) remark.getAttribute("font");
					addConditionalRemark(remarkString, condition, colorString,
							fontString);
				}
			}
			if (child.getName().equals("columndivider")) {
				double width = child.getDoubleAttribute("width");
				int index = child.getIntAttribute("index");
				// mm.addC
				mm.addColumnDivider(index, (float) width);
				columnDividers.put(new Integer(index), new Double(width));
			}
		}
		mm.setColumnAttributes(columnAttributes);
		runSyncInEventThread(new Runnable() {
			public void run() {
				mm.setFooterRenderer(myFooterRenderer);
			}
		});
	}

	private void reloadColumns(Message m) throws NavajoException {
		mm.removeAllColumns();
		columnCondition.clear();
		List<Message> ss = columnMessage.getAllMessages();
		for (Message message : ss) {
			columnMessage.removeMessage(message);
		}
		int i = 0;

		for (XMLElement child : columnList) {

			loadColumn(i++, child, columnMessage, m);
		}

	}

	private void loadColumn(int i, XMLElement child, Message columnArrayMessage, Message definitionMessage)
			throws NavajoException {
		String name = (String) child.getAttribute("name");
		String editableString = (String) child.getAttribute("editable");
		String aggr = child.getStringAttribute("aggregate");
		String condition = child.getStringAttribute("condition");
		String typehint = child.getStringAttribute("typeHint");
		int size = child.getIntAttribute("size", -1);

		// try to use the property (that this column points to) from the message the table listens to
		// only if label is not specified (for backwards compatibility)
		String label = (String) child.getAttribute("label");
		if (label == null || label.trim().isEmpty())
		{
			if (definitionMessage != null && definitionMessage.isArrayMessage())
			{
				Message singleEntry = null;
				if (definitionMessage.getDefinitionMessage() != null)
				{
					singleEntry = definitionMessage.getDefinitionMessage();
				}
				else if (definitionMessage.getArraySize() > 0)
				{
					singleEntry = definitionMessage.getMessage(0);
				}
				
				if (singleEntry != null)
				{
					Property defProp = singleEntry.getProperty(name);
					if (defProp != null)
					{
						// it will be evaluated in a bit so it needs to be formatted as a string literal in Navajo terms.
						label = "'" + defProp.getDescription() + "'";
					}
				}
			}
		}
		if (label == null)
		{
			label = "''";
		}
		
		Message columnMessage = NavajoFactory.getInstance().createMessage(
				myContext.getStateNavajo(), "Columns",
				Message.MSG_TYPE_ARRAY_ELEMENT);
		columnArrayMessage.addMessage(columnMessage);

		addProperty(columnMessage, "Label", label, Property.STRING_PROPERTY);
		addProperty(columnMessage, "Name", name, Property.STRING_PROPERTY);
		addProperty(columnMessage, "Aggregate", aggr, Property.STRING_PROPERTY);
		addProperty(columnMessage, "Condition", aggr, Property.STRING_PROPERTY);
		addProperty(columnMessage, "TypeHint", typehint,
				Property.STRING_PROPERTY);
		addProperty(columnMessage, "Size", size, Property.INTEGER_PROPERTY);

		boolean editable = "true".equals(editableString);
		// logger.debug("Putting size for column # "+columnCount+"
		// to: "+size);
		columnSize.put(new Integer(i), new Integer(size));
		// String sizeString = (String) child.getAttribute("size");
		String labelString = label;
		// logger.debug("Label to evaluate: "+labelString);

		try {
			Operand evalLabel = this.getContext().evaluate(labelString, this,
					null, null);
			if (evalLabel != null) {

				labelString = "" + evalLabel.value;
				// logger.debug("Label evaluated to:
				// "+labelString);

			} else {
				// logger.debug("Null evaluated label.");
			}
		} catch (Exception ex) {
			logger.error("Error detected",ex);
			// logger.debug("Exception while evaluating label:
			// "+label);
		}

		if (size != -1) {
			// int size = Integer.parseInt(sizeString);
			mm.addColumn(name, labelString, editable, size);
		} else {
			mm.addColumn(name, labelString, editable);
		}
		if (typehint != null) {
			mm.setTypeHint(name, typehint);
		}
		if (aggr != null) {
			// logger.debug("Adding agr: "+aggr+" col: "+i);
			// Thread.dumpStack();
			addAggregate(i, aggr);
		}
		mm.messageChanged();
		addColumnVisiblityCondition(i, condition);

	}

	// public void setColumnEditable(int columnIndex, boolean value) {
	// mm.setColumnEditable(columnIndex,value);
	// mm.fireDataChanged();
	// }

	private void addProperty(Message m, String name, Object value, String type)
			throws NavajoException {
		Navajo n = m.getRootDoc();
		Property p = NavajoFactory.getInstance().createProperty(n, name, type,
				null, 0, null, Property.DIR_IN);
		p.setAnyValue(value);
		m.addProperty(p);
	}

	private void addColumnVisiblityCondition(int i, String condition) {
		columnCondition.add(condition);
	}

	public String[] getCustomChildTags() {
		return new String[] { "column", "column-attribute", "remarks",
				"columndivider" };
	}

	public void messageTableSelectionChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		updateConditionalRemarks();
		try {
			MessageTablePanel mm = (MessageTablePanel) getContainer();
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("selectedIndex", new Integer(mm.getSelectedRow()));
			tempMap.put("selectedMessage", mm.getSelectedMessage());
			performTipiEvent("onSelectionChanged", tempMap, false);
		} catch (TipiException ex) {
			logger.error("Error detected",ex);
		}
	}

	public void messageTableActionPerformed(ActionEvent ae) {
		MessageTablePanel mm = (MessageTablePanel) getContainer();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("selectedIndex", new Integer(mm.getSelectedRow()));
		tempMap.put("selectedMessage", mm.getSelectedMessage());
		setWaitCursor(true);
		performTipiEvent("onActionPerformed", tempMap, false, new Runnable() {
			public void run() {
				setWaitCursor(false);
			}
		});

	}

	@Override
	public void loadData(final Navajo n, final String method)
			throws TipiException, TipiBreakException {

		super.loadData(n, method);
		runSyncInEventThread(new Runnable() {
			public void run() {
				flushAggregateValues();
				updateConditionalRemarks();
			}
		});
		final MessageTablePanel mtp = (MessageTablePanel) getContainer();
		if (messagePath != null && n != null) {
			final Message m = n.getMessage(messagePath);
			myMessage = m;
			// logger.debug("MEssage: "+myMessage);
			if (m != null) {
				runSyncInEventThread(new Runnable() {
					public void run() {
						// first reload the columns using the message found
						try {
							reloadColumns(m);
						} catch (NavajoException e) {
							logger.error("Error detected",e);
						}
						// Hardcode it to true. If the component seems to work
						// fine (check the output) set to false for a small (?)
						// perf. gain.
						boolean rowLoadEventFound = false;
						int elementIndex = 0;
						List<TipiEvent> list = getEventList();
						for (TipiEvent tipiEvent : list) {
							if ("onRowLoad".equals(tipiEvent.getEventName())) {
								rowLoadEventFound = true;
							}
						}
						if (rowLoadEventFound) {
							for (Message element : myMessage.getAllMessages()) {

								Map<String, Object> m = new HashMap<String, Object>();
								m.put("service", method);
								m.put("index", elementIndex);
								m.put("message", element);
								m.put("navajo", n);
								try {
									performTipiEvent("onRowLoad", m, true);
								} catch (TipiBreakException e) {
									logger.debug("Row break!");
									break;
								} catch (TipiException e) {
									logger.error("Error detected",e);
									break;
								}
								elementIndex++;
							}

						}
						if (columnSize.size() == 0) {
							mtp.createColumnsFromDef(m);
						}
						int selectedIndex = mtp.getSelectedRow();
						mtp.setMessage(m);
						mtp.getTable().updateTableSize();
						mtp.updateTableSize();
						updateColumnVisibility();
						mtp.updateColumnSizes();
						if (selectedIndex < mtp.getRowCount()) {
							mtp.setSelectedRow(selectedIndex);
						}

					}
				});
			}
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("service", method);
		m.put("navajo", n);
		performTipiEvent("onLoad", m, true);
		mm.repaint();
	}

	protected void updateColumnVisibility() {
		for (int index = columnCondition.size() - 1; index >= 0; index--) {
			// for (int index: columnCondition.keySet()) {
			try {
				String condition = columnCondition.get(index);
				if (condition == null) {
					continue;
				}
				Operand o = evaluate(condition, this, null);
				if (o == null) {
					continue;
				}
				if (o.value instanceof Boolean) {
					setColumnVisible(index, ((Boolean) o.value).booleanValue());
				}
			} catch (Exception e) {
				logger.error("Error detected",e);
			}
		}
		mm.createDefaultColumnsFromModel();
	}

	/*
	 * Overridden, to pervent automatically call perform the onload event.
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.components.core.TipiDataComponentImpl#doPerformOnLoad
	 * (java.lang.String)
	 */

	@Override
	protected void doPerformOnLoad(String method, Navajo n, boolean sync) {
	}

	public void setComponentValue(final String name, final Object object) {
		runSyncInEventThread(new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				if (name.equals("filtersvisible")) {
					setFiltersVisible(Boolean.valueOf(object.toString())
							.booleanValue());
				}
				if (name.equals("visible")) {
					mm.setVisible(Boolean.valueOf(object.toString())
							.booleanValue());
				}
				if (name.equals("hideColumn")) {
					setColumnVisible(object.toString(), false);
				}
				if (name.equals("showColumn")) {
					setColumnVisible(object.toString(), true);
				}
				if (name.equals("columnsvisible")) {
					setColumnsVisible(Boolean.valueOf(object.toString())
							.booleanValue());
				}
				if (name.equals("sortable")) {
					mm.setSortingAllowed(Boolean.valueOf(object.toString())
							.booleanValue());
				}
				if (name.equals("headervisible")) {
					setHeaderVisible(Boolean.valueOf(object.toString())
							.booleanValue());
				}
				if (name.equals("ignoreList")) {
					setIgnoreList((List<String>) object);
				}

				if (name.equals("readOnly")) {
					mm.setReadOnly(Boolean.valueOf(object.toString())
							.booleanValue());
				}
				if (name.equals("selectedIndex")) {
					selectedMessageIndex = ((Integer) object).intValue();
					mm.setSelectedRow(selectedMessageIndex);
					// setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
				}
				if (name.equals("selectedMessage")) {
					final Message m = ((Message) object);
					mm.setSelectedMessage(m);
					// setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
				}
				if (name.equals("rowHeight")) {
					mm.setRowHeight(((Integer) object).intValue());
				}
				if (name.equals("showRowHeaders")) {
					mm.setShowRowHeaders(((Boolean) object).booleanValue());
				}

				if (name.equals("autoresize")) {
					if ("all".equals(object)) {
						mm.setAutoResize(JTable.AUTO_RESIZE_ALL_COLUMNS);
					}
					if ("last".equals(object)) {
						mm.setAutoResize(JTable.AUTO_RESIZE_LAST_COLUMN);
					}
					if ("next".equals(object)) {
						mm.setAutoResize(JTable.AUTO_RESIZE_NEXT_COLUMN);
					}
					if ("subsequent".equals(object)) {
						mm.setAutoResize(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
					}
					if ("off".equals(object)) {
						mm.setAutoResize(JTable.AUTO_RESIZE_OFF);
					}
				}
				if (name.equals("columnDefinitionSavePath")) {
					setColumnDefinitionSavePath(object.toString());
				}
				if (name.equals("filtermode")) {
					mm.setFilterMode("" + object);
				}
				if (name.equals("refreshAfterEdit")) {
					mm.setRefreshAfterEdit(Boolean.valueOf(object.toString())
							.booleanValue());
				}
				if (name.equals("highColor")) {
					mm.setHighColor((Color) object);
				}
				if (name.equals("lowColor")) {
					mm.setLowColor((Color) object);
				}
				if (name.equals("selectedColor")) {
					mm.setSelectedColor((Color) object);
				}
				if (name.equals("useScroll")) {
					mm.setUseScrollBars((Boolean) object);
				}
				if (name.equals("border")) {
					mm.setBorder((Border) object);
				}
			}
		});

		super.setComponentValue(name, object);
	}

	private void setIgnoreList(List<String> value) {
		String[] l = new String[value.size()];
		int i = 0;
		for (String string : value) {
			l[i++] = string;
		}
		mm.setIgnoreList(l);
	}

	/**
	 * Uber deprecated, no idea why this is still here.
	 * 
	 * @deprecated
	 * @param name
	 * @param visible
	 */
	@Deprecated
	private final void setColumnVisible(String name, boolean visible) {
		MessageTablePanel mm = (MessageTablePanel) getContainer();
		if (visible) {
			mm.addColumn(name, name, false);
		} else {
			if (name.equals("selected")) {
				mm.removeColumn(mm.getSelectedColumn());
			} else {
				mm.removeColumn(name);
			}
		}
	}

	private final void setColumnVisible(int index, boolean visible) {
		MessageTablePanel mm = (MessageTablePanel) getContainer();
		// TableColumn tc = mm.getTable().getColumnModel().getColumn(index);
		MessageTableModel m = mm.getTable().getMessageModel();
		if (!visible) {
			m.removeColumn(index);
		}
	}

	public void setHeaderVisible(boolean b) {
		mm.setHeaderVisible(b);
	}

	public void setFiltersVisible(boolean b) {
		MessageTablePanel mtp = (MessageTablePanel) getContainer();
		mtp.setFiltersVisible(b);
	}

	public void setColumnsVisible(boolean b) {
		MessageTablePanel mtp = (MessageTablePanel) getContainer();
		mtp.setColumnsVisible(b);
	}

	public Object getComponentValue(String name) {
		if (name != null) {
			if (name.equals("selectedMessage")) {
				Message m = mm.getSelectedMessage();
				return m;
			}
			if (name.equals("lastMessage")) {
				Message m = mm.getMessage();
				if (m == null) {
					return null;
				}
				int count = m.getArraySize();

				if (count == 0) {
					return null;
				}
				return m.getMessage(count - 1);
			}
			if (name.equals("firstMessage")) {
				Message m = mm.getMessage();
				if (m == null) {
					return null;
				}
				int count = m.getArraySize();

				if (count == 0) {
					return null;
				}
				return m.getMessage(0);
			}

			if (name.equals("selectedMessages")) {
				List<Message> all = mm.getSelectedMessages();
				if (all != null && all.size() > 0) {
					Navajo n = NavajoFactory.getInstance().createNavajo();
					n.addHeader(NavajoFactory.getInstance().createHeader(n,
							"Anonymous", "unknown", "unknown", -1));
					Message array = NavajoFactory.getInstance().createMessage(
							n, all.get(0).getName(), Message.MSG_TYPE_ARRAY);
					for (int i = 0; i < all.size(); i++) {
						Message cur = all.get(i);
						array.addMessage(cur);
					}
					try {
						n.addMessage(array);
					} catch (NavajoException e) {
						logger.error("Error detected",e);
					}
					return array;
				} else {
					logger.debug("AAp.. all is null of 0");
				}
			}
			if (name.equals("filteredMessage")) {
				Message m = mm.getMessageAsPresentedOnTheScreen(true);
				return m;
			} else if (name.equals("selectedIndex")) {
				runSyncInEventThread(new Runnable() {
					public void run() {
						if (mm != null) {
							selectedMessageIndex = mm.getSelectedRow();
						}
					}
				});
				return new Integer(selectedMessageIndex);
			}
			if (name.equals("rowCount")) {
				return new Integer(mm.getRowCount());
			}
			if (name.equals("columnCount")) {
				return new Integer(mm.getColumnCount());
			}
			return super.getComponentValue(name);
		} else {
			return null;
		}
	}

	protected void performComponentMethod(final String name,
			final TipiComponentMethod compMeth, final TipiEvent event) {
		runSyncInEventThread(new Runnable() {

			public void run() {
				int count = mm.getRowCount();
				if (count != 0) {
					if ("selectNext".equals(name)) {
						int r = mm.getSelectedRow();
						if ((r < count - 1)) {
							mm.setSelectedRow(r + 1);
						}
						return;
					}
					if ("repaint".equals(name)) {
						mm.fireDataChanged();
						return;
					}
					if ("selectPrevious".equals(name)) {
						int r = mm.getSelectedRow();
						if ((r > 0)) {
							mm.setSelectedRow(r - 1);
						}
						return;
					}
					if ("selectFirst".equals(name)) {
						mm.setSelectedRow(0);
					}
					if ("selectLast".equals(name)) {
						mm.setSelectedRow(count - 1);
					}
					if ("selectMessages".equals(name)) {
						List<Message> list = (List<Message>) compMeth
								.getEvaluatedParameterValue("messages", event);
						ListSelectionModel lsm = mm.getTable()
								.getSelectionModel();
						mm.getTable().getSelectionModel()
								.setValueIsAdjusting(true);
						lsm.clearSelection();
						int rows = mm.getTable().getRowCount();
						for (int i = 0; i < rows; i++) {
							for (Message message : list) {
								if (mm.getTable().getMessageRow(i).hashCode() == message
										.hashCode()) {
									lsm.addSelectionInterval(i, i);

								}
							}
						}
						mm.getTable().getSelectionModel()
								.setValueIsAdjusting(false);

					}

					if ("showEditDialog".equals(name)) {
						Operand title = compMeth.getEvaluatedParameter("title",
								event);
						try {
							String titleString;
							titleString = title == null ? "Aap" : ""
									+ title.value;
							mm.showEditDialog(titleString, mm.getSelectedRow());
						} catch (Exception ex1) {
							logger.error("Error detected",ex1);
						}
					}
					if ("selectByValue".equals(name)) {
						Operand name = compMeth.getEvaluatedParameter(
								"propertyName", event);
						Operand value = compMeth.getEvaluatedParameter("value",
								event);

						try {
							int rowCount = mm.getRowCount();
							mm.getTable().getSelectionModel()
									.setValueIsAdjusting(true);
							mm.getTable().getSelectionModel().clearSelection();

							for (int i = 0; i < rowCount; i++) {
								Message current = mm.getMessageRow(i);
								Property p = current
										.getProperty((String) name.value);
								if (p != null) {
									if (p.getTypedValue().equals(value.value)) {
										mm.getTable().getSelectionModel()
												.addSelectionInterval(i, i);
									}
								}
							}
							mm.getTable().getSelectionModel()
									.setValueIsAdjusting(false);
						} catch (Exception ex1) {
							logger.error("Error detected",ex1);
						}
					}
				}
				if ("export".equals(name)) {
					Operand filename = compMeth.getEvaluatedParameter(
							"filename", event);
					Operand delimiter = compMeth.getEvaluatedParameter(
							"delimiter", event);
					// doExportAll();
					mm.getTable().exportTable((String) filename.value,
							(String) delimiter.value);
				}

				if ("setAllSelected".equals(name)) {
					logger.debug("In setAllSelected");
					Operand propertyName = compMeth.getEvaluatedParameter(
							"propertyName", event);
					Operand value = compMeth.getEvaluatedParameter("value",
							event);
					logger.debug("Value: " + value.value);
					logger.debug("PropertyName: " + propertyName.value);
					ArrayList<Message> al = mm.getSelectedMessages();
					if (al == null) {
						// Nothing is selected
						return;
					}
					logger.debug("# of selected msgs: " + al.size());
					for (int i = 0; i < al.size(); i++) {
						Message current = al.get(i);
						Property cp = current.getProperty(""
								+ propertyName.value);
						cp.setAnyValue(value.value);
					}
				}

				if ("fireAction".equals(name)) {
					try {
						performTipiEvent("onActionPerformed", null, false);

					} catch (TipiException e) {
						logger.error("Error detected",e);
					}
				}

				if ("doEmail".equals(name)) {
					mm.doEmail();
				}
				if ("doWord".equals(name)) {
					mm.doWord();
				}
				if ("doExcel".equals(name)) {
					mm.doExcel();
					// doExportAll();
					// DONT CHECK IN!
				}
				if ("doSaveColumns".equals(name)) {
					mm.doSaveColumns();
				}

				if ("doChooseColumns".equals(name)) {
					mm.doChooseColumns();
				}
				if ("stopCellEditing".equals(name)) {
					mm.stopCellEditing();
				}
				if ("updateConditionalRemarks".equals(name)) {
					updateConditionalRemarks();
				}
				if ("editCell".equals(name)) {
					Operand property = compMeth.getEvaluatedParameter(
							"property", event);

					Property value = (Property) property.value;
					if (value == null) {
						logger.warn("Error: can not editCell, property null. Expression: "
										+ compMeth.getParameter("property")
												.toString());
					} else {
						editCell(value);
					}
				}
				if ("sort".equals(name)) {
					Operand index = compMeth.getEvaluatedParameter("index",
							event);
					Operand direction = compMeth.getEvaluatedParameter(
							"ascending", event);
					mm.doSort(((Integer) index.value),
							((Boolean) direction.value));
				}
				if ("setColumnEditable".equals(name)) {
				}
				if ("doRunReport".equals(name)) {
					Operand format = compMeth.getEvaluatedParameter("format",
							event);
					Operand marginsOperand = compMeth.getEvaluatedParameter(
							"margins", event);
					Operand orientationOperand = compMeth
							.getEvaluatedParameter("orientation", event);
					String orientation = null;
					if (orientationOperand != null) {
						orientation = (String) orientationOperand.value;
					}
					int[] margin = null;

					if (marginsOperand != null) {
						margin = new int[4];
						String marginString = (String) marginsOperand.value;
						StringTokenizer st = new StringTokenizer(marginString,
								",");
						margin[0] = Integer.parseInt(st.nextToken());
						margin[1] = Integer.parseInt(st.nextToken());
						margin[2] = Integer.parseInt(st.nextToken());
						margin[3] = Integer.parseInt(st.nextToken());
					}
					try {
						doRunReport((String) format.value, orientation, margin);
					} catch (TipiException e) {
						logger.error("Error detected",e);
					}
				}
			}
		});

	}

	protected void editCell(final Property value) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				mm.editCell(value);
			}
		});
	}

	public void setColumnDefinitionSavePath(String path) {
		mm.setColumnDefinitionSavePath(path);
	}

	public void stateChanged(ChangeEvent e) {
		Map<String, Object> m = (Map<String, Object>) e.getSource();
		Object old = m.get("old");
		Object newP = m.get("new");
		flushAggregateValues();
		updateConditionalRemarks();
		mm.repaint();
		try {

			if (old == null) {
				if (newP != null) {
					// TODO: Should not be in sync mode, right?
					performTipiEvent("onValueChanged", m, true);
				}
			} else {
				if (!old.equals(newP)) {
					performTipiEvent("onValueChanged", m, true);
				}
			}
		} catch (TipiException ex) {
			logger.error("Error detected",ex);
		}
	}

	public void addAggregate(int columnIndex, String expression) {
		if (myFooterRenderer == null) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					myFooterRenderer = new MessageTableFooterRenderer(
							TipiTable.this);
					myFooterRenderer.setVisible(true);
					mm.setFooterRenderer(myFooterRenderer);
					mm.doLayout();

				}
			});
		}
		myFooterRenderer.addAggregate(columnIndex, expression);

	}

	public void flushAggregateValues() {
		if (myFooterRenderer != null) {
			myFooterRenderer.flushAggregateValues();
		}
	}

	public void removeAggregate(int columnIndex) {
		if (myFooterRenderer != null) {
			myFooterRenderer.removeAggregate(columnIndex);
		}
	}

	public void removeAllAggregate() {
		if (myFooterRenderer != null) {
			myFooterRenderer.removeAllAggregate();
		}
		mm.setFooterRenderer(null);
	}

	public String getAggregateFunction(int column) {
		if (myFooterRenderer != null) {
			return myFooterRenderer.getAggregateFunction(column);
		}
		return null;
	}

	public void updateConditionalRemarks() {
		if (remarkPanel == null || conditionalRemarks.size() == 0) {
			return;
		}
		remarkPanel.removeAll();
		int complied = 0;
		for (int i = 0; i < conditionalRemarks.size(); i++) {
			ConditionalRemark current = conditionalRemarks.get(i);
			Operand oo = getContext().evaluate(current.getCondition(), this,
					null, myMessage);
			boolean complies = false;
			if (oo.value != null) {
				Boolean b = (Boolean) oo.value;
				complies = b.booleanValue();
			}
			if (complies) {
				Operand o = myContext.evaluate(current.getRemark(), this, null,
						mm.getMessage());
				Operand q = myContext.evaluate(current.getColor(), this, null,
						mm.getMessage());
				Operand r = myContext.evaluate(current.getFont(), this, null,
						mm.getMessage());
				Color c = q == null ? null : (Color) q.value;
				Font f = r == null ? null : (Font) r.value;
				// Operand o = evaluate(current.getRemark(),this,null);
				remarkPanel.add(createRemark("" + o.value, c, f),
						new GridBagConstraints(0, complied, 1, 1, 1.0, 0.0,
								GridBagConstraints.WEST,
								GridBagConstraints.HORIZONTAL, new Insets(1, 1,
										1, 1), 0, 0));
				logger.debug("COMPLYING:  ");
				complied++;
			}
		}
		remarkPanel.setVisible(complied > 0);
		remarkPanel.revalidate();
		mm.revalidate();
	}

	private Component createRemark(String remark, Color c, Font f) {
		JLabel ll = new JLabel(remark);
		// ll.setFont(ll.getFont().deriveFont(20.0f));
		if (f != null) {
			ll.setFont(f);
		}
		if (c != null) {
			ll.setForeground(c);
		}
		return ll;
	}

	public void addConditionalRemark(String remark, String condition, String c,
			String font) {
		ConditionalRemark cr = new ConditionalRemark(this, remark, condition,
				c, font);
		conditionalRemarks.add(cr);
		logger.warn("************************\nCreating remark panel\n********************************\n");
		if (remarkPanel == null) {
			createRemarkPanel();
		}
		logger.debug("size:");
	}

	private final void createRemarkPanel() {
		remarkPanel = new JPanel();
		Operand r = myContext.evaluate(remarkBorder, this, null,
				mm.getMessage());
		Border b = r == null ? null : (Border) r.value;
		if (b != null) {
			remarkPanel.setBorder(b);
		}
		remarkPanel.setVisible(false);
		remarkPanel.setLayout(new GridBagLayout());
		// mm.add(remarkPanel, BorderLayout.SOUTH);
		mm.add(remarkPanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(2, 2, 2, 2), 0, 0));
		mm.revalidate();
	}

	public void doEmail() {
		mm.doEmail();
	}

	public void doWord() {
		mm.doWord();
	}

	public void doExcel() {
		mm.doExcel();
	}

	public void doSaveColumns() {
		mm.doSaveColumns();
	}

	public void doChooseColumns() {
		mm.doChooseColumns();
	}

	public void doRunReport(String format, String orientation, int[] margins)
			throws TipiException {
		Binary b;
		try {
			b = mm.getTableReport(format, orientation, margins);
		} catch (NavajoException e) {
			throw new TipiException("Error calling birt report!", e);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("report", b);
		m.put("format", format);
		performTipiEvent("onReportFinished", m, false);
	}

	@Override
	public Object getActualComponent() {
		return mm.getTable();
	}

}
