package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.*;

import org.jdesktop.animation.timing.*;
import org.jdesktop.animation.timing.interpolation.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.components.*;

public class RichTable extends JTable {
	private MessageTableModel myModel = new MessageTableModel();
	Animator anim;

	public RichTable() {
		this.setOpaque(false);
		this.setModel(myModel);
		myModel.setShowRowHeaders(false);
		this.setDefaultRenderer(Property.class, new RichTableCellRenderer());
		this.setShowVerticalLines(false);
		this.setShowHorizontalLines(false);
		this.setPreferredSize(new Dimension(400, 600));
		setBorder(null);

		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				highlightRowAt(e.getPoint());
			}
		});
	}

	public Message getSelectedMessage() {
		return myModel.getMessageRow(getSelectedRow());
	}

	public void highlightRowAt(Point p) {
		int row = this.rowAtPoint(p);
		if (row < getRowCount() && row >= 0) {
			this.setRowSelectionInterval(row, row);
			Rectangle vr = this.getVisibleRect();
			Rectangle current = getCellRect(row, 0, true);
			// System.err.println("Matthijs is gek");
			// System.err.println("Point, " + p + ", " + vr);
			if (vr.height - p.y < 50) {
				if (row < getRowCount() - 1) {
					row += 4;
					if (row > getRowCount() - 1) {
						row = getRowCount();
					}
				}
				Rectangle target = getCellRect(row, 0, true);
				scrollTo(target.y + target.height, current.y);
			}
			if (p.y - vr.y < 50) {
				row -= 4;
				if (row < 0) {
					row = 0;
				}
			}
			Rectangle target = getCellRect(row, 0, true);
			scrollTo(target.y, current.y);
		}
	}

	private void scrollTo(int target, int current) {
		if (anim == null || !anim.isRunning()) {
			anim = PropertySetter.createAnimator(500, this, "rect", current, target);
			anim.start();
		}
	}

	public void setRect(int y) {
		Rectangle r = new Rectangle(1, y, 1, 15);
		this.scrollRectToVisible(r);
		// System.err.println("woot: " + y);
	}

	public void setMessage(Message m) {
		myModel.setMessage(m);
		createDefaultColumnsFromModel();

	}

	public int addColumn(String id, String title, boolean editable) {
		return myModel.addColumn(id, title, editable);
	}
}
