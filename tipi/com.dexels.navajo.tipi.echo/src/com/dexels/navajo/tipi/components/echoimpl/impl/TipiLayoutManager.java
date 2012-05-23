package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;

public interface TipiLayoutManager {
    /**
     * If the layout manager uses a per-component string, adds the component
     * <code>comp</code> to the layout, associating it with the string
     * specified by <code>name</code>.
     * 
     * @param name
     *            the string to be associated with the component
     * @param comp
     *            the component to be added
     */
    void addLayoutComponent(String name, Component comp);

    /**
     * Removes the specified component from the layout.
     * 
     * @param comp
     *            the component to be removed
     */
    void removeLayoutComponent(Component comp);

    /**
     * Calculates the preferred size dimensions for the specified container,
     * given the components it contains.
     * 
     * @param parent
     *            the container to be laid out
     * 
     * @see #minimumLayoutSize
     */
    Extent[] preferredLayoutSize(Component parent);

    /**
     * Calculates the minimum size dimensions for the specified container, given
     * the components it contains.
     * 
     * @param parent
     *            the component to be laid out
     * @see #preferredLayoutSize
     */
    Extent[] minimumLayoutSize(Component parent);

    /**
     * Lays out the specified container.
     * 
     * @param parent
     *            the container to be laid out
     */
    void layoutContainer(Component parent);
}
