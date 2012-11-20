/* 
 * This file is part of the Echo2 Table Extension (hereinafter "ETE").
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
package com.dexels.navajo.echoclient.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.table.TableCellRenderer;
import echopointng.table.SortableTableModel;

/**
 * Default renderer for <code>SortableTableModel</code> backed Tables.
 * 
 * @author Jason Dalton
 */
public class MessageTableHeaderRenderer implements TableCellRenderer {

	private static final long serialVersionUID = 8746797472974170209L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MessageTableHeaderRenderer.class);
	private static final ImageReference upArrowImage = new ResourceImageReference(
			"/echopointng/resource/images/ArrowUp.gif");

	private static final ImageReference downArrowImage = new ResourceImageReference(
			"/echopointng/resource/images/ArrowDown.gif");

	/**
	 * @see nextapp.echo2.app.table.TableCellRenderer#getTableCellRendererComponent(nextapp.echo2.app.Table,
	 *      java.lang.Object, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(Table table, Object value,
			int column, int row) {
		SortableTableModel model = (SortableTableModel) table.getModel();
		MessageTable mt = (MessageTable) table;
		return getSortButton(mt, (String) value, column, model,
				(MessageTable) table);
	}

	protected Button getSortButton(MessageTable mt, String label, int column,
			SortableTableModel model, MessageTable table) {
		TableHeaderImpl button = new TableHeaderImpl(label);
		// Color color = mt.getHeaderForeground();
		// if (color!=null) {
		// button.setForeground(color);
		// }
		// color = mt.getHeaderBackground();
		// if (color!=null) {
		// button.setForeground(color);
		// }
		// color = mt.getHeaderPressedBackground();
		// if (color!=null) {
		// button.setPressedBackground(color);
		// }
		// color = mt.getHeaderPressedForeground();
		// if (color!=null) {
		// button.setPressedForeground(color);
		// }
		// color = mt.getHeaderRolloverBackground();
		// if (color!=null) {
		// button.setRolloverBackground(color);
		// }
		// color = mt.getHeaderRolloverForeground();
		// if (color!=null) {
		// button.setRolloverForeground(color);
		// }
		//
		button.addActionListener(getSortButtonListener(column, model));
		// button.setLayoutData(getLayoutData());
		// button.setInsets(new Insets(2, 0, 0, 0));
		//
		// button.setTextPosition(new Alignment(Alignment.CENTER,
		// Alignment.DEFAULT));
		//

		// int colsize = table.getColumnSize(column).getValue()-2;
		// button.setWidth(new Extent(colsize, Extent.PX));

		button.setHeight(new Extent(mt.getHeaderHeight(), Extent.PX));

		ImageReference icon = null;
		if (model.getCurrentSortColumn() == column) {

			if (model.getSortDirective(column) == SortableTableModel.ASCENDING) {
				icon = upArrowImage;
			} else {
				icon = downArrowImage;
			}
			button.setIcon(icon);
		}

		return button;
	}

	protected LayoutData getLayoutData() {
		TableLayoutData data = new TableLayoutData();
		// data.setBackground(new Color(175,175,239));
		return data;
	}

	protected ActionListener getSortButtonListener(final int column,
			final SortableTableModel model) {
		return new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5681197561161929673L;

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Boioioioin!");
				int currentSort = model.getCurrentSortColumn();
				int ascending = SortableTableModel.ASCENDING;
				if (currentSort >= 0) {
					ascending = model.getSortDirective(currentSort);
				}
				model.sortByColumn(
						column,
						ascending == SortableTableModel.ASCENDING ? SortableTableModel.DESCENDING
								: SortableTableModel.ASCENDING);
			}
		};
	}
}
