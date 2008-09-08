package com.dexels.navajo.tipi.components.echoimpl;

import java.util.*;

import nextapp.echo2.app.*;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.extras.app.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.echoclient.components.MessageTable;
import com.dexels.navajo.echoclient.components.PageNavigator;
import com.dexels.navajo.echoclient.components.Styles;
import com.dexels.navajo.echoclient.components.TableEditorListener;
import com.dexels.navajo.echoclient.components.PageNavigator.*;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.echoimpl.impl.*;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.XMLElement;

import echopointng.ContainerEx;
import echopointng.able.Positionable;
import echopointng.table.PageableTableNavigation;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiTable extends TipiEchoDataComponentImpl {

	private String messagePath = "";

	private boolean colDefs = false;

	private MessageTable myTable;

	private PageNavigator pageNavigator;
	private TransitionPane myTransitionPane;

	private int currentTableIndex = 1;

	private SplitPane myPane;

	public TipiTable() {
	}

	public Object createContainer() {
		myPane = new SplitPane(SplitPane.ORIENTATION_VERTICAL, new Extent(25, Extent.PX));
		pageNavigator = new PageNavigator();
		myTable = new MessageTable();
		pageNavigator.setTable(myTable);
		pageNavigator.initialize();
		myTransitionPane = new TransitionPane();
		myTransitionPane.setDuration(500);
		myTransitionPane.setType(TransitionPane.TYPE_FADE_TO_WHITE);
		ContentPane cp = new ContentPane();
		myTransitionPane.add(cp);
		cp.add(myTable);
		myPane.add(pageNavigator);
		myPane.add(myTransitionPane);
		// myTransitionPane.add(myTable);
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(MessageTable.class, "Default");
		myTable.setStyle(ss);
		myTable.setRolloverEnabled(false);
		myTable.addSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Map<String, Object> tempMap = new HashMap<String, Object>();
					tempMap.put("selectedIndex", new Integer(myTable.getSelectedIndex()));
					tempMap.put("selectedMessage", myTable.getSelectedMessage());
					performTipiEvent("onSelectionChanged", tempMap, false);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		myTable.addTableEditorListener(new TableEditorListener() {

			public void propertyChanged(Property p, String eventType, int column, int row) {
				Map event = new HashMap();
				event.put("column", new Integer(column));
				event.put("row", new Integer(row));
				event.put("new", p.getValue());
				event.put("message", p.getParentMessage());
				event.put("name", p.getName());
				try {
					performTipiEvent(eventType, event, true);
				} catch (TipiException e) {
					e.printStackTrace();
				}

			}
		});

		pageNavigator.addPageIndexChangeListener(new PageIndexChangeListener() {

			public void pageIndexChanged(PageIndexChangeEvent e) {
				int newPage = e.getNewPageIndex();
				if (newPage == currentTableIndex) {
					return;
				}
				boolean b = newPage > currentTableIndex;

				System.err.println("Swtitching");
				myTransitionPane.setType(!b ? TransitionPane.TYPE_CAMERA_PAN_LEFT : TransitionPane.TYPE_CAMERA_PAN_RIGHT);
				myTransitionPane.removeAll();
				ContentPane cp = new ContentPane();
				// myTransitionPane.setType(!b?TransitionPane.TYPE_CAMERA_PAN_LEFT:TransitionPane.TYPE_CAMERA_PAN_RIGHT);
				myTransitionPane.add(cp);
				cp.add(myTable);
				currentTableIndex = newPage;

			}
		});
		myTable.addPrintListener(new ActionListener(){

			public void actionPerformed(ActionEvent ae) {
				printTable((Binary) ae.getSource());
			}});
		return myPane;
	}

	public Object getActualComponent() {
		return myTable;
	}

	public void loadData(final Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);
		runAsyncInEventThread(new Runnable() {

			public void run() {
				MessageTable mm = (MessageTable) getActualComponent();
				Message m = n.getMessage(messagePath);
				if (m != null) {
					if (!colDefs) {
						mm.removeAllColumns();
						ArrayList props = m.getMessage(0).getAllProperties();
						for (int i = 0; i < props.size(); i++) {
							Property p = (Property) props.get(i);
							mm.addColumn(p.getName(), p.getName(), false, -1);
						}
					}
					mm.setMessage(m);
				}
				pageNavigator.setTotalPages(myTable.getTotalPages());
				pageNavigator.addPageIndexChangeListener(myTable);
				pageNavigator.setPageIndex(0);
//				myTable.setPageNavigator(pageNavigator);
			}
		});
	}

	public Object getComponentValue(String name) {
		MessageTable mm = (MessageTable) getActualComponent();
		if ("selectedMessage".equals(name)) {
			return mm.getSelectedMessage();
		}
		if (name.equals("selectedIndex")) {
			return new Integer(mm.getSelectedIndex());
		}
		return super.getComponentValue(name);
	}

	public void load(final XMLElement elm, final XMLElement instance, final TipiContext context)
			throws com.dexels.navajo.tipi.TipiException {

		runSyncInEventThread(new Runnable() {

			public void run() {
				MessageTable mm = (MessageTable) getActualComponent();

				boolean editableColumnsFound = false;

				String rowsPerPage = (String) elm.getAttribute("rowsPerPage");
				if (rowsPerPage != null) {
					int rpp = Integer.parseInt(rowsPerPage);
					MessageTable xmm = (MessageTable) getActualComponent();
					if (xmm != null) {
						pageNavigator.setVisible(rpp == 0);
						myPane.setSeparatorPosition(rpp == 0 ? new Extent(0, Extent.PX) : new Extent(25, Extent.PX));
						if (rpp > 0) {
							xmm.setRowsPerPage(rpp);
						}
					}
				}
				messagePath = (String) elm.getAttribute("messagepath");
				if (messagePath != null) {
					if (messagePath.startsWith("'") && messagePath.endsWith("'")) {
						messagePath = messagePath.substring(1, messagePath.length() - 1);
					}
				}
				try {
					TipiTable.super.load(elm, instance, context);
				} catch (TipiException e) {
					e.printStackTrace();
				}
				List<XMLElement> children = elm.getChildren();
				for (int i = 0; i < children.size(); i++) {
					XMLElement child = children.get(i);
					if (child.getName().equals("column")) {
						Operand o = evaluate(child.getStringAttribute("label"), TipiTable.this, null);
						String label = null;
						if (o == null) {
							label = "";
						} else {
							label = (String) o.value;
							if (label == null) {
								label = "";
							}
						}
						String name = (String) child.getAttribute("name");
						String editableString = (String) child.getAttribute("editable");
						int size = child.getIntAttribute("size", -1);

						boolean editable = "true".equals(editableString);
						colDefs = true;
						mm.addColumn(name, label, editable, size);
						editableColumnsFound = editableColumnsFound || editable;
						// mm.messageChanged();
					}
					if (child.getName().equals("column-attribute")) {
						String name = (String) child.getAttribute("name");
						String type = (String) child.getAttribute("type");
						// if (name != null && type != null && !name.equals("")
						// &&
						// !type.equals("")) {
						// columnAttributes.put(name,
						// cap.parseAttribute(child));
						// }
					}
				}
				mm.setSelectionEnabled(!editableColumnsFound);
				// mm.setColumnAttributes(columnAttributes);
			}
		});

	}

	public String[] getCustomChildTags() {
		return new String[] { "column", "column-attribute" };
	}

	// public void processStyles() {
	// super.processStyles();
	// Color c = ColorParser.parseColor(getStyle("foreground"));
	// if (c!=null) {
	// myTable.setForeground(c);
	// }
	// c = ColorParser.parseColor(getStyle("background"));
	// if (c!=null) {
	// myTable.setBackground(c);
	// }
	// String headHeight = getStyle("headerheight");
	// if (headHeight!=null) {
	// int hh = Integer.parseInt(headHeight);
	// myTable.setHeaderHeight(hh);
	// }
	//      
	// }
	//    
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
		int count = myTable.getModel().getRowCount();
		if (count != 0) {
			if ("selectNext".equals(name)) {
				int r = myTable.getSelectedIndex();
				if ((r < count - 1)) {
					myTable.setSelectedIndex(r + 1);
				}
				return;
			}

			if ("selectPrevious".equals(name)) {
				int r = myTable.getSelectedIndex();
				if ((r > 0)) {
					myTable.setSelectedIndex(r - 1);
				}
				return;
			}
			if ("selectFirst".equals(name)) {
				myTable.setSelectedIndex(0);
			}
			if ("selectLast".equals(name)) {
				myTable.setSelectedIndex(count - 1);
			}
			if ("printReport".equals(name)) {
				Binary b;
				try {
					b = getTableReport("pdf", "horizontal", new int[] { 10, 10, 10, 10 });
					printTable(b);
				} catch (NavajoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void printTable(Binary b) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("report", b);
			performTipiEvent("onReport", param, false);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	public Binary getTableReport(String format, String orientation, int[] margins) throws NavajoException {
		return myTable.getTableReport(format, orientation, margins);
	}
}
