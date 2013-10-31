package com.dexels.navajo.tipi.swingclient.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.BinaryOpenerFactory;
import com.dexels.navajo.document.ExpressionChangedException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.swingclient.SwingClient;



@SuppressWarnings("deprecation")
public class MessageTablePanel extends BasePanel implements CopyCompatible,
		Printable {

	private static final long serialVersionUID = 2048693736371650314L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MessageTablePanel.class);
	
	final JScrollPane jScrollPane1 = new JScrollPane(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	final MessageTable messageTable;
	private boolean ghosted = false;

	private boolean useScroll = true;

	private boolean enabled = true;
	private boolean showColumnEditDialog = false;
	final FilterPanel filterPanel = new FilterPanel();
	private Map<String, ColumnAttribute> columnAttributes;
	private List<HeaderMenuListener> headerMenuListeners = new ArrayList<HeaderMenuListener>();
	private boolean copyMenuVisible = false;

	private MessageTableFooter tableFooter = null;

	public MessageTablePanel() {
		this(null);
	}

	public MessageTablePanel(MessageTable t) {
		super();
		if (t == null) {
			messageTable = new MessageTable();
		} else {
			messageTable = t;
		}
		jScrollPane1.add(messageTable);

		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
		filterPanel.setFiltersVisible(false);
		filterPanel.setColumnsVisible(false);
		filterPanel.setMessageTable(messageTable);
		JTableHeader th = messageTable.getTableHeader();
		th.addMouseListener(new MouseAdapter() {
			@Override
			public final void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					firePopupEvent(e);
				}
			}

			@Override
			public final void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					firePopupEvent(e);
				}
			}
		});
		messageTable.addMouseListener(new MouseAdapter() {
			@Override
			public final void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popupInTable(e);
				}
			}

			@Override
			public final void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popupInTable(e);
				}
			}
		});

	}

	public void editCell(Property p) {
		messageTable.editCellAt(p);
	}

	public void setShowColumnsItem(boolean b) {
		showColumnEditDialog = b;
	}

	public boolean hasChanged() {
		return messageTable.hasChanged();
	}

	public void addChangeListener(ChangeListener c) {
		messageTable.addChangeListener(c);
	}

	public void removeChangeListener(ChangeListener c) {
		messageTable.removeChangeListener(c);
	}

	public JScrollPane getScrollPane() {
		return jScrollPane1;
	}

	private final void copyMe() {
		SwingClient.getUserInterface().copyToClipBoard(this);
	}

	public void setCopyMenuVisible(boolean state) {
		copyMenuVisible = state;
	}

	public void addHeaderMenuListener(HeaderMenuListener h) {
		headerMenuListeners.add(h);
	}

	public void removeHeaderMenuListener(HeaderMenuListener h) {
		headerMenuListeners.remove(h);
	}

	public void fireHeaderMenuEvent() {
		for (int i = 0; i < headerMenuListeners.size(); i++) {
			HeaderMenuListener h = headerMenuListeners.get(i);
			h.fireHeaderMenuEvent();
		}
	}

	final void popupInTable(MouseEvent e) {
		if (copyMenuVisible) {
			JPopupMenu pop = new JPopupMenu();
			JMenuItem sel = new JMenuItem("Copy");
			JMenuItem emp = new JMenuItem("Empty clipboard");
			pop.add(sel);
			pop.add(emp);
			sel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					copyMe();
				}
			});
			emp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SwingClient.getUserInterface().clearClipboard();
				}
			});
			Point p = e.getPoint();
			pop.show(messageTable, (int) p.getX(), (int) p.getY());
		}
	}

	public void firePopupEvent(MouseEvent e) {
		logger.info("Popupevent fired!!!!");

		final int col = messageTable.getColumnModel().getColumnIndexAtX(
				e.getX());
		if (messageTable.isCellEditable(0, col)) {
			Object o = messageTable.getValueAt(0, col);
			if (!Property.class.isInstance(o) && showColumnEditDialog) {
				// we're in a propertyless column
				JPopupMenu pop = new JPopupMenu();
				JMenuItem sel1 = new JMenuItem("Kolominstellingen wijzigen");
				sel1.addActionListener(new ActionListener() {
					@Override
					public final void actionPerformed(ActionEvent e) {
						try {
							filterPanel.showColumnManagementDialog();
							filterPanel.loadColumns();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				pop.add(sel1);
				JMenuItem sel = new JMenuItem("Kolominstellingen opslaan");
				sel.addActionListener(new ActionListener() {
					@Override
					public final void actionPerformed(ActionEvent e) {
						try {
							getTable().saveColumnsNavajo();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				pop.add(sel);
				JMenuItem sel2 = new JMenuItem("Kolominstellingen verwijderen");
				sel2.addActionListener(new ActionListener() {
					@Override
					public final void actionPerformed(ActionEvent e) {
						try {
							getTable().removeColumnsNavajo();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				pop.add(sel2);

				if (showColumnEditDialog) {
					JMenuItem sel3 = new JMenuItem("Wijzig kolommen");
					sel3.addActionListener(new ActionListener() {
						@Override
						public final void actionPerformed(ActionEvent e) {
							try {
								getFilterPanel().showColumnManagementDialog();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
					pop.add(sel3);

				}
				JMenuItem sel4 = new JMenuItem("Afdrukken");
				sel4.addActionListener(new ActionListener() {
					@Override
					public final void actionPerformed(ActionEvent e) {
						try {
							Binary b = getTableReport(null, null, null);
							BinaryOpenerFactory.getInstance().open(b);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				pop.add(sel4);

				// JMenuItem sel5 = new JMenuItem("Toon HTML bestand");
				// sel5.addActionListener(new ActionListener() {
				// public final void actionPerformed(ActionEvent e) {
				// try {
				// Binary b = getTableReport("html");
				// MergeUtils.openDocument(b,"html");
				// logger.info("BINARIIIII: "+b.getLength()+" extension: "+b.getExtension());
				// }
				// catch (Exception ex) {
				// ex.printStackTrace();
				// }
				// }
				// });
				// pop.add(sel5);
				//
				//
				//
				// JMenuItem sel6 = new JMenuItem("Toon Word bestand");
				// sel6.addActionListener(new ActionListener() {
				// public final void actionPerformed(ActionEvent e) {
				// try {
				// Binary b = getTableReport("doc");
				// MergeUtils.openDocument(b,"doc");
				// logger.info("BINARIIIII: "+b.getLength()+" extension: "+b.getExtension());
				// }
				// catch (Exception ex) {
				// ex.printStackTrace();
				// }
				// }
				// });
				// pop.add(sel6);
				//

				pop.show(this, e.getX(), e.getY());
			} else {
				Property p = (Property) o;
				if (p.getType().equals("boolean")) {
					JPopupMenu pop = new JPopupMenu();
					JMenuItem sel = new JMenuItem("Selecteer alles");
					JMenuItem dsel = new JMenuItem("Deselecteer alles");
					JMenuItem inv = new JMenuItem("Selectie omkeren");
					inv.addActionListener(new ActionListener() {
						@Override
						public final void actionPerformed(ActionEvent e) {
							invertSelection(col);
							setAllUpdateFlags();
						}
					});
					sel.addActionListener(new ActionListener() {
						@Override
						public final void actionPerformed(ActionEvent e) {
							setSelectAll(col, true);
							setAllUpdateFlags();
						}
					});
					dsel.addActionListener(new ActionListener() {
						@Override
						public final void actionPerformed(ActionEvent e) {
							setSelectAll(col, false);
							setAllUpdateFlags();
						}
					});
					pop.add(sel);
					pop.add(dsel);
					pop.add(inv);
					pop.show(this, e.getX(), e.getY());
				} else if (p.isDirIn()) {
					final JPopupMenu pop = new JPopupMenu();
//					final int[] selectedRows = messageTable.getSelectedRows();
					final GenericPropertyComponent comp = new GenericPropertyComponent();
					Property clone = p.copy(NavajoFactory.getInstance()
							.createNavajo());
					clone.setValue("");

					comp.setProperty(clone);
					comp.setLabel(getColumnName(col));
					comp.setForcedTotalWidth(200);

					pop.add(comp);
					pop.show(this, e.getX(), e.getY());
					// !!!comp.requestFocusInWindow(); SETTING FOCUS RESULTS IN
					// UNDESIRED BEHAVIOUR!

					comp.addPropertyEventListener(new PropertyEventListener() {
						@Override
						public void propertyEventFired(Property p, String id,
								boolean internal) {
							if ("onActionPerformed".equals(id)) {
								pop.setVisible(false);
							}
							if ("onFocusLost".equals(id)) {
								setColumnValue(p.getName(), comp.getProperty()
										.getValue());
							}
						}

					});
				}
			}
		} else {
			logger.info("Column is not editable..");
		}
	}

	private final void setColumnValue(String column, String value) {
		try {
			List<Message> selected = getSelectedMessages();
			for (int i = 0; i < selected.size(); i++) {
				Message row = selected.get(i);
				Property p = row.getProperty(column);
				if (p.isDirIn()) {
					p.setValue(value);
					if (row.getProperty("Update") != null) {
						row.getProperty("Update").setValue(true);
					}
				}
			}
			fireDataChanged();
			fireHeaderMenuEvent();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public final void setAutoStoreColumnSizes(String path, boolean value) {
		messageTable.setAutoStoreColumnSizes(path, value);
	}

	private final void invertSelection(int column) {
		List<Message> selected = getSelectedMessages();
		if (selected != null && selected.size() > 0) {
			for (int i = 0; i < selected.size(); i++) {
				Object o = messageTable.getValueAt(i, column);
				Property q = (Property) o;
				Message m = selected.get(i);
				Property p = m.getProperty(q.getName());
				boolean value = ((Boolean) p.getTypedValue()).booleanValue();
				p.setValue(!value);
				try {
					messageTable.fireChangeEvents(p, !value, value);
					p.getParentMessage().refreshExpression();
				} catch (NavajoException e) {
					logger.error("Error: ",e);
				} catch (ExpressionChangedException e) {
				}
			}
		} else {
			final int rowCount = getRowCount();
			for (int i = 0; i < rowCount; i++) {
				Object o = messageTable.getValueAt(i, column);
				Property p = (Property) o;
				boolean value = ((Boolean) p.getTypedValue()).booleanValue();
				p.setValue(!value);
				try {
					messageTable.fireChangeEvents(p, !value, value);
					p.getParentMessage().refreshExpression();
				} catch (NavajoException e) {
					logger.error("Error: ",e);
				} catch (ExpressionChangedException e) {
				}
			}
		}
		fireDataChanged();
		fireHeaderMenuEvent();
		doSort(getSortedColumn(), getSortingDirection());
	}

	private final void setSelectAll(int column, boolean value) {
		ArrayList<Message> selected = getSelectedMessages();
		if (selected != null && selected.size() > 0) {
			for (int i = 0; i < selected.size(); i++) {
				Object o = messageTable.getValueAt(i, column);
				Property q = (Property) o;
				Message m = selected.get(i);
				Property p = m.getProperty(q.getName());
				if (((Boolean) p.getTypedValue()).booleanValue() != value) {
					try {
						Object oldValue = p.getTypedValue();
						p.setValue(value);
						messageTable.fireChangeEvents(p, oldValue, value);
						m.refreshExpression();
					} catch (NavajoException e) {
						logger.error("Error: ",e);
					} catch (ExpressionChangedException e) {
					}
				}
			}
		} else {

			selected = getMessage().getAllMessages();
			final int rowCount = getRowCount();
			for (int i = 0; i < rowCount; i++) {
				Object o = messageTable.getValueAt(i, column);
				Message m = selected.get(i);
				Property p = m.getProperty(((Property) o).getName());
				if (((Boolean) p.getTypedValue()).booleanValue() != value) {
					try {
						Object oldValue = p.getTypedValue();
						p.setValue(value);
						messageTable.fireChangeEvents(p, oldValue, value);
						p.getParentMessage().refreshExpression();
					} catch (NavajoException e) {
						logger.error("Error: ",e);
					} catch (ExpressionChangedException e) {
					}
				}
			}
		}
		fireDataChanged();
		fireHeaderMenuEvent();
		doSort(getSortedColumn(), getSortingDirection());
	}

	public void setAllUpdateFlags() {
		// Used in AdvancedMessageTable only.
	}

	public final void setRowColor(int row, Color c) {
		messageTable.setRowColor(row, c);
	}

	public final Color getRowColor(int row) {
		return messageTable.getRowColor(row);
	}

	public final void setColumnAttributes(Map<String, ColumnAttribute> m) {
		columnAttributes = m;
		messageTable.setColumnAttributes(columnAttributes);
	}

	public final void setHeaderVisible(boolean b) {
		// if (jScrollPane1.getColumnHeader()!=null) {
		// jScrollPane1.getColumnHeader().setVisible(b);
		// }
		messageTable.setHeaderVisible(b);
	}

	public final MessageTable getTable() {
		return messageTable;
	}

	public final void fireActionEvent() {
		messageTable.fireActionEvent();
	}

	private final void jbInit() throws Exception {
		this.setLayout(new GridBagLayout());
		messageTable
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					@Override
					public final void valueChanged(ListSelectionEvent e) {
						messageTable_valueChanged(e);
					}
				});
		// this.add(jScrollPane1, BorderLayout.CENTER);
		// this.add(filterPanel, BorderLayout.NORTH);

		this.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
						0, 0, 0), 0, 0));
		this.add(filterPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		// this.add(tableFooter, BorderLayout.SOUTH);
		jScrollPane1.getViewport().add(messageTable, null);
		jScrollPane1.setColumnHeader(null);
		setOpaque(false);
		jScrollPane1.setOpaque(false);
		jScrollPane1.getViewport().setOpaque(false);
	}

	public final void setSelectionMode(int i) {
		messageTable.setSelectionMode(i);
	}

	public void addToolbar(JToolBar b) {
		this.add(b, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
	}

	public void clearTable() {
		/** @todo Implement? Or just leave it? */

		setMessage(NavajoFactory.getInstance().createMessage(
				NavajoFactory.getInstance().createNavajo(), "no_message",
				Message.MSG_TYPE_ARRAY));
		messageTable.messageChanged();
		messageTable.rebuildSort();
	}

	public final void rebuildSort() {
		messageTable.rebuildSort();
	}

	public final void messageChanged() {
		messageTable.messageChanged();
	}

	public void setMessage(Message m) {

		messageTable.setMessage(m);
		if (filterPanel.isVisible()) {
			filterPanel.setMessageTable(messageTable);
		}
		createDefaultColumnsFromModel();
		setLoadMessage(m);
		if (messageTable.hasPropertyFilters()) {
			messageTable.performFilters();
		}
		revalidate();
	}

	public void updateColumnSizes() {
		((MessageTableColumnModel) messageTable.getColumnModel())
				.loadSizes(messageTable.columnSizeMap);

	}

	public void createDefaultColumnsFromModel() {
		messageTable.createDefaultColumnsFromModel();
	}

	public void updateTableSize() {
		if (!useScroll) {
			Dimension pref = messageTable.getPreferredSize();
			pref.height = messageTable.getRowHeight() * (getRowCount() + 1)
					+ 16 + messageTable.getTableHeader().getHeight()
					+ messageTable.getHeaderHeight();
			if (tableFooter != null) {
				pref.height += tableFooter.getHeight();
			}
			jScrollPane1.setPreferredSize(pref);
			revalidate();
		} else {
			jScrollPane1.setPreferredSize(messageTable
					.getPreferredScrollableViewportSize());

		}
	}

	public final Message getMessage() {
		return messageTable.getMessage();
	}

	public void exportTable(String filename, String delimiter) {
		messageTable.exportTable(filename, delimiter);
	}

	public void exportTable(FileWriter fw, String delimiter,
			boolean useSortedClone) {
		messageTable.exportTable(fw, delimiter, useSortedClone);
	}

	// /**
	// * @deprecated
	// */
	//
	// public final void addColumn(String id, String title, Class c, boolean
	// editable) {
	// addColumn(id, title, editable);
	// }

	public final void removeColumn(String id) {
		messageTable.removeColumn(id);
		messageTable.getMessageModel().fireTableStructureChanged();
		messageTable.createDefaultColumnsFromModel();
	}

	public final String getSelectedColumn() {
		int col = messageTable.getSelectedColumn();
		if (col > -1) {
			// logger.info("Columnname: " +
			// messageTable.getMessageModel().getColumnName(col));
			// logger.info("ColumnId  : " +
			// messageTable.getMessageModel().getColumnId(col));
			return messageTable.getMessageModel().getColumnId(col);
		} else {
			// logger.info("No column selected");
			return null;
		}
	}

	public final void addSubMessage(Message msg) {
		if (getMessage() == null) {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			Message m = NavajoFactory.getInstance().createMessage(n,
					msg.getName(), Message.MSG_TYPE_ARRAY);
			try {
				n.addMessage(m);
				setMessage(m);
			} catch (Exception e) {
				logger.error("Error: ",e);
			}

		}
		if (getMessage().getArraySize() == 0) {
			messageTable.createDefaultFromModel(null);
		}

		getMessage().addMessage(msg);
		MessageTableModel mtm = messageTable.getMessageModel();
		mtm.fireTableRowsInserted(messageTable.getRowCount() - 1,
				messageTable.getRowCount() - 1);
		rebuildSort();
	}

	public final int addColumn(String id, String title, boolean editable) {
		return messageTable.addColumn(id, title, editable);
	}

	public final void createColumnsFromDef(Message m) {
		Message def = m.getDefinitionMessage();
		if (def == null) {
			if (m.getArraySize() > 0) {
				def = m.getMessage(0);
			}
			if (def == null) {
				return;
			}
		}
		removeAllColumns();
		ArrayList<Property> al = def.getAllProperties();
		for (int i = 0; i < al.size(); i++) {
			Property current = al.get(i);
			String desc = current.getDescription();
			if (desc == null) {
				desc = current.getName();
			}
			addColumn(current.getName(), desc, current.isDirIn(),
					current.getLength());
		}
	}

	public final void createColumnsFromDef(Message m, String[] excludeColumns) {
		Message def = m.getDefinitionMessage();
		if (def == null) {
			return;
		}
		removeAllColumns();
		ArrayList<Property> al = def.getAllProperties();
		for (int i = 0; i < al.size(); i++) {
			Property current = al.get(i);
			String desc = current.getDescription();
			if (desc == null) {
				desc = current.getName();
			}
			if (!excludeColumn(current.getName(), excludeColumns)) {
				addColumn(current.getName(), desc, current.isDirIn(),
						current.getLength());
			}
		}
	}

	private final boolean excludeColumn(String col, String[] excludes) {
		// logger.info("Seeing if I should exclude column: " + col);
		for (int i = 0; i < excludes.length; i++) {
			if (col.equals(excludes[i])) {
				return true;
			}
		}
		return false;
	}

	public final int addColumn(String id, String title, boolean editable,
			int size) {
		return messageTable.addColumn(id, title, editable, size);
	}

	public final FilterPanel getFilterPanel() {
		return filterPanel;
	}

	public void setColumnEditable(int columnIndex, boolean value) {
		messageTable.setColumnEditable(columnIndex, value);
	}

	public Message getMessageAsPresentedOnTheScreen(
			boolean includeInvisibleColumns) {
		return messageTable
				.getMessageAsPresentedOnTheScreen(includeInvisibleColumns);
	}

	public final void addListSelectionListener(ListSelectionListener l) {
		messageTable.addListSelectionListener(l);
	}

	public final void addCellEditorListener(CellEditorListener ce) {
		messageTable.addCellEditorListener(ce);
	}

	public final void removeCellEditorListener(CellEditorListener ce) {
		messageTable.removeCellEditorListener(ce);
	}

	public final void setAutoResize(int i) {
		messageTable.setAutoResizeMode(i);
	}

	public final Message getMessageRow(int row) {
		return messageTable.getMessageRow(row);
	}

	public final int getRowCount() {
		return messageTable.getRowCount();
	}

	public Message getSelectedMessage() {
		return messageTable.getSelectedMessage();
	}

	public final int getSelectedRow() {
		return messageTable.getSelectedRow();
	}

	public final ArrayList<Message> getSelectedMessages() {
		return messageTable.getSelectedMessages();
	}

	public final void messageLoaded(int startIndex, int endIndex, int newTotal) {
		messageTable.messageLoaded(startIndex, endIndex, newTotal);
	}

	public final void addActionListener(ActionListener e) {
		messageTable.addActionListener(e);
	}

	public final void removeActionListener(ActionListener e) {
		messageTable.removeActionListener(e);
	}

	public final void doSort(int columnIndex, boolean ascending) {
		messageTable.doSort(columnIndex, ascending);
	}

	@Override
	public final void setEnabled(boolean e) {
		enabled = e;
		super.setEnabled(enabled && (!ghosted));
	}

	public void rowSelected(ListSelectionEvent e) {
	}

	final void messageTable_valueChanged(ListSelectionEvent e) {
		setStateUpdated();
		rowSelected(e);
	}

	public final void fireDataChanged() {
		setStateUpdated();
		messageTable.fireDataChanged();
	}

	public void setHighColor(Color c) {
		messageTable.setHighColor(c);
	}

	public void setLowColor(Color c) {
		messageTable.setLowColor(c);
	}

	public void setSelectedColor(Color c) {
		messageTable.setSelectedColor(c);
	}

	public void clearColumnDividers() {
		messageTable.clearColumnDividers();
	}

	public void addColumnDivider(int index, float width) {
		messageTable.addColumnDivider(index, width);
	}

	public final void clearSelection() {
		messageTable.clearSelection();
	}

	public final void setSelectedMessage(Message m) {
		if (m == null) {
			clearSelection();
			return;
		}
		for (int i = 0; i < messageTable.getRowCount(); i++) {
			Message msg = messageTable.getMessageRow(i);
			if (msg == m) {
				setSelectedRow(i);
			}
		}
	}

	public void setRowHeight(int r) {
		messageTable.setRowHeight(r);
	}

	public final void setColumnsVisible(boolean b) {
		filterPanel.setColumnsVisible(b);
	}

	public final void setManageColumnButtonVisible(boolean b) {
		filterPanel.setManageColumnButtonVisible(b);
	}

	public final void setFiltersVisible(boolean b) {
		filterPanel.setFiltersVisible(b);
	}

	public final void setRowSelectionInterval(int start, int end) {
		messageTable.setRowSelectionInterval(start, end);
	}

	public final void setSelectedRow(int row) {
		if (row >= getRowCount()) {
			// not enough rows
			return;
		}
		if (row == -1) {
			clearSelection();
			return;
		}
		setRowSelectionInterval(row, row);
	}

	public void updateProperties(java.util.List<Property> l) {
		messageTable.updateProperties(l);
	}

	public void updateExpressions() throws NavajoException {
		messageTable.updateExpressions();
	}

	public void setReadOnly(boolean b) {
		messageTable.setReadOnly(b);
	}

	public final void addPropertyFilter(String propName, Property value,
			String operator) {
		messageTable.addPropertyFilter(propName, value, operator);
	}

	public final void clearPropertyFilters() {
		messageTable.clearPropertyFilters();
		filterPanel.clearLabel();
	}

	public final void performFilters() {
		messageTable.performFilters();
	}

	public final void removeFilters() {
		messageTable.removeFilters();
	}

	public final int getSortedColumn() {
		return messageTable.getSortedColumn();
	}

	public final boolean getSortingDirection() {
		return messageTable.getSortingDirection();
	}

	public final String getColumnId(int index) {
		return messageTable.getColumnId(index);
	}

	public final int getColumnWidth(int index) {
		return messageTable.getColumnWidth(index);
	}

	public final int getColumnCount() {
		return messageTable.getColumnCount();
	}

	// public final void resizeColumns() {
	// messageTable.resizeColumns();
	// }

	public final String getColumnName(int index) {
		return messageTable.getColumnName(index);
	}

	public final void removeAllColumns() {
		messageTable.removeAllColumns();
	}

	public final void setColumnWidth(int index, int width) {
		messageTable.setColumnWidth(index, width);
		// try {
		// messageTable.getColumnModel().getColumn(index).setPreferredWidth(width);
		// } catch(Throwable th) {
		// logger.info("SOMETHING WRONG IN setColumnWidth in messageTablePanel! Fix me!");
		// }
	}

	public final void setColumnDefinitionSavePath(String path) {
		messageTable.setColumnDefinitionSavePath(path);
		if (path == null || "".equals(path)) {
			filterPanel.setSaveColumnButtonVisible(false);
		} else {
			filterPanel.setSaveColumnButtonVisible(true);
		}
	}

	@Override
	public void setConstraint(String id) {
		messageTable.setConstraint(id);
	}

	@Override
	public String getConstraint() {
		return messageTable.getConstraint();
	}

	public final void repaintHeader() {
		messageTable.repaintHeader();
		if (tableFooter != null) {
			tableFooter.repaint();
		} else {
			logger.info("Trying to repaint invisible footer!");
		}
	}

	/**
	 * copyObject
	 * 
	 * @return Object
	 * @todo Implement this
	 *       com.dexels.navajo.swingclient.components.CopyCompatible method
	 */
	@Override
	public Object copyObject() {
		return messageTable.copyObject();
	}

	public String getTypeHint(String id) {
		return messageTable.getTypeHint(id);
	}

	public void setTypeHint(String id, String type) {
		messageTable.setTypeHint(id, type);
	}

	public void setUseScrollBars(boolean b) {
		useScroll = b;
		if (b) {
			jScrollPane1
					.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPane1
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		} else {
			jScrollPane1
					.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			jScrollPane1
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane1.setPreferredSize(messageTable.getPreferredSize());
		}
	}

	public void createColumnModel() {
		messageTable.createDefaultColumnsFromModel();
		// tableFooter.setColumnModel(messageTable.getColumnModel());
	}

	public void setFooterRenderer(TableCellRenderer tc) {
		// Thread.dumpStack();
		// if (tableFooter==null) {
		//
		// }
		if (tc == null) {
			if (tableFooter != null) {
				logger.info("Removind footer renderer!");
				tableFooter.setDefaultRenderer(tc);
				tableFooter.setVisible(false);
				remove(tableFooter);
				tableFooter = null;
			}
		} else {
			tableFooter = new MessageTableFooter(messageTable, tc);

			// changed this:
			// tableFooter.setDefaultRenderer(tc);
			messageTable.setMessageTableFooter(tableFooter);
			messageTable.getColumnModel().addColumnModelListener(tableFooter);
			tableFooter.setColumnModel(messageTable.getColumnModel());
			tableFooter.setDefaultRenderer(tc);
			tableFooter.setVisible(true);
			this.add(tableFooter, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.BOTH,
					new Insets(2, 2, 2, 2), 0, 0));
			revalidate();
		}
	}

	public void setFilterMode(String mode) {
		if (filterPanel != null) {
			filterPanel.setFilterMode(mode);
		}

	}

	public boolean isSortingAllowed() {
		return messageTable.isSortingAllowed();
	}

	public void setSortingAllowed(boolean b) {
		messageTable.setSortingAllowed(b);
	}

	public void setRefreshAfterEdit(boolean b) {
		messageTable.setRefreshAfterEdit(b);
	}

	public boolean getRefreshAfterEdit() {
		return messageTable.getRefreshAfterEdit();
	}

	public void stopCellEditing() {
		messageTable.stopCellEditing();
	}

	public void setColumnLabel(int index, String name) {
		messageTable.getMessageModel().setColumnTitle(index, name);
		messageTable.fireTableStructureChanged();

	}

	public void addConditionalRemark(String remark, String condition, int type) {

	}

	public void showEditDialog(String title, int row) throws Exception {
		messageTable.showEditDialog(title, row);
	}

	public void setShowRowHeaders(boolean b) {
		messageTable.setShowRowHeaders(b);
	}

	public boolean isShowingRowHeaders() {
		return messageTable.isShowingRowHeaders();
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		print(graphics);
		if (pageIndex == 0) {
			return Printable.PAGE_EXISTS;
		} else {
			return Printable.NO_SUCH_PAGE;
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return checkMaxMin(super.getPreferredSize(), getMaximumSize(),
				getMinimumSize());
	}

	private Dimension checkMax(Dimension preferredSize, Dimension maximumSize) {
		// Dimension maximumSize = getMaximumSize();
		if (maximumSize == null) {
			return preferredSize;
		}
		return new Dimension(Math.min(preferredSize.width, maximumSize.width),
				Math.min(preferredSize.height, maximumSize.height));
	}

	private Dimension checkMin(Dimension preferredSize, Dimension minimumSize) {
		// Dimension minimumSize = getMinimumSize();
		if (minimumSize == null) {
			return preferredSize;
		}
		return new Dimension(Math.max(preferredSize.width, minimumSize.width),
				Math.max(preferredSize.height, minimumSize.height));
	}

	public Dimension checkMaxMin(Dimension preferredSize,
			Dimension maximumSize, Dimension minimumSize) {
		return checkMin(checkMax(preferredSize, maximumSize), minimumSize);
	}

	public void doEmail() {
		if (filterPanel != null) {
			filterPanel.doEmail();
		}
	}

	public void doExcel() {
		if (filterPanel != null) {
			filterPanel.doExcel();
		}
	}

	public void doSaveColumns() {
		if (filterPanel != null) {
			filterPanel.doSaveColumns();
		}

	}

	public void doChooseColumns() {
		if (filterPanel != null) {
			filterPanel.doChooseColumns();
		}
	}

	public void setIgnoreList(String[] ignoreList) {
		if (filterPanel != null) {
			filterPanel.setIgnoreList(ignoreList);
		}

	}

	public Binary getTableReport(String format, String orientation,
			int[] margins) throws NavajoException {
		return messageTable.getTableReport(format, orientation, margins);
	}

}
