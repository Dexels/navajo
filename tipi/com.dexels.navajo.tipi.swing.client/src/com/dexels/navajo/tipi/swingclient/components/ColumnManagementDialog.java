package com.dexels.navajo.tipi.swingclient.components;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;

class NameIdMap {
	HashMap<String, String> propertyNameIdMap = new HashMap<String, String>();
	HashMap<String, String> propertyIdNameMap = new HashMap<String, String>();

	public void put(String id, String name) {
		propertyNameIdMap.put(name, id);
		propertyIdNameMap.put(id, name);
	}

	public String getById(String id) {
		return propertyIdNameMap.get(id);
	}

	public String getByName(String name) {
		return propertyNameIdMap.get(name);
	}

	public void clear() {
		propertyNameIdMap.clear();
		propertyIdNameMap.clear();
	}
}

public class ColumnManagementDialog extends JDialog {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ColumnManagementDialog.class);
	
	private static final long serialVersionUID = -6795986412646589943L;
	private JList<String> availableColumnList = new JList<String>();
	private JList<String> visibleColumnList = new JList<String>();
	private JButton hideButton = new JButton();
	private JButton showButton = new JButton();
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	private GridBagLayout gridBagLayout1 = new GridBagLayout();
	private MessageTable myTable;
	private NameIdMap nameIdMap = new NameIdMap();
	private JScrollPane scroll1 = new JScrollPane();
	private JScrollPane scroll2 = new JScrollPane();
	private ArrayList<String> availableItems = new ArrayList<String>();
	private ArrayList<String> visibleItems = new ArrayList<String>();
	private JButton okButton = new JButton();
	private JButton cancelButton = new JButton();
	private JButton jButton1 = new JButton();
	private JButton upButton = new JButton();
	private JButton downButton = new JButton();
	private String[] ignoreList;
	private Container myToplevel;
	private HashMap<String, Boolean> editableMap = new HashMap<String, Boolean>();

	public ColumnManagementDialog(Dialog toplevel) {
		super(toplevel, true);
		myToplevel = toplevel;
		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	public ColumnManagementDialog(Frame toplevel) {
		super(toplevel, true);
		myToplevel = toplevel;
		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	public final void setTopLevel(Frame top) {
	}

	public final void setIgnoreList(String[] list) {
		ignoreList = list;
	}

	private final boolean isInIgnoreList(String s) {
		if (ignoreList == null) {
			return false;
		}
		for (int i = 0; i < ignoreList.length; i++) {
			if (ignoreList[i].equals(s)) {
				return true;
			}
		}
		return false;
	}

	public final void setMessageTable(MessageTable mt) {
		myTable = mt;
		if (myTable == null) {
			logger.info("Null message table! ignoring");
			return;
		}

		Message m = mt.getMessage();
		Message first = m.getMessage(0);
		ArrayList<Property> props = first.getAllProperties();

		for (int i = 0; i < props.size(); i++) {
			Property current = props.get(i);
			String name = current.getDescription();
			String id = current.getName();

			// Check if the column is allready showing and what his editability
			// is, then use that.
			boolean tc = myTable.getMessageModel().isShowingColumn(id);
			int index = myTable.getMessageModel().getColumnIndex(id);
			if (tc && index >= 0) {
				boolean editableNow = myTable.getMessageModel()
						.isColumnEditable(index);
				editableMap.put(id, Boolean.valueOf(editableNow));
			} else {
				if (current.isDirIn()) {
					editableMap.put(id, Boolean.TRUE);
				} else {
					editableMap.put(id, Boolean.FALSE);
				}
			}
			if (name == null) {
				name = id;
			}
			if (!isInIgnoreList(current.getName())) {
				nameIdMap.put(id, name);
				availableItems.add(id);
			}
		}
		fillEmUp();
	}

	private final void fillEmUp() {
		int currentColumnCount = myTable.getColumnCount();
		DefaultListModel<String> model = (DefaultListModel<String>) visibleColumnList
				.getModel();

		for (int i = 0; i < currentColumnCount; i++) {
			String item = myTable.getMessageModel().getColumnName(i);
			String id = myTable.getMessageModel().getColumnId(i);
			model.addElement(item);
			visibleItems.add(id);
			availableItems.remove(id);
			nameIdMap.put(id, item);
		}

		DefaultListModel<String> model2 = (DefaultListModel<String>) availableColumnList
				.getModel();
		for (int j = 0; j < availableItems.size(); j++) {
			String id = availableItems.get(j);
			String name = nameIdMap.getById(id);
			model2.addElement(name);
		}
	}

	private final void jbInit() throws Exception {
		availableColumnList.setModel(new DefaultListModel<String>());
		visibleColumnList.setModel(new DefaultListModel<String>());
		this.setTitle("Toevoegen en verwijderen van kolommen");
		this.getContentPane().setSize(500, 400);
		setLocationRelativeTo(myToplevel);
		this.getContentPane().setLayout(gridBagLayout1);
		hideButton.setIcon(new ImageIcon(ColumnManagementDialog.class
				.getResource("arrow_left.gif")));
		hideButton.setText("");
		hideButton
				.addActionListener(new ColumnManagementDialog_hideButton_actionAdapter(
						this));
		showButton.setIcon(new ImageIcon(ColumnManagementDialog.class
				.getResource("arrow_right.gif")));
		showButton
				.addActionListener(new ColumnManagementDialog_showButton_actionAdapter(
						this));
		jLabel1.setText("Beschikbare kolommen");
		jLabel2.setText("Zichtbare kolommen");
		okButton.setText("Ok");
		okButton.addActionListener(new ColumnManagementDialog_okButton_actionAdapter(
				this));
		cancelButton.setToolTipText("");
		cancelButton.setText("Annuleren");
		cancelButton
				.addActionListener(new ColumnManagementDialog_cancelButton_actionAdapter(
						this));
		jButton1.setText("jButton1");
		upButton.addActionListener(new ColumnManagementDialog_upButton_actionAdapter(
				this));
		downButton.setIcon(new ImageIcon(ColumnManagementDialog.class
				.getResource("arrow_down.gif")));
		downButton.setText("");
		downButton
				.addActionListener(new ColumnManagementDialog_downButton_actionAdapter(
						this));
		upButton.setIcon(new ImageIcon(ColumnManagementDialog.class
				.getResource("arrow_up.gif")));
		upButton.setText("");
		scroll1.getViewport().add(availableColumnList);
		this.getContentPane().add(
				scroll1,
				new GridBagConstraints(0, 1, 1, 2, 1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(2, 2, 2, 2), 130, 204));
		this.getContentPane().add(
				hideButton,
				new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTH,
						GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2),
						26, 0));
		this.getContentPane().add(
				showButton,
				new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTH,
						GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2),
						26, 0));
		this.getContentPane().add(
				scroll2,
				new GridBagConstraints(2, 1, 2, 2, 1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(2, 2, 2, 2), 133, 206));
		scroll2.getViewport().add(visibleColumnList);
		this.getContentPane().add(
				jLabel1,
				new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(2, 2, 2, 2), 10, 0));
		this.getContentPane().add(
				jLabel2,
				new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(2, 2, 2, 2), 32, 0));
		this.getContentPane().add(
				okButton,
				new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				cancelButton,
				new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				upButton,
				new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
						0, 0));
		this.getContentPane().add(
				downButton,
				new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
						0, 0));
	}

	final void hideButton_actionPerformed(ActionEvent e) {
		List<String> removedNames = visibleColumnList.getSelectedValuesList();
		DefaultListModel<String> vm = (DefaultListModel<String>) visibleColumnList.getModel();
		DefaultListModel<String> am = (DefaultListModel<String>) availableColumnList.getModel();
		for (String name : removedNames) {
			vm.removeElement(name);
			am.addElement(name);
			availableItems.add(name);
			visibleItems.remove(name);
		}
		visibleColumnList.setSelectedIndex(1);
	}

	final void showButton_actionPerformed(ActionEvent e) {
		List<String> addedNames = availableColumnList.getSelectedValuesList();
		DefaultListModel<String> vm = (DefaultListModel<String>) visibleColumnList.getModel();
		DefaultListModel<String> am = (DefaultListModel<String>) availableColumnList.getModel();
		for (String name : addedNames) {
			am.removeElement(name);
			vm.addElement(name);
			availableItems.remove(name);
			visibleItems.add(name);
		
		}
		visibleColumnList.setSelectedIndex(1);
	}

	final void okButton_actionPerformed(ActionEvent e) {
		int currentColumnCount = myTable.getColumnCount();
		int min = (myTable.isShowingRowHeaders() ? 1 : 0);
		for (int i = currentColumnCount - 1; i >= min; i--) {
			String item = myTable.getMessageModel().getColumnId(i);
			myTable.removeColumn(item);
		}
		DefaultListModel<String> vm = (DefaultListModel<String>) visibleColumnList.getModel();
		Enumeration<?> m = vm.elements();
		myTable.removeAllColumns();
		while (m.hasMoreElements()) {
			String name = (String) m.nextElement();
			logger.info("Name: " + name);
			String id = nameIdMap.getByName(name);
			if (id != null && !id.equals("") && !id.equals("ERROR!")) {
				boolean editable = false;
				if (editableMap.get(id) != null) {
					editable = editableMap.get(id).booleanValue();
				}
				myTable.addColumn(id, name, editable);
			}
		}
		this.setVisible(false);
		// SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// myTable.setDefaultColumnSizes(myTable.getMessage());
		myTable.createDefaultFromModel(null);
		myTable.setEqualColumnSizes();
		// }
		// });
	}

	final void cancelButton_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	final void upButton_actionPerformed(ActionEvent e) {
		int index = visibleColumnList.getSelectedIndex();
		DefaultListModel<String> vm = (DefaultListModel<String>) visibleColumnList.getModel();
		if (index > 0) {
			String value = visibleColumnList.getSelectedValue();
			vm.removeElement(value);
			vm.insertElementAt(value, index - 1);
			visibleColumnList.setSelectedIndex(index - 1);
		}
	}

	final void downButton_actionPerformed(ActionEvent e) {
		int index = visibleColumnList.getSelectedIndex();
		DefaultListModel<String> vm = (DefaultListModel<String>) visibleColumnList.getModel();
		if (index < vm.indexOf(vm.lastElement())) {
			String value = visibleColumnList.getSelectedValue();
			vm.removeElement(value);
			vm.insertElementAt(value, index + 1);
			visibleColumnList.setSelectedIndex(index + 1);
		}
	}

}

