/**
 * Copyright (c) 2006-2008, Alexander Potochkin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the JXLayer project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jdesktop.jxlayer.demo;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.demo.util.*;
import org.jdesktop.jxlayer.plaf.ext.*;

/**
 * A demo to show the abilities of the {@link MouseScrollableUI}.
 */
public class MouseScrollableDemo extends JFrame {

    private MouseScrollableUI mouseScrollableUI = new MouseScrollableUI();

    public MouseScrollableDemo() {
        super("JXLayer MouseScrollableDemo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar bar = new JMenuBar();

        JMenu optionsMenu = new JMenu("Options");
        final JCheckBoxMenuItem disableItem = new JCheckBoxMenuItem("Disable mouse scrolling");
        disableItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
        disableItem.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                mouseScrollableUI.setEnabled(!disableItem.isSelected());
            }
        });
        optionsMenu.add(disableItem);
        bar.add(optionsMenu);
        bar.add(new LafMenu());
        setJMenuBar(bar);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(new JXLayer<JScrollPane>(createLeftScrollPane(), mouseScrollableUI));
        splitPane.setRightComponent(new JXLayer<JScrollPane>(createRightScrollPane(), mouseScrollableUI));
        splitPane.setDividerLocation(330);

        add(splitPane);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private JScrollPane createLeftScrollPane() {
        JPanel panel = new JPanel(new GridLayout(0, 3));
        for (int i = 0; i < 25; i++) {
            panel.add(new JTextField(8));
            panel.add(new JPanel());
            panel.add(new JCheckBox("JCheckBox"));
            panel.add(new JRadioButton("JRadioButton"));
        }
        return new JScrollPane(panel);
    }

    private JScrollPane createRightScrollPane() {
        JTable table = new JTable(new AbstractTableModel() {
            public int getRowCount() {
                return 50;
            }

            public int getColumnCount() {
                return 20;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return "" + rowIndex + " " + columnIndex;
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return true;
            }
        });
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        return new JScrollPane(table);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MouseScrollableDemo().setVisible(true);
            }
        });
    }
}
