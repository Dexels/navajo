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

package org.jdesktop.jxlayer;

import java.awt.*;

import javax.swing.*;

/**
 * The default layout manager for the {@link JXLayer}.<br/>
 * It places the glassPane on top of the view component and makes it the same size as {@link JXLayer},
 * it also makes the view component the same size but minus layer's insets<br/>
 *
 * @see JXLayer#getView()
 * @see JXLayer#getGlassPane()
 */
public class LayerLayout implements LayoutManager {
    /**
     * {@inheritDoc}
     */
    public void layoutContainer(Container parent) {
        JXLayer layer = (JXLayer) parent;
        JComponent view = layer.getView();
        JComponent glassPane = layer.getGlassPane();
        if (view != null) {
            Insets insets = layer.getInsets();
            view.setLocation(insets.left, insets.top);
            view.setSize(layer.getWidth() - insets.left - insets.right,
                    layer.getHeight() - insets.top - insets.bottom);
        }
        if (glassPane != null) {
            glassPane.setLocation(0, 0);
            glassPane.setSize(layer.getWidth(), layer.getHeight());
        }
    }

    /**
     * {@inheritDoc}
     */
    public Dimension minimumLayoutSize(Container parent) {
        JXLayer layer = (JXLayer) parent;
        Insets insets = layer.getInsets();
        Dimension ret = new Dimension(insets.left + insets.right,
                insets.top + insets.bottom);
        JComponent view = layer.getView();
        if (view != null) {
            Dimension size = view.getMinimumSize();
            ret.width += size.width;
            ret.height += size.height;
        }
        if (ret.width == 0 || ret.height == 0) {
            ret.width = ret.height = 4;
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    public Dimension preferredLayoutSize(Container parent) {
        JXLayer layer = (JXLayer) parent;
        Insets insets = layer.getInsets();
        Dimension ret = new Dimension(insets.left + insets.right,
                insets.top + insets.bottom);
        JComponent view = layer.getView();
        if (view != null) {
            Dimension size = view.getPreferredSize();
            ret.width += size.width;
            ret.height += size.height;
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * {@inheritDoc}
     */
    public void removeLayoutComponent(Component comp) {
    }
}
