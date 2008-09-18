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
import java.awt.event.*;
import java.awt.im.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import org.jdesktop.jxlayer.plaf.*;
import org.jdesktop.jxlayer.plaf.item.*;

/**
 * The universal decorator for Swing components
 * with which you can implement various advanced painting effects
 * as well as receive notification of all {@code MouseEvent}s,
 * {@code KeyEvent}s and {@code FocusEvent}s which generated within its borders.
 * <p/>
 * {@code JXLayer} delegates its painting and input events handling
 * to its {@link LayerUI} object which performs the actual decoration.
 * <p/>
 * The custom painting and events notification automatically work
 * for {@code JXLayer} itself and all its subcomponents.
 * This powerful combination makes it possible to enrich existing components
 * with new advanced functionality such as temporary locking of a hierarchy,
 * data tips for compound components, enhanced mouse scrolling etc...
 * <p/>
 * {@code JXLayer} is a great solution if you just need to do custom painting
 * over compound component or catch input events of its subcomponents.
 * <p/>
 * <pre>
 *         // create a component to be decorated with the layer
 *        JPanel panel = new JPanel();
 *        panel.add(new JButton("JButton"));
 *
 *        // This custom layerUI will fill the layer with translucent green
 *        // and print out all mouseMotion events generated within its borders
 *        AbstractLayerUI&lt;JPanel&gt; layerUI = new AbstractLayerUI&lt;JPanel&gt;() {
 *
 *            protected void paintLayer(Graphics2D g2, JXLayer&lt;JPanel&gt; l) {
 *                // this paints the layer as is
 *                super.paintLayer(g2, l);
 *                // fill it with the translucent green
 *                g2.setColor(new Color(0, 128, 0, 128));
 *                g2.fillRect(0, 0, l.getWidth(), l.getHeight());
 *            }
 *
 *            // overridden method which catches MouseMotion events
 *            protected void processMouseMotionEvent(MouseEvent e) {
 *                System.out.println("MouseMotionEvent detected: "
 *                        + e.getX() + " " + e.getY());
 *            }
 *        };
 *
 *        // create the layer for the panel using our custom layerUI
 *        JXLayer&lt;JPanel&gt; layer = new JXLayer&lt;JPanel&gt;(panel, layerUI);
 *
 *        // work with the layer as with any other Swing component
 *        frame.add(layer);
 * </pre>
 * <p/>
 * <b>Note:</b> {@code JXLayer} is very friendly to your application,
 * it uses only public Swing API and doesn't rely on any global settings
 * like custom {@code RepaintManager} or {@code AWTEventListener}.
 * It neither change the opaque state of its subcomponents
 * nor use the glassPane of its parent frame.
 * <p/>
 * {@code JXLayer} can be used under restricted environment
 * (e.g. unsigned applets)
 *
 * @see #setUI(LayerUI)
 * @see LayerUI
 * @see AbstractLayerUI
 */
