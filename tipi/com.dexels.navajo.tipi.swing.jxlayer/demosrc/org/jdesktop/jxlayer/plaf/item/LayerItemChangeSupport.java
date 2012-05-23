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

package org.jdesktop.jxlayer.plaf.item;

import javax.swing.event.*;

/**
 * This is a utility class that can be used by subclasses of {@code LayerItem}.
 * You can use an instance of this class as a member field
 * and delegate the {@code LayerItemChangeEvent}'s processing to it.
 *
 * @see org.jdesktop.jxlayer.plaf.AbstractLayerUI
 * @see org.jdesktop.jxlayer.plaf.effect.AbstractLayerEffect
 */
public class LayerItemChangeSupport {
    private final LayerItem source;
    private final EventListenerList listenerList;

    /**
     * Creates a new {@code LayerItemChangeSupport} object.
     *
     * @param source the {@link LayerItem} object to be given
     * as the source for any {@link LayerItemChangeEvent}
     */
    public LayerItemChangeSupport(LayerItem source) {
        this.source = source;
        listenerList = new EventListenerList();
    }

    /**
     * Add a {@code LayerItemListener} to the listener list.
     * If {@code listener} is null, no exception is thrown and no action
     * is taken.
     *
     * @param listener the {@link LayerItemListener} to be added
     */
    public void addLayerItemListener(LayerItemListener listener) {
        listenerList.add(LayerItemListener.class, listener);
    }

    /**
     * Remove a {@link LayerItemListener} from the listener list.
     *
     * @param listener the {@link LayerItemListener} to be removed
     */
    public void removeLayerItemListener(LayerItemListener listener) {
        listenerList.remove(LayerItemListener.class, listener);
    }

    /**
     * Returns an array of all the listeners that have been added to the
     * {@code LayerItemChangeSupport} object with {@code addLayerItemListener()}
     *
     * @return array all of the {@code LayerItemListener} added or an
     *         empty array if no listeners have been added
     */
    public LayerItemListener[] getLayerItemListeners() {
        return listenerList.getListeners(LayerItemListener.class);
    }

    /**
     * Notifies all {@code LayerItemListener}s that
     * have been added to this object.
     *
     * @see #addLayerItemListener(LayerItemListener)
     */
    public void fireLayerItemChanged() {
        fireLayerItemChanged(new LayerItemChangeEvent(source));
    }

    /**
     * Notifies all {@code LayerItemListener}s that
     * have been added to this object.
     *
     * @param event the {@code LayerItemChangeEvent}
     * @see #addLayerItemListener(LayerItemListener)
     */
    public void fireLayerItemChanged(LayerItemChangeEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == LayerItemListener.class) {
                ((LayerItemListener) listeners[i + 1]).layerItemChanged(event);
            }
        }
    }
}