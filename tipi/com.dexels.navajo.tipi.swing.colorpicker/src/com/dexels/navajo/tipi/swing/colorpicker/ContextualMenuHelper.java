/*
 * @(#)ContextualMenuHelper.java
 *
 * $Date: 2015-08-30 11:03:40 -0400 (Sun, 30 Aug 2015) $
 *
 * Copyright (c) 2013 by Jeremy Wood.
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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import com.dexels.navajo.tipi.swing.colorpicker.util.ColorPickerBooleanProperty;
import com.dexels.navajo.tipi.swing.colorpicker.util.ColorPickerEnumProperty;

public class ContextualMenuHelper {
    private static final String MENU_KEY = ContextualMenuHelper.class.getName() + ".menuKey";

    /**
     * Add a menu item that invokes the argument Runnable when selected.
     * 
     * @param runnable this is invoked (via SwingUtilities.invokeLater()) when this menu item is selected.
     */
    public static void add(JComponent jc, String menuItemName, Runnable runnable) {
        ContextualMenuHelper cmh = getContextualMenuHelper(jc);
        cmh.add(menuItemName, runnable);
    }

    /**
     * Return the ContextualMenuHelper the static helper methods refer to.
     * 
     * @param jc the component to retrieve a common ContextualMenuHelper for.
     * @return the common ContextualMenuHelper for the argument. This will create one if it doesn't already exist.
     */
    public static ContextualMenuHelper getContextualMenuHelper(JComponent jc) {
        ContextualMenuHelper cmh = (ContextualMenuHelper) jc.getClientProperty(MENU_KEY);
        if (cmh == null) {
            cmh = new ContextualMenuHelper(jc);
            jc.putClientProperty(MENU_KEY, cmh);
        }
        return cmh;
    }

    /**
     * Add a checkbox menu item to control a BooleanProperty.
     * 
     * @param runnable this is invoked (via SwingUtilities.invokeLater()) when this menu item is selected.
     */
    public static void addToggle(JComponent jc, ColorPickerBooleanProperty property, Runnable runnable) {
        ContextualMenuHelper cmh = getContextualMenuHelper(jc);
        cmh.addToggle(property, runnable);
    }

    /**
     * Add a submenu that offers a choice of radiobutton menu items to control an EnumProperty.
     * 
     * @param runnable this is invoked (via SwingUtilities.invokeLater()) when any choice is selected.
     */
    public static void addPopupMenu(String popupName, JComponent jc, ColorPickerEnumProperty<?> property, Runnable runnable) {
        ContextualMenuHelper cmh = getContextualMenuHelper(jc);
        cmh.addPopupMenu(popupName, property, runnable);
    }

    public ContextualMenuHelper() {
    }

    public ContextualMenuHelper(JComponent jc) {
        addComponent(jc);
    }

    /** Install this contextual menu on the component provided. */
    public void addComponent(JComponent jc) {
        jc.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                showPopupMenu(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }

            private void showPopupMenu(final MouseEvent e) {
                if (!e.isPopupTrigger())
                    return;
                e.consume();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        showPopup(e.getComponent(), e.getX(), e.getY());
                    }
                });
            }
        });

        jc.putClientProperty(MENU_KEY, this);
    }

    JPopupMenu popup = new JPopupMenu();

    public JMenuItem add(String menuItemName, final Runnable runnable) {
        JMenuItem menuItem = new JMenuItem(menuItemName);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(runnable);
            }
        });
        popup.add(menuItem);
        return menuItem;
    }

    public JMenuItem addToggle(final ColorPickerBooleanProperty property, final Runnable runnable) {
        final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(property.getName());
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                property.setValue(menuItem.isSelected());
                SwingUtilities.invokeLater(runnable);
            }
        });
        property.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        menuItem.setSelected(property.getValue());
                    }
                });
                SwingUtilities.invokeLater(runnable);
            }
        });
        popup.add(menuItem);
        return menuItem;
    }

    @SuppressWarnings("rawtypes")
    public JMenu addPopupMenu(String popupName, final ColorPickerEnumProperty property, final Runnable runnable) {
        Object[] values = property.getValues();
        JMenu myPopup = new JMenu(popupName);
        for (int a = 0; a < values.length; a++) {
            final Object currentValue = values[a];
            final JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(currentValue.toString());
            myPopup.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                @SuppressWarnings("unchecked")
                public void actionPerformed(ActionEvent e) {
                    property.setValue(currentValue);
                    SwingUtilities.invokeLater(runnable);
                }
            });

            property.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            menuItem.setSelected(currentValue.equals(property.getValue()));
                        }
                    });
                    SwingUtilities.invokeLater(runnable);
                }
            });
            menuItem.setSelected(currentValue.equals(property.getValue()));
        }
        popup.add(myPopup);
        return myPopup;
    }

    protected void showPopup(Component c, int x, int y) {
        popup.show(c, x, y);
    }

    /**
     * Clear any registered contextual menu information for this component.
     * 
     * @param component the component to purge all contextual menu info for.
     */
    public static void clear(JComponent component) {
        ContextualMenuHelper cmh = (ContextualMenuHelper) component.getClientProperty(MENU_KEY);
        if (cmh == null)
            return;
        cmh.popup.removeAll();
    }

    public void addMenuItem(JMenuItem menuItem) {
        popup.add(menuItem);
    }

    public void removeMenuItem(JMenuItem menuItem) {
        popup.remove(menuItem);
    }
}