class ColumnManagementDialog_hideButton_actionAdapter implements
		java.awt.event.ActionListener {
	ColumnManagementDialog adaptee;

	ColumnManagementDialog_hideButton_actionAdapter(
			ColumnManagementDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.hideButton_actionPerformed(e);
	}
}

class ColumnManagementDialog_showButton_actionAdapter implements
		java.awt.event.ActionListener {
	ColumnManagementDialog adaptee;

	ColumnManagementDialog_showButton_actionAdapter(
			ColumnManagementDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.showButton_actionPerformed(e);
	}
}

class ColumnManagementDialog_okButton_actionAdapter implements
		java.awt.event.ActionListener {
	ColumnManagementDialog adaptee;

	ColumnManagementDialog_okButton_actionAdapter(ColumnManagementDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.okButton_actionPerformed(e);
	}
}

class ColumnManagementDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	ColumnManagementDialog adaptee;

	ColumnManagementDialog_cancelButton_actionAdapter(
			ColumnManagementDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.cancelButton_actionPerformed(e);
	}
}

class ColumnManagementDialog_upButton_actionAdapter implements
		java.awt.event.ActionListener {
	ColumnManagementDialog adaptee;

	ColumnManagementDialog_upButton_actionAdapter(ColumnManagementDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.upButton_actionPerformed(e);
	}
}

class ColumnManagementDialog_downButton_actionAdapter implements
		java.awt.event.ActionListener {
	ColumnManagementDialog adaptee;

	ColumnManagementDialog_downButton_actionAdapter(
			ColumnManagementDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.downButton_actionPerformed(e);
	}
}
