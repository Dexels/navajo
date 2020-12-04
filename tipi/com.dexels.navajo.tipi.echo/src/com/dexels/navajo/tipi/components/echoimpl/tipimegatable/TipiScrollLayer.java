/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.tipimegatable;

import java.util.Stack;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
 * @author not attributable
 * @version 1.0
 */
public class TipiScrollLayer extends TipiTableBaseLayer {
    // private int direction = BoxLayout.Y_AXIS;

    public TipiScrollLayer(TipiMegaTable tmt) {
        super(tmt);
    }

    public void loadLayer(XMLElement elt) {
        super.loadLayer(elt);
        /**
         * @todo Implement this
         *       com.dexels.navajo.tipi.components.swingimpl.tipimegatable.TipiMegaTableLayer
         *       abstract method
         */
        messagePath = elt.getStringAttribute("messagePath");
        // String direc = elt.getStringAttribute("direction");
        // if ("horizontal".equals(direc)) {
        // direction = BoxLayout.X_AXIS;
        // }
        // if ("vertical".equals(direc)) {
        // direction = BoxLayout.Y_AXIS;
        // }
        // titleFontString = elt.getStringAttribute("titleFont");
        // if (titleFontString != null) {
        // try {
        // titleFont = (Font) myTable.evaluateExpression(titleFontString);
        // }
        // catch (Exception ex) {
        // ex.printStackTrace();
        // }
        // }
//        scroll = elt.getBooleanAttribute("scroll", "true", "false", false);
    }

    public void loadData(final Navajo n, Message current, Stack layerStack, final Component currentPanel) {
        Message nextMessage = null;
        if (current == null) {
            nextMessage = n.getMessage(messagePath);
        } else {
            nextMessage = current.getMessage(messagePath);
        }
        // logger.info("scroll. Loading with nextMessage:
        // "+nextMessage.getName()+" type: "+nextMessage.getType());
        // logger.info("My messagePatH: "+messagePath);
        if (layerStack.isEmpty()) {
            return;
        }
        final Stack newStack = (Stack) layerStack.clone();
        final TipiTableBaseLayer nextLayer = (TipiTableBaseLayer) newStack.pop();
        final Message msg = nextMessage;
        Column jt = new Column();
        currentPanel.add(jt);
        for (int i = 0; i < msg.getArraySize(); i++) {
            Message cc = msg.getMessage(i);
//            String title = null;
//            Operand titleOperand = myTable.getContext().evaluate(titleProperty, myTable, null, cc.getRootDoc(), cc);
//            if (titleOperand == null) {
//                Property titleProp = cc.getProperty(titleProperty);
//                title = "";
//                if (titleProp != null) {
//                    title = titleProp.getValue();
//                }
//            } else {
//                title = "" + titleOperand.value;
//            }
            // if (titleProp!=null) {
            // logger.info("*********\nDEPRECATED: You used only a
            // propertyname as title in your scroll layer, in TipiMegaTabel\nYou
            // should just use an expression..\n********");
            // title = titleProp.getValue();
            // } else {
            // if (titleOperand!=null) {
            // title = ""+titleOperand.value;
            // }
            // }

            Column newPanel = new Column();
            // if (titleFont!=null) {
            // newPanel.setBorder(BorderFactory.createTitledBorder(newPanel.getBorder(),title,1,1,titleFont));
            // } else {
            // newPanel.setBorder(BorderFactory.createTitledBorder(title));
            // }
            jt.add(newPanel);
            nextLayer.loadData(n, cc, newStack, newPanel);
        }
    }

}
