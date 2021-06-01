/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dexels.navajo.expression.api.TMLExpressionException;

public class UtilsStringTest {

    //  ADD

    @Test
    public void testAddTwoStrings() {

        String a = "bla";
        String b = "bli";

        String result = (String) Utils.add(a, b, "\"bla\" + \"bli\"");

        assertEquals("blabli", result);
    }

    @Test
    public void testAddIntegerAndString() {

        Integer a = 15;
        String b = "bla";

        String result = (String) Utils.add(a, b, "15 + \"bla\"");

        assertEquals("15bla", result);
    }

    @Test
    public void testAddStringAndInteger() {

        String a = "bla";
        Integer b = 10;

        String result = (String) Utils.add(a, b, "\"bla\" + 10");

        assertEquals("bla10", result);
    }

    @Test
    public void testAddLongAndString() {

        Long a = 15L;
        String b = "bla";

        String result = (String) Utils.add(a, b, "ToLong(15) + \"bla\"");

        assertEquals("15bla", result);
    }

    @Test
    public void testAddStringAndLong() {

        String a = "bla";
        Long b = 10L;

        String result = (String) Utils.add(a, b, "\"bla\" + ToLong(10)");

        assertEquals("bla10", result);
    }

    @Test
    public void testAddDoubleAndString() {

        Double a = 15.0;
        String b = "bla";

        String result = (String) Utils.add(a, b, "15.0 + \"bla\"");

        assertEquals("15.0bla", result);
    }

    @Test
    public void testAddStringAndDouble() {

        String a = "bla";
        Double b = 10.0;

        String result = (String) Utils.add(a, b, "\"bla\" + 10.0");

        assertEquals("bla10.0", result);
    }

    // SUBTRACT

    @Test(expected = TMLExpressionException.class)
    public void testSubtractTwoStrings() {

        String a = "bla";
        String b = "bli";

        Utils.subtract(a, b, "\"bla\" - \"bli\"");
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractIntegerAndString() {

        Integer a = 15;
        String b = "bla";

        Utils.subtract(a, b, "15 - \"bla\"");
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractStringAndInteger() {

        String a = "bla";
        Integer b = 10;

        Utils.subtract(a, b, "\"bla\" - 10");
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractLongAndString() {

        Long a = 15L;
        String b = "bla";

        Utils.subtract(a, b, "ToLong(15) - \"bla\"");
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractStringAndLong() {

        String a = "bla";
        Long b = 10L;

        Utils.subtract(a, b, "\"bla\" - ToLong(10)");
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractDoubleAndString() {

        Double a = 15.0;
        String b = "bla";

        Utils.subtract(a, b, "15.0 - \"bla\"");
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractStringAndDouble() {

        String a = "bla";
        Double b = 10.0;

        Utils.subtract(a, b, "\"bla\" - 10.0");
    }

}
