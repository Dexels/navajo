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
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.expression.api.FunctionDefinition;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.compiled.GiveLongTestFunction;
import com.dexels.navajo.expression.compiled.GivePercentageTestFunction;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.parser.Expression;

public class TestCompiledPercentageExpression {

    @Before
    public void setup() {

        FunctionInterface giveLongFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(giveLongFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        FunctionInterface givePercentageFunction = new GivePercentageTestFunction();
        fd = new FunctionDefinition(givePercentageFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToPercentage", fd);
    }

    @Test
    public void testAddWithPercentage() {

        Operand result;
        result = Expression.evaluate("ToPercentage(15) + ToPercentage(10)", null, null, null);
        Assert.assertEquals(new Percentage(25), result.value);

        result = Expression.evaluate("15 + ToPercentage(10)", null, null, null);
        Assert.assertEquals(new Percentage(25), result.value);

        result = Expression.evaluate("ToPercentage(15) + 10", null, null, null);
        Assert.assertEquals(new Percentage(25), result.value);

        result = Expression.evaluate("ToLong(15) + ToPercentage(10)", null, null, null);
        Assert.assertEquals(new Percentage(25), result.value);

        result = Expression.evaluate("ToPercentage(15) + ToLong(10)", null, null, null);
        Assert.assertEquals(new Percentage(25), result.value);

        result = Expression.evaluate("15.0 + ToPercentage(10)", null, null, null);
        Assert.assertEquals(new Percentage(25), result.value);

        result = Expression.evaluate("ToPercentage(15) + 10.0", null, null, null);
        Assert.assertEquals(new Percentage(25), result.value);
    }

    @Test
    public void testSubtractWithPercentage() {

        Operand result;
        result = Expression.evaluate("ToPercentage(15) - ToPercentage(10)", null, null, null);
        Assert.assertEquals(new Percentage(5), result.value);

        result = Expression.evaluate("15 - ToPercentage(10)", null, null, null);
        Assert.assertEquals(new Percentage(5), result.value);

        result = Expression.evaluate("ToPercentage(15) - 10", null, null, null);
        Assert.assertEquals(new Percentage(5), result.value);

        result = Expression.evaluate("ToLong(15) - ToPercentage(10)", null, null, null);
        Assert.assertEquals(new Percentage(5), result.value);

        result = Expression.evaluate("ToPercentage(15) - ToLong(10)", null, null, null);
        Assert.assertEquals(new Percentage(5), result.value);

        result = Expression.evaluate("15.0 - ToPercentage(10)", null, null, null);
        Assert.assertEquals(new Percentage(5), result.value);

        result = Expression.evaluate("ToPercentage(15) - 10.0", null, null, null);
        Assert.assertEquals(new Percentage(5), result.value);
    }

}
