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

public class UtilsLongTest {

    //  ADD

    @Test
    public void testAddTwoLongs() {

        Long a = 15L;
        Long b = 10L;

        long result = (Long) Utils.add(a, b, "ToLong(15) + ToLong(10)");

        assertEquals(25L, result);
    }

    @Test
    public void testAddIntegerAndLong() {

        Integer a = 15;
        Long b = 10L;

        long result = (Long) Utils.add(a, b, "15 + ToLong(10)");

        assertEquals(25L, result);
    }

    @Test
    public void testAddLongAndInteger() {

        Long a = 15L;
        Integer b = 10;

        long result = (Long) Utils.add(a, b, "ToLong(15) + 10");

        assertEquals(25L, result);
    }

    @Test
    public void testAddDoubleAndLong() {

        Double a = 15.0;
        Long b = 10L;

        double result = (Double) Utils.add(a, b, "ToDouble(15) + ToLong(10)");

        assertEquals(25.0, result, 0.000001);
    }

    @Test
    public void testAddLongAndDouble() {

        Long a = 15L;
        Double b = 10.0;

        double result = (Double) Utils.add(a, b, "ToLong(15) + ToDouble(10)");

        assertEquals(25.0, result, 0.000001);
    }

    // SUBTRACT

    public void testSubtractTwoLongs() {

        Long a = 15L;
        Long b = 10L;

        long result = (Long) Utils.subtract(a, b);

        assertEquals(5L, result);
    }

    @Test
    public void testSubtractIntegerAndLong() {

        Integer a = 15;
        Long b = 10L;

        long result = (Long) Utils.subtract(a, b);

        assertEquals(5L, result);
    }

    @Test
    public void testSubtractLongAndInteger() {

        Long a = 15L;
        Integer b = 10;

        long result = (Long) Utils.subtract(a, b);

        assertEquals(5L, result);
    }

    @Test
    public void testSubtractDoubleAndLong() {

        Double a = 15.0;
        Long b = 10L;

        double result = (Double) Utils.subtract(a, b);

        assertEquals(5.0, result, 0.000001);
    }

    @Test
    public void testSubtractLongAndDouble() {

        Long a = 15L;
        Double b = 10.0;

        double result = (Double) Utils.subtract(a, b);

        assertEquals(5.0, result, 0.000001);
    }

    // DOUBLE CONVERSION

    @Test
    public void testGetDoubleValueFromLong() {

        Long a = 15L;

        double result = Utils.getDoubleValue(a);

        assertEquals(15.0, result, 0.000001);
    }

}