public class JXLayer<V extends JComponent> extends JComponent
        implements LayerItemListener {
    private V view;
    private JComponent glassPane;
    private boolean isPainting;
    private final InputContext inputContext = new LayerInputContext();
    private static final LayerLayout sharedLayoutInstance = new LayerLayout();

    /**
     * Creates a new {@code JXLayer} object with empty view component
     * and empty {@link LayerUI}.
     *
     * @see #setView
     * @see #setUI
     */
    public JXLayer() {
        this(null, null);
    }

    /**
     * Creates a new {@code JXLayer} object with empty {@link LayerUI}.
     *
     * @param view the component to be decorated with this {@code JXLayer}
     * @see #setUI
     */
    public JXLayer(V view) {
        this(view, null);
    }

    /**
     * Creates a new {@code JXLayer} object with provided view component
     * and {@link LayerUI} object.
     *
     * @param view the component to be decorated
     * @param ui   the {@link LayerUI} deleagate to be used by this {@code JXLayer}
     */
    public JXLayer(V view, LayerUI<V> ui) {
        setLayout(sharedLayoutInstance);
        setGlassPane(new JXGlassPane());
        setOpaque(true);
        setView(view);
        setUI(ui);
        enableEvents(AWTEvent.FOCUS_EVENT_MASK |
                AWTEvent.MOUSE_EVENT_MASK |
                AWTEvent.MOUSE_MOTION_EVENT_MASK |
                AWTEvent.MOUSE_WHEEL_EVENT_MASK |
                AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * Returns the view component for this {@code JXLayer}.
     * <br/>This is a bound property.
     *
     * @return the view component for this {@code JXLayer}
     */
    public V getView() {
        return view;
    }

    /**
     * Sets the view component (the component to be decorated)
     * for this {@code JXLayer}.<br/>This is a bound property.
     *
     * @param view the view component for this {@code JXLayer}
     */
    public void setView(V view) {
        JComponent oldView = getView();
        if (oldView != null) {
            super.remove(oldView);
        }
        if (view != null) {
            super.addImpl(view, null, getComponentCount());
        }
        this.view = view;
        firePropertyChange("view", oldView, view);
        revalidate();
        repaint();
    }

    /**
     * Sets the {@link LayerUI} which will perform painting
     * and receive input events for this {@code JXLayer}.
     *
     * @param ui the {@link LayerUI} for this {@code JXLayer}
     */
    public void setUI(LayerUI<V> ui) {
        super.setUI(ui);
    }

    /**
     * Returns the {@link LayerUI} for this {@code JXLayer}.
     *
     * @return the {@link LayerUI} for this {@code JXLayer}
     */
    @SuppressWarnings("unchecked")
    public LayerUI<V> getUI() {
        return (LayerUI<V>) ui;
    }

    /**
     * Returns the glassPane component of this {@code JXLayer}.
     * <br/>This is a bound property.
     *
     * @return the glassPane component of this {@code JXLayer}
     */
    public JComponent getGlassPane() {
        return glassPane;
    }

    /**
     * Sets the glassPane component of this {@code JXLayer}.
     * <br/>This is a bound property.
     *
     * @param glassPane the glassPane component of this {@code JXLayer}
     */
    public void setGlassPane(JComponent glassPane) {
        if (glassPane == null) {
            throw new IllegalArgumentException("GlassPane can't be set to null");
        }
        JComponent oldGlassPane = getGlassPane();
        if (oldGlassPane != null) {
            super.remove(oldGlassPane);
        }
        super.addImpl(glassPane, null, 0);
        this.glassPane = glassPane;
        firePropertyChange("glassPane", oldGlassPane, glassPane);
        revalidate();
        repaint();
    }

    /**
     * {@code JXLayer} can have only two direct children:
     * the view component and the glassPane,
     * so this method throws {@code UnsupportedOperationException}.
     * <p/>
     * {@inheritDoc}
     *
     * @see #setView
     * @see #setGlassPane
     */
    protected void addImpl(Component comp, Object constraints, int index) {
        throw new UnsupportedOperationException("JXLayer.add() is not supported;" +
                " use setView() instead");
    }

    /**
     * Removes the {@code JXLayer}'s view component.
     *
     * @param comp the component to be removed
     */
    public void remove(Component comp) {
        if (comp == getView()) {
            view = null;
        } else if (comp == getGlassPane()) {
            throw new IllegalArgumentException("GlassPane can't be removed");
        }
        super.remove(comp);
    }

    /**
     * Removes the {@code JXLayer}'s view component.
     */
    public void removeAll() {
        setView(null);
    }

    /**
     * A non-<code>null</code> border,
     * or non-zero insets, isn't supported, to prevent the geometry
     * of this component from becoming too complex to inhibit subclassing.
     *
     * @param border the {@code Border} to set
     * @throws IllegalArgumentException this method is not implemented
     */
    public void setBorder(Border border) {
        if (border != null) {
            throw new IllegalArgumentException("JXLayer.setBorder() not supported");
        }
    }

    private boolean isLayerValid() {
        return getUI() != null && getUI().isEnabled() && getView() != null
                && getWidth() > 0 && getHeight() > 0;
    }

    /**
     * Delegates all painting to the {@link LayerUI} object.
     * <p>
     * If no view component or {@code LayerUI} object is provided,
     * {@link LayerUI#isEnabled()} returns {@code false},
     * any of {@code JXLayer}'s size is less than {@code 1}
     * or {@code g} is not instance of Graphics2D
     * then the super implementation of {@code paint} method is called.
     *
     * @param g the {@code Graphics} to render to
     */
    public void paint(Graphics g) {
        LayerUI<V> layerUI = getUI();
        if (isLayerValid() && !isPainting && g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g.create();
            isPainting = true;
            layerUI.paint(g2, this);
            isPainting = false;
            g2.dispose();
        } else {
            super.paint(g);
        }
    }

    /**
     * To enable the correct painting of the glassPane and view component,
     * the {@code JXLayer}> overrides the default implementation of
     * this method to return {@code false}.

     * @return false
     */
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    /**
     * This method is public as an implementation side effect.
     * {@code JXLayer} can be registered as a {@code LayerItemListener}
     * and usually receives the  {@code LayerItemChangeEvent}s
     * from its {@code LayerUI}.
     *
     * @param e the {@link LayerItemChangeEvent}
     */
    public void layerItemChanged(LayerItemChangeEvent e) {
        Shape clip = e.getClip(getWidth(), getHeight());
        if (clip != null) {
            repaint(clip.getBounds());
        } else {
            repaint();
        }
    }

    /**
     * Delegates its functionality to the {@link LayerUI#updateUI(JXLayer)} method,
     * if {@code LayerUI} is set and enabled.
     */
    public void updateUI() {
        LayerUI<V> layerUI = getUI();
        if (isLayerValid()) {
            layerUI.updateUI(this);
        }
    }

    /**
     * Delegates its functionality to the {@link LayerUI#contains(JComponent, int, int)} method,
     * if {@code LayerUI} is set and enabled.
     *
     * {@inheritDoc}
     */
    public boolean contains(int x, int y) {
        LayerUI<V> layerUI = getUI();
        if (isLayerValid()) {
            return layerUI.contains(this, x, y);
        }
        // the implementation of Component.inside(int, int) method
        // which is eventually called by super.contains(int, int)
        // inside() is deprecated, so I copied this line here
        return (x >= 0) && (x < getWidth()) && (y >= 0) && (y < getHeight());
    }

    /**
     * Returns the proxy input context which is used to catch all input events
     * and focus events from the subcomponents of this {@code JXLayer}.
     * <p/>
     * When input event is happened and {@code LayerUI} is set and enabled
     * then this proxy input context notifies
     * the {@link LayerUI#eventDispatched(AWTEvent, JXLayer)} method
     * and then calls the super implementation.
     *
     * @return the private proxy {@code InputContext} instance
     */
    public InputContext getInputContext() {
        return inputContext;
    }

    /**
     * Processes events on this {@code JXLayer}.
     * If an input or focus event is generated and {@code LayerUI} is set and enabled,
     * this method notifies the {@link LayerUI#eventDispatched(AWTEvent, JXLayer)}
     * and then calls the super implementation.
     *
     * @param event the event to be processed
     */
    protected void processEvent(AWTEvent event) {
        super.processEvent(event);
        if (event instanceof InputEvent || event instanceof FocusEvent) {
            LayerUI<V> layerUI = getUI();
            if (isLayerValid()) {
                layerUI.eventDispatched(event, this);
            }
        }
    }

    private class LayerInputContext extends InputContext {

        public void dispatchEvent(AWTEvent event) {
            LayerUI<V> layerUI = getUI();
            if (isLayerValid()
                    && (event instanceof InputEvent || event instanceof FocusEvent)) {
                layerUI.eventDispatched(event, JXLayer.this);
            }
            JXLayer.super.getInputContext().dispatchEvent(event);
        }

        public void dispose() {
            JXLayer.super.getInputContext().dispose();
        }

        public void endComposition() {
            JXLayer.super.getInputContext().endComposition();
        }

        public Object getInputMethodControlObject() {
            return JXLayer.super.getInputContext().getInputMethodControlObject();
        }

        public Locale getLocale() {
            return JXLayer.super.getInputContext().getLocale();
        }

        public boolean isCompositionEnabled() {
            return JXLayer.super.getInputContext().isCompositionEnabled();
        }

        public void reconvert() {
            JXLayer.super.getInputContext().reconvert();
        }

        public void removeNotify(Component client) {
            JXLayer.super.getInputContext().removeNotify(client);
        }

        public boolean selectInputMethod(Locale locale) {
            return JXLayer.super.getInputContext().selectInputMethod(locale);
        }

        public void setCharacterSubsets(Character.Subset[] subsets) {
            JXLayer.super.getInputContext().setCharacterSubsets(subsets);
        }

        public void setCompositionEnabled(boolean enable) {
            JXLayer.super.getInputContext().setCompositionEnabled(enable);
        }
    }
}


