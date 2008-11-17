/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.dexels.navajo.tipi.swingimpl.dnd.test;

/*
 * BasicDnD.java requires no other files.
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import org.jdesktop.animation.transitions.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.components.*;

public class BasicDnD extends JPanel implements ActionListener {
	private static JFrame frame;
	// private JTextArea textArea;
	private JTextField textField;
	// private JList list;
//	private JTable table;
	private MessageTable table;
	// private JTree tree;
	// private JColorChooser colorChooser;
	private JCheckBox toggleDnD;

	public BasicDnD() throws NavajoException {
		super(new BorderLayout());
		JPanel leftPanel = createVerticalBoxPanel();
		JPanel rightPanel = createVerticalBoxPanel();

		// Create a table model.
		DefaultTableModel tm = new DefaultTableModel();
		tm.addColumn("Column 0");
		tm.addColumn("Column 1");
		tm.addColumn("Column 2");
		tm.addColumn("Column 3");
		tm.addRow(new String[] { "Table 00", "Table 01", "Table 02", "Table 03" });
		tm.addRow(new String[] { "Table 10", "Table 11", "Table 12", "Table 13" });
		tm.addRow(new String[] { "Table 20", "Table 21", "Table 22", "Table 23" });
		tm.addRow(new String[] { "Table 30", "Table 31", "Table 32", "Table 33" });

	
//		table = new JTable(tm);
		table = new MessageTable();
//
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Array", Message.MSG_TYPE_ARRAY);
		Message m1 = NavajoFactory.getInstance().createMessage(n, "Array", Message.MSG_TYPE_ARRAY_ELEMENT);
		Property p = NavajoFactory.getInstance().createProperty(n,"Test",Property.STRING_PROPERTY,"Row1",10,"Description",Property.DIR_OUT);
		Message m2 = NavajoFactory.getInstance().createMessage(n, "Array", Message.MSG_TYPE_ARRAY_ELEMENT);
		Property p2 = NavajoFactory.getInstance().createProperty(n,"Test",Property.STRING_PROPERTY,"Row2",10,"Description",Property.DIR_OUT);
		n.addMessage(m);
		m.addMessage(m1);
		m.addMessage(m2);
		m1.addProperty(p);
		m2.addProperty(p2);
		

		
		leftPanel.add(createPanelForComponent(table, "JTable"));
		
		final JButton butt = new JButton("GOOO!");
		leftPanel.add(butt);
		 butt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				 ScreenTransition s = new ScreenTransition(BasicDnD.this,new TransitionTarget(){

						public void setupNextScreen() {
							System.err.println("Threadname: "+Thread.currentThread().getName());
							butt.setText("ROOOOOOOOO");
							butt.getParent().doLayout();
						}},2000);
				 s.start();
			}});
		 	
		 

		//		rightPanel.add(new MessageButton());
		BinaryEditor comp = new BinaryEditor();
	
		comp.setBorder(BorderFactory.createTitledBorder("Binary component"));
		rightPanel.add(comp);
		rightPanel.add(new StringDragButton("Drag1","aap"));
		rightPanel.add(new StringDragButton("Drag2","noot"));
		rightPanel.add(new StringDropButton("Drop1","jet"));
		rightPanel.add(new StringDropButton("Drop2","wim"));
		rightPanel.add(new StringDragDropButton("DragDrop","zus","jet"));
		rightPanel.add(new StringDragDropButton("DragDrop2","jet","jet"));
		rightPanel.add(new JPanel());
		
		table.addColumn("Test", "Stuff", true);
		table.setMessage(m);
		table.setFillsViewportHeight(true);

		textField = new JTextField(30);
		textField.setText("Favorite foods:\nPizza, Moussaka, Pot roast, Chicken Halal");

		
		
		//		rightPanel.add(createPanelForComponent(textField, "JTextField"));

		// Create a list model and a list.

		// Create a tree.
		// DefaultMutableTreeNode rootNode = new
		// DefaultMutableTreeNode("Mia Familia");
		// DefaultMutableTreeNode sharon = new DefaultMutableTreeNode("Sharon");
		// rootNode.add(sharon);
		// DefaultMutableTreeNode maya = new DefaultMutableTreeNode("Maya");
		// sharon.add(maya);
		// DefaultMutableTreeNode anya = new DefaultMutableTreeNode("Anya");
		// sharon.add(anya);
		// sharon.add(new DefaultMutableTreeNode("Bongo"));
		// maya.add(new DefaultMutableTreeNode("Muffin"));
		// anya.add(new DefaultMutableTreeNode("Winky"));
		//      

		// Create the toggle button.
		toggleDnD = new JCheckBox("Turn on Drag and Drop");
		toggleDnD.setActionCommand("toggleDnD");
		toggleDnD.addActionListener(this);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setOneTouchExpandable(true);

		add(splitPane, BorderLayout.CENTER);
		add(toggleDnD, BorderLayout.PAGE_END);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	protected JPanel createVerticalBoxPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		return p;
	}

	public JPanel createPanelForComponent(JComponent comp, String title) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(comp, BorderLayout.CENTER);
		if (title != null) {
			panel.setBorder(BorderFactory.createTitledBorder(title));
		}
		return panel;
	}

	public void actionPerformed(ActionEvent e) {
		if ("toggleDnD".equals(e.getActionCommand())) {
			boolean toggle = toggleDnD.isSelected();
			textField.setDragEnabled(toggle);
			table.setDragEnabled(toggle);
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 * @throws NavajoException 
	 */
	private static void createAndShowGUI() throws NavajoException {
		// Create and set up the window.
		frame = new JFrame("BasicDnD");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new BasicDnD();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try {
					createAndShowGUI();
				} catch (NavajoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}