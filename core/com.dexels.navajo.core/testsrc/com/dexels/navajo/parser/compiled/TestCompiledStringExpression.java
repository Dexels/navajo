/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.parser.compiled;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.FunctionDefinition;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.compiled.GiveLongTestFunction;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.parser.Expression;

public class TestCompiledStringExpression {

    //  ADD

    @Test
    public void testAddTwoStrings() {

        Operand result = Expression.evaluate("'bla' + 'bli'", null, null, null);

        assertEquals("blabli", result.value);
    }

    @Test
    public void testAddIntegerAndString() {

        Operand result = Expression.evaluate("15 + 'bla'", null, null, null);

        assertEquals("15bla", result.value);
    }

    @Test
    public void testAddStringAndInteger() {

        Operand result = Expression.evaluate("'bla' + 10", null, null, null);

        assertEquals("bla10", result.value);
    }

    @Test
    public void testAddLongAndString() {

        FunctionInterface giveLongFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(giveLongFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        Operand result = Expression.evaluate("ToLong(15) + 'bla'", null, null, null);

        assertEquals("15bla", result.value);
    }

    @Test
    public void testAddStringAndLong() {

        FunctionInterface giveLongFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(giveLongFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        Operand result = Expression.evaluate("'bla' + ToLong(10)", null, null, null);

        assertEquals("bla10", result.value);
    }

    @Test
    public void testAddDoubleAndString() {

        Operand result = Expression.evaluate("15.0 + 'bla'", null, null, null);

        assertEquals("15.0bla", result.value);
    }

    @Test
    public void testAddStringAndDouble() {

        Operand result = Expression.evaluate("'bla' + 10.0", null, null, null);

        assertEquals("bla10.0", result.value);
    }

    // SUBTRACT

    @Test(expected = TMLExpressionException.class)
    public void testSubtractTwoStrings() {

        Expression.evaluate("'bla' - 'bli'", null, null, null);
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractIntegerAndString() {

        Expression.evaluate("15 - 'bla'", null, null, null);
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractStringAndInteger() {

        Expression.evaluate("'bla' - 10", null, null, null);
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractLongAndString() {

        FunctionInterface giveLongFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(giveLongFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        Expression.evaluate("ToLong(15) - 'bla'", null, null, null);
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractStringAndLong() {

        FunctionInterface giveLongFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(giveLongFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        Expression.evaluate("'bla' - ToLong(10)", null, null, null);
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractDoubleAndString() {

        Expression.evaluate("15.0 - 'bla'", null, null, null);
    }

    @Test(expected = TMLExpressionException.class)
    public void testSubtractStringAndDouble() {

        Expression.evaluate("'bla' - 10.0", null, null, null);
    }

}
