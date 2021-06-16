/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.functions.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;

public class TestMathFunctions {

    private FunctionFactoryInterface fff;

    private ClassLoader cl;

    @Before
    public void setUp() {
        fff = FunctionFactoryFactory.getInstance();
        cl = getClass().getClassLoader();
    }

    @Test
    public void testRoundToWhole() {

        FunctionInterface round = fff.getInstance(cl, "Round");
        round.reset();

        round.insertFloatOperand(10.5);
        round.insertIntegerOperand(0);
        double actual = (Double) round.evaluateWithTypeChecking();

        assertEquals(11.0, actual, 0.000001);
    }

    @Test
    public void testRoundToFraction() {

        FunctionInterface round = fff.getInstance(cl, "Round");
        round.reset();

        round.insertFloatOperand(10.12345);
        round.insertIntegerOperand(3);
        double actual = (Double) round.evaluateWithTypeChecking();

        assertEquals(10.123, actual, 0.000001);
    }

    @Test(expected = TMLExpressionException.class)
    public void testRoundWithBogusParameter() {

        FunctionInterface round = fff.getInstance(cl, "Round");
        round.reset();

        round.insertStringOperand("10.5");
        round.insertIntegerOperand(0);
        round.evaluateWithTypeChecking();
    }

    @Test
    public void testMin() {

        FunctionInterface min = fff.getInstance(cl, "Min");
        min.reset();

        min.insertIntegerOperand(20);
        min.insertIntegerOperand(10);
        int actual = (Integer) min.evaluateWithTypeChecking();

        assertEquals(10, actual);
    }

    @Test
    public void testMinNumberList() {

        FunctionInterface min = fff.getInstance(cl, "Min");
        min.reset();

        List<Object> numbers = new ArrayList<>();
        numbers.add(Math.PI);
        numbers.add(3);
        numbers.add(5L);
        min.insertListOperand(numbers);
        double actual = (Double) min.evaluateWithTypeChecking();

        assertEquals(3.0, actual, 0.000001);
    }

    @Test
    public void testMinDateList() throws ParseException {

        FunctionInterface min = fff.getInstance(cl, "Min");
        min.reset();

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = new ArrayList<>();
        dates.add(parser.parse("2017-11-10"));
        dates.add(parser.parse("2015-11-10"));
        dates.add(parser.parse("2019-10-11"));
        min.insertListOperand(dates);
        Date actual = (Date) min.evaluateWithTypeChecking();

        assertEquals(parser.parse("2015-11-10"), actual);
    }

    @Test
    public void testMax() {

        FunctionInterface max = fff.getInstance(cl, "Max");
        max.reset();

        max.insertIntegerOperand(20);
        max.insertIntegerOperand(10);
        int actual = (Integer) max.evaluateWithTypeChecking();

        assertEquals(20, actual);
    }

    @Test
    public void testMaxNumberList() {

        FunctionInterface max = fff.getInstance(cl, "Max");
        max.reset();

        List<Object> numbers = new ArrayList<>();
        numbers.add(Math.PI);
        numbers.add(3);
        numbers.add(5L);
        max.insertListOperand(numbers);
        double actual = (Double) max.evaluateWithTypeChecking();

        assertEquals(5.0, actual, 0.000001);
    }

    @Test
    public void testMaxDateList() throws ParseException {

        FunctionInterface max = fff.getInstance(cl, "Max");
        max.reset();

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = new ArrayList<>();
        dates.add(parser.parse("2017-11-10"));
        dates.add(parser.parse("2015-11-10"));
        dates.add(parser.parse("2019-10-11"));
        max.insertListOperand(dates);
        Date actual = (Date) max.evaluateWithTypeChecking();

        assertEquals(parser.parse("2019-10-11"), actual);
    }

    @Test
    public void testCeil() {

        FunctionInterface ceil = fff.getInstance(cl, "Ceil");
        ceil.reset();

        ceil.insertFloatOperand(1.1);
        double actual = (Double) ceil.evaluate();

        assertEquals(2.0, actual, 0.000001);
    }

    @Test(expected = TMLExpressionException.class)
    public void testCeilWithBogusParameter() {

        FunctionInterface ceil = fff.getInstance(cl, "Ceil");
        ceil.reset();

        ceil.insertStringOperand("1.1");
        ceil.evaluate();
    }

    @Test
    public void testFloor() {

        FunctionInterface floor = fff.getInstance(cl, "Floor");
        floor.reset();

        floor.insertFloatOperand(1.1);
        double actual = (Double) floor.evaluate();

        assertEquals(1.0, actual, 0.000001);
    }

    @Test(expected = TMLExpressionException.class)
    public void testFloorWithBogusParameter() {

        FunctionInterface floor = fff.getInstance(cl, "Floor");
        floor.reset();

        floor.insertStringOperand("1.1");
        floor.evaluate();
    }


}