package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiDataComponent;
import com.dexels.navajo.tipi.swingclient.components.GenericPropertyComponent;
import com.dexels.navajo.tipi.swingclient.components.MessageTable;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class MessageTableFooterRenderer extends JLabel implements
		TableCellRenderer {
	private static final long serialVersionUID = 3302229790598054162L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MessageTableFooterRenderer.class);
	private final TipiDataComponent myComponent;

	private GenericPropertyComponent myPropComponent = new GenericPropertyComponent();

	private boolean initialized = false;
	private final Map<Integer, String> aggregateMap = new HashMap<Integer, String>();
	private final Map<Integer, Operand> aggregateValueMap = new HashMap<Integer, Operand>();

	public MessageTableFooterRenderer(TipiDataComponent tc) {
		super();
		myComponent = tc;
		setBorder(null);
		setOpaque(false);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if (!initialized) {
			myPropComponent.setLabelVisible(false);
			try {
				initialized = true;
			} catch (Exception e) {
				logger.error("Error detected",e);
			}
		}
		myPropComponent.setForeground(Color.black);
		myPropComponent.hideBorder();
		myPropComponent.setOpaque(false);
		MessageTable mm = (MessageTable) table;

		String expr = getAggregateFunction(column);
		if (expr == null) {
			setupProp(mm, new Operand(null, "String", null), column);
			return myPropComponent;
		} else {
			try {
				Operand o = myComponent.getContext().evaluate(expr,
						myComponent, null, mm.getMessage());
				if (o == null) {
					setText("null");
					return this;
				} else {
					setupProp(mm, o, column);
					aggregateValueMap.put(new Integer(column), o);
					myPropComponent.hideBorder();
					return myPropComponent;
				}
			} catch (Exception ex) {
				logger.error("Error detected",ex);
				setForeground(Color.red);
				setText("error");
				return this;
			}
		}

	}

	private final void setupProp(MessageTable mm, Operand val, int column) {
		if (val != null) {
			Property p = null;
			try {
				p = NavajoFactory.getInstance().createProperty(null,
						mm.getColumnId(column), val.type, "", 0,
						mm.getColumnName(column), Property.DIR_OUT);
				p.setAnyValue(val.value);
			} catch (NavajoException ex1) {
				logger.error("Error detected",ex1);
			}
			myPropComponent.setProperty(p);
			// myPropComponent
			// logger.debug("TYPE: "+p.getType()+" name: "+p.getName()+" value: "+p.getValue());
		}
	}

	public void addAggregate(int columnIndex, String expression) {
		aggregateMap.put(new Integer(columnIndex), expression);
	}

	public void flushAggregateValues() {
		aggregateValueMap.clear();
	}

	public void removeAggregate(int columnIndex) {
		aggregateMap.remove(new Integer(columnIndex));
	}

	public void removeAllAggregate() {
		aggregateMap.clear();
	}

	public String getAggregateFunction(int column) {
		return aggregateMap.get(new Integer(column));
	}

	public Set<Integer> getAggregateFunctions() {
		return aggregateMap.keySet();
	}
}
