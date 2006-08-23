package com.dexels.navajo.tipi.components.echoimpl;

import java.util.*;

import nextapp.echo2.app.*;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.echoimpl.impl.*;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;
import com.dexels.navajo.tipi.tipixml.XMLElement;

import echopointng.ContainerEx;
import echopointng.able.Positionable;
import echopointng.table.PageableTableNavigation;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiTable extends TipiEchoDataComponentImpl {

    private String messagePath = "";

    private boolean colDefs = false;

    private MessageTable myTable;

    public TipiTable() {
    }

    public Object createContainer() {
         ContainerEx myContainer = new ContainerEx();
        myTable = new MessageTable();
        myContainer.setPosition(Positionable.STATIC);
        myTable.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    performTipiEvent("onSelectionChanged", null, true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

         myTable.addTableEditorListener(new TableEditorListener(){

            public void propertyChanged(Property p, String eventType, int column, int row) {
//            	System.err.println("FIRINGGGGGGGGGG!!!!!!!!!!!");
            	Map event = new HashMap();
                event.put("column", new Integer(column));
                event.put("row", new Integer(row));
                event.put("new", p.getValue());
                event.put("message", p.getParentMessage());
                event.put("name", p.getName());
                try {
                    performTipiEvent(eventType, event, true);
                } catch (TipiException e) {
                    e.printStackTrace();
                }
                
            }});
         myContainer.add(myTable);
//         myContainer.setBackground(new Color(255,0,0));
    return myContainer;
//         return myTable;
    }

    public Object getActualComponent() {
        return myTable;
    }

    public void loadData(Navajo n, TipiContext context, String method) throws TipiException {
        super.loadData(n, context, method);
//        ((ContainerEx)getContainer()).setzIndex(0);
        
        MessageTable mm = (MessageTable) getActualComponent();
        // System.err.println("Navajo: ");
        // try {
        // n.write(System.err);
        // } catch (NavajoException e) {
        // e.printStackTrace();
        // }
        // System.err.println("------------------------------------------------------------------------------------>>
        // Path: " + messagePath);
        Message m = n.getMessage(messagePath);
        // System.err.println("------------------------------------------------------------------------------------>>
        // Got message: " + m);
        if (m != null) {
            if (!colDefs) {
                mm.removeAllColumns();
                ArrayList props = m.getMessage(0).getAllProperties();
                for (int i = 0; i < props.size(); i++) {
                    Property p = (Property) props.get(i);
                    mm.addColumn(p.getName(), p.getName(), false, -1);
                }
            }
            mm.setMessage(m);
        }
    }

    public Object getComponentValue(String name) {
        MessageTable mm = (MessageTable) getActualComponent();
        if ("selectedMessage".equals(name)) {
            return mm.getSelectedMessage();
        }
        if (name.equals("selectedIndex")) {
            return new Integer(mm.getSelectedIndex());
        }
        return super.getComponentValue(name);
    }

    public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
        MessageTable mm = (MessageTable) getActualComponent();
        // TipiSwingColumnAttributeParser cap = new
        // TipiSwingColumnAttributeParser();
        
        boolean editableColumnsFound = false;
        messagePath = (String) elm.getAttribute("messagepath");
        if (messagePath != null) {
            if (messagePath.startsWith("'") && messagePath.endsWith("'")) {
                messagePath = messagePath.substring(1, messagePath.length() - 1);
            }
        }
        super.load(elm, instance, context);
        Vector children = elm.getChildren();
        for (int i = 0; i < children.size(); i++) {
            XMLElement child = (XMLElement) children.elementAt(i);
            if (child.getName().equals("column")) {
                Operand o = evaluate(child.getStringAttribute("label"), this, null);
                String label = null;
                if (o == null) {
                    label = "";
                } else {
                    label = (String) o.value;
                    if (label == null) {
                        label = "";
                    }
                }
                String name = (String) child.getAttribute("name");
                String editableString = (String) child.getAttribute("editable");
                int size = child.getIntAttribute("size", -1);

                boolean editable = "true".equals(editableString);
                colDefs = true;
                // System.err.println("Adding column " + name + ", editable: " +
                // editable);
                mm.addColumn(name, label, editable, size);
                editableColumnsFound = editableColumnsFound || editable;
                // mm.messageChanged();
            }
            if (child.getName().equals("column-attribute")) {
                String name = (String) child.getAttribute("name");
                String type = (String) child.getAttribute("type");
                // if (name != null && type != null && !name.equals("") &&
                // !type.equals("")) {
                // columnAttributes.put(name, cap.parseAttribute(child));
                // }
            }
        }
            mm.setSelectionEnabled(!editableColumnsFound);
        // mm.setColumnAttributes(columnAttributes);
    }

//    public void setComponentValue(final String name, final Object object) {
//
//        if ("w".equals(name)) {
//        ContainerEx cont = (ContainerEx) getContainer();
//        cont.setWidth( new Extent(( (Integer) object).intValue(),Extent.PX));
//        }
//        if ("h".equals(name)) {
//        ContainerEx cont = (ContainerEx) getContainer();
//        cont.setHeight(  new Extent(( (Integer) object).intValue(),Extent.PX));
//        }
//       super.setComponentValue(name, object);
//   }

    
    public void processStyles() {
         super.processStyles();
        Color c = ColorParser.parseColor(getStyle("foreground"));
        if (c!=null) {
            myTable.setForeground(c);
        }
        c = ColorParser.parseColor(getStyle("background"));
        if (c!=null) {
            myTable.setBackground(c);
        }
        c = ColorParser.parseColor(getStyle("pressedforeground"));
        if (c!=null) {
            myTable.setHeaderPressedForeground(c);
        }
        c = ColorParser.parseColor(getStyle("pressedbackground"));
        if (c!=null) {
            myTable.setHeaderPressedBackground(c);
        }
        c = ColorParser.parseColor(getStyle("rolloverbackground"));
        if (c!=null) {
            myTable.setHeaderRolloverBackground(c);
        }
        c = ColorParser.parseColor(getStyle("rolloverforeground"));
        if (c!=null) {
            myTable.setHeaderRolloverForeground(c);
        }
        String headHeight = getStyle("headerheight");
        if (headHeight!=null) {
            int hh = Integer.parseInt(headHeight);
                myTable.setHeaderHeight(hh);
        }
      
    }    
    
    
    
}
