package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.util.ArrayList;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import com.dexels.navajo.document.Message;

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
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class MessageTablePanel extends Column {
	private Row myHtmlTemplatePanel = new Row();

	private MessageTable myTable = new MessageTable();

	private Button nextButton, previousButton, loadMessage;

	private Label myLabel;

	private ArrayList myListeners = new ArrayList();

	public MessageTablePanel() {
		initUI();
	}

	private void initUI() {
		nextButton = new Button("Next page");
		previousButton = new Button("Previous page");
		loadMessage = new Button("Get details..");
		myLabel = new Label();

		// nextButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// myTable.nextPage();
		// updateTablebar();
		// }
		// });
		// previousButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// myTable.previousPage();
		// updateTablebar();
		// }
		// });
		loadMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < myListeners.size(); i++) {
					ActionListener l = (ActionListener) myListeners.get(i);
					l.actionPerformed(new ActionEvent(myTable,
							"onActionPerformed"));
				}
			}
		});

		// this.setLayoutManager(new EchoBorderLayout());
		this.add(myHtmlTemplatePanel);
		myHtmlTemplatePanel.add(nextButton);
		myHtmlTemplatePanel.add(previousButton);
		myHtmlTemplatePanel.add(loadMessage);
		myHtmlTemplatePanel.add(myLabel);
		myHtmlTemplatePanel.add(myTable);
	}

	public void addActionListener(ActionListener l) {
		myListeners.add(l);
	}

	public void removeActionListener(ActionListener l) {
		myListeners.remove(l);
	}

	// private final void updateTablebar() {
	// int index = myTable.getCurrentPage() + 1;
	// int count = myTable.getPageCount() + 1;
	// myLabel.setText("page " + index + " of " + count);
	// }

	public int getColumnCount() {
		return myTable.getColumnModel().getColumnCount();
	}

	public void setMessage(Message m) {
		if (m.getArraySize() > 0) {
			nextButton.setVisible(true);
			previousButton.setVisible(true);
			loadMessage.setVisible(true);
			myLabel.setVisible(true);
		}
		myTable.setMessage(m);
		// updateTablebar();
	}

	public Message getSelectedMessage() {
		return myTable.getSelectedMessage();
	}

	public void addColumn(String id, String name, boolean editable) {
		myTable.addColumn(id, name, editable);
	}

	public void removeAllColumns() {
		myTable.removeAllColumns();
	}

}
