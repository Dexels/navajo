/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dexels.navajo.functions;

import java.util.Date;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author boer001
 */
public final class NowAsDate extends FunctionInterface {

    @Override
    public String remarks() {
        return "NowAsDate() returns the current timestamp as a java.util.Date. ";
    }

    @Override
    public final Object evaluate() throws TMLExpressionException {
        return new Date();
    }

    @Override
    public String usage() {
        return "NowAsDate()";
    }

    public static void main(String[] args) throws Exception {
        NowAsDate n = new NowAsDate();
        n.reset();
        System.err.println("result NowAsDate()= " + ((Date)n.evaluate()).getTime());
    }
}