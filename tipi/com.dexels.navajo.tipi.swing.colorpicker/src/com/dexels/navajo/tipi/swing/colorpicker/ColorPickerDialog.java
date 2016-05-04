/*
 * @(#)ColorPickerDialog.java
 *
 * $Date: 2014-06-06 14:04:49 -0400 (Fri, 06 Jun 2014) $
 *
 * Copyright (c) 2011 by Jeremy Wood.
 * All rights reserved.
 *
 * The copyright of this software is owned by Jeremy Wood. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Jeremy Wood. For details see accompanying license terms.
 * 
 * This software is probably, but not necessarily, discussed here:
 * https://javagraphics.java.net/
 * 
 * That site should also contain the most recent official version
 * of this software.  (See the SVN repository for more details.)
 */
package com.dexels.navajo.tipi.swing.colorpicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import com.dexels.navajo.tipi.swing.colorpicker.DialogFooter.EscapeKeyBehavior;

/**
 * This wraps a <code>ColorPicker</code> in a simple dialog with "OK" and "Cancel" options.
 * <P>
 * (This object is used by the static calls in <code>ColorPicker</code> to show a dialog.) <br>
 * <IMG SRC="https://javagraphics.java.net/resources/colorpicker.png" alt="Screenshot of ColorPickerDialog">
 * 
 * @see ColorPicker
 * @see ColorPickerPanel
 *
 */
class ColorPickerDialog extends JDialog {
    private static final long serialVersionUID = -2476563469757035242L;
    
    ColorPickerDialogContent content;
    ColorPicker cp;
    int alpha;
    Color returnValue = null;
    ActionListener okListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            returnValue = cp.getColor();
        }
    };
    DialogFooter footer;


    public ColorPickerDialog(JFrame owner, Color color, boolean includeOpacity) {
        super(owner);
        initialize(owner, color, includeOpacity);
    }

    public ColorPickerDialog(Dialog owner, Color color, boolean includeOpacity) {
        super(owner);
        initialize(owner, color, includeOpacity);
    }

    private void initialize(Component owner, Color color, boolean includeOpacity) {
        content = new ColorPickerDialogContent();
        content.setOpaque(false);
        getContentPane().add(content);
        
        cp = new ColorPicker(true, includeOpacity);
        cp.setOpaque(false);
        setModal(true);
        setResizable(false);
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 10, 10, 10);
        content.add(cp, c);
        c.gridy++;
        footer = DialogFooter.createDialogFooter(new JComponent[] {}, DialogFooter.OK_CANCEL_OPTION, DialogFooter.OK_OPTION, EscapeKeyBehavior.TRIGGERS_CANCEL);
        c.gridy++;
        c.weighty = 0;
        content.add(footer, c);
        cp.setRGB(color.getRed(), color.getGreen(), color.getBlue());
        cp.setOpacity(color.getAlpha());

        alpha = color.getAlpha();
        pack();
        setLocationRelativeTo(owner);

        footer.getButton(DialogFooter.OK_OPTION).addActionListener(okListener);
        
        // Custom options
        cp.setModeControlsVisible(false);
    }

    /**
     * @return the color committed when the user clicked 'OK'. Note this returns <code>null</code> if the user canceled this dialog, or exited via the close
     *         decoration.
     */
    public Color getColor() {
        return returnValue;
    }

    public void setBackgroundPaint(GradientPaint bgpaint) {
       content.setBackgroundPaint(bgpaint);
        
    }

}
