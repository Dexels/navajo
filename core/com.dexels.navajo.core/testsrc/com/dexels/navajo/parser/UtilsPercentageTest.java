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

import com.dexels.navajo.document.types.Percentage;

public class UtilsPercentageTest {

    //  ADD

    @Test
    public void testAddTwoPercentages() {

        Percentage a = new Percentage(15);
        Percentage b = new Percentage(10);

        Percentage result = (Percentage) Utils.add(a, b, "ToPercentage(15) + ToPercentage(10)");

        assertEquals(new Percentage(25), result);
    }

    @Test
    public void testAddIntegerAndPercentage() {

        Integer a = 15;
        Percentage b = new Percentage(10);

        Percentage result = (Percentage) Utils.add(a, b, "15 + ToPercentage(10)");

        assertEquals(new Percentage(25), result);
    }

    @Test
    public void testAddPercentageAndInteger() {

        Percentage a = new Percentage(15);
        Integer b = 10;

        Percentage result = (Percentage) Utils.add(a, b, "ToPercentage(15) + 10");

        assertEquals(new Percentage(25), result);
    }

    @Test
    public void testAddLongAndPercentage() {

        Long a = 15L;
        Percentage b = new Percentage(10);

        Percentage result = (Percentage) Utils.add(a, b, "ToLong(15) + ToPercentage(10)");

        assertEquals(new Percentage(25), result);
    }

    @Test
    public void testAddPercentageAndLong() {

        Percentage a = new Percentage(15);
        Long b = 10L;

        Percentage result = (Percentage) Utils.add(a, b, "ToPercentage(15) + ToLong(10)");

        assertEquals(new Percentage(25), result);
    }

    @Test
    public void testAddDoubleAndPercentage() {

        Double a = 15.0;
        Percentage b = new Percentage(10);

        Percentage result = (Percentage) Utils.add(a, b, "15.0 + ToPercentage(10)");

        assertEquals(new Percentage(25), result);
    }

    @Test
    public void testAddPercentageAndDouble() {

        Percentage a = new Percentage(15);
        Double b = 10.0;

        Percentage result = (Percentage) Utils.add(a, b, "ToPercentage(15) + 10.0");

        assertEquals(new Percentage(25), result);
    }

    // SUBTRACT

    @Test
    public void testSubtractTwoPercentages() {

        Percentage a = new Percentage(15);
        Percentage b = new Percentage(10);

        Percentage result = (Percentage) Utils.subtract(a, b, "ToPercentage(15) - ToPercentage(10)");

        assertEquals(new Percentage(5), result);
    }

    @Test
    public void testSubtractIntegerAndPercentage() {

        Integer a = 15;
        Percentage b = new Percentage(10);

        Percentage result = (Percentage) Utils.subtract(a, b, "15 - ToPercentage(10)");

        assertEquals(new Percentage(5), result);
    }

    @Test
    public void testSubtractPercentageAndInteger() {

        Percentage a = new Percentage(15);
        Integer b = 10;

        Percentage result = (Percentage) Utils.subtract(a, b, "ToPercentage(15) - 10");

        assertEquals(new Percentage(5), result);
    }

    @Test
    public void testSubtractLongAndPercentage() {

        Long a = 15L;
        Percentage b = new Percentage(10);

        Percentage result = (Percentage) Utils.subtract(a, b, "ToLong(15) - ToPercentage(10)");

        assertEquals(new Percentage(5), result);
    }

    @Test
    public void testSubtractPercentageAndLong() {

        Percentage a = new Percentage(15);
        Long b = 10L;

        Percentage result = (Percentage) Utils.subtract(a, b, "ToPercentage(15) - ToLong(10)");

        assertEquals(new Percentage(5), result);
    }

    @Test
    public void testSubtractDoubleAndPercentage() {

        Double a = 15.0;
        Percentage b = new Percentage(10);

        Percentage result = (Percentage) Utils.subtract(a, b, "15.0 - ToPercentage(10)");

        assertEquals(new Percentage(5), result);
    }

    @Test
    public void testSubtractPercentageAndDouble() {

        Percentage a = new Percentage(15);
        Double b = 10.0;

        Percentage result = (Percentage) Utils.subtract(a, b, "ToPercentage(15) - 10.0");

        assertEquals(new Percentage(5), result);
    }

}
