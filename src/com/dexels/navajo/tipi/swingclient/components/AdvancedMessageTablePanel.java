package com.dexels.navajo.tipi.swingclient.components;

import com.dexels.navajo.document.*;
import com.dexels.navajo.client.*;

import java.util.*;
import javax.swing.event.*;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.*;
import com.dexels.navajo.tipi.swingclient.components.validation.*;

import java.io.*;

//import com.dexels.navajo.document.nanoimpl.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class AdvancedMessageTablePanel
extends MessageTablePanel
implements ResponseListener, ListSelectionListener, ChangeListener {

	private String updateService = null;
	private String insertService = null;
	private String deleteService = null;
	private Message initMessage = null;
	private String initMethod = null;
	private String loadMessagePath = null;
	private String insertMessagePath = null;
	private String requiredMessagePath = null;

//	private String confirmationMessageName = null;
	private int nrOfDeletedRows = 0;
	private int nrOfInsertedRows = 0;
	private Set changedMessages = new HashSet();
	private ArrayList insertedMessages = new ArrayList();
	private ArrayList messageTableListeners = new ArrayList();
	private JToolBar myToolbar;
	private Navajo myNavajo;
	private boolean loadColumnsFromDef = false;
	private boolean hasConditionErrors = false;
	private boolean toolbarEnabled = true;
	private boolean toolbarModifiable = true;
	private String[] excludeColumns;
	private ArrayList myConfirmationListeners = new ArrayList();
	private ArrayList confirmationMessageNames = new ArrayList();
	private Object previousValue = null;

//	private boolean doResetChanged = false;

	public AdvancedMessageTablePanel() {
//		addCellEditorListener(this);

		messageTable.getSelectionModel().addListSelectionListener(this);
		messageTable.getColumnModel().getSelectionModel().addListSelectionListener(this);
//		messageTable.setCellSelectionEnabled(true);
		//addFocusListener(new FocusAdapter() {
		//  public void focusLost(FocusEvent fe) {
		//    editingStopped(new MessageTableChangeEvent(this, messageTable.getEditingRow(), messageTable.getEditingColumn()));
//		if(doResetChanged){
//		resetChanged();
//		}
		// }
		//});
		JTableHeader th = getTable().getTableHeader();
		th.addMouseListener(new MouseAdapter() {
			public final void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					firePopupEvent(e);
				}
			}

			public final void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					firePopupEvent(e);
				}
			}
		});
		getTable().setTopLevelParent(this);
		getTable().addChangeListener(this);

	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getFirstIndex() >= 0 && e.getSource() == messageTable.getColumnModel().getSelectionModel()) {
			// Get the data model for this table
			if (messageTable.getSelectedRow() > -1 && messageTable.getSelectedColumn() > -1) {
				TableModel model = (TableModel) messageTable.getModel();
				previousValue = model.getValueAt(messageTable.getSelectedRow(), messageTable.getSelectedColumn());
				if(Property.class.isInstance(previousValue)){
					//System.err.println("It's a property");
					previousValue = ((Property)previousValue).clone();
				}
				//System.err.println("Value selected = " + previousValue);
			}
		}

	}

	// Added by Arjen (18/11/2004 to support IGNORING changed functionality).
	public void resetChanged() {
//		doResetChanged = true;
		changedMessages.clear();
		messageTable.resetChanged();
	}

	public final boolean hasChanged() {
		if (changedMessages.size() > 0 || insertedMessages.size() >0) {
			return true;
		}
		return false;
//		return messageTable.hasChanged();
	}

	public final void firePopupEvent(MouseEvent e) {
		super.firePopupEvent(e);

		final int col = messageTable.getColumnModel().getColumnIndexAtX(e.getX());
		if (messageTable.isCellEditable(0, col)) {
			Object o = messageTable.getValueAt(0, col);
			if (Property.class.isInstance(o)) {
				Property p = (Property) o;
				if (p != null && p.getType().equals("selection") && p.isDirIn()) {
					try {
						JPopupMenu pop = new JPopupMenu();
						pop.addSeparator();
						ArrayList a = p.getAllSelections();
						for (int i = 0; i < a.size(); i++) {
							Selection s = (Selection) a.get(i);
							final JMenuItem si = new JMenuItem(s.getName());
							si.setName(s.getName());
							si.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									setSelectedColumnValue(col, si.getName());
								}
							});
							pop.add(si);
						}
						pop.show(messageTable, e.getX(), e.getY());
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		else {
			// System.err.println("Column is not editable..");
		}
	}

//	private final void resetSelections(int column) {
//	try {
//	final int rowCount = getRowCount();
//	for (int i = 0; i < rowCount; i++) {
//	Object o = messageTable.getValueAt(i, column);
//	Property p = (Property) o;
//	p.setSelected("-1");
//	}
//	fireDataChanged();
//	}
//	catch (Exception ex) {
//	ex.printStackTrace();
//	}
//	}

	private final void setSelectedColumnValue(int column, String name) {
		try {
			final int rowCount = getRowCount();
			ArrayList selected = getSelectedMessages();
			if (selected != null && selected.size() > 0) {
				for (int i = 0; i < selected.size(); i++) {
					Object o = messageTable.getValueAt(0, column);
					Property q = (Property) o;
					Message m = (Message) selected.get(i);
					Property p = m.getProperty(q.getName());
					if (p.isDirIn()) {
						p.clearSelections();
						p.getSelection(name).setSelected(true);
//						Message row = getMessageRow(i);
//changedMessages.add(row);
						changedMessages.add(m);
					}
				}
			}
			else {
				for (int i = 0; i < rowCount; i++) {
					Object o = messageTable.getValueAt(i, column);
					Property p = (Property) o;
					if (p.isDirIn()) {
						p.clearSelections();
						p.getSelection(name).setSelected(true);
						Message row = getMessageRow(i);
						changedMessages.add(row);
					}
				}
			}
//			clearPropertyFilters();
			fireDataChanged();
			fireHeaderMenuEvent();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public final void addToolbar(JToolBar b) {
		setToolbar(b);
		b.setFloatable(false);
		super.addToolbar(b);
	}

	public final void setToolbar(JToolBar bar) {
		myToolbar = bar;
		setToolbarEnabled(false);
	}

	public final void clearTable() {
		super.clearTable();
		setToolbarEnabled(false);
	}

	public void setToolbarEnabled(final boolean state) {
		if ( toolbarModifiable ) {
			toolbarEnabled = state;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (myToolbar != null) {
						Component[] comps = myToolbar.getComponents();
						for (int i = 0; i < comps.length; i++) {
							comps[i].setEnabled(state);
						}
					}
				}
			});
		}
	}

	public void setToolbarModifiable(final boolean state) {
		toolbarModifiable = state;
	}

	public final ArrayList getInsertedMessages() {
		return insertedMessages;
	}

	public final Message getInsertMessage() {
		if (insertMessagePath == null) {
			return null;
		}
		return getMessage().getRootDoc().getMessage(insertMessagePath);
	}

	public final Message getRequiredMessage() {
		return getMessage().getRootDoc().getMessage(requiredMessagePath);
	}

	public boolean hasConditionErrors() {
		return hasConditionErrors;
	}

	public void setAllUpdateFlags() {
		ArrayList selectedMessages = getSelectedMessages();
		if (selectedMessages != null && selectedMessages.size() > 0) {
			for (int i = 0; i < selectedMessages.size(); i++) {
				Message m = (Message) selectedMessages.get(i);
				changedMessages.add(m);
			}
		}
		else {
			Message arrayMsg = getMessage();
			for (int i = 0; i < arrayMsg.getArraySize(); i++) {
				Message m = arrayMsg.getMessage(i);
				changedMessages.add(m);
				//System.err.println("... adding m");
			}
		}
	}

	public final void commit() {
		if (!toolbarEnabled) {
			//System.err.println("WARNING: Not commmitting AMTP, my toolbar is disabled, so I assume I'm read-only");
			return;
		}
		Navajo rootDoc = null;
		ArrayList methods = null;

//		System.err.println("Committing AMTP!");
		// System.err.println("Changed: " + changedMessages.size());
//		System.err.println("Inserted: " + insertedMessages.size());
		// NOTE: Inserted messages are not changed!!!!!!!!
		Iterator it = changedMessages.iterator();

		while (it.hasNext()) {
			Message current = (Message) it.next();
			// System.err.println("Updating: " + current.getName());
			rootDoc = current.getRootDoc();
			methods = rootDoc.getAllMethods();
			if (current.getProperty("Update") != null) {
				current.getProperty("Update").setValue("true");
			}
			else {
				// System.err.println("Whoops no Update flag found for row: " +
				// current.getIndex());
				continue;
			}
		}
		if (changedMessages.size() > 0) {
			if (updateService == null) {
				for (int j = 0; j < methods.size(); j++) {
					Method currentMethod = (Method) methods.get(j);
					if (currentMethod.getName().startsWith("ProcessUpdate")) {
						updateService = currentMethod.getName();
						// System.err.println("UpdateMethod found: " + updateService);
						break;
					}
				}
			}
			if (updateService != null) {
				try {
					Navajo n = NavajoClientFactory.getClient().doSimpleSend(rootDoc, updateService);
					if (n.getMessage("ConditionErrors") != null) {
						hasConditionErrors = true;
					}
					else {
						hasConditionErrors = false;
					}

					if (confirmationMessageNames.size() > 0) {
						for (int i = 0; i < confirmationMessageNames.size(); i++) {
							Message confirm = n.getMessage( (String) confirmationMessageNames.get(i));
							if (confirm != null) {
								fireConfirmationEvent(confirm, "update");
							}
						}
						// clearPropertyFilters();
						reload();
						return;
					}

				}
				catch (Exception ex1) {
					ex1.printStackTrace();
				}
			}
		}
		// Inserted Messages
		if (methods != null) {
			methods.clear();
		}
		nrOfInsertedRows = insertedMessages.size();
		for (int i = 0; i < insertedMessages.size(); i++) {
			Message current = (Message) insertedMessages.get(i);
			Navajo m = NavajoFactory.getInstance().createNavajo();
			Message insertMessage = current.copy(m);
			insertMessage.setName( (insertMessagePath.startsWith("/") ? insertMessagePath.substring(1) : insertMessagePath));

			if (i == 0 && insertService == null) {
				rootDoc = getMessage().getRootDoc();
				methods = rootDoc.getAllMethods();
				for (int j = 0; j < methods.size(); j++) {
					Method currentMethod = (Method) methods.get(j);
					if (currentMethod.getName().startsWith("ProcessInsert")) {
						insertService = currentMethod.getName();
						// System.err.println("InsertMethod found: " + insertService);
						break;
					}
				}
			}
			try {
				if (insertService != null) {
					if (requiredMessagePath != null) {
						Message required = getMessage().getRootDoc().getMessage(requiredMessagePath);
						m.addMessage(required);
						if (required != null) {
//							required.write(System.err);
						}
						else {
							// System.err.println("======================================>>>>> Could not find requiredmsg: " + requiredMessagePath);
						}
					}
					m.addMessage(insertMessage);

					Navajo n = NavajoClientFactory.getClient().doSimpleSend(m, insertService);
					if (n.getMessage("ConditionErrors") != null) {
						hasConditionErrors = true;
					}
					else {
						hasConditionErrors = false;
					}

					if (confirmationMessageNames.size() > 0) {
						boolean confirmed = false;
						for (int j = 0; j < confirmationMessageNames.size(); j++) {
							Message confirm = n.getMessage( (String) confirmationMessageNames.get(j));
							if (confirm != null) {
								confirmed = true;
								fireConfirmationEvent(confirm, "insert");
							}
						}
						// clearPropertyFilters();

						if(confirmed){
//							System.err.println("Quitting for loop~!!!");
							reload();
							return;
						}
					}

				}
				System.err.println("Inserted: " + insertMessage.getName());
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		// clearPropertyFilters();
		reload();
	}

	public final void delete() {
		Navajo rootDoc = null;
		ArrayList methods = null;
		ArrayList selectedMsgs = getSelectedMessages();
		ArrayList toBeDeleted = new ArrayList();
		boolean performTransaction = false;
		TableCellEditor ed = messageTable.getDefaultEditor(Property.class);
		// System.err.println("Ed: " + ed);
		ed.stopCellEditing();
		if (selectedMsgs != null) {
			nrOfDeletedRows = selectedMsgs.size();
			for (int i = 0; i < selectedMsgs.size(); i++) {
				Message selected = (Message) selectedMsgs.get(i);
				rootDoc = selected.getRootDoc();
				if (insertedMessages.contains(selected)) {
					messageTable.getMessage().removeMessage(selected);
					insertedMessages.remove(selected);
					messageTable.reallocateIndexes();
					fireRowDeleted(selected);
					messageTable.getMessageModel().fireTableStructureChanged();
				}
				else {
					toBeDeleted.add(selected);
					performTransaction = true;
				}
			}
		}
		if (performTransaction) {
			doDelete(rootDoc, toBeDeleted);

		}

	}

	public void setSavedColumnTitle(String column, String title) {
		messageTable.setSavedColumnTitle(column, title);
	}

	private final void doDelete(Navajo rootDoc, ArrayList toBeDeleted) {
		for (int i = 0; i < toBeDeleted.size(); i++) {
			Message current = (Message) toBeDeleted.get(i);
			Property deletedProperty = current.getProperty("Delete");
			if (deletedProperty == null) {
				SwingClient.getUserInterface().showInfoDialog("Error while deleting. Not supported here.");
				return;
			}
			deletedProperty.setValue("true");
		}
		if (deleteService == null) {
			ArrayList methods = rootDoc.getAllMethods();
			for (int j = 0; j < methods.size(); j++) {
				Method currentMethod = (Method) methods.get(j);
				if (currentMethod.getName().startsWith("ProcessDelete")) {
					deleteService = currentMethod.getName();
					// System.err.println("DeleteMethod: " + deleteService);
					break;
				}
			}
		}
		if (deleteService != null) {
			try {
				Navajo n = NavajoClientFactory.getClient().doSimpleSend(rootDoc, deleteService);
				if (n.getMessage("ConditionErrors") != null) {
					hasConditionErrors = true;
				}
				else {
					hasConditionErrors = false;
				}
				if (confirmationMessageNames.size() > 0) {
					for (int k = 0; k < confirmationMessageNames.size(); k++) {
						Message confirm = n.getMessage( (String) confirmationMessageNames.get(k));
						if (confirm != null) {
							fireConfirmationEvent(confirm, "delete");
						}
					}
					reload();
					return;
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			reload();
		}
	}

	private final void doDelete(Navajo rootDoc, Message selected) {
		// System.err.println("CURRENT SELECTED: # " + nrOfDeletedRows + "\n\n");
//		selected.write(System.err);
		if (!SwingClient.getUserInterface().areYouSure()) {
			return;
		}
		Property deletedProperty = selected.getProperty("Delete");
		if (deletedProperty == null) {
			SwingClient.getUserInterface().showInfoDialog("Error while deleting. Not supported here.");
			return;
		}
		selected.getProperty("Delete").setValue("true");
		ArrayList methods = rootDoc.getAllMethods();
		if (deleteService == null) {
			for (int j = 0; j < methods.size(); j++) {
				Method currentMethod = (Method) methods.get(j);
				if (currentMethod.getName().startsWith("ProcessDelete")) {
					deleteService = currentMethod.getName();
					// System.err.println("DeleteMethod: " + deleteService);
					break;
				}
			}
		}
		if (deleteService != null) {
			try {
				Navajo n = NavajoClientFactory.getClient().doSimpleSend(rootDoc, deleteService);
				if (confirmationMessageNames.size() > 0) {
					for (int i = 0; i < confirmationMessageNames.size(); i++) {
						Message confirm = n.getMessage( (String) confirmationMessageNames.get(i));
						if (confirm != null) {
							fireConfirmationEvent(confirm, "delete");
						}
					}
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public final void insert() {
		if (insertMessagePath != null) {
			Message insertMessage = myNavajo.getMessage(insertMessagePath);
			// System.err.println("Inserting: " + insertMessagePath);
			if (insertMessage != null) {
				Navajo n = insertMessage.getRootDoc();
				Message newRow = n.copyMessage(insertMessage, NavajoFactory.getInstance().createNavajo());
				if (newRow.getProperty("Update") == null) {
					try {
						newRow.addProperty(NavajoFactory.getInstance().createProperty(n, "Update", "boolean", "false", 1, "Update", "in"));
					}
					catch (Exception e) {
						// System.err.println("WARNING: problem adding property Update to
						// the inserted message");
					}
				}
				if (newRow.getProperty("Delete") == null) {
					try {
						newRow.addProperty(NavajoFactory.getInstance().createProperty(n, "Delete", "boolean", "false", 1, "Update", "in"));
					}
					catch (Exception e) {
						//System.err.println("WARNING: problem adding property Delete to the inserted message");
					}
				}

				insertedMessages.add(newRow);
				addSubMessage(newRow);
				Rectangle rect = new Rectangle(0, (int) getSize().getHeight() + 10, 1, 1);
				//System.err.println("Rectangle: " + rect);
				getScrollPane().getVerticalScrollBar().setValue(getScrollPane().getVerticalScrollBar().getMaximum());
				getScrollPane().getViewport().scrollRectToVisible(rect);
			}
			else {
				// System.err.println("InsertMessage not found");
			}
		}
		else {
			// System.err.println("insertMessage datapath not specified");
		}
	}

	public final Navajo insert(Message msg) {
		// System.err.println("Inserting external message");
		insertedMessages.add(msg);
		addSubMessage(msg);
		rebuildSort();

		// Check it
		return msg.getRootDoc();
	}

	private final void reload() {
		previousValue = null;
		//System.err.println("reloading..");
		// Remove filter.
//		clearPropertyFilters(); // 07-05-07 Not sure if we really want this
		if (changedMessages != null && insertedMessages != null) {
			//System.err.println("Clearing changedmessages and inserted! <<<------------------------------------------------");
			changedMessages.clear();
			insertedMessages.clear();
		}
		try {
			if (initMessage != null) {
				NavajoClientFactory.getClient().doAsyncSend(initMessage.getRootDoc(), initMethod, this, "reload");
			}
			else {
				//System.err.println("Cannot reload AMTP, because I have no initMessage");
				removeBusyPanel();
			}
		}
		catch (ClientException ex) {
			ex.printStackTrace();
			removeBusyPanel();
		}
	}

	public final void receive(final Navajo n, final String method, final String id) {
		//System.err.println("AMTP Received: " + id + ", from service: " + method);
		removeBusyPanel();

		try {

			// ==========================================


			if (id.equals("update")) {
				reload();
			}
			if (id.equals("insert")) {
				nrOfInsertedRows--;
				if (nrOfInsertedRows == 0) {
					reload();
				}
			}
			if (id.equals("delete")) {
				nrOfDeletedRows--;
				if (nrOfDeletedRows == 0) {
					reload();
				}
			}
			if (id.equals("reload")) {
				myNavajo = n;
				Message loadMsg = n.getMessage(loadMessagePath);
				Message insertMessage = n.getMessage(insertMessagePath);
				Message added = null;
				if (loadMsg == null) {
					//System.err.println("WARNING: LoadMessage is null, we're making a DUMMY");
					StringTokenizer tok = new StringTokenizer(loadMessagePath, "/");
					Message lastAvailable = n.getRootMessage();
					while (tok.hasMoreTokens()) {
						String mName = tok.nextToken();
						Message m = lastAvailable.getMessage(mName);
						if (m != null) {
							lastAvailable = m;
						}
						else {
							//System.err.println("Could not find message: " + mName + ", creating and adding it..");
							Message newM = NavajoFactory.getInstance().createMessage(n, mName);
							if (added == null) {
								added = newM;
							}
							lastAvailable.addMessage(newM);
							lastAvailable = newM;
						}
					}

					if (added != null) {
						// System.err.println("Adding: " + added.getName());
						loadMsg = added;
					}
				}
				if (loadColumnsFromDef) {
					if (excludeColumns == null) {
						createColumnsFromDef(loadMsg);
					}
					else {
						createColumnsFromDef(loadMsg, excludeColumns);
					}
				}
				setMessage(loadMsg);
				if (loadMsg == null) {
					fireTableLoaded(n.getMessage(insertMessagePath));
				}
				else {
					fireTableLoaded(loadMsg);
				}
				if (insertMessage != null && insertMessage.getProperty("Authorized") != null) {
					if ("false".equals(insertMessage.getProperty("Authorized").getValue())) {
						setToolbarEnabled(false);
					}
				}
			}


		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public final Set getChangedMessages() {
		return changedMessages;
	}

	public final void setInsertMethod(String method) {
		insertService = method;
	}

	public final void setUpdateMethod(String method) {
		updateService = method;
	}

	public final void setDeleteMethod(String method) {
		deleteService = method;
	}

	/*
	 * public void editingStopped(ChangeEvent ce) { MessageTableChangeEvent e =
	 * (MessageTableChangeEvent) ce; Object o = e.getSource(); if
	 * (MessageTable.class.isInstance(o)) { MessageTable current = (MessageTable)
	 * o; Message currentMsg = current.getMessageRow(e.getRow());
	 * 
	 * Object val = current.getValueAt(e.getRow(), e.getColumn()); if (val != null &&
	 * previousValue != null) { System.err.println("Previous: " + previousValue + ",
	 * current: " + val + ", equal? " +
	 * val.toString().equals(previousValue.toString())); }
	 * 
	 * if (val != null && previousValue != null &&
	 * val.toString().equals(previousValue.toString())) {
	 * System.err.println("Previous: " + previousValue + ", current: " + val + ",
	 * equal? " + val.toString().equals(previousValue.toString())); return; }
	 * 
	 * if (currentMsg == null) { return; }
	 * 
	 * if (!insertedMessages.contains(currentMsg)) {
	 * changedMessages.add(currentMsg); } fireRowUpdated(currentMsg); } }
	 */

//	public final void editingCanceled(ChangeEvent e) {
//	}

	public final void setService(Message initMessage, String initMethod, String dataPath, String newDataPath, String requiredDataPath) {
		this.initMessage = initMessage;
		this.initMethod = initMethod;
		this.loadMessagePath = dataPath;
		this.insertMessagePath = newDataPath;
		this.requiredMessagePath = requiredDataPath;
	}

	public final void setService(Message initMessage, String initMethod, String dataPath, String newDataPath) {
		this.initMessage = initMessage;
		this.initMethod = initMethod;
		this.loadMessagePath = dataPath;
		this.insertMessagePath = newDataPath;
	}

	public final Message getInitMessage() {
		return initMessage;
	}

	public final void setMessage(Message m) {
		if (m != null) {
			setToolbarEnabled(true);
		}
		else {
			setToolbarEnabled(false);
		}
		super.setMessage(m);

		// removed this line, because this will clear the changed msgs when you do
		// an insert
//		System.err.println("Clearing changedmessages!
//		<<<------------------------------------------------");
//		changedMessages.clear();
	}

	// overridden
	public final void rowSelected(ListSelectionEvent e) {
		fireRowSelected(getSelectedMessage());
	}

	public final void addMessageTableListener(MessageTableListener mtl) {
		// throw new UnsupportedOperationException("Not yet implemented");
		messageTableListeners.add(mtl);
	}

	public final void removeMessageTableListener(MessageTableListener mtl) {
		messageTableListeners.remove(mtl);
	}

	protected final void fireTableLoaded(Message m) {
		for (int i = 0; i < messageTableListeners.size(); i++) {
			MessageTableListener current = (MessageTableListener) messageTableListeners.get(i);
			current.tableLoaded(m);
		}
	}

	protected final void fireRowUpdated(Message m) {
		for (int i = 0; i < messageTableListeners.size(); i++) {
			MessageTableListener current = (MessageTableListener) messageTableListeners.get(i);
			current.rowUpdated(m);
		}
	}

	protected final void fireRowDeleted(Message m) {
		for (int i = 0; i < messageTableListeners.size(); i++) {
			MessageTableListener current = (MessageTableListener) messageTableListeners.get(i);
			current.rowDeleted(m);
		}
	}

	protected final void fireRowInserted(Message m) {
		for (int i = 0; i < messageTableListeners.size(); i++) {
			MessageTableListener current = (MessageTableListener) messageTableListeners.get(i);
			current.rowInserted(m);
		}
	}

	protected final void fireRowSelected(Message m) {
		for (int i = 0; i < messageTableListeners.size(); i++) {
			MessageTableListener current = (MessageTableListener) messageTableListeners.get(i);
			current.rowSelected(m);
		}
	}

	public final void handleException(Exception e) {
		//System.err.println("--> An exception is passed to AdvancedMessageTablePanel it was: " + e.toString());
		removeBusyPanel();
		super.handleException(e);
	}

	public final String getIdentifier() {
		return "AMTP, " + insertMessagePath;
	}

	public final void setCreateColumnsFromDef(boolean b, String[] exclude) {
		loadColumnsFromDef = b;
		excludeColumns = exclude;
	}

	private final void fireConfirmationEvent(Message m, String type) {
		for (int i = 0; i < myConfirmationListeners.size(); i++) {
			ConfirmationListener l = (ConfirmationListener) myConfirmationListeners.get(i);
			l.confirmationNeeded(m, type);
		}
	}

	public void addConfirmationListener(ConfirmationListener l) {
		myConfirmationListeners.add(l);
	}

	public void removeConfirmationListener(ConfirmationListener l) {
		myConfirmationListeners.remove(l);
	}

	public void setConfirmationMessage(String s) {
		confirmationMessageNames.add(s);
//		confirmationMessageName = s;
	}

	public void stateChanged(ChangeEvent e) {
		try{
			Map source = (Map)e.getSource();
			Message currentMsg = (Message)source.get("message");
			int orw = ((Integer)source.get("row")).intValue();
			//System.err.println("Row: " + orw);
			/*
    Object val = current.getValueAt(e.getRow(), e.getColumn());
    if (val != null && previousValue != null) {
      System.err.println("Previous: " + previousValue + ", current: " + val + ", equal? " + val.toString().equals(previousValue.toString()));
    }

    if (val != null && previousValue != null && val.toString().equals(previousValue.toString())) {
  	  System.err.println("Previous: " + previousValue + ", current: " + val + ", equal? " + val.toString().equals(previousValue.toString()));
      return;
    }*/

			if (currentMsg == null) {
				return;
			}

//			currentMsg.write(System.err);
			if (!insertedMessages.contains(currentMsg)) {
				changedMessages.add(currentMsg);
			}
			fireRowUpdated(currentMsg);

		}catch(Exception ex){
			ex.printStackTrace();	
		}
	}





}
