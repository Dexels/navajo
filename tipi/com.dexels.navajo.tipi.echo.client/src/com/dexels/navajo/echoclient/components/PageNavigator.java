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

package com.dexels.navajo.echoclient.components;

import java.io.Serializable;
import java.util.EventListener;
import java.util.EventObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.NavajoException;

/**
 * A component which provides navigation between pages.
 */
public class PageNavigator extends Row {

	private static final long serialVersionUID = -2445605624264163792L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(PageNavigator.class);
	/**
	 * An <code>EventListener</code> to provide notification of page index
	 * changes.
	 */
	public static interface PageIndexChangeListener extends EventListener,
			Serializable {

		/**
		 * Provides notification of a page index change.
		 * 
		 * @param e
		 *            the <code>PageIndexChangeEvent</code> describing the
		 *            change
		 */
		public void pageIndexChanged(PageIndexChangeEvent e);
	}

	/**
	 * An <code>EventObject</code> describing a page index change
	 */
	public class PageIndexChangeEvent extends EventObject {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6684128956754164585L;
		private int newPageIndex;

		/**
		 * Creates a new <code>PageIndexChangeEvent</code>.
		 * 
		 * @param newPageIndex
		 *            the new page index
		 */
		private PageIndexChangeEvent(int newPageIndex) {
			super(PageNavigator.this);
			this.newPageIndex = newPageIndex;
		}

		/**
		 * Returns the new page index.
		 * 
		 * @return the new page index
		 */
		public int getNewPageIndex() {
			return newPageIndex;
		}
	}

	private Label totalPagesLabel;
	private Label pageField;
	private int pageIndex, totalPages;
	private MessageTable myTable;

	/**
	 * Creates a new <code>PageNavigator</code>.
	 */
	public PageNavigator() {
		super();

	}

	public void initialize() {
		setCellSpacing(new Extent(20));

		Button previousPageButton = new Button(Styles.ICON_24_LEFT_ARROW);
		previousPageButton.setRolloverEnabled(true);
		previousPageButton.setRolloverIcon(Styles.ICON_24_LEFT_ARROW_ROLLOVER);
		previousPageButton.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -967186369832202380L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setPageIndex(getPageIndex() - 1);
			}
		});
		add(previousPageButton);

		Row entryRow = new Row();
		entryRow.setCellSpacing(new Extent(5));
		add(entryRow);

		Label itemLabel = new Label("Pagina:");
		entryRow.add(itemLabel);

		pageField = new Label();
		pageField.setStyleName("PageNavigator.PageField");
		// pageField.setWidth(new Extent(4, Extent.EX));
		pageField.setText("1");
		// pageField.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// try {
		// setPageIndex(Integer.parseInt(pageField.getText()) - 1);
		// } catch (NumberFormatException ex) {
		// setPageIndex(getPageIndex());
		// }
		// }
		// });
		entryRow.add(pageField);

		Label prepositionLabel = new Label("Totaal:");
		entryRow.add(prepositionLabel);

		totalPagesLabel = new Label("1");
		entryRow.add(totalPagesLabel);

		Button nextPageButton = new Button(Styles.ICON_24_RIGHT_ARROW);
		nextPageButton.setRolloverEnabled(true);
		nextPageButton.setRolloverIcon(Styles.ICON_24_RIGHT_ARROW_ROLLOVER);
		nextPageButton.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7550564950228140160L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setPageIndex(getPageIndex() + 1);
			}
		});
		add(nextPageButton);

		Button printButton = new Button("Print");
		printButton.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8177043022109815038L;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					myTable.getTableReport(NavajoClientFactory.createClient(), "pdf", "horizontal", new int[] { 5,
							5, 5, 5 });
				} catch (NavajoException e1) {
					logger.error("Error: ", e1);
				}
			}
		});
		add(printButton);
	}

	public void setTable(MessageTable table) {
		myTable = table;
	}

	/**
	 * Adds a listener to be notified of page index changes.
	 * 
	 * @param l
	 *            the listener to add
	 */
	public void addPageIndexChangeListener(PageIndexChangeListener l) {
		getEventListenerList().addListener(PageIndexChangeListener.class, l);
	}

	/**
	 * Notifies <code>PageIndexChangeListener</code>s that the page index has
	 * changed.
	 */
	private void firePageIndexChanged() {
		EventListener[] listeners = getEventListenerList().getListeners(
				PageIndexChangeListener.class);
		logger.info("Firing index changed... Listeners count: "
				+ listeners.length);
		PageIndexChangeEvent e = new PageIndexChangeEvent(getPageIndex());
		for (int i = 0; i < listeners.length; ++i) {
			((PageIndexChangeListener) listeners[i]).pageIndexChanged(e);
		}
	}

	/**
	 * Returns the current page index.
	 * 
	 * @return the current page index
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * Returns the total number of pages.
	 * 
	 * @return the total number of pages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * Removes a listener from being notified of page index changes.
	 * 
	 * @param l
	 *            the listener to remove
	 */
	public void removePageIndexChangeListener(PageIndexChangeListener l) {
		getEventListenerList().removeListener(PageIndexChangeListener.class, l);
	}

	/**
	 * Sets the current page index.
	 * 
	 * @param pageIndex
	 *            the new page index
	 */
	public void setPageIndex(int pageIndex) {
		logger.info("Setting to pageIndex: " + pageIndex
				+ " totalPages: " + totalPages);
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		if (totalPages <= 1) {
			pageIndex = 0;
		}
		if (pageIndex > 0 && pageIndex > totalPages - 1) {
			pageIndex = totalPages - 1;
		}
		this.pageIndex = pageIndex;
		pageField.setText(Integer.toString(pageIndex + 1));
		firePageIndexChanged();
	}

	/**
	 * Sets the total number of pages.
	 * 
	 * @param totalPages
	 *            the total number of pages
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
		if (totalPages == 0) {
			totalPagesLabel.setText("1");
		} else {
			totalPagesLabel.setText(Integer.toString(totalPages));
		}
	}
}
