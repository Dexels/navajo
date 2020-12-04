/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;

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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiFileChooser extends TipiSwingComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 811549739836564130L;
	final JTextField fileNameField = new JTextField();
	JButton selectButton = new JButton("Browse");

	private String defaultDir = null;
	private String selectionMode = "all";

	public TipiFileChooser() {
	}

	// public void setWaitCursor(boolean b) {
	// super.setWaitCursor(b);
	// fileNameField.setCursor(Cursor.getPredefinedCursor(b?Cursor.WAIT_CURSOR:Cursor.DEFAULT_CURSOR));
	// selectButton.setCursor(Cursor.getPredefinedCursor(b?Cursor.WAIT_CURSOR:Cursor.DEFAULT_CURSOR));
	// }

	@Override
	public Object createContainer() {
		final JPanel p = new JPanel();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		p.setLayout(new GridBagLayout());
		// JButton selectButton = new JButton("Browse");
		selectButton.setMargin(new Insets(0, 0, 0, 0));
		p.add(selectButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						0, 0, 0, 0), 2, 2));
		p.add(fileNameField, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if (defaultDir==null) {
				// defaultDir = System.getProperties("user.dir");
				// }
				JFileChooser fc = new JFileChooser(defaultDir);
				if ("all".equals(selectionMode)) {
					fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				}
				if ("files".equals(selectionMode)) {
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				}
				if ("dirs".equals(selectionMode)) {
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				}
				int i = fc.showOpenDialog(p);
				if (i == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					fileNameField.setText(f.getAbsolutePath());
				}
			}
		});
		return p;
	}

	@Override
	public Object getComponentValue(String name) {
		if ("file".equals(name)) {
			return fileNameField.getText();
		}
		return super.getComponentValue(name);
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if ("file".equals(name)) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					fileNameField.setText((String) object);
					return;
				}
			});
		}
		if ("defaultdir".equals(name)) {
			defaultDir = object.toString();
		}

		if ("selectionMode".equals(name)) {
			selectionMode = object.toString();
		}

		super.setComponentValue(name, object);
	}
}
