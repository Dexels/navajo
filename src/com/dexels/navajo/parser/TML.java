package com.dexels.navajo.parser;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
import java.util.*;
import javax.xml.transform.stream.StreamResult;

import com.dexels.navajo.document.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.xml.*;
import gnu.regexp.*;


public class TML {

    public TML() {}

    public static String showValue(Object aap) {
        if (aap instanceof Integer)
            return ((Integer) aap).intValue() + "";
        else if (aap instanceof Boolean)
            return ((Boolean) aap).booleanValue() + "";
        else if (aap instanceof String)
            return (String) aap;
        else if (aap instanceof Double)
            return ((Double) aap).doubleValue() + "";
        else if (aap instanceof Date)
            return ((Date) aap).toString();
        else
            return "";
    }

    public static String showList(ArrayList aap) {

        StringBuffer result = new StringBuffer();

        result.append("{");
        for (int i = 0; i < aap.size(); i++) {
            Object o = aap.get(i);

            if (o instanceof ArrayList) {
                result.append(showList((ArrayList) o));
            } else {
                result.append(showValue(o));
            }
            if ((i + 1) < aap.size())
                result.append(",");
        }
        result.append("}");
        return result.toString();
    }

    public static void main(String args[]) throws Exception {

        Navajo doc = new Navajo();
        Message msg = null;

        msg = Message.create(doc, "vuur");
        doc.addMessage(msg);
        msg = Message.create(doc, "aap2");
        doc.addMessage(msg);
        msg = Message.create(doc, "aap1");
        doc.addMessage(msg);
        Message submsg = Message.create(doc, "noot0");

        msg.addMessage(submsg);
        msg = Message.create(doc, "aap0");
        doc.addMessage(msg);
        Property prop = Property.create(doc, "noot", Property.INTEGER_PROPERTY, "10", 0, "", Property.DIR_IN);

        msg.addProperty(prop);
        prop = Property.create(doc, "selecteer", "+", "Selectie", Property.DIR_IN);
        msg.addProperty(prop);
        Selection sel = Selection.create(doc, "optie1", "1", true);

        prop.addSelection(sel);
        sel = Selection.create(doc, "optie2", "2", true);
        prop.addSelection(sel);
        sel = Selection.create(doc, "optie3", "3", false);
        prop.addSelection(sel);
        prop = Property.create(doc, "mies", Property.FLOAT_PROPERTY, "5.0", 0, "", Property.DIR_IN);
        msg.addProperty(prop);
        prop = Property.create(doc, "datum", Property.DATE_PROPERTY, "1971-06-17", 0, "", Property.DIR_IN);
        msg.addProperty(prop);
        prop = Property.create(doc, "selectie", "1", "", Property.DIR_IN);
        msg.addProperty(prop);
        sel = Selection.create(doc, "freddy", "2", true);
        prop.addSelection(sel);
        Message msg2 = Message.create(doc, "submessage0");

        msg.addMessage(msg2);
        prop = Property.create(doc, "vuur", Property.STRING_PROPERTY, "vuur_stringtje", 0, "", Property.DIR_IN);
        msg2.addProperty(prop);
        prop = Property.create(doc, "info", Property.INTEGER_PROPERTY, "30", 0, "", Property.DIR_IN);
        msg2.addProperty(prop);
        msg2 = Message.create(doc, "submessage1");
        msg.addMessage(msg2);
        prop = Property.create(doc, "vuur", Property.STRING_PROPERTY, "vuur_stringtjes500", 0, "", Property.DIR_IN);
        msg2.addProperty(prop);
        prop = Property.create(doc, "info", Property.INTEGER_PROPERTY, "45", 0, "", Property.DIR_IN);
        msg2.addProperty(prop);
        msg2 = Message.create(doc, "submessage3");
        msg.addMessage(msg2);
        prop = Property.create(doc, "vuur", Property.STRING_PROPERTY, "vuur_stringen", 0, "", Property.DIR_IN);
        msg2.addProperty(prop);
        prop = Property.create(doc, "info", Property.INTEGER_PROPERTY, "11", 0, "", Property.DIR_IN);
        msg2.addProperty(prop);
        msg2 = Message.create(doc, "submessage2");
        msg.addMessage(msg2);
        prop = Property.create(doc, "vuur", Property.STRING_PROPERTY, "vuur_stringen", 0, "", Property.DIR_IN);
        msg2.addProperty(prop);
        prop = Property.create(doc, "info", Property.INTEGER_PROPERTY, "10", 0, "", Property.DIR_IN);
        msg2.addProperty(prop);

        XMLDocumentUtils.toXML(doc.getMessageBuffer(), null, null, new StreamResult(System.out));

        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("([/aap0/noot] + (4 * 1)) * 2");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("([aap.noot] - 5) > 5 OR (2 * 8) + 1 > 16");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("(TODAY + 5#0#0)");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("Max({6,9,2,4,10*3-1,20})");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("$mies");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("Max([/aap0/selecteer:value])");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("[aap.selecteer:value] + {4, 5}");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("Contains({[aap/selecteer:name], 'optie5'}, 'op'+'tie1')");

        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("$mies");
        java.io.StringReader input = new java.io.StringReader("$mies");

        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("Contains([/aap0/submessage.*/vuur], 'vur_stringen')");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("[/aap0/submessage.*/vuur] + 'albert'");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("Sum([/aap0/submessage.*/vuur])");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("3");
        // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream("[:value] + 4");
        Class c = Class.forName("com.dexels.navajo.parser.Aaap");

        Util.debugLog("Got class");
        Mappable mo = (Mappable) c.newInstance();

        Util.debugLog("Created instance");

        TMLParser parser = new TMLParser(input);
        Message p = doc.getMessage("aap0/submessage0");
        Selection parentSel = doc.getMessage("aap0").getProperty("selecteer").getSelection("optie1");

        parser.setNavajoDocument(doc);
        parser.setMappableObject(mo);
        parser.setParentMsg(p);
        parser.setParentSel(parentSel);

        parser.Expression();
        Object aap = parser.jjtree.rootNode().interpret();

        if (aap instanceof ArrayList)
            Util.debugLog("resultaat = " + showList((ArrayList) aap));
        else
            Util.debugLog("resultaat = " + showValue(aap));

    }
}
