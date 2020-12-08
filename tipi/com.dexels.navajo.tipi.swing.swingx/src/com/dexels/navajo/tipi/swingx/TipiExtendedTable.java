/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingx;

import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.search.Searchable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;
import com.dexels.navajo.tipi.swingclient.components.MessageTableModel;
import com.dexels.navajo.tipi.swingclient.components.PropertyCellEditor;
import com.dexels.navajo.tipi.swingclient.components.PropertyCellRenderer;

public class TipiExtendedTable extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = -1924180960988641513L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiExtendedTable.class);
	
	private JXTable myTable = null;

	@Override
	public Object createContainer() {
		myTable = new JXTable();
		// myTable.getColumnModel().addColumn(new TableColumn(0,50));
		// myTable.getColumnModel().addColumn(new TableColumn(2,50));
		// myTable.getColumnModel().addColumn(new TableColumn(3,100));
		JScrollPane js = new JScrollPane(myTable);
		myTable.setColumnControlVisible(true);
//		myTable.setHighlighters(new HighlighterPipeline(new Highlighter[] { AlternateRowHighlighter.classicLinePrinter }));
		// myTable.getHighlighters().addHighlighter(new
		// RolloverHighlighter(Color.BLACK, Color.WHITE ));
		myTable.setRolloverEnabled(true);
		myTable.setCellEditor(new PropertyCellEditor());
		myTable.setTerminateEditOnFocusLost(true);
		myTable.setEditable(true);
		myTable.setSearchable(new Searchable() {

			@Override
			public int search(String arg0) {
				return 0;
			}

			@Override
			public int search(Pattern arg0) {
				return 0;
			}

			@Override
			public int search(String arg0, int arg1) {
				return 0;
			}

			@Override
			public int search(Pattern arg0, int arg1) {
				return 0;
			}

			@Override
			public int search(String arg0, int arg1, boolean arg2) {
				return 0;
			}

			@Override
			public int search(Pattern arg0, int arg1, boolean arg2) {
				return 0;
			}
		});

		myTable.setDefaultRenderer(Property.class, new PropertyCellRenderer());
		return js;
	}

	@Override
	public void loadData(final Navajo n, String method) throws TipiException, TipiBreakException {
		Message m = n.getMessage("FileAssociations");
		final MessageTableModel mtm = new MessageTableModel(m);
		createColumns(mtm, m);
		logger.info("Created model: " + mtm.getRowCount());
		logger.info("Created model: " + mtm.getColumnCount());
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				myTable.setModel(mtm);

			}
		});
	}

	private void createColumns(MessageTableModel mtm, Message m) {
		if (m == null) {
			return;
		}
		Message def = m.getDefinitionMessage();
		if (def == null) {
			if (m.getArraySize() == 0) {
				return;
			}
			def = m.getMessage(0);
		}
		List<Property> l = def.getAllProperties();
		for (Property p : l) {
			String desc = p.getDescription();
			if (desc == null) {
				desc = p.getName();
			}
			mtm.addColumn(p.getName(), desc, true);
		}
	}

}
