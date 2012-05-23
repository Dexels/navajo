/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.SwingUtilities;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.swingclient.components.MessageTable;

public class TipiMessageTable extends MessageTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8878916392303033449L;
	final TipiContext myContext;

	// private final TipiDndManager myDndManager;

	public TipiMessageTable(TipiContext tc, TipiComponent component) {
		myContext = tc;
		// myDndManager = new TipiDndManager(this,component);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					System.err.println("Table detected right click!");
					Point p = e.getPoint();
					int rowNumber = rowAtPoint(p);
					if (getSelectionModel().isSelectedIndex(rowNumber)) {
						// current row is already selected. Skippit.
						return;
					}

					int keyMask = InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK;
					int mask = e.getModifiers() & keyMask;
					System.err.println("Modifiers: " + e.getModifiers()
							+ " mask:  " + mask + " keyMask: " + keyMask);
					if (mask != 0) {
						getSelectionModel().addSelectionInterval(rowNumber,
								rowNumber);

					} else {
						getSelectionModel().setSelectionInterval(rowNumber,
								rowNumber);

					}
				}
			}
		});
	}

	public synchronized void setMessage(Message m) {
		setSavePathJustChanged(true);
		if (columnPathString != null) {
			loadColumnsNavajo();
		}
		super.setMessage(m);
	}

	public void loadColumnsNavajo() {
		if (columnPathString == null) {
			// ignoring, but should not happen at all, I think
			return;
		}
		Navajo n;
		try {
			n = myContext.getStorageManager().getStorageDocument(
					columnPathString);
		} catch (TipiException e) {
			e.printStackTrace();
			return;
		}
		if (n != null) {
			loadColumnsNavajo(n);

		} else {
			createDefaultColumnsFromModel();
		}
	}

	public void saveColumnsNavajo() throws IOException, NavajoException {
		Navajo n = super.saveColumnDefNavajo();
		if (n != null) {
			try {
				myContext.getStorageManager().setStorageDocument(
						columnPathString, n);
			} catch (TipiException e) {
				e.printStackTrace();
				throw new IOException("Errrorrrr saving columns. columnPath: "
						+ columnPathString);
			}
		}
	}

	// public TipiDndManager getDndManager() {
	// return myDndManager;
	// }

}
