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
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.event.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.demo.util.*;
import org.jdesktop.jxlayer.plaf.ext.*;

public class SpotLightDemo extends JFrame {
    private SpotLightUI spotLightUI = new SpotLightUI(15);

    private Ellipse2D shape;
    
    public SpotLightDemo() {
        super("SpotLight effect demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JXLayer<JComponent> layer = 
                new JXLayer<JComponent>(createDemoPanel(4, 5),
                        spotLightUI);
        JScrollPane pane = new JScrollPane(layer);
        add(pane);

        shape = new Ellipse2D.Double(20, 20, 120, 120);
        spotLightUI.addShape(shape);
        
        add(createToolPanel(), BorderLayout.SOUTH);
        
        setSize(400, 300);
        setLocationRelativeTo(null);
        setJMenuBar(LafMenu.createMenuBar());
    }

    private JComponent createDemoPanel(int w, int h) {
        JPanel panel = new JPanel(new GridLayout(w, h));
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                JButton button = new JButton("Hello");
                button.setFocusable(false);
                panel.add(button);
            }
        }
        panel.setPreferredSize(new Dimension(500, 400));
        return panel;
    }
    
    private JComponent createToolPanel() {
        JPanel panel = new JPanel();
        final JSpinner xspinner = new JSpinner(
                new SpinnerNumberModel((int) shape.getX(), 0, 400, 5));
        final JSpinner yspinner = new JSpinner(
                new SpinnerNumberModel((int) shape.getY(), 0, 400, 5));
        
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                spotLightUI.reset();
                shape.setFrame((Integer) xspinner.getValue(), 
                        ((Integer)yspinner.getValue()),
                        shape.getWidth(), shape.getHeight());
                spotLightUI.addShape(shape);
            }
        };
        xspinner.addChangeListener(listener);
        yspinner.addChangeListener(listener);
        
        panel.add(new JLabel("X:"));
        panel.add(xspinner);
        panel.add(new JLabel(" Y:"));
        panel.add(yspinner);
        
        final JSpinner clipSpinner = new JSpinner(new SpinnerNumberModel(15, 0, 100, 1));
        clipSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                spotLightUI.setSoftClipWidth((Integer) clipSpinner.getValue());
            }
        });

        panel.add(new JLabel(" Border size:"));
        panel.add(clipSpinner);
        return panel;
    }
    
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SpotLightDemo().setVisible(true);
            }
        });
    }
}
