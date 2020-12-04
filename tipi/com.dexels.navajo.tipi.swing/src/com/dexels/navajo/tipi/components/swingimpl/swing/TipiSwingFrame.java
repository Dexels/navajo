/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public interface TipiSwingFrame {
	public Container getContentPane();

	public void setJMenuBar(JMenuBar j);

	public boolean isVisible();

	public void setVisible(boolean b);

	public boolean isResizable();

	public Rectangle getBounds();

	public void setBounds(Rectangle r);

	public void setTitle(String title);

	public void setExtendedState(int state);

	public int getExtendedState();

	public void setIconImage(ImageIcon i);

	public String getTitle();

	public void setUndecorated(boolean b);

	public void pack();

	public void setModal(boolean b);

	public void setLocationRelativeTo(Component c);

	public void setDefaultCloseOperation(int type);

	public void addWindowListener(WindowListener wl);

    public void setSize(int width, int height);
}
