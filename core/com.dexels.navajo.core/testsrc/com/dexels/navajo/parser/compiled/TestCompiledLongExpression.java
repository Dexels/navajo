/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.parser.compiled;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.FunctionDefinition;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.compiled.GiveLongTestFunction;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.parser.Expression;

public class TestCompiledLongExpression {

    @Test
    public void testAddWithLongs() {

        FunctionInterface helperFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(helperFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        Operand result;
        result = Expression.evaluate("ToLong(1) + ToLong(1)", null, null, null);
        Assert.assertEquals(2L, (long) result.value);

        result = Expression.evaluate("1 + ToLong(1)", null, null, null);
        Assert.assertEquals(2L, (long) result.value);

        result = Expression.evaluate("ToLong(1) + 1", null, null, null);
        Assert.assertEquals(2L, (long) result.value);

        result = Expression.evaluate("1.0 + ToLong(1)", null, null, null);
        Assert.assertEquals(2.0, (double) result.value, 0.000001);

        result = Expression.evaluate("ToLong(1) + 1.0", null, null, null);
        Assert.assertEquals(2.0, (double) result.value, 0.000001);
    }

    @Test
    public void testSubtractWithLongs() {

        FunctionInterface helperFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(helperFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        Operand result;
        result = Expression.evaluate("ToLong(2) - ToLong(1)", null, null, null);
        Assert.assertEquals(1L, (long) result.value);

        result = Expression.evaluate("2 - ToLong(1)", null, null, null);
        Assert.assertEquals(1L, (long) result.value);

        result = Expression.evaluate("ToLong(2) - 1", null, null, null);
        Assert.assertEquals(1L, (long) result.value);

        result = Expression.evaluate("2.0 - ToLong(1)", null, null, null);
        Assert.assertEquals(1.0, (double) result.value, 0.000001);

        result = Expression.evaluate("ToLong(2) - 1.0", null, null, null);
        Assert.assertEquals(1.0, (double) result.value, 0.000001);
    }

    @Test
    public void testMultiplyWithLongs() {

        FunctionInterface helperFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(helperFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        Operand result;
        result = Expression.evaluate("ToLong(3) * ToLong(1)", null, null, null);
        Assert.assertEquals(3L, (long) result.value);

        result = Expression.evaluate("3 * ToLong(1)", null, null, null);
        Assert.assertEquals(3L, (long) result.value);

        result = Expression.evaluate("ToLong(3) * 1", null, null, null);
        Assert.assertEquals(3L, (long) result.value);

        result = Expression.evaluate("3.0 * ToLong(1)", null, null, null);
        Assert.assertEquals(3L, (double) result.value, 0.000001);

        result = Expression.evaluate("ToLong(3) * 1.0", null, null, null);
        Assert.assertEquals(3L, (double) result.value, 0.000001);
    }

    @Test
    public void testDivideWithLongs() {

        FunctionInterface helperFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(helperFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        Operand result;
        result = Expression.evaluate("ToLong(8) / ToLong(2)", null, null, null);
        Assert.assertEquals(4.0, (double) result.value, 0.000001);

        result = Expression.evaluate("8 / ToLong(2)", null, null, null);
        Assert.assertEquals(4.0, (double) result.value, 0.000001);

        result = Expression.evaluate("ToLong(8) / 2", null, null, null);
        Assert.assertEquals(4.0, (double) result.value, 0.000001);

        result = Expression.evaluate("8.0 / ToLong(2)", null, null, null);
        Assert.assertEquals(4.0, (double) result.value, 0.000001);

        result = Expression.evaluate("ToLong(8) / 2.0", null, null, null);
        Assert.assertEquals(4.0, (double) result.value, 0.000001);
    }

}
