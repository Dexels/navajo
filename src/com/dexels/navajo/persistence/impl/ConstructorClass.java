package com.dexels.navajo.persistence.impl;


import com.dexels.navajo.persistence.Constructor;
import com.dexels.navajo.persistence.Persistable;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.xml.XMLDocumentUtils;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class ConstructorClass implements Constructor {

    String content;

    public ConstructorClass(String content) {
        this.content = content;
    }

    public Persistable construct() throws Exception {

        java.io.StringBufferInputStream buffer = new java.io.StringBufferInputStream(content);
        Document d = XMLDocumentUtils.createDocument(buffer, false);

        d.getDocumentElement().normalize();

        Navajo pc = new Navajo(d);

        return pc;
    }

}
