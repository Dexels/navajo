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

import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.demo.util.*;
import org.jdesktop.jxlayer.plaf.ext.*;

/**
 * A demo to show the abilities of the {@link DebugRepaintingUI}.
 */
public class DebugRepaintingDemo extends JFrame {

    public DebugRepaintingDemo() {
        super("DebugLayerDemo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tb = new JTabbedPane();
        tb.setTabPlacement(JTabbedPane.BOTTOM);
        tb.addTab("Components", createButtonPanel());
        tb.addTab("Table", createTable());
        tb.addTab("Tree", new JTree());
        JXLayer<JComponent> layer = new JXLayer<JComponent>(tb);
        add(layer);

        final DebugRepaintingUI dp = new DebugRepaintingUI();
        layer.setUI(dp);

        JMenuBar bar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        bar.add(optionsMenu);
        final JCheckBoxMenuItem startItem = new JCheckBoxMenuItem("Start debugging", true);
        startItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
        optionsMenu.add(startItem);
        startItem.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                dp.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        bar.add(optionsMenu);
        bar.add(new LafMenu());
        setJMenuBar(bar);

        setSize(400, 350);
        setLocationRelativeTo(null);
    }

    private JComponent createTable() {
        return new JScrollPane(new JTable(new AbstractTableModel() {

            public int getColumnCount() {
                return 10;
            }

            public int getRowCount() {
                return 20;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return "" + rowIndex;
            }
        }));
    }

    private JComponent createButtonPanel() {
        Box box = Box.createVerticalBox();
        box.add(Box.createGlue());
        addToBox(box, new JButton("JButton"));
        addToBox(box, new JRadioButton("JRadioButton"));
        addToBox(box, new JCheckBox("JCheckBox"));
        addToBox(box, new JTextField(10));
        String[] str = {"One", "Two", "Three"};
        addToBox(box, new JComboBox(str));
        addToBox(box, new JSlider(0, 100));
        return box;
    }

    private void addToBox(Box box, JComponent c) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(c);
        box.add(panel);
        box.add(Box.createGlue());
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DebugRepaintingDemo().setVisible(true);
            }
        });
    }
}
