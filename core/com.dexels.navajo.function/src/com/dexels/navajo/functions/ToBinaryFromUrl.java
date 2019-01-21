package com.dexels.navajo.functions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * <p>
 * Title: Navajo Product Project
 * </p>
 * <p>
 * Description: This is the official source for the Navajo server
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class ToBinaryFromUrl extends FunctionInterface {
    private final static Logger logger = LoggerFactory.getLogger(ToBinaryFromUrl.class);

    public ToBinaryFromUrl() {
    }

    @Override
    public String remarks() {
        return "Load a binary from a URL";
    }

    @Override
    public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = getOperand(0);
        String s = (String) o;
        try {
            URL u = new URL(s);
            Binary b = new Binary(u.openStream());
            return b;
        } catch (MalformedURLException e) {
            throw new TMLExpressionException("Bad url in function ToBinaryFromUrl: " + s);
        } catch (IOException e) {
            // throw new TMLExpressionException("Error opening url in function
            // ToBinaryFromUrl: "+s);
            logger.warn("IOException in ToBinaryFromUrl (URL {}) error: {}. Return null", s, e.getMessage());
            return null;
        }
    }

    @Override
    public String usage() {
        return "ToBinaryFromUrl(String): Binary";
    }

}
