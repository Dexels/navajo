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
import javax.swing.border.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.demo.util.*;
import org.jdesktop.jxlayer.plaf.ext.*;

public class ButtonPanelDemo extends JFrame {
    private ButtonGroup radioGroup = new ButtonGroup();

    private ButtonPanelUI buttonPanelUI = new ButtonPanelUI();
    private ButtonPanelUI cyclicButtonPanelUI = new ButtonPanelUI(true);

    public ButtonPanelDemo() {
        super("ButtonPanelUI demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel topPanel = new JPanel(new GridLayout(1, 0));

        JPanel checkBoxPanel = createCheckBoxPanel();
        topPanel.add(new JXLayer<JComponent>(checkBoxPanel, buttonPanelUI));

        JPanel radioGroupPanel = createRadioButtonPanel();
        topPanel.add(new JXLayer<JComponent>(radioGroupPanel, buttonPanelUI));

        add(topPanel);

        JPanel buttonPanel = createButtonPanel();
        add(new JXLayer<JComponent>(buttonPanel, cyclicButtonPanelUI), BorderLayout.SOUTH);

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem unselectItem = new JMenuItem("Unselect radioButton");
        unselectItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
        unselectItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // hack for 1.5
                // in 1.6 ButtonGroup.clearSelection added
                JRadioButton b = new JRadioButton();
                radioGroup.add(b);
                b.setSelected(true);
                radioGroup.remove(b);
            }
        });
        menu.add(unselectItem);

        JCheckBoxMenuItem disableItem = new JCheckBoxMenuItem("Disable ButtonPanelUI");
        disableItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
        disableItem.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
                buttonPanelUI.setEnabled(!isSelected);
                cyclicButtonPanelUI.setEnabled(!isSelected);
            }
        });
        menu.add(disableItem);

        bar.add(menu);
        bar.add(new LafMenu());
        setJMenuBar(bar);

        setSize(300, 300);
        setLocationRelativeTo(null);
    }

    private JPanel createRadioButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        JRadioButton one = new JRadioButton("One");
        panel.add(one);
        radioGroup.add(one);
        JRadioButton two = new JRadioButton("Two");
        panel.add(two);
        radioGroup.add(two);
        JRadioButton three = new JRadioButton("Three");
        panel.add(three);
        radioGroup.add(three);
        JRadioButton four = new JRadioButton("Four");
        panel.add(four);
        radioGroup.add(four);
        one.setSelected(true);
        panel.setBorder(BorderFactory.createTitledBorder(""));
        return panel;
    }

    private JPanel createCheckBoxPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        JCheckBox one = new JCheckBox("One");
        panel.add(one);
        JCheckBox two = new JCheckBox("Two");
        panel.add(two);
        JCheckBox three = new JCheckBox("Three");
        panel.add(three);
        JCheckBox four = new JCheckBox("Four");
        panel.add(four);
        panel.setBorder(BorderFactory.createTitledBorder(""));
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel ret = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Does ButtonPanelUI support arrow keys ?");
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        JPanel temp = new JPanel();
        temp.add(label);
        ret.add(temp);

        JPanel panel = new JPanel();
        panel.add(new JButton("Yes"));
        panel.add(new JButton("Sure"));
        panel.add(new JButton("Absolutely !"));
        panel.setBorder(BorderFactory.createTitledBorder(null,
                "ButtonPanelUI.setFocusCyclic(true)",
                TitledBorder.CENTER, TitledBorder.BOTTOM));
        ret.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        ret.add(panel, BorderLayout.SOUTH);
        return ret;
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ButtonPanelDemo().setVisible(true);
            }
        });
    }
}