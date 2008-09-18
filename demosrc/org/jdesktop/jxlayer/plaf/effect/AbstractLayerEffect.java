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

package org.jdesktop.jxlayer.plaf.effect;

import org.jdesktop.jxlayer.plaf.item.*;

/**
 * The default implementation of the {@link LayerEffect} interface.
 * It provides the mutable enabled state
 * as well as {@code LayerItemListener} suppport.
 *
 * @see AbstractBufferedImageOpEffect
 * @see BufferedImageOpEffect
 */
public abstract class AbstractLayerEffect implements LayerEffect {
    private final LayerItemChangeSupport support = new LayerItemChangeSupport(this);
    private boolean isEnabled = true;

    /**
     * {@inheritDoc}
     *
     * The default return value for this method is {@code true}.
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Enables or disables this {@code AbstractEffect},
     * depending on the value of the parameter {@code isEnabled}.
     * An enabled item takes part in painting process
     * when a disabled one is not taken into account.
     * <p/>
     * When the enabled state is changed this method
     * notifies all the {@code LayerItemListener} which were added
     * to this {@code AbstractEffect}.  
     *
     * @param isEnabled if {@code true} this item is enabled;
     * otherwise this item is disabled
     */
    public void setEnabled(boolean isEnabled) {
        boolean oldEnabled = isEnabled();
        this.isEnabled = isEnabled;
        if (oldEnabled == isEnabled) {
            return;
        }
        fireLayerItemChanged();
    }

    /**
     * {@inheritDoc}
     */
    public void addLayerItemListener(LayerItemListener l) {
        support.addLayerItemListener(l);
    }

    /**
     * {@inheritDoc}
     */
    public LayerItemListener[] getLayerItemListeners() {
        return support.getLayerItemListeners();
    }

    /**
     * {@inheritDoc}
     */
    public void removeLayerItemListener(LayerItemListener l) {
        support.removeLayerItemListener(l);
    }

    /**
     * Notifies all {@link LayerItemListener}s
     * that have been added to this object.
     */
    protected void fireLayerItemChanged() {
        support.fireLayerItemChanged();
    }

    /**
     * Notifies all {@link LayerItemListener}s that have registered interest
     * for notification on this event type.
     *
     * @param event the {@code LayerItemChangeEvent} to be passed
     * to the {@code LayerItemListener}s added to this object  
     */
    protected void fireLayerItemChanged(LayerItemChangeEvent event) {
        support.fireLayerItemChanged(event);
    }
}
