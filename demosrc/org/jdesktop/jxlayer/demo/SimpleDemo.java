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
import org.jdesktop.jxlayer.plaf.*;

/**
 * Here is a simple {@link JXLayer}'s demo which is a good point to start with.
 * It shows how easy it is to decorate any Swing components with JXLayer.
 * <p/>
 * We are going to decorate a button and paint a translucent foreground over it,
 * if the button in its normal state we'll use the green foreground color,
 * for the rollovered state (the mouse is over the button) we'll use orange
 * <p/>
 * To make it more intersting we'll provide a setForeground() method for our LayerUI
 * and make it possible to change the default foreground color from the "Options" menu
 * 
 * @see JXLayer
 * @see AbstractLayerUI
 */
public class SimpleDemo extends JFrame {

    /**
     * It is our custom LayerUI, which will help us to implement what we need
     */
    private SimpleButtonUI simpleButtonUI = new SimpleButtonUI();

    public SimpleDemo() {
        super("JXLayer demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setJMenuBar(createMenuBar());

        // We create a button, it will the "view" component for our JXLayer
        JButton button = new JButton("JButton");
        // Wrap it into the layer and set simpleButtonUI as the layer's UI;
        JXLayer<JButton> layer = new JXLayer<JButton>(button, simpleButtonUI);
        // add the layer as an ordinary component
        add(layer);

        setSize(200, 200);
        setLocationRelativeTo(null);
    }

    /**
     * Here is our custom LayerUI implementation;
     * the generic type matches the type of the view component
     */
    static class SimpleButtonUI extends AbstractLayerUI<JButton> {
        // The mutable foreground color
        private Color foreground = Color.GREEN;

        public Color getForeground() {
            return foreground;
        }

        public void setForeground(Color foreground) {
            // save the old color
            Color oldRolloverColor = getForeground();
            this.foreground = foreground;
            // if the new color is set we mark the UI as dirty
            if (!oldRolloverColor.equals(foreground)) {
                // it will eventually repaint the layer
                setDirty(true);
            }
        }

        // Note that we override AbstractLayerUI.paintLayer(), not LayerUI.paint()
        protected void paintLayer(Graphics2D g2, JXLayer<JButton> layer) {

            // super implementation just paints the layer as is
            super.paintLayer(g2, layer);

            // note that we can access the button via layer.getView() method
            final ButtonModel model = layer.getView().getModel();

            // choose the color depending on the state of the button
            if (model.isRollover()) {
                g2.setColor(Color.ORANGE);
            } else {
                g2.setColor(foreground);
            }

            // paint the selected color translucently
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f));
            g2.fillRect(0, 0, layer.getWidth(), layer.getHeight());
        }
    }

    // That's it! Please try the other demos in this package.
    // 
    // Should you have any questions or comments about JXLayer
    // feel free to send them to the following mailing list:
    //
    // https://jxlayer.dev.java.net/servlets/SummarizeList?listName=users
    //
    // Have a nice day

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu optionsMenu = new JMenu("Options");
        final JCheckBoxMenuItem colorItem = new JCheckBoxMenuItem("Red foreground");
        optionsMenu.add(colorItem);
        colorItem.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                simpleButtonUI.setForeground(colorItem.isSelected()?Color.RED:Color.GREEN);
            }
        });
        final JCheckBoxMenuItem enableItem = new JCheckBoxMenuItem("Enable SimpleButtonUI");
        enableItem.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                simpleButtonUI.setEnabled(enableItem.isSelected());
            }
        });
        enableItem.setSelected(true);
        optionsMenu.add(enableItem);

        bar.add(optionsMenu);
        bar.add(new LafMenu());
        return bar;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleDemo().setVisible(true);
            }
        });
    }
}
