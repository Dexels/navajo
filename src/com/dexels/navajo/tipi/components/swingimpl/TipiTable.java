package com.dexels.navajo.tipi.components.swingimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0();
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

public class TipiTable extends TipiSwingDataComponentImpl implements ChangeListener {
	private String messagePath = "";
	private MessageTablePanel mm;
	private Map<String,ColumnAttribute> columnAttributes = new HashMap<String,ColumnAttribute>();
	private final Map<Integer,Integer> columnSize = new HashMap<Integer,Integer>();
	private final List<String> columnCondition= new ArrayList<String>();
	private MessageTableFooterRenderer myFooterRenderer = null;
	private final List<ConditionalRemark> conditionalRemarks = new ArrayList<ConditionalRemark>();
	private final Map<Integer,Double> columnDividers = new HashMap<Integer,Double>();
	private Message myMessage = null;
	private JPanel remarkPanel = null;
	private String remarkBorder = null;
	private String titleExpression = null;

	// use with care. Here for threading probs
	private int selectedMessageIndex = -1;
	private boolean ignoreColumns = false;
	private List<XMLElement> columnList = new ArrayList<XMLElement>();

	public Object createContainer() {
		TipiMessageTablePanel mm = new TipiMessageTablePanel(myContext);
		mm.setShowRowHeaders(false);
		// Don't register actionPerformed, that is done elsewhere.
		mm.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				messageTableSelectionChanged(e);
			}
		});
		mm.addChangeListener(this);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		mm.doLayout();
		return mm;
	}

	public final void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
		mm = (MessageTablePanel) getContainer();
		mm.removeAllColumns();
		removeAllAggregate();
		columnSize.clear();
		mm.setFooterRenderer(null);
		TipiSwingColumnAttributeParser cap = new TipiSwingColumnAttributeParser();
		messagePath = (String) elm.getAttribute("messagepath");
		if (messagePath != null) {
			if (messagePath.startsWith("'") && messagePath.endsWith("'")) {
				messagePath = messagePath.substring(1, messagePath.length() - 1);
			}
		}
		super.load(elm, instance, context);
		List<XMLElement> children = elm.getChildren();
