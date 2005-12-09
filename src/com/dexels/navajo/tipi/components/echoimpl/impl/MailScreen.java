/* 
 * This file is part of the Echo Web Application Framework (hereinafter "Echo").
 * Copyright (C) 2002-2005 NextApp, Inc.
 *
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 */

package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.layout.SplitPaneLayoutData;

/**
 * The multi-paned main mail-viewing screen of the application. This component
 * contains the page navigator, message list, and message preview panes.
 */
public class MailScreen extends ContentPane {

	// Constants for new dialog positioning algorithm.
	private static final int DIALOG_POSITION_INITIAL = 80;

	private static final int DIALOG_POSITION_INCREMENT = 20;

	private static final int DIALOG_POSITION_MAXIMUM = 200;

	// private Folder[] folders;

	// private MessageListTable messageListTable;
	private Button pageNavigator;

	private ContentPane messagePane;

	private SelectField folderSelect;

	// private Message selectedMessage;
	private int dialogPosition = DIALOG_POSITION_INITIAL;

	/**
	 * Creates a new <code>MailScreen</code>.
	 */
	public MailScreen() {
		super();

		SplitPane mainSplitPane = new SplitPane(
				SplitPane.ORIENTATION_HORIZONTAL, new Extent(175));
		mainSplitPane.setSeparatorWidth(new Extent(1, Extent.PX));
		add(mainSplitPane);

		SplitPane titleOptionSplitPane = new SplitPane(
				SplitPane.ORIENTATION_VERTICAL, new Extent(70));
		titleOptionSplitPane.setSeparatorHeight(new Extent(1, Extent.PX));
		mainSplitPane.add(titleOptionSplitPane);

		Column titleColumn = new Column();
		titleColumn.setStyleName("MailScreen.TitleColumn");
		titleOptionSplitPane.add(titleColumn);
		SplitPaneLayoutData spl = new SplitPaneLayoutData();
		spl.setOverflow(SplitPaneLayoutData.OVERFLOW_AUTO);
		titleColumn.setLayoutData(spl);
		Label label;

		label = new Label("Main");
		label.setStyleName("Title.Main");
		titleColumn.add(label);
		Button bb = new Button("Arrrrr");
		bb.setBorder(new Border(5, new Color(100, 200, 200), 5));
		titleColumn.add(bb);
		titleColumn.add(new Button("Orrrrr"));
		titleColumn.add(new Button("Errrrr"));
		titleColumn.add(new Button("Urrrrr"));
		titleColumn.add(new Button("Irrrrr"));

		label = new Label("Sun");
		label.setStyleName("Title.Sub");
		titleColumn.add(label);

		titleOptionSplitPane.add(createOptionPane());

		SplitPane mailSplitPane = new SplitPane(SplitPane.ORIENTATION_VERTICAL,
				new Extent(320));
		mailSplitPane.setResizable(true);
		mainSplitPane.add(mailSplitPane);

		SplitPane messageListSplitPane = new SplitPane(
				SplitPane.ORIENTATION_VERTICAL, new Extent(32));
		messageListSplitPane.setSeparatorHeight(new Extent(1, Extent.PX));
		mailSplitPane.add(messageListSplitPane);

		Row controlPane = new Row();
		controlPane.setStyleName("ControlPane");
		messageListSplitPane.add(controlPane);

		pageNavigator = new Button();
		controlPane.add(pageNavigator);

		// messageListTable = new MessageListTable();
		// messageListTable.addMessageSelectionListener(new
		// MessageListTable.MessageSelectionListener() {
		// public void messageSelected(MessageSelectionEvent e) {
		// try {
		// selectedMessage = e.getMessage();
		// messagePane.setMessage(selectedMessage);
		// } catch (MessagingException ex) {
		// EmailApp.getApp().processFatalException(ex);
		// }
		// }
		// });
		// messageListSplitPane.add(messageListTable);

		// messagePane = new MessagePane();
		// mailSplitPane.add(messagePane);
	}

	/**
	 * Creates the "option pane".
	 * 
	 * @return the option pane <code>Component</code>
	 */
	private Component createOptionPane() {
		Button button;
		Label label;

		Column optionColumn = new Column();
		optionColumn.setStyleName("MailScreen.OptionColumn");

		Column folderSelectColumn = new Column();
		folderSelectColumn.setStyleName("MailScreen.FolderSelectColumn");
		optionColumn.add(folderSelectColumn);

		label = new Label("Folder?");
		folderSelectColumn.add(label);

		folderSelect = new SelectField();
		folderSelectColumn.add(folderSelect);

		Column actionsColumn = new Column();
		optionColumn.add(actionsColumn);

		button = new Button("New");
		button.setStyleName("MailScreen.OptionButton");
		actionsColumn.add(button);

		button = new Button("Reply");
		button.setStyleName("MailScreen.OptionButton");
		actionsColumn.add(button);

		button = new Button("Logout");
		button.setStyleName("MailScreen.OptionButton");
		optionColumn.add(button);

		return optionColumn;
	}

}
