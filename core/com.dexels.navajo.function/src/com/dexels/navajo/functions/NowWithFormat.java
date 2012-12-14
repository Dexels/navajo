/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dexels.navajo.functions;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author boer001
 */
public final class NowWithFormat extends FunctionInterface {

    @Override
    public String remarks() {
        return "NowWithFormat(String format) returns the current timestamp as a string in the given format.";
    }

    @Override
    public final Object evaluate() throws TMLExpressionException {
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss:SSS";

        if (this.getOperands().size() != 1) {
            throw new TMLExpressionException("NowWithFormat(String format) expected");
        }
        Object a = this.getOperands().get(0);
        if (a == null) {
            throw new TMLExpressionException("NowWithFormat(String) expected");
        }
        if (!(a instanceof String)) {
            throw new TMLExpressionException("NowWithFormat(String) expected");
        }
        dateFormat = (String) a;

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(today);
    }

    @Override
    public String usage() {
        return "NowWithFormat(String)";
    }

    public static void main(String[] args) throws Exception {
        NowWithFormat n = new NowWithFormat();
        n.reset();
        n.insertOperand("yyyy-MM-dd'T'HH:mm:ss:SSS");
        System.err.println("result NowWithFormat()= " + (String) n.evaluate());
    }
}