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

package org.jdesktop.jxlayer.plaf;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.plaf.item.*;

/**
 * The {@code AbstractLayerUI} provided default implementation for most
 * of the abstract methods in the {@link LayerUI} class.
 * It takes care of the management of {@code LayerItemListener}s and
 * defines the hook method to configure the {@code Graphics2D} instance
 * specified in the {@link #paint(Graphics,JComponent)} method.
 * It also provides convenient methods named
 * {@code process<eventType>Event} to process the given class of event.
 * <p/>
 * If state of the {@code AbstractLayerUI} is changed, call {@link #setDirty(boolean)}
 * with {@code true} as the parameter, it will repaint all {@code JXLayer}s
 * connected with this {@code AbstractLayerUI}
 *
 * @see JXLayer#setUI(LayerUI)
 */
public class AbstractLayerUI<V extends JComponent>
        extends LayerUI<V> {

    private final LayerItemChangeSupport support = new LayerItemChangeSupport(this);
    private static final Map<RenderingHints.Key, Object> emptyRenderingHintMap =
            Collections.unmodifiableMap(new HashMap<RenderingHints.Key, Object>(0));
    private boolean enabled = true;
    private boolean isDirty;

    /**
     * Configures the specified {@link JXLayer} appropriate for this {@code AbstractLayerUI}.
     * <p/>
     * This method is invoked when the {@code LayerUI} instance is being installed
     * as the UI delegate on the specified {@code JXLayer}.
     * Subclasses can install any listeners to the passed {@code JXLayer},
     * configure its glassPane or do any other setting up.
     * The default implementation registers the passed {@code JXLayer}
     * as a {@link LayerItemListener} for this {@code AbstractLayerUI}.
     * <p/>
     * <b>Note:</b> Subclasses can safely cast the passed component
     * to the {@code JXLayer<V>}
     *
     * @param c the {@code JXLayer} where this UI delegate is being installed
     * @see #uninstallUI(JComponent)
     */
    @Override
    public void installUI(JComponent c) {
        addLayerItemListener((JXLayer) c);
    }

    /**
     * Reverses configuration which was done on the specified component during
     * {@code installUI(JComponent)}.  This method is invoked when this
     * {@code LayerUI} instance is being removed as the UI delegate
     * for the specified {@link JXLayer}.
     * <p/>
     * {@code uninstallUI(JComponent)} should undo the
     * configuration performed in {@code installUI(JComponent)}, being careful to
     * leave the {@code JXLayer} instance in a clean state
     * (e.g. all previously set listeners must be removed in this method).
     * The default implementation removes the passed {@code JXLayer}
     * from the {@link LayerItemListener}'s list of this {@code AbstractLayerUI}.
     * <p/>
     * <b>Note:</b> Subclasses can safely cast the passed component
     * to the {@code JXLayer<V>}
     *
     * @param c the {@code JXLayer} where this UI delegate is being installed
     * @see #installUI(JComponent)
     */
    @Override
    public void uninstallUI(JComponent c) {
        removeLayerItemListener((JXLayer) c);
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
     * Notifies all {@code LayerItemListener}s that
     * have been added to this object.
     *
     * @see #addLayerItemListener(LayerItemListener)
     */
    protected void fireLayerItemChanged() {
        support.fireLayerItemChanged();
    }

    /**
     * Notifies all {@code LayerItemListener}s that
     * have been added to this object.
     *
     * @param event the {@code LayerItemChangeEvent}
     * @see #addLayerItemListener(LayerItemListener)
     */
    protected void fireLayerItemChanged(LayerItemChangeEvent event) {
        support.fireLayerItemChanged(event);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * {@code AbstractLayerUI} is enabled initially by default.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enables or disables this component, depending on the value of the
     * parameter {@code enabled}. An enabled {@code LayerUI} paints
     * the {@link JXLayer}s components they are set to and receives
     * their input and focuse events.
     * <p/>
     * {@code AbstractLayerUI} is enabled initially by default.
     *
     * @param enabled If {@code true}, this {@code AbstractLayerUI} is
     *            enabled; otherwise this {@code AbstractLayerUI} is disabled
     */
    public void setEnabled(boolean enabled) {
        boolean oldEnabled = isEnabled();
        this.enabled = enabled;
        if (oldEnabled != enabled) {
            // I don't use setDirty(true) here because I need the layer 
            // to be repainted even if the dirty flag was already set
            fireLayerItemChanged();
        }
    }

    /**
     * Returns the "dirty bit".
     * If {@code true}, then the {@code AbstractLayerUI} is considered dirty
     * and in need of being repainted.
     *
     * @return {@code true} if the {@code AbstractLayerUI} state has changed
     * and the {@link JXLayer}s it is set to need to be repainted.
     */
    protected boolean isDirty() {
        return isDirty;
    }

    /**
     * Sets the "dirty bit".
     * If {@code isDirty} is {@code true}, then the {@code AbstractLayerUI}
     * is considered dirty and it triggers the repainting
     * of the {@link JXLayer}s this {@code AbstractLayerUI} it is set to.
     *
     * @param isDirty whether this {@code AbstractLayerUI} is dirty or not.
     */
    protected void setDirty(boolean isDirty) {
        boolean oldDirty = isDirty();
        this.isDirty = isDirty;
        if (isDirty && !oldDirty) {
            fireLayerItemChanged();
        }
    }

    /**
     * {@inheritDoc}
     * <p/>
     * <b>Note:</b> It is rarely necessary to override this method, for
     * custom painting override {@link #paintLayer(Graphics2D, JXLayer)} instead
     * <p/>
     * This method configures the passed {@code Graphics} with help of the
     * {@link #configureGraphics(Graphics2D, JXLayer)} method,
     * then calls {@code paintLayer(Graphics2D, JXLayer)}
     * and resets the "dirty bit" at the end.
     *
     * @see #configureGraphics(Graphics2D, JXLayer)
     * @see #paintLayer(Graphics2D, JXLayer)
     * @see #setDirty(boolean)
     */
    @SuppressWarnings("unchecked")
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        JXLayer<V> l = (JXLayer<V>) c;
        configureGraphics(g2, l);
        paintLayer(g2, l);
        setDirty(false);
    }

    /**
     * Subclasses should implement this method
     * and perform custom painting operations here.
     * <p/>
     * The default implementation paints the passed {@code JXLayer} as is.
     *
     * @param g2 the {@code Graphics2D} context in which to paint
     * @param l the {@code JXLayer} being painted
     */
    protected void paintLayer(Graphics2D g2, JXLayer<V> l) {
        l.paint(g2);
    }

    /**
     * This method is called by the {@link #paint} method prior to
     * any drawing operations to configure the {@code Graphics2D} object.
     *  The default implementation sets the {@link Composite}, the clip,
     * {@link AffineTransform} and rendering hints
     * obtained from the corresponding hook methods.
     *
     * @param g2 the {@code Graphics2D} object to configure
     * @param l the {@code JXLayer} being painted
     *
     * @see #getComposite(JXLayer)
     * @see #getClip(JXLayer)
     * @see #getTransform(JXLayer)
     * @see #getRenderingHints(JXLayer)
     */
    protected void configureGraphics(Graphics2D g2, JXLayer<V> l) {
        Composite composite = getComposite(l);
        if (composite != null) {
            g2.setComposite(composite);
        }
        Shape clip = getClip(l);
        if (clip != null) {
            g2.clip(clip);
        }
        AffineTransform transform = getTransform(l);
        if (transform != null) {
            g2.transform(transform);
        }
        Map<RenderingHints.Key, Object> hints = getRenderingHints(l);
        if (hints != null) {
            for (RenderingHints.Key key : hints.keySet()) {
                Object value = hints.get(key);
                if (value != null) {
                    g2.setRenderingHint(key, hints.get(key));
                }
            }
        }
    }

    /**
     * Returns the {@link Composite} to be used during painting of this {@code JXLayer},
     * the default implementation returns {@code null}.
     *
     * @param l the {@code JXLayer} being painted
     * @return the {@link Composite} to be used during painting for the {@code JXLayer}
     */
    protected Composite getComposite(JXLayer<V> l) {
        return null;
    }

    /**
     * Returns the {@link AffineTransform} to be used during painting of this {@code JXLayer},
     * the default implementation returns {@code null}.
     *
     * @param l the {@code JXLayer} being painted
     * @return the {@link AffineTransform} to be used during painting of the {@code JXLayer}
     */
    protected AffineTransform getTransform(JXLayer<V> l) {
        return null;
    }

    /**
     * Returns the {@link Shape} to be used as the clip during painting of this {@code JXLayer},
     * the default implementation returns {@code null}.
     *
     * @param l the {@code JXLayer} being painted
     * @return the {@link Shape} to be used as the clip during painting of the {@code JXLayer}
     */
    protected Shape getClip(JXLayer<V> l) {
        return null;
    }

    /**
     * Returns the map of rendering hints to be used during painting of this {@code JXLayer},
     * the default implementation returns the empty unmodifiable map.
     *
     * @param l the {@code JXLayer} being painted
     * @return the map of rendering hints to be used during painting of the {@code JXLayer}
     */
    protected Map<RenderingHints.Key, Object> getRenderingHints(JXLayer<V> l) {
        return emptyRenderingHintMap;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * This method calls the appropriate
     * {@code process<eventType>Event}
     * method for the given class of event.
     */
    @Override
    public void eventDispatched(AWTEvent e, JXLayer<V> l) {
        if (e instanceof FocusEvent) {
            processFocusEvent((FocusEvent) e, l);
        } else if (e instanceof MouseEvent) {
            switch (e.getID()) {
                case MouseEvent.MOUSE_PRESSED:
                case MouseEvent.MOUSE_RELEASED:
                case MouseEvent.MOUSE_CLICKED:
                case MouseEvent.MOUSE_ENTERED:
                case MouseEvent.MOUSE_EXITED:
                    processMouseEvent((MouseEvent) e, l);
                    break;
                case MouseEvent.MOUSE_MOVED:
                case MouseEvent.MOUSE_DRAGGED:
                    processMouseMotionEvent((MouseEvent) e, l);
                    break;
                case MouseEvent.MOUSE_WHEEL:
                    processMouseWheelEvent((MouseWheelEvent) e, l);
                    break;
            }
        } else if (e instanceof KeyEvent) {
            processKeyEvent((KeyEvent) e, l);
        }
    }

    /**
     * Processes {@code FocusEvent} occurring on the {@link JXLayer}
     * or any of its subcomponents.
     *
     * @param e the {@code FocusEvent} to be processed
     * @param l the layer this LayerUI is set to
     */
    protected void processFocusEvent(FocusEvent e, JXLayer<V> l) {
    }

    /**
     * Processes {@code MouseEvent} occurring on the {@link JXLayer}
     * or any of its subcomponents.
     *
     * @param e the {@code MouseEvent} to be processed
     * @param l the layer this LayerUI is set to
     */
    protected void processMouseEvent(MouseEvent e, JXLayer<V> l) {
    }

    /**
     * Processes mouse motion events occurring on the {@link JXLayer}
     * or any of its subcomponents.
     *
     * @param e the {@code MouseEvent} to be processed
     * @param l the layer this LayerUI is set to
     */
    protected void processMouseMotionEvent(MouseEvent e, JXLayer<V> l) {
    }

    /**
     * Processes {@code MouseWheelEvent} occurring on the {@link JXLayer}
     * or any of its subcomponents.
     *
     * @param e the {@code MouseWheelEvent} to be processed
     * @param l the layer this LayerUI is set to
     */
    protected void processMouseWheelEvent(MouseWheelEvent e, JXLayer<V> l) {
    }

    /**
     * Processes {@code KeyEvent} occurring on the {@link JXLayer}
     * or any of its subcomponents.
     *
     * @param e the {@code KeyEvent} to be processed
     * @param l the layer this LayerUI is set to
     */
    protected void processKeyEvent(KeyEvent e, JXLayer<V> l) {
    }
}