//		int columnCount = 0;
		columnList.clear();
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			if (child.getName().equals("column")) {
				columnList.add(child);
				String condition = loadColumn(i, child);
				
//				if(condition!=null) {
					addColumnVisiblityCondition(i,condition);
//				}
//				columnCount++;
			}
			if (child.getName().equals("column-attribute")) {
				String name = (String) child.getAttribute("name");
				String type = (String) child.getAttribute("type");
				if (name != null && type != null && !name.equals("") && !type.equals("")) {
					columnAttributes.put(name, cap.parseAttribute(child));
				}
			}
			if (child.getName().equals("remarks")) {
				remarkBorder = (String) child.getAttribute("border");
				List<XMLElement> remarks = child.getChildren();
				for (int j = 0; j < remarks.size(); j++) {
					XMLElement remark = remarks.get(j);
					String condition = (String) remark.getAttribute("condition");
					String remarkString = (String) remark.getAttribute("remark");
					String colorString = (String) remark.getAttribute("color");
					String fontString = (String) remark.getAttribute("font");
					addConditionalRemark(remarkString, condition, colorString, fontString);
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
	}

	private void reloadColumns() {
		mm.removeAllColumns();
		for (XMLElement child : columnList) {
			int i = 0;
			loadColumn(i++, child);
		}
	}
	
	private String loadColumn(int i, XMLElement child) {
		ignoreColumns = false;
		String label = (String) child.getAttribute("label");
		String name = (String) child.getAttribute("name");
		String editableString = (String) child.getAttribute("editable");
		String aggr = child.getStringAttribute("aggregate");
		String condition = child.getStringAttribute("condition");
		String typehint = child.getStringAttribute("typeHint");
		if (aggr != null) {
			addAggregate(i, aggr);
		}
		boolean editable = "true".equals(editableString);
		int size = child.getIntAttribute("size", -1);
		// System.err.println("Putting size for column # "+columnCount+"
		// to: "+size);
		columnSize.put(new Integer(i), new Integer(size));
		// String sizeString = (String) child.getAttribute("size");
		String labelString = label;
		// System.err.println("Label to evaluate: "+labelString);

		try {
			Operand evalLabel = this.getContext().evaluate(labelString, this, null, null);
			if (evalLabel != null) {

				labelString = "" + evalLabel.value;
				// System.err.println("Label evaluated to:
				// "+labelString);

			} else {
				// System.err.println("Null evaluated label.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// System.err.println("Exception while evaluating label:
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
		mm.messageChanged();
		return condition;
	}

	private void addColumnVisiblityCondition(int i, String condition) {
		columnCondition.add(condition);
	}

	public String[] getCustomChildTags() {
		return new String[] { "column", "column-attribute", "remarks", "columndivider" };
	}

	public XMLElement store() {
		TipiSwingColumnAttributeParser cap = new TipiSwingColumnAttributeParser();
		XMLElement xx = super.store();
		MessageTablePanel mm = (MessageTablePanel) getContainer();
		MessageTableModel mtm = mm.getTable().getMessageModel();
		if (!ignoreColumns) {
			for (int i = 0; i < mtm.getColumnCount(); i++) {
				String id = mtm.getColumnId(i);
				String name = mtm.getColumnName(i);
				boolean isEditable = mtm.isColumnEditable(i);
				XMLElement columnDefinition = new CaseSensitiveXMLElement();
				columnDefinition.setName("column");
				columnDefinition.setAttribute("name", id);
				columnDefinition.setAttribute("label", name);
				columnDefinition.setAttribute("editable", "" + isEditable);
				String typeHint = mm.getTypeHint(id);
				if (typeHint != null) {
					columnDefinition.setAttribute("typeHint", typeHint);
				}
				String aggr = getAggregateFunction(i);
				if (aggr != null) {
					columnDefinition.setAttribute("aggregate", aggr);
				}
				Integer sizeInt = columnSize.get(new Integer(i));
				if (sizeInt != null) {
					columnDefinition.setIntAttribute("size", (sizeInt.intValue()));
				}
				xx.addChild(columnDefinition);
			}
		}
		for (Iterator<String> iter = columnAttributes.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			ColumnAttribute ca = columnAttributes.get(name);
			xx.addChild(cap.storeAttribute(ca));
		}
		XMLElement remarks = new CaseSensitiveXMLElement();
			remarks.setName("remarks");
		if (titleExpression != null) {
			remarks.setAttribute("title", titleExpression);
		}
		if (remarkBorder != null) {
			remarks.setAttribute("border", remarkBorder);
		}
		xx.addChild(remarks);
		for (int i = 0; i < conditionalRemarks.size(); i++) {
			ConditionalRemark current = conditionalRemarks.get(i);
			
			XMLElement rem = new CaseSensitiveXMLElement();
			rem.setName("remark");
			if(current.getRemark()!=null) {
				rem.setAttribute("remark", current.getRemark());
			}
			if(current.getCondition()!=null) {
				rem.setAttribute("condition", current.getCondition());
			}
			if(current.getColor()!=null) {
				rem.setAttribute("color", current.getColor());
			}
			if(current.getFont()!=null) {
				rem.setAttribute("font", current.getFont());
			}
			remarks.addChild(rem);
		}
		for (Iterator<Integer> iter = columnDividers.keySet().iterator(); iter.hasNext();) {
			Integer item = iter.next();
			XMLElement cdiv = new CaseSensitiveXMLElement();
			cdiv.setName("columndivider");
			cdiv.setAttribute("index", item);
			cdiv.setAttribute("width", columnDividers.get(item));
			xx.addChild(cdiv);
		}
	

		return xx;
	}

	public void messageTableSelectionChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		updateConditionalRemarks();
		try {
			MessageTablePanel mm = (MessageTablePanel) getContainer();
			Map<String,Object> tempMap = new HashMap<String,Object>();
			tempMap.put("selectedIndex", new Integer(mm.getSelectedRow()));
			tempMap.put("selectedMessage", mm.getSelectedMessage());
			performTipiEvent("onSelectionChanged", tempMap, false);
		} catch (TipiException ex) {
			ex.printStackTrace();
		}
	}

	public void messageTableActionPerformed(ActionEvent ae) {
		try {
			MessageTablePanel mm = (MessageTablePanel) getContainer();
			Map<String,Object> tempMap = new HashMap<String,Object>();
			tempMap.put("selectedIndex", new Integer(mm.getSelectedRow()));
			tempMap.put("selectedMessage", mm.getSelectedMessage());
			performTipiEvent("onActionPerformed", tempMap, false);
		} catch (TipiException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void loadData(final Navajo n, String method) throws TipiException, TipiBreakException {
		// Thread.currentThread().dumpStack();
		super.loadData(n, method);
		flushAggregateValues();
		updateConditionalRemarks();
		reloadColumns();
		final MessageTablePanel mtp = (MessageTablePanel) getContainer();
		if (messagePath != null && n != null) {
			final Message m = n.getMessage(messagePath);
			myMessage = m;
			// System.err.println("MEssage: "+myMessage);
			if (m != null) {
				runSyncInEventThread(new Runnable() {
					public void run() {
						if (columnSize.size() == 0) {
							mtp.createColumnsFromDef(m);
							ignoreColumns = true;
						}
						mtp.setMessage(m);
					    mtp.getTable().updateTableSize();
					    mtp.updateTableSize();
					    updateColumnVisibility();
					}
				});
			}
		}
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("service", method);
		performTipiEvent("onLoad", m, true);

	}

	protected void updateColumnVisibility() {
		System.err.println("COLUMN CONDITIONS: "+columnCondition);
		for (int index= columnCondition.size()-1;index>=0;index--) {
//		for (int index: columnCondition.keySet()) {
			try {
				String condition = columnCondition.get(index);
				if(condition==null) {
					continue;
				}
				System.err.println("Checking: "+condition);
				Operand o = evaluate(condition,this,null);
				if(o==null) {
					continue;
				}
				if(o.value instanceof Boolean) {
					System.err.println("Successful binary evaluation: "+o.value);
					setColumnVisible(index, ((Boolean)o.value).booleanValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mm.createDefaultColumnsFromModel();
	}

	/*
	 * Overridden, to pervent automatically call perform the onload event.
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.tipi.components.core.TipiDataComponentImpl#doPerformOnLoad(java.lang.String)
	 */
	protected void doPerformOnLoad(String method) throws TipiException {
	}

	public void setComponentValue(final String name, final Object object) {
		if (name.equals("filtersvisible")) {
			setFiltersVisible(Boolean.valueOf(object.toString()).booleanValue());
		}
		if (name.equals("hideColumn")) {
			setColumnVisible(object.toString(), false);
		}
		if (name.equals("showColumn")) {
			setColumnVisible(object.toString(), true);
		}
		if (name.equals("columnsvisible")) {
			setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
		}
		if (name.equals("sortable")) {
			mm.setSortingAllowed(Boolean.valueOf(object.toString()).booleanValue());
		}
		if (name.equals("headervisible")) {
			setHeaderVisible(Boolean.valueOf(object.toString()).booleanValue());
		}
		if (name.equals("readOnly")) {
			mm.setReadOnly(Boolean.valueOf(object.toString()).booleanValue());
		}
		if (name.equals("selectedIndex")) {
			selectedMessageIndex = ((Integer) object).intValue();
			runSyncInEventThread(new Runnable() {
				public void run() {
					mm.setSelectedRow(selectedMessageIndex);
				}
			});
			// setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
		}
		if (name.equals("rowHeight")) {
			mm.setRowHeight(((Integer) object).intValue());
			// setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
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

			runSyncInEventThread(new Runnable() {
				public void run() {
					System.err.println("SETTING SAVE PATH: " + object.toString());
					setColumnDefinitionSavePath(object.toString());
				}
			});
		}
		if (name.equals("filtermode")) {
			mm.setFilterMode("" + object);
		}
		if (name.equals("refreshAfterEdit")) {
			mm.setRefreshAfterEdit(Boolean.valueOf(object.toString()).booleanValue());
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
		super.setComponentValue(name, object);
	}

	/**
	 * Uber deprecated, no idea why this is still here.
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
		//TableColumn tc = mm.getTable().getColumnModel().getColumn(index);
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
			if (name.equals("selectedMessages")) {
				List<Message> all = mm.getSelectedMessages();
				if (all != null && all.size() > 0) {
					Navajo n = NavajoFactory.getInstance().createNavajo();
					Message array = NavajoFactory.getInstance().createMessage(n, all.get(0).getName(), Message.MSG_TYPE_ARRAY);
					for (int i = 0; i < all.size(); i++) {
						Message cur = all.get(i);
						array.addMessage(cur);
					}
					// array.write(System.err);
					return array;
				} else {
					System.err.println("AAp.. all is null of 0");
				}
			}
			if (name.equals("filteredMessage")) {
				Message m = mm.getMessageAsPresentedOnTheScreen(true);
				return m;
			} else if (name.equals("selectedIndex")) {
				runSyncInEventThread(new Runnable() {
					public void run() {
						if(mm!=null) {
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

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
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
			if ("showEditDialog".equals(name)) {
				Operand title = compMeth.getEvaluatedParameter("title", event);
				try {
					String titleString;
					titleString = title == null ? "Aap" : "" + title.value;
					mm.showEditDialog(titleString, mm.getSelectedRow());
				} catch (Exception ex1) {
					ex1.printStackTrace();
				}
			}
		}
		if ("export".equals(name)) {
			Operand filename = compMeth.getEvaluatedParameter("filename", event);
			Operand delimiter = compMeth.getEvaluatedParameter("delimiter", event);
			// doExportAll();
			mm.getTable().exportTable((String) filename.value, (String) delimiter.value);
		}

		if ("setAllSelected".equals(name)) {
			System.err.println("In setAllSelected");
			Operand propertyName = compMeth.getEvaluatedParameter("propertyName", event);
			Operand value = compMeth.getEvaluatedParameter("value", event);
			System.err.println("Value: " + value.value);
			System.err.println("PropertyName: " + propertyName.value);
			ArrayList<Message> al = mm.getSelectedMessages();
			System.err.println("# of selected msgs: " + al.size());
			for (int i = 0; i < al.size(); i++) {
				Message current = al.get(i);
				Property cp = current.getProperty("" + propertyName.value);
				try {
					System.err.println("Property: " + cp.getFullPropertyName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				cp.setAnyValue(value.value);
			}
		}

		if ("fireAction".equals(name)) {
			try {
				performTipiEvent("onActionPerformed", null, false);
				// for (int i = 0; i < getEventList().size(); i++) {
				// TipiEvent current = (TipiEvent) getEventList().get(i);
				// if (current.isTrigger("onActionPerformed", "aap")) {
				// try {
				// current.performAction(current);
				// }
				// catch (TipiException ex) {
				// ex.printStackTrace();
				// }
				// }
				// }
			} catch (TipiException e) {
				e.printStackTrace();
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

		if ("doRunReport".equals(name)) {
			Operand format = compMeth.getEvaluatedParameter("format", event);
			Operand marginsOperand = compMeth.getEvaluatedParameter("margins", event);
			Operand orientationOperand = compMeth.getEvaluatedParameter("orientation", event);
			String orientation = null;
			if (orientationOperand != null) {
				orientation = (String) orientationOperand.value;
			}
			int[] margin = null;

			if (marginsOperand != null) {
				margin = new int[4];
				String marginString = (String) marginsOperand.value;
				StringTokenizer st = new StringTokenizer(marginString, ",");
				margin[0] = Integer.parseInt(st.nextToken());
				margin[1] = Integer.parseInt(st.nextToken());
				margin[2] = Integer.parseInt(st.nextToken());
				margin[3] = Integer.parseInt(st.nextToken());
			}
			try {
				doRunReport((String) format.value, orientation, margin);
			} catch (TipiException e) {
				e.printStackTrace();
			}
		}

	}

	public void setColumnDefinitionSavePath(String path) {
		mm.setColumnDefinitionSavePath(path);
	}

	@SuppressWarnings("unchecked")
	public void stateChanged(ChangeEvent e) {
		Map<String,Object> m = (Map<String,Object>) e.getSource();
		System.err.println("StateChanged in TABLE");
		flushAggregateValues();
		updateConditionalRemarks();
		mm.repaint();
		try {
			performTipiEvent("onValueChanged", m, false);
		} catch (TipiException ex) {
			ex.printStackTrace();
		}
	}

	public void addAggregate(int columnIndex, String expression) {
		if (myFooterRenderer == null) {
			myFooterRenderer = new MessageTableFooterRenderer(this);
		}
		mm.setFooterRenderer(myFooterRenderer);
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
			Operand oo = getContext().evaluate(current.getCondition(), this, null, myMessage);
			boolean complies = false;
			if (oo.value != null) {
				Boolean b = (Boolean) oo.value;
				complies = b.booleanValue();
			}
			if (complies) {
				Operand o = myContext.evaluate(current.getRemark(), this, null, mm.getMessage());
				Operand q = myContext.evaluate(current.getColor(), this, null, mm.getMessage());
				Operand r = myContext.evaluate(current.getFont(), this, null, mm.getMessage());
				Color c = q == null ? null : (Color) q.value;
				Font f = r == null ? null : (Font) r.value;
				// Operand o = evaluate(current.getRemark(),this,null);
				remarkPanel.add(createRemark("" + o.value, c, f), new GridBagConstraints(0, complied, 1, 1, 1.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
				System.err.println("COMPLYING:  ");
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

	public void addConditionalRemark(String remark, String condition, String c, String font) {
		ConditionalRemark cr = new ConditionalRemark(this, remark, condition,  c, font);
		conditionalRemarks.add(cr);
		System.err.println("************************\nCreating remark panel\n********************************\n");
		if (remarkPanel == null) {
			createRemarkPanel();
		}
		System.err.println("size:");
	}

	private final void createRemarkPanel() {
		remarkPanel = new JPanel();
		Operand r = myContext.evaluate(remarkBorder, this, null, mm.getMessage());
		Border b = r == null ? null : (Border) r.value;
		if (b != null) {
			remarkPanel.setBorder(b);
		}
		remarkPanel.setVisible(false);
		remarkPanel.setLayout(new GridBagLayout());
		// mm.add(remarkPanel, BorderLayout.SOUTH);
		mm.add(remarkPanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
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

	public void doRunReport(String format, String orientation, int[] margins) throws TipiException {
		Binary b;
		try {
			b = mm.getTableReport(format, orientation, margins);
		} catch (NavajoException e) {
			throw new TipiException("Error calling birt report!", e);
		}
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("report", b);
		m.put("format", format);
		performTipiEvent("onReportFinished", m, false);
	}

}
