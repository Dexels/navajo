/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swing.colorpicker;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ColorPickerDialogContent extends JPanel {
    private static final long serialVersionUID = -4048115778909223398L;
    protected GradientPaint paint;

    @Override
    public void paint(Graphics g) {
       
        if (paint != null) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setPaint(paint);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paint(g);

    }

    public void setBackgroundPaint(GradientPaint bgpaint) {
        this.paint = bgpaint;
    }

}
