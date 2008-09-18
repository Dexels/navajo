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

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.demo.util.*;
import org.jdesktop.jxlayer.plaf.effect.*;
import org.jdesktop.jxlayer.plaf.ext.*;
import org.jdesktop.swingx.painter.*;

import com.jhlabs.image.*;

/**
 * A demo to show the abilities of the {@link LockableUI}.
 * 
 * @see BusyPainterUI  
 */
public class LockableDemo extends JFrame {
    private JXLayer<JComponent> layer;
    private LockableUI blurUI = 
            new LockableUI(new BufferedImageOpEffect(new BlurFilter()));
    private LockableUI embossUI = 
            new LockableUI(new BufferedImageOpEffect(new EmbossFilter()));
    private LockableUI busyPainterUI = new BusyPainterUI();

    private JCheckBoxMenuItem disablingItem =
            new JCheckBoxMenuItem(new AbstractAction("Lock the layer") {

                public void actionPerformed(ActionEvent e) {
                    blurItem.setEnabled(!disablingItem.isSelected());
                    embossItem.setEnabled(!disablingItem.isSelected());
                    busyPainterItem.setEnabled(!disablingItem.isSelected());

                    blurUI.setLocked(disablingItem.isSelected());
                    embossUI.setLocked(disablingItem.isSelected());
                    busyPainterUI.setLocked(disablingItem.isSelected());
                }
            });

    private JRadioButtonMenuItem blurItem = new JRadioButtonMenuItem("Blur effect");
    private JRadioButtonMenuItem embossItem = new JRadioButtonMenuItem("Emboss effect");
    private JRadioButtonMenuItem busyPainterItem = new JRadioButtonMenuItem("BusyPainter");

    public LockableDemo() {
        super("Lockable layer demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent view = createLayerPanel();
        layer = new JXLayer<JComponent>(view);

        layer.setUI(blurUI);
        add(layer);
        add(createToolPanel(), BorderLayout.EAST);
        setJMenuBar(createMenuBar());
        setSize(380, 300);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LockableDemo().setVisible(true);
            }
        });
    }

    private JMenuBar createMenuBar() {
        JMenu menu = new JMenu("Menu");

        disablingItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));

        menu.add(disablingItem);
        menu.addSeparator();

        blurItem.setSelected(true);
        blurItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_MASK));
        menu.add(blurItem);

        embossItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.ALT_MASK));
        menu.add(embossItem);

        busyPainterItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.ALT_MASK));
        menu.add(busyPainterItem);

        ButtonGroup group = new ButtonGroup();
        group.add(blurItem);
        group.add(embossItem);
        group.add(busyPainterItem);

        ItemListener menuListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (blurItem.isSelected()) {
                    layer.setUI(blurUI);
                } else if (embossItem.isSelected()) {
                    layer.setUI(embossUI);
                } else if (busyPainterItem.isSelected()) {
                    layer.setUI(busyPainterUI);
                }
            }
        };

        blurItem.addItemListener(menuListener);
        embossItem.addItemListener(menuListener);
        busyPainterItem.addItemListener(menuListener);

        JMenuBar bar = new JMenuBar();
        bar.add(menu);

        bar.add(new LafMenu());
        return bar;
    }

    private JComponent createLayerPanel() {
        JComponent panel = new JPanel();
        panel.add(new JCheckBox("JCheckBox"));
        panel.add(new JRadioButton("JRadioButton"));
        panel.add(new JTextField(15));
        JButton button = new JButton("Have a nice day");
        button.setMnemonic('H');
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("LockableLayerDemo.actionPerformed");
            }
        });
        panel.add(button);
        panel.add(new JTextField(15));
        panel.add(new JCheckBox("JCheckBox"));
        panel.add(new JRadioButton("JRadioButton"));
        panel.add(new JTextField(15));
        panel.add(new JCheckBox("JCheckBox"));
        panel.add(new JRadioButton("JRadioButton"));
        panel.setBorder(BorderFactory.createEtchedBorder());
        return panel;
    }

    private JComponent createToolPanel() {
        JComponent box = Box.createVerticalBox();
        JCheckBox button = new JCheckBox(disablingItem.getText());
        button.setModel(disablingItem.getModel());
        box.add(Box.createGlue());
        box.add(button);
        box.add(Box.createGlue());
        JRadioButton blur = new JRadioButton(blurItem.getText());
        blur.setModel(blurItem.getModel());
        box.add(blur);
        JRadioButton emboss = new JRadioButton(embossItem.getText());
        emboss.setModel(embossItem.getModel());
        box.add(emboss);
        JRadioButton translucent = new JRadioButton(busyPainterItem.getText());
        translucent.setModel(busyPainterItem.getModel());
        box.add(translucent);
        box.add(Box.createGlue());
        return box;
    }

    /**
     * Subclass of the {@link LockableUI} which uses the {@link BusyPainterUI}
     * from the SwingX project to implement the "busy effect" 
     * when {@link JXLayer} is locked.  
     */
    public static class BusyPainterUI extends LockableUI 
            implements ActionListener {
        private BusyPainter<JComponent> busyPainter;
        private Timer timer;
        private int frameNumber;

        public BusyPainterUI() {
            busyPainter = new BusyPainter<JComponent>() {
                protected void doPaint(Graphics2D g, JComponent object,
                                       int width, int height) {
                    // centralize the effect
//                    Rectangle r = getTrajectory().getBounds();
//                    int tw = width - r.width - 2 * r.x;
//                    int th = height - r.height - 2 * r.y;
//                    g.translate(tw / 2, th / 2);
                    super.doPaint(g, object, width, height);
                }
            };
//            busyPainter.setPointShape(new Ellipse2D.Double(0, 0, 20, 20));
//            busyPainter.setTrajectory(new Ellipse2D.Double(0, 0, 100, 100));
            timer = new Timer(100, this);
        }

        @Override
        protected void paintLayer(Graphics2D g2, JXLayer<JComponent> l) {
            super.paintLayer(g2, l);
            if (isLocked()) {
                busyPainter.paint(g2, l, l.getWidth(), l.getHeight());
            }
        }

        @Override
        public void setLocked(boolean isLocked) {
            super.setLocked(isLocked);
            if (isLocked) {
                timer.start();
            } else {
                timer.stop();
            }
        }

        // Change the frame for the busyPainter
        // and mark BusyPainterUI as dirty 
        public void actionPerformed(ActionEvent e) {
            frameNumber = (frameNumber + 1) % 8;
            busyPainter.setFrame(frameNumber);
            // this will repaint the layer
            setDirty(true);
        }
    }
}

