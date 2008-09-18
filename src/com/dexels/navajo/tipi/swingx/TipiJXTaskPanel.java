package com.dexels.navajo.tipi.swingx;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.action.*;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiJXTaskPanel extends TipiSwingDataComponentImpl {

	@Override
	public Object createContainer() {
		JXTaskPaneContainer p = new JXTaskPaneContainer();
		return p;
	}

	public static void main(String args[]) throws Exception {
		// JXButtonDemo.main(new String[]{});
		// JXErrorPaneDemo.main(new String[] {});
		// JXTitledPanel
		// JXGradientChooserDemo.main(null);
		JXFrame frame = new JXFrame();

		// a container to put all JXTaskPane together
		JXTaskPaneContainer taskPaneContainer = new JXTaskPaneContainer();

		// create a first taskPane with common actions
		JXTaskPane actionPane = new JXTaskPane();
		actionPane.setTitle("Files and Folders");
		actionPane.setSpecial(true);

		// actions can be added, a hyperlink will be created
		Action renameSelectedFile = ActionFactory.createBoundAction("aap", "noot", "mies");
		actionPane.add(renameSelectedFile);
		// actionPane.add(createDeleteFileAction());
		final JXHyperlink jgx = new JXHyperlink();
		jgx.setText("MONKEYYYY");
		actionPane.add(jgx);
		jgx.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(jgx, "Den aep!");
			}
		});
		// add this taskPane to the taskPaneContainer
		taskPaneContainer.add(actionPane);

		// create another taskPane, it will show details of the selected file
		JXTaskPane details = new JXTaskPane();
		details.setTitle("Details");

		// add standard components to the details taskPane
		JLabel searchLabel = new JLabel("Search:");
		JTextField searchField = new JTextField("");
		details.add(searchLabel);
		details.add(searchField);

		taskPaneContainer.add(details);

		// put the action list on the left
		frame.add(taskPaneContainer, BorderLayout.WEST);
		JFileChooser fileBrowser = new JFileChooser(new File("c:/"));
		// and a file browser in the middle
		frame.add(fileBrowser, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);

	}
}
