/*    
 JExtendedInternalFrame: A JInternalFrame that can be modal
 Copyright (C) 2002 Martin Bravenboer

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import java.awt.ActiveEvent;
import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class JExtendedInternalFrame extends JInternalFrame {
	public JExtendedInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
	}

	public JExtendedInternalFrame() {
		super();
	}

	public synchronized void stopModal() {
		System.err.println("STOPMODAL!");
		notifyAll();
	}

	public synchronized void startModal() {
		System.err.println("Starting modal");
		if (isVisible() && !isShowing()) {
			Component parent = this.getParent();
			while (parent != null) {
				if (parent.isVisible() == false) {
					parent.setVisible(true);
				}

				parent = parent.getParent();
			}
		}

		try {
			if (SwingUtilities.isEventDispatchThread()) {
				EventQueue theQueue = getToolkit().getSystemEventQueue();

				while (isVisible()) {
					AWTEvent event = theQueue.getNextEvent();

					Object src = event.getSource();
					if (event instanceof ActiveEvent) {
						((ActiveEvent) event).dispatch();
					} else if (src instanceof Component) {
						Component src2 = (Component) src;

						if (!(event instanceof MouseEvent)) {
							src2.dispatchEvent(event);
						} else {
							MouseEvent mouseEvent = (MouseEvent) event;

							if (isVisible()
									&& (mouseEvent.getID() == MouseEvent.MOUSE_PRESSED || mouseEvent.getID() == MouseEvent.MOUSE_CLICKED)) {
								Component target = SwingUtilities.getDeepestComponentAt(src2, mouseEvent.getX(), mouseEvent.getY());

								// It would be nice if we could go
								// SwingUtilities.isDescendingFrom(target, this)
								// But it dosent account for popups

								if (isChild(target)) {
									src2.dispatchEvent(event);
								} else {
									if (mouseEvent.getID() == MouseEvent.MOUSE_PRESSED) {
										// Beep
										getToolkit().beep();
									}
								}
							} else {
								src2.dispatchEvent(event);
							}
						}
					}
				}
			} else {
				while (isVisible()) {
					wait();
				}
			}
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

	private boolean isChild(Component component) {
		while (component != null) {
			if (component == this) {
				return true;
			} else if (component instanceof JPopupMenu) {
				component = ((JPopupMenu) component).getInvoker();
			} else {
				component = component.getParent();
			}
		}
		return false;
	}
}
