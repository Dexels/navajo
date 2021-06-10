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

import com.dexels.navajo.document.types.Money;

public class UtilsMoneyTest {

    //  ADD

    @Test
    public void testAddTwoMoneys() {

        Money a = new Money(15);
        Money b = new Money(10);

        Money result = (Money) Utils.add(a, b, "ToMoney(15) + ToMoney(10)");

        assertEquals(new Money(25), result);
    }

    @Test
    public void testAddIntegerAndMoney() {

        Integer a = 15;
        Money b = new Money(10);

        Money result = (Money) Utils.add(a, b, "15 + ToMoney(10)");

        assertEquals(new Money(25), result);
    }

    @Test
    public void testAddMoneyAndInteger() {

        Money a = new Money(15);
        Integer b = 10;

        Money result = (Money) Utils.add(a, b, "ToMoney(15) + 10");

        assertEquals(new Money(25), result);
    }

    @Test
    public void testAddLongAndMoney() {

        Long a = 15L;
        Money b = new Money(10);

        Money result = (Money) Utils.add(a, b, "ToLong(15) + ToMoney(10)");

        assertEquals(new Money(25), result);
    }

    @Test
    public void testAddMoneyAndLong() {

        Money a = new Money(15);
        Long b = 10L;

        Money result = (Money) Utils.add(a, b, "ToMoney(15) + ToLong(10)");

        assertEquals(new Money(25), result);
    }

    @Test
    public void testAddDoubleAndMoney() {

        Double a = 15.0;
        Money b = new Money(10);

        Money result = (Money) Utils.add(a, b, "15.0 + ToMoney(10)");

        assertEquals(new Money(25), result);
    }

    @Test
    public void testAddMoneyAndDouble() {

        Money a = new Money(15);
        Double b = 10.0;

        Money result = (Money) Utils.add(a, b, "ToMoney(15) + 10.0");

        assertEquals(new Money(25), result);
    }

    // SUBTRACT

    @Test
    public void testSubtractTwoMoneys() {

        Money a = new Money(15);
        Money b = new Money(10);

        Money result = (Money) Utils.subtract(a, b, "ToMoney(15) - ToMoney(10)");

        assertEquals(new Money(5), result);
    }

    @Test
    public void testSubtractIntegerAndMoney() {

        Integer a = 15;
        Money b = new Money(10);

        Money result = (Money) Utils.subtract(a, b, "15 - ToMoney(10)");

        assertEquals(new Money(5), result);
    }

    @Test
    public void testSubtractMoneyAndInteger() {

        Money a = new Money(15);
        Integer b = 10;

        Money result = (Money) Utils.subtract(a, b, "ToMoney(15) - 10");

        assertEquals(new Money(5), result);
    }

    @Test
    public void testSubtractLongAndMoney() {

        Long a = 15L;
        Money b = new Money(10);

        Money result = (Money) Utils.subtract(a, b, "ToLong(15) - ToMoney(10)");

        assertEquals(new Money(5), result);
    }

    @Test
    public void testSubtractMoneyAndLong() {

        Money a = new Money(15);
        Long b = 10L;

        Money result = (Money) Utils.subtract(a, b, "ToMoney(15) - ToLong(10)");

        assertEquals(new Money(5), result);
    }

    @Test
    public void testSubtractDoubleAndMoney() {

        Double a = 15.0;
        Money b = new Money(10);

        Money result = (Money) Utils.subtract(a, b, "15.0 - ToMoney(10)");

        assertEquals(new Money(5), result);
    }

    @Test
    public void testSubtractMoneyAndDouble() {

        Money a = new Money(15);
        Double b = 10.0;

        Money result = (Money) Utils.subtract(a, b, "ToMoney(15) - 10.0");

        assertEquals(new Money(5), result);
    }

}
