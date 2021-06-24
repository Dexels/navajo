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
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;

public class TestAbsFunction {

    private FunctionFactoryInterface fff;

    private ClassLoader cl;

    @Before
    public void setUp() {
        fff = FunctionFactoryFactory.getInstance();
        cl = getClass().getClassLoader();
    }

    @Test
    public void testAbsWithInteger() {

        FunctionInterface fi = fff.getInstance(cl, "Abs");
        fi.reset();

        fi.insertIntegerOperand(-10);
        Object result = fi.evaluateWithTypeChecking();

        assertEquals(Integer.class, result.getClass());
        assertEquals(10, ((Integer) result).intValue());
    }

    @Test
    public void testAbsWithLong() {

        FunctionInterface fi = fff.getInstance(cl, "Abs");
        fi.reset();

        fi.insertLongOperand(-10L);
        Object result = fi.evaluateWithTypeChecking();

        assertEquals(Long.class, result.getClass());
        assertEquals(10L, ((Long) result).longValue());
    }

    @Test
    public void testAbsWithDouble() {

        FunctionInterface fi = fff.getInstance(cl, "Abs");
        fi.reset();

        fi.insertFloatOperand(-10.0D);
        Object result = fi.evaluateWithTypeChecking();

        assertEquals(Double.class, result.getClass());
        assertEquals(10, ((Double) result).intValue());
    }

    @Test(expected = TMLExpressionException.class)
    public void testAbsWithBogusParameter() {

        FunctionInterface fi = fff.getInstance(cl, "Abs");
        fi.reset();

        fi.insertStringOperand("-10");
        fi.evaluateWithTypeChecking();
    }

    @Test
    public void testAbsWithNull() {

        FunctionInterface fi = fff.getInstance(cl, "Abs");
        fi.reset();

        fi.insertOperand(Operand.NULL);
        Object result = fi.evaluateWithTypeChecking();

        assertNull(result);
    }

    @Test(expected = TMLExpressionException.class)
    public void testAbsWithNoParameters() {

        FunctionInterface fi = fff.getInstance(cl, "Abs");
        fi.reset();
        fi.evaluateWithTypeChecking();
    }

}
