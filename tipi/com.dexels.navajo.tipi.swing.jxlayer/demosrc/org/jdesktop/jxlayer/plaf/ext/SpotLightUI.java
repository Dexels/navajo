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

package org.jdesktop.jxlayer.plaf.ext;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.plaf.*;

/**
 * SpotLightUI paints the layer as is and then
 * it paints translucent mask on the top.
 * <p/>
 * Here you can see the example of overridden {@code contains()} method,
 * which implements a custom mouseEvent filter;<br>
 * A mouseEvent will be processed only if any of added shapes contain its coordinates
 * <p/>
 * The mask is generated with help of the {@code paintLayer()};<br>
 * The perfomance if very good because most of the time the cached image is used,
 * and it is repainted only when the layer is resized 
 * or when {@code setDirty(true)} is called)
 *
 * @see #paint(Graphics,JComponent)
 * @see #contains(JComponent,int,int)
 */
public class SpotLightUI extends AbstractBufferedLayerUI<JComponent> {

    /**
     * Clip list.
     */
    private ArrayList<Shape> clipList = new ArrayList<Shape>();

    /**
     * Overlay color for the non-matching items.
     */
    private Color overlayColor;

    /**
     * Width of the "soft border"
     */
    private int softClipWidth;

    /**
     * Creates an instance of SpotLightUI
     */
    public SpotLightUI() {
        this(0);
    }

    /**
     * Creates an instance of SpotLightUI with the specified width 
     * of the "soft border" 
     * 
     * @param softClipWidth the width of the "soft border" 
     */
    public SpotLightUI(int softClipWidth) {
        this(new Color(0, 0, 0, 128), softClipWidth);
    }

    /**
     * Creates an instance of SpotLightUI with the specified width 
     * of the "soft border" and overlay color, which is usually translucent.
     * 
     * @param overlayColor the overlay color
     * @param softClipWidth the width of the "soft border"
     */
    public SpotLightUI(final Color overlayColor, int softClipWidth) {
        setOverlayColor(overlayColor);
        setSoftClipWidth(softClipWidth);
    }

    private void softClipping(Graphics2D g2, Shape shape) {
        g2.setComposite(AlphaComposite.Src);
        for (int i = 0; i < softClipWidth; i++) {
            int alpha = (i + 1) * overlayColor.getAlpha()
                    / (softClipWidth + 1);
            Color temp = new Color(
                    overlayColor.getRed(),
                    overlayColor.getGreen(),
                    overlayColor.getBlue(), alpha);
            g2.setColor(temp);
            g2.setStroke(new BasicStroke(softClipWidth - i));
            g2.draw(shape);
        }
    }

    /**
     * Returns the overlay color used for spotlight effect 
     * 
     * @return the overlay color used for spotlight effect
     */
    public Color getOverlayColor() {
        return overlayColor;
    }

    /**
     * Sets the overlay color used for spotlight effect
     * 
     * @param overlayColor the overlay color used for spotlight effect
     */
    public void setOverlayColor(Color overlayColor) {
        if (overlayColor == null) {
            throw new IllegalArgumentException("overlayColor is null");
        }
        this.overlayColor = overlayColor;
        setDirty(true);
    }

    /**
     * Gets the width for the "soft border"
     * 
     * @return the width for the "soft border"
     */
    public int getSoftClipWidth() {
        return softClipWidth;
    }

    /**
     * Sets the width for the "soft border"
     * 
     * @param softClipWidth the width for the "soft border"
     */
    public void setSoftClipWidth(int softClipWidth) {
        if (softClipWidth < 0) {
            throw new IllegalArgumentException("softClipWidth can't be less than 0");
        }
        this.softClipWidth = softClipWidth;
        setDirty(true);
    }

    /**
     * Resets this SpotLightUI.
     */
    public void reset() {
        clipList.clear();
        setDirty(true);
    }

    /**
     * Adds the specified shape to the slip list.
     *
     * @param shape Shape to add to the clip list.
     */
    public void addShape(Shape shape) {
        clipList.add(shape);
        setDirty(true);
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        //always paint the layer as is 
        c.paint(g);
        //and then call the super implementation
        super.paint(g, c);
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    protected void paintLayer(Graphics2D g2, JXLayer<JComponent> l) {
        //note that we don't call super.paintLayer() here
        //since layer is already painted by paint() method.
        //The SpotLight mask is regenerated after setDirty(true) is called
        //or after layer changes its size
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, l.getWidth(), l.getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(overlayColor);
        g2.fillRect(0, 0, l.getWidth(), l.getHeight());
        for (Shape shape : clipList) {
            g2.setClip(shape);
            g2.setComposite(AlphaComposite.Clear);
            g2.fill(shape);
            softClipping(g2, shape);
        }
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public boolean contains(JComponent c, int x, int y) {
        for (Shape shape : clipList) {
            if (shape.contains(x, y)) {
                return true;
            }
        }
        return false;
    }
}

